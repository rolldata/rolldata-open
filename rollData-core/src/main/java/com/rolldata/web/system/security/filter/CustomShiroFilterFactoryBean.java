package com.rolldata.web.system.security.filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

import javax.servlet.Filter;
import java.util.Map;

/**
 * @Title: CustomShiroFilterFactoryBean
 * @Description:  自定义ShiroFilterFactoryBean解决资源中文路径问题
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2023-07-03
 * @version: V1.0
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Override
    protected FilterChainManager createFilterChainManager() {
        FilterChainManager manager = super.createFilterChainManager();
        // URL携带中文400，servletPath中文校验bug
        Map<String, Filter> filterMap = manager.getFilters();
        Filter invalidRequestFilter = filterMap.get(DefaultFilter.invalidRequest.name());
        if (invalidRequestFilter instanceof InvalidRequestFilter) {
            ((InvalidRequestFilter) invalidRequestFilter).setBlockNonAscii(false);
        }
        return manager;
    }

}
