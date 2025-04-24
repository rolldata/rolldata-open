package com.rolldata.web.system.dao;

import com.rolldata.web.system.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 系统配置信息DAO层
 * @Title: SysConfigDao
 * @Description: 系统配置信息DAO层
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018年11月29日
 * @version V1.0
 */
public interface SysConfigDao extends JpaRepository<SysConfig, String>{
	
	/**
	 * 根据类型查询集合
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<SysConfig> querySysConfigByType(String type) throws Exception;
	
	/**
	 * 根据名称查询单个内容
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public SysConfig querySysConfigByName(String name) throws Exception;
	
	/**
	 * 根据名称修改内容值
	 * @param name
	 * @param value
	 * @throws Exception
	 */
	@Modifying(clearAutomatically = true)
	@Query("update SysConfig s set s.value = :value where s.name = :name")
	public void updateValueByName(@Param("name") String name, @Param("value") String value) throws Exception;
	
}
