package com.neuedu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 封装图片的域名类
 */
public class PropertiesUtil {
    private  static Properties properties=new Properties();
    static {

        InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readByKey(String key){
        return  properties.getProperty(key);
    }

}
