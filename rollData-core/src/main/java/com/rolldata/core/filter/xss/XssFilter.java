package com.rolldata.core.filter.xss;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * @Title: XssFilter
 * @Description: XSS过滤器；另外，有些情况不想显示过滤后内容的话，可以用StringEscapeUtils.unescapeHtml4()这个方法，
 * 把StringEscapeUtils.escapeHtml4()转义之后的字符恢复原样。
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String enctype = httpRequest.getContentType();
        String url = httpRequest.getRequestURI();
        if(url.contains("modelController")){//模型相关的都忽略
            chain.doFilter(httpRequest, response);
        }else{
            // 处理上传过滤
            if (StringUtils.isNotBlank(enctype) && enctype.contains("multipart/form-data")) {
                CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                        httpRequest.getSession().getServletContext()
                );
                MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(httpRequest);
                chain.doFilter(new XssHttpServletRequestWrapper(multipartRequest), response);
            } else {
                chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
            }
        }
	}

	@Override
	public void destroy() {
	}
}
