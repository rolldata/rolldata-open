package com.rolldata.web.system.pojo.datahandle;

import java.io.Serializable;

/**
 * @Title: Breadcrumb
 * @Description: Breadcrumb
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2023-06-16
 * @version: V1.0
 */
public class Breadcrumb implements Serializable {


    private static final long serialVersionUID = -7588795119842861601L;

    private String id;

    private String name;

    public Breadcrumb(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Breadcrumb setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Breadcrumb setName(String name) {
        this.name = name;
        return this;
    }
}
