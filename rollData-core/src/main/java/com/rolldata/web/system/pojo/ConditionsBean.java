package com.rolldata.web.system.pojo;

import com.rolldata.web.system.enums.Op;

import java.util.List;

/**
 * 模型条件对象
 *
 * @Title: ConditionsBean
 * @Description: 模型条件对象
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date: 2019-11-18
 * @version:V1.0
 */
public class ConditionsBean {

    /**
     * 条件类型 0参数
     */
    public static final String TYPE_PARAME = "0";

    /**
     * 条件类型 1字符串
     */
    public static final String TYPE_VARCHAR = "1";

    /**
     * 条件类型 2公式
     */
    public static final String TYPE_FORMULA = "2";

    /**
     * 条件类型 3单元格
     */
    public static final String TYPE_CELL = "3";

    private String name;

    private String type;

    private String operator;

    private String value;

    private List<String> values;

    private String str;

    private String condition;

    /**
     * 拼接一组or/and 用
     */
    private List<ConditionsBean> children;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}

    public String getOperator() {

        return Op.parse(this.operator).toString();
    }

    public void setOperator(String operator) { this.operator = operator;}

    public String getValue() {

        return this.value;
    }

    public void setValue(String value) { this.value = value;}

    public String getStr() { return str;}

    public void setStr(String str) { this.str = str;}

    public String getCondition() { return condition;}

    public void setCondition(String condition) { this.condition = condition;}

    public List<ConditionsBean> getChildren() { return children;}

    public void setChildren(List<ConditionsBean> children) { this.children = children;}

    public List<String> getValues() {
        return values;
    }

    public ConditionsBean setValues(List<String> values) {
        this.values = values;
        return this;
    }

    @Override
    public String toString() {
        return "ConditionsBean{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", operator='" + operator + '\'' +
            ", value='" + value + '\'' +
            ", values=" + values +
            ", str='" + str + '\'' +
            ", condition='" + condition + '\'' +
            ", children=" + children +
            '}';
    }
}