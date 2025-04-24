package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.exception.WdFileNotFoundException;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.common.pojo.UploadFile;
import com.rolldata.core.util.CacheUtils;
import com.rolldata.core.util.FileUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.ResourceUtil;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.UUIDGenerator;
import com.rolldata.web.common.enums.TreeNodeType;
import com.rolldata.web.common.pojo.TreeNode;
import com.rolldata.web.system.dao.SysDictionaryDao;
import com.rolldata.web.system.dao.SysDictionaryDataDao;
import com.rolldata.web.system.dao.WdSysDictLevelDao;
import com.rolldata.web.system.entity.SysDictionary;
import com.rolldata.web.system.entity.SysDictionaryData;
import com.rolldata.web.system.entity.WdSysDictLevel;
import com.rolldata.web.system.pojo.DictDigitRule;
import com.rolldata.web.system.pojo.DictTree;
import com.rolldata.web.system.pojo.RequestDictJson;
import com.rolldata.web.system.pojo.ResponseDictJson;
import com.rolldata.web.system.service.SysDictionaryDataService;
import com.rolldata.web.system.service.SysDictionaryService;
import com.rolldata.web.system.util.ExcelUtil;
import com.rolldata.web.system.util.UserUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典类型表业务层实现
 * @author shenshilong
 * @createDate 2018-6-11
 */
@Service("sysDictionaryDataService")
@Transactional
public class SysDictionaryDataServiceImpl implements SysDictionaryDataService{
    
    @Autowired
    private SysDictionaryDataDao sysDictionaryDataDao;
    
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    
    @Autowired
    private WdSysDictLevelDao wdSysDictLevelDao;
    
     /**
     * 单条查询
     * @param id
     * @throws Exception
     * @createDate 2018-6-11
     */
    @Override
    public SysDictionaryData getById(String id) {
        return sysDictionaryDataDao.findById(id).orElse(null);
    }
    
     /**
     * 保存
     *
      * @param ajaxJson
      * @param requestDictJson
      * @throws Exception
     * @createDate 2018-6-11
     */
    @Override
    public void save (AjaxJson ajaxJson, RequestDictJson requestDictJson) throws Exception {
    
        Map map = getDictCdeData(ajaxJson, requestDictJson);
        if (!ajaxJson.isSuccess()) {
            return;
        }
        Integer max = 0;
        SysDictionaryData sysDictionaryData = new SysDictionaryData();
        if (StringUtil.isNotEmpty(requestDictJson.getpId())) {
            sysDictionaryData.setParentId(requestDictJson.getpId());
        } else {
            sysDictionaryData.setParentId(TreeNode.ROOT);
        }
        String cIndex = sysDictionaryDataDao.queryMaxCindex(requestDictJson.getDictTypeId());
        if (StringUtil.isNotEmpty(cIndex)) {
            max = Integer.parseInt(cIndex);
        }
        sysDictionaryData.setId(UUIDGenerator.generate());
        sysDictionaryData.setDictName(requestDictJson.getDictName());
        sysDictionaryData.setDictTypeId(requestDictJson.getDictTypeId());
        sysDictionaryData.setLevel(requestDictJson.getLevel());

        //原来设计取map.get("endNum") , 现改成取表里C_INDEX最大的
        sysDictionaryData.setcIndex(String.valueOf(max + 1));
        sysDictionaryData.setDictCde((String)ajaxJson.getObj());
        sysDictionaryData.setExt1(requestDictJson.getExt1());
        sysDictionaryData.setExt2(requestDictJson.getExt2());
        sysDictionaryData.setExt3(requestDictJson.getExt3());
        sysDictionaryData.setExt4(requestDictJson.getExt4());
        sysDictionaryData.setExt5(requestDictJson.getExt5());
        sysDictionaryData.setExt6(requestDictJson.getExt6());
        sysDictionaryData.setExt7(requestDictJson.getExt7());
        sysDictionaryData.setExt8(requestDictJson.getExt8());
        sysDictionaryData.setExt9(requestDictJson.getExt9());
        sysDictionaryData.setExt10(requestDictJson.getExt10());
        sysDictionaryData.setExt11(requestDictJson.getExt11());
        sysDictionaryData.setExt12(requestDictJson.getExt12());
        sysDictionaryData.setExt13(requestDictJson.getExt13());
        sysDictionaryData.setExt14(requestDictJson.getExt14());
        sysDictionaryData.setExt15(requestDictJson.getExt15());
        sysDictionaryData.setExt16(requestDictJson.getExt16());
        sysDictionaryData.setExt17(requestDictJson.getExt17());
        sysDictionaryData.setExt18(requestDictJson.getExt18());
        sysDictionaryData.setExt19(requestDictJson.getExt19());
        sysDictionaryData.setExt20(requestDictJson.getExt20());
        sysDictionaryData.setCreateTime(new Date());
        sysDictionaryData.setCreateUser(UserUtils.getUser().getId());
        sysDictionaryData.setUpdateTime(new Date());
        sysDictionaryData.setUpdateUser(UserUtils.getUser().getId());
        sysDictionaryData = sysDictionaryDataDao.save(sysDictionaryData);
        
        ajaxJson.setSuccessAndMsg(Boolean.TRUE, MessageUtils.getMessage("common.sys.save.success"));
        ajaxJson.setObj(getDictData(sysDictionaryData));
    }
    
    /**
     * 取位数最大值
     * @param digit 位数
     * @return
     */
    private int getDigitMax(int digit) {
    
        StringBuffer dsb = new StringBuffer();
        for (int l = 0; l < digit; l++) {
            dsb.append("9");
        }
        return Integer.parseInt(dsb.toString());
    }
    
    /**
     * 查询档案内容表中同一目录下代码是否重复
     */
    @Override
    public boolean isExistByDictCde(String dictCde, String dictTypeId) throws Exception{
        SysDictionaryData sysDictionaryData = sysDictionaryDataDao.getBydictCdeAndDictTypeId(dictCde, dictTypeId);
        if (sysDictionaryData != null) {
            return true;
        }
        return false;
    }
    
    /**
     * 查询档案内容表中同一目录下名称是否重复
     */
    @Override
    public boolean isExistByDictName(String pId, String dictName, String dictTypeId) throws Exception{
        SysDictionaryData sysDictionaryData = sysDictionaryDataDao.getBydictNameAndDictTypeId(
            pId,
            dictName,
            dictTypeId
        );
        if (sysDictionaryData != null) {
            return true;
        }
        return false;
    }
    
    /**
     * 修改父ID
     */
    @Override
    public void updateParentId(String id, String parentId) throws Exception{
        sysDictionaryDataDao.updateByIdAndParentId(id, parentId);
    }
    
    /**
     * 单条更新
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-12
     */
    @Override
    public void update(SysDictionaryData sysDictionaryData) throws Exception {
        
        sysDictionaryDataDao.update(
            sysDictionaryData.getId(),
            sysDictionaryData.getDictName(),
            sysDictionaryData.getExt1(),
            sysDictionaryData.getExt2(),
            sysDictionaryData.getExt3(),
            sysDictionaryData.getExt4(),
            sysDictionaryData.getExt5(),
            sysDictionaryData.getExt6(),
            sysDictionaryData.getExt7(),
            sysDictionaryData.getExt8(),
            sysDictionaryData.getExt9(),
            sysDictionaryData.getExt10(),
            sysDictionaryData.getExt11(),
            sysDictionaryData.getExt12(),
            sysDictionaryData.getExt13(),
            sysDictionaryData.getExt14(),
            sysDictionaryData.getExt15(),
            sysDictionaryData.getExt16(),
            sysDictionaryData.getExt17(),
            sysDictionaryData.getExt18(),
            sysDictionaryData.getExt19(),
            sysDictionaryData.getExt20(),
            UserUtils.getUser().getId(),
            new Date()
        );

    }

     /**
     * 根据dictTypeId删除全部
     * @param dictTypeId
     * @throws Exception
     * @createDate 2018-6-12
     */
    @Override
    public void delAllByDictTypeId(String dictTypeId) throws Exception {
        sysDictionaryDataDao.delAllByDictTypeId(dictTypeId);
    }
    
     /**
     * 删除
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-11
     */
    @Override
    public void delete(SysDictionaryData sysDictionaryData) throws Exception {
        
        SysDictionaryData oldObj = sysDictionaryDataDao.queryDistinctById(sysDictionaryData.getId());
        sysDictionaryDataDao.delteSysDictionaryDataById(sysDictionaryData.getId());
    }
    
    /**
     * 级联删除(循环判断是否存在子节点)
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-11
     */
    @Override
    public void recursiveMethodDel(SysDictionaryData sysDictionaryData) throws Exception{
        List<SysDictionaryData> list = sysDictionaryDataDao.findByParentId(sysDictionaryData.getId());
        if(list.size() > 0){
            for (SysDictionaryData sysDictionaryData2 : list) {
                recursiveMethodDel(sysDictionaryData2);
            }
        }
        delete(sysDictionaryData);
    }

    /**
     * 获取数据字典内容表新建、编辑返回json
     * @param sysDictionaryData
     * @throws Exception
     */ 
    @Override
    public ResponseDictJson getDictData(SysDictionaryData sysDictionaryData) throws Exception {
        SysDictionary sysDictionary2 = sysDictionaryDao.findById(sysDictionaryData.getDictTypeId()).orElse(null);
        ResponseDictJson responseDictJson = new ResponseDictJson();
        responseDictJson.setCheck(false);
        responseDictJson.setCopy(false);
        responseDictJson.setEdit(true);
        if (SysDictionary.SHOWTIME_TABLE.equals(sysDictionary2.getShowType())) {
            responseDictJson.setDrag(false);
        }else {
            responseDictJson.setDrag(true);
        }
        List<DictTree> dictTreeList = new ArrayList<DictTree>();
            DictTree dictTree = new DictTree();
            if (SysDictionary.SHOWTIME_TABLE.equals(sysDictionary2.getShowType())) {
                dictTree.setpId("0");
                dictTree.setDrag(false);
                dictTree.setDrop(false);
                dictTree.setType(TreeNodeType.DICT);
            }else {
                dictTree.setpId(sysDictionaryData.getParentId());
                dictTree.setDrag(true);
                dictTree.setDrop(true);
                dictTree.setType(TreeNodeType.DICT);
            }
            dictTree.setChecked(false);
            dictTree.setNocheck(false);
            dictTree.setCopy(false);
            dictTree.setIcon(null);
            dictTree.setIconSkin(null);
            dictTree.setValid_children(null);
            dictTree.setId(sysDictionaryData.getId());
            dictTree.setName(sysDictionaryData.getDictCde()+"-"+sysDictionaryData.getDictName());
            dictTree.setDictId(sysDictionaryData.getId());
            dictTree.setDictTypeId(sysDictionaryData.getDictTypeId());
            dictTree.setDictCde(sysDictionaryData.getDictCde());
            dictTree.setDictName(sysDictionaryData.getDictName());
            
            dictTreeList.add(dictTree);
        
        responseDictJson.setTreeNodes(dictTreeList);
        return responseDictJson;
    }

    /**
     * 查询档案内容表中同一目录下代码是否重复,排除自己
     */
    @Override
    public boolean isExistByDictCde(String dictCde, String dictTypeId,
                                    String dictId) throws Exception {
        SysDictionaryData sysDictionaryData = sysDictionaryDataDao.getBydictCdeAndDictTypeId(dictCde, dictTypeId,dictId);
        if (sysDictionaryData != null) {
            return true;
        }
        return false;
    }

    /**
     * 查询档案内容表中同一目录下名称是否重复,排除自己
     */
    @Override
    public boolean isExistByDictName(String pId, String dictName, String dictTypeId,
                                     String dictId) throws Exception {
        SysDictionaryData sysDictionaryData = sysDictionaryDataDao.getBydictNameAndDictTypeId(
            pId,
            dictName,
            dictTypeId,
            dictId
        );
        if (sysDictionaryData != null) {
            return true;
        }
        return false;
    }
    
    /**
     * 导入组织模版数据,存入缓存
     * @param ajaxJson
     * @param uploadFile
     * @param dictTypeId
     * @throws Exception
     */
    @Override
    public void uploadContent (AjaxJson ajaxJson, CommonsMultipartFile uploadFile, String dictTypeId)
            throws Exception {
    
        if (!uploadFile.isEmpty()) {
            String originalFilename = uploadFile.getOriginalFilename();
        
            // 当文件超过设置的大小时，则不运行上传
            if (uploadFile.getSize() > UploadFile.fileSize) {
                throw new Exception(MessageUtils.getMessageOrSelf("common.sys.file.size.error",UploadFile.sysConfigInfo.getFileSize()));
            }
            int pos = originalFilename.lastIndexOf(".");
        
            // 获取文件名后缀
            String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
            String fileName = originalFilename.substring(0, pos);
        
            // 判断该类型的文件是否在允许上传的文件类型内
            if (!Arrays.asList(UploadFile.TypeMap.get("dict").split(",")).contains(fileSuffix)) {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.file.type.error"));
                return;
            }
            File tmpFile = null;
            try {
                // 检查上传文件的目录
                String realPath = ResourceUtil.getUploadFileTempPath();
                FileUtil.mkDir(realPath);
                String timeMillis = System.currentTimeMillis() + "";
                //临时文件名
                String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
                //正式保存的文件名
                fileName = fileName + "." + fileSuffix;
                String path = realPath + tempfileName ;
                tmpFile = new File(path);
    
                // 通过CommonsMultipartFile的方法直接写文件
                uploadFile.transferTo(tmpFile);
                if (UploadFile.DICT_TXT.equalsIgnoreCase(fileSuffix)) {
                    analysisTxt(path, dictTypeId);
                } else {
                    Map<String, DictDigitRule> digitMap = new HashMap<String, DictDigitRule>();
                    List<WdSysDictLevel> dictLevels = wdSysDictLevelDao.queryWdSysDictLevelsByDictTypeId(dictTypeId);
                    for (WdSysDictLevel wdSysDictLevel : dictLevels) {
                        DictDigitRule dictDigitRule = new DictDigitRule();
                        dictDigitRule.setMax(getDigitMax(Integer.parseInt(wdSysDictLevel.getDigit())));
                        dictDigitRule.setDigit(Integer.parseInt(wdSysDictLevel.getDigit()));
                        digitMap.put(wdSysDictLevel.getSequence(), dictDigitRule);
                    }
                    Map<String, Object> rsMap = analysisXLS(digitMap, tmpFile, dictTypeId);
                    Map msgMap = new HashMap();
                    String _uuid = UUIDGenerator.generate();
                    Integer total = (Integer)rsMap.get("total");
                    msgMap.put("_uuid", _uuid);
                    msgMap.put("right", "基础档案:" + ((List)rsMap.get("rsList")).size() + "条");
                    msgMap.put("bad", "基础档案:" + (total - ((List)rsMap.get("rsList")).size()) + "条");
                    msgMap.put("dictTypeId", dictTypeId);
                    msgMap.put("dictList", rsMap.get("rsList"));
    
                    //TODO:用户重新上传,清除上一次缓存
                    CacheUtils.put(_uuid, rsMap.get("rsList"));
                    ajaxJson.setObj(msgMap);
                }
            } catch (FileNotFoundException e) {
                throw new WdFileNotFoundException(e);
            } catch (Exception e) {
                throw e;
            } finally {
            
                // 删除临时文件
                if (tmpFile.exists()) {
                    tmpFile.delete();
                }
            }
        } else {
            throw new Exception(MessageUtils.getMessage("common.sys.file.notfound.error"));
        }
    }
    
    /**
     * 创建基础档案数据时返回code
     * @param ajaxJson 返回前台json
     * @param requestDictJson
     * @throws Exception
     */
    @Override
    public Map getDictCdeData (AjaxJson ajaxJson, RequestDictJson requestDictJson) throws Exception {
    
        Map<String, Object> map = new HashMap<String, Object>();
        
        //结尾
        int endNum = 0;
    
        //位数最大的值  2位=99,3位=999
        int maxNum = 0;
    
        //库里上一个最大值得结尾
        String upEndNum = "";
        String parentDictCde = requestDictJson.getParentDictCde();
        int maxLeng = parentDictCde.length();
        if (!TreeNode.ROOT.equals(requestDictJson.getpId())) {
            requestDictJson.setLevel(String.valueOf(Integer.parseInt(requestDictJson.getLevel()) + 1));
        }
        WdSysDictLevel wdSysDictLevel = wdSysDictLevelDao.queryWdSysDictLevelByDictTypeIdAndSequence(requestDictJson.getDictTypeId(), requestDictJson.getLevel());
        if (null == wdSysDictLevel) {
            ajaxJson.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessageOrSelf("report.dict.subordinate.level.error", requestDictJson.getLevel()));
            return map;
        }
        maxNum = getDigitMax(Integer.parseInt(wdSysDictLevel.getDigit()));
        maxLeng = "1".equals(requestDictJson.getLevel()) ? parentDictCde.length() : parentDictCde.length() + 1;

        /*
            不同库substring的写法不同,hibernate会转
            SQLSERVER：select substring('cde',3,len('cde'))
            MYSQL：select substring('cde',3,length('cde'))
            ORACLE：select substr('cde',3,length('cde')) from dual
         */
        upEndNum = sysDictionaryDataDao.queryMaxMantissa(
            maxLeng,
            requestDictJson.getDictTypeId(),
            requestDictJson.getpId()
        );
        if (StringUtil.isNotEmpty(upEndNum)) {
            endNum = Integer.parseInt(upEndNum) + 1;
            if (endNum > maxNum) {
                ajaxJson.setSuccessAndMsg(Boolean.FALSE, MessageUtils.getMessage("report.dict.digit.toobig.error"));
                return map;
            }
        } else {
            endNum = 1;
        }
    
        //父节点parentDictCde=0 并且 是第一层时 置空
        if (TreeNode.ROOT.equals(parentDictCde) && "1".equals(requestDictJson.getLevel())) {
            parentDictCde = "";
        }
        String rs = StringUtil.frontCompWithZore(endNum, wdSysDictLevel.getDigit());
        ajaxJson.setObj(parentDictCde + rs);
        ajaxJson.setSuccessAndMsg(Boolean.TRUE, MessageUtils.getMessage("common.sys.query.success"));
        map.put("dictCde", parentDictCde + rs);
        map.put("endNum", endNum);
        return map;
    }
    
    /**
     * 执行导入基础档案
     *
     * @param dictTypeId 字典id
     * @param _uuid 执行id
     * @throws Exception
     */
    @Override
    public void complyImportDict (String dictTypeId, String _uuid) throws Exception {
    
        List<SysDictionaryData> sysDictionaryDataList = (List<SysDictionaryData>)CacheUtils.get(_uuid);
        if (null == sysDictionaryDataList) {
            new Exception("缓存中数据异常");
        }
        //先删除旧的，后添加新的
        sysDictionaryDataDao.delAllByDictTypeId(dictTypeId);
        for (SysDictionaryData dict : sysDictionaryDataList) {
            sysDictionaryDataDao.saveAndFlush(dict);
        }

    }
    
    /**
     * 生成模版数据文件及路径
     *
     * @param dictTypeId 基础档案id
     * @return
     * @throws Exception
     */
    @Override
    public String createTemplate (String dictTypeId) throws Exception {

        int propertyCount = 0;
        String path = ResourceUtil.getTemplatePath() + "dict" + System.currentTimeMillis() + ".xlsx";
        System.out.println("生成路径" + path);
        List<SysDictionaryData> dictionaryDatas = sysDictionaryDataDao.findByDictTypeId(dictTypeId);
        List<SysDictionaryData> data = new ArrayList<>(dictionaryDatas.size());
        SysDictionary sysDictEntity = sysDictionaryDao.querySysDictionaryById(dictTypeId);
        if (StringUtil.isNotEmpty(sysDictEntity.getPropertyCount())) {
            propertyCount = Integer.parseInt(sysDictEntity.getPropertyCount());
        }

        // 父字段 为key
        Map<String, List<SysDictionaryData>> parentIdData = new HashMap<>();
        for (SysDictionaryData dictionaryData : dictionaryDatas) {
            List<SysDictionaryData> childDatas = parentIdData.get(dictionaryData.getParentId());
            if (null == childDatas) {
                childDatas = new ArrayList<>();
                parentIdData.put(dictionaryData.getParentId(), childDatas);
            }
            childDatas.add(dictionaryData);
        }
        final String parentKey = "0";
        List<SysDictionaryData> dictionaryData = parentIdData.get(parentKey);
        if(dictionaryData!=null){
            for (SysDictionaryData sysDictionaryData : dictionaryData) {
                data.add(sysDictionaryData);
                childNode(parentIdData, sysDictionaryData.getId(), data);
            }
        }

        // 创建新的Excel 工作簿
        try (XSSFWorkbook workBook = new XSSFWorkbook();) {

            // 在Excel工作簿中建一工作表，其名为缺省值
            // 如要新建一名为"sheet1"的工作表，其语句为：
            XSSFSheet sheet = workBook.createSheet("sheet1");
            XSSFRow fristRow = sheet.createRow((short) (0));
            XSSFCell fristCell = fristRow.createCell((short) 0);

            // 设置单元格值
            // 默认字符型
            fristCell.setCellType(CellType.STRING);
            fristCell.setCellValue("名称");
            if (data.size() == 0) {
                int startDemo = 1;
                XSSFRow row = sheet.createRow((short) (1));
                XSSFCell cell0 = row.createCell((short) 0);
                cell0.setCellType(CellType.STRING);
                cell0.setCellValue("示例名称");
                for (int l = 0; l < propertyCount; l++) {
                    XSSFCell cell = row.createCell((short) startDemo++);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue("示例属性" + (l + 1));
                }
            }
            for (int i = 0; i < data.size(); i++) {
                SysDictionaryData sysDictionaryData = data.get(i);

                // 在索引i的位置创建行
                XSSFRow row = sheet.createRow((short) (i + 1));
                int level = 0;
                if (!"1".equals(sysDictionaryData.getLevel())) {
                    level = 1;
                    for (; level < Integer.parseInt(sysDictionaryData.getLevel()) - 1; level++) {
                        XSSFCell cell = row.createCell((short) level);

                        // 设置单元格值
                        // 默认字符型
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("");
                    }
                }
                int g = 0;
                for (int j = level; j < (propertyCount + 1 + level); j++) {

                    // 在索引j的位置创建单元格
                    XSSFCell cell = row.createCell((short) j);

                    // 设置单元格值
                    // 默认字符型
                    cell.setCellType(CellType.STRING);
                    if (g == 0) {
                        cell.setCellValue(sysDictionaryData.getDictName());
                    } else {
                        cell.setCellValue(RequestDictJson._getNextPropertyValue(g, sysDictionaryData));
                    }
                    g++;
                }
            }
            FileOutputStream fOut = null;
            try {
                // 新建一输出文件流
                fOut = new FileOutputStream(path);
                // 把相应的Excel 工作簿存盘
                workBook.write(fOut);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fOut != null) {
                    // 清空缓冲区数据
                    fOut.flush();
                    // 操作结束，关闭文件
                    fOut.close();
                }
            }
        } catch (Exception ex) {
            throw ex;

        }
        return path;
    }

    private void childNode(Map<String, List<SysDictionaryData>> parentIdData, String parentKey,
        List<SysDictionaryData> data) throws Exception {

        List<SysDictionaryData> childDatas = parentIdData.get(parentKey);
        if (null == childDatas) {
            return;
        }
        for (SysDictionaryData childData : childDatas) {
            data.add(childData);
            childNode(parentIdData, childData.getId(), data);
        }
    }

    /**
     * 查询基础档案数据
     *
     * @param dictId 字典id
     * @return
     * @throws Exception
     */
    @Override
    public List<SysDictionaryData> queryAllByDictId(String dictId) throws Exception {

        return sysDictionaryDataDao.queryAllByDictId(dictId);
    }

    /**
     * 解析Excel 并插入库
     * @param path
     * @param dictTypeId
     */
    private Map<String, Object> analysisXLS(Map<String, DictDigitRule> digitMap, File path, String dictTypeId) throws Exception {
    
        Map<String, Object> rsMap = new HashMap<String, Object>();

        // 为跳过第一行目录设置count
        int index = 0;

        //记录起始y坐标位置
        int startY = 0;
    
        //记录重复的名称位置
        List<Integer> repeatNameIndex =  new ArrayList<Integer>();
    
        //记录重复的名称及重复的次数
        Map<String, Integer> repeatNameCount = new HashMap<String, Integer>();
    
        //过滤掉重复后的结果集
        List<SysDictionaryData> rsList = new ArrayList<SysDictionaryData>();
    
        //记录此行是否取到层级名称了, 如果此行对应有值,则再循环取出来的放入属性中
        Map<Integer, List<Integer>> attributesMap = new HashMap<Integer, List<Integer>>();
    
        //记录每层的code大小
        Map<Integer, Integer> eachLevelCode = new HashMap<Integer, Integer>();
    
        //上一个层级
        Integer oldLevel = -1;

        //缓存父id的map,key=层级,value=父id
        Map<Integer, String> map = new HashMap<Integer, String>();
        String pId = SysDictionaryData.ROOT;
        String id = "";
        Integer maxLevel = 0;
        try (Workbook workbook = ExcelUtil.getWorkBook(path);) {

            //取第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet){
                if (index == 0) {
                    index++;
                    continue;
                }
                int end = row.getLastCellNum();

                //记录每行第一个不为空的格子,有效格
                int effectiveGrid = -1;

                //记录设置过档案属性的变量
                List<Integer> attributes = new ArrayList<Integer>();
                attributes.add(0);

                //新建档案对象(有多余的对象没set到集合总,垃圾回收?)
                SysDictionaryData sysDictionaryData = new SysDictionaryData();
                sysDictionaryData.setcIndex(String.valueOf(index));
                sysDictionaryData.setDictTypeId(dictTypeId);
                sysDictionaryData.setCreateUser(UserUtils.getUser().getId());
                sysDictionaryData.setCreateTime(new Date());
                sysDictionaryData.setUpdateUser(UserUtils.getUser().getId());
                sysDictionaryData.setUpdateTime(new Date());
                id = UUIDGenerator.generate();
                sysDictionaryData.setId(id);
                for (int x = 0; x < end; x++) {
                    Cell cell = row.getCell(x);
                    if (index == 1 && x == startY && null == cell && !StringUtil.isNotEmpty(cell)) {
                        throw new Exception("首行根层级不能为空");
                    }

                    //记录有效格位置
                    if (null != cell && StringUtil.isNotEmpty(ExcelUtil.getCellValue(cell)) && effectiveGrid == -1) {
                        effectiveGrid = x;

                        //另一跟节点时,清缓存的父节点
                        if (x == 0) {
                            map.clear();
                        }
                    }
                    if (null == cell || !StringUtil.isNotEmpty(ExcelUtil.getCellValue(cell))) {
                        if (effectiveGrid == -1) {
                            continue;
                        }
                    }
                    pId = map.get(x - 1);
                    if (x == 0) {
                        pId = SysDictionaryData.ROOT;
                        Integer oldLevelCode = eachLevelCode.get(1);
                        if (null != oldLevelCode) {

                            //把层级那个从新赋值
                            eachLevelCode.clear();
                            eachLevelCode.put(1, oldLevelCode);
                        }
                    }
                    if (!StringUtil.isNotEmpty(sysDictionaryData.getParentId())) {
                        sysDictionaryData.setParentId(pId);
                    }
                    if (null != attributesMap.get(index)) {
                        List<Integer> attrs = attributesMap.get(index);

                        //设置档案属性
                        String cellValue = ExcelUtil.getCellValue(cell);
                        if (attrs.size() == 1) {
                            sysDictionaryData.setExt1(cellValue);
                        } else if (attrs.size() == 2) {
                            sysDictionaryData.setExt2(cellValue);
                        } else if (attrs.size() == 3) {
                            sysDictionaryData.setExt3(cellValue);
                        } else if (attrs.size() == 4) {
                            sysDictionaryData.setExt4(cellValue);
                        } else if (attrs.size() == 5) {
                            sysDictionaryData.setExt5(cellValue);
                        } else if (attrs.size() == 6) {
                            sysDictionaryData.setExt6(cellValue);
                        } else if (attrs.size() == 7) {
                            sysDictionaryData.setExt7(cellValue);
                        } else if (attrs.size() == 8) {
                            sysDictionaryData.setExt8(cellValue);
                        } else if (attrs.size() == 9) {
                            sysDictionaryData.setExt9(cellValue);
                        } else if (attrs.size() == 10) {
                            sysDictionaryData.setExt10(cellValue);
                        } else if (attrs.size() == 11) {
                            sysDictionaryData.setExt11(cellValue);
                        } else if (attrs.size() == 12) {
                            sysDictionaryData.setExt12(cellValue);
                        } else if (attrs.size() == 13) {
                            sysDictionaryData.setExt13(cellValue);
                        } else if (attrs.size() == 14) {
                            sysDictionaryData.setExt14(cellValue);
                        }else if (attrs.size() == 15) {
                            sysDictionaryData.setExt15(cellValue);
                        }else if (attrs.size() == 16) {
                            sysDictionaryData.setExt16(cellValue);
                        }else if (attrs.size() == 17) {
                            sysDictionaryData.setExt17(cellValue);
                        }else if (attrs.size() == 18) {
                            sysDictionaryData.setExt18(cellValue);
                        }else if (attrs.size() == 19) {
                            sysDictionaryData.setExt19(cellValue);
                        }else if (attrs.size() == 20) {
                            sysDictionaryData.setExt20(cellValue);
                        }

                        //取出最大的+1
                        attrs.add(attrs.get(attrs.size() - 1) + 1);
                    } else {

                        //上一层大于当前层
                        if (oldLevel > (x + 1)) {
                            Integer oldLevelCode = eachLevelCode.get((x + 1));
                            Integer upLevel = eachLevelCode.get(x + 2);
                            if (null != upLevel) {
                                eachLevelCode.remove(x + 2);
                            }
                        }
                        if (repeatNameCount.containsKey(sysDictionaryData.getDictName())) {
                            int count = repeatNameCount.get(sysDictionaryData.getDictName());
                            repeatNameCount.put(sysDictionaryData.getDictName(), count + 1);
                            repeatNameIndex.add(index);
                        } else {
                            Integer cIndex = x + 1;
                            oldLevel = cIndex;
                            sysDictionaryData.setDictName(ExcelUtil.getCellValue(cell));
                            sysDictionaryData.setLevel(String.valueOf(cIndex));
                            if (cIndex >= maxLevel) {
                                maxLevel = cIndex;
                            }
                            sysDictionaryData.setcIndex(String.valueOf(index - repeatNameIndex.size()));
                            DictDigitRule maxDigit = digitMap.get(String.valueOf(cIndex));
                            if (null == maxDigit) {
                                continue;
                            }
                            if (eachLevelCode.containsKey(cIndex)) {
                                Integer num = eachLevelCode.get(cIndex);
                                num++;
                                eachLevelCode.put(cIndex, num);

                                //如果数据超过最大的位数,禁止创建
                                if (num <= maxDigit.getMax()) {
                                    sysDictionaryData.setDictCde(String.valueOf(num));
                                    String code = StringUtil.frontCompWithZore(Integer.parseInt(sysDictionaryData.getDictCde()), String.valueOf(maxDigit.getDigit()));
                                    sysDictionaryData.setDictCde(code);
                                    repeatNameCount.put(sysDictionaryData.getDictName(), 1);
                                    rsList.add(sysDictionaryData);
                                    if (StringUtil.isNotEmpty(maxDigit.getCde())) {
                                        maxDigit.setCde(String.valueOf(Integer.parseInt(maxDigit.getCde()) + 1));
                                    } else {
                                        maxDigit.setCde(maxDigit.getThisCode());
                                    }
                                }
                            } else {
                                if (StringUtil.isNotEmpty(maxDigit.getCde())) {
                                    maxDigit.setCde(String.valueOf(Integer.parseInt(maxDigit.getCde()) + 1));
                                } else {
                                    maxDigit.setCde(maxDigit.getThisCode());
                                }
                                sysDictionaryData.setDictCde("1");
                                String code = StringUtil.frontCompWithZore(Integer.parseInt(sysDictionaryData.getDictCde()), String.valueOf(maxDigit.getDigit()));
                                sysDictionaryData.setDictCde(code);
                                eachLevelCode.put(cIndex, 1);
                                rsList.add(sysDictionaryData);
                                repeatNameCount.put(sysDictionaryData.getDictName(), 1);
                            }
                            attributesMap.put(index, attributes);
                        }
                    }
                    if (null != cell && StringUtil.isNotEmpty(ExcelUtil.getCellValue(cell))) {
                        map.put(x, id);
                    }
                }
                index++;
            }
        } catch (Exception ex) {
            throw ex;
        }

        /*
        没想到什么好的办法获取父级组合的code
         */
        for (int i = 2; i <= maxLevel; i++) {
            for (SysDictionaryData dict : rsList) {
                if (dict.getLevel().equals(String.valueOf(i))) {
                    for (SysDictionaryData parentDict : rsList) {
                        if (parentDict.getId().equals(dict.getParentId())) {
                            dict.setDictCde(parentDict.getDictCde() + dict.getDictCde());
                        }
                    }
                }
            }
        }
        rsMap.put("total", index - 1);
        rsMap.put("rsList", rsList);
        return rsMap;
    }

    /**
     * 解析TXT 并插入库
     * @param path
     * @param dictTypeId
     * @throws Exception
     */
    private void analysisTxt(String path, String dictTypeId) throws Exception {

        //解析文本内容
        String contentStr=FileUtil.getFileContent(path);
        String[] rows=contentStr.split("\n");
        Map<Integer, String> map=new HashMap<Integer, String>();
        int oldCountNum =0;
        for (int i = 1; i < rows.length; i++) {
            int countNum = getSubCount(rows[i],"\t");
            if (countNum==0) {
                SysDictionaryData sysDictionaryData = new SysDictionaryData();
                sysDictionaryData.setcIndex(i+"");
                sysDictionaryData.setParentId(SysDictionaryData.ROOT);//最顶级赋值
                sysDictionaryData.setDictTypeId(dictTypeId);
                sysDictionaryData.setLevel(countNum+"");
                dealContend(sysDictionaryData,rows[i]);
                SysDictionaryData newSysDictionaryData = sysDictionaryDataDao.saveAndFlush(sysDictionaryData);
                oldCountNum=countNum;
                map.put(countNum, newSysDictionaryData.getId());
            }else if(countNum>0) {
                if(i==0){
                    throw new Exception("首行不能出现TAB");
                }else {
                    String parentId = map.get(countNum-1);
                    if(StringUtil.isNotEmpty(parentId)){
                        SysDictionaryData sysDictionaryData = new SysDictionaryData();
                        sysDictionaryData.setDictTypeId(dictTypeId);
                        sysDictionaryData.setcIndex(i+"");
                        sysDictionaryData.setParentId(parentId);//最顶级赋值
                        sysDictionaryData.setLevel(countNum+"");
                        dealContend(sysDictionaryData,rows[i]);
                        SysDictionaryData newSysDictionaryData = sysDictionaryDataDao.saveAndFlush(sysDictionaryData);
                        if(oldCountNum != countNum){
                            map.put(countNum, newSysDictionaryData.getId());
                        }
                        oldCountNum=countNum;
                    }else {
                        throw new Exception("数据存在越级请核对！");
                    }
                }
            }
        }
    }

    /**
     * 获取一个字符串在另一个字符串中出现的次数
     * @param str 字符串   String str = "\t\tkkabkkcdkkefkks"; 
     * @param key 要匹配的字串  getSubCount(str,"\t")
     * @return
     */
     public int getSubCount(String str,String key)  
        {  
            int count = 0;  
            int index = 0;  
            while((index= str.indexOf(key,index))!=-1)  
            {  
                index = index + key.length();  
      
                count++;  
            }  
            return count;  
        }
     
     /**
         * 处理空格隔开的内容
         * @param sysDictionaryData
         * @param contentStr
         */
        private void dealContend(SysDictionaryData sysDictionaryData,
                String contentStr) {
            contentStr = contentStr.replace("\t", "");
            String[] contentArr=contentStr.split("\\s+");//1个或多个空格
            for (int i = 0; i < contentArr.length; i++) {
                switch (i) {
                    case 0:
                        sysDictionaryData.setDictCde(contentArr[i]);
                        break;
                    case 1:
                        sysDictionaryData.setDictName(contentArr[i]);
                        break;
                    case 2:
                        sysDictionaryData.setExt1(contentArr[i]);
                        break;
                    case 3:
                        sysDictionaryData.setExt2(contentArr[i]);
                        break;
                    case 4:
                        sysDictionaryData.setExt3(contentArr[i]);
                        break;
                    case 5:
                        sysDictionaryData.setExt4(contentArr[i]);
                        break;
                    case 6:
                        sysDictionaryData.setExt5(contentArr[i]);
                        break;
                    case 7:
                        sysDictionaryData.setExt6(contentArr[i]);
                        break;
                    case 8:
                        sysDictionaryData.setExt7(contentArr[i]);
                        break;
                    case 9:
                        sysDictionaryData.setExt8(contentArr[i]);
                        break;
                    case 10:
                        sysDictionaryData.setExt9(contentArr[i]);
                        break;
                    case 11:
                        sysDictionaryData.setExt10(contentArr[i]);
                        break;
                    case 12:
                        sysDictionaryData.setExt11(contentArr[i]);
                        break;
                    case 13:
                        sysDictionaryData.setExt12(contentArr[i]);
                        break;
                    case 14:
                        sysDictionaryData.setExt13(contentArr[i]);
                        break;
                    case 15:
                        sysDictionaryData.setExt14(contentArr[i]);
                        break;
                    case 16:
                        sysDictionaryData.setExt15(contentArr[i]);
                        break;
                    case 17:
                        sysDictionaryData.setExt16(contentArr[i]);
                        break;
                    case 18:
                        sysDictionaryData.setExt17(contentArr[i]);
                        break;
                    case 19:
                        sysDictionaryData.setExt18(contentArr[i]);
                        break;
                    case 20:
                        sysDictionaryData.setExt19(contentArr[i]);
                        break;
                    case 21:
                        sysDictionaryData.setExt20(contentArr[i]);
                        break;
                    default:
                        break;
                }
            }
        }

    /**
     * 修改字典顺序，上移下移用
     * @param rDictJson
     * @throws Exception
     */
    @Override
    public void updateDictionaryDataToSort(RequestDictJson rDictJson) throws Exception {
        String id = rDictJson.getDictId();
        String toId = rDictJson.getToId();
        SysDictionaryData sysDictionaryData = sysDictionaryDataDao.queryDistinctById(id);
        SysDictionaryData toSysDictionaryData = sysDictionaryDataDao.queryDistinctById(toId);
        String updateUser = UserUtils.getUser().getId();
        Date updateTime = new Date();
        sysDictionaryDataDao.updateSortByIdAndToId(id,toSysDictionaryData.getcIndex(), updateUser, updateTime);
        sysDictionaryDataDao.updateSortByIdAndToId(toId,sysDictionaryData.getcIndex(), updateUser, updateTime);
    }
}
