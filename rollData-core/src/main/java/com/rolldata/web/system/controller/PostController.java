package com.rolldata.web.system.controller;

import com.rolldata.core.common.enums.EventType;
import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.AllUserListParent;
import com.rolldata.web.system.pojo.SysPostPojo;
import com.rolldata.web.system.service.PostService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: PostController
 * @Description: 职务控制器
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-11-30
 * @version: V1.0
 */
@Controller
@RequestMapping("/postController")
@RequiresPathPermission("sys:post")
public class PostController {

    private Logger log = LogManager.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private UserService userService;

    /**
     * 创建职务
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "createPost")
    @RequestMapping(value = "/createPost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson createPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysPostPojo postPojo = JsonUtil.fromJson(data, SysPostPojo.class);
        try {
            if (EventType.CREATE_MD.toString().equals(postPojo.getEvent())) {
                postService.before(ajax, postPojo.getSettings().getPostName(), postPojo.getSettings().getPostCode(),
                                   postPojo.getSettings().getPostId());
                if (ajax.isSuccess()) {
                    log.info("创建职务");
                    ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.create.success"));
                    ajax.setObj(postService.createPost(postPojo.getSettings()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.create.error"));
            log.error("操作创建职务失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 修改职务
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "updatePost")
    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updatePost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysPostPojo sysPostPojo = JsonUtil.fromJson(data, SysPostPojo.class);
        try {
            if (EventType.UPDATE_MD.toString().equals(sysPostPojo.getEvent())) {
                postService.before(ajax, sysPostPojo.getSettings().getPostName(),
                                   sysPostPojo.getSettings().getPostCode(), sysPostPojo.getSettings().getPostId());
                if (ajax.isSuccess()) {
                    log.info("修改职务");
                    ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.update.success"));
                    ajax.setObj(postService.updatePost(sysPostPojo.getSettings()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
            log.error("操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 删除职务
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequiresMethodPermissions(value = "deletePost")
    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson deletePost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        SysPostPojo sysPostPojo = JsonUtil.fromJson(data, SysPostPojo.class);
        try {
            if (EventType.DELETE_MD.toString().equals(sysPostPojo.getEvent())) {
                postService.delete(sysPostPojo.getSettings().getIds());
                ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.delete.success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
            log.error("删除失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 职务详细
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryPostDetailed", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryPostDetailed(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        Map map = JsonUtil.getMap4Json(data);
        try {
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(postService.queryPostDetailedInfo(String.valueOf(map.get("postId"))));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            log.error("查询失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 查询全部职务
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryPostList", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryPostList(HttpServletRequest request, HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        try {
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            ajax.setObj(postService.queryPostList());
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            log.error("查询失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    /**
     * 查询职务用户列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserPostInfoByOrgId", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson queryUserPostInfoByOrgId(HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {

        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        AllUserListParent fromJson = JsonUtil.fromJson(data, AllUserListParent.class);
        try {
            log.info("查询发布配置组织下的职务和用户列表");
            ajax.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
            Map map = new HashMap();
            List<SysUser> sysUsers = userService.queryUsersByOrgIdAndType(fromJson.getOrgId(), fromJson.getType());
            map.put("users", sysUsers);
//            map.put("posts", postService.queryUserPosts(sysUsers));
            ajax.setObj(map);
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
            log.error("查询失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
}
