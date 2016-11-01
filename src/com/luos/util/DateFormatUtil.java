package com.luos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luos on 2016/11/1.
 */
public class DateFormatUtil {

    public static String DateToString(Date date, String format){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(date!=null){
            result = sdf.format(date);
        }
        return result;
    }

    public static Date StringToDate(String date, String format) throws ParseException {
        Date resultDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(StringUtil.isNotEmpty(date)){
            resultDate = sdf.parse(date);
        }
        return  resultDate;
    }
}
