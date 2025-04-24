package com.rolldata.core.util;

import static com.rolldata.web.system.entity.SysConfig.DEFAULT_JSP_UPDATE_VERSION;
import static com.rolldata.web.system.entity.SysConfig.JSP_UPDATE_VERSION;

/**
 * 为了不暴露过多信息在 JSP 页面上,把方法放这里
 *
 * @Title: JspCodeUtils
 * @Description: JspCodeUtils
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2023-04-14
 * @version: V1.0
 */
public class JspCodeUtils {

    /**
     * jsp 获取缓存中版本
     *
     * @return
     */
    public static String getJspVersion() {

        String jspVersion = (String) CacheUtils.get(JSP_UPDATE_VERSION);
        return StringUtil.isEmpty(jspVersion) ? DEFAULT_JSP_UPDATE_VERSION : jspVersion;
    }
}
