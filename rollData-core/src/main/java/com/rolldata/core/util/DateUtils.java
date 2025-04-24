package com.rolldata.core.util;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @Title: DateUtils
 * @Description: 时间操作定义类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class DateUtils extends PropertyEditorSupport {
	
	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;
	
	public final static String PATTERN_YYYY_MM = "yyyy-MM";
	
	public final static String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

	public final static String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public final static String PATTERN_YYYYMMDDHHMMSSSS = "yyyyMMddHHmmssSS";
	
	public final static String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public final static String PATTERN_HH_MM = "HH:mm";
	
	public final static String PATTERN_HHMMSSSS = "HHmmssSS";
	
	public final static String PATTERN_HHMMSS = "HH:mm:ss";
	
	public final static String PATTERN_YYYYMMDD = "yyyyMMdd";
	
	public final static String PATTERN_YYMMDD = "yyMMdd";
	
	public final static String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";
	
	public final static String PATTERN_YYYYMM = "yyyyMM";
	
	public final static String PATTERN_YYYY = "yyyy";
	
	public final static String PATTERN_YYYYMMDDHH = "yyyyMMddHH";
	
	public final static String PATTERN_YYMM = "yyMM";
	
	public final static String PATTERN_YY = "yy";

	public final static String PATTERN_MM = "MM";

	public final static String PATTERN_M = "M";

	/**
	 * 现有年份最大值
	 */
	public final static Date MAX_TIME = str2Date("9999-12-31 23:59:59", PATTERN_YYYY_MM_DD_HH_MM_SS);
	/**
	 * 日期控件匹配校验用
	 */
	public static String[] parsePatterns = { "yyyy","yyyy-MM","yyyy-MM-dd","HH:mm:ss", "yyyy-MM-dd HH:mm:ss" };
	
	// 指定模式的时间格式
	private static SimpleDateFormat getSDFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 指定毫秒数表示的日历
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日历
	 */
	public static Calendar getCalendar(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(millis));
		return cal;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getDate
	// 各种方式获取的Date
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 当前日期
	 * 
	 * @return 系统当前时间
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * 指定毫秒数表示的日期
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数表示的日期
	 */
	public static Date getDate(long millis) {
		return new Date(millis);
	}

	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamptoStr(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(getSDFormat(PATTERN_YYYY_MM_DD));
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, getSDFormat(PATTERN_YYYY_MM_DD));
		return new Timestamp(date.getTime());
	}
	/**
	 * 字符串转换成日期
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date_sdf
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(SimpleDateFormat date_sdf) {
		Date date=getDate();
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	/**
	 * 格式化时间
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateformat(String date,String format)
	{
		SimpleDateFormat sformat = new SimpleDateFormat(format);
		Date _date=null;
		try {
			 _date=sformat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sformat.format(_date);
	}
	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param date_sdf
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, SimpleDateFormat date_sdf) {
		if (null == date) {
			return null;
		}
		return date_sdf.format(date);
	}
	/**
	 * 日期转换为字符串
	 * 
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String getDate(String format) {
		Date date=new Date();
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 指定毫秒数的时间戳
	 * 
	 * @param millis
	 *            毫秒数
	 * @return 指定毫秒数的时间戳
	 */
	public static Timestamp getTimestamp(long millis) {
		return new Timestamp(millis);
	}

	/**
	 * 以字符形式表示的时间戳
	 * 
	 * @param time
	 *            毫秒数
	 * @return 以字符形式表示的时间戳
	 */
	public static Timestamp getTimestamp(String time) {
		return new Timestamp(Long.parseLong(time));
	}

	/**
	 * 系统当前的时间戳
	 * 
	 * @return 系统当前的时间戳
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 指定日期的时间戳
	 * 
	 * @param date
	 *            指定日期
	 * @return 指定日期的时间戳
	 */
	public static Timestamp getTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 指定日历的时间戳
	 * 
	 * @param cal
	 *            指定日历
	 * @return 指定日历的时间戳
	 */
	public static Timestamp getCalendarTimestamp(Calendar cal) {
		return new Timestamp(cal.getTime().getTime());
	}

	public static Timestamp gettimestamp() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SS);
		String nowTime = df.format(dt);
		Timestamp buydate = Timestamp.valueOf(nowTime);
		return buydate;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getMillis
	// 各种方式获取的Millis
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 系统时间的毫秒数
	 *
	 * @return 系统时间的毫秒数
	 */
	public static long getMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 指定日历的毫秒数
	 *
	 * @param cal
	 *            指定日历
	 * @return 指定日历的毫秒数
	 */
	public static long getMillis(Calendar cal) {
		return cal.getTime().getTime();
	}

	/**
	 * 指定日期的毫秒数
	 *
	 * @param date
	 *            指定日期
	 * @return 指定日期的毫秒数
	 */
	public static long getMillis(Date date) {
		return date.getTime();
	}

	/**
	 * 指定时间戳的毫秒数
	 *
	 * @param ts
	 *            指定时间戳
	 * @return 指定时间戳的毫秒数
	 */
	public static long getMillis(Timestamp ts) {
		return ts.getTime();
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatDate
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日
	 *
	 * @return 默认日期按“年-月-日“格式显示
	 */
	public static String formatDate() {
		return getSDFormat(PATTERN_YYYY_MM_DD).format(getCalendar().getTime());
	}
	/**
	 * 获取时间字符串
	 */
	public static String getDataString(SimpleDateFormat formatstr) {
		return formatstr.format(getCalendar().getTime());
	}
	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Calendar cal) {
		return getSDFormat(PATTERN_YYYY_MM_DD).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日“格式显示
	 */
	public static String formatDate(Date date) {
		return getSDFormat(PATTERN_YYYY_MM_DD).format(date);
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日“格式显示
	 */
	public static String formatDate(long millis) {
		return getSDFormat(PATTERN_YYYY_MM_DD).format(new Date(millis));
	}

	/**
	 * 默认日期按指定格式显示
	 *
	 * @param pattern
	 *            指定的格式
	 * @return 默认日期按指定格式显示
	 */
	public static String formatDate(String pattern) {
		return getSDFormat(pattern).format(getCalendar().getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 *
	 * @param cal
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Calendar cal, String pattern) {
		return getSDFormat(pattern).format(cal.getTime());
	}

	/**
	 * 指定日期按指定格式显示
	 *
	 * @param date
	 *            指定的日期
	 * @param pattern
	 *            指定的格式
	 * @return 指定日期按指定格式显示
	 */
	public static String formatDate(Date date, String pattern) {
		return getSDFormat(pattern).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
	 *
	 * @return 默认日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime() {
		return getSDFormat(PATTERN_YYYY_MM_DD_HH_MM).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(long millis) {
		return getSDFormat(PATTERN_YYYY_MM_DD_HH_MM).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Calendar cal) {
		return getSDFormat(PATTERN_YYYY_MM_DD_HH_MM).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：年-月-日 时：分
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“年-月-日 时：分“格式显示
	 */
	public static String formatTime(Date date) {
		return getSDFormat(PATTERN_YYYY_MM_DD_HH_MM).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// formatShortTime
	// 将日期按照一定的格式转化为字符串
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 默认方式表示的系统当前日期，具体格式：时：分
	 *
	 * @return 默认日期按“时：分“格式显示
	 */
	public static String formatShortTime() {
		return getSDFormat(PATTERN_HH_MM).format(getCalendar().getTime());
	}

	/**
	 * 指定毫秒数表示日期的默认显示，具体格式：时：分
	 *
	 * @param millis
	 *            指定的毫秒数
	 * @return 指定毫秒数表示日期按“时：分“格式显示
	 */
	public static String formatShortTime(long millis) {
		return getSDFormat(PATTERN_HH_MM).format(new Date(millis));
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 *
	 * @param cal
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Calendar cal) {
		return getSDFormat(PATTERN_HH_MM).format(cal.getTime());
	}

	/**
	 * 指定日期的默认显示，具体格式：时：分
	 *
	 * @param date
	 *            指定的日期
	 * @return 指定日期按“时：分“格式显示
	 */
	public static String formatShortTime(Date date) {
		return getSDFormat(PATTERN_HH_MM).format(date);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// parseDate
	// parseCalendar
	// parseTimestamp
	// 将字符串按照一定的格式转化为日期或时间
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 */
	public static Date parseDate(String src, String pattern)
			throws ParseException {
		return getSDFormat(pattern).parse(src);

	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的日期
	 * @throws ParseException
	 */
	public static Calendar parseCalendar(String src, String pattern)
			throws ParseException {

		Date date = parseDate(src, pattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatAddDate(String src, String pattern, int amount)
			throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.DATE, amount);
		return formatDate(cal);
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 *
	 * @param src
	 *            将要转换的原始字符窜
	 * @param pattern
	 *            转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 */
	public static Timestamp parseTimestamp(String src, String pattern)
			throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 *
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

		long millisDiff = getMillis(calSrc) - getMillis(calDes);

		if (flag == 'y') {
			return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
		}

		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}

		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}

		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}

		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}

		return 0;
	}
    /**
     * String类型 转换为Date,
     * 如果参数长度为10 转换格式”yyyy-MM-dd“
     *如果参数长度为19 转换格式”yyyy-MM-dd HH:mm:ss“
     * * @param text
	 *             String类型的时间值
     */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				if (text.indexOf(":") == -1 && text.length() == 10) {
					setValue(getSDFormat(PATTERN_YYYY_MM_DD).parse(text));
				} else if (text.indexOf(":") > 0 && text.length() == 19) {
					setValue(getSDFormat(PATTERN_YYYY_MM_DD_HH_MM_SS).parse(text));
				} else {
					throw new IllegalArgumentException(
							"Could not parse date, date format is error ");
				}
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException(
						"Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}
	public static int getYear(){
	    GregorianCalendar calendar=new GregorianCalendar();
	    calendar.setTime(getDate());
	    return calendar.get(Calendar.YEAR);
	  }
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 *
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, PATTERN_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 取得当前时间串加三个随机数，用于临时文件副本名称
	 *
	 * @return
	 */
	public static String getTimeString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getSDFormat(PATTERN_HHMMSSSS).format(new Date()));
		sb.append(String.valueOf(Math.round((Math.random() * 100000000))));
		return sb.toString();
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date str2Date (String str, String pattern) {
		if (null == str || "".equals(str)) {
			return null;
		}
		Date date = null;
		try {
			date = getSDFormat(pattern).parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str (Date date, String pattern) {
		if (null == date) {
			return null;
		}
		return getSDFormat(pattern).format(date);
	}
	
	/**
	 * 获取两日期间所有月份
	 * @param minDate yyyy-MM
	 * @param maxDate yyyy-MM
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
	    List<String> result = new ArrayList<String>();
 
	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();
 
	    min.setTime(DateUtils.getSDFormat(PATTERN_YYYY_MM).parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
 
	    max.setTime(DateUtils.getSDFormat(PATTERN_YYYY_MM).parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
 
	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(DateUtils.getSDFormat(PATTERN_YYYY_MM).format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }
	    min = null;max = null;curr = null;
	    return result;
	  }
	
	/**
	 * 获取两日期间所有天数
	 * @param startTime yyyy-MM-dd
	 * @param endTime yyyy-MM-dd
	 * @return
	 * @throws Exception
	 */
	public static List<String> getDaysBetween(String startTime, String endTime) throws Exception {
		List<String> result = new ArrayList<String>();
		Date start = DateUtils.getSDFormat(PATTERN_YYYY_MM_DD).parse(startTime);
        Date end = DateUtils.getSDFormat(PATTERN_YYYY_MM_DD).parse(endTime);

        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
        	result.add(DateUtils.getSDFormat(PATTERN_YYYY_MM_DD).format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
		return result;
	}

	/**
	 * 获取两时间区间内所有时分秒
	 * @param startTime HH:mm:ss
	 * @param endTime HH:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static List<String> getMillisBetween(String startTime, String endTime) throws Exception{
		List<String> result = new ArrayList<String>();
		Date start = DateUtils.getSDFormat(PATTERN_HHMMSS).parse(startTime);
        Date end = DateUtils.getSDFormat(PATTERN_HHMMSS).parse(endTime);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.SECOND, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
        	result.add(DateUtils.getSDFormat(PATTERN_HHMMSS).format(tempStart.getTime()));
            tempStart.add(Calendar.SECOND, 1);
        }
		return result;
	}

	/**
	 * 获取两时间区间内所有时分秒
	 * @param startTime yyyy-MM-dd HH:mm:ss
	 * @param endTime yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static List<String> getDaysMillisBetween(String startTime, String endTime) throws Exception{
		List<String> result = new ArrayList<String>();
		Date start = DateUtils.getSDFormat(PATTERN_YYYY_MM_DD_HH_MM_SS).parse(startTime);
        Date end = DateUtils.getSDFormat(PATTERN_YYYY_MM_DD_HH_MM_SS).parse(endTime);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.SECOND, +1);// 日期加1(包含结束)
        while (tempStart.before(tempEnd)) {
        	result.add(DateUtils.getSDFormat(PATTERN_YYYY_MM_DD_HH_MM_SS).format(tempStart.getTime()));
            tempStart.add(Calendar.SECOND, 1);
        }
		return result;
	}
	
	/**
	 * 将未指定格式的日期字符串转化成java.util.Date类型日期对象 <br>
	 * Author：BluesLee (https://www.iteye.com/blog/dang-247484)<br>
	 * CreateDate：2008-9-25 <br>
	 * Modifier：BluesLee <br>
	 * ModifyDate：2008-9-25 <br>
	 * Version:1.1<br>
	 * All right reserved.<br>
	 *
	 * @param date 待转换的日期字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDate(String date) throws ParseException {
		Date result = null;
		String parse = date;
		parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
		parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
		parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
		parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
		parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
		parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
		parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
		DateFormat format = new SimpleDateFormat(parse);
		result = format.parse(date);
		return result;
	}
	
	/**
	 * 将字符串,格式化成指定格式
	 *
	 * @param date    日期字符串
	 * @param pattern 格式
	 * @return
	 */
	public static String parseDateStrToString(String date, String pattern) throws ParseException {
		
		return DateUtils.formatDate(DateUtils.parseStringToDate(date), pattern);
	}

	/**
	 * 获取某月多少天
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取某月多少天
	 * @param month 2022-01
	 * @return
	 * @throws ParseException
	 */
	public static int getDaysOfMonthByMonth(String month) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM);
		return getDaysOfMonth(sdf.parse(month));
	}

	/**
	 * 获取某月多少天
	 * @param day 2022-01-01
	 * @return
	 * @throws ParseException
	 */
	public static int getDaysOfMonthByDay(String day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYY_MM_DD);
		return getDaysOfMonth(sdf.parse(day));
	}

	/**
	 * 推算几天后的日期(输入负数则往前计算)
	 *
	 * @param data 开始时间
	 * @param num  推算的天数
	 * @return
	 */
	public static Date getAfterDate(Date data, long num) {

		//2019-4-26 到 2019-4-27 算一天
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.add(Calendar.DATE, (int) num);
			data = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 推算几月后的日期(输入负数则往前计算)
	 *
	 * @param data 开始时间
	 * @param num  推算的月
	 * @return
	 */
	public static Date getAfterMonthDate(Date data, long num) {

		//2019-4-26 到 2019-4-27 算一天
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.add(Calendar.MONTH, (int) num);
			data = cal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 按年计算去年或明年同期日期
	 * @param dateTime 时间yyyy-mm-dd
	 * @param period 期(要取得期数,负数取上期,正数取下期0当期)
	 * @return
	 * @throws ParseException
	 */
	public static String getLastYearOrNextYear(String dateTime, int period) {
		Calendar calendar = Calendar.getInstance();
		try{
			calendar.setTime(DateUtils.getSDFormat(PATTERN_YYYY_MM_DD).parse(dateTime));
			calendar.add(Calendar.YEAR, period);
		}catch(ParseException e){
			e.printStackTrace();
			return dateTime;
		}
		return DateUtils.getSDFormat(PATTERN_YYYY_MM_DD).format(calendar.getTime());
	}
}
