package com.rolldata.core.common.exception;

/**
 * 
 * @Title: BusinessException
 * @Description: 系统繁忙异常
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Throwable cause)
	{
		super(cause);
	}
	
	public BusinessException(String message,Throwable cause)
	{
		super(message,cause);
	}
}
