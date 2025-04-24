package com.rolldata.web.system.controller;

import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.SysOrg;
import com.rolldata.web.system.pojo.*;
import com.rolldata.web.system.service.OrgService;
import com.rolldata.web.system.service.SysUserOrgService;
import com.rolldata.web.system.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: OrgController
 * @Description: 组织机构控制器
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018-05-25
 * @version V1.0
 */
@Controller
@RequestMapping("/orgController")
@RequiresPathPermission("sys:org")
public class OrgController {
    private Logger log = LogManager.getLogger(OrgController.class);
    
    @Autowired
    private OrgService orgService;
    
    @Autowired
    private SystemService systemService;

    @Autowired
    private SysUserOrgService sysUserOrgService;
    
    /**
     * 添加页面
     * @param  request
     * @param  response
     * @returns ModelAndView
     */
    @RequestMapping(value = "/orgManage")
    @RequiresMethodPermissions(value = "orgManage")
    public ModelAndView addOrg(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("web/system/orgList");
    }

    /**
     * 保存(单条SysOrg)
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "saveOrg")
    @RequestMapping(value = "/saveOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("保存组织");
        AjaxJson ajax = new AjaxJson();
        int count = 0;
        String data = request.getParameter("data");
        RequestOrgJsonParent rOrgJsonParent = JsonUtil.fromJson(data, RequestOrgJsonParent.class);
        try {
            RequestOrgJson rOrgJson = rOrgJsonParent.getSettings();
            if (EventType.CREATE_MD.toString().equals(rOrgJsonParent.getEvent())) {

                // 名称,类型为1(公司)的不能重复
                count = SysOrg.TYPE_COMPANY.equals(rOrgJson.getOrgType()) ? orgService.checkName(rOrgJson.getOrgName())
                                          : 0;
                if (count > 0) {
                    ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.name.repeat",
                                                                               rOrgJson.getOrgName()));
                    return ajax;
                }

                // 编码,类型为1(公司)的不能重复
                count = SysOrg.TYPE_COMPANY.equals(rOrgJson.getOrgType()) ?
                    orgService.checkCode(rOrgJson.getOrgCde()) : 0;
                if (count > 0) {
                    ajax.setSuccessAndMsg(
                        false,
                        MessageUtils.getMessageOrSelf("common.sys.code.repeat", rOrgJson.getOrgCde())
                    );
                    return ajax;
                }
                ResponseOrgJson responseOrgJson = orgService.save(rOrgJson);
                this.sysUserOrgService.saveAdmin();
                ajax.setSuccessAndMsg(true, MessageUtils.getMessageOrSelf("common.sys.save.success"));
                ajax.setObj(responseOrgJson);
            } else {
                ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.error"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存组织操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
        }
        return ajax;
    }

    /**
     * 更新(单条SysOrg)
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "updateOrg")
    @RequestMapping(value = "/updateOrg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("修改组织");
        AjaxJson ajax = new AjaxJson();
        int count = 0;
        try {
            String data = request.getParameter("data");
            RequestOrgJsonParent rOrgJsonParent = JsonUtil.fromJson(data, RequestOrgJsonParent.class);
            RequestOrgJson rOrgJson = rOrgJsonParent.getSettings();
            ResponseOrgJson responseOrgJson = new ResponseOrgJson();
            if (EventType.UPDATE_MD.toString().equals(rOrgJsonParent.getEvent())) {

                // 名称,类型为1(公司)的不能重复
                count = SysOrg.TYPE_COMPANY.equals(rOrgJson.getOrgType()) ? orgService
                                          .checkName(rOrgJson.getOrgId(), rOrgJson.getOrgName()) : 0;
                if (count > 0) {
                    ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.name.repeat",
                                                                               rOrgJson.getOrgName()));
                    return ajax;
                }

                // 编码,类型为1(公司)的不能重复
                count = SysOrg.TYPE_COMPANY.equals(rOrgJson.getOrgType()) ?
                    orgService.checkCode(rOrgJson.getOrgId(), rOrgJson.getOrgCde()) : 0;
                if (count > 0) {
                    ajax.setSuccessAndMsg(
                        false,
                        MessageUtils.getMessageOrSelf("common.sys.code.repeat", rOrgJson.getOrgCde())
                    );
                    return ajax;
                }
                responseOrgJson = orgService.update(rOrgJson);
                if (SysOrg.TYPE_COMPANY.equals(rOrgJson.getOrgType())) {
                    this.sysUserOrgService.updateOrgNameByOrgId(
                        rOrgJson.getOrgId(),
                        rOrgJson.getOrgName(),
                        rOrgJson.getOrgCde()
                    );
                } else {
                    this.sysUserOrgService.updateOrgNameByDepartmentId(
                        rOrgJson.getOrgId(),
                        rOrgJson.getOrgName(),
                        rOrgJson.getOrgCde()
                    );
                }
                ajax.setSuccessAndMsg(true, MessageUtils.getMessageOrSelf("common.sys.update.success"));
            } else {
                ajax.setSuccessAndMsg(false, MessageUtils.getMessageOrSelf("common.sys.error"));
            }
            ajax.setObj(responseOrgJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改组织操作失败：{}", e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error") + ":" + e.getMessage());
        }
        return ajax;
    }
    
    /**
     * 删除(可以有子菜单)功能菜单信息 
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "deleteOrgs")
    @RequestMapping(value = "/deleteOrgs", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson deleteOrgs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("删除组织");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        try {
            RequestOrgJsonParent rOrgJsonParent = JsonUtil.fromJson(data, RequestOrgJsonParent.class);
            RequestOrgJson rOrgJson = rOrgJsonParent.getSettings();
            List<String> orgIdList = rOrgJson.getIds();
            if(EventType.DELETE_MD.toString().equals(rOrgJsonParent.getEvent())){
                List<String> delIds = new ArrayList<>();
                for (String id : orgIdList) {
                    SysOrg sysOrg = this.orgService.getSysOrgById(id);
                    if (null == sysOrg) {
                        continue;
                    }
                    this.orgService.deleteRecursiveMethod(id, delIds, sysOrg.getType());
                    if (SysOrg.TYPE_COMPANY.equals(sysOrg.getType())) {
                        this.sysUserOrgService.deleteEntitysByOrgIds(delIds);
                    } else {
                        this.sysUserOrgService.deleteEntitysByDepartmentIds(delIds);
                    }
                }
                ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
            }else{
                ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.error"));
            }
            //ajax.setObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除组织操作失败：{}", e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error") + ":" + e.getMessage());
        }
        return ajax;
    }

    /**
     * 组织机构列表(tree)
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgTreeList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getOrgTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询组织机构列表");
        AjaxJson ajax = new AjaxJson();
        try {
            List<RoleOrgList> roleOrgLists = orgService.orgTreeList();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
            ajax.setObj(roleOrgLists);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构列表操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 组织机构树，组织部门一起，无数据权限版
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgTreeListNoDataPermission", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson getOrgTreeListNoDataPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询组织机构列表");
        AjaxJson ajax = new AjaxJson();
        try {
            List<RoleOrgList> roleOrgLists = orgService.orgTreeListNoDataPermission();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.success"));
            ajax.setObj(roleOrgLists);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构列表操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 组织机构权限配置树
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgLimitTreeList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson orgLimitTreeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("查询组织机构权限配置树");
        AjaxJson ajax = new AjaxJson();
        String data =  request.getParameter("data");
        Map map4Json = JsonUtil.getMap4Json(data);
        try {
            OrgTreeParent orgTreeParent = orgService.orgLimitTreeList((String) map4Json.get("dataOwn"));
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(orgTreeParent);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构权限配置树操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }
    
    /**
     * 根节点详情
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgRootDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson orgRootDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("查询根节点详情");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String data =  request.getParameter("data");
            RequestOrgJson rOrgJson = JsonUtil.fromJson(data, RequestOrgJson.class);
            OrgDetailedJson orgDetailedJson = orgService.getOrgDetailed(rOrgJson.getOrgId());
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(orgDetailedJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询根节点详情操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 文件夹节点详情
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgFolderDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson orgFolderDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("查询文件夹节点详情");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String data =  request.getParameter("data");
            RequestOrgJson rOrgJson = JsonUtil.fromJson(data, RequestOrgJson.class);
            OrgDetailedJson orgDetailedJson = orgService.getOrgDetailed(rOrgJson.getOrgId());
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(orgDetailedJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询文件夹节点详情操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    /**
     * 组织节点详情
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/orgDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson orgDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("查询组织节点详情");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            String data =  request.getParameter("data");
            RequestOrgJson rOrgJson = JsonUtil.fromJson(data, RequestOrgJson.class);
            OrgDetailedJson orgDetailedJson = orgService.getOrgDetailed(rOrgJson.getOrgId());
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(orgDetailedJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织节点详情操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
    
    
    /**
     * 根据ID查询获得组织对象
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @createDate 2018-6-7
     */
    @RequestMapping(value = "/querySysOrgById", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson querySysOrgById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("根据ID查询获得组织对象");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        SysOrg sysOrg = JsonUtil.fromJson(data, SysOrg.class);
        try {
            SysOrg backSysOrg = orgService.getSysOrgById(sysOrg.getId());
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajaxJson.setObj(backSysOrg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("根据ID查询获得组织对象操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }

    /**
     * 角色配置组织权限的公司树
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryRoleCompanyTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryRoleCompanyTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("查询组织机构的公司树");
        AjaxJson ajax = new AjaxJson();
        try {
            OrgTreeParent orgTreeParent = orgService.queryRoleCompanyTree();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(orgTreeParent);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构的公司树失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 角色配置组织权限的部门列表
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryRoleDepartments", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryRoleDepartments(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("查询组织机构的部门列表");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysOrg sysOrg = JsonUtil.fromJson(data, SysOrg.class);
        try {
            List<OrgTree> roleOrgLists = orgService.queryRoleDepartments(sysOrg.getParentId());
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(roleOrgLists);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构的部门列表失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 查询公司树，所有公司类型，无数据权限
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryCompanyTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryRoleCompanyTreeNoDataPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("查询组织机构的公司树");
        AjaxJson ajax = new AjaxJson();
        try {
            OrgTreeParent orgTreeParent = orgService.queryCompanyTreeNoDataPermission();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(orgTreeParent);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询组织机构的公司树失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }

    /**
     * 查询公司部门用户树
     *
     * @param request
     * @param response
     * @return AjaxJson
     * @throws Exception
     */
    @RequestMapping(value = "/queryOrgUserTree", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryOrgUserTree(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("查询公司部门用户树");
        AjaxJson ajax = new AjaxJson();
        try {
            OrgTreeParent orgTreeParent = orgService.queryOrgUserTree();
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(orgTreeParent);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询公司部门用户树失败：{}", e.getMessage(),e);
            systemService.addErrorLog(e);
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajax;
    }
}
