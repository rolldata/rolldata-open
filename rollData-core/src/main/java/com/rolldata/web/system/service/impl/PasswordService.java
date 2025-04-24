package com.rolldata.web.system.service.impl;

import com.rolldata.web.system.entity.SysUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("passwordService")
public class PasswordService {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	
	@Value(value = "${shiro.credentials.hashAlgorithmName}")
	private String algorithmName = "md5";
	
	@Value(value = "${shiro.credentials.hashIterations}")
	private final int hashIterations = 2;

	public void encryptPassword(SysUser user) {
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
		user.setPassword(newPassword);
	}
	
	public boolean verifyPassword(String oldPassword, SysUser user){
		
		String dbPassword = user.getPassword();
		oldPassword = new SimpleHash(algorithmName, oldPassword, ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
		if (dbPassword.equals(oldPassword)) {
			return true;
		}else {
			return false;
		}
	}
}
