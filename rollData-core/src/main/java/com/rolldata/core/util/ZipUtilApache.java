package com.rolldata.core.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * 
 * @Title:ZipUtilApache
 * @Description:压缩解压zip工具类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2016-5-16
 * @version V1.0
 */
public class ZipUtilApache {
	static final int BUFFER = 1024;

	/**
	 * 压缩文件夹
	 * 
	 * @param destDirPath
	 *            压缩后存放路径 C:\\innovation_file\\
	 * @param inputPath
	 *            需要压缩的文件夹路径 C:\\innovation_file\\temp\\test\\ 生成test.zip
	 *            文件夹内全部文件进行压缩
	 * @return zip压缩包路径，包含文件名
	 * @throws Exception
	 */
	public static String zip(String destDirPath, String inputPath)
			throws Exception {
		File inputFile = new File(inputPath);
		// 创建压缩文件
		File destDir = new File(destDirPath);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		File destFile = new File(destDir + File.separator + inputFile.getName()
				+ ".zip");
		// 递归压缩方法
		ZipOutputStream out = new ZipOutputStream(
				new FileOutputStream(destFile));
		// 设置压缩编码.设置为GBK后在windows下就不会乱码了，如果要放到Linux或者Unix下就不要设置了
		out.setEncoding("GBK");
		zip(out, inputFile, "");
		out.close();
		return destFile.toString();
	}

	/**
	 * 压缩文件
	 * 
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 */
	private static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		System.out.println("Zipping   " + f.getName());
		// 记录日志，开始压缩
		if (f.isDirectory()) {
			// 如果是文件夹，则获取下面的所有文件
			File[] fl = f.listFiles();
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base + "/"));
				// 此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
			}
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			// 如果是文件，则压缩
			out.putNextEntry(new ZipEntry(base));
			// 生成下一个压缩节点
			FileInputStream in = new FileInputStream(f);
			// 读取文件内容
			int len;
			byte[] buf = new byte[BUFFER];
			while ((len = in.read(buf, 0, BUFFER)) != -1) {
				out.write(buf, 0, len);
				// 写入到压缩包
			}
			in.close();
			out.closeEntry();
		}
	}

	/**
	 * * 解压缩zip文件 * * 文件名添加时间戳，可以解压包含中文的
	 * 
	 * @param fileName
	 *            * 要解压的文件名 包含路径 如："c:\\test.zip" *
	 * @param filePath
	 *            * 解压后存放文件的路径 如："c:\\temp" *
	 * @throws Exception
	 */
	public static List<String> unZip(String fileName, String destFilePath)
			throws Exception {
		List<String> fileNames = new ArrayList<String>();
		ZipFile zipFile = new ZipFile(fileName, "GBK");
		// 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			String fileNme = "";
			String extNme = "";
			if (entry.getName().lastIndexOf(".") > 0) {
				fileNme = entry.getName().substring(0,
						entry.getName().lastIndexOf("."));
				extNme = entry.getName().substring(
						entry.getName().lastIndexOf("."));
			} else {
				fileNme = entry.getName();
			}
			// 加时间戳
			String timeStr = DateUtils.date2Str(new Date(),DateUtils.PATTERN_YYYYMMDDHHMMSSSS);
			String newFile = destFilePath + fileNme + "-" + timeStr + extNme;
			if (entry.isDirectory()) {
				File dir = new File(newFile);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry));
			File file = new File(newFile);
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			fileNames.add(newFile);
			bos.flush();
			bos.close();
			bis.close();
			System.out.println("解压文件：" + file.getName());
		}
		zipFile.close();
		return fileNames;
	}

	/**
	 * * 解压缩zip文件 * * 文件名不添加时间戳，可以解压包含中文的
	 * 
	 * @param fileName
	 *            * 要解压的文件名 包含路径 如："c:\\test.zip" *
	 * @param filePath
	 *            * 解压后存放文件的路径 如："c:\\temp" *
	 * @throws Exception
	 */
	public static List<String> unZipFile(String fileName, String destFilePath)
			throws Exception {
		List<String> fileNames = new ArrayList<String>();
		ZipFile zipFile = new ZipFile(fileName, "GBK");
		// 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			String filename = entry.getName().replace("/",
					FileUtil.SYSTEM_FILE_SEPARATOR);// 防止文件流当做文件夹处理，替换/为\
			String fileNme = "";
			String extNme = "";
			if (filename.lastIndexOf(".") > 0) {
				fileNme = filename.substring(0, filename.lastIndexOf("."));
				extNme = filename.substring(filename.lastIndexOf("."));
			} else {
				fileNme = filename;
			}
			// 加时间戳
			// String timeStr = Constants.sdf1.format(new Date());
			String newFile = destFilePath + fileNme + extNme;
			if (entry.isDirectory()) {
				File dir = new File(newFile);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry));
			File file = new File(newFile);
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
			byte[] buf = new byte[BUFFER];
			int len = 0;
			while ((len = bis.read(buf, 0, BUFFER)) != -1) {
				fos.write(buf, 0, len);
			}
			fileNames.add(newFile);
			bos.flush();
			bos.close();
			bis.close();
			System.out.println("解压文件：" + file.getName());
		}
		zipFile.close();
		return fileNames;
	}

    /**
     * 获取zip里的问价内容
     *
     * @param file 解析的压缩包绝对路径
     * @return map<文件名称, 内容>
     * @throws Exception
     */
    public static Map<String, StringBuffer> readZipFile(String file) throws Exception {

        Map<String, StringBuffer> map = new HashMap<>();
        java.util.zip.ZipFile zf = new java.util.zip.ZipFile(file, StandardCharsets.UTF_8);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in, StandardCharsets.UTF_8);
        java.util.zip.ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                StringBuffer sb = new StringBuffer();
                map.put(ze.getName(), sb);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
            }
        }
        if (null != zin) {
            zin.closeEntry();
        }
        if (null != in) {
            in.close();
        }
        if (null != zf) {
            zf.close();
        }
        return map;
    }

    /**
     * 获得文件编码
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }

    /**
     * 压缩流里的内容到zip
     *
     * @param input        输入流
     * @param zipStream    zip输出流
     * @param zipEntryName 文件名
     * @throws Exception
     */
    public static void compressZip(InputStream input, java.util.zip.ZipOutputStream zipStream, String zipEntryName)
        throws Exception {

        byte[] bytes = null;
        BufferedInputStream bufferStream = null;
        try {
            if (input == null) {
                throw new Exception("获取压缩的数据项失败! 数据项名为：" + zipEntryName);
            }
            // 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样
            java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry("" + zipEntryName);

            // 定位到该压缩条目位置，开始写入文件到压缩包中
            zipStream.putNextEntry(zipEntry);

            // 读写缓冲区
            bytes = new byte[1024];

            // 输入缓冲流
            bufferStream = new BufferedInputStream(input);
            int read = 0;
            while ((read = bufferStream.read(bytes)) != -1) {
                zipStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferStream) {
                    bufferStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public static void main(String[] args) {
		try {
           /* readZipFile("F:\\2-apache-tomcat-8.5.38\\apache-tomcat-8.5.38\\webapps\\rolldata\\uploadFile\\temp\\资源1.zip");*/
            String s = codeString(
                "C:\\Users\\Administrator\\Downloads\\资源 (1)\\空可视化.wdbi");
            System.out.println(s);
        } catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
}
