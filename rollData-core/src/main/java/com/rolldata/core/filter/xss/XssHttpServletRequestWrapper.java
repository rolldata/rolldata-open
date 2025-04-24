package com.rolldata.core.filter.xss;

import com.rolldata.core.util.XssShieldUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * @Title: XssHttpServletRequestWrapper
 * @Description: xss过滤配置
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getHeader(String name) {
		//zhaibx json串双引号被替换故修改
		//return StringEscapeUtils.escapeHtml4(super.getHeader(name));
		if(name=="custReportStr"){//判断BI自定义图形的参数不过滤
			return super.getHeader(name);
		}else{
			return XssShieldUtil.stripXss(super.getHeader(XssShieldUtil.stripXss(name)));
		}
	}

	@Override
	public String getQueryString() {
		//zhaibx json串双引号被替换故修改
		//return StringEscapeUtils.escapeHtml4(super.getQueryString());
		return XssShieldUtil.stripXss(super.getQueryString());
	}
	
	@Override
	public String getParameter(String name) {
		//zhaibx json串双引号被替换故修改
		//return StringEscapeUtils.escapeHtml4(super.getParameter(name));
		if(name=="custReportStr"){//判断BI自定义图形的参数不过滤
			return super.getParameter(name);
		}else if(name == "data"){
			String value = super.getParameter(name);
			if(value.indexOf("SELECT_ALLPOWER")>0){//驾驶舱有万能组件的不过滤
				return super.getParameter(name);
			}else{
				return XssShieldUtil.stripXss(super.getParameter(XssShieldUtil.stripXss(name)));
			}
		}else{
			return XssShieldUtil.stripXss(super.getParameter(XssShieldUtil.stripXss(name)));
		}
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] escapseValues = new String[length];
			for (int i = 0; i < length; i++) {
				//zhaibx json串双引号被替换故修改
				escapseValues[i] = XssShieldUtil.stripXss(values[i]);// StringEscapeUtils.escapeHtml4(values[i]);
			}
			return escapseValues;
		}
		return super.getParameterValues(name);
	}

}