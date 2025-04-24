package com.rolldata.core.common.exception;

import com.rolldata.core.util.FileUtil;

import java.io.FileNotFoundException;

/**
 * 包装{@link FileNotFoundException}报错信息
 *
 * @Title: WdFileNotFoundException
 * @Description: WdFileNotFoundException
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-03-06
 * @version: V1.0
 */
public class WdFileNotFoundException extends FileNotFoundException {

    private static final long serialVersionUID = -887429749478245289L;

    public WdFileNotFoundException(String message) {
        super(message);
    }

    public WdFileNotFoundException(Throwable cause) {
        super(cause.getMessage().substring(cause.getMessage().lastIndexOf(FileUtil.SYSTEM_FILE_SEPARATOR) + 1));
    }
}
