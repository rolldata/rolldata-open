package com.rolldata.core.common.exception;

/**
 * a problem while loading
 * @Title:AssetLoadException
 * @Description: 加载时出现问题
 * @Company:www.wrenchdata.com
 * @author:shenshilong
 * @date:2020-12-17
 * @version:V1.0
 */
public class AssetLoadException extends RuntimeException {

    private static final long serialVersionUID = -7370683355944394386L;

    public AssetLoadException(String message) {
        super(message);
    }

    public AssetLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
