package com.rolldata.web.system.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rolldata.web.system.entity.AnnouncementEntity;

/**
 * 
 * @Title: AnnouncementDao
 * @Description: 公告管理工厂类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月10日
 * @version V1.0
 */
public interface AnnouncementDao  extends JpaRepository<AnnouncementEntity, String>{

	@Modifying(clearAutomatically = true)
    @Query("update AnnouncementEntity s set s.title = :title,s.content = :content,s.isFile = :isFile,s.createUser = :createUser,s.createTime = :createTime where s.id = :id")
	public void updateAnnouncementInfo(@Param("id")String id, @Param("title")String title, @Param("content")String content, @Param("isFile")String isFile,  @Param("createUser")String createUser, @Param("createTime")Date createTime) throws Exception;

	@Modifying(clearAutomatically = true)
    @Query("update AnnouncementEntity s set s.title = :title,s.content = :content,s.isFile = :isFile,s.fileName = :fileName,s.createUser = :createUser,s.createTime = :createTime where s.id = :id")
	public void updateAnnouncementInfo(@Param("id")String id, @Param("title")String title, @Param("content")String content, @Param("isFile")String isFile, @Param("fileName")String fileName,
			 @Param("createUser")String createUser, @Param("createTime")Date createTime) throws Exception;

	public AnnouncementEntity queryAnnouncementEntityById(String id) throws Exception;

	@Query("select s from AnnouncementEntity s where s.id in (:ids)")
	public List<AnnouncementEntity> queryAnnouncementEntityByIds(@Param("ids")List<String> deleteIds) throws Exception;

	@Query("select s from AnnouncementEntity s order by s.createTime desc")
	public List<AnnouncementEntity> queryAnnouncementList() throws Exception;

	@Query(value="select new com.rolldata.web.system.entity.AnnouncementEntity(s.id,s.title,s.content,s.isFile,s.fileName,su.userName as createUser,s.createTime) from AnnouncementEntity s left join SysUser su on s.createUser = su.id where (s.title like :search or s.content like :search2) and s.createUser in (:userIds) order by s.createTime desc",
			countQuery="select count(s) from AnnouncementEntity s left join SysUser su on s.createUser = su.id where (s.title like :search or s.content like :search2) and s.createUser in (:userIds) ")
	public Page<AnnouncementEntity> queryAnnouncementList (@Param("search") String search, @Param("search2") String search2, @Param("userIds") List<String> userIds, Pageable pageable);

	@Modifying(clearAutomatically = true)
    @Query("delete from AnnouncementEntity s where s.id = :id")
	public void deleteAnnouncementEntityById(@Param("id")String id) throws Exception;

}
