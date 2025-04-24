package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * @Title: FreeLicPojo
 * @Description: 免费版在线授权交互类
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2022-10-18
 * @version: V1.0
 */
public class FreeLicPojo implements Serializable {
    private static final long serialVersionUID = -1811787545431542160L;

    /**注册授权方式，0在线免费注册，1上传文件*/
    private String authType;

    /**单位名称*/
    private String unitName;

    /**姓名称呼*/
    private String userName;

    /**手机号*/
    private String phoneNum;

    /**图片验证码*/
    private String verifyCode;

    /**手机验证码*/
    private String phoneVerifyCode;

    /**缓存中图片验证码*/
    private String cacheVerifyCode;

    /**缓存中图片验证码*/
    private String cachePhoneVerifyCode;

    /**机器码*/
    private String machineCode;

    /**
     * 获取 单位名称
     *
     * @return unitName 单位名称
     */
    public String getUnitName() {
        return this.unitName;
    }

    /**
     * 设置 单位名称
     *
     * @param unitName 单位名称
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * 获取 姓名称呼
     *
     * @return userName 姓名称呼
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 设置 姓名称呼
     *
     * @param userName 姓名称呼
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取 手机号
     *
     * @return phoneNum 手机号
     */
    public String getPhoneNum() {
        return this.phoneNum;
    }

    /**
     * 设置 手机号
     *
     * @param phoneNum 手机号
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * 获取 图片验证码
     *
     * @return verifyCode 图片验证码
     */
    public String getVerifyCode() {
        return this.verifyCode;
    }

    /**
     * 设置 图片验证码
     *
     * @param verifyCode 图片验证码
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * 获取 手机验证码
     *
     * @return phoneVerifyCode 手机验证码
     */
    public String getPhoneVerifyCode() {
        return this.phoneVerifyCode;
    }

    /**
     * 设置 手机验证码
     *
     * @param phoneVerifyCode 手机验证码
     */
    public void setPhoneVerifyCode(String phoneVerifyCode) {
        this.phoneVerifyCode = phoneVerifyCode;
    }

    /**
     * 获取 缓存中图片验证码
     *
     * @return cacheVerifyCode 缓存中图片验证码
     */
    public String getCacheVerifyCode() {
        return this.cacheVerifyCode;
    }

    /**
     * 设置 缓存中图片验证码
     *
     * @param cacheVerifyCode 缓存中图片验证码
     */
    public void setCacheVerifyCode(String cacheVerifyCode) {
        this.cacheVerifyCode = cacheVerifyCode;
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
     * 获取 缓存中图片验证码
     *
     * @return cachePhoneVerifyCode 缓存中图片验证码
     */
    public String getCachePhoneVerifyCode() {
        return this.cachePhoneVerifyCode;
    }

    /**
     * 设置 缓存中图片验证码
     *
     * @param cachePhoneVerifyCode 缓存中图片验证码
     */
    public void setCachePhoneVerifyCode(String cachePhoneVerifyCode) {
        this.cachePhoneVerifyCode = cachePhoneVerifyCode;
    }

    /**
     * 获取 注册授权方式，0在线免费注册，1上传文件
     *
     * @return authType 注册授权方式，0在线免费注册，1上传文件
     */
    public String getAuthType() {
        return this.authType;
    }

    /**
     * 设置 注册授权方式，0在线免费注册，1上传文件
     *
     * @param authType 注册授权方式，0在线免费注册，1上传文件
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
