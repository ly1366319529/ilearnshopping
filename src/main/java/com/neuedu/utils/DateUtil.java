package com.neuedu.utils;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间转换类
 */
public class DateUtil {


    private  static  final  String STANDARD_FORMAT="yyyy-MM-dd HH:mm:ss";
    /**
     * 将时间转为字符串
     * @param date
     * @param fomate  转换的时间格式
     * @return
     */
    public  static  String dateToStr(Date date,String fomate){
        DateTime dateTime=new DateTime();
        return  dateTime.toString(fomate);
    }
    public  static  String dateToStr(Date date){
        DateTime dateTime=new DateTime();
        return  dateTime.toString(STANDARD_FORMAT);
    }

    public  static  Date strToDate(String str){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }
    public  static  Date strToDate(String str,String fomate){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(fomate);
        DateTime dateTime=dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }
/*
    public static void main(String[] args) {
        System.out.println(dateToStr(new Date()));
        System.out.println(strToDate("2018-12-11 11:00:14"));
    }*/
}
