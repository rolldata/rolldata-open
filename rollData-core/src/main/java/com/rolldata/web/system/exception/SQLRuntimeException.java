package com.rolldata.web.system.exception;

/**
 * @Title: SQLRuntimeException
 * @Description: SQL异常
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2020-11-18
 * @version: V1.0
 */
public class SQLRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6274085645671265857L;

    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(Exception ex) {
        super(ex.getMessage());
    }

    public SQLRuntimeException(String message, Exception ex) {
        super(message + ":" + ex.getMessage());

    }
}
