package com.rolldata.web.system.service;

import com.rolldata.web.system.entity.UserRole;

import java.util.List;

/**
 * 用户角色表
 * @author shenshilong
 * @createDate 2018-8-1
 */

public interface UserRoleService {
	
	/**
	 * 保存UserRole对象
	 * @param user
	 * @throws Exception
	 */
	public void save(UserRole userRole) throws Exception;
	
	/**
     * 根据userId删除UserRole对象
     * @param user
     * @throws Exception
     */
	public void deleteByUserId(String userId) throws Exception;
	
	/**
	 * 通过userId获取roleId集合
	 * @author shenshilong
	 * @param userId
	 * @return
	 * @throws Exception
	 * @createDate 2018-7-30
	 */
	public List<String> getByUserId(String userId) throws Exception;
}
