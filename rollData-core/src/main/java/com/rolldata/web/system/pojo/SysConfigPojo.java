package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 
 * @Title: SysConfigPojo
 * @Description: 系统配置信息
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年10月7日
 * @version V1.0
 */
public class SysConfigPojo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3810457574847652771L;

	/**上传文件大小，单位兆*/
	private String fileSize = "10";
	
	/**上传图片大小，单位兆*/
	private String imgSize = "5";
	
//	/**上传图片最大高度，单位px*/
//	private String imgMaxHight = "250";
//	
//	/**上传图片最小高度，单位px*/
//	private String imgMinHight = "200";
//	
//	/**上传图片最大宽度，单位px*/
//	private String imgMaxWidth = "250";
//	
//	/**上传图片最小宽度，单位px*/
//	private String imgMinWidth = "200";

	/**系统默认版本号，升级或覆盖*/
	private String versionNum = "v3.6.0";

	/**发布版本的时间*/
	private String releaseTime = "2022-07-15";

	/**上次更新时间*/
	private String lastUpdateTime;

	/**填报任务计划任务配置*/
	private String fillTaskCron;

	/**ETL调度任务计划任务配置*/
	private String dpTaskCron;

	/**预警调度任务计划任务配置*/
	private String warnTaskCron;

	/**预警发送消息调度计划任务配置*/
	private String sendMesTaskCron;

	/**财务数据抽取计划任务配置*/
	private String synAccountDataCron;

	/**第三方接口数据抽取计划任务配置*/
	private String thirdpartyInterfaceDataCron;

	/**指标计算计划任务配置*/
	private String calculateItemDispatchCron;

	/**
	 * 获取上传文件大小，单位兆  
	 * @return fileSize 上传文件大小，单位兆  
	 */
	public String getFileSize() {
		return fileSize;
	}
	
	/**  
	 * 设置上传文件大小，单位兆  
	 * @param fileSize 上传文件大小，单位兆  
	 */
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**  
	 * 获取上传图片大小，单位兆  
	 * @return imgSize 上传图片大小，单位兆  
	 */
	public String getImgSize() {
		return imgSize;
	}

	/**  
	 * 设置上传图片大小，单位兆  
	 * @param imgSize 上传图片大小，单位兆  
	 */
	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * 获取 系统默认版本号，升级或覆盖
	 *
	 * @return versionNum 系统默认版本号，升级或覆盖
	 */
	public String getVersionNum() {
		return this.versionNum;
	}

	/**
	 * 设置 系统默认版本号，升级或覆盖
	 *
	 * @param versionNum 系统默认版本号，升级或覆盖
	 */
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	/**
	 * 获取 发布版本的时间
	 *
	 * @return releaseTime 发布版本的时间
	 */
	public String getReleaseTime() {
		return this.releaseTime;
	}

	/**
	 * 设置 发布版本的时间
	 *
	 * @param releaseTime 发布版本的时间
	 */
	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	/**
	 * 获取 上次更新时间
	 *
	 * @return lastUpdateTime 上次更新时间
	 */
	public String getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	/**
	 * 设置 上次更新时间
	 *
	 * @param lastUpdateTime 上次更新时间
	 */
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * 获取 填报任务计划任务配置
	 *
	 * @return fillTaskCron 填报任务计划任务配置
	 */
	public String getFillTaskCron() {
		return this.fillTaskCron;
	}

	/**
	 * 设置 填报任务计划任务配置
	 *
	 * @param fillTaskCron 填报任务计划任务配置
	 */
	public void setFillTaskCron(String fillTaskCron) {
		this.fillTaskCron = fillTaskCron;
	}

	/**
	 * 获取 ETL调度任务计划任务配置
	 *
	 * @return dpTaskCron ETL调度任务计划任务配置
	 */
	public String getDpTaskCron() {
		return this.dpTaskCron;
	}

	/**
	 * 设置 ETL调度任务计划任务配置
	 *
	 * @param dpTaskCron ETL调度任务计划任务配置
	 */
	public void setDpTaskCron(String dpTaskCron) {
		this.dpTaskCron = dpTaskCron;
	}

	/**
	 * 获取 预警调度任务计划任务配置
	 *
	 * @return warnTaskCron 预警调度任务计划任务配置
	 */
	public String getWarnTaskCron() {
		return this.warnTaskCron;
	}

	/**
	 * 设置 预警调度任务计划任务配置
	 *
	 * @param warnTaskCron 预警调度任务计划任务配置
	 */
	public void setWarnTaskCron(String warnTaskCron) {
		this.warnTaskCron = warnTaskCron;
	}

	/**
	 * 获取 预警发送消息调度计划任务配置
	 *
	 * @return sendMesTaskCron 预警发送消息调度计划任务配置
	 */
	public String getSendMesTaskCron() {
		return this.sendMesTaskCron;
	}

	/**
	 * 设置 预警发送消息调度计划任务配置
	 *
	 * @param sendMesTaskCron 预警发送消息调度计划任务配置
	 */
	public void setSendMesTaskCron(String sendMesTaskCron) {
		this.sendMesTaskCron = sendMesTaskCron;
	}

	/**
	 * 获取 财务数据抽取计划任务配置
	 *
	 * @return synAccountDataCron 财务数据抽取计划任务配置
	 */
	public String getSynAccountDataCron() {
		return this.synAccountDataCron;
	}

	/**
	 * 设置 财务数据抽取计划任务配置
	 *
	 * @param synAccountDataCron 财务数据抽取计划任务配置
	 */
	public void setSynAccountDataCron(String synAccountDataCron) {
		this.synAccountDataCron = synAccountDataCron;
	}

	/**
	 * 获取 第三方接口数据抽取计划任务配置
	 *
	 * @return thirdpartyInterfaceDataCron 第三方接口数据抽取计划任务配置
	 */
	public String getThirdpartyInterfaceDataCron() {
		return this.thirdpartyInterfaceDataCron;
	}

	/**
	 * 设置 第三方接口数据抽取计划任务配置
	 *
	 * @param thirdpartyInterfaceDataCron 第三方接口数据抽取计划任务配置
	 */
	public void setThirdpartyInterfaceDataCron(String thirdpartyInterfaceDataCron) {
		this.thirdpartyInterfaceDataCron = thirdpartyInterfaceDataCron;
	}

	/**
	 * 获取 指标计算计划任务配置
	 *
	 * @return calculateItemDispatchCron 指标计算计划任务配置
	 */
	public String getCalculateItemDispatchCron() {
		return this.calculateItemDispatchCron;
	}

	/**
	 * 设置 指标计算计划任务配置
	 *
	 * @param calculateItemDispatchCron 指标计算计划任务配置
	 */
	public void setCalculateItemDispatchCron(String calculateItemDispatchCron) {
		this.calculateItemDispatchCron = calculateItemDispatchCron;
	}

//	/**
//	 * 获取上传图片最大高度，单位px  
//	 * @return imgMaxHight 上传图片最大高度，单位px  
//	 */
//	public String getImgMaxHight() {
//		return imgMaxHight;
//	}
//
//	/**  
//	 * 设置上传图片最大高度，单位px  
//	 * @param imgMaxHight 上传图片最大高度，单位px  
//	 */
//	public void setImgMaxHight(String imgMaxHight) {
//		this.imgMaxHight = imgMaxHight;
//	}
//	
//	/**  
//	 * 获取上传图片最小高度，单位px  
//	 * @return imgMixHight 上传图片最小高度，单位px  
//	 */
//	public String getImgMinHight() {
//		return imgMinHight;
//	}
//
//	/**  
//	 * 设置上传图片最小高度，单位px  
//	 * @param imgMixHight 上传图片最小高度，单位px  
//	 */
//	public void setImgMinHight(String imgMinHight) {
//		this.imgMinHight = imgMinHight;
//	}
//	
//	/**  
//	 * 获取上传图片最大宽度，单位px  
//	 * @return imgMaxWidth 上传图片最大宽度，单位px  
//	 */
//	public String getImgMaxWidth() {
//		return imgMaxWidth;
//	}
//
//	/**  
//	 * 设置上传图片最大宽度，单位px  
//	 * @param imgMaxWidth 上传图片最大宽度，单位px  
//	 */
//	public void setImgMaxWidth(String imgMaxWidth) {
//		this.imgMaxWidth = imgMaxWidth;
//	}
//	
//	/**  
//	 * 获取上传图片最小宽度，单位px  
//	 * @return imgMinWidth 上传图片最小宽度，单位px  
//	 */
//	public String getImgMinWidth() {
//		return imgMinWidth;
//	}
//	
//	/**  
//	 * 设置上传图片最小宽度，单位px  
//	 * @param imgMinWidth 上传图片最小宽度，单位px  
//	 */
//	public void setImgMinWidth(String imgMinWidth) {
//		this.imgMinWidth = imgMinWidth;
//	}
	
}
