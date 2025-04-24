package com.rolldata.web.system.util;

import net.iharder.Base64;

public class Base64Utils {
	private static final String UTF_8 = "UTF-8";
    public static byte[] decode(String base64) throws Exception {  
        return Base64.decode(base64.getBytes(UTF_8));  
    }  
}
