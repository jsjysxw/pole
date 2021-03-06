package cn.com.avatek.pole.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shenxw on 2016/12/26.
 */

public class TimeFormatTool {

    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    private static SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public static String getTimeFormat(){
        return simpleDateFormat.format(new Date());
    }
    public static String getTimeFormat1(){
        return simpleDateFormat1.format(new Date());
    }
    public static String getDateFormat(){
        return getDateFormat(new Date());
    }
    public static String getDateFormat(Date date){
        return dateFormat.format(date);
    }
    public static String getDateFormat(long date){
        Date dd = new Date(date*1000);
        return dateFormat.format(dd);
    }

    public static String getDateFormat(String date){
        Date dd = null;
        try {
            long longDate =  Long.parseLong(date);
             dd = new Date(longDate*1000);
        }catch (Exception e){
            dd = new Date();
        }
        return dateFormat.format(dd);
    }
}
