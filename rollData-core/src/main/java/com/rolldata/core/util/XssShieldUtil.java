package com.rolldata.core.util;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title: XssShieldUtil
 * @Description: xss过滤配置
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class XssShieldUtil {
	 private static List<Pattern> patterns = null;
	private static List<Pattern> sqlPatterns = null;
	    private static List<Object[]> getXssPatternList() {
	        List<Object[]> ret = new ArrayList<Object[]>();

	        ret.add(new Object[]{"<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE});
	        ret.add(new Object[]{"eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
	        ret.add(new Object[]{"expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
	        ret.add(new Object[]{"(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE});
//	        ret.add(new Object[]{"<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
	        ret.add(new Object[]{"(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
	        ret.add(new Object[]{"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});

	        // 以下关键字直接替换
			ret.add(new Object[]{"(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});

			// 关键标签替换,参考：https://www.w3school.com.cn/tags/index.asp
			ret.add(new Object[]{"<+\\s*(img|image|input|a|button|form|iframe|textarea|area|article|base|canvas|caption|cite|col|colgroup|command|datalist|details|dialog|embed|figure|frame|frameset|link|source|track|video|audio)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
			return ret;
	    }
	    private static List<Pattern> getPatterns() {

	        if (patterns == null) {

	            List<Pattern> list = new ArrayList<Pattern>();

	            String regex = null;
	            Integer flag = null;
	            int arrLength = 0;

	            for(Object[] arr : getXssPatternList()) {
	                arrLength = arr.length;
	                for(int i = 0; i < arrLength; i++) {
	                    regex = (String)arr[0];
	                    flag = (Integer)arr[1];
	                    list.add(Pattern.compile(regex, flag));
	                }
	            }

	            patterns = list;
	        }

	        return patterns;
	    }

	private static List<Pattern> getSqlPatterns() {
		if (sqlPatterns == null) {
			List<Pattern> list = new ArrayList<Pattern>();
			String regex = null;
			Integer flag = null;
			int arrLength = 0;
			List<Object[]> ret = new ArrayList<Object[]>(getXssPatternList());
			ret.add(new Object[]{"\\s*\\w*(select|insert|delete|update|drop|create|truncate|show|exec|master.|sys.|call)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL});
			for(Object[] arr : ret) {
				arrLength = arr.length;
				for(int i = 0; i < arrLength; i++) {
					regex = (String)arr[0];
					flag = (Integer)arr[1];
					list.add(Pattern.compile(regex, flag));
				}
			}
			sqlPatterns = list;
		}
		return sqlPatterns;
	}

	/**
	 * 处理xss过滤
	 * @param value
	 * @return
	 */
	    public static String stripXss(String value) {
	        if(StringUtils.isNotBlank(value)) {

	            Matcher matcher = null;

	            for(Pattern pattern : getPatterns()) {
	                matcher = pattern.matcher(value);
	                // 匹配
	                if(matcher.find()) {
	                    // 删除相关字符串
	                    value = matcher.replaceAll("");
	                }
	            }
//TODO 表单里公式存在大于小于号，去掉转译
//	            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	        }

//	      if (LOG.isDebugEnabled())
//	          LOG.debug("strip value: " + value);

	        return value;
	    }

	/**
	 * 处理xss过滤以及sql脚本注入
	 * @param value
	 * @return
	 */
	public static String stripSql(String value) {
		if(StringUtils.isNotBlank(value)) {
			Matcher matcher = null;
			for(Pattern pattern : getSqlPatterns()) {
				matcher = pattern.matcher(value);
				// 匹配
				if(matcher.find()) {
					// 删除相关字符串
					value = matcher.replaceAll("");
				}
			}
		}
		return value;
	}

	    public static void main(String[] args) {

	        String value = null;
	        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);</script>");
	        System.out.println("type-1: '" + value + "'");

	        value = XssShieldUtil.stripXss("<script src='' onerror='alert(document.cookie)'></script>");
	        System.out.println("type-2: '" + value + "'");

	        value = XssShieldUtil.stripXss("</script>");
	        System.out.println("type-3: '" + value + "'");

	        value = XssShieldUtil.stripXss(" eval(abc);");
	        System.out.println("type-4: '" + value + "'");

	        value = XssShieldUtil.stripXss(" expression(abc);");
	        System.out.println("type-5: '" + value + "'");

	        value = XssShieldUtil.stripXss("<img src='' oncontrolselect  = '' onerror='alert(document.cookie);'></img>");
	        System.out.println("type-6: '" + value + "'");

	        value = XssShieldUtil.stripXss("<  img src='' onerror='alert(document.cookie);'/>");
	        System.out.println("type-7: '" + value + "'");

	        value = XssShieldUtil.stripXss("<img src='' onerror='alert(document.cookie);'>");
	        System.out.println("type-8: '" + value + "'");

	        value = XssShieldUtil.stripXss("<script language=text/javascript>alert(document.cookie);");
	        System.out.println("type-9: '" + value + "'");

	        value = XssShieldUtil.stripXss("<script>window.location='url'");
	        System.out.println("type-10: '" + value + "'");

	        value = XssShieldUtil.stripXss(" onload='alert(\"abc\");");
	        System.out.println("type-11: '" + value + "'");

	        value = XssShieldUtil.stripXss("<img src=x<!--'<\"-->>");
	        System.out.println("type-12: '" + value + "'");

	        value = XssShieldUtil.stripXss("<=img onstop=");
	        System.out.println("type-13: '" + value + "'");
			value = XssShieldUtil.stripSql("insert admin");
			System.out.println("type-14: '" + value + "'");

	    }
}
