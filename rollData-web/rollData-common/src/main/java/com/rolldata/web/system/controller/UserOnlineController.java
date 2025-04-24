package com.rolldata.web.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.DateUtils;
import com.rolldata.web.system.entity.SysUserOnline;
import com.rolldata.web.system.security.session.OnlineSession;
import com.rolldata.web.system.security.session.OnlineSessionDAO;
import com.rolldata.web.system.service.UserOnlineService;

/**
 * 
 * @Title: UserOnlineController
 * @Description: 在线用户
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/userOnlineController")
public class UserOnlineController {

	@Autowired
	private UserOnlineService userOnlineService;
	@Autowired
	private OnlineSessionDAO onlineSessionDAO;

	public UserOnlineController() {
	}

	@RequestMapping("/forceLogout")
	@ResponseBody
	public AjaxJson forceLogout(@RequestParam(value = "ids") String[] ids) {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setMsg("强制退出成功");
		try {
			for (String id : ids) {
				SysUserOnline online = new SysUserOnline();
					online = userOnlineService.getUserOnline(id);
				
				if (online == null) {
					continue;
				}
				OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getId());
				if (onlineSession == null) {
					continue;
				}
				onlineSession.setStatus(OnlineSession.OnlineStatus.force_logout);
				online.setStatus(OnlineSession.OnlineStatus.force_logout);
				userOnlineService.update(online);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxJson;
	}
	@RequestMapping("/queryOnlineUser")
	@ResponseBody
	public AjaxJson queryOnlineUser( ) {
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setMsg("查询成功");
		try {
			Page<SysUserOnline> page = userOnlineService.findExpiredUserOnlineList(DateUtils.getDate(), 0, 100);
			ajaxJson.setObj(page);
		} catch (Exception e) {
			ajaxJson.setMsg("查询失败");
			e.printStackTrace();
		}
		return ajaxJson;
	}
}
