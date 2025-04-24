package com.rolldata.core.extend.datasource;
/**
 * 
 * @Title: DataSourceContextHolder
 * @Description: 获得和设置上下文环境的类，主要负责改变上下文数据源的名称
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class DataSourceContextHolder {

	private static final ThreadLocal contextHolder=new ThreadLocal();
	
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}
	
}
