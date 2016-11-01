package com.luos.util;

/**
 * Created by luos on 2016/11/1.
 */
public class StringUtil {

    public static boolean isEmpty(String str){
        if("".equals(str)||str==null){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str){
        if(!"".equals(str)&&str!=null){
            return true;
        } else {
            return false;
        }
    }
}
