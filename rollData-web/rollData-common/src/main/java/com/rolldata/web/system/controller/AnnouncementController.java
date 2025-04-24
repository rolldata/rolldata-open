package com.rolldata.web.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.security.annotation.RequiresMethodPermissions;
import com.rolldata.core.security.annotation.RequiresPathPermission;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.entity.AnnouncementEntity;
import com.rolldata.web.system.pojo.AnnouncementPojo;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.service.AnnouncementService;
import com.rolldata.web.system.service.SystemService;
import com.rolldata.web.system.util.DownloadUtil;
/**
 * @Title: AnnouncementController
 * @Description: 公告管理控制器
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2022-11-04
 * @version: V1.0
 */
@Controller
@RequestMapping("/announcementController")
@RequiresPathPermission("sys:announcement")
public class AnnouncementController {
	
	private Logger log = LogManager.getLogger(AnnouncementController.class);
    
    @Autowired
    private SystemService systemService;
    
    @Autowired
    private AnnouncementService announcementService;
    /**
     * 跳转公告管理页面
     * @param  request
     * @param  response
     * @returns ModelAndView
     */
    @RequestMapping(value = "/announcementManage")
    @RequiresMethodPermissions(value = "announcementManage")
    public ModelAndView announcementManage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/web/system/announcementManage");
    }

	/**
	 * 门户打开公告页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/openAnnouncement")
	public ModelAndView openAnnouncement(HttpServletRequest request, HttpServletResponse response) {
		String announcementId =  request.getParameter("announcementId");
		Map<String, String> map = new HashMap<String, String>();
		map.put("announcementId", announcementId);
		return new ModelAndView("/portal/system/announcement",map);
	}
    
    /**
	 * 保存公告信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "saveAnnouncementInfo")
    @RequestMapping(value = "/saveAnnouncementInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveAnnouncementInfo(@RequestParam("uploadFile")CommonsMultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("保存公告信息");
		AjaxJson ajax = new AjaxJson();
        try {
        	String title = request.getParameter("title");
        	String content = request.getParameter("content");
        	AnnouncementPojo announcementPojo = new AnnouncementPojo();  
        	announcementPojo.setTitle(title);
        	announcementPojo.setContent(content);
        	announcementPojo.setIsFile(request.getParameter("isFile"));
        	announcementService.saveAnnouncementInfo(uploadFile,announcementPojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.save.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.save.error")+":"+e.getMessage());
            log.error("保存公告信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	/**
	 * 修改公告信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "updateAnnouncementInfo")
    @RequestMapping(value = "/updateAnnouncementInfo", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson updateAnnouncementInfo(@RequestParam("uploadFile")CommonsMultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("修改公告信息");
		AjaxJson ajax = new AjaxJson();
        try {
        	String title = request.getParameter("title");
        	String content = request.getParameter("content");
        	AnnouncementPojo announcementPojo = new AnnouncementPojo();  
        	announcementPojo.setId(request.getParameter("annId"));
        	announcementPojo.setTitle(title);
        	announcementPojo.setContent(content);
        	announcementPojo.setIsFile(request.getParameter("isFile"));
        	announcementPojo.setIsReUpload(request.getParameter("isReUpload"));
        	announcementService.updateAnnouncementInfo(uploadFile,announcementPojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.update.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.update.error")+":"+e.getMessage());
            log.error("修改公告信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	/**
	 * 批量删除公告信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequiresMethodPermissions(value = "deleteAnnouncements")
    @RequestMapping(value = "/deleteAnnouncements", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson deleteAnnouncements(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("批量删除公告信息");
        AjaxJson ajax = new AjaxJson();
        String data = request.getParameter("data");
        AnnouncementPojo announcementPojo = JsonUtil.fromJson(data, AnnouncementPojo.class);
        try {
        	announcementService.deleteAnnouncements(announcementPojo);
            ajax.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.delete.success"));
        } catch (Exception e) {
            e.printStackTrace();
            ajax.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.delete.error"));
            log.error("批量删除公告信息操作失败：" + e.getMessage());
            systemService.addErrorLog(e);
        }
        return ajax;
    }
	
	 /**
		 * 查询公告信息列表
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
	    @RequestMapping(value = "/queryAnnouncementList")
		public AjaxJson queryAnnouncementList(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			log.info("操作查询公告信息列表");
			AjaxJson ajaxJson = new AjaxJson();
			try {
				List<AnnouncementEntity> returnList = announcementService.queryAnnouncementList();
				ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
				ajaxJson.setObj(returnList);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("查询公告信息列表操作失败：" +e.getMessage());
				systemService.addErrorLog(e);
				ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
			}
			return ajaxJson;
		}
		
		 /**
		 * 查询公告信息列表
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
	    @RequestMapping(value = "/queryAnnouncementListByPage")
		public AjaxJson queryAnnouncementListByPage(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			log.info("操作查询公告信息列表");
			AjaxJson ajaxJson = new AjaxJson();
			String data = request.getParameter("data");
			PageJson pageJson = JsonUtil.fromJson(data, PageJson.class);
			try {
				PageJson returnPageJson = announcementService.queryAnnouncementList(pageJson);
				ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
				ajaxJson.setObj(returnPageJson);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("查询公告信息列表操作失败：" +e.getMessage());
				systemService.addErrorLog(e);
				ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
			}
			return ajaxJson;
		}
		/**
		 * 下载公告附件
		 * 
		 * @param file
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/downloadAnnouncementFile")
		@ResponseBody
		public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
			log.info("下载公告附件");
			String annId = request.getParameter("annId");
			try {
				String filePath = announcementService.downloadAnnouncementFile(annId);
				DownloadUtil.downloadFile(filePath, response);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("下载文件操作失败：" + e.getMessage());
				systemService.addErrorLog(e);
			} 
		}

	/**
	 * 查询单个公告内容
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAnnouncementById")
	public AjaxJson queryAnnouncementById(HttpServletRequest request,
										  HttpServletResponse response) throws Exception{
		log.info("操作查询单个公告内容");
		AjaxJson ajaxJson = new AjaxJson();
		String data = request.getParameter("data");
		try {
			AnnouncementPojo announcementPojo = JsonUtil.fromJson(data,AnnouncementPojo.class);
			AnnouncementEntity result = announcementService.queryAnnouncementById(announcementPojo);
			ajaxJson.setSuccessAndMsg(true,MessageUtils.getMessage("common.sys.query.success"));
			ajaxJson.setObj(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询单个公告内容操作失败：" +e.getMessage());
			systemService.addErrorLog(e);
			ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
		}
		return ajaxJson;
	}
}
