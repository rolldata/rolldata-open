package com.rolldata.web.system.pojo;

import java.io.Serializable;

/**
 * 简单对象 KEY VALUE
 *
 * @Title: ValueInfo
 * @Description: ValueInfo
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2022-12-13
 * @version: V1.0
 */
public class ValueInfo implements Serializable {

    private static final long serialVersionUID = 7848569534048513313L;

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public ValueInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ValueInfo setValue(String value) {
        this.value = value;
        return this;
    }
}
