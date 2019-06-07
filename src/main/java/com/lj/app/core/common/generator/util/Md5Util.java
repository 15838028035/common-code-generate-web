package com.lj.app.core.common.generator.util;

import org.springframework.util.DigestUtils;

/**
 */
public class Md5Util {

    private Md5Util(){
        throw new IllegalStateException("Utility class");
    }

    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
