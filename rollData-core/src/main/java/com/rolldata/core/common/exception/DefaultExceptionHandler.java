package com.rolldata.core.common.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @Title: DefaultExceptionHandler
 * @Description: 没有权限 异常
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@ControllerAdvice
public class DefaultExceptionHandler {
	/**
	 * 没有权限 异常
	 * <p/>
	 * 后续根据不同的需求定制即可
	 */
	@ExceptionHandler({ UnauthorizedException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView processUnauthenticatedException(
			NativeWebRequest request, UnauthorizedException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.setViewName("common/unauthorized");
		return mv;
	}
}
