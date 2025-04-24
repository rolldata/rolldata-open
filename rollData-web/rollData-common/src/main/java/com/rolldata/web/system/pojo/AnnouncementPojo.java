package com.rolldata.web.system.pojo;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rolldata.web.system.entity.AnnouncementEntity;

/**
 * 
 * @Title: AnnouncementPojo
 * @Description: 公告管理详细信息
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
public class AnnouncementPojo extends AnnouncementEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1158692750844610632L;
	
	/**
	 * 上传附件
	 */
	private CommonsMultipartFile uploadFile;
	
	/**
	 * id集合
	 */
	private List<String> ids;
	
	/**
	 * 是否重新上传，0否，1是
	 */
	private String isReUpload;

	/**  
	 * 获取上传附件  
	 * @return uploadFile 上传附件  
	 */
	public CommonsMultipartFile getUploadFile() {
		return uploadFile;
	}

	/**  
	 * 设置上传附件  
	 * @param uploadFile 上传附件  
	 */
	public void setUploadFile(CommonsMultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	/**  
	 * 获取id集合  
	 * @return ids id集合  
	 */
	public List<String> getIds() {
		return ids;
	}
	
	/**  
	 * 设置id集合  
	 * @param ids id集合  
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	/**  
	 * 获取是否重新上传，0否，1是  
	 * @return isReUpload 是否重新上传，0否，1是  
	 */
	public String getIsReUpload() {
		return isReUpload;
	}
	
	/**  
	 * 设置是否重新上传，0否，1是  
	 * @param isReUpload 是否重新上传，0否，1是  
	 */
	public void setIsReUpload(String isReUpload) {
		this.isReUpload = isReUpload;
	}
	
	
}
