package com.rolldata.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 
 * @Title: MessageUtils
 * @Description: 消息工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class MessageUtils {

	private static MessageSource messageSource;

	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code
	 *            消息键
	 * @param args
	 *            参数
	 * @return
	 */
	public static String getMessage(String code, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		if (messageSource == null) {
			messageSource = SpringContextHolder.getBean(MessageSource.class);
		}
		return messageSource.getMessage(code, args, locale);
	}

	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code
	 *            消息键
	 * @param args
	 *            参数
	 * @return
	 */
	public static String getMessage(String code, String defaultMessage, Object... args) {
		Locale locale = LocaleContextHolder.getLocale();
		if (messageSource == null) {
			messageSource = SpringContextHolder.getBean(MessageSource.class);
		}
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code
	 *            消息键
	 * @param args
	 *            参数
	 * @return
	 */
	public static String getMessageOrSelf(String code, Object... args) {
		String message = "";
		try {
			message = getMessage(code, args);
		} catch (Exception e) {
			message = code;
		}
		return message;
	}

}
