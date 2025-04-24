package com.rolldata.web.system.pojo.excel;

/**
 * 单元格的合并信息
 *
 * @Title: MergeInfo
 * @Description: MergeInfo
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2022-05-20
 * @version: V1.0
 */
public class MergeInfo {

    int firstRow;
    int lastRow;
    int firstCol;
    int lastCol;

    /**
     * 方便调试,如果对象有改动,则 mark 变成 '已改动'
     */
    private String mark;

    /**
     * 没有合并的=false
     */
    private boolean flag;

    /**
     * 单元格的值
     */
    private String value;

    public MergeInfo() {
    }

    public MergeInfo(int firstRow, int lastRow, int firstCol, int lastCol, String value) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.value = value;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public int getLastCol() {
        return lastCol;
    }

    public MergeInfo setMark(String mark) {
        this.mark = mark;
        this.flag = true;
        return this;
    }

    /**
     * 追加一个被合并掉的行
     *
     * @return
     */
    public MergeInfo addRow() {
        this.lastRow++;
        return this;
    }

    /**
     * 追加一个被合并掉的列
     *
     * @return
     */
    public MergeInfo addCol() {
        this.lastCol++;
        return this;
    }

    public boolean isFlag() {
        return flag;
    }

    public MergeInfo setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MergeInfo{" +
            "firstRow=" + firstRow +
            ", lastRow=" + lastRow +
            ", firstCol=" + firstCol +
            ", lastCol=" + lastCol +
            ", mark='" + mark + '\'' +
            ", flag=" + flag +
            ", value='" + value + '\'' +
            '}';
    }
}
