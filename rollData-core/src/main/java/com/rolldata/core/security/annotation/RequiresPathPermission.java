package com.rolldata.core.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @Title: RequiresPathPermission
 * @Description: 自定义权限注册 需要配合RequiresMethodPermissions 使用
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPathPermission {

	String value();

}
