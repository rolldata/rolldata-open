package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.message.MessageHandler;
import com.rolldata.web.system.pojo.MessagePojo;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.service.SysMessageService;
import com.rolldata.web.system.service.SystemService;
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
 * @Title: SysMessageController
 * @Description: SysMessageController
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/9/19
 * @version V1.0  
 */
@Controller
@RequestMapping("/sysMessageController")
@RequiresPathPermission("sys:message")
public class SysMessageController {
    
    private Logger log = LogManager.getLogger(SysMessageController.class);
    
    @Autowired
    private SystemService systemService;
    
    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 消息列表页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/messageeList")
    public ModelAndView messageeList(HttpServletRequest request, HttpServletResponse response) {
    	return new ModelAndView("/tpl/message");
    }
    /**
     * 消息列表页 移动端
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/messageeListByMobile")
    public ModelAndView messageeListByMobile(HttpServletRequest request, HttpServletResponse response) {
    	return new ModelAndView("web/mobile/message_mobile");
    }
    /**
	 * 查询个人所有消息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
    @RequestMapping(value = "/queryPersonalMessage")
	public AjaxJson queryPersonalMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("操作查询个人消息");
		AjaxJson ajaxJson = new AjaxJson();
		String data = request.getParameter("data");
		PageJson pageJson = JsonUtil.fromJson(data, PageJson.class);
		try {
			PageJson returnPage = sysMessageService.queryPersonalMessage(pageJson);
			ajaxJson.setObj(returnPage);
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询个人消息操作失败：" +e.getMessage(),e);
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}

    /**
     * 查询个人所有消息数量
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryPersonalMessageNum")
    public AjaxJson queryPersonalMessageNum(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception{
        log.info("查询个人所有消息数量");
        AjaxJson ajaxJson = new AjaxJson();
        try {

            int num = sysMessageService.queryPersonalMessageNum();
            ajaxJson.setObj(num);
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询个人所有消息数量操作失败：" +e.getMessage(),e);
            systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
	
	/**
	 * 个人清除消息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/deletePersonalMessage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson deletePersonalMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("个人清除消息");
        AjaxJson ajax = new AjaxJson();
        try {
        	sysMessageService.deletePersonalMessage();
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
            log.error("个人清除消息操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
	/**
	 * 新增消息test方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/saveSysMessage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveSysMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("新增消息test方法");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        MessagePojo messagePojo = JsonUtil.fromJson(data, MessagePojo.class);
        try {
        	sysMessageService.saveOneSysMessage(messagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            log.error("新增消息test方法操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
    /**
	 * 新增消息test方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/saveSysMessages", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveSysMessages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("新增消息test方法");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        MessagePojo messagePojo = JsonUtil.fromJson(data, MessagePojo.class);
        try {
        	sysMessageService.saveSysMessages(messagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            log.error("新增消息test方法操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
    
    /**
	 * 标记已读状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/updateSysMessage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateSysMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("标记已读状态");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        MessagePojo messagePojo = JsonUtil.fromJson(data, MessagePojo.class);
        try {
        	sysMessageService.updateSysMessage(messagePojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error"));
            log.error("标记已读状态操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }

    @Autowired
    MessageHandler messageHandler;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson sendMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("标记已读状态");
        AjaxJson ajax = new AjaxJson();
        try {
            String data = request.getParameter("data");
            MessagePojo messagePojo = JsonUtil.fromJson(data, MessagePojo.class);
            MessageHandler.sendMessageToAllUser(messagePojo.getContent());
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error"));
            log.error("新增消息test方法操作失败：" + e.getMessage(),e);
            systemService.addErrorLog(e);
        }
        return ajax;
    }
}
