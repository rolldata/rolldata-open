package com.rolldata.web.system.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rolldata.web.system.entity.AnnouncementEntity;
import com.rolldata.web.system.pojo.AnnouncementPojo;
import com.rolldata.web.system.pojo.PageJson;

/**
 * 
 * @Title: AnnouncementService
 * @Description: 公告管理服务类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
public interface AnnouncementService {

	/**
	 * 保存公告信息
	 * @param uploadFile
	 * @param announcementPojo
	 * @throws Exception
	 */
	public void saveAnnouncementInfo(CommonsMultipartFile uploadFile, AnnouncementPojo announcementPojo) throws Exception;

	/**
	 * 修改公告信息
	 * @param uploadFile
	 * @param announcementPojo
	 * @throws Exception
	 */
	public void updateAnnouncementInfo(CommonsMultipartFile uploadFile, AnnouncementPojo announcementPojo) throws Exception;

	/**
	 * 批量删除公告信息
	 * @param announcementPojo
	 * @throws Exception
	 */
	public void deleteAnnouncements(AnnouncementPojo announcementPojo) throws Exception;

	/**
	 * 查询公告信息列表
	 * @return
	 * @throws Exception
	 */
	public List<AnnouncementEntity> queryAnnouncementList() throws Exception;
	
	/**
	 * 查询公告信息带分页列表
	 * @param pageJson 
	 * @return
	 * @throws Exception
	 */
	public PageJson queryAnnouncementList(PageJson pageJson) throws Exception;

	/**
	 * 根据公告id查询文件进行下载
	 * @param annId
	 * @return
	 * @throws Exception
	 */
	public String downloadAnnouncementFile(String annId) throws Exception;

	/**
	 * 根据id查询公告内容
	 * @param announcementPojo
	 * @return
	 * @throws Exception
	 */
	public AnnouncementEntity queryAnnouncementById(AnnouncementPojo announcementPojo) throws Exception;
}
