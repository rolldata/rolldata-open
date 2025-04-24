package com.rolldata.web.system.util;

import com.rolldata.core.util.BaseCheckUtils;
import com.rolldata.core.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rolldata.core.util.ObjectUtils.assertNotNull;

/**
 * 导出Excel文件,表头表格形式,没有任何样式
 * @Title:ExportExcel
 * @Description:ExportExcel
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-10-23
 * @version:V1.0
 */
public class ExportExcel {

    private final Logger log = LogManager.getLogger(ExportExcel.class);

    /**
     * 导出路径
     */
    private String exportPath;

    /**
     * 显示的导出表的标题
     */
    private String title;

    /**
     * 导出表的列名
     */
    private String[] rowName;
    
    private List<Map<String, Object>> dataList = new ArrayList<>();

    private List<ExpObj> expObjs;

    /**
     * 字段维度类型
     */
    private Map<String, String> fieldTypes;

    /**
     * 字段格式化
     */
    private Map<String, String> fieldFormats;

    public ExportExcel() {
    }

    /**
     * 构造函数，传入要导出的数据
     *
     * @param title    表格标题(合并)
     * @param rowName  表头名
     * @param dataList 数据
     */
    public ExportExcel(String title, String[] rowName, List<Map<String, Object>> dataList) {
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
    }

    public ExportExcel(String title, String[] rowName, List<Map<String, Object>> dataList,
            Map<String, String> fieldTypes, Map<String, String> fieldFormats) {

        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.fieldTypes = fieldTypes;
        this.fieldFormats = fieldFormats;
    }

    /**
     * 导出单sheet数据
     *
     * @param path
     * @throws IOException
     */
    public void export(String path) throws IOException {

        this.setExportPath(path);
        try (Workbook workBook = new SXSSFWorkbook(100);) {
            createExcelSheet(workBook, this.dataList, this.title, this.rowName, this.title);
            fileOut(workBook, this.exportPath);
        } catch (IOException e) {
            this.log.error(e);
            throw e;
        }
    }

    public void exportColumnReport(String path) throws IOException {

        this.setExportPath(path);
        try (Workbook workBook = new SXSSFWorkbook(100);) {
            createExcelSheetReport(workBook, this.dataList, this.title, this.rowName);
            fileOut(workBook, this.exportPath);
        } catch (IOException e) {
            this.log.error(e);
            throw e;
        }
    }

    /**
     * 导出Excel,支持多sheet
     * <p>
     * 如果:{@link ExpObj#sheetName sheet名称}没设置,则默认根据容器序号生成sheet0,sheet1.
     *
     * @throws Exception
     */
    public void export() throws IOException {

        assertNotNull(this.expObjs, "sheet数据不能为空");
        try (Workbook workBook = new SXSSFWorkbook(100);) {
            for (int i = 0; i < this.expObjs.size(); i++) {
                ExpObj expObj = this.expObjs.get(i);
                List<Map<String, Object>> expDataList = expObj.getDataList();
                String sheetName = expObj.getSheetName();
                String[] expRowName = expObj.getRowName();
                String tableTitle = expObj.getTableTitle();
                createExcelSheet(
                    workBook,
                    expDataList,
                    StringUtil.isEmpty(sheetName) ? "sheet" + i : sheetName,
                    expRowName,
                    tableTitle
                );
            }
            fileOut(workBook, this.exportPath);
        } catch (IOException e) {
            this.log.error(e);
            throw e;
        }
    }

    public void createExcelSheet(Workbook workBook, List<Map<String, Object>> expDataList, String sheetName,
        String[] expRowName, String tableTitle) {

        commonEach(workBook, expDataList, sheetName, expRowName, false);
    }

    public void createExcelSheetReport(Workbook workBook, List<Map<String, Object>> expDataList, String sheetName,
        String[] expRowName) {

        commonEach(workBook, expDataList, sheetName, expRowName, true);
    }

    private void commonEach(Workbook workBook, List<Map<String, Object>> expDataList, String sheetName,
        String[] expRowName, boolean isFormat) {

        DataFormat dataFormat = workBook.createDataFormat();
        Sheet sheet = workBook.createSheet(sheetName);

        // 创建单元格样式
        Font font = workBook.createFont();
        font.setBold(true);

        // 定义所需列数
        int columnNum = expRowName.length;
        Row rowRowName = sheet.createRow(0);
        rowRowName.setHeight((short) 400);

        // 将列头设置到sheet的单元格中
        for (int n = 0; n < columnNum; n++) {
            font.setBold(true);
            Cell cellRowName = rowRowName.createCell(n);
            cellRowName.setCellType(CellType.STRING);
            XSSFRichTextString text = new XSSFRichTextString(expRowName[n]);
            cellRowName.setCellValue(text);
        }
        Map<String ,CellStyle> cellStyleMap = new HashMap<String ,CellStyle>();
        // 将查询到的数据设置到sheet对应的单元格中
        for (int i = 0; i < expDataList.size(); i++) {

            // 遍历每个对象
            Map<String, Object> stringObjectMap = expDataList.get(i);

            // 创建所需的行数
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < expRowName.length; j++) {
                String rowName = expRowName[j];
                Object val = stringObjectMap.get(rowName);
                Cell cell = row.createCell(j);
                if (StringUtil.isNotEmpty(val)) {
                    if (isFormat) {
                        cellFormatValue(workBook, cell, String.valueOf(val), rowName, dataFormat, cellStyleMap);
                    } else {
                        cellValue(cell, String.valueOf(val));
                    }
                } else {
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue("");
                }
            }
        }
    }

    private void cellValue(Cell cell, String valStr) {

        // 用new BigDecimal(valStr);报异常判断是否是数值,大数据量时,创建对象占内存
        boolean isNumber = BaseCheckUtils.isDouble(valStr, true);
        if (isNumber) {
            cell.setCellValue(Double.parseDouble(valStr));
            cell.setCellType(CellType.NUMERIC);
        } else {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(valStr);
        }
    }

    private void cellFormatValue(Workbook workBook, Cell cell, String valStr, String rowName, DataFormat dataFormat, Map<String,CellStyle> cellStyleMap) {

        String fieldType = fieldTypes.get(rowName);
        if ("dimension".equalsIgnoreCase(fieldType)) {
            cell.setCellType(CellType.STRING);
            cell.setCellValue(valStr);
        } else {
            String format = fieldFormats.get(rowName);
            cell.setCellType(CellType.NUMERIC);
            if (StringUtil.isEmpty(format)) {
                cell.setCellValue(Double.valueOf(valStr));
            } else {
                short formatShort = dataFormat.getFormat(format);
                //防止出现报错：The maximum number of Cell Styles was exceeded. You can define up to 64000 style in a .xlsx Workbook
                //按列进行格式化 zhaibx 20240607
                CellStyle cellStyle = null;
                if(cellStyleMap.get(rowName)!=null){
                    cellStyle = cellStyleMap.get(rowName);
                }else{
                    cellStyle = workBook.createCellStyle();
                    cellStyleMap.put(rowName,cellStyle);
                }
                cellStyle.setDataFormat(formatShort);
                cell.setCellValue(Double.parseDouble(valStr));
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 输出成功后,此方法始终关闭流。
     *
     * @param workBook
     * @param exportPath 导出路径
     * @throws IOException
     */
    public static void fileOut(Workbook workBook, String exportPath) throws IOException {

        try (FileOutputStream fOut = new FileOutputStream(exportPath);) {

            // 把相应的Excel 工作簿存盘
            workBook.write(fOut);

            // 清空缓冲区数据
            fOut.flush();
        }
    }

    public static class ExpObj {

        /**
         * sheet名称
         */
        private String sheetName;

        /**
         * 表格标题(非必须)
         */
        private String tableTitle;

        /**
         * 导出表的列名
         */
        private String[] rowName;

        /**
         * 每个sheet对应的列表数据
         */
        private List<Map<String, Object>> dataList = new ArrayList<>();

        /**
         * 获取 sheet名称
         *
         * @return sheetName sheet名称
         */
        public String getSheetName() {
            return this.sheetName;
        }

        /**
         * 设置 sheet名称
         *
         * @param sheetName sheet名称
         */
        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        /**
         * 获取 每个sheet对应的列表数据
         *
         * @return dataList 每个sheet对应的列表数据
         */
        public List<Map<String, Object>> getDataList() {
            return this.dataList;
        }

        /**
         * 设置 每个sheet对应的列表数据
         *
         * @param dataList 每个sheet对应的列表数据
         */
        public void setDataList(List<Map<String, Object>> dataList) {
            this.dataList = dataList;
        }

        /**
         * 获取 表格标题(非必须)
         *
         * @return tableTitle 表格标题(非必须)
         */
        public String getTableTitle() {
            return this.tableTitle;
        }

        /**
         * 设置 表格标题(非必须)
         *
         * @param tableTitle 表格标题(非必须)
         */
        public void setTableTitle(String tableTitle) {
            this.tableTitle = tableTitle;
        }

        /**
         * 获取 导出表的列名
         *
         * @return rowName 导出表的列名
         */
        public String[] getRowName() {
            return this.rowName;
        }

        /**
         * 设置 导出表的列名
         *
         * @param rowName 导出表的列名
         */
        public void setRowName(String[] rowName) {
            this.rowName = rowName;
        }
    }

    /**
     * 获取 显示的导出表的标题
     *
     * @return title 显示的导出表的标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置 显示的导出表的标题
     *
     * @param title 显示的导出表的标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取 导出表的列名
     *
     * @return rowName 导出表的列名
     */
    public String[] getRowName() {
        return this.rowName;
    }

    /**
     * 设置 导出表的列名
     *
     * @param rowName 导出表的列名
     */
    public void setRowName(String[] rowName) {
        this.rowName = rowName;
    }

    /**
     * 获取
     *
     * @return dataList
     */
    public List<Map<String, Object>> getDataList() {
        return this.dataList;
    }

    /**
     * 设置
     *
     * @param dataList
     */
    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }


    /**
     * 获取
     *
     * @return expObjs
     */
    public List<ExpObj> getExpObjs() {
        return this.expObjs;
    }

    /**
     * 设置
     *
     * @param expObjs
     */
    public void setExpObjs(List<ExpObj> expObjs) {
        this.expObjs = expObjs;
    }

    /**
     * 获取 导出路径
     *
     * @return exportPath 导出路径
     */
    public String getExportPath() {
        return this.exportPath;
    }

    /**
     * 设置 导出路径
     *
     * @param exportPath 导出路径
     */
    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

}