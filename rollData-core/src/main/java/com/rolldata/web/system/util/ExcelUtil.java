package com.rolldata.web.system.util;

import com.rolldata.core.util.DateUtils;
import com.rolldata.core.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ExcelUtil
 * @Description: Excel工具类
 * @Company:www.wrenchdata.com
 * @author shenshilong
 * @date 2018年10月8日
 * @version V1.0
 */
public class ExcelUtil {

    private static Logger logger = LogManager.getLogger(ExcelUtil.class);

	/**
	 * 2003 xls
	 */
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";

	/**
	 * 2007 xlsx
	 */
	public static final String OFFICE_EXCEL_2007_POSTFIX = "xlsx";

    /**
     * 列出现过的记录下来(可以初始化一部分)
     */
	static private final Map<Integer, String> colRangeMap = new HashMap<>();

    /**
     * 列字母对应列号(从0开始,A=1)
     */
    public static final Map<String, Integer> RANGE_COL_MAP = new HashMap<>(32);

    public static final String[] LETTERS = {
        "A","B","C","D","E","F",
        "G","H","I","J","K","L",
        "M","N","O","P","Q","R",
        "S","T","U","V","W","X",
        "Y","Z"
    };

    public static final DecimalFormat CELL_DECIMALFORMAT = new DecimalFormat("#.######");

    static {

        // 初始化常用Excel列
        for (int i = 0; i < LETTERS.length; i++) {
            colRangeMap.put(new Integer(i), LETTERS[i]);
        }

        for (int i = 0; i < LETTERS.length; i++) {
            RANGE_COL_MAP.put(LETTERS[i], new Integer(i + 1));
        }
    }

	/**
	 * 判断文件是否是excel
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void checkFile(File file) throws IOException {

		// 判断文件是否存在
		if (null == file) {
			logger.error("文件不存在！");
			throw new FileNotFoundException("文件不存在！");
		}
		String fileName = file.getName();

		// 判断文件是否是excel文件
		if (!fileName.endsWith(OFFICE_EXCEL_2003_POSTFIX) && !fileName.endsWith(OFFICE_EXCEL_2007_POSTFIX)) {
			logger.error(fileName + "不是excel文件");
			throw new IOException(fileName + "不是excel文件");
		}
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 * 
	 * @param file
	 * @return
	 */
	public static Workbook getWorkBook(File file) {

		// 获得文件名
		String fileName = file.getName();

		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			FileInputStream in = new FileInputStream(file);

			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith(OFFICE_EXCEL_2003_POSTFIX)) {
				// 2003
				workbook = new HSSFWorkbook(in);
			} else if (fileName.endsWith(OFFICE_EXCEL_2007_POSTFIX)) {
				// 延迟解析比率，解决报错 java.io.IOException: Zip bomb detected
				ZipSecureFile.setMinInflateRatio(-1.0d);
				// 2007
				workbook = new XSSFWorkbook(in);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return workbook;
	}

	/**
	 * 读入excel文件，解析后返回
	 *
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> readExcel(File file) throws IOException {

		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				// 获得当前sheet的开始行
				int firstRowNum = sheet.getFirstRowNum();
				// 获得当前sheet的结束行
				int lastRowNum = sheet.getLastRowNum();
				// 循环除了第一行的所有行
				for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
					// 获得当前行
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					// 获得当前行的开始列
					int firstCellNum = row.getFirstCellNum();
					// 获得当前行的列数
					int lastCellNum = row.getPhysicalNumberOfCells();
					String[] cells = new String[row.getPhysicalNumberOfCells()];
					// 循环当前行
					for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
						Cell cell = row.getCell(cellNum);
						cells[cellNum] = getCellValue(cell);
					}
					list.add(cells);
				}
			}
			workbook.close();
		}
		return list;
	}

	/**
	 * 获取单元格数据，默认去掉空格回车换行等字符
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		return getCellValue(cell,true);
	}

	/**
	 * 获取单元格数值
	 * @param cell
	 * @param isReplaceBlank 是否去掉空格回车换行等字符
	 * @return
	 */
	public static String getCellValue(Cell cell,boolean isReplaceBlank) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}

		// 判断数据的类型
		switch (cell.getCellType()) {
			case NUMERIC: // 数字
                try {
                    cellValue = getCellNumberString(cell);
                } catch (Exception e) {
                    logger.error("读取数字失败,默认给0", e);
                    cellValue = "0";
                }
                break;
			case STRING: // 字符串
				if(isReplaceBlank){
					cellValue = StringUtil.replaceBlank(String.valueOf(cell.getStringCellValue()));
				}else{
					cellValue = String.valueOf(cell.getStringCellValue());
				}
				break;
			case BOOLEAN: // Boolean
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case FORMULA: // 公式

                /*
                原来的代码，放在switch上面的：
                if (cell.getCellType().equals(CellType.FORMULA)) {
                    cell.setCellType(CellType.NUMERIC);
                }
                 */
                if (cell instanceof XSSFCell) {
                    XSSFCell xssfCell = (XSSFCell) cell;
                    CellType valueType = xssfCell.getCachedFormulaResultType();
                    switch (valueType) {
                        case NUMERIC:
                            double numValue = cell.getNumericCellValue();
                            cellValue = CELL_DECIMALFORMAT.format(numValue).trim();
                            break;
                        case STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        case BOOLEAN:
                            break;
                    }
                } else {
                    try {
                        cellValue = cell.getStringCellValue();
                    } catch (IllegalStateException e) {
                        try {
                            double numValue = cell.getNumericCellValue();
                            cellValue = CELL_DECIMALFORMAT.format(numValue).trim();
                        } catch (Exception exception) {
                            logger.error("Excel获取公式的值失败", exception);
                            cellValue = String.valueOf(cell.getCellFormula());
                        }
                    }
                }
				break;
			case BLANK: // 空值
				cellValue = "";
				break;
			case ERROR: // 故障
				cellValue = "非法字符";
				break;
			default:
				cellValue = "";
				break;
		}
		return cellValue;
	}

    /**
     * 获取Excel字体大小转成像素
     * <p>
     *     Excle字号      html像素<br/>
     *     |5~6         |6      |<br/>
     *     |6~8         |8      |<br/>
     *     |8~9         |9      |<br/>
     *     |9~10        |10     |<br/>
     *     |10~12       |12     |<br/>
     *     |14          |14     |<br/>
     *     |16          |16     |<br/>
     *     |18          |18     |<br/>
     *
     * </p>
     * @param font
     * @return
     */
	public static double getOfficeExcelFontTurnPixel (Font font) {
     
	    double fontSize = 0.0d;
        fontSize = (double)font.getFontHeightInPoints();
        
        //打算这么干,这段代码是在poi源码里弄出来的
        /*if (font instanceof org.apache.poi.xssf.usermodel.XSSFFont) {
            XSSFFont xssfFont = (XSSFFont) font;
            CTFont ct = xssfFont.getCTFont();
            CTFontSize size = ct.sizeOfSzArray() == 0 ? null : ct.getSzArray(0);
            if (size != null) {
                fontSize = size.getVal();
            }
        }*/
        if (5.0 <= fontSize && fontSize <= 6.0) {
            fontSize = 6.0;
        } else if (6.0 < fontSize && fontSize <= 8.0) {
            fontSize = 8.0;
        } else if (8.0 < fontSize && fontSize <= 9.0) {
            fontSize = 9.0;
        } else if (9.0 < fontSize && fontSize <= 10.0) {
            fontSize = 10.0;
        } else if (10.0 < fontSize && fontSize <= 12.0) {
            fontSize = 12.0;
        } else if (12.0 < fontSize && fontSize <= 14.0) {
            fontSize = 14.0;
        } else if (14.0 < fontSize && fontSize <= 16.0) {
            fontSize = 16.0;
        } else if (16.0 < fontSize && fontSize <= 18.0) {
            fontSize = 18.0;
        } else if (18.0 < fontSize && fontSize <= 20.0) {
            fontSize = 20.0;
        } else if (20.0 < fontSize && fontSize <= 22.0) {
            fontSize = 22.0;
        } else if (22.0 < fontSize && fontSize <= 24.0) {
            fontSize = 24.0;
        } else if (24.0 < fontSize && fontSize <= 26.0) {
            fontSize = 26.0;
        } else if (26.0 < fontSize && fontSize <= 28.0) {
            fontSize = 28.0;
        } else if (28.0 < fontSize && fontSize <= 36.0) {
            fontSize = 36.0;
        } else if (36.0 < fontSize && fontSize <= 48.0) {
            fontSize = 48.0;
        } else if (48.0 < fontSize && fontSize <= 72.0) {
            fontSize = 72.0;
        }
        return fontSize;
    }
    
    /**
     * 像素字体转成Excel字体大小
     *
     * @param fontSize
     * @return
     */
    public static double getPixelTurnOfficeExcelFont (double fontSize) {
        
        if (5.0 <= fontSize && fontSize <= 6.0) {
            fontSize = 6.0;
        } else if (6.0 < fontSize && fontSize <= 8.0) {
            fontSize = 8.0;
        } else if (8.0 < fontSize && fontSize <= 9.0) {
            fontSize = 9.0;
        } else if (9.0 < fontSize && fontSize <= 10.0) {
            fontSize = 10.0;
        } else if (10.0 < fontSize && fontSize <= 12.0) {
            fontSize = 12.0;
        } else if (12.0 < fontSize && fontSize <= 14.0) {
            fontSize = 14.0;
        } else if (14.0 < fontSize && fontSize <= 16.0) {
            fontSize = 16.0;
        } else if (16.0 < fontSize && fontSize <= 18.0) {
            fontSize = 18.0;
        } else if (18.0 < fontSize && fontSize <= 20.0) {
            fontSize = 20.0;
        } else if (20.0 < fontSize && fontSize <= 22.0) {
            fontSize = 22.0;
        } else if (22.0 < fontSize && fontSize <= 24.0) {
            fontSize = 24.0;
        } else if (24.0 < fontSize && fontSize <= 26.0) {
            fontSize = 26.0;
        } else if (26.0 < fontSize && fontSize <= 28.0) {
            fontSize = 28.0;
        } else if (28.0 < fontSize && fontSize <= 36.0) {
            fontSize = 36.0;
        } else if (36.0 < fontSize && fontSize <= 48.0) {
            fontSize = 48.0;
        } else if (48.0 < fontSize && fontSize <= 72.0) {
            fontSize = 72.0;
        }
        return fontSize;
    }
    
    /**
	 * 读入excel文件，返回sheet页名列表
	 *
	 * @param file
	 * @throws IOException
	 */
	public static List<String> readExcelReturnSheetsName(File file) throws IOException {

		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String> list = new ArrayList<String>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				list.add(sheet.getSheetName());
			}
			workbook.close();
		}
		return list;
	}
	
	/**
	 * 读入excel文件，匹配sheet页名称，返回该sheet页指定行有效数据
	 *
	 * @param file 文件
	 * @param sheetName 指定sheet页
	 * @param lineNum 指定读取行
	 * @throws IOException
	 */
	public static List<String> readExcelReturnFields(File file,String sheetName,int lineNum) throws IOException {

		// 检查文件
		checkFile(file);
		// 获得Workbook工作薄对象
		Workbook workbook = getWorkBook(file);
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String> fieldlist = new ArrayList<String>();
		if (workbook != null) {
			for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
				// 获得当前sheet工作表
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if (sheet == null) {
					continue;
				}
				if(sheet.getSheetName().equals(sheetName)) {
					// 获得当前sheet的开始行
					int firstRowNum = StringUtil.isNotEmpty(lineNum) ? (lineNum-1) : sheet.getFirstRowNum();//减一，下标从0开始的，行号从1
					// 获得当前sheet的结束行
					int lastRowNum = sheet.getLastRowNum();
					// 循环所有行
					for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
						if(rowNum == firstRowNum) {//只获取第一行
							// 获得当前行
							Row row = sheet.getRow(rowNum);
							if (row == null) {
								continue;
							}
							// 获得当前行的开始列
							int firstCellNum = row.getFirstCellNum();
							// 获得当前行的列数
//							int lastCellNum = row.getPhysicalNumberOfCells();
							int lastCellNum = row.getLastCellNum();
//						String[] cells = new String[row.getPhysicalNumberOfCells()];
							// 循环当前行
							for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
								Cell cell = row.getCell(cellNum);
								fieldlist.add(getCellValue(cell));
							}
							break;
						}
					}
				}
			}
			workbook.close();
		}
		return fieldlist;
	}

	/**
	 * 读取excel指定行开始，获取有效列的数据
	 * @param file 文件
	 * @param sheetName 指定的sheet页名称
	 * @param beginLine 读取起始行
	 * @param endLine 结束行
	 * @param lineName 列名集合
	 * @return
	 * @throws IOException 
	 */
	public static List<Map<String, Object>> readExcelReturnContent(File file, String sheetName, int beginLine, int endLine, String[] lineName) throws IOException {
		// 检查文件
				checkFile(file);
				// 获得Workbook工作薄对象
				Workbook workbook = getWorkBook(file);
				// 创建返回对象，把每行中的值作为一个对象，所有行作为一个集合返回
				List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
				if (workbook != null) {
					for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
						// 获得当前sheet工作表
						Sheet sheet = workbook.getSheetAt(sheetNum);
						if (sheet == null) {
							continue;
						}
						if(sheet.getSheetName().equals(sheetName)) {
							// 获得当前sheet的开始行
							int firstRowNum = beginLine==0 ? beginLine :beginLine-1;
							// 获得当前sheet的结束行,判断最后一行是否小于输入的，如果小于，就用获取的，比如只有20行，输入200行，就只读取20行的数据
							int lastRowNum = sheet.getLastRowNum() < endLine ? sheet.getLastRowNum() : endLine;
//							lastRowNum = endLine;
							// 循环所有行
							for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
									// 获得当前行
									Row row = sheet.getRow(rowNum);
									if (row == null) {
										continue;
									}
									// 获得当前行的开始列
									int firstCellNum = row.getFirstCellNum();
									// 获得当前行的列数
//									int lastCellNum = row.getPhysicalNumberOfCells();
//									int lastCellNum = row.getLastCellNum();
								int lastCellNum = lineName.length;
//								String[] cells = new String[row.getPhysicalNumberOfCells()];
								if(firstCellNum>=0){
									Map<String, Object> resultMap = new HashMap<String, Object>();
									// 循环当前行
									for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
										Cell cell = row.getCell(cellNum);
										resultMap.put(lineName[cellNum], getCellValue(cell));
									}
									dataList.add(resultMap);
								}
							}
						}
					}
					workbook.close();
				}
				
		return dataList;
	}

    /**
     * 根据列号,推算出字母
     *
     * @param num 列号
     * @return
     */
	public static String convertNum(int num) {

        String cacheCol = colRangeMap.get(num);
        if (StringUtil.isNotEmpty(cacheCol)) {
            return cacheCol;
        }
        String str = "";
        Integer keyNum = num;
        while (num >= 0) {
            str = "" + (char) (num % 26 + 97) + str;
            num = (int) (Math.floor(num / 26) - 1);
        }
        str = str.toUpperCase();
        colRangeMap.put(keyNum, str);
        return str;
    }
    
    /**
     * 根据列号字母,推算坐标
     *
     * @param colStr A->1,AA->27
     * @return
     */
    public static Integer excelColStrToNum(String colStr) {

        Integer cacheResult = RANGE_COL_MAP.get(colStr);
        if (null != cacheResult) {
            return cacheResult;
        }
        colStr = colStr.toUpperCase();
        int length = colStr.length();
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return Integer.valueOf(result);
    }

    /**
     * 获取sheet里最大列和最长行的行号
     *
     * @param sheet
     * @return
     */
    public static Map<String, Integer> buildMaxColumn(Sheet sheet) {
        
        Map<String, Integer> map = new HashMap<>();
        
        // 获取sheet的最后一个行号
        int rowCount = sheet.getLastRowNum();
        int maxColumnCount = 0;
        
        // sheet中最长行的行号
        int maxRowCount = 0;
        for (int i = 0; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            
            // 获取当前行的最后一个单元格
            int columnCount = row.getLastCellNum();
            if (columnCount > maxColumnCount) {
                maxColumnCount = columnCount;
            }
            maxRowCount = i;
        }
        map.put("maxColumnCount", maxColumnCount);
        map.put("maxRowCount", maxRowCount);
        return map;
    }

    /**
     * 获取单元格类型是数字的格子值
     *
     * @param cell 单元格
     * @return 分析类型返回相应格式的值
     */
    public static String getCellNumberString(Cell cell) {

        String cellValue = "";
        if (null == cell) {
            return cellValue;
        }
        double value = cell.getNumericCellValue();
        String formatStr = cell.getCellStyle().getDataFormatString();
        if (StringUtil.isNotEmpty(formatStr)) {

            // 49=@(数字文本) 0=General(通用)
            if (BuiltinFormats.getBuiltinFormat(49).equals(formatStr) ||
                BuiltinFormats.getBuiltinFormat(0).equals(formatStr)) {

                // 转成字符获取数据
                cell.setCellType(CellType.STRING);
                cellValue = cell.toString();
            }else if(formatStr.indexOf("%") >= 0){
				BigDecimal b = new BigDecimal(Double.toString(value));
				BigDecimal bd2 = b.setScale(4,BigDecimal.ROUND_HALF_UP);
				cellValue = bd2.toString();
			} else if (formatStr.indexOf("#,") >= 0 ||
                formatStr.indexOf("0.0") >= 0) {

                /**
                 * 把数字当成String来读，避免出现1读成1.0的情况，结尾是.0的，去掉.0
                 * 原代码留作还原：
                 * cellValue = cell.toString();
                 *  String replaceStr = ".0";
                 *  cellValue = cellValue.length() >= 3 ? replaceStr.equals(cellValue
                 *  .substring(cellValue.length() - 2))
                 *      ? cellValue
                 *      .replace(replaceStr, "") : cellValue : cellValue;
                 *  试用下面代码,把类型重新赋值,再取;可以不用顾虑.0情况
                 */
				cell.setCellType(CellType.STRING);
				cellValue = cell.toString();

				// 有时候取出来有科学计数法,但是真实数据0.23
				String doubleStr = String.valueOf(value);
				if (cellValue.indexOf('E') != -1) {
					cellValue = doubleStr;
				}
				if (cellValue.length() > doubleStr.length()) {
					cellValue = doubleStr;
				}
            } else {

                // 时间,1如果有冒号,并且不包含年;2带汉字`时`;
                if ((formatStr.indexOf(':') >= 0 &&
                    formatStr.indexOf("yy") == -1)
                    || formatStr.indexOf('时') >= 0) {

                    /**
                     * https://blog.csdn.net/liushimiao0104/article/details/82223511
                     * int format = cell.getCellStyle().getDataFormat();
                     * 原来仿照上面链接,只有部分好用
                     */
                    // 小时 时间 HH:mm
                    cellValue = DateUtils.date2Str(
                        org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value),
                        DateUtils.PATTERN_HH_MM
                    );
                } else if (formatStr.indexOf("yy") >= 0 && formatStr.indexOf(':') >= 0) {

                    // 带年月日的 yyyy年MM月dd日
                    cellValue = DateUtils.date2Str(
                        org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value),
                        DateUtils.PATTERN_YYYY_MM_DD_HH_MM_SS
                    );
                } else {
                    cellValue = DateUtils.date2Str(
                        org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value),
                        DateUtils.PATTERN_YYYY_MM_DD
                    );
                }
            }
        } else {
            cellValue = cell.toString();
        }
        return cellValue;
    }

	public static void createEmpty(String path) {

        try (Workbook workbook = new XSSFWorkbook()) {
            try (FileOutputStream fileOut = new FileOutputStream(path)) {
				workbook.write(fileOut);
			}
        } catch (IOException e) {
            logger.info("创建空的XLSX文件时出现异常:{}", e.getMessage());
        }
    }
}