package com.rolldata.web.system.pojo;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rolldata.web.system.entity.BiHomeConfig;

/**
 * 
 * @Title: BiHomePagePojo
 * @Description: BI首页配置详细
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月8日
 * @version V1.0
 */
public class BiHomePagePojo extends BiHomeConfig{

	public BiHomePagePojo(String id, String name, String resourceId, String imgName, String createUser,
			String resourceType, String resourceName,String resourceUrl,String relationId,String imgUrl) {
		super(id, name, resourceId, imgName, createUser);
		this.resourceType = resourceType;
		this.resourceName = resourceName;
		this.resourceUrl = resourceUrl;
		this.relationId = relationId;
		super.setImgUrl(imgUrl);
	}

	public BiHomePagePojo() {
		super();
	}

	public BiHomePagePojo(String id) {
		super(id);
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8826548493817813889L;

	/**
	 * 上传图片
	 */
	private CommonsMultipartFile uploadImgFile;
	
	/**
	 * id集合
	 */
	private List<String> ids;
	
	/**
	 * 是否重新上传，0否，1是
	 */
	private String isReUpload;
	
	/**
	 * 交换至新位置的id
	 */
	private String toId;
	
	/**
	 * 资源类型
	 */
	private String resourceType;
	
	/**
	 * 资源名称
	 */
	private String resourceName;
	
	/**
	 * 资源路径
	 */
	private String resourceUrl;
	
	/**
	 * 关联的报表驾驶舱等id
	 */
	private String relationId;

	/**  
	 * 获取上传图片  
	 * @return uploadImgFile 上传图片  
	 */
	public CommonsMultipartFile getUploadImgFile() {
		return uploadImgFile;
	}
	
	/**  
	 * 设置上传图片  
	 * @param uploadImgFile 上传图片  
	 */
	public void setUploadImgFile(CommonsMultipartFile uploadImgFile) {
		this.uploadImgFile = uploadImgFile;
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

	/**  
	 * 获取交换至新位置的id  
	 * @return toId 交换至新位置的id  
	 */
	public String getToId() {
		return toId;
	}
	
	/**  
	 * 设置交换至新位置的id  
	 * @param toId 交换至新位置的id  
	 */
	public void setToId(String toId) {
		this.toId = toId;
	}

	/**  
	 * 获取资源类型  
	 * @return resourceType 资源类型  
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**  
	 * 设置资源类型  
	 * @param resourceType 资源类型  
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**  
	 * 获取资源名称  
	 * @return resourceName 资源名称  
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**  
	 * 设置资源名称  
	 * @param resourceName 资源名称  
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**  
	 * 获取资源路径  
	 * @return resourceUrl 资源路径  
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}
	
	/**  
	 * 设置资源路径  
	 * @param resourceUrl 资源路径  
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	/**  
	 * 获取关联的报表驾驶舱等id  
	 * @return relationId 关联的报表驾驶舱等id  
	 */
	public String getRelationId() {
		return relationId;
	}

	/**  
	 * 设置关联的报表驾驶舱等id  
	 * @param relationId 关联的报表驾驶舱等id  
	 */
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	
}
