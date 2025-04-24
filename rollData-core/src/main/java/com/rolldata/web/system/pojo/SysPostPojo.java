package com.rolldata.web.system.pojo;

/**
 * @Title: SysPostPojo
 * @Description: SysPostPojo
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2019-11-30
 * @version: V1.0
 */
public class SysPostPojo extends EventJson {

    private static final long serialVersionUID = 5063596018302549752L;

    private SysPostDetailedInfo settings;

    /**
     * 获取
     *
     * @return settings
     */
    public SysPostDetailedInfo getSettings() {
        return this.settings;
    }

    /**
     * 设置
     *
     * @param settings
     */
    public void setSettings(SysPostDetailedInfo settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "SysPostPojo{" + "settings=" + settings + '}';
    }
}
