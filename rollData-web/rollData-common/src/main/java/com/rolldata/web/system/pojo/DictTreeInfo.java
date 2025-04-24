package com.rolldata.web.system.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rolldata.core.common.exception.BusinessException;
import com.rolldata.core.util.DataSourceTool;
import com.rolldata.core.util.Reflections;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.entity.SysDictionary;
import com.rolldata.web.system.entity.SysDictionaryData;
import com.rolldata.web.system.entity.WdSysDictLevel;
import com.rolldata.web.system.util.BaseFetchFormulaUtils;
import com.rolldata.web.system.util.SQLDMLPretreatmentUtil;
import com.rolldata.web.system.util.SpecialSymbolConstants;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.EntityManager;

import static com.rolldata.web.system.util.DynamicDBUtil.SQL_PAGE_TEMPLATE;
import static com.rolldata.web.system.util.DynamicDBUtil.getAfterSelectInsertPoint;
import static com.rolldata.web.system.util.SpecialSymbolConstants.COMMA;
import static com.rolldata.web.system.util.SpecialSymbolConstants.SQL_AS;
import static com.rolldata.web.system.util.SpecialSymbolConstants.bundleColumn;

/**
 * 构建字典树对象
 *
 * @Title: DictTreeInfo
 * @Description: 构建字典树
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-12-12
 * @version: V1.0
 */
@JsonIgnoreProperties(value={"dictBefore"})
public class DictTreeInfo implements Serializable {

    private static final long serialVersionUID = 8667817472378705372L;

    /**
     * 树形结构数据
     */
    private ResponseDictJson tree;

    /**
     * 每层的列表
     */
    private Map<String, List<Map<String, String>>> levelDataMap;

    /**
     * 节点的层级
     */
    private Map<String, String> nodeLevelMap;

    /**
     * 节点的子集列表
     */
    private Map<String, List<Map<String, String>>> nodeChildDataMap;

    /**
     * 层级信息
     */
    private List<WdSysDictLevel> levelTables;

    private SysDictionary sysDictionary;

    private List<Map<String, String>> dataList;

    /**
     * 过滤条件配置
     */
    private ModelFilterConf filterConf;

    /**
     * 事务管理器,只做传递用,不执行打开关闭操作
     */
    private EntityManager entityManager;

    private static final String COMMON_SELECT = "SELECT sdd.dict_type_id as dictTypeId,sdd.id as id, sdd.dict_cde as dictCde, sdd.dict_name as dictName, " +
                                                "sdd.parent_id as parentId, " +
                                                "sdd.c_level as level, sdd.c_index as cIndex, "
        + "sdd.ext1 as ext1, sdd.ext2 as ext2, sdd.ext3 as ext3, sdd.ext4 as ext4,"
        + "sdd.ext5 as ext5, sdd.ext6 as ext6, sdd.ext7 as ext7, sdd.ext8 as ext8,"
        + "sdd.ext9 as ext9, sdd.ext10 as ext10, sdd.ext11 as ext11, sdd.ext12 as ext12,"
        + "sdd.ext13 as ext13, sdd.ext14 as ext14,sdd.ext15 as ext15,sdd.ext16 as ext16,"
        + "sdd.ext17 as ext17,sdd.ext18 as ext18,sdd.ext19 as ext19,sdd.ext20 as ext20,"
        + " sdd.create_time as createTime, sdd.create_user as createUser, " +
                                                "sdd.update_time as updateTime, " +
                                                "sdd.update_user as updateUser";

    private static final String COMMON_SELECT_ORACLE = "SELECT sdd.dict_type_id as \"dictTypeId\",sdd.id as \"id\", sdd.dict_cde as \"dictCde\", sdd.dict_name as \"dictName\", " +
            "sdd.parent_id as \"parentId\", " +
            "sdd.c_level as \"level\", sdd.c_index as \"cIndex\", "
        + "sdd.ext1 as \"ext1\", sdd.ext2 as \"ext2\", sdd.ext3 as \"ext3\", sdd.ext4 as \"ext4\","
        + "sdd.ext5 as \"ext5\", sdd.ext6 as \"ext6\", sdd.ext7 as \"ext7\", sdd.ext8 as \"ext8\","
        + "sdd.ext9 as \"ext9\", sdd.ext10 as \"ext10\", sdd.ext11 as \"ext11\", sdd.ext12 as \"ext12\","
        + "sdd.ext13 as \"ext13\", sdd.ext14 as \"ext14\",sdd.ext15 as \"ext15\",sdd.ext16 as \"ext16\","
        + "sdd.ext17 as \"ext17\", sdd.ext18 as \"ext18\",sdd.ext19 as \"ext19\",sdd.ext16 as \"ext20\","
        + " sdd.create_time as \"createTime\", sdd.create_user as \"createUser\", " +
            "sdd.update_time as \"updateTime\", " +
            "sdd.update_user as \"updateUser\"";

    /**
     * 默认排序
     */
    private static final String DEFAULT_ORDER = " order by CAST(c_index AS " + SQLDMLPretreatmentUtil.CASE_TYPE[SQLDMLPretreatmentUtil.CASE_NUMBER] + ") ASC";

    /**
     * 查询数据接口
     */

    private DictBefore dictBefore;

    private final DictBefore defaultDictBefore = new DefaultDictBefore();

    /**
     * 任务详细对象
     */
    private Map<String, Object> detailedMap;

    public DictTreeInfo() {
        this.setTree(new ResponseDictJson());
        this.setLevelDataMap(new LinkedHashMap<>());
        this.setNodeLevelMap(new HashMap<>());
        this.setNodeChildData(new HashMap<>());
    }

    /**
     * 判断类型,根据类型,拼SQL查询数据,组装树
     *
     * @param sysDictionary
     * @return
     * @throws Exception
     */
    public DictTreeInfo getTree(SysDictionary sysDictionary) throws Exception {

        if (SysDictionary.RELY_TYPE1.equals(sysDictionary.getRelyType())) {
            this.queryParentChildrenTree(sysDictionary);
        } else if (SysDictionary.RELY_TYPE2.equals(sysDictionary.getRelyType())) {
            this.queryDictTree(sysDictionary);
        } else if (SysDictionary.RELY_TYPE0.equals(sysDictionary.getRelyType())) {
            this.queryListDictTree(sysDictionary);
        }
        return this;
    }

    /**
     * 判断类型,根据已有数据,组装树
     *
     * @param sysDictionary 字典实体
     * @param dictBefore    数据接口
     * @return
     * @throws Exception
     */
    public DictTreeInfo getTree(SysDictionary sysDictionary, DictBefore dictBefore) throws Exception {

        setDictBefore(dictBefore);
        if (SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
            if (SysDictionary.RELY_TYPE1.equals(sysDictionary.getRelyType())) {
                this.queryParentChildrenTree(sysDictionary);
            } else if (SysDictionary.RELY_TYPE2.equals(sysDictionary.getRelyType())) {
                this.queryDictTree(sysDictionary);
            } else if (SysDictionary.RELY_TYPE0.equals(sysDictionary.getRelyType())) {
                this.queryListDictTree(sysDictionary);
            }
        } else {
            this.queryParentChildrenTree(sysDictionary);
        }
        return this;
    }

    /**
     * 获取系统档案数据
     *
     * @param sysDictionary
     * @return
     * @throws Exception
     */
    public List<SysDictionaryData> getSystemSysDictionaryData(SysDictionary sysDictionary) throws Exception {

        /*预编译数据*/
        List<String> precompiledValues = new ArrayList<>();
        precompiledValues.add(sysDictionary.getId());

        /*拼接SQL, 执行脚本*/
        String sqlTemplate = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER) ) {
            sqlTemplate = COMMON_SELECT + " FROM wd_sys_dict_content sdd" +
                    " where sdd.dict_type_id = " + SpecialSymbolConstants.QUESTION_MARK + " ";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            sqlTemplate = COMMON_SELECT_ORACLE + " FROM wd_sys_dict_content sdd" +
                    " where sdd.dict_type_id = " + SpecialSymbolConstants.QUESTION_MARK + " ";
        }
        String whereSQL = "";
        if (null != this.filterConf) {

            // 使用系统默认排序
            this.filterConf.setOrders(Collections.emptyList());
            whereSQL = getFilters(precompiledValues, filterConf);
        }
        sqlTemplate = sqlTemplate + whereSQL + DEFAULT_ORDER;
        return BaseFetchFormulaUtils.queryEntitiesBySQL(sqlTemplate, precompiledValues, SysDictionaryData.class);
    }

    /**
     * 列表结构的数据
     *
     * @param sysDictionary
     * @return
     * @throws Exception
     */
    public DictTreeInfo queryListDictTree(SysDictionary sysDictionary) throws Exception {

        this.setSysDictionary(sysDictionary);
        before();
        List<DictTree> dictTreeList = new ArrayList<>();
        this.tree.setTreeNodes(dictTreeList);
        if (null != this.dataList) {
            for (Map<String, String> stringObjectMap : this.dataList) {
                String id = stringObjectMap.get(sysDictionary.getRealValue());
                String name = stringObjectMap.get(sysDictionary.getShowValue());
                this.nodeLevelMap.put(id, String.valueOf(1));
                setDictTreeList(
                    dictTreeList,
                    sysDictionary,
                    stringObjectMap,
                    id,
                    null,
                    name,
                    String.valueOf(1)
                );
            }
        }
        after();
        return this;
    }

    /**
     * 查询绑定表的列表数据
     *
     * @param dictionary
     * @return
     */
    public List<SysDictionaryData> querySysDictionaryData(SysDictionary dictionary) {

        this.setSysDictionary(dictionary);
        /*预编译数据*/
        List<String> precompiledValues = new ArrayList<>();

        /*拼接SQL, 执行脚本*/
        String sqlTemplate = "select {0} from {1} where 1 = 1 {2}";
        String columns = this.assembleColumns();
        String whereSQL = "";
        sqlTemplate = MessageFormat.format(
            sqlTemplate,
            columns,
            bundleColumn(dictionary.getTableName()),
            whereSQL
        );
        List<SysDictionaryData> sysDictionaryData = Collections.emptyList();
        try {
            sysDictionaryData = BaseFetchFormulaUtils.queryEntitiesBySQL(sqlTemplate, precompiledValues, SysDictionaryData.class);
        } catch (Exception e) {
            throw new BusinessException("数据字典查询失败,检查字段是否有数值类型", e);
        }
        return sysDictionaryData;
    }

    public void clearLevelDataMap() {
        this.levelDataMap = null;
    }

    public void clearNodeLevelMap() {
        this.nodeLevelMap = null;
    }

    public void clearNodeChildDataMap() {
        this.nodeChildDataMap = null;
    }


    /**
     * 查询父子节点树
     *
     * @param sysDictionary 控件对象
     * @return
     */
    public DictTreeInfo queryParentChildrenTree(SysDictionary sysDictionary) throws Exception {

        this.setSysDictionary(sysDictionary);
        before();

        // id集合
        List<String> ids = new ArrayList<>();
        List<DictTree> dictTreeList = new ArrayList<>();
        this.tree.setTreeNodes(dictTreeList);
        if (null != this.dataList) {
            if (!SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
                this.dataList.forEach(cloumn -> ids.add(String.valueOf(cloumn.get("id"))));
            } else {
                this.dataList.forEach(cloumn -> ids.add(String.valueOf(cloumn.get(sysDictionary.getRealValue()))));
            }
            for (Map<String, String> stringObjectMap : this.dataList) {
                if (!existParentId(stringObjectMap.get(sysDictionary.getParentValue()), ids)) {
                    String id = stringObjectMap.get(sysDictionary.getRealValue());

                    // 系统的基础档案,获取真正的id(历史数据没有此标志,所有用非判断)
                    if (!SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
                        id = stringObjectMap.get("id");
                    }
                    String parentId = stringObjectMap.get(sysDictionary.getParentValue());
                    String name = stringObjectMap.get(sysDictionary.getShowValue());
                    this.nodeLevelMap.put(id, String.valueOf(1));
                    DictTree rootDictTree = setDictTreeList(
                        dictTreeList,
                        sysDictionary,
                        stringObjectMap,
                        id,
                        parentId,
                        name,
                        String.valueOf(1)
                    );
                    putChildNode(rootDictTree, sysDictionary, 1);
                    setNodeChildData(rootDictTree);
                }
            }
        }
        after();
        return this;
    }

    /**
     * 递归父子节点类型树
     *
     * @param parentNode    父节点
     * @param sysDictionary 字典对象
     * @param leve          层级
     */
    private void putChildNode(DictTree parentNode, SysDictionary sysDictionary, int leve) {

        List<DictTree> dictTreeList = new ArrayList<>();
        ++leve;
        for (Map<String, String> stringObjectMap : this.dataList) {
            String myPrentId = stringObjectMap.get(sysDictionary.getParentValue());
            String parentId = parentNode.getId();
            if (parentId.equals(myPrentId)) {
                String id = stringObjectMap.get(sysDictionary.getRealValue());

                // 系统的基础档案,获取真正的id(历史数据没有此标志,所有用非判断)
                if (!SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
                    id = stringObjectMap.get("id");
                }
                String name = stringObjectMap.get(sysDictionary.getShowValue());
                this.nodeLevelMap.put(id, String.valueOf(leve));
                DictTree dictTree = setDictTreeList(
                    dictTreeList,
                    sysDictionary,
                    stringObjectMap,
                    id,
                    parentId,
                    name,
                    String.valueOf(leve)
                );
                putChildNode(dictTree, sysDictionary, leve);
                setNodeChildData(dictTree);
            }
        }
        if (!dictTreeList.isEmpty()) {
            parentNode.setChildren(dictTreeList);
        }
    }

    private void setNodeChildData(DictTree rootDictTree) {

        List<Map<String, String>> childData = new ArrayList<>();
        List<DictTree> children = rootDictTree.getChildren();
        if (null != children && !children.isEmpty()) {
            for (DictTree child : children) {
                childData.add(child.getSelfMap());
            }
        }
        this.nodeChildDataMap.put(rootDictTree.getDictCde(), childData);
    }

    private DictTree setDictTreeList(List<DictTree> dictTreeList, SysDictionary sysDictionary,
            Map<String, String> stringObjectMap, String id, String parentId, String name, String level) {

        String dictCode = stringObjectMap.get(sysDictionary.getRealValue());
        DictTree dictTree = new DictTree();
        dictTree.setDrag(true);
        dictTree.setDrop(true);
        dictTree.setpId(parentId);
        dictTree.setType(TreeNodeType.DICT);
        dictTree.setId(id);
        dictTree.setName(name);
        dictTree.setDictId(id);
        dictTree.setDictTypeId(sysDictionary.getId());
        dictTree.setDictCde(dictCode);
        dictTree.setDictName(name);
        dictTree.setLevel(level);
        String[] propertyNames = null;
        String propertyName = sysDictionary.getPropertyName();

        List<Map<String, String>> levelDatas = this.levelDataMap.computeIfAbsent(level, l -> new ArrayList<>());
        Map<String, String> map = new HashMap<>();
        map.put("dictTypeId", sysDictionary.getId());
        map.put("dictCde", dictCode);
        map.put("dictName", name);
        map.put("parentId", parentId);
        map.put("level", level);

        // 设置ext属性
        if (StringUtil.isNotEmpty(this.sysDictionary.getPropertyCount())) {
            propertyNames = propertyName.split(COMMA);
            for (int i = 0; i < Integer.parseInt(this.sysDictionary.getPropertyCount()); i++) {
                if (StringUtil.isNotEmpty(stringObjectMap.get(propertyNames[i]))) {
                    String value = String.valueOf(stringObjectMap.get(propertyNames[i]));
                    Reflections.invokeSetter(dictTree, "ext" + (i + 1), value);
                    extFlip(i, propertyNames[i], dictTree, map);
                    map.put("ext" + (i + 1), value);
                } else {
                    extFlip(i, propertyNames[i], dictTree, map);
                    map.put("ext" + (i + 1), "");
                }
            }
        }
        dictTree.setSelfMap(map);
        dictTreeList.add(dictTree);
        levelDatas.add(map);
        return dictTree;
    }

    /**
     * 判断属性
     *
     * @param i            第几个属性位置
     * @param propertyName 属性名称
     * @param dictTree     数据对象
     * @param map          缓冲区
     */
    private void extFlip (int i, String propertyName, DictTree dictTree, Map<String, String> map) {

        switch (i) {
            case 0:
                map.put(propertyName, dictTree.getExt1());
                break;
            case 1:
                map.put(propertyName, dictTree.getExt2());
                break;
            case 2:
                map.put(propertyName, dictTree.getExt3());
                break;
            case 3:
                map.put(propertyName, dictTree.getExt4());
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否有父节点,没有父节点说明此节点是根节点
     *
     * @param pId 父id
     * @param ids 所有数据id集合
     * @return
     */
    private boolean existParentId(Object pId, List<String> ids) {

        if (StringUtil.isNotEmpty(pId)) {
            return ids.contains(pId.toString());
        }
        return false;
    }
    /**
     * 查询字典树
     *
     * @param sysDictionary 控件对象
     * @return
     * @throws Exception
     */
    public DictTreeInfo queryDictTree(SysDictionary sysDictionary) throws Exception {

        this.setSysDictionary(sysDictionary);
        before();
        if (null == this.dataList || this.dataList.isEmpty()) {
            return this;
        }

        List<DictTree> dictTreeList = new ArrayList<>();
        this.tree.setTreeNodes(dictTreeList);

        // 每级长度
        List<Integer> codeLengths = new ArrayList<>();

        // k-层级,v-层级数据
        Map<Integer, List<Map<String, String>>> levelMap = new HashMap<>();

        // 记录每级位数
        for (Map<String, String> stringObjectMap : this.dataList) {
            String code = stringObjectMap.get(sysDictionary.getRealValue());
            if (StringUtil.isEmpty(code)) {
                continue;
            }
            int levelLength = code.length();
            if (!codeLengths.contains(levelLength)) {
                codeLengths.add(levelLength);
                List<Map<String, String>> datas = new ArrayList<>();
                datas.add(stringObjectMap);
                levelMap.put(levelLength, datas);
            } else {
                List<Map<String, String>> datas = levelMap.get(levelLength);
                datas.add(stringObjectMap);
            }
        }

        // 层级排序(默认正序)
        Collections.sort(codeLengths);

        // 一级节点长度
        Integer levelLength = codeLengths.get(0);
        codeLengths.remove(0);

        // 循环一级
        List<Map<String, String>> datas = levelMap.get(levelLength);
        for (Map<String, String> stringObjectMap : datas) {
            String id = stringObjectMap.get(sysDictionary.getRealValue());
            String name = stringObjectMap.get(sysDictionary.getShowValue());
            DictTree rootDictTree = setDictTreeList(
                dictTreeList,
                sysDictionary,
                stringObjectMap,
                id,
                TreeNode.ROOT,
                name,
                String.valueOf(1)
            );
            this.nodeLevelMap.put(id, String.valueOf(1));

            // 拷贝新集合,每次递归都remove
            List<Integer> chlidCodeLengths = new ArrayList<>(codeLengths);
            setDictTreeChild(rootDictTree, levelMap, chlidCodeLengths, sysDictionary, 1);
            setNodeChildData(rootDictTree);
        }

        // 剩余数据不规则等数据,放到一级目录
        for (Integer codeLength : codeLengths) {
            List<Map<String, String>> lastDatas = levelMap.get(codeLength);
            if (!lastDatas.isEmpty()) {
                for (Map<String, String> stringObjectMap : lastDatas) {
                    String id = stringObjectMap.get(sysDictionary.getRealValue());
                    String name = stringObjectMap.get(sysDictionary.getShowValue());
                    DictTree rootDictTree = setDictTreeList(
                        dictTreeList,
                        sysDictionary,
                        stringObjectMap,
                        id,
                        TreeNode.ROOT,
                        name,
                        String.valueOf(1)
                    );
                    this.nodeLevelMap.put(id, String.valueOf(1));
                    this.nodeChildDataMap.put(rootDictTree.getDictCde(), new ArrayList<>());
                }
            }
        }
        after();
        return this;
    }

    private void before() throws Exception {

        this.dataList = getDictBefore().before(this.sysDictionary, this.filterConf);
    }

    /**
     * 获取过滤条件和排序
     *
     * @param precompiledValues 预编译值
     * @param filterConf
     * @return
     */
    public String getFilters(List<String> precompiledValues, ModelFilterConf filterConf) {

        StringBuffer conditionSQLBuffer = new StringBuffer();

        // 过滤条件
        List<ConditionsBean> conditions = filterConf.getConditions();
        String parameValue = "";
        for (ConditionsBean condition : conditions) {
            String value = condition.getValue();
            switch (condition.getType()) {
                case ConditionsBean.TYPE_VARCHAR:
                case ConditionsBean.TYPE_CELL:
                    parameValue = value;
                    break;
                case ConditionsBean.TYPE_FORMULA:

                    // 把需要任务详细的替换
                    value = replaceKeyword(value);
//                    parameValue = BaseFetchFormulaUtils.getAllFormula(value);
                    break;
                default:
                    conditionSQLBuffer.append(SpecialSymbolConstants.SPACE).append(condition.getOperator());
                    break;
            }
            if (StringUtil.isNotEmpty(parameValue)) {
                List<String> parameValues = Arrays.asList(parameValue.split(COMMA));
                conditionSQLBuffer.append(SpecialSymbolConstants.SPACE)
                                  .append(condition.getCondition())
                                  .append(SpecialSymbolConstants.LEFT_BRACKETS)
                                  .append(SpecialSymbolConstants.SPACE)
                                  .append(SpecialSymbolConstants.SYSTEM_COLUMN_SURROUND)
                                  .append(condition.getName())
                                  .append(SpecialSymbolConstants.SYSTEM_COLUMN_SURROUND);
                SQLDMLPretreatmentUtil.appendParame(
                    conditionSQLBuffer,
                    condition.getOperator(),
                    parameValue,
                    parameValues,
                    precompiledValues
                );
                conditionSQLBuffer.append(SpecialSymbolConstants.RIGHT_BRACKETS);
            }
        }
        String order = SQLDMLPretreatmentUtil.appendSQLOrder(
            null,
            filterConf.getOrders(),
            SpecialSymbolConstants.SYSTEM_COLUMN_SURROUND
        );
        conditionSQLBuffer.append(order);
        return conditionSQLBuffer.toString();
    }

    private String replaceKeyword(String value) {

        Set<String> keywords = new HashSet<>();
        keywords.add("SELECTORG(");
        keywords.add("DATATIME(");
        keywords.add("SELECTDEPARTMENT(");
        for (String keyword : keywords) {
            int index = value.indexOf(keyword);
            if (index >= 0) {
                StringBuilder stringBuilder = new StringBuilder(value);
                String reportTaskDetailedId = "";
                if (null!= detailedMap) {
                    if (value.indexOf("SELECTORG(") >= 0) {
                        reportTaskDetailedId = (String) detailedMap.get("org_id");
                    } else if (value.indexOf("DATATIME(") >= 0) {
                        reportTaskDetailedId = (String) detailedMap.get("data_time");
                    } else if (value.indexOf("SELECTDEPARTMENT(") >= 0) {
                        reportTaskDetailedId = (String) detailedMap.get("c_department");
                    }
                }
                stringBuilder.insert(index == 0 ? keyword.length() : index, "\"" + reportTaskDetailedId+ "\",");
                value = stringBuilder.toString();
            }
        }
        return value;
    }

    private void after() {

        List<WdSysDictLevel> levelTable = new ArrayList<>();
        AtomicInteger level = new AtomicInteger(1);
        this.levelDataMap.forEach((k, v) -> {
            WdSysDictLevel wdSysDictLevel = new WdSysDictLevel();
            wdSysDictLevel.setDictTypeId(this.sysDictionary.getId());
            wdSysDictLevel.setLevelName("层级" + level.get());
            wdSysDictLevel.setSequence(String.valueOf(level.getAndIncrement()));
            wdSysDictLevel.setCodeLevel(String.valueOf(this.levelDataMap.size()));
            Map<String, String> stringObjectMap = v.get(0);
            String o = stringObjectMap.get(this.getSysDictionary().getRealValue());
            if (null == o) {
                wdSysDictLevel.setDigit("0");
            } else {
                wdSysDictLevel.setDigit(String.valueOf(o.length()));
            }
            levelTable.add(wdSysDictLevel);
        });
        this.setLevelTables(levelTable);
    }

    private void setDictTreeChild(DictTree parentTreeNode, Map<Integer, List<Map<String, String>>> levelMap,
        List<Integer> codeLengths, SysDictionary sysDictionary, int level) {

        if (codeLengths.isEmpty()) {
            return;
        }
        List<Map<String, String>> deleteCodes = new ArrayList<>();
        List<DictTree> childs = new ArrayList<>();
        List<Map<String, String>> datas = levelMap.get(codeLengths.get(0));
        codeLengths.remove(0);
        ++level;
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> stringObjectMap = datas.get(i);
            String id = stringObjectMap.get(sysDictionary.getRealValue());
            String name = stringObjectMap.get(sysDictionary.getShowValue());
            if (id.startsWith(parentTreeNode.getId())) {
                DictTree treeNode = setDictTreeList(
                    childs,
                    sysDictionary,
                    stringObjectMap,
                    id,
                    TreeNode.ROOT,
                    name,
                    String.valueOf(level)
                );
                this.nodeLevelMap.put(id, String.valueOf(level));
                deleteCodes.add(stringObjectMap);

                // 拷贝新集合,每次递归都remove
                List<Integer> chlidCodeLengths = new ArrayList<>(codeLengths);
                setDictTreeChild(treeNode, levelMap, chlidCodeLengths, sysDictionary, level);
                setNodeChildData(treeNode);
            }
        }
        if (!childs.isEmpty()) {
            for (int index = 0; index < deleteCodes.size(); index++) {
                datas.remove(deleteCodes.get(index));
            }
            parentTreeNode.setChildren(childs);
        }
    }

    private String assembleColumns() {

        StringBuilder columns = new StringBuilder();
        columns.append(bundleColumn(this.sysDictionary.getShowValue()))
               .append(SQL_AS)
               .append(bundleColumn("dictName"));
        columns.append(COMMA)
               .append(bundleColumn(this.sysDictionary.getRealValue()))
               .append(SQL_AS)
               .append(bundleColumn("dictCde"));
        if (StringUtil.isNotEmpty(this.sysDictionary.getParentValue())) {
            columns.append(COMMA)
                   .append(bundleColumn(this.sysDictionary.getParentValue()))
                   .append(SQL_AS)
                   .append(bundleColumn("parentId"));
        }
        if (StringUtil.isNotEmpty(this.sysDictionary.getPropertyColumn())) {
            String[] propertyColumns = this.sysDictionary.getPropertyColumn().split(",");
            for (int i = 0; i < propertyColumns.length; i++) {
                String column = propertyColumns[i];
                columns.append(COMMA)
                       .append(bundleColumn(column))
                       .append(SQL_AS)
                       .append(bundleColumn("Ext" + (i + 1)));
            }
        }
        return columns.toString();
    }

    /**
     * 参照:sysDictionaryDataDao.queryDistinctBySelectDictId
     *
     * @param dictId    档案主表id
     * @param levelName 层级名称
     * @return
     * @throws Exception
     */
    public List<SysDictionaryData> queryDistinctBySelectDictId(String dictId, String levelName) throws Exception {

        /*预编译数据*/
        List<String> precompiledValues = new ArrayList<>();
        precompiledValues.add(dictId);
        precompiledValues.add(dictId);
        precompiledValues.add(levelName);

        /*拼接SQL, 执行脚本*/
        String sqlTemplate = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER)){
            sqlTemplate = COMMON_SELECT + ", dict.property_count as propertyCount," +
                    " dict.property_name as propertyName FROM " +
                    "wd_sys_dict_content sdd, wd_sys_dict_type dict " +
                    " where sdd.dict_type_id = dict.id and dict.id = ? and sdd.c_level = (" +
                    "select wdl.c_sequence from wd_sys_dict_level wdl" +
                    " where wdl.dict_type_id = ? and wdl.level_name = ?)";
        } else if (
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            sqlTemplate = COMMON_SELECT_ORACLE + ", dict.property_count as \"propertyCount\"," +
                    " dict.property_name as \"propertyName\" FROM " +
                    "wd_sys_dict_content sdd, wd_sys_dict_type dict " +
                    " where sdd.dict_type_id = dict.id and dict.id = ? and sdd.c_level = (" +
                    "select wdl.c_sequence from wd_sys_dict_level wdl" +
                    " where wdl.dict_type_id = ? and wdl.level_name = ?)";
        }

        String whereSQL = "";
        if (null != this.filterConf) {

            // 使用系统默认排序
            this.filterConf.setOrders(Collections.emptyList());
            whereSQL = getFilters(precompiledValues, filterConf);
        }
        sqlTemplate = sqlTemplate + whereSQL + DEFAULT_ORDER;
        return BaseFetchFormulaUtils.queryEntitiesBySQL(
            this.entityManager,
            sqlTemplate,
            precompiledValues,
            SysDictionaryData.class
        );
    }

    /**
     * 参照：sysDictionaryDataDao.querySysDictionaryDataByDictCdeAndDictTypeId
     *
     * @param parentDictCde 父节点代码
     * @param dictId        主表id
     * @return
     * @throws Exception
     */
    public SysDictionaryData querySysDictionaryDataByDictCdeAndDictTypeId(String parentDictCde, String dictId)
            throws Exception {

        /*预编译数据*/
        List<String> precompiledValues = new ArrayList<>();
        precompiledValues.add(dictId);
        precompiledValues.add(parentDictCde);

        /*拼接SQL, 执行脚本*/
        String sqlTemplate = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL) ||
                DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER) ) {
            sqlTemplate = COMMON_SELECT + " FROM wd_sys_dict_content sdd" +
                    " where dict_type_id = ? and dict_cde = ?";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            sqlTemplate = COMMON_SELECT_ORACLE + " FROM wd_sys_dict_content sdd" +
                    " where dict_type_id = ? and dict_cde = ?";
        }
        String whereSQL = "";
        sqlTemplate = sqlTemplate + whereSQL;
        return BaseFetchFormulaUtils.queryEntityBySQL(
            this.entityManager,
            sqlTemplate,
            precompiledValues,
            SysDictionaryData.class
        );
    }

    /**
     * 参照：sysDictionaryDataDao.queryCascadeDistinctByDictId
     *
     * @param dictId        档案主表id
     * @param parentDictCde 父cde
     * @return
     * @throws Exception
     */
    public List<SysDictionaryData> queryCascadeDistinctByDictId(String dictId, String parentDictCde) throws Exception {

        /*预编译数据*/
        List<String> precompiledValues = new ArrayList<>();
        precompiledValues.add(dictId);
        precompiledValues.add(parentDictCde);

        /*拼接SQL, 执行脚本*/
        String sqlTemplate = "";
        if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_MYSQL) ||
                DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER) ) {
            sqlTemplate = COMMON_SELECT + ", dict.property_count as propertyCount," +
                    " dict.property_name as propertyName FROM " +
                    "wd_sys_dict_content sdd, wd_sys_dict_type dict " +
                    "  where sdd.dict_type_id = dict.id " +
                    " and sdd.parent_id = (select sysdd.id from wd_sys_dict_type " +
                    "sysd, wd_sys_dict_content sysdd where sysd.id = sysdd.dict_type_id and sysd.id = ? " +
                    "and sysdd.dict_cde = ?)";
        } else if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_ORACLE) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_DM8) ||
            DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_H2)) {
            sqlTemplate = COMMON_SELECT_ORACLE + ", dict.property_count as \"propertyCount\"," +
                    " dict.property_name as \"propertyName\" FROM " +
                    "wd_sys_dict_content sdd, wd_sys_dict_type dict " +
                    "  where sdd.dict_type_id = dict.id " +
                    " and sdd.parent_id = (select sysdd.id from wd_sys_dict_type " +
                    "sysd, wd_sys_dict_content sysdd where sysd.id = sysdd.dict_type_id and sysd.id = ? " +
                    "and sysdd.dict_cde = ?)";
        }
        String whereSQL = "";
        if (null != this.filterConf) {

            // 使用系统默认排序
            this.filterConf.setOrders(Collections.emptyList());
            whereSQL = getFilters(precompiledValues, filterConf);
        }
        sqlTemplate = sqlTemplate + whereSQL + DEFAULT_ORDER;
        return BaseFetchFormulaUtils.queryEntitiesBySQL(
            this.entityManager,
            sqlTemplate,
            precompiledValues,
            SysDictionaryData.class
        );
    }

    /**
     * 数据提供接口,一般用于:外部已经有数据时
     */
    public interface DictBefore extends Serializable {

        /**
         * 处理逻辑前,查询数据
         *
         * @param dictionary 字典实体
         * @param filterConf 过滤对象
         * @return
         * @throws Exception
         */
        List<Map<String, String>> before(SysDictionary dictionary, ModelFilterConf filterConf) throws Exception;
    }

    public final class DefaultDictBefore implements DictBefore {

        private static final long serialVersionUID = -3258105298624196693L;

        @Override
        public List<Map<String, String>> before(SysDictionary dictionary, ModelFilterConf filterConf) throws Exception {

            Objects.requireNonNull(dictionary, "字典对象不能空");

            /*必须项判断*/
            if (StringUtil.isEmpty(dictionary.getTableName())) {
                throw new BusinessException("请选择表名");
            }
            if (StringUtil.isEmpty(dictionary.getShowValue())) {
                throw new BusinessException("请选择显示字段");
            }
            if (StringUtil.isEmpty(dictionary.getRealValue())) {
                throw new BusinessException("请选择实际值字段");
            }
            if (SysDictionary.TYPE1.equals(dictionary.getCType()) &&
                SysDictionary.RELY_TYPE1.equals(dictionary.getRelyType()) &&
                StringUtil.isEmpty(dictionary.getParentValue())) {
                throw new BusinessException("请选择父节点字段");
            }

            /*预编译数据*/
            List<String> precompiledValues = new ArrayList<>();

            /*拼接SQL, 执行脚本*/
            String sqlTemplate = "select distinct {0} from {1} where 1 = 1 {2}";
            String columns = dictionary.assembleColumns();
            String whereSQL = "";
            if (null != filterConf) {
                whereSQL = getFilters(precompiledValues, filterConf);
            }
            sqlTemplate = MessageFormat.format(
                sqlTemplate,
                columns,
                bundleColumn(dictionary.getTableName()),
                whereSQL
            );
            if (null != filterConf &&
                filterConf.isLimit()) {
                if (DataSourceTool.FORMALDBTYPE.equals(DataSourceTool.DBTYPE_SQLSERVER)) {
                    sqlTemplate = sqlTemplate.substring(getAfterSelectInsertPoint(sqlTemplate));
                }
                sqlTemplate = MessageFormat.format(SQL_PAGE_TEMPLATE.get(DataSourceTool.FORMALDBTYPE.toUpperCase()), sqlTemplate, 0, 100);
            }
            return BaseFetchFormulaUtils.queryValueMapBySQL(sqlTemplate, precompiledValues);
        }
    }

    /**
     * 获取 树形结构数据
     *
     * @return tree 树形结构数据
     */
    public ResponseDictJson getTree() {
        return this.tree;
    }

    /**
     * 设置 树形结构数据
     *
     * @param tree 树形结构数据
     */
    public void setTree(ResponseDictJson tree) {
        this.tree = tree;
    }

    /**
     * 获取 每层的列表
     *
     * @return levelDataMap 每层的列表
     */
    public Map<String, List<Map<String, String>>> getLevelDataMap() {
        return this.levelDataMap;
    }

    /**
     * 设置 每层的列表
     *
     * @param levelDataMap 每层的列表
     */
    public void setLevelDataMap(Map<String, List<Map<String, String>>> levelDataMap) {
        this.levelDataMap = levelDataMap;
    }

    /**
     * 获取 节点的层级
     *
     * @return nodeLevelMap 节点的层级
     */
    public Map<String, String> getNodeLevelMap() {
        return this.nodeLevelMap;
    }

    /**
     * 设置 节点的层级
     *
     * @param nodeLevelMap 节点的层级
     */
    public void setNodeLevelMap(Map<String, String> nodeLevelMap) {
        this.nodeLevelMap = nodeLevelMap;
    }

    /**
     * 获取 节点的子集列表
     *
     * @return nodeChildDataMap 节点的子集列表
     */
    public Map<String, List<Map<String, String>>> getNodeChildDataMap() {
        return this.nodeChildDataMap;
    }

    /**
     * 设置 节点的子集列表
     *
     * @param nodeChildDataMap 节点的子集列表
     */
    public void setNodeChildData(Map<String, List<Map<String, String>>> nodeChildDataMap) {
        this.nodeChildDataMap = nodeChildDataMap;
    }

    /**
     * 获取 层级信息
     *
     * @return levelTables 层级信息
     */
    public List<WdSysDictLevel> getLevelTables() {
        return this.levelTables;
    }

    /**
     * 设置 层级信息
     *
     * @param levelTables 层级信息
     */
    public void setLevelTables(List<WdSysDictLevel> levelTables) {
        this.levelTables = levelTables;
    }

    /**
     * 获取
     *
     * @return sysDictionary
     */
    public SysDictionary getSysDictionary() {
        return this.sysDictionary;
    }

    /**
     * 设置
     *
     * @param sysDictionary
     */
    public void setSysDictionary(SysDictionary sysDictionary) {
        this.sysDictionary = sysDictionary;
    }

    /**
     * 获取 过滤条件配置
     *
     * @return filterConf 过滤条件配置
     */
    public ModelFilterConf getFilterConf() {
        return this.filterConf;
    }

    /**
     * 设置 过滤条件配置
     *
     * @param filterConf 过滤条件配置
     */
    public void setFilterConf(ModelFilterConf filterConf) {
        this.filterConf = filterConf;
    }

    /**
     * 获取 事务管理器只做传递用不执行打开关闭操作
     *
     * @return entityManager 事务管理器只做传递用不执行打开关闭操作
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * 设置 事务管理器只做传递用不执行打开关闭操作
     *
     * @param entityManager 事务管理器只做传递用不执行打开关闭操作
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DictBefore getDictBefore() {

        if (null == this.dictBefore) {

            // 默认查询数据接口
            return defaultDictBefore;
        }
        return this.dictBefore;
    }

    public DictTreeInfo setDictBefore(DictBefore dictBefore) {
        this.dictBefore = dictBefore;
        return this;
    }

    public DictTreeInfo setDetailedMap(Map<String, Object> detailedMap) {
        this.detailedMap = detailedMap;
        return this;
    }
}
