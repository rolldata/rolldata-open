package com.rolldata.web.system.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class DownloadUtil {
    
    public static void downloadFile(String filePath,HttpServletResponse response) throws Exception{
        File f = new File(filePath);
        if(!f.exists()){
            throw new Exception("下载文件不存在:"+filePath);
        }
        String fileName = f.getName();//文件名
        // 清空response
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        //String tempFileName =new String(fileName.getBytes("utf-8"),"ISO-8859-1");  
        String tempFileName = URLEncoder.encode(fileName, "UTF-8");  
        response.setHeader("Content-Disposition","attachment;fileName="+tempFileName);//设置响应的文件名
        response.setHeader("Content-Length",String.valueOf(f.length()));//设置文件大小
        InputStream is = new BufferedInputStream(new FileInputStream(filePath));
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        byte[] b = new byte[1024];
        int length=0;
        while((length=is.read(b))>0){
            os.write(b, 0, length);
        }
        //关闭文件输入流
        is.close();
        os.flush();
        os.close();
    }

    /**
     * 下载表单里的文件 <br> 文件名处理
     * @param filePath
     * @param response
     * @throws Exception
     */
    public static void downloadFormFile (String filePath, HttpServletResponse response) throws Exception {

        File f = new File(filePath);
        if(!f.exists()){
            throw new Exception("下载文件不存在:"+filePath);
        }

        //TODO: 和上面的方法合并一下,加个tempFileName参数即可
        String fileName = f.getName();
        String tmpFileNameBefore = fileName.substring(0, fileName.lastIndexOf("_"));
        String tmpFileNameAfter = fileName.substring(fileName.lastIndexOf("_"));
        fileName = tmpFileNameAfter.substring(tmpFileNameAfter.lastIndexOf("."));
        fileName = tmpFileNameBefore + fileName;

        // 清空response
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        //String tempFileName =new String(fileName.getBytes("utf-8"),"ISO-8859-1");  
        String tempFileName = URLEncoder.encode(fileName, "UTF-8");

        //设置响应的文件名
        response.setHeader("Content-Disposition","attachment;fileName=" + tempFileName);

        //设置文件大小
        response.setHeader("Content-Length", String.valueOf(f.length()));
        InputStream is = new BufferedInputStream(new FileInputStream(filePath));
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = is.read(b)) > 0) {
            os.write(b, 0, length);
        }

        //关闭文件输入流
        is.close();
        os.flush();
        os.close();
    }
}
