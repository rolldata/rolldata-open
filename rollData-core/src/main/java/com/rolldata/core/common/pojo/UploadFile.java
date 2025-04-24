package com.rolldata.core.common.pojo;

import com.rolldata.web.system.Constants;
import com.rolldata.web.system.pojo.SysConfigPojo;

import java.util.HashMap;

/**
 * 
 * @Title: UploadFile
 * @Description: 上传文件配置
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class UploadFile {
	/**获取系统配置*/
	public static SysConfigPojo sysConfigInfo = (SysConfigPojo)Constants.property.get(Constants.sysConfigInfo);
	
	public static long fileSize1 =  1 * 1024 * 1024;
	
	 /**设置文件上传大小*/ 
    public static long fileSize = Integer.parseInt(sysConfigInfo.getFileSize()) * 1024 * 1024;
    
    /**设置图片上传大小*/
    public static long imgSize = Integer.parseInt(sysConfigInfo.getImgSize()) * 1024 * 1024;
    
//    /**设置图片上传最小宽度*/
//    public static int imgMinWidth = Integer.parseInt(sysConfigInfo.getImgMinWidth());
//    
//    /**设置图片上传最大宽度*/
//    public static int imgMaxWidth = Integer.parseInt(sysConfigInfo.getImgMaxWidth());
//    
//    /**设置图片上传最小高度*/
//    public static int imgMinHight = Integer.parseInt(sysConfigInfo.getImgMinHight());
//    
//    /**设置图片上传最大高度*/
//    public static int imgMaxHight = Integer.parseInt(sysConfigInfo.getImgMaxHight());
    
    /**设置文件允许上传的类型*/
    public static final HashMap<String, String> TypeMap = new HashMap<String, String>();

    /**
     *
     */
    public static String XLS = "xls";

    public static String XLSX = "xlsx";

    public static String DICT_TXT = "txt";

    public static String PDF = "pdf";

    public static String WORD = "docx";

    public static String ZIP = "zip";

    public static String WDBI = "wdbi";

    public static String WDFORM = "wdform";

    public static String WDRP = "wdrp";

    /**
     * 多维文件后缀
     */
    public static String MDRP = "mdrp";
    /**更新包文件后缀*/
    public static String WDUP = "wdup";
    /**模型包文件后缀*/
    public static String WDMD = "wdmd";
    /**sql文件后缀*/
    public static String SQL = "sql";

    /**设置文件允许上传的类型*/
    static {
        TypeMap.put("image", "gif,jpg,jpeg,png,bmp");
        TypeMap.put("flash", "swf,flv");
        TypeMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        TypeMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,txt,pdf,zip,rar");
        TypeMap.put("dict", "xls,xlsx,txt");
        TypeMap.put("lic", "lic");
        TypeMap.put("excel", "xls,xlsx");
        TypeMap.put("item", "xls,xlsx,txt");
        TypeMap.put("resource", ZIP + "," + WDBI + "," + WDFORM + "," + WDRP + "," + MDRP);
        TypeMap.put("js", "js");
        TypeMap.put("css", "css");
        TypeMap.put("csv", "csv");
        TypeMap.put("txt", "txt");
        TypeMap.put("zip", "zip");
        TypeMap.put("wdup", "wdup");
        TypeMap.put("wdmd", "wdmd");
    }
}
