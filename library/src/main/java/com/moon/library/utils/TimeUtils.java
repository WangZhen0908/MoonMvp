package com.moon.library.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class TimeUtils {
    /**
     * 一分钟多少秒
     */
    public static final int MINUTE_SECOND = 60;
    /**
     * 最小显示时间"00"，不然前面+"0"
     */
    public static final int MIN_SHOW = 9;
    /**
     * 毫秒时间转换
     */
    public static final int MILLI_SECOND = 1000;

    public static String secondsToChinese(long seconds) {

        long hour = seconds / 3600;
        long minute = seconds / 60 % 60;
        long second = seconds % 60;

        return (hour == 0 ? "" : (hour + "时"))
                + minute + "分"
                + second + "秒";

    }

    public static String secondsToEnglish(long seconds) {

        long hour = seconds / 3600;
        long minute = seconds / 60 % 60;
        long second = seconds % 60;

        return String.format("%02d", hour) + ":"
                + String.format("%02d", minute) + ":"
                + String.format("%02d", second);

    }

    public static String dateFormatNormal(long millis) {
        return dateFormat(millis, "yyyy/MM/dd HH:mm");
    }

    public static String dateFormat(long millis, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return sdf.format(calendar.getTime());

    }


}
