package com.rolldata.web.system.util;

import java.io.*;

/**
 * @Title: EncryptFile
 * @Description: 加密文件
 * @Company: www.wrenchdata.com
 * @author: zhaibx
 * @date: 2023-09-27
 * @version: V1.0
 */
public class EncryptFile {
	private static final int numOfEncAndDec = (int) 5e4; //加密解密秘钥
//    private static int dataOfFile = 2; //文件字节内容
	private static byte[] b = new byte[8*1024];
//效率太低，废弃
//    public static void EncFile(File srcFile, File encFile) throws Exception {
//        if(!srcFile.exists()){
//            System.out.println("source file not exixt");
//            return;
//        }
//
//        if(!encFile.exists()){
//            System.out.println("encrypt file created");
//            encFile.createNewFile();
//        }
//        InputStream fis  = new FileInputStream(srcFile);
//        OutputStream fos = new FileOutputStream(encFile);
//
//        while ((dataOfFile = fis.read()) > -1) {
//            fos.write(dataOfFile^numOfEncAndDec);
//        }
//
//        fis.close();
//        fos.flush();
//        fos.close();
//    }
// 效率太低，废弃
//    public static void DecFile(File encFile, File decFile) throws Exception {
//    	 if(!encFile.exists()){
//    	 	System.out.println("encrypt file not exixt");
//    	 	return;
//    	 }
//
//    	 if(!decFile.exists()){
//    		System.out.println("decrypt file created");
//    	 	decFile.createNewFile();
//    	 }
//
//    	 InputStream fis  = new FileInputStream(encFile);
//    	 OutputStream fos = new FileOutputStream(decFile);
//
//    	 while ((dataOfFile = fis.read()) > -1) {
//    		fos.write(dataOfFile^numOfEncAndDec);
//    	 }
//
//    	 fis.close();
//    	 fos.flush();
//    	 fos.close();
//    }

	public static void EncFile(File srcFile, File encFile) throws Exception {
		if(!srcFile.exists()){
			System.out.println("source file not exixt");
			return;
		}

		if(!encFile.exists()){
			System.out.println("encrypt file created");
			encFile.createNewFile();
		}
		InputStream fis  = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(encFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
		int j;
		while((j=(fis.read(b)))!=-1){
			for(int i=0;i<j;i++){
				bufferedOutputStream.write(b[i]^numOfEncAndDec);
			}
		}
		fis.close();
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}

	public static void DecFile(File encFile, File decFile) throws Exception {
		if(!encFile.exists()){
			System.out.println("encrypt file not exixt");
			return;
		}

		if(!decFile.exists()){
			System.out.println("decrypt file created");
			decFile.createNewFile();
		}

		InputStream fis  = new FileInputStream(encFile);
		FileOutputStream fos = new FileOutputStream(decFile);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);

		int j;
		while((j=(fis.read(b)))!=-1){
			for(int i=0;i<j;i++){
				bufferedOutputStream.write(b[i]^numOfEncAndDec);
			}
		}

		fis.close();
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
	}
    public static void main(String[] args) {
		long startTime = System.currentTimeMillis();    //获取开始时间
		File srcFile = new File("C:\\Users\\Administrator\\Desktop\\update\\rolldata_update_20230919.zip"); //初始文件
		File encFile = new File("C:\\Users\\Administrator\\Desktop\\update\\rolldata_update_20230919.wdup"); //加密文件
		File decFile = new File("C:\\Users\\Administrator\\Desktop\\update\\rolldata_update_20231009_dec.zip"); //解密文件
		try {
    	             EncFile(srcFile, encFile); //加密操作
//    	             DecFile(encFile,decFile);
			long endTime = System.currentTimeMillis();    //获取结束时间
			String runTime = (endTime - startTime) /1000 +"";
			System.out.println("运行时间："+ runTime + "s");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
}
