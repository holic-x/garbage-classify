package com.rc.framework.utils;

import java.util.UUID;

public class CustomUtil {

    /**
     * 随机生成UUID
     */

    public static String randomGenUUID(){
        String randomId = UUID.randomUUID().toString().replaceAll("-","");
        return randomId;
    }
}
