package com.rolldata.web.system.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rolldata.web.system.entity.SysEmailCode;

/**
 * 
 * @Title: SysEmailCodeDao
 * @Description: 邮箱验证码工厂类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月18日
 * @version V1.0
 */
public interface SysEmailCodeDao extends JpaRepository<SysEmailCode, String>{

	/**
	 * 根据用户代码和邮箱查询历史验证码
	 * @param userCde
	 * @param toAddress
	 * @return
	 * @throws Exception
	 */
	public SysEmailCode querySysEmailCodeByUserCodeAndToAddress(String userCde, String toAddress) throws Exception;

	/**
	 * 根据用户代码和邮箱更新信息
	 * @param userCde
	 * @param toAddress
	 * @param verifyCode
	 * @param createUser
	 * @param createTime
	 */
	@Modifying(clearAutomatically = true)
    @Query("update SysEmailCode s set s.verifyCode = :verifyCode,s.createUser = :createUser,s.createTime = :createTime where s.userCode = :userCde and s.toAddress = :toAddress")
	public void updateSysEmailCode(@Param("userCde")String userCde, @Param("toAddress")String toAddress, @Param("verifyCode")String verifyCode, @Param("createUser")String createUser, @Param("createTime")Date createTime);

}
