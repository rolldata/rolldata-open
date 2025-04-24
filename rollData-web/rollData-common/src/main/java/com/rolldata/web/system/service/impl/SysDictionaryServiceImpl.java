package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.dao.SysDictionaryDao;
import com.rolldata.web.system.dao.SysDictionaryDataDao;
import com.rolldata.web.system.dao.WdSysDictLevelDao;
import com.rolldata.web.system.entity.SysDictionary;
import com.rolldata.web.system.entity.SysDictionaryData;
import com.rolldata.web.system.entity.WdSysDictLevel;
import com.rolldata.web.system.pojo.DictTree;
import com.rolldata.web.system.pojo.DictTreeInfo;
import com.rolldata.web.system.pojo.DictTypeTree;
import com.rolldata.web.system.pojo.ModelFilterConf;
import com.rolldata.web.system.pojo.RequestDictJson;
import com.rolldata.web.system.pojo.RequestDictTypeJson;
import com.rolldata.web.system.pojo.ResponseDictJson;
import com.rolldata.web.system.pojo.ResponseDictTypeJson;
import com.rolldata.web.system.pojo.ResponseDictTypeTreeJson;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.SysDictionaryService;
import com.rolldata.web.system.util.BaseFetchFormulaUtils;
import com.rolldata.web.system.util.SpecialSymbolConstants;
import com.rolldata.web.system.util.UserUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.rolldata.core.util.ObjectUtils.assertNotNull;
import static com.rolldata.core.util.ObjectUtils.judgeNotNull;


/**
 * 数据字典表业务处理层
 * @author shenshilong
 * @createDate 2018-6-11
 */

@Service("sysDictionaryService")
@Transactional
public class SysDictionaryServiceImpl implements SysDictionaryService {

    public static final String ERROR_MESSAGE = "基础档案不存在";

    private final Logger log = LogManager.getLogger(SysDictionaryServiceImpl.class);
    
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    
    @Autowired
    private SysDictionaryDataDao sysDictionaryDataDao;
    
    @Autowired
    private WdSysDictLevelDao wdSysDictLevelDao;

    private static final String treeNodes = "treeNodes";

    private EntityManagerFactory emf;

    // 使用这个标记来注入EntityManagerFactory
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * 保存
     * @param requestDictTypeJson
     * @throws Exception
     */
    @Override
    public ResponseDictTypeJson save(RequestDictTypeJson requestDictTypeJson) throws Exception {
    
        List<WdSysDictLevel> levelTables = requestDictTypeJson.getLevelTable();
        List<String> propertyColumns = requestDictTypeJson.getPropertyColumns();
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setDictTypeCde(requestDictTypeJson.getDictTypeCde());
        sysDictionary.setDictTypeName(requestDictTypeJson.getDictTypeName());
        sysDictionary.setShowType(SysDictionary.SHOWTIME_TREE);
        sysDictionary.setCodeLevel(requestDictTypeJson.getCodeLevel());
        sysDictionary.setPropertyName(null != requestDictTypeJson.getPropertyTable() ? StringUtils.join(requestDictTypeJson.getPropertyTable(), SpecialSymbolConstants.COMMA) : "");
        sysDictionary.setPropertyCount(requestDictTypeJson.getPropertyCount());
        sysDictionary.setDictType(SysDictionary.DICTTYPE_ORD);

        // 设置1数据档案 信息
        sysDictionary.setCType(requestDictTypeJson.getCType());
        sysDictionary.setRelyType(requestDictTypeJson.getRelyType());
        sysDictionary.setTableName(requestDictTypeJson.getTableName());
        sysDictionary.setShowValue(requestDictTypeJson.getShowValue());
        sysDictionary.setRealValue(requestDictTypeJson.getRealValue());
        sysDictionary.setParentValue(requestDictTypeJson.getParentValue());
        sysDictionary.setPropertyColumn(null != propertyColumns ? StringUtils.join(propertyColumns, SpecialSymbolConstants.COMMA) : "");

        sysDictionary.setCreateUser(UserUtils.getUser().getId());
        sysDictionary.setCreateTime(new Date());
        sysDictionary.setUpdateUser(UserUtils.getUser().getId());
        sysDictionary.setUpdateTime(new Date());
        this.sysDictionaryDao.save(sysDictionary);

        // 新建基础档案层级表
        if (null != levelTables) {
            for (WdSysDictLevel levelTable : levelTables) {
                WdSysDictLevel wdSysDictLevel = new WdSysDictLevel();
                wdSysDictLevel.setDictTypeId(sysDictionary.getId());
                wdSysDictLevel.setSequence(levelTable.getSequence());
                wdSysDictLevel.setDigit(levelTable.getDigit());
                wdSysDictLevel.setLevelName(levelTable.getLevelName());
                wdSysDictLevel.setCreateTime(new Date());
                wdSysDictLevel.setCreateUser(UserUtils.getUser().getId());
                wdSysDictLevel.setUpdateTime(new Date());
                wdSysDictLevel.setUpdateUser(UserUtils.getUser().getId());
                this.wdSysDictLevelDao.saveAndFlush(wdSysDictLevel);
            }
        }
        return createResponseData(sysDictionary.getId(), sysDictionary.getDictTypeCde(), sysDictionary.getDictTypeName(), sysDictionary.getShowType(), sysDictionary.getPropertyCount(), sysDictionary.getPropertyName());
    }
    
    /**
     * 查询档案目录表中代码是否重复
     */
    @Override
    public boolean isExistByDictTypeCde(String dictTypeCde) throws Exception{
        SysDictionary sysDictionary = this.sysDictionaryDao.getByDictTypeCde(dictTypeCde);
        return judgeNotNull(sysDictionary);
    }
    
    /**
     * 查询档案目录表中代码是否重复
     */
    @Override
    public boolean isExistByDictTypeName(String dictTypeName) throws Exception{
        SysDictionary sysDictionary = this.sysDictionaryDao.getByDictTypeName(dictTypeName);
        return judgeNotNull(sysDictionary);
    }
    
    /**
     * 更新
     *
     * @param ajaxJson
     * @param rDictTypeJson
     * @throws Exception
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
    public void update (AjaxJson ajaxJson, RequestDictTypeJson rDictTypeJson) throws Exception {
    
        List<WdSysDictLevel> wdSysDictLevels = new ArrayList<>();
        String propertyName = null != rDictTypeJson.getPropertyTable() ? StringUtils.join(rDictTypeJson.getPropertyTable(), SpecialSymbolConstants.COMMA) : "";
        String propertyColumn = null != rDictTypeJson.getPropertyColumns()
            ? StringUtils.join(rDictTypeJson.getPropertyColumns(), SpecialSymbolConstants.COMMA) : "";

        /*类型是0基础档案的执行(由于有历史数据,判断用1稳妥)*/
        if (!SysDictionary.TYPE1.equals(rDictTypeJson.getCType())) {
            List<WdSysDictLevel> oldEntitys = this.wdSysDictLevelDao.queryWdSysDictLevelsByDictTypeId(rDictTypeJson.getDictTypeId());
            for (int i = 0; i < oldEntitys.size(); i++) {

                //查询原数据, 比较编码层级数大小, 执行1次,并删除多余层级
                if (Integer.parseInt(oldEntitys.get(i).getCodeLevel()) > Integer.parseInt(rDictTypeJson.getCodeLevel()) && i == 0) {
                    int oldFl = Integer.parseInt(oldEntitys.get(0).getCodeLevel());
                    int nowFl = Integer.parseInt(rDictTypeJson.getCodeLevel());
                    for (; nowFl < oldFl; oldFl--) {
                        this.log.info("删除基础档案[ " + rDictTypeJson.getDictTypeCde() + " ]层级" + oldFl);
                        this.sysDictionaryDataDao.deleteUnnecessaryDigit(rDictTypeJson.getDictTypeId(), String.valueOf(oldFl));
                    }
                }
                if (rDictTypeJson.getLevelTable().size() > i && !oldEntitys.get(i).getDigit().equals(rDictTypeJson.getLevelTable().get(i).getDigit())) {
                    if (Integer.parseInt(oldEntitys.get(i).getDigit()) > Integer.parseInt(rDictTypeJson.getLevelTable().get(i).getDigit())) {
                        ajaxJson.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("common.sys.update.error") + SpecialSymbolConstants.COMMA + MessageUtils.getMessage("report.sequence") + (i + 1) + MessageUtils.getMessage("report.dict.digit.error"));
                        return;
                    } else {
                        wdSysDictLevels.add(rDictTypeJson.getLevelTable().get(i));
                    }
                }
            }
        }

        /*修改基础档案主表*/
        this.sysDictionaryDao.update(
            rDictTypeJson.getDictTypeId(),
            rDictTypeJson.getDictTypeCde(),
            rDictTypeJson.getDictTypeName(),
            rDictTypeJson.getCodeLevel(),
            rDictTypeJson.getPropertyCount(),
            propertyName,
            rDictTypeJson.getCType(),
            rDictTypeJson.getRelyType(),
            rDictTypeJson.getTableName(),
            rDictTypeJson.getShowValue(),
            rDictTypeJson.getRealValue(),
            rDictTypeJson.getParentValue(),
            propertyColumn,
            UserUtils.getUser().getId(),
            new Date()
        );
        this.wdSysDictLevelDao.deleteAllByDictTypeId(rDictTypeJson.getDictTypeId());
        List<WdSysDictLevel> levelTables = rDictTypeJson.getLevelTable();
        
        // 新建基础档案层级表
        if (null != levelTables) {
            for (WdSysDictLevel levelTable : levelTables) {
                WdSysDictLevel wdSysDictLevel = new WdSysDictLevel();
                wdSysDictLevel.setDictTypeId(rDictTypeJson.getDictTypeId());
                wdSysDictLevel.setSequence(levelTable.getSequence());
                wdSysDictLevel.setDigit(levelTable.getDigit());
                wdSysDictLevel.setLevelName(levelTable.getLevelName());
                wdSysDictLevel.setCreateTime(new Date());
                wdSysDictLevel.setCreateUser(UserUtils.getUser().getId());
                wdSysDictLevel.setUpdateTime(new Date());
                wdSysDictLevel.setUpdateUser(UserUtils.getUser().getId());
                this.wdSysDictLevelDao.saveAndFlush(wdSysDictLevel);
            }
        }
    
        // 把位数改大
//        if (!wdSysDictLevels.isEmpty()) {
//            for (WdSysDictLevel wdSysDictLevel : wdSysDictLevels) {
//                List<SysDictionaryData> sysDictionaryDataList = this.sysDictionaryDataDao.querySysDictionaryDataByLevel(rDictTypeJson.getDictTypeId(), wdSysDictLevel.getSequence());
//                for (SysDictionaryData sysDictionaryData : sysDictionaryDataList) {
//                    this.sysDictionaryDataDao.updateDictCde(sysDictionaryData.getId(), StringUtil.frontCompWithZore(Integer.parseInt(sysDictionaryData.getDictCde()), wdSysDictLevel.getDigit()));
//                }
//            }
//        }
        ajaxJson.setSuccessAndMsg(Boolean.TRUE, MessageUtils.getMessage("common.sys.update.success"));
        ResponseDictTypeJson responseData = createResponseData(
            rDictTypeJson.getDictTypeId(),
            rDictTypeJson.getDictTypeCde(),
            rDictTypeJson.getDictTypeName(),
            SysDictionary.SHOWTIME_TREE,
            rDictTypeJson.getPropertyCount(),
            propertyName
        );
        ajaxJson.setObj(responseData);
    }

    /**
     * 删除
     *
     * @param ids
     * @throws Exception
     */
    @Override
    public void delete(List<String> ids) throws Exception {
    
        for (String id : ids) {
            this.sysDictionaryDao.deleteSysDictionaryById(id);
            this.sysDictionaryDataDao.delAllByDictTypeId(id);
            this.wdSysDictLevelDao.deleteAllByDictTypeId(id);
        }
    }

    /**
     * 查询全部
     *
     * @throws Exception
     * @param dictTypeJson
     */
    public List<SysDictionary> getAlList(RequestDictTypeJson dictTypeJson) throws Exception {
//放开权限控制20210826
//        return this.sysDictionaryDao.querySysDictionaries(UserUtils.getUserIdPermissionList());
        if(null!=dictTypeJson && StringUtil.isNotEmpty(dictTypeJson.getCType())){
            return this.sysDictionaryDao.querySysDictionariesByCType(dictTypeJson.getCType());
        }else{
            return this.sysDictionaryDao.querySysDictionaries();
        }
    }

    /**
     * 拼接新增，编辑返回值对象
     * @param dicId
     * @param dictTypeCde
     * @param dictTypeName
     * @param showType
     * @param propertyCount
     * @param propertyName
     * @return
     * @throws Exception
     * @createDate 2018-8-04
     */
    private ResponseDictTypeJson createResponseData(String dicId, String dictTypeCde, String dictTypeName,
        String showType, String propertyCount, String propertyName) throws Exception{
        
        ResponseDictTypeJson responseDictTypeJson = new ResponseDictTypeJson();
        List<DictTypeTree> list = new ArrayList<>();
        DictTypeTree dictTypeTree = new DictTypeTree();
        dictTypeTree.setId(dicId);
        dictTypeTree.setpId(TreeNode.ROOT);
        dictTypeTree.setName(dictTypeName);
        dictTypeTree.setType(TreeNodeType.FOLDER);
        dictTypeTree.setChecked(true);
        dictTypeTree.setNocheck(false);
        dictTypeTree.setDrag(false);
        dictTypeTree.setCopy(false);
        dictTypeTree.setDrop(false);
        dictTypeTree.setDictTypeId(dicId);
        dictTypeTree.setDictTypeCde(dictTypeCde);
        dictTypeTree.setShowType(showType);
        dictTypeTree.setDictTypeName(dictTypeName);
        dictTypeTree.setPropertyCount(propertyCount);
        dictTypeTree.setPropertyName(propertyName.split(SpecialSymbolConstants.COMMA));
        list.add(dictTypeTree);
        responseDictTypeJson.setTreeNodes(list);
        return responseDictTypeJson;
    }
    
    /**
     * 拼接目录管理树返回值对象
     * @return
     * @throws Exception
     * @createDate 2018-8-04
     * @param dictTypeJson
     */
    @Override
    public ResponseDictTypeTreeJson createManageTree(RequestDictTypeJson dictTypeJson) throws Exception{
        ResponseDictTypeTreeJson responseDictTypeJson = new ResponseDictTypeTreeJson();
        List<DictTypeTree> dictTypeTreeslist = new ArrayList<>();
        List<SysDictionary> list = this.getAlList(dictTypeJson);
        Map<String, List<WdSysDictLevel>> levelMap = this.setUpDictLevelInfo();
        for (SysDictionary sysDictionary : list) {
            DictTypeTree dictTypeTree = new DictTypeTree();
            dictTypeTree.setId(sysDictionary.getId());
            dictTypeTree.setpId(TreeNode.ROOT);
            dictTypeTree.setName(sysDictionary.getDictTypeName());
            dictTypeTree.setDictTypeId(sysDictionary.getId());
            dictTypeTree.setDictTypeCde(sysDictionary.getDictTypeCde());
            dictTypeTree.setShowType(sysDictionary.getShowType());
            dictTypeTree.setDictTypeName(sysDictionary.getDictTypeName());
            dictTypeTree.setPropertyCount(sysDictionary.getPropertyCount());
            if (StringUtil.isNotEmpty(sysDictionary.getPropertyName())) {
                dictTypeTree.setPropertyName(sysDictionary.getPropertyName().split(SpecialSymbolConstants.COMMA));
            } else {
                dictTypeTree.setPropertyName(new String[]{});
            }

            //挂载层级信息
            dictTypeTree.setLevelTable(levelMap.get(sysDictionary.getId()));

            // 历史数据没这个字段
            String cType = sysDictionary.getCType();
            dictTypeTree.setCType(StringUtil.isNotEmpty(cType) ? cType : SysDictionary.TYPE0);
            dictTypeTreeslist.add(dictTypeTree);
        }
        responseDictTypeJson.setTreeNodes(dictTypeTreeslist);
        responseDictTypeJson.setCheck(false);
        responseDictTypeJson.setCopy(false);
        responseDictTypeJson.setDrag(false);
        responseDictTypeJson.setEdit(true);
        return responseDictTypeJson;
    }
    
    @Override
    public boolean isExistByDictTypeCde(String dictTypeCde, String dictTypeId) throws Exception {
        SysDictionary sysDictionary = this.sysDictionaryDao.getByDictTypeCde(dictTypeCde,dictTypeId);
        return judgeNotNull(sysDictionary);
    }

    @Override
    public boolean isExistByDictTypeName(String dictTypeName, String dictTypeId) throws Exception {
        SysDictionary sysDictionary = this.sysDictionaryDao.getByDictTypeName(dictTypeName,dictTypeId);
        return judgeNotNull(sysDictionary);
    }

    /**
     * 获取数据内容表类型数据
     *
     * @param dictTypeId
     * @param getpId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDictJson getDictDataList(String dictTypeId, String getpId) throws Exception {

        SysDictionary sysDictionary = this.sysDictionaryDao.querySysDictionaryById(dictTypeId);
        assertNotNull(sysDictionary, ERROR_MESSAGE);
        ResponseDictJson responseDictJson = new ResponseDictJson();

        /*类型判断*/
        if (SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
            DictTreeInfo dictTreeInfo = new DictTreeInfo();
            dictTreeInfo.getTree(sysDictionary);
            responseDictJson.setTreeNodes(dictTreeInfo.getTree().getTreeNodes());
        } else {
            List<SysDictionaryData> dictionaryDataList = this.sysDictionaryDataDao.findByDictTypeId(dictTypeId);
            putDictTree(responseDictJson, dictionaryDataList);
        }
        return responseDictJson;
    }

    /**
     * 基础档案数据(表单上报端调用,其他地方还是原来的方法)
     *
     * @param dictTypeJson 前台对象
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDictJson getDictDataList(RequestDictTypeJson dictTypeJson) throws Exception {

        SysDictionary sysDictionary = this.sysDictionaryDao.querySysDictionaryById(dictTypeJson.getDictTypeId());
        assertNotNull(sysDictionary, ERROR_MESSAGE);
        ResponseDictJson responseDictJson = new ResponseDictJson();
        DictTreeInfo dictTreeInfo = new DictTreeInfo();

        // 查询任务详细
        List<Map<String, Object>> taskDetails = BaseFetchFormulaUtils.queryValueMapBySQL(
            "select org_id, c_department, fill_user, data_time, version_num, ascription_user from "
                + "wd_report_task_detailed t where t.id=?",
            Collections.singletonList(dictTypeJson.getTaskDetailId())
        );
        if (!taskDetails.isEmpty()) {
            dictTreeInfo.setDetailedMap(taskDetails.get(0));
        }
        dictTreeInfo.setFilterConf(dictTypeJson.getFilterConf());

        /*类型判断*/
        if (SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
            dictTreeInfo.getTree(sysDictionary);
            responseDictJson.setTreeNodes(dictTreeInfo.getTree().getTreeNodes());
        } else {
            List<SysDictionaryData> dictionaryDataList = dictTreeInfo.getSystemSysDictionaryData(sysDictionary);
            putDictTree(responseDictJson, dictionaryDataList);
        }
        return responseDictJson;
    }

    /**
     * 设置字典树JSON
     *
     * @param responseDictJson
     * @param dictionaryDataList
     */
    private void putDictTree(ResponseDictJson responseDictJson, List<SysDictionaryData> dictionaryDataList) {

        responseDictJson.setEdit(true);
        responseDictJson.setDrag(true);
        List<DictTree> dictTreeList = new ArrayList<>();
        for (SysDictionaryData  sysDictionaryData : dictionaryDataList) {

            //层级是1
            if ("1".equals(sysDictionaryData.getLevel())) {
                DictTree dictTree = new DictTree();
                dictTree.setDrag(true);
                dictTree.setDrop(true);
                dictTree.setpId(sysDictionaryData.getParentId());
                dictTree.setType(TreeNodeType.DICT);
                dictTree.setId(sysDictionaryData.getId());
                dictTree.setName(sysDictionaryData.getDictName());
                dictTree.setDictId(sysDictionaryData.getId());
                dictTree.setDictTypeId(sysDictionaryData.getDictTypeId());
                dictTree.setDictCde(sysDictionaryData.getDictCde());
                dictTree.setDictName(sysDictionaryData.getDictName());
                dictTree.setLevel(sysDictionaryData.getLevel());
                dictTree.setExt1(sysDictionaryData.getExt1());
                dictTree.setExt2(sysDictionaryData.getExt2());
                dictTree.setExt3(sysDictionaryData.getExt3());
                dictTree.setExt4(sysDictionaryData.getExt4());
                dictTree.setExt5(sysDictionaryData.getExt5());
                dictTree.setExt6(sysDictionaryData.getExt6());
                dictTree.setExt7(sysDictionaryData.getExt7());
                dictTree.setExt8(sysDictionaryData.getExt8());
                dictTree.setExt9(sysDictionaryData.getExt9());
                dictTree.setExt10(sysDictionaryData.getExt10());
                dictTree.setExt11(sysDictionaryData.getExt11());
                dictTree.setExt12(sysDictionaryData.getExt12());
                dictTree.setExt13(sysDictionaryData.getExt13());
                dictTree.setExt14(sysDictionaryData.getExt14());
                dictTree.setExt15(sysDictionaryData.getExt15());
                dictTree.setExt16(sysDictionaryData.getExt16());
                dictTree.setExt17(sysDictionaryData.getExt17());
                dictTree.setExt18(sysDictionaryData.getExt18());
                dictTree.setExt19(sysDictionaryData.getExt19());
                dictTree.setExt20(sysDictionaryData.getExt20());
                dictTreeList.add(dictTree);
                dictDataListChild(dictTree, dictionaryDataList);
            }
        }
        responseDictJson.setTreeNodes(dictTreeList);
    }

    /**
     * 设置基础档案数据子节点
     * @param dictTree 父对象
     * @param dictionaryDataList 全部数据集
     */
    private void dictDataListChild (DictTree dictTree, List<SysDictionaryData> dictionaryDataList) {

        List<DictTree> childDictTree = new ArrayList<>();
        for (SysDictionaryData sysDictionaryData : dictionaryDataList) {
            if (dictTree.getId().equals(sysDictionaryData.getParentId())) {
                DictTree dictTreeChlid = new DictTree();
                dictTreeChlid.setDrag(true);
                dictTreeChlid.setDrop(true);
                dictTreeChlid.setpId(sysDictionaryData.getParentId());
                dictTreeChlid.setType(TreeNodeType.DICT);
                dictTreeChlid.setId(sysDictionaryData.getId());
                dictTreeChlid.setName(sysDictionaryData.getDictName());
                dictTreeChlid.setDictId(sysDictionaryData.getId());
                dictTreeChlid.setDictTypeId(sysDictionaryData.getDictTypeId());
                dictTreeChlid.setDictCde(sysDictionaryData.getDictCde());
                dictTreeChlid.setDictName(sysDictionaryData.getDictName());
                dictTreeChlid.setLevel(sysDictionaryData.getLevel());
                dictTreeChlid.setExt1(sysDictionaryData.getExt1());
                dictTreeChlid.setExt2(sysDictionaryData.getExt2());
                dictTreeChlid.setExt3(sysDictionaryData.getExt3());
                dictTreeChlid.setExt4(sysDictionaryData.getExt4());
                dictTreeChlid.setExt5(sysDictionaryData.getExt5());
                dictTreeChlid.setExt6(sysDictionaryData.getExt6());
                dictTreeChlid.setExt7(sysDictionaryData.getExt7());
                dictTreeChlid.setExt8(sysDictionaryData.getExt8());
                dictTreeChlid.setExt9(sysDictionaryData.getExt9());
                dictTreeChlid.setExt10(sysDictionaryData.getExt10());
                dictTreeChlid.setExt11(sysDictionaryData.getExt11());
                dictTreeChlid.setExt12(sysDictionaryData.getExt12());
                dictTreeChlid.setExt13(sysDictionaryData.getExt13());
                dictTreeChlid.setExt14(sysDictionaryData.getExt14());
                dictTreeChlid.setExt15(sysDictionaryData.getExt15());
                dictTreeChlid.setExt16(sysDictionaryData.getExt16());
                dictTreeChlid.setExt17(sysDictionaryData.getExt17());
                dictTreeChlid.setExt18(sysDictionaryData.getExt18());
                dictTreeChlid.setExt19(sysDictionaryData.getExt19());
                dictTreeChlid.setExt20(sysDictionaryData.getExt20());
                childDictTree.add(dictTreeChlid);
                dictDataListChild(dictTreeChlid, dictionaryDataList);
            }
        }
        if (!childDictTree.isEmpty()) {
            dictTree.setChildren(childDictTree);
        }
    }
    
    /**
     * 下拉框提供数据
     *
     * @param dictTree
     * @return
     * @throws Exception
     */
    @Override
    public Map getSelectCascadeComponentDict (DictTree dictTree) throws Exception {
    
        Map rootMap = new HashMap<>();
        SysDictionary sysDictionary = this.sysDictionaryDao.querySysDictionaryById(dictTree.getDictId());
        assertNotNull(sysDictionary, ERROR_MESSAGE);
        assertNotNull(dictTree.getLevelName(), "层级不能为空");
        if (SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
            siteSQLDictionary(dictTree, rootMap, sysDictionary);
        } else {

            EntityManager entityManager = this.emf.createEntityManager();
            try {
                entityManager.getTransaction().begin();
                // TODO:也整理到DictTreeInfo类里;
                DictTreeInfo dictTreeInfo = new DictTreeInfo();
                dictTreeInfo.setEntityManager(entityManager);
                dictTreeInfo.setFilterConf(dictTree.getFilterConf());
                List<SysDictionaryData> sysDictionaryDataList = new ArrayList<>();
                List<Map<String, Object>> datas = new ArrayList<>();
                if (StringUtil.isEmpty(dictTree.getParentDictCde())) {
                    sysDictionaryDataList = dictTreeInfo.queryDistinctBySelectDictId(
                        dictTree.getDictId(),
                        dictTree.getLevelName()
                    );
                }
                SysDictionaryData sysDictionaryData = dictTreeInfo.querySysDictionaryDataByDictCdeAndDictTypeId(
                    dictTree.getParentDictCde(),
                    dictTree.getDictId()
                );
                WdSysDictLevel wdSysDictLevel = this.wdSysDictLevelDao.queryWdSysDictLevelByDictTypeIdAndLevelName(
                    dictTree.getDictId(),
                    dictTree.getLevelName()
                );
                if (null != sysDictionaryData && null != wdSysDictLevel) {
                    int parentLevel = Integer.parseInt(sysDictionaryData.getLevel());

                    // 父节点的层级和LevelName是不是父子层级, 不是父子层级,返回LevelName所在层级数据
                    if (wdSysDictLevel.getSequence().equals(String.valueOf(parentLevel + 1))) {
                        sysDictionaryDataList = dictTreeInfo.queryCascadeDistinctByDictId(
                            dictTree.getDictId(),
                            dictTree.getParentDictCde()
                        );
                    } else {
                        sysDictionaryDataList = dictTreeInfo.queryDistinctBySelectDictId(
                            dictTree.getDictId(),
                            dictTree.getLevelName()
                        );
                    }
                }
                for (SysDictionaryData dictionaryData : sysDictionaryDataList) {
                    Map<String, Object> map = new HashMap<>();
                    putBaseProperty(dictionaryData, map);
                    datas.add(map);
                }
                rootMap.put(this.treeNodes, datas);
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                entityManager.close();
            }


        }
        return rootMap;
    }

    /**
     * 获取数据字典数据
     *
     * @param dictTree      已知数据
     * @param rootMap       返回集合
     * @param sysDictionary 库数据
     * @throws Exception
     */
    private void siteSQLDictionary(DictTree dictTree, Map rootMap, SysDictionary sysDictionary) throws Exception {

        DictTreeInfo dictTreeInfo = new DictTreeInfo();
        dictTreeInfo.setFilterConf(dictTree.getFilterConf());
        dictTreeInfo.getTree(sysDictionary);
        WdSysDictLevel wdSysDictLevel = this.wdSysDictLevelDao.queryWdSysDictLevelByDictTypeIdAndLevelName(
            dictTree.getDictId(),
            dictTree.getLevelName()
        );
        String sequence = wdSysDictLevel.getSequence();
        if (StringUtil.isEmpty(dictTree.getParentDictCde())) {

            // 父节点是空,返回层级数据
            Map<String, List<Map<String, String>>> levelDataMap = dictTreeInfo.getLevelDataMap();
            rootMap.put(this.treeNodes, levelDataMap.get(sequence));
        } else {
            Map<String, String> nodeLevelMap = dictTreeInfo.getNodeLevelMap();
            int level = Integer.parseInt(nodeLevelMap.get(dictTree.getParentDictCde()));

            // 父节点的层级和LevelName是不是父子层级, 不是父子层级,返回LevelName所在层级数据
            if (level + 1 == Integer.parseInt(sequence)) {
                Map<String, List<Map<String, String>>> nodeChildDataMap = dictTreeInfo.getNodeChildDataMap();
                rootMap.put(this.treeNodes, nodeChildDataMap.get(dictTree.getParentDictCde()));
            } else {
                Map<String, List<Map<String, String>>> levelDataMap = dictTreeInfo.getLevelDataMap();
                rootMap.put(this.treeNodes, levelDataMap.get(sequence));
            }
        }
    }

    /**
     * 下拉树形数据
     *
     * @param dictTree
     * @return
     * @throws Exception
     */
    @Override
    public Map getSelectComponentDict (DictTree dictTree) throws Exception {
        
        Map rootMap = new HashMap<>();
        List<Map<String, Object>> datas = new ArrayList<>();
        List<SysDictionaryData> sysDictionaryDataList = this.sysDictionaryDataDao.queryAllByMainCde(dictTree.getDictCde());
        for (SysDictionaryData sysDictionaryData : sysDictionaryDataList) {
            
            //第一层
            if ("1".equals(sysDictionaryData.getLevel())) {
                Map<String, Object> map = new HashMap<>();
                putBaseProperty(sysDictionaryData, map);
    
                //递归子节点
                dictChildrenNode(sysDictionaryData, sysDictionaryDataList, map);
                datas.add(map);
            }
        }
        rootMap.put(treeNodes, datas);
        return rootMap;
    }
    
    /**
     * 档案编辑详细数据
     * @param dictTypeId
     * @return
     * @throws Exception
     */
    @Override
    public RequestDictTypeJson queryDictDataById (String dictTypeId) throws Exception {
    
        RequestDictTypeJson requestDictTypeJson = new RequestDictTypeJson();
        List<WdSysDictLevel> levelTable = new ArrayList<>();
        SysDictionary sysDictionary = this.sysDictionaryDao.querySysDictionaryById(dictTypeId);
        List<WdSysDictLevel> wdSysDictLevels = this.wdSysDictLevelDao.queryWdSysDictLevelsByDictTypeId(dictTypeId);
        assertNotNull(sysDictionary, ERROR_MESSAGE);
        requestDictTypeJson.setDictTypeId(sysDictionary.getId());
        requestDictTypeJson.setDictTypeName(sysDictionary.getDictTypeName());
        requestDictTypeJson.setDictTypeCde(sysDictionary.getDictTypeCde());
        requestDictTypeJson.setCodeLevel(sysDictionary.getCodeLevel());
        requestDictTypeJson.setPropertyCount(sysDictionary.getPropertyCount());
        requestDictTypeJson.setPropertyTable(Arrays.asList(sysDictionary.getPropertyName().split(SpecialSymbolConstants.COMMA)));
        requestDictTypeJson.setCType(sysDictionary.getCType());

        // 类型是[1数据档案]需要的数据
        requestDictTypeJson.setRelyType(sysDictionary.getRelyType());
        requestDictTypeJson.setRealValue(sysDictionary.getRealValue());
        requestDictTypeJson.setShowValue(sysDictionary.getShowValue());
        requestDictTypeJson.setParentValue(sysDictionary.getParentValue());
        requestDictTypeJson.setTableName(sysDictionary.getTableName());
        if (StringUtil.isNotEmpty(sysDictionary.getPropertyColumn())) {
            requestDictTypeJson.setPropertyColumns(Arrays.asList(sysDictionary.getPropertyColumn().split(SpecialSymbolConstants.COMMA)));
        }
        for (WdSysDictLevel wdSysDictLevel : wdSysDictLevels) {
            levelTable.add(wdSysDictLevel);
        }
        if (!levelTable.isEmpty()) {
            requestDictTypeJson.setLevelTable(levelTable);
        }
        return requestDictTypeJson;
    }

    /**
     * 预览档案数据
     *
     * @param dictTypeJson 前台JSON对象
     * @return
     * @throws Exception
     */
    @Override
    public Object previewDictData(RequestDictTypeJson dictTypeJson) throws Exception {


        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setRealValue(dictTypeJson.getRealValue());
        sysDictionary.setShowValue(dictTypeJson.getShowValue());
        sysDictionary.setPropertyName(StringUtil.joinString(dictTypeJson.getPropertyTable(), SpecialSymbolConstants.COMMA));
        sysDictionary.setCType(dictTypeJson.getCType());
        sysDictionary.setRelyType(dictTypeJson.getRelyType());
        sysDictionary.setParentValue(dictTypeJson.getParentValue());
        sysDictionary.setTableName(dictTypeJson.getTableName());
        sysDictionary.setPropertyColumn(StringUtil.joinString(dictTypeJson.getPropertyColumns(), SpecialSymbolConstants.COMMA));
        sysDictionary.setPropertyCount(dictTypeJson.getPropertyCount());

        DictTreeInfo dictTreeInfo = new DictTreeInfo();
        ModelFilterConf modelFilterConf = new ModelFilterConf();
        modelFilterConf.setLimit(true);
        dictTreeInfo.setFilterConf(modelFilterConf);
        dictTreeInfo.getTree(sysDictionary);
        dictTreeInfo.clearLevelDataMap();
        dictTreeInfo.clearNodeChildDataMap();
        dictTreeInfo.clearNodeLevelMap();
        return dictTreeInfo;
    }

    /**
     * 数据档案id
     *
     * @param dictTypeId
     * @return
     * @throws Exception
     */
    @Override
    public List<SysTableInfo> queryDictColumns(String dictTypeId) throws Exception {

        List<SysTableInfo> tableInfos = new ArrayList<>();
        SysDictionary sysDictionary = this.sysDictionaryDao.querySysDictionaryById(dictTypeId);
        Objects.requireNonNull(sysDictionary, ERROR_MESSAGE);
        if (SysDictionary.TYPE1.equals(sysDictionary.getCType())) {
            tableInfos.add(new SysTableInfo("实际值", sysDictionary.getRealValue()));
            tableInfos.add(new SysTableInfo("显示值", sysDictionary.getShowValue()));
            if (StringUtil.isNotEmpty(sysDictionary.getPropertyColumn())) {
                String[] attrs = sysDictionary.getPropertyColumn().split(SpecialSymbolConstants.COMMA);
                String[] attrNames = sysDictionary.getPropertyName().split(SpecialSymbolConstants.COMMA);
                for (int i = 0; i < attrs.length; i++) {
                    tableInfos.add(new SysTableInfo(attrNames[i], attrs[i]));
                }
            }
        } else {
            tableInfos.add(new SysTableInfo("档案名称", "DICT_NAME"));
            tableInfos.add(new SysTableInfo("档案编码", "DICT_CDE"));
            if (StringUtil.isNotEmpty(sysDictionary.getPropertyName())) {
                String[] attrNames = sysDictionary.getPropertyName().split(SpecialSymbolConstants.COMMA);
                for (int i = 0; i < attrNames.length; i++) {
                    tableInfos.add(new SysTableInfo(attrNames[i], "EXT" + (i + 1)));
                }
            }
        }
        return tableInfos;
    }

    /**
     * 判断属性
     * @param i 第几个属性位置
     * @param propertyName 属性名称
     * @param sysDictionaryData 数据对象
     * @param map 缓冲区
     */
    private void extFlip (int i, String propertyName, SysDictionaryData sysDictionaryData, Map map) {

        map.put(propertyName, RequestDictJson._getPropertyValue(i, sysDictionaryData));
    }

    /**
     * 获取字典子节点
     *
     * @param parentData            父节点对象
     * @param sysDictionaryDataList 基础档案对象集合
     * @param map                   结果集
     */
    private void dictChildrenNode (SysDictionaryData parentData, List<SysDictionaryData> sysDictionaryDataList, Map<String, Object> map) {
    
        List<Map<String, Object>> bufferDatas = new ArrayList<>();
        for (SysDictionaryData childrenData : sysDictionaryDataList) {
            if (parentData.getId().equals(childrenData.getParentId())) {
                Map<String, Object> bufferMap = new HashMap<>();
                putBaseProperty(childrenData, bufferMap);
                bufferDatas.add(bufferMap);
                dictChildrenNode(childrenData, sysDictionaryDataList, bufferMap);
            }
        }
        if (!bufferDatas.isEmpty()) {
            map.put("children", bufferDatas);
        }
    }

    /**
     * 设置基本属性
     *
     * @param sysDictionaryData 数据对象
     * @param map               缓冲
     */
    private void putBaseProperty (SysDictionaryData sysDictionaryData, Map<String, Object> map) {
    
        map.put("dictTypeId", sysDictionaryData.getDictTypeId());
        map.put("dictCde", sysDictionaryData.getDictCde());
        map.put("dictName", sysDictionaryData.getDictName());
        map.put("parentId", sysDictionaryData.getParentId());
        map.put("level", sysDictionaryData.getLevel());
        map.put("cIndex", sysDictionaryData.getcIndex());
        map.put("ext1", sysDictionaryData.getExt1());
        map.put("ext2", sysDictionaryData.getExt2());
        map.put("ext3", sysDictionaryData.getExt3());
        map.put("ext4", sysDictionaryData.getExt4());
        map.put("ext5", sysDictionaryData.getExt5());
        map.put("ext6", sysDictionaryData.getExt6());
        map.put("ext7", sysDictionaryData.getExt7());
        map.put("ext8", sysDictionaryData.getExt8());
        map.put("ext9", sysDictionaryData.getExt9());
        map.put("ext10", sysDictionaryData.getExt10());
        map.put("ext11", sysDictionaryData.getExt11());
        map.put("ext12", sysDictionaryData.getExt12());
        map.put("ext13", sysDictionaryData.getExt13());
        map.put("ext14", sysDictionaryData.getExt14());
        map.put("ext15", sysDictionaryData.getExt15());
        map.put("ext16", sysDictionaryData.getExt16());
        map.put("ext17", sysDictionaryData.getExt17());
        map.put("ext18", sysDictionaryData.getExt18());
        map.put("ext19", sysDictionaryData.getExt19());
        map.put("ext20", sysDictionaryData.getExt20());
        if(StringUtil.isNotEmpty(sysDictionaryData.getPropertyCount())) {//无设置属性不赋值
            String[] propertyNames = sysDictionaryData.getPropertyName().split(SpecialSymbolConstants.COMMA);
        	for (int i = 0; i < Integer.parseInt(sysDictionaryData.getPropertyCount()); i++) {
        		extFlip(i, propertyNames[i], sysDictionaryData, map);
        	}
        }
    }

    /**
     * 字典层级表里全部数据分组排序
     * @return
     * @throws Exception
     */
    private Map<String, List<WdSysDictLevel>> setUpDictLevelInfo () throws Exception {

        //记录循环里上一个DictTypeId
        String upDictTypeId = "";
        Map<String, List<WdSysDictLevel>> map = new HashMap<>();
        List<WdSysDictLevel> wdSysDictLevels = this.wdSysDictLevelDao.queryEntitysOrderByDictTypeIdAndSequence();
        for (WdSysDictLevel wdSysDictLevel : wdSysDictLevels) {
            if (!upDictTypeId.equals(wdSysDictLevel.getDictTypeId())) {
                List<WdSysDictLevel> levels = new ArrayList<>();
                levels.add(wdSysDictLevel);
                map.put(wdSysDictLevel.getDictTypeId(), levels);
            } else {
                List<WdSysDictLevel> levels = map.get(wdSysDictLevel.getDictTypeId());
                levels.add(wdSysDictLevel);
            }
            upDictTypeId = wdSysDictLevel.getDictTypeId();
        }
        return map;
    }
    
}
