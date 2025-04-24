package com.rolldata.web.system.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rolldata.core.util.CacheUtils;
import com.rolldata.core.util.MessageUtils;
import com.rolldata.core.util.StringUtil;
import com.rolldata.web.system.dao.SysEmailCodeDao;
import com.rolldata.web.system.dao.UserDao;
import com.rolldata.web.system.entity.SysEmailCode;
import com.rolldata.web.system.entity.SysUser;
import com.rolldata.web.system.pojo.EmailCodePojo;
import com.rolldata.web.system.pojo.MailInfo;
import com.rolldata.web.system.service.PasswordBackService;
import com.rolldata.web.system.service.UserService;
import com.rolldata.web.system.util.email.EmailResult;
import com.rolldata.web.system.util.email.EmailUtil;

/**
 * 
 * @Title: PasswordBackServiceImpl
 * @Description: 密码找回服务类实现
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2019年5月18日
 * @version V1.0
 */
@Service("passwordBackService")
@Transactional
public class PasswordBackServiceImpl implements PasswordBackService{

	 @Autowired
	 private UserDao userDao;
	 
	 @Autowired
	 private SysEmailCodeDao sysEmailCodeDao;
	 
	 @Autowired
	 private UserService userService;
	 
	/**
	 * 发送邮箱验证码并保存
	 * @param emailCodePojo
	 */
	@Override
	public void sendEmailVerifyCode(EmailCodePojo emailCodePojo) throws Exception{
		String randomStr = StringUtil.random(6);
//		randomStr = GenericsUtils.getPasswords(6);
		String userCde = emailCodePojo.getUserCode();
		String toAddress = emailCodePojo.getToAddress();
		//根据登录名查询用户，判断邮箱是否存在并一致
		SysUser user = userDao.getUserByUserCde(userCde);
		if (null == user) {
            throw new Exception(MessageUtils.getMessage("common.sys.login.nouser"));
        }
        toAddress = user.getMail();
		MailInfo mailInfo = new MailInfo();
		String content = "<html lang=\"en\">" + 
				"<head>" + 
				"    <meta charset=\"UTF-8\">" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + 
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">" + 
				"    <title>密码找回</title>" +
				"    <style>" + 
				"    </style>" + 
				"</head>" + 
				"<body style=\"margin: 0;padding:0;width:100%;height:100%;background: #f1f1f1\">" + 
				"    <div class=\"big\" style=\"width: 100%;height: 500px;padding: 0;margin: 0;box-sizing: border-box;padding-top: 40px;background: #f1f1f1;padding-bottom: 220px;\">" + 
				"        <div class=\"title\" style=\"margin:0 5%;padding:7px 20px;width: 80%;height:25px;line-height:25px;background: #009688;" + 
				"        color:#fff;font-family: '黑体';font-weight: bold\"></div>" +
				"        <div style=\"margin:0 5%;padding:0px 20px;width: 80%;height: 210px;background: #ffffff;border: 1px solid #e5e5e5\">     " + 
				"            <h3 style=\"font-weight: normal;font-size: 14px;color:sandybrown\">Hi,<span>"+user.getUserName()+"</span>:</h3>" + 
				"            <div style=\"font-size: 12px;\">" + 
				"                欢迎您使用密码找回功能。" +
				"            </div>" + 
				"            <div class=\"cont\">" + 
				"                <p style=\"font-size: 12px\">您本次的邮箱验证码为:<span style=\"color:red;font-weight: bold;font-size: 18px;padding: 0 7px;\">"+randomStr+"</span></p>" + 
				"            </div>" + 
				"            <div style=\"font-size: 12px;text-indent: 2em;line-height: 20px;padding-bottom: 40px;\">" + 
				"                请在5分钟内访问页面并重新设置密码，如果您并未发过此邮件，那么您可以忽略此邮件，无需采取进一步操作。" + 
				"            </div>" + 
				"        </div>" + 
				"    </div>" + 
				"</body>" + 
				"</html>";
		mailInfo.setSubject("密码找回");
		mailInfo.setContent(content);
		mailInfo.setToAddress(toAddress);
		EmailUtil email = new EmailUtil();
		
		if(StringUtil.isNotEmpty(user)) {
			if(StringUtil.isNotEmpty(user.getMail())/* && user.getMail().equals(toAddress)*/) {
				Date nowDate = new Date();
				//查询历史保存验证码记录信息，判断间隔时间
				SysEmailCode oldEmailCodeObj = sysEmailCodeDao.querySysEmailCodeByUserCodeAndToAddress(userCde,toAddress);
				if(StringUtil.isNotEmpty(oldEmailCodeObj)) {
					//有记录判断间隔时长，1分钟后再次发送
					Date lastCreateTime = oldEmailCodeObj.getCreateTime();
					//1分钟内不让重复提交
					long updatetime = lastCreateTime.getTime() + 60 * 1000;
					long nowDateTime = nowDate.getTime();
					if(nowDateTime < updatetime) {
						throw new Exception(MessageUtils.getMessage("common.email.send.resubmit"));
					}else {
						//更新验证码
						EmailResult result = email.sendEmail(mailInfo);
						if(result.isSuccess()) {
							try {
								//发送成功保存信息
								sysEmailCodeDao.updateSysEmailCode(userCde,toAddress,randomStr,user.getId(),nowDate);
							} catch (Exception e) {
								throw  e;
							}
						}else {
							throw new Exception(result.getMsg());
						}
					}
				}else {
					//无数据是新增，首次发送验证码
					EmailResult result = email.sendEmail(mailInfo);
					if(result.isSuccess()) {
						try {
							//发送成功保存信息
							SysEmailCode sysEmailCode = new SysEmailCode();
							sysEmailCode.setUserCode(userCde);
							sysEmailCode.setToAddress(toAddress);
							sysEmailCode.setVerifyCode(randomStr);
							sysEmailCode.setCreateUser(user.getId());
							sysEmailCode.setCreateTime(nowDate);
							sysEmailCodeDao.save(sysEmailCode);
						} catch (Exception e) {
							throw  e;
						}
					}else {
						throw new Exception(result.getMsg());
					}
				}
			}else {
//				throw new Exception(MessageUtils.getMessage("common.email.noverify"));
				throw new Exception(MessageUtils.getMessage("common.email.noverify.usermail"));
			}
		}else {
			throw new Exception(MessageUtils.getMessage("common.sys.login.nouser"));
		}
	}

	/**
	 * 验证邮箱验证码
	 * @param emailCodePojo
	 * @throws Exception
	 */
	@Override
	public void verifyEmailCode(EmailCodePojo emailCodePojo) throws Exception {
		String userCde = emailCodePojo.getUserCode();
//		String toAddress = emailCodePojo.getToAddress();
        String toAddress = "";
        String verifyCode = emailCodePojo.getVerifyCode();
		if(StringUtil.isEmpty(verifyCode)) {
			throw new Exception(MessageUtils.getMessage("common.email.verify.notnull"));
		}
		//根据登录名查询用户，判断邮箱是否存在并一致
		SysUser user = userDao.getUserByUserCde(userCde);
		if(StringUtil.isNotEmpty(user)) {
			if(StringUtil.isNotEmpty(user.getMail()) /*&& user.getMail().equals(toAddress)*/) {
                toAddress = user.getMail();
				Date nowDate = new Date();
				//查询历史保存验证码记录信息，判断间隔时间
				SysEmailCode oldEmailCodeObj = sysEmailCodeDao.querySysEmailCodeByUserCodeAndToAddress(userCde,toAddress);
				if(StringUtil.isNotEmpty(oldEmailCodeObj)) {
					//有记录判断间隔时长
					Date lastCreateTime = oldEmailCodeObj.getCreateTime();
					//五分钟内验证
					long updatetime = lastCreateTime.getTime() + 5 * 60 * 1000;
					long nowDateTime = nowDate.getTime();
					if(nowDateTime > updatetime) {
						throw new Exception(MessageUtils.getMessage("common.email.verify.timeout"));
					}else {
						//对比验证码
						if(!verifyCode.equals(oldEmailCodeObj.getVerifyCode())) {
							throw new Exception(MessageUtils.getMessage("common.email.verify.fail"));
						}
					}
				}else {
					//无数据是提示发送验证码
					throw new Exception(MessageUtils.getMessage("common.email.verify.nocode"));
				}
			}else {
				throw new Exception(MessageUtils.getMessage("common.email.noverify"));
			}
		}else {
			throw new Exception(MessageUtils.getMessage("common.sys.login.nouser"));
		}
	}

	/**
	 * 验证邮箱重置密码
	 * @param emailCodePojo
	 * @throws Exception
	 */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT,
        readOnly = false, rollbackFor = Exception.class)
	public void updateResetPassword(EmailCodePojo emailCodePojo) throws Exception {
		String userCde = emailCodePojo.getUserCode();
		String toAddress = emailCodePojo.getToAddress();
		String verifyCode = emailCodePojo.getVerifyCode();
		if(StringUtil.isEmpty(verifyCode)) {
			throw new Exception(MessageUtils.getMessage("common.email.verify.notnull"));
		}
		String newPassword = emailCodePojo.getNewPassword();
		String confirmPassword = emailCodePojo.getConfirmPassword();
		if(StringUtil.isEmpty(newPassword) && StringUtil.isEmpty(confirmPassword)) {
			throw new Exception(MessageUtils.getMessage("common.sys.password.notnull"));
		}
		if(!newPassword.equals(confirmPassword)) {
			throw new Exception(MessageUtils.getMessage("common.sys.password.notempty"));
		}
		//根据登录名查询用户，判断邮箱是否存在并一致
		SysUser user = userDao.getUserByUserCde(userCde);
		if(StringUtil.isNotEmpty(user)) {
			if(StringUtil.isNotEmpty(user.getMail())/* && user.getMail().equals(toAddress)*/) {
				Date nowDate = new Date();
                toAddress = user.getMail();
				//查询历史保存验证码记录信息，判断间隔时间
				SysEmailCode oldEmailCodeObj = sysEmailCodeDao.querySysEmailCodeByUserCodeAndToAddress(userCde,toAddress);
				if(StringUtil.isNotEmpty(oldEmailCodeObj)) {
					//有记录判断间隔时长
					Date lastCreateTime = oldEmailCodeObj.getCreateTime();
					//五分钟内验证
					long updatetime = lastCreateTime.getTime() + 5 * 60 * 1000;
					long nowDateTime = nowDate.getTime();
					if(nowDateTime > updatetime) {
						throw new Exception(MessageUtils.getMessage("common.email.verify.timeout"));
					}else {
						//对比验证码
						if(!verifyCode.equals(oldEmailCodeObj.getVerifyCode())) {
							throw new Exception(MessageUtils.getMessage("common.email.verify.fail"));
						}else {
							//验证通过重置密码
							SysUser sysUser = new SysUser();
							sysUser.setPassword(newPassword);
							sysUser.setUserCde(userCde);
							sysUser.setId(user.getId());
							userService.updateResetPassword(sysUser);
							SysUser sysUser2 = new SysUser();
							sysUser2.setUserCde(userCde);
							sysUser2.setIslocked(SysUser.NO_LOCKED);
							userService.updateIslockedByCde(sysUser2);
							//重置密码清除缓存
							CacheUtils.remove("passwordRetryCache", userCde);
						}
					}
				}else {
					//无数据是提示发送验证码
					throw new Exception(MessageUtils.getMessage("common.email.verify.nocode"));
				}
			}else {
				throw new Exception(MessageUtils.getMessage("common.email.noverify"));
			}
		}else {
			throw new Exception(MessageUtils.getMessage("common.sys.login.nouser"));
		}
	}

}
