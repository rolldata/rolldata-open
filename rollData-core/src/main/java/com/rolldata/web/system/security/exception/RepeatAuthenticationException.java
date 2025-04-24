package com.rolldata.web.system.security.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * @Title: RepeatAuthenticationException
 * @Description: 重复认证异常
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class RepeatAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3278647858926719428L;

	public RepeatAuthenticationException() {
		super();
	}

	public RepeatAuthenticationException(String message) {
		super(message);
	}

	public RepeatAuthenticationException(Throwable cause) {
		super(cause);
	}

	public RepeatAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
