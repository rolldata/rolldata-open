package com.rolldata.web.system.util;

import com.rolldata.core.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: HttpBase
 * @Description: HttpBase
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-05-21
 * @version: V1.0
 */
public abstract class HttpBase<T> {

    protected Map<String, String> headers = new HashMap<>();

    public T header(String name, String value) {
        if (StringUtil.isNotEmpty(name) && StringUtil.isNotEmpty(value)) {
            headers.put(name, value);
        }
        return (T) this;
    }
}
