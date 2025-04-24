package com.rolldata.web.system.pojo;

/**
 * @Title: SysPlugLicPojo
 * @Description: 组件授权信息交互类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2024-03-15
 * @version: V1.0
 */
public class SysPlugLicPojo implements java.io.Serializable{

    /**组件id*/
    private String plugId;

    /**机器码*/
    private String machineCode;

    /**授权对象，客户简称*/
    private String authorizeObject;

    /**组件版本（试用版1，正式版2）*/
    private String modelVersion;

    /**开始时间*/
    private String startTime;

    /**结束时间*/
    private String endTime;

    /**版本信息*/
    private String versionNum;

    /**发布时间*/
    private String releaseTime;

    /**授权机 机器码*/
    private String serverMachinecode;

    /**
     * 获取 组件id
     *
     * @return plugId 组件id
     */
    public String getPlugId() {
        return this.plugId;
    }

    /**
     * 设置 组件id
     *
     * @param plugId 组件id
     */
    public void setPlugId(String plugId) {
        this.plugId = plugId;
    }

    /**
     * 获取 机器码
     *
     * @return machineCode 机器码
     */
    public String getMachineCode() {
        return this.machineCode;
    }

    /**
     * 设置 机器码
     *
     * @param machineCode 机器码
     */
    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    /**
     * 获取 授权对象，客户简称
     *
     * @return authorizeObject 授权对象，客户简称
     */
    public String getAuthorizeObject() {
        return this.authorizeObject;
    }

    /**
     * 设置 授权对象，客户简称
     *
     * @param authorizeObject 授权对象，客户简称
     */
    public void setAuthorizeObject(String authorizeObject) {
        this.authorizeObject = authorizeObject;
    }

    /**
     * 获取 组件版本（试用版1，正式版2）
     *
     * @return modelVersion 组件版本（试用版1，正式版2）
     */
    public String getModelVersion() {
        return this.modelVersion;
    }

    /**
     * 设置 组件版本（试用版1，正式版2）
     *
     * @param modelVersion 组件版本（试用版1，正式版2）
     */
    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    /**
     * 获取 开始时间
     *
     * @return startTime 开始时间
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * 设置 开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取 结束时间
     *
     * @return endTime 结束时间
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * 设置 结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取 版本信息
     *
     * @return versionNum 版本信息
     */
    public String getVersionNum() {
        return this.versionNum;
    }

    /**
     * 设置 版本信息
     *
     * @param versionNum 版本信息
     */
    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * 获取 发布时间
     *
     * @return releaseTime 发布时间
     */
    public String getReleaseTime() {
        return this.releaseTime;
    }

    /**
     * 设置 发布时间
     *
     * @param releaseTime 发布时间
     */
    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 获取 授权机 机器码
     *
     * @return serverMachinecode 授权机 机器码
     */
    public String getServerMachinecode() {
        return this.serverMachinecode;
    }

    /**
     * 设置 授权机 机器码
     *
     * @param serverMachinecode 授权机 机器码
     */
    public void setServerMachinecode(String serverMachinecode) {
        this.serverMachinecode = serverMachinecode;
    }
}
