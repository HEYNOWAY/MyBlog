package com.luos.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置文件的工具类
 *
 * Created by luos on 2016/10/30.
 */
public class PropertiesUtil {

    /**
     * 通过key来获取配置文件中的值
     * @param key
     * @return
     */
    public static String getValue(String key){
        Properties prop = new Properties();
        InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/dairy.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) prop.get(key);
    }
}
