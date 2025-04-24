package com.rolldata.web.system.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rolldata.web.system.entity.BiHomeConfig;
import com.rolldata.web.system.pojo.BiHomePagePojo;

/**
 * 
 * @Title: BiHomeConfigDao
 * @Description: BI首页配置信息DAO层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月9日
 * @version V1.0
 */
public interface BiHomeConfigDao extends JpaRepository<BiHomeConfig, String>{

	/**
	 * 查询排序最大值
	 * @return
	 * @throws Exception
	 */
	@Query("select MAX(cast(s.sort as int)) from BiHomeConfig s")
	public String queryMaxSort() throws Exception;

	/**
	 * 根据名称查询集合
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<BiHomeConfig> queryBiHomeConfigByName(String name) throws Exception;


	/**
	 * 根据id集合查询列表
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Query("select s from BiHomeConfig s where s.id in (:ids)")
	public List<BiHomeConfig> queryBiHomeConfigByIds(@Param("ids")List<String> ids) throws Exception;

	/**
	 * 根据名称查询集合，排除自己
	 * @param name
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Query("select s from BiHomeConfig s where s.name = :name and s.id <> :id")
	public List<BiHomeConfig> queryBiHomeConfigByNameAndNotId(@Param("name")String name, @Param("id")String id) throws Exception;

	/**
	 * 更新图片名等对象信息
	 * @param id
	 * @param name
	 * @param imgName
	 * @param imgUrl
	 * @param resourceId
	 * @param userId
	 * @param date
	 */
	@Modifying(clearAutomatically = true)
    @Query("update BiHomeConfig s set s.name = :name,s.imgName = :imgName,s.imgUrl = :imgUrl,s.resourceId = :resourceId,s.updateUser = :updateUser,s.updateTime = :updateDate where s.id = :id")
	public void updateBiHomeConfigInfo( @Param("id")String id, @Param("name")String name, @Param("imgName")String imgName, @Param("imgUrl")String imgUrl, @Param("resourceId")String resourceId, @Param("updateUser")String userId,
			@Param("updateDate")Date date) throws Exception;
	
	/**
	 * 更新除图片名称外其他信息
	 * @param id
	 * @param name
	 * @param resourceId
	 * @param userId
	 * @param date
	 */
	@Modifying(clearAutomatically = true)
    @Query("update BiHomeConfig s set s.name = :name,s.resourceId = :resourceId,s.updateUser = :updateUser,s.updateTime = :updateDate where s.id = :id")
	public void updateBiHomeConfigInfo(@Param("id")String id,  @Param("name")String name, @Param("resourceId")String resourceId, @Param("updateUser")String userId,@Param("updateDate")Date date) throws Exception;

	/**
	 * 根据id查询详细
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BiHomeConfig queryBiHomePagePojoById(String id) throws Exception;

	/**
	 * 交换顺序
	 * @param id
	 * @param sort
	 * @throws Exception
	 */
	@Modifying(clearAutomatically = true)
    @Query("update BiHomeConfig s set s.sort = :sort,s.updateUser = :updateUser,s.updateTime = :updateDate where s.id = :id")
	public void updateBiHomeConfigInfo(@Param("id")String id, @Param("sort")String sort,@Param("updateUser")String userId,@Param("updateDate")Date date) throws Exception;

	/**
	 * 根据id删除
	 * @param id
	 * @throws Exception
	 */
	@Modifying(clearAutomatically = true)
    @Query("delete from BiHomeConfig s where s.id = :id")
	public void deleteBiHomeConfigById(@Param("id")String id) throws Exception;

}
