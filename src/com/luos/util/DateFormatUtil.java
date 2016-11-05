package com.luos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luos on 2016/11/1.
 */
public class DateFormatUtil {

    /**
     * 把Date类型转换成String类型
     *
     * @param date
     * @param format
     * @return
     */
    public static String DateToString(Date date, String format){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(date!=null){
            result = sdf.format(date);
        }
        return result;
    }

    /**
     * 把String类型转换成Date类型
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date, String format) throws ParseException {
        Date resultDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(StringUtil.isNotEmpty(date)){
            resultDate = sdf.parse(date);
        }
        return  resultDate;
    }
}
