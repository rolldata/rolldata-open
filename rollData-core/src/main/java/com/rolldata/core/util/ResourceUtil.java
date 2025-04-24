package com.rolldata.core.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 
 * @Title: ResourceUtil
 * @Description: 项目参数工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class ResourceUtil {
	public static final String LOCAL_CLINET_USER = "LOCAL_CLINET_USER";

    private static final ResourceBundle bundle = ResourceBundle.getBundle("sysConfig");

	/**静态文件的文件夹名称*/
	public static final String staticFolder = "static";

	/**图片文件夹名称*/
	public static final String imgFolder = "images";

	/**js文件夹名称*/
	public static final String jsFolder = "js";

	/**css文件夹名称*/
	public static final String cssFolder = "css";

	/**文件上传文件夹名称*/
	public static final String uploadFile = "uploadFile";

	/**图片上传的文件夹名称*/
	public static final String uploadImgFolder = "upload";
	
	/**生成的填报模板文件夹名称*/
	public static final String templateFolder = "template";
	
	/**系统json文件夹名称*/
	public static final String jsonFileFolder = "jsonFile";

	/**临时文件夹名称*/
	public static final String tempFolder = "temp";
	
	/**公告附件文件夹名称*/
	public static final String announcementFolder = "announcement";
	
	/**头像文件夹名称*/
	public static final String headImgFolder = "headPhoto";
	
	/**报表保存文件夹名称*/
	public static final String reportFolder = "report";
	
	/**驾驶舱保存文件夹名称*/
	public static final String visualizationFolder = "visualization";

	/**流程文件夹名称*/
	public static final String flowFolder = "flow";

	/**
	 * 数据报表文件夹名称
	 */
	public static final String dataReportFolder = "dataReport";

    /**
     * 报表默认文件名称
     */
	public static final String dataReportVersionFolder = "dataReportVersion";

    /**
     * 多维报表默认文件名称
     */
    public static final String multidimensionalReportFolder = "multidimensionalReport";

    /**
     * 大于此值,循环查询(不同库的in限制不同)
     */
	public static final int SQLSERVER_IN_CRITICAL = 999;

	/**本系统必要文件存放文件夹*/
	public static final String ROLLDATAFOLDER = "ROLLDATA";

    /**
     * 报表版本绝对路径
     */
    public static final String DATA_REPORT_VERSION_PATH = "data.report.version.path";

	/**更新文件夹名称*/
    public static final String updateFile = "updatefile";

	/**备份文件夹名称*/
	public static final String bakFile = "bakfile";

	/**生成资源模型数据文件夹名称*/
	public static final String dataFileFolder = "dataFile";

	public static final String widgetFormFolder = "widgetForm";

	static {
		File widgetFormFolder = new File(getWidgetFormJsonFilePath());
		if (!widgetFormFolder.exists()) {
			widgetFormFolder.mkdirs();
		}
	}

    /**
	 * 获取session定义名称
	 *
	 * @return
	 */
	public static final String getSessionattachmenttitle(String sessionName) {
		return bundle.getString(sessionName);
	}

	/**
	 * 获得请求路径
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {

		String requestPath = request.getRequestURI()
				+ (StringUtil.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : "");
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}

	/**
	 * 获取配置文件参数
	 *
	 * @param name
	 * @return
	 */
	public static final String getConfigByName(String name) {
		return bundle.getString(name);
	}

	/**
	 * 获取配置文件参数
	 *
	 * @param path
	 * @return
	 */
	public static final Map<Object, Object> getConfigMap(String path) {
		ResourceBundle bundle = ResourceBundle.getBundle(path);
		Set set = bundle.keySet();
		return oConvertUtils.SetToMap(set);
	}

	public static String getSysPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String separator = FileUtil.SYSTEM_FILE_SEPARATOR;
		String resultPath = "";
		// windows下
		if ("\\".equals(separator)) {
			String temp = path.replaceFirst("file:/", "").replaceFirst("WEB-INF/classes/", "");
			resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		}
		// linux下
		if ("/".equals(separator)) {
			String temp = path.replaceFirst("file:", "").replaceFirst("WEB-INF/classes/", "");
			resultPath = temp.replaceAll("%20", " ");
		}
		return resultPath;
	}

	public static String getSysClassesPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String separator = FileUtil.SYSTEM_FILE_SEPARATOR;
		String resultPath = "";
		// windows下
		if ("\\".equals(separator)) {
			String temp = path.replaceFirst("file:/", "");
			resultPath = temp.replaceAll("/", separator + separator).replaceAll("%20", " ");
		}
		// linux下
		if ("/".equals(separator)) {
			String temp = path.replaceFirst("file:", "");
			resultPath = temp.replaceAll("%20", " ");
		}
		return resultPath;
	}

	/**
	 * 获取项目根目录
	 *
	 * @return
	 */
	public static String getPorjectPath() {
		String nowpath; // 当前tomcat的bin目录的路径 如
						// D:\java\software\apache-tomcat-6.0.14\bin
		String tempdir;
		nowpath = System.getProperty("user.dir");
		tempdir = nowpath.replace("bin", "webapps"); // 把bin 文件夹变到 webapps文件里面
		tempdir += "\\"; // 拼成D:\java\software\apache-tomcat-6.0.14\webapps\sz_pro
		return tempdir;
	}

	public static String getClassPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		String temp = path.replaceFirst("file:/", "");
		String separator = FileUtil.SYSTEM_FILE_SEPARATOR;
		String resultPath = temp.replaceAll("/", separator + separator);
		return resultPath;
	}

	public static String getSystempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 获取系统用户路径
	 * 
	 * @return
	 */
	public static String getSysUserPath() {
		try {
			return URLDecoder.decode(System.getProperty("user.home"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return System.getProperty("user.home").replace("20%", " ");
	}

	public static String getParameter(String field) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		return request.getParameter(field);
	}

	/**
	 * 上传文件的路径 （表单用，图片也当文件）
	 * 
	 * @return
	 */
	public static String getUploadFilePath() {
		return getSysPath() + uploadFile + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	/**
	 * 上传公告附件的路径
	 * 
	 * @return
	 */
	public static String getUploadAnnouncementFilePath() {
		return getSysPath() + uploadFile + FileUtil.SYSTEM_FILE_SEPARATOR + announcementFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	
	/**
	 * 上传文件的临时路径 （表单用，图片也当文件）
	 * 
	 * @return
	 */
	public static String getUploadFileTempPath() {
		return getUploadFilePath() + tempFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	/**
	 * 上传图片的路径 （BI配置首页用）
	 * 
	 * @param
	 * @return
	 * @date:2016-6-6
	 */
	public static String getUploadImgPath() {
		return getSysPath() + staticFolder + FileUtil.SYSTEM_FILE_SEPARATOR + imgFolder
				+ FileUtil.SYSTEM_FILE_SEPARATOR + uploadImgFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	/**
	 * 上传文件的临时路径 （表单用）
	 * 
	 * @return
	 */
	public static String getUploadImgTempPath() {
		return getUploadImgPath() + tempFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	/**
	 * 查看文件的路径（预览图片用）
	 * 
	 * @return
	 */
	public static String getShowImgPath() {
		return staticFolder + "/" + imgFolder + "/" + uploadImgFolder + "/";
	}

	/**
	 * 上传JS的路径 （BI配置自定义图型用）
	 *
	 * @return
	 */
	public static String getUploadJsPath() {
		return getSysPath() + staticFolder + FileUtil.SYSTEM_FILE_SEPARATOR + jsFolder
				+ FileUtil.SYSTEM_FILE_SEPARATOR + uploadImgFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	/**
	 * 上传CSS的路径 （BI配置自定义图型用）
	 *
	 * @return
	 */
	public static String getUploadCssPath() {
		return getSysPath() + staticFolder + FileUtil.SYSTEM_FILE_SEPARATOR + cssFolder
				+ FileUtil.SYSTEM_FILE_SEPARATOR + uploadImgFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	/**
	 * 页面加载js文件的路径（自定义图形预览用）
	 *
	 * @return
	 */
	public static String getShowJsPath() {
		return staticFolder + "/" + jsFolder + "/" + uploadImgFolder + "/";
	}

	/**
	 * 页面加载css文件的路径（自定义图形预览用）
	 *
	 * @return
	 */
	public static String getShowCssPath() {
		return staticFolder + "/" + cssFolder + "/" + uploadImgFolder + "/";
	}

	/**
	 * 查看文件的路径（表单预览图片用）
	 * 
	 * @return
	 */
	public static String getShowFilePath() {
		return uploadFile + "/";
	}

	/**
	 * 模板存放路径
	 * 
	 * @return
	 */
	public static String getTemplatePath() {
		return getSysPath() + templateFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	
	/**
	 * 控件表单json存放路径
	 * @return
	 */
	public static String getWidgetFormJsonFilePath() {

		return getSysPath() + jsonFileFolder + FileUtil.SYSTEM_FILE_SEPARATOR + widgetFormFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	
	public static String getFlowFoler(){
		return getSysPath() + jsonFileFolder + FileUtil.SYSTEM_FILE_SEPARATOR + flowFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	/**
	 * 上传头像的路径
	 * 
	 * @param
	 * @return
	 * @date:2016-6-6
	 */
	public static String getUploadHeadImgPath() {
		return getSysPath() + staticFolder + FileUtil.SYSTEM_FILE_SEPARATOR + imgFolder
				+ FileUtil.SYSTEM_FILE_SEPARATOR + headImgFolder + FileUtil.SYSTEM_FILE_SEPARATOR;
	}
	
	/**获取本系统必要文件路径*/
	public static String getRollDataPath() {
		return getSysUserPath() + FileUtil.SYSTEM_FILE_SEPARATOR + ROLLDATAFOLDER + FileUtil.SYSTEM_FILE_SEPARATOR;
	}

	public static void main(String[] args) {
//		LogUtil.info(getPorjectPath());
//		LogUtil.info(getSysPath());
		System.out.println(getRollDataPath());
	}

}
