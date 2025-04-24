package com.rolldata.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @Title: BaseCheckUtils
 * @Description: 基本类型校验工具
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-06-30
 * @version: V1.0
 */
public class BaseCheckUtils {

    /**
     * 正负数
     */
    public static final Pattern NUM_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
		if(str.indexOf('.')>0){//判断是否有小数点
			if(str.indexOf('.')==str.lastIndexOf('.') && str.split("\\.").length==2){ //判断是否只有一个小数点
	            return NUM_PATTERN.matcher(str.replace(".","")).matches();
	        }else {
	            return false;
	        }
		}else {
			return NUM_PATTERN.matcher(str).matches();
		}
    }

    /**
     * 判断浮点数（double和float）
     *
     * @param str
     * @param isNull
     * @return
     */
    public static boolean isDouble(String str, boolean isNull) {
        if(StringUtil.isNotEmpty(str)) {
            return BaseCheckUtils.isNumeric(str);
        }else {
            if(isNull) {//判断数据项设置的是否可为空，true是不能为空
                return false;
            }else {
                return true;
            }
        }
    }

    /**
     * 判断字符串是否为yyyy年MM月dd日格式的日期
     *
     * @param str
     * @param formate
     * @param isNull
     * @return
     */
    public static boolean isValidDate(String str, String formate, boolean isNull) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(formate);
        if(StringUtil.isNotEmpty(str)) {
            try {
                // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
                format.setLenient(false);
                format.parse(str);
            } catch (ParseException e) {
                // e.printStackTrace();
                // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
                convertSuccess=false;
            }
        }else {
            if(isNull) {//判断数据项设置的是否可为空，true是不能为空
                convertSuccess=false;
            }
        }
        return convertSuccess;
    }

    /**
     * 判断整数（int）
     *
     * @param str
     * @param isNull
     * @return
     */
    public static boolean isInteger(String str, boolean isNull) {
        if (StringUtil.isNotEmpty(str)) {
            return NUM_PATTERN.matcher(str).matches();
        } else {

            //判断数据项设置的是否可为空，true是不能为空
            if (isNull) {
                return false;
            } else {
                return true;
            }
        }
    }
}
