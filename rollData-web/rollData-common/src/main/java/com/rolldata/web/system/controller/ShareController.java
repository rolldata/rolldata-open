package com.rolldata.web.system.controller;

import com.rolldata.core.common.model.json.AjaxJson;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.web.system.service.ShareService;
import com.rolldata.web.system.service.SystemService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Title: CommonController
 * @Description: 系统共有控制器
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-12-15
 * @version: V1.0
 */
@Controller
@RequestMapping("/shareController")
public class ShareController {

    private final Logger log = LogManager.getLogger(ShareController.class);

    @Autowired
    private SystemService systemService;

    @Autowired
    private ShareService shareService;

    /**
     * 查询系统库所有表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querySysTables", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson querySysTables(HttpServletRequest request, HttpServletResponse response) throws Exception{

        this.log.info("查询系统库所有表");
        AjaxJson ajaxJson = new AjaxJson();
        try {
            ajaxJson.setObj(this.shareService.querySysTables());
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            this.log.error("查询系统库所有表失败：" + e.getMessage());
            this.systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }

    /**
     * 查询系统库表字段集合
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querySysTableColunms", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson querySysTableColunms(HttpServletRequest request, HttpServletResponse response) throws Exception{

        this.log.info("查询系统库表字段集合");
        AjaxJson ajaxJson = new AjaxJson();
        String data = request.getParameter("data");
        try {
            Map map = JsonUtil.getMap4Json(data);
            ajaxJson.setObj(this.shareService.querySysTableColunms((String) map.get("tableName")));
            ajaxJson.setSuccessAndMsg(true, MessageUtils.getMessage("common.sys.query.success"));
        } catch (Exception e) {
            e.printStackTrace();
            this.log.error("查询系统库表字段集合失败：" + e.getMessage());
            this.systemService.addErrorLog(e);
            ajaxJson.setSuccessAndMsg(false, MessageUtils.getMessage("common.sys.query.error"));
        }
        return ajaxJson;
    }
}
