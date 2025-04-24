package com.rolldata.web.system.service.impl;

import com.rolldata.core.common.exception.WdFileNotFoundException;
import com.rolldata.core.common.pojo.UploadFile;
import com.rolldata.core.util.FileUtil;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.ResourceUtil;
import com.rolldata.web.system.dao.AnnouncementDao;
import com.rolldata.web.system.entity.AnnouncementEntity;
import com.rolldata.web.system.pojo.AnnouncementPojo;
import com.rolldata.web.system.pojo.PageJson;
import com.rolldata.web.system.service.AnnouncementService;
import com.rolldata.web.system.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Title: AnnouncementServiceImpl
 * @Description: 公告管理服务类实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
@Service("announcementService")
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService{

	@Autowired
    private AnnouncementDao announcementDao;
	
	/**
	 * 保存公告信息
	 * @param file
	 * @param announcementPojo
	 * @throws Exception
	 */
	@Override
	public void saveAnnouncementInfo(CommonsMultipartFile file, AnnouncementPojo announcementPojo)
			throws Exception {
		if(announcementPojo.getIsFile().equals("0")) {//默认无附件
			AnnouncementEntity announcement = new AnnouncementEntity();
			announcement.setTitle(announcementPojo.getTitle());
			announcement.setContent(announcementPojo.getContent());
			announcement.setIsFile(announcementPojo.getIsFile());
			announcement.setCreateUser(UserUtils.getUser().getId());
			announcement.setCreateTime(new Date());
			announcementDao.saveAndFlush(announcement);
		}else {
			if (!file.isEmpty()) {
				String originalFilename = file.getOriginalFilename();
				// 当文件超过设置的大小时，则不运行上传
				if (file.getSize() > UploadFile.fileSize) {
					throw new Exception(MessageUtils.getMessageOrSelf("common.sys.file.size.error",UploadFile.sysConfigInfo.getFileSize()));
				}
				int pos = originalFilename.lastIndexOf(".");
				// 获取文件名后缀
				String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
				String fileName = originalFilename.substring(0, pos);
				// 判断该类型的文件是否在允许上传的文件类型内
				if (!Arrays.asList(UploadFile.TypeMap.get("file").split(",")).contains(fileSuffix)
						&& !Arrays.asList(UploadFile.TypeMap.get("image").split(",")).contains(fileSuffix)) {
					throw new Exception(MessageUtils.getMessage("common.sys.file.type.error"));
				}
				File tmpFile = null;
				try {
					// 检查上传文件的目录
					String realPath = ResourceUtil.getUploadFileTempPath();
					FileUtil.mkDir(realPath);
					String timeMillis = System.currentTimeMillis() + "";
			        //临时文件名
			        String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
			        //正式保存的文件名
					fileName = fileName + "." + fileSuffix;
					String path = realPath + tempfileName ;
					tmpFile = new File(path);
					// 通过CommonsMultipartFile的方法直接写文件
					file.transferTo(tmpFile);
						
					FileUtil.copyFile(path, ResourceUtil.getUploadAnnouncementFilePath()+ fileName);
					AnnouncementEntity announcement = new AnnouncementEntity();
					announcement.setTitle(announcementPojo.getTitle());
					announcement.setFileName(fileName);
					announcement.setContent(announcementPojo.getContent());
					announcement.setIsFile(announcementPojo.getIsFile());
					announcement.setCreateUser(UserUtils.getUser().getId());
					announcement.setCreateTime(new Date());
					announcementDao.saveAndFlush(announcement);
				} catch (FileNotFoundException e) {
					throw new WdFileNotFoundException(e);
				} catch (Exception e) {
					throw e;
				}finally {
					// 删除临时文件
					if (tmpFile.exists()) {
						tmpFile.delete();
					}
				}
			}else {
				throw new Exception(MessageUtils.getMessage("common.sys.file.notfound.error"));
			}
		}
	}

	/**
	 * 修改公告信息
	 * @param file
	 * @param announcementPojo
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
	public void updateAnnouncementInfo(CommonsMultipartFile file, AnnouncementPojo announcementPojo)
			throws Exception {
		String isReUpload = announcementPojo.getIsReUpload();
		if(announcementPojo.getIsFile().equals("0")) {//无附件
			AnnouncementEntity oldAnnouncement = announcementDao.queryAnnouncementEntityById(announcementPojo.getId());
			if(oldAnnouncement.getIsFile().equals("1")) {//如果之前有附件先删除
				FileUtil.delFile(ResourceUtil.getUploadAnnouncementFilePath()+ oldAnnouncement.getFileName());
			}
			announcementDao.updateAnnouncementInfo(announcementPojo.getId(),announcementPojo.getTitle(),announcementPojo.getContent(),announcementPojo.getIsFile(),null,UserUtils.getUser().getId(),new Date());
		}else {
			//重新上传文件
			if(isReUpload.equals("1")) {
				if (!file.isEmpty()) {
					String originalFilename = file.getOriginalFilename();
					// 当文件超过设置的大小时，则不运行上传
					if (file.getSize() > UploadFile.fileSize) {
						throw new Exception(MessageUtils.getMessageOrSelf("common.sys.file.size.error",UploadFile.sysConfigInfo.getFileSize()));
					}
					int pos = originalFilename.lastIndexOf(".");
					// 获取文件名后缀
					String fileSuffix = originalFilename.substring(pos + 1).toLowerCase();
					String fileName = originalFilename.substring(0, pos);
					// 判断该类型的文件是否在允许上传的文件类型内
					if (!Arrays.asList(UploadFile.TypeMap.get("file").split(",")).contains(fileSuffix)
							&& !Arrays.asList(UploadFile.TypeMap.get("image").split(",")).contains(fileSuffix)) {
						throw new Exception(MessageUtils.getMessage("common.sys.file.type.error"));
					}
					File tmpFile = null;
					try {
						// 检查上传文件的目录
						String realPath = ResourceUtil.getUploadFileTempPath();
						FileUtil.mkDir(realPath);
				        String timeMillis = System.currentTimeMillis() + "";
				        //临时文件名
				        String tempfileName = fileName + "_" + timeMillis + "." + fileSuffix;
				        //正式保存的文件名
						fileName = fileName + "." + fileSuffix;
						String path = realPath + tempfileName ;
						tmpFile = new File(path);
						// 通过CommonsMultipartFile的方法直接写文件
						file.transferTo(tmpFile);
						//查询旧的对象信息
						AnnouncementEntity oldAnnouncement = announcementDao.queryAnnouncementEntityById(announcementPojo.getId());
						//删除旧的附件
						FileUtil.delFile(ResourceUtil.getUploadAnnouncementFilePath()+ oldAnnouncement.getFileName());
						//复制文件到正式目录
						FileUtil.copyFile(path, ResourceUtil.getUploadAnnouncementFilePath()+ fileName);
						announcementDao.updateAnnouncementInfo(announcementPojo.getId(),announcementPojo.getTitle(),announcementPojo.getContent(),announcementPojo.getIsFile(),fileName,UserUtils.getUser().getId(),new Date());
					} catch (FileNotFoundException e) {
						throw new WdFileNotFoundException(e);
					} catch (Exception e) {
						throw e;
					} finally {
						// 删除临时文件
						if (tmpFile.exists()) {
							tmpFile.delete();
						}
					}
				}else {
					throw new Exception(MessageUtils.getMessage("common.sys.file.notfound.error"));
				}
			}else {
				announcementDao.updateAnnouncementInfo(announcementPojo.getId(),announcementPojo.getTitle(),announcementPojo.getContent(),announcementPojo.getIsFile(),UserUtils.getUser().getId(),new Date());
			}
		}
	}

	/**
	 * 批量删除公告信息
	 * @param announcementPojo
	 * @throws Exception
	 */
	@Override
	public void deleteAnnouncements(AnnouncementPojo announcementPojo) throws Exception {
		List<String> deleteIds = announcementPojo.getIds();
		List<AnnouncementEntity> returnList = announcementDao.queryAnnouncementEntityByIds(deleteIds);
		for (int i = 0; i < returnList.size(); i++) {
			AnnouncementEntity announcementEntity = returnList.get(i);
			if(announcementEntity.getIsFile().equals("1")) {
				FileUtil.delFile(ResourceUtil.getUploadAnnouncementFilePath()+ announcementEntity.getFileName());
			}
			announcementDao.deleteAnnouncementEntityById(announcementEntity.getId());
		}
	}

	/**
	 * 查询公告信息列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<AnnouncementEntity> queryAnnouncementList() throws Exception {
		List<AnnouncementEntity> returnList = announcementDao.queryAnnouncementList();
		return returnList;
	}

	/**
	 * 根据公告id查询文件进行下载
	 * @param annId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String downloadAnnouncementFile(String annId) throws Exception {
		String filePath = "";
		AnnouncementEntity announcementEntity = announcementDao.queryAnnouncementEntityById(annId);
		if(announcementEntity!=null) {
			filePath = ResourceUtil.getUploadAnnouncementFilePath()+ announcementEntity.getFileName();
		}
		return filePath;
	}

	/**
	 * 查询公告信息带分页列表
	 * @param pageJson 
	 * @return
	 * @throws Exception
	 */
	@Override
	public PageJson queryAnnouncementList(PageJson pageJson) throws Exception {
		String search = "%" + pageJson.getSearch() + "%";
		Pageable pageable = PageRequest.of(pageJson.getPageable(), pageJson.getSize());
		Page<AnnouncementEntity> page = announcementDao.queryAnnouncementList(search, search, UserUtils.getUserIdPermissionList(), pageable);
		List<AnnouncementEntity> returnList = new ArrayList<AnnouncementEntity>();
		for(AnnouncementEntity announcementEntity: page.getContent()) {
			returnList.add(announcementEntity);
		}
		pageJson.setTotalElements(page.getTotalElements());
        pageJson.setTotalPagets(page.getTotalPages());
		pageJson.setResult(returnList);
		return pageJson;
	}

	/**
	 * 根据id查询公告内容
	 * @param announcementPojo
	 * @return
	 * @throws Exception
	 */
	@Override
	public AnnouncementEntity queryAnnouncementById(AnnouncementPojo announcementPojo) throws Exception {
		return announcementDao.queryAnnouncementEntityById(announcementPojo.getId());
	}

}
