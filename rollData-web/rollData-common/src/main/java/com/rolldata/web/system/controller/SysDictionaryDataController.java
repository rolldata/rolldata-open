package com.rolldata.web.system.controller;


import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.common.pojo.UploadFile;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.FileUtil;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.SysDictionaryData;
import com.rolldata.web.system.pojo.RequestDictJson;
import com.rolldata.web.system.pojo.RequestDictJsonParent;
import com.rolldata.web.system.pojo.ResponseDictJson;
import com.rolldata.web.system.service.SysDictionaryDataService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.DownloadUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sysDictionaryDataController")
@RequiresPathPermission("sys:dictionary:data")
public class SysDictionaryDataController {
    private Logger log = LogManager.getLogger(SysDictionaryDataController.class);
    
    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;
    
    @Autowired
    private SystemService systemService;
    /**
     * 根据id查询SysDictionaryData对象
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @createDate 2018-6-12
     */
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("根据id查询基础数据内容单个对象");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        SysDictionaryData sysDictionaryData = JsonUtil.fromJson(data, SysDictionaryData.class);
        SysDictionaryData obj = null;
        try {
            obj =  sysDictionaryDataService.getById(sysDictionaryData.getId());
            ajaxJson.setSuccessAndMsg(true,  MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(obj);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据id查询基础数据内容单个对象操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 保存
     * @param request
     * @param response
     * @return
     * @createDate 2018-6-12
     */
    @RequiresMethodPermissions(value = "save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("保存档案内容");
        String data = request.getParameter("data");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            RequestDictJsonParent rJsonParent = JsonUtil.fromJson(data, RequestDictJsonParent.class);
            RequestDictJson rDictJson = rJsonParent.getSettings();
            if(EventType.CREATE_MD.toString().equals(rJsonParent.getEvent())){
    
                //代码重复
                /*if(sysDictionaryDataService.isExistByDictCde(rDictJson.getDictCde(), rDictJson.getDictTypeId())){
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictJson.getDictCde()));
                    return ajaxJson;
                }*/
    
                //名称重复
                boolean existByDictName = sysDictionaryDataService.isExistByDictName(
                    rDictJson.getpId(),
                    rDictJson.getDictName(),
                    rDictJson.getDictTypeId()
                );
                if(existByDictName){
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictJson.getDictName()));
                    return ajaxJson;
                }
                sysDictionaryDataService.save(ajaxJson, rDictJson);
            } else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存档案内容操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 更新
     * @param request
     * @param response
     * @return
     * @createDate 2018-6-12
     */
    @RequiresMethodPermissions(value = "update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("更新档案内容");
        String data = request.getParameter("data");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            RequestDictJsonParent rJsonParent = JsonUtil.fromJson(data, RequestDictJsonParent.class);
            RequestDictJson rDictJson = rJsonParent.getSettings();
            if(EventType.UPDATE_MD.toString().equals(rJsonParent.getEvent())){
                /*if(sysDictionaryDataService.isExistByDictCde(rDictJson.getDictCde(), rDictJson.getDictTypeId(),rDictJson.getDictId())){  //代码重复
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictJson.getDictCde()));
                    return ajaxJson;
                }*/
                boolean existByDictName = sysDictionaryDataService.isExistByDictName(
                    rDictJson.getpId(),
                    rDictJson.getDictName(),
                    rDictJson.getDictTypeId(),
                    rDictJson.getDictId()
                );
                if(existByDictName){  //名称重复
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictJson.getDictName()));
                    return ajaxJson;
                }
                SysDictionaryData objData = new SysDictionaryData();
                objData.setId(rDictJson.getDictId());
                objData.setDictCde(rDictJson.getDictCde());
                objData.setDictName(rDictJson.getDictName());
                objData.setDictTypeId(rDictJson.getDictTypeId());
                objData.setParentId(rDictJson.getpId());
                objData.setExt1(rDictJson.getExt1());
                objData.setExt2(rDictJson.getExt2());
                objData.setExt3(rDictJson.getExt3());
                objData.setExt4(rDictJson.getExt4());
                objData.setExt5(rDictJson.getExt5());
                objData.setExt6(rDictJson.getExt6());
                objData.setExt7(rDictJson.getExt7());
                objData.setExt8(rDictJson.getExt8());
                objData.setExt9(rDictJson.getExt9());
                objData.setExt10(rDictJson.getExt10());
                objData.setExt11(rDictJson.getExt11());
                objData.setExt12(rDictJson.getExt12());
                objData.setExt13(rDictJson.getExt13());
                objData.setExt14(rDictJson.getExt14());
                objData.setExt15(rDictJson.getExt15());
                objData.setExt16(rDictJson.getExt17());
                objData.setExt17(rDictJson.getExt17());
                objData.setExt18(rDictJson.getExt18());
                objData.setExt19(rDictJson.getExt19());
                objData.setExt20(rDictJson.getExt20());
                sysDictionaryDataService.update(objData);
                ResponseDictJson responseDictJson = sysDictionaryDataService.getDictData(objData);
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
                ajaxJson.setObj(responseDictJson);
            }else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新档案内容操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 删除
     * @return
     * @throws Exception
     * @createDate 2018-6-14
     */
    @RequiresMethodPermissions(value = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("删除档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictJsonParent rJsonParent = JsonUtil.fromJson(data, RequestDictJsonParent.class);
            RequestDictJson rDictJson = rJsonParent.getSettings();
            if (EventType.DELETE_MD.toString().equals(rJsonParent.getEvent())) {
                List<String> list = rDictJson.getIds();
                for (String id : list) {
                    SysDictionaryData sysDictionaryData = new SysDictionaryData();
                    sysDictionaryData.setId(id);
                    sysDictionaryDataService.recursiveMethodDel(sysDictionaryData);
                }
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
            }else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除档案内容操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 拖拽
     * @return
     * @throws Exception
     * @createDate 2018-6-14
     */
    @RequestMapping(value = "/drag", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson drag(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("拖拽档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictJson rDictJson = JsonUtil.fromJson(data, RequestDictJson.class);
            List<String> list = rDictJson.getIds();
            for (String id : list) {
                sysDictionaryDataService.updateParentId(id, rDictJson.getpId());
            }
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.drag.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("拖拽档案内容操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.drag.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 下载基础档案模版及数据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
    @ResponseBody
    public void downloadTemplate (HttpServletRequest request, HttpServletResponse response) throws Exception {


        String downloadType = request.getParameter("downloadType");
        String dictTypeId = request.getParameter("dictTypeId");
        String suff = "dict.xlsx";
        if (UploadFile.DICT_TXT.equalsIgnoreCase(downloadType)) {
            suff = "示例_导入地区.txt";
        }
        //TODO 待整理  抽成公共的
//        String filePath = ResourceUtil.getTemplatePath() + suff;
        String filePath = "";
        log.info("下载模板");
        try {
            
            //生成临时模版数据文件
            filePath = sysDictionaryDataService.createTemplate(dictTypeId);
            DownloadUtil.downloadFile(filePath,  response);
            FileUtil.deletefile(filePath);
        } catch (Exception e) {
            FileUtil.deletefile(filePath);
            e.printStackTrace();
            log.error("下载模板操作失败：{}", e.getMessage());
            systemService.addErrorLog(e);
        }
    }

    /**
     * 基础档案导入
     *
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "uploadFile")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson uploadFile(@RequestParam("importExcel") CommonsMultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("上传文件");
        AjaxJson ajaxJson = new AjaxJson();
        String dictTypeId = request.getParameter("dictTypeId");
        try{
            sysDictionaryDataService.uploadContent(ajaxJson, file, dictTypeId);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.file.upload.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.file.upload.error"));
        } 
        return ajaxJson;
    }

    /**
     * 执行插入数据基础档案的导入
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "uploadFile")
    @RequestMapping(value = "/complyImportDict", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson complyImportDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.info("执行基础档案导入");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        Map map = JsonUtil.fromJson(data, Map.class);
        try{
            sysDictionaryDataService.complyImportDict((String) map.get("dictTypeId"), (String) map.get("_uuid"));
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.file.upload.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行基础档案导入失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.file.upload.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 创建基础档案数据时返回code
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDictCdeData", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getDictCdeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        log.info("创建基础档案数据时返回code");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictJsonParent rJsonParent = JsonUtil.fromJson(data, RequestDictJsonParent.class);
            RequestDictJson rDictJson = rJsonParent.getSettings();
            sysDictionaryDataService.getDictCdeData(ajaxJson, rDictJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建基础档案数据时返回code失败：" + e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }

    /**
     * 移动，上移下移
     * @return
     * @throws Exception
     * @createDate 2018-6-14
     */
    @RequestMapping(value = "/exchangeOrder", method = RequestMethod.POST)
    @RequiresMethodPermissions(value = "exchangeOrder")
    @ResponseBody
    public AjaxJson exchangeOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("移动档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictJson rDictJson = JsonUtil.fromJson(data, RequestDictJson.class);
            sysDictionaryDataService.updateDictionaryDataToSort(rDictJson);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("移动档案内容操作失败：" +e.getMessage());
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
        }
        return ajaxJson;
    }
}
