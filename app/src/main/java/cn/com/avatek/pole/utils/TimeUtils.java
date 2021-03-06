package cn.com.avatek.pole.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by avatek on 2016/2/29 0029.
 */
public class TimeUtils {

    /**
     * 时间戳转化成时间字符串
     * @param time
     * @return
     */
    public static String getTimeStr(long time){
        Date date=new Date(time);
        String strs="";
        try {
//yyyy表示年MM表示月dd表示日
//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//进行格式化
            strs=sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 时间戳转化成时间字符串
     * @param time
     * @return
     */
    public static String getTimeStr1(long time){
        Date date=new Date(time);
        String strs="";
        try {
//yyyy表示年MM表示月dd表示日
//yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//进行格式化
            strs=sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 掉此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
            Log.d("--444444---", times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }

    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static int daysBetween(String toDays) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
//        cal.setTime(sdf.parse(new Date()));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(toDays));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
}
