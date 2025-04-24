package com.rolldata.web.system.service;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.pojo.RequestDictJson;
import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rolldata.web.system.entity.SysDictionaryData;
import com.rolldata.web.system.pojo.ResponseDictJson;

import java.util.Map;

/**
 * 数据字典类型表业务层接口
 * @author shenshilong
 * @createDate 2018-6-11
 */
public interface SysDictionaryDataService {
    
    /**
     * 根据id查询
     * @param id
     * @return
     * @createDate 2018-6-12
     */
    public SysDictionaryData getById(String id);
    
    /**
     * 保存
     *
     * @param ajaxJson
     * @param requestDictJson
     * @throws Exception
     * @createDate 2018-6-11
     */
    public void save (AjaxJson ajaxJson, RequestDictJson requestDictJson) throws Exception;
    
    /**
     * 更新
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-12
     */
    public void update(SysDictionaryData sysDictionaryData) throws Exception;
    
    /**
     * 根据dictTypeId删除全部
     * @param dictTypeId
     * @throws Exception
     * @createDate 2018-6-12
     */
    public void delAllByDictTypeId(String dictTypeId) throws Exception;
    
    /**
     * 删除(根据主键id)
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-11
     */
    public void delete(SysDictionaryData sysDictionaryData) throws Exception;
    
    /**
     * 级联删除
     * @param sysDictionaryData
     * @throws Exception
     * @createDate 2018-6-12
     */
    public void recursiveMethodDel(SysDictionaryData sysDictionaryData) throws Exception;
    
    /**
     * 根据数据字典表主键查询所有
     * @return
     * @throws Exception
     * @createDate 2018-6-12
     */
    //public List<SysDictionaryData> getAllByDictTypeId(String dictTypeId) throws Exception;
    
    /**
     * 获取数据字典内容表新建、编辑返回json
     * @param sysDictionary
     * @throws Exception
     */ 
    public ResponseDictJson getDictData(SysDictionaryData sysDictionaryData) throws Exception;
    
    /**
     * 查询档案内容表中同一目录下代码是否重复
     */
    public boolean isExistByDictCde(String dictCde, String dictTypeId) throws Exception;
    
    /**
     * 查询档案内容表中同一目录下名称是否重复
     */
    public boolean isExistByDictName(String pId, String dictName, String dictTypeId) throws Exception;
    
    /**
     * 修改父ID
     */
    public void updateParentId(String id, String parentId) throws Exception;

    /**
     * 查询档案内容表中同一目录下代码是否重复，排除自己
     */
    public boolean isExistByDictCde(String dictCde, String dictTypeId,
            String dictId) throws Exception;

    /**
     * 查询档案内容表中同一目录下名称是否重复,排除自己
     */
    public boolean isExistByDictName(String pId, String dictName, String dictTypeId,
            String dictId) throws Exception;

    /**
     * 上传文件保存基础档案内容
     *
     * @param ajaxJson
     * @param file
     * @param dictTypeId
     * @throws Exception
     */
    public void uploadContent (AjaxJson ajaxJson, CommonsMultipartFile file, String dictTypeId) throws Exception;
    
    /**
     * 创建基础档案数据时返回code
     * @param ajaxJson 返回前台json
     * @param requestDictJson
     * @throws Exception
     */
    public Map getDictCdeData (AjaxJson ajaxJson, RequestDictJson requestDictJson) throws Exception;
    
    /**
     * 执行导入基础档案
     * @param dictTypeId 字典id
     * @param uuid 执行id
     * @throws Exception
     */
    public void complyImportDict (String dictTypeId, String uuid) throws Exception;
    
    /**
     * 生成模版数据文件及路径
     * @param dictTypeId 基础档案id
     * @return
     * @throws Exception
     */
    public String createTemplate (String dictTypeId) throws Exception;

    /**
     * 查询基础档案数据
     *
     * @param dictId 字典id
     * @return
     * @throws Exception
     */
    List<SysDictionaryData> queryAllByDictId(String dictId) throws Exception;

    /**
     * 修改字典顺序，上移下移用
     * @param rDictJson
     * @throws Exception
     */
    void updateDictionaryDataToSort(RequestDictJson rDictJson) throws Exception;
}
