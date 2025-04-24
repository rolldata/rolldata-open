package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.dao.UserRoleDao;
import com.rolldata.web.system.entity.UserRole;
import com.rolldata.web.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("UserRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	/**
	 * 保存UserRole对象
	 * @param user
	 * @throws Exception
	 */
	public void save(UserRole userRole) throws Exception{
		userRoleDao.save(userRole);
	}
	
	/**
     * 根据userId删除UserRole对象
     * @param user
     * @throws Exception
     */
	public void deleteByUserId(String userId) throws Exception{
		userRoleDao.deleteUserRoleByUserId(userId);
	}
	
	/**
	 * 通过userId获取roleId集合
	 * @author shenshilong
	 * @param userId
	 * @return
	 * @throws Exception
	 * @createDate 2018-7-30
	 */
	public List<String> getByUserId(String userId) throws Exception{
		return userRoleDao.getUserRoleByUserId(userId);
	}
}
