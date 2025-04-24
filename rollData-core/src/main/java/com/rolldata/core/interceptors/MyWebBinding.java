package com.rolldata.core.interceptors;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

import java.util.Date;
//import org.springframework.web.context.request.WebRequest;

/**
 * 
 * @Title:MyWebBinding
 * @Description:自定义类型转换
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2018-5-3
 * @version V1.0
 */
public class MyWebBinding implements WebBindingInitializer {

//	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 1. 使用spring自带的CustomDateEditor
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, true));
		//2. 自定义的PropertyEditorSupport
//			binder.registerCustomEditor(Date.class, new DateConvertEditor());
//	}

	@Override
	public void initBinder(WebDataBinder binder) {
		// 1. 使用spring自带的CustomDateEditor
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// binder.registerCustomEditor(Date.class, new
		// CustomDateEditor(dateFormat, true));
		//2. 自定义的PropertyEditorSupport
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
	}

}
