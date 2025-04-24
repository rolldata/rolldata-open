package com.rolldata.web.system.service;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.web.system.pojo.DictTree;
import com.rolldata.web.system.pojo.RequestDictTypeJson;
import com.rolldata.web.system.pojo.ResponseDictJson;
import com.rolldata.web.system.pojo.ResponseDictTypeJson;
import com.rolldata.web.system.pojo.ResponseDictTypeTreeJson;
import com.rolldata.web.system.pojo.SysTableInfo;
import java.util.List;
import java.util.Map;


/**
 * @Title: SysDictionaryService
 * @Description: 数据字典
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-06-11
 * @version V1.0
 */
public interface SysDictionaryService {
    
    /**
     * 保存
     * @param requestDictTypeJson
     * @return
     * @throws Exception
     */
    public ResponseDictTypeJson save(RequestDictTypeJson requestDictTypeJson) throws Exception;
    
    /**
     * 更新
     *
     * @param ajaxJson
     * @param rDictTypeJson
     * @return
     * @throws Exception
     */
    public void update (AjaxJson ajaxJson, RequestDictTypeJson rDictTypeJson) throws Exception;
    
    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void delete(List<String> ids) throws Exception;

    /**
     * 拼接目录管理树返回值对象
     * @param sysDictionary
     * @param dictTypeJson
     * @return
     * @throws Exception
     * @createDate 2018-8-04
     */
    public ResponseDictTypeTreeJson createManageTree(RequestDictTypeJson dictTypeJson) throws Exception;
    
    /**
     * 查询档案目录表中代码是否重复
     */
    public boolean isExistByDictTypeCde(String dictTypeCde) throws Exception;
    
    /**
     * 查询档案目录表中代码是否重复
     */
    public boolean isExistByDictTypeName(String dictTypeName) throws Exception;
    
    /**
     * 查询档案目录表中代码是否重复,排除自己
     */
    public boolean isExistByDictTypeCde(String dictTypeCde, String dictTypeId) throws Exception;
    /**
     * 查询档案目录表中代码是否重复,排除自己
     */
    public boolean isExistByDictTypeName(String dictTypeName, String dictTypeId) throws Exception;

    /**
     * 获取数据内容表类型数据
     * @param dictTypeId
     * @param getpId
     * @return
     * @throws Exception
     */
    public ResponseDictJson getDictDataList(String dictTypeId, String getpId) throws Exception;

    /**
     * 基础档案数据
     *
     * @param dictTypeJson 前台对象
     * @return
     * @throws Exception
     */
    public ResponseDictJson getDictDataList(RequestDictTypeJson dictTypeJson) throws Exception;

    /**
     * 下拉框提供数据
     * @param requestDictJson
     * @return
     * @throws Exception
     */
    public Map getSelectCascadeComponentDict (DictTree dictTree) throws Exception;
    
    /**
     * 下拉树形数据
     * @param dictTree
     * @return
     * @throws Exception
     */
    public Map getSelectComponentDict (DictTree dictTree) throws Exception;
    
    /**
     * 档案编辑详细数据
     * @param dictTypeId
     * @return
     * @throws Exception
     */
    public RequestDictTypeJson queryDictDataById (String dictTypeId) throws Exception;

    /**
     * 预览档案数据
     *
     * @param dictTypeJson 前台JSON对象
     * @return
     * @throws Exception
     */
    Object previewDictData(RequestDictTypeJson dictTypeJson) throws Exception;

    /**
     * 数据档案id
     *
     * @param dictTypeId
     * @return
     * @throws Exception
     */
    List<SysTableInfo> queryDictColumns(String dictTypeId) throws Exception;
}
