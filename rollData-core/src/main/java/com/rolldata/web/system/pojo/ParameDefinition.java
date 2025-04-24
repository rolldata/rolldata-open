package com.rolldata.web.system.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: ParameDefinition
 * @Description: 参数面板
 * @Company: www.wrenchdata.com
 * @author: shenshilong[shilong_shen@163.com]
 * @date: 2019-11-19
 * @version: V1.0
 */
public class ParameDefinition implements Serializable {

    private static final long serialVersionUID = -3115771841427190170L;

    private String name;

    private String id;

    private String value;

    /**
     * 值的集合
     */
    private List<String> values;

    /**
     * 导出文件的显示值
     */
    private List<String> shows;

    /**
     * 导出文件的显示值
     */
    private String show;

    /**
     * 是否隐藏(报表循环导出那用,如果参数变多,可以拿出去)
     */
    private boolean hide;

    /**
     * 控件类型,正常=normal,2日期=date(后台赋值)
     */
    private String controlType;

    /**
     * 日期控件的格式
     */
    private String dataFormat;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getValue() { return value;}

    public void setValue(String value) { this.value = value;}

    /**
     * 获取 值的集合
     *
     * @return values 值的集合
     */
    public List<String> getValues() {
        return this.values;
    }

    /**
     * 设置 值的集合
     *
     * @param values 值的集合
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    public boolean isHide() {
        return hide;
    }

    public ParameDefinition setHide(boolean hide) {
        this.hide = hide;
        return this;
    }

    public String getControlType() {
        return controlType;
    }

    public ParameDefinition setControlType(String controlType) {
        this.controlType = controlType;
        return this;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public ParameDefinition setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public List<String> getShows() {
        return shows;
    }

    public ParameDefinition setShows(List<String> shows) {
        this.shows = shows;
        return this;
    }

    public String getShow() {
        return show;
    }

    public ParameDefinition setShow(String show) {
        this.show = show;
        return this;
    }

    @Override
    public String toString() {
        return "ParameDefinition{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", value='" + value + '\'' +
                ", values=" + values +
                ", shows=" + shows +
                ", show='" + show + '\'' +
                ", hide=" + hide +
                ", controlType='" + controlType + '\'' +
                ", dataFormat='" + dataFormat + '\'' +
                '}';
    }
}
