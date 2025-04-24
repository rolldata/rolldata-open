package com.rolldata.core.util;

/** 
 * @Title: AdministratorUtils
 * @Description: 系统默认管理员工具类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018/8/29
 * @version V1.0  
 */
public class AdministratorUtils {
    
    private final static String administrator = SysPropertiesUtil.getConfig("Administrator");
    
    private final static String adminRole = SysPropertiesUtil.getConfig("adminRole");

    private final static String isStartTimerHandle = SysPropertiesUtil.getConfig("isStartTimerHandle");

    private final static String isUserSinglePoint = SysPropertiesUtil.getConfig("isUserSinglePoint");

    /**
     * 获取系统配置好的超级管理员用户名
     * @return
     */
    public static String getAdministratorName() {
        return administrator;
    }
    
    /**
     * 获取系统配置的超级管理员角色代码
     * @return
     */
    public static String getAdminRoleCde() {
        return adminRole;
    }

    public static Boolean getStartTimerHandle () {
        return Boolean.parseBoolean(isStartTimerHandle);
    }

    /**
     * 获取是否开启用户账号单点登录
     *
     * @return
     */
    public static Boolean getUserSinglePoint() {
        return Boolean.parseBoolean(isUserSinglePoint);
    }
}
