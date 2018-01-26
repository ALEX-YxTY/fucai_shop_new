package com.meishipintu.fucaiShopNew.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/18.
 * <p>
 * 主要功能：
 */

public class DateUtils {

    public static String DateUtilSecond(Long timestamp) {
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(timestamp));
        return date;

    }
    public static String DateUtilOne(Long timestamp) {
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date(timestamp));
        return date;

    }


    //获取起止时间
    //type=1 默认使用1日到下月1日  type=2 使用0时至次日0时
    public static String getStartAndEndTime(int year, int month, int day, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long timeStart = calendar.getTimeInMillis();
        if (type == 1) {
            calendar.add(Calendar.MONTH, 1);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        long timeEnd = calendar.getTimeInMillis();
        return timeStart + "," + timeEnd;
    }

    //显示时间格式为yyy-MM-dd
    public static String formart2(String timeStamp) {
        if (timeStamp == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date(Long.parseLong(timeStamp)));
        return format;
    }

    //显示时间格式为MM-dd HH:mm:ss
    public static String formart3(String timeStamp) {
        if (timeStamp == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date(Long.parseLong(timeStamp)));
        return format;
    }
}
