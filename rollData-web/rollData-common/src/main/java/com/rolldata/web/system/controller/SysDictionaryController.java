package com.rolldata.web.system.controller;

import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.pojo.DictTree;
import com.rolldata.web.system.pojo.RequestDictTypeJson;
import com.rolldata.web.system.pojo.RequestDictTypeJsonParent;
import com.rolldata.web.system.pojo.ResponseDictJson;
import com.rolldata.web.system.pojo.ResponseDictTypeJson;
import com.rolldata.web.system.pojo.ResponseDictTypeTreeJson;
import com.rolldata.web.system.pojo.SysTableInfo;
import com.rolldata.web.system.service.SysDictionaryService;
import com.rolldata.web.system.service.SystemService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 数据字典内容表控制器
 * @author shenshilong
 * @createDate 2018-6-14
 */
@Controller
@RequestMapping(value = "/sysDictionaryController")
@RequiresPathPermission("sys:dictionary")
public class SysDictionaryController {
    
    private Logger log = LogManager.getLogger(SysDictionaryController.class);
    
    @Autowired
    private SysDictionaryService sysDictionaryService;
    
    @Autowired
    private SystemService systemService;
    /**
     * 跳转控制台档案管理页面
     * @param  request
     * @param  response
     * @returns ModelAndView
     */
    @RequestMapping(value = "/dictManage")
    @RequiresMethodPermissions(value = "dictManage")
    public ModelAndView dictManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/web/system/dictList");
    }

    /**
     * 跳转门户档案维护页面
     * @param  request
     * @param  response
     * @returns ModelAndView
     */
    @RequestMapping(value = "/portalDictManage")
    @RequiresMethodPermissions(value = "portalDictManage")
    public ModelAndView portalDictManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/portal/system/dictManage");
    }

    /**
     * 查询当前对象对应的数据字典内容表信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @createDate 2018-6-12
     */
    @RequestMapping(value = "/dictTypeDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson dictTypeDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("查询当前对象对应的数据字典内容表信息");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJson rDictTypeJson = JsonUtil.fromJson(data, RequestDictTypeJson.class);
            ResponseDictJson rDictJson = sysDictionaryService.getDictDataList(rDictTypeJson);
            ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(rDictJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询当前对象对应的数据字典内容表信息操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, "字典" + MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 保存
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @createDate 2018-6-13
     */
    @RequiresMethodPermissions(value = "save")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson save(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("保存基础档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJsonParent rDictTypeJsonParent = JsonUtil.fromJson(data, RequestDictTypeJsonParent.class);
            RequestDictTypeJson rDictTypeJson = rDictTypeJsonParent.getSettings();
            if (EventType.CREATE_MD.toString().equals(rDictTypeJsonParent.getEvent())) {
                if(sysDictionaryService.isExistByDictTypeCde(rDictTypeJson.getDictTypeCde())){  //基础档案目录代码重复
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictTypeJson.getDictTypeCde()));
                    return ajaxJson;
                }
                if(sysDictionaryService.isExistByDictTypeName(rDictTypeJson.getDictTypeName())){  //基础档案目录名称重复
                    ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictTypeJson.getDictTypeName()));
                    return ajaxJson;
                }
                ResponseDictTypeJson responseDictTypeJson = sysDictionaryService.save(rDictTypeJson);
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.save.success"));
                ajaxJson.setObj(responseDictTypeJson);
            }else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存基础档案内容操作失败：" +e.getMessage(),e);
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
     * @createDate 2018-6-13
     */
    @RequiresMethodPermissions(value = "update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson update(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.info("更新基础档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJsonParent rDictTypeJsonParent = JsonUtil.fromJson(data, RequestDictTypeJsonParent.class);
            RequestDictTypeJson rDictTypeJson = rDictTypeJsonParent.getSettings();
            if (EventType.UPDATE_MD.toString().equals(rDictTypeJsonParent.getEvent())) {
                if(rDictTypeJson.getDictTypeId() != null && !"".equals(rDictTypeJson.getDictTypeId())){
                    if(sysDictionaryService.isExistByDictTypeCde(rDictTypeJson.getDictTypeCde(),rDictTypeJson.getDictTypeId())){  //基础档案目录代码重复
                        ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictTypeJson.getDictTypeCde()));
                        return ajaxJson;
                    }
                    if(sysDictionaryService.isExistByDictTypeName(rDictTypeJson.getDictTypeName(),rDictTypeJson.getDictTypeId())){  //基础档案目录名称重复
                        ajaxJson.setSuccessAndMsg(false,MessageUtils.getMessageOrSelf("common.sys.code.exis", rDictTypeJson.getDictTypeName()));
                        return ajaxJson;
                    }
                    sysDictionaryService.update(ajaxJson, rDictTypeJson);
                }else {
                    ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
                }
            } else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新基础档案内容操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 查询所有数据字典类型表数据
     * @param request
     * @param response
     * @return
     * @createDate 2018-6-13
     */
    @RequestMapping(value = "/dictTreeList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getAllList(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("查询所有数据字典类型表数据");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJson dictTypeJson = JsonUtil.fromJson(data,RequestDictTypeJson.class);
            ResponseDictTypeTreeJson  responseDictTypeTreeJson = sysDictionaryService.createManageTree(dictTypeJson);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
            ajaxJson.setObj(responseDictTypeTreeJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询所有数据字典类型表数据操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 删除
     * @param request
     * @param response
     * @throws Exception
     * @createDate 2018-6-13
     */
    @RequiresMethodPermissions(value = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson delDictionary(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("删除基本档案内容");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJsonParent rDictTypeJsonParent = JsonUtil.fromJson(data, RequestDictTypeJsonParent.class);
            RequestDictTypeJson rDictTypeJson = rDictTypeJsonParent.getSettings();
            if (EventType.DELETE_MD.toString().equals(rDictTypeJsonParent.getEvent())) {
                sysDictionaryService.delete(rDictTypeJson.getIds());
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
            } else {
                ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除基本档案内容操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 为表单里配置的下拉框提供数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSelectCascadeComponentDict", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getSelectCascadeComponentDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.info("查询下拉框提供数据");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        DictTree dictTree = JsonUtil.fromJson(data, DictTree.class);
        try {
            ajaxJson.setObj(sysDictionaryService.getSelectCascadeComponentDict(dictTree));
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询下拉框提供数据操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 查询下拉框提供数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSelectComponentDict", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getSelectComponentDict(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.info("查询下拉框提供数据");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        DictTree dictTree = JsonUtil.fromJson(data, DictTree.class);
        try {
            ajaxJson.setObj(sysDictionaryService.getSelectComponentDict(dictTree));
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询下拉框提供数据操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 编辑基础档案详细数据
     * @param request
     * @param response
     * @return
     * @createDate 2018-6-13
     */
    @RequestMapping(value = "/queryDictDataById", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryDictDataById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        log.info("获取编辑基础档案详细数据");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJsonParent rDictTypeJsonParent = JsonUtil.fromJson(data, RequestDictTypeJsonParent.class);
            RequestDictTypeJson rDictTypeJson = rDictTypeJsonParent.getSettings();
            if (EventType.QUERY_MD.toString().equals(rDictTypeJsonParent.getEvent())) {
                ajaxJson.setObj(sysDictionaryService.queryDictDataById(rDictTypeJson.getDictTypeId()));
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            } else {
                ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取编辑基础档案详细数据失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }

    /**
     * 预览档案数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/previewDictData", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson previewDictData(HttpServletRequest request, HttpServletResponse response) throws Exception{

        log.info("预览档案数据");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestDictTypeJson dictTypeJson = JsonUtil.fromJson(data, RequestDictTypeJson.class);
            ajaxJson.setObj(this.sysDictionaryService.previewDictData(dictTypeJson));
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("预览档案数据失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error") + ":" + e.getMessage());
        }
        return ajaxJson;
    }

    /**
     * 查询数据档案可过滤字段
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryDictColumns", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryDictColumns(HttpServletRequest request, HttpServletResponse response) throws Exception {

        this.log.info("查询数据档案可过滤字段");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        DictTree dictTree = JsonUtil.fromJson(data, DictTree.class);
        try {
            List<SysTableInfo> dictColumns = this.sysDictionaryService.queryDictColumns(dictTree.getDictTypeId());
            ajaxJson.setObj(dictColumns);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            this.log.error("查询数据档案可过滤字段失败：" + e.getMessage(),e);
            this.systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
}
