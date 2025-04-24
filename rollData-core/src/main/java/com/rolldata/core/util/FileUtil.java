package com.rolldata.core.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Title: FileUtil
 * @Description: 文件操作类
 * @Company:www.wrenchdata.com
 * @author zhaibx
 * @date 2020年6月19日
 * @version V1.0
 */
public class FileUtil {

	/**
	 * 保存文件等,处理文件的后缀
	 */
	public static final String HANDLE_SUFFIX_JSON = ".json";

	public static final String HANDLE_SUFFIX_JS = ".js";

	public static final String CHARACTER_ENCODING_UTF8 = "UTF-8";

	public static final String HANDLE_SUFFIX_HTML = ".html";

	public static final String HANDLE_SUFFIX_DATA = ".data";
	/**
	 * 私有构造方法
	 */
	private FileUtil() {

	}
	/**
	 * 系统路径符 window中 / unix 中 \
	 */
	public final static String SYSTEM_FILE_SEPARATOR = System
			.getProperty("file.separator");
	// 验证字符串是否为正确路径名的正则表达式
	private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";
	boolean flag = false;
	File file;

	/**
	 * 传入路径，返回是否是绝对路径，是绝对路径返回true，反之
	 * 
	 * @param path
	 * @return
	 * @since 2015年4月21日
	 */
	public static boolean isAbsolutePath(String path) {
		if (path.startsWith("/") || path.indexOf(":") > 0) {
			return true;
		}
		return false;
	}

	public boolean deleteFolder(String deletePath) {// 根据路径删除指定的目录或文件，无论存在与否
		flag = false;
		if (deletePath.matches(matches)) {
			file = new File(deletePath);
			if (!file.exists()) {// 判断目录或文件是否存在
				return flag; // 不存在返回 false
			} else {

				if (file.isFile()) {// 判断是否为文件
					return deleteFile(deletePath);// 为文件时调用删除文件方法
				} else {
					return deleteDirectory(deletePath);// 为目录时调用删除目录方法
				}
			}
		} else {
			//System.out.println("要传入正确路径！");
			return false;
		}
	}

	public boolean deleteFile(String filePath) {// 删除单个文件
		flag = false;
		file = new File(filePath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();// 文件删除
			flag = true;
		}
		return flag;
	}

	public boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		File dirFile = new File(dirPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
		for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
			if (files[i].isFile()) {// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				//System.out.println(files[i].getAbsolutePath() + " 删除成功");
				if (!flag)
					break;// 如果删除失败，则跳出
			} else {// 运用递归，删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;// 如果删除失败，则跳出
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {// 删除当前目录
			return true;
		} else {
			return false;
		}
	}

	// 创建单个文件
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {// 判断文件是否存在
			//System.out.println("目标文件已存在" + filePath);
			return false;
		}
		if (filePath.endsWith(File.separator)) {// 判断文件是否为目录
			//System.out.println("目标文件不能为目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {// 判断目标文件所在的目录是否存在
			// 如果目标文件所在的文件夹不存在，则创建父文件夹
			//System.out.println("目标文件所在目录不存在，准备创建它！");
			if (!file.getParentFile().mkdirs()) {// 判断创建目录是否成功
				//System.out.println("创建目标文件所在的目录失败！");
				return false;
			}
		}
		try {
			if (file.createNewFile()) {// 创建目标文件
				//System.out.println("创建文件成功:" + filePath);
				return true;
			} else {
				//System.out.println("创建文件失败！");
				return false;
			}
		} catch (IOException e) {// 捕获异常
			e.printStackTrace();
			//System.out.println("创建文件失败！" + e.getMessage());
			return false;
		}
	}

	// 创建临时文件
	public static String createTempFile(String prefix, String suffix, String dirName) {
		File tempFile = null;
		if (dirName == null) {// 目录如果为空
			try {
				tempFile = File.createTempFile(prefix, suffix);// 在默认文件夹下创建临时文件
				return tempFile.getCanonicalPath();// 返回临时文件的路径
			} catch (IOException e) {// 捕获异常
				e.printStackTrace();
				System.out.println("创建临时文件失败：" + e.getMessage());
				return null;
			}
		} else {
			// 指定目录存在
			File dir = new File(dirName);// 创建目录
			if (!dir.exists()) {
				// 如果目录不存在则创建目录
				if (FileUtil.mkDir(dirName)) {
					System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
					return null;
				}
			}
			try {
				tempFile = File.createTempFile(prefix, suffix, dir);// 在指定目录下创建临时文件
				return tempFile.getCanonicalPath();// 返回临时文件的路径
			} catch (IOException e) {// 捕获异常
				e.printStackTrace();
				System.out.println("创建临时文件失败!" + e.getMessage());
				return null;
			}
		}
	}

	/**
	 * 在指定的位置创建指定的文件
	 *
	 * @param filePath
	 *            完整的文件路径
	 * @param mkdir
	 *            是否创建相关的文件夹
	 * @throws Exception
	 */
	public static void mkFile(String filePath, boolean mkdir) throws Exception {
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		file.createNewFile();
		file = null;
	}

	/**
	 * 在指定的位置创建文件夹
	 *
	 * @param dirPath
	 *            文件夹路径
	 * @return 若创建成功，则返回True；反之，则返回False
	 */
	public static boolean mkDir(String dirPath) {
		File dir = new File(dirPath);
		if (dir.exists()) {// 判断目录是否存在
//			System.out.println("创建目录失败，目标目录已存在！");
			return false;
		}
		if (!dirPath.endsWith(File.separator)) {// 结尾是否以"/"结束
			dirPath = dirPath + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
//			System.out.println("创建目录成功！" + dirPath);
			return true;
		} else {
//			System.out.println("创建目录失败！");
			return false;
		}
	}

	/**
	 * 删除指定的文件
	 *
	 * @param filePath
	 *            文件路径
	 *
	 * @return 若删除成功，则返回True；反之，则返回False
	 *
	 */
	private static boolean delFile2(String filePath) {
		return new File(filePath).delete();
	}

	public static void delFile(String filePath) {
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();// 文件删除
		}
	}
	/**
	 * 删除指定的文件夹
	 *
	 * @param dirPath
	 *            文件夹路径
	 * @param delFile
	 *            文件夹中是否包含文件
	 * @return 若删除成功，则返回True；反之，则返回False
	 *
	 */
	public static boolean delDir(String dirPath, boolean delFile) {
		if (delFile) {
			File file = new File(dirPath);
			if (file.isFile()) {
				return file.delete();
			} else if (file.isDirectory()) {
				if (file.listFiles().length == 0) {
					return file.delete();
				} else {
					int zfiles = file.listFiles().length;
					File[] delfile = file.listFiles();
					for (int i = 0; i < zfiles; i++) {
						if (delfile[i].isDirectory()) {
							delDir(delfile[i].getAbsolutePath(), true);
						}
						delfile[i].delete();
					}
					return file.delete();
				}
			} else {
				return false;
			}
		} else {
			return new File(dirPath).delete();
		}
	}

	/**
	 * 复制文件/文件夹 若要进行文件夹复制，请勿将目标文件夹置于源文件夹中
	 * 
	 * @param source
	 *            源文件（夹）
	 * @param target
	 *            目标文件（夹）
	 * @param isFolder
	 *            若进行文件夹复制，则为True；反之为False
	 * @throws Exception
	 */
	public static void copy(String source, String target, boolean isFolder) throws Exception {
		if (isFolder) {
			(new File(target)).mkdirs();
			File a = new File(source);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (source.endsWith(File.separator)) {
					temp = new File(source + file[i]);
				} else {
					temp = new File(source + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(target + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copy(source + "/" + file[i], target + "/" + file[i], true);
				}
			}
		} else {
			int byteread = 0;
			File oldfile = new File(source);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(source);
				File file = new File(target);
				file.getParentFile().mkdirs();
				file.createNewFile();
				FileOutputStream fs = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		}
	}

	/**
	 * 移动指定的文件（夹）到目标文件（夹）
	 * 
	 * @param source
	 *            源文件（夹）
	 * @param target
	 *            目标文件（夹）
	 * @param isFolder
	 *            若为文件夹，则为True；反之为False
	 * @return
	 * @throws Exception
	 */
	public static boolean move(String source, String target, boolean isFolder) throws Exception {
		copy(source, target, isFolder);
		if (isFolder) {
			return delDir(source, true);
		} else {
			return delFile2(source);
		}
	}

	
	/**
	 * 获取文件名称[不含后缀名]
	 * 不去掉文件目录的空格
	 * @param
	 * @return String
	 */
	public static String getFilePrefix2(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}
	

	/**
	 * 读取文件的内容
	 * 
	 * @param path 要读取文件的绝对路径
	 * @return 以行读取文件后的内容
	 * @throws IOException
	 */
	public static final String getFileContent(String path) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8")); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				// 判断读取到的字符串是否不为空
				while (line != null) {
					filecontent += line + "\n";
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
			}

		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}
	
	/**
	 * 新建文件(带内容) -- 文件夹需存在
	 * 
	 * @param filePath
	 *            文件路径及名称，如：F:\\a.txt
	 * @param fileContent
	 *            要写入的文件内容
	 */
	public static void createFile(String filePath, String fileContent) {
		try {
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 新建文件(带内容) -- 文件夹需存在
	 *
	 * @param filePath
	 *            文件路径及名称，如：F:\\a.txt
	 * @param fileContent
	 *            要写入的文件内容
     * @param append 是否追加到文件末尾
	 */
	public static void createFileUtf8(String filePath, String fileContent, Boolean append) {
		// 表单内容json存成文件
		buildFile(filePath, false);
		BufferedWriter out =null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "UTF-8"));
			out.write(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	/**
	 * 生产文件 如果文件所在路径不存在则生成路径
	 * 
	 * @param fileName
	 *            文件名 带路径
	 * @param isDirectory
	 *            是否为路径
	 */
	public static File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	}
	
	/**
	 * 根据路径删除指定的目录或文件，无论存在与否  
	 * @param sPath
	 * @return
	 */
	public boolean DeleteFolder(String sPath) {  
	    File file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return false;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}
	/**
	 * 递归删除文件夹下所有文件及该文件夹
	 * 
	 * @param delpath
	 * @return
	 * @throws Exception
	 */
	public static boolean deletefile(String delpath)
			throws FileNotFoundException {

		File file = new File(delpath);
		// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(delpath + "\\" + filelist[i]);
				if (!delfile.isDirectory()) {
					delfile.delete();
				} else if (delfile.isDirectory()) {
					deletefile(delpath + "\\" + filelist[i]);
				}
			}
			file.delete();
		}
		return true;
	}
	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            复制后路径 如：f:/fqf.txt
	 * @throws Exception
	 *             未处理异常
	 * 
	 * 
	 * @return fileUrl 新的文件地址
	 */
	public static String copyFile(String oldPath, String newPath)
			throws Exception {
		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		System.out.println("old path " + oldPath);
		System.out.println("new path " + newPath);

		if (oldfile.exists()) { // 文件存在时

			InputStream inStream = new FileInputStream(oldPath); // 读入原文件

			// 目标目录不存在，则建立目录
			File target = new File(newPath.substring(0,
					newPath.lastIndexOf(SYSTEM_FILE_SEPARATOR)));
			if (!target.exists()) {
				target.mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(newPath);

			byte[] buffer = new byte[1444];
			int length;
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.flush();
			fs.close();
			inStream = null;
			fs = null;
		} else {
			throw new FileNotFoundException("文件未找到");
		}
		return newPath;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldFile
	 *            原文件路径
	 * @param newPath
	 *            复制后路径
	 * @return 复制后路径
	 * @throws Exception
	 */
	public static String copyFile(CommonsMultipartFile oldFile, String newPath)
			throws Exception {
		int bytesum = 0;
		int byteread = 0;
		System.out.println("new path " + newPath);

		InputStream inStream = oldFile.getInputStream(); // 读入原文件

		// 目标目录不存在，则建立目录
		File target = new File(newPath.substring(0,
				newPath.lastIndexOf(SYSTEM_FILE_SEPARATOR)));
		if (!target.exists()) {
			target.mkdirs();
		}
		FileOutputStream fs = new FileOutputStream(newPath);

		byte[] buffer = new byte[1444];
		while ((byteread = inStream.read(buffer)) != -1) {
			bytesum += byteread; // 字节数 文件大小
			fs.write(buffer, 0, byteread);
		}
		inStream.close();
		fs.flush();
		fs.close();
		inStream = null;
		fs = null;
		return newPath;
	}
	
	/**
	 * 读取某个文件夹下的所有文件
	 * @param filepath 文件或文件夹路径
	 * @param isContainChildren 是否包含子级目录
	 * @param regexp 正则表达式
	 * @param regexpExc 正则表达式（排除）
	 * @param suffix 后缀（txt/csv/xls,xlsx）
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
    public static List<String> readfile(String filepath,boolean isContainChildren,String regexp,String regexpExc,String suffix) throws FileNotFoundException, IOException {
    	List<String> returnList = new ArrayList<String>();
        File file = new File(filepath);
		if (!file.isDirectory()) {
			dealFile(file,regexp,regexpExc,suffix,returnList);
		} else if (file.isDirectory()) {
		    String[] filelist = file.list();
		    for (int i = 0; i < filelist.length; i++) {
		        File readfile = new File(filepath + SYSTEM_FILE_SEPARATOR + filelist[i]);
		        if (!readfile.isDirectory()) {
		        	dealFile(readfile,regexp,regexpExc,suffix,returnList);
		        }else if (readfile.isDirectory()) {
		        	if(isContainChildren) {
		        		readfile(filepath + SYSTEM_FILE_SEPARATOR + filelist[i], returnList,regexp,regexpExc,suffix);
		        	}
		        }
		    }
		}
        return returnList;
    }

    /**
     * 是文件类型的进行此处理
     * @param file
     * @param regexp
     * @param regexpExc
     * @param suffix
     * @param returnList
     */
    private static void dealFile(File file, String regexp, String regexpExc, String suffix, List<String> returnList) {
    	String fileName = file.getName();
	    String absolutePath = file.getAbsolutePath();
	  //截取后缀并判断
    	String tempSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
    	tempSuffix = tempSuffix.toLowerCase();
    	if(StringUtil.isNotEmpty(suffix)) {
    		String[] suffixs = suffix.split(",");
    		for (int i = 0; i < suffixs.length; i++) {
    			if(tempSuffix.equals(suffixs[i])) {
    				dealMatches(fileName,absolutePath,regexp,regexpExc,returnList);
    			}
    		}
    	}else {
    		dealMatches(fileName,absolutePath,regexp,regexpExc,returnList);
    	}
	}

    /**
     * 文件名处理匹配正则表达式
     * @param fileName
     * @param absolutePath
     * @param regexp
     * @param regexpExc
     * @param returnList
     */
	private static void dealMatches(String fileName,String absolutePath, String regexp, String regexpExc, List<String> returnList) {
		if(StringUtil.isNotEmpty(regexp)) {
//    		模糊匹配
//    		Pattern r = Pattern.compile(regexp);
//    		Matcher m = r.matcher(fileName);
//    		System.out.println(m.find());
			//全匹配
			if(fileName.matches(regexp)) {
				if(StringUtil.isNotEmpty(regexpExc)) {
					if(!fileName.matches(regexpExc)) {
						returnList.add(absolutePath);
					}
				}else {
					returnList.add(absolutePath);
				}
			}
		}else {
			returnList.add(absolutePath);
		}
	}

	/**
     * 读取某个文件夹下的所有文件
     * @param filepath
     * @param returnList
     */
	private static void readfile(String filepath, List<String> returnList,String regexp,String regexpExc, String suffix) {
		File file = new File(filepath);
		if (!file.isDirectory()) {
			dealFile(file,regexp,regexpExc,suffix,returnList);
		} else if (file.isDirectory()) {
		    String[] filelist = file.list();
		    for (int i = 0; i < filelist.length; i++) {
		        File readfile = new File(filepath + SYSTEM_FILE_SEPARATOR + filelist[i]);
		        if (!readfile.isDirectory()) {
		        	dealFile(readfile,regexp,regexpExc,suffix,returnList);
		        } else if (readfile.isDirectory()) {
		            readfile(filepath + SYSTEM_FILE_SEPARATOR + filelist[i],returnList,regexp,regexpExc,suffix);
		        }
		    }
		}
	}
	
	/**
	 * 读取文件的第一行内容
	 * 
	 * @param path 要读取文件的绝对路径
	 * @param encode 编码格式
	 * @return 以行读取文件后的内容
	 * @throws IOException
	 */
	public static final String getFileContentByFirstLine(String path,String encode) throws IOException {
		String filecontent = "";
		try {
			File f = new File(path);
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),encode)); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
					filecontent += line;
				br.close(); // 关闭BufferedReader对象
			}

		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}
	
	/**
	 * 读取文件的内容
	 * 
	 * @param path 要读取文件的绝对路径
	 * @param encode 编码格式
	 * @param lineNum 读取行数
	 * @return 以行读取文件后的内容
	 * @throws IOException
	 */
	public static final List<String> getFileContent(String path,String encode,int lineNum) throws IOException {
		List<String> filecontent = new ArrayList<String>();
		try {
			File f = new File(path);
			if (f.exists()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),encode)); // 建立BufferedReader对象，并实例化为br
				String line = br.readLine(); // 从文件读取一行字符串
				int num=1;
				// 判断读取到的字符串是否不为空
				while (line != null) {
					filecontent.add(line);
					if(num==lineNum) {
						break;
					}
					num++;
					line = br.readLine(); // 从文件中继续读取一行数据
				}
				br.close(); // 关闭BufferedReader对象
			}
		} catch (IOException e) {
			throw e;
		}
		return filecontent;
	}
	
	/**
	 * 新建文件(带内容) -- 文件夹需存在
	 *
	 * @param filePath
	 *            文件路径及名称，如：F:\\a.txt
	 * @param fileContent
	 *            要写入的文件内容
     * @param append 是否追加到文件末尾
     * @param encode 编码格式ASCII;GB2312;GBK;UNICODE;UTF-8
	 */
	public static void createFile(String filePath, String fileContent, Boolean append, String encode) {
		// 表单内容json存成文件
		buildFile(filePath, false);
		BufferedWriter out =null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encode));
			out.write(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
	}

	/**
	 * 根据编码类型判断编码格式
	 * @param encodeType 编码格式1,ASCII;2,GB2312;3,GBK;4,UNICODE;5,UTF-8;6,ISO-8859-1
	 * @return
	 */
	public static String judgementEncodeType(String encodeType) {
		String encode="";
		switch (encodeType) {//编码格式1,ASCII;2,GB2312;3,GBK;4,UNICODE;5,UTF-8;6,ISO-8859-1
		case "1":
			encode="ASCII";
			break;
		case "2":
			encode="GB2312";
			break;
		case "3":
			encode="GBK";
			break;
		case "4":
			encode="UNICODE";
			break;
		case "5":
			encode="UTF-8";
			break;
		case "6":
			encode="ISO-8859-1";
			break;
		default:
			encode="UTF-8";
			break;
		}
		return encode;
	}
	public static void createFile(byte[] bytes,String filePath) throws Exception{
		BufferedOutputStream bw = null;
		try {
			// 写入文件
			bw = new BufferedOutputStream(new FileOutputStream(filePath));
			bw.write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 以行为单位读取文件，并将文件的每一行格式化到ArrayList中，常用于读面向行的格式化文件
	 */
	public static ArrayList<String> readFileByLines(String filePath) throws Exception {
		ArrayList<String> listStr = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String tempString = null;
			int flag = 0;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				if (tempString.trim().equals(""))
					continue;
				if (tempString.substring(tempString.length() - 1).equals(";")) {
					if (flag == 1) {
						sb.append(tempString);
						listStr.add(sb.substring(0, sb.length() - 1));
						sb.delete(0, sb.length());
						flag = 0;
					} else
						listStr.add(tempString.substring(0, tempString.length() - 1));
				} else {
					flag = 1;
					sb.append(tempString);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return listStr;
	}

	public static void main(String[] args) {
//		String dirName = "E:/createFile/";// 创建目录
//		FileUtil.mkDir(dirName);// 调用方法创建目录
//		String fileName = dirName + "/file1.txt";// 创建文件
//		FileUtil.createFile(fileName);// 调用方法创建文件
//		String prefix = "temp";// 创建临时文件
//		String surfix = ".txt";// 后缀
//		for (int i = 0; i < 10; i++) {// 循环创建多个文件
//			System.out.println("创建临时文件: "// 调用方法创建临时文件
//					+ FileUtil.createTempFile(prefix, surfix, dirName));
//		}
		try {
			List<String> fileList = readfile("D:\\temp",false,null,null,"xls,xlsx");
			for (int i = 0; i < fileList.size(); i++) {
				System.out.println(fileList.get(i));
			}
//			String content = getFileContentByFirstLine("D:\\temp\\工作簿2020-02-12.csv","UTF-8");
//			System.out.println(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}