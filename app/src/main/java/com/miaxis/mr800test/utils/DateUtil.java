package com.miaxis.mr800test.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xu.nan on 2016/7/28.
 */
public class DateUtil {

    public static String toHourMinString(Date date){
        SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm:ss");
        return myFmt.format(date);
    }

    public static String toMonthDay(Date date){
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(date);
    }

    public static String toAll(Date date){
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(date);
    }

    /**
     * 计算周岁
     * @param dateStr   “19990101”
     * @return
     */
    public static int getAge(String dateStr){
        int age = 0;
        Date now = new Date();
        int yNow = getYear(now);
        int y = Integer.valueOf(dateStr.substring(0,4));
        age = yNow - y;

        int mNow = getMonth(now);
        int m = Integer.valueOf(dateStr.substring(4,6));
        int dNow = getDay(now);
        int d = Integer.valueOf(dateStr.substring(6,8));
        if(m > mNow){
            age --;
        }else if(d > dNow){
            age --;
        }
        return age;
    }

    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    public static int getDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定时间的前 N 天 的日期字符串yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getSpecifiedDayBefore(Date date, int n) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,day - n);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

}
