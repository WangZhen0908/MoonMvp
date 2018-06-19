package com.moon.library.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateUtils {


    /**
     * 星期枚举类
     */
    public enum Week {

        MONDAY("星期一", "Monday", "Mon.", 1), TUESDAY("星期二", "Tuesday", "Tues.", 2), WEDNESDAY(
                "星期三", "Wednesday", "Wed.", 3), THURSDAY("星期四", "Thursday",
                "Thur.", 4), FRIDAY("星期五", "Friday", "Fri.", 5), SATURDAY("星期六",
                "Saturday", "Sat.", 6), SUNDAY("星期日", "Sunday", "Sun.", 7);

        String name_cn;
        String name_en;
        String name_enShort;
        int number;

        Week(String name_cn, String name_en, String name_enShort, int number) {
            this.name_cn = name_cn;
            this.name_en = name_en;
            this.name_enShort = name_enShort;
            this.number = number;
        }

        public String getChineseName() {
            return name_cn;
        }

        public String getName() {
            return name_en;
        }

        public String getShortName() {
            return name_enShort;
        }

        public int getNumber() {
            return number;
        }
    }


    /**
     * 获取SimpleDateFormat
     *
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern)
            throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 时间格式
     *
     * @param mTime Long
     * @return 返回‘yyyy-MM-dd HH:mm:ss’格式的时间
     */
    public static String format(long mTime) {
        SimpleDateFormat sFormat = new SimpleDateFormat(
                DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
        Date date = new Date(mTime);
        String time = sFormat.format(date);

        // Logger.zs("TimeFormat_getStringTime:" + time);
        return time;
    }

    /**
     * 时间格式
     *
     * @param mTime Long
     */
    public static String format(long mTime, DateStyle style) {
        SimpleDateFormat sFormat = new SimpleDateFormat(style.getValue());
        Date date = new Date(mTime);
        String time = sFormat.format(date);

        // Logger.zs("TimeFormat_getStringTime:" + time);
        return time;
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date     日期
     * @param dateType 日期格式
     * @return 数值
     */
    private static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期字符串
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期字符串
     */
    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    /**
     * 格式化时间
     *
     * @param seconds
     * @return
     */
    public static String formatTime(int seconds) {
        StringBuilder time = new StringBuilder();

        int day = seconds / (24 * 60 * 60);
        int remainder = seconds - day * 24 * 60 * 60;

        int hours = remainder / (60 * 60);
        remainder = remainder - hours * 60 * 60;

        int minutes = remainder / 60;

        remainder = remainder - minutes * 60;

        int second = remainder;

        if (seconds == 0) {
            time.append("0天0时0分");
            return time.toString();
        }

        time.append(day).append("天");
        time.append(hours).append("时");
        time.append(minutes).append("分");

        return time.toString();
    }

    /**
     * 格式化时间
     *
     * @param seconds
     * @param num     显示几位时间
     * @return
     */
    public static String formatTime(int seconds, int num) {
        StringBuilder time = new StringBuilder();

        int day = seconds / (24 * 60 * 60);
        int remainder = seconds - day * 24 * 60 * 60;

        int hours = remainder / (60 * 60);
        remainder = remainder - hours * 60 * 60;

        int minutes = remainder / 60;

        remainder = remainder - minutes * 60;

        int second = remainder;

        if (seconds == 0) {
            time.append("0天0时0分");
            return time.toString();
        }
        if (num == 1) {
            time.append(second).append("秒");
        } else if (num == 2) {
            time.append(minutes).append("分");
            time.append(second).append("秒");
        } else if (num == 3) {
            time.append(hours).append("时");
            time.append(minutes).append("分");
            time.append(second).append("秒");
        } else {
            time.append(day).append("天");
            time.append(hours).append("时");
            time.append(minutes).append("分");
            time.append(second).append("秒");
        }
        return time.toString();
    }

    // a integer to xx:xx:xx
    public static String secondsToTime(long time) {
        String timeStr = null;
        long minute = 0;
        long second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = minute + "分钟" + second + "秒";
            }
        }
        return timeStr;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date     日期
     * @param dateType 类型
     * @param amount   数值
     * @return 计算后日期
     */
    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps 时间long集合
     * @return 日期
     */
    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        LongSparseArray<long[]> map = new LongSparseArray();
//		Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i)
                                - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = {timestamps.get(i),
                                timestamps.get(j)};
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0],
                                timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        // 当timestamps的size为2，需要与当前时间作为参照
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
                            timestamp = Math.max(timestampsLastTmp[0],
                                    timestampsLastTmp[1]);
                        } else {
                            long now = new Date().getTime();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo
                                    - now)) {
                                timestamp = dateOne;
                            } else {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date 日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (StringToDate(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date 日期字符串
     * @return 日期风格
     */
    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        LongSparseArray<DateStyle> map = new LongSparseArray<>();
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyle style : DateStyle.values()) {
            Date dateTmp = StringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        dateStyle = map.get(getAccurateDate(timestamps).getTime());
        return dateStyle;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static Date StringToDate(String date) {
        DateStyle dateStyle = null;
        return StringToDate(date, dateStyle);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date     日期字符串
     * @param parttern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String parttern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(parttern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date      日期字符串
     * @param dateStyle 日期风格
     * @return 日期
     */
    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle == null) {
            List<Long> timestamps = new ArrayList<Long>();
            for (DateStyle style : DateStyle.values()) {
                Date dateTmp = StringToDate(date, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                }
            }
            myDate = getAccurateDate(timestamps);
        } else {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date     日期
     * @param parttern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date      日期
     * @param dateStyle 日期风格
     * @return 日期字符串
     */
    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date     旧日期字符串
     * @param parttern 新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String parttern) {
        return StringToString(date, null, parttern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date      旧日期字符串
     * @param dateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle dateStyle) {
        return StringToString(date, null, dateStyle);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddParttern 旧日期格式
     * @param newParttern  新日期格式
     * @return 新日期字符串
     */
    public static String StringToString(String date, String olddParttern,
                                        String newParttern) {
        String dateString = null;
        if (olddParttern == null) {
            DateStyle style = getDateStyle(date);
            if (style != null) {
                Date myDate = StringToDate(date, style.getValue());
                dateString = DateToString(myDate, newParttern);
            }
        } else {
            Date myDate = StringToDate(date, olddParttern);
            dateString = DateToString(myDate, newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date         旧日期字符串
     * @param olddDteStyle 旧日期风格
     * @param newDateStyle 新日期风格
     * @return 新日期字符串
     */
    public static String StringToString(String date, DateStyle olddDteStyle,
                                        DateStyle newDateStyle) {
        String dateString = null;
        if (olddDteStyle == null) {
            DateStyle style = getDateStyle(date);
            dateString = StringToString(date, style.getValue(),
                    newDateStyle.getValue());
        } else {
            dateString = StringToString(date, olddDteStyle.getValue(),
                    newDateStyle.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date       日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期字符串
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date      日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期字符串
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date       日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期字符串
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date         日期
     * @param minuteAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, Calendar.MINUTE, minuteAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期字符串
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date         日期
     * @param secondAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, Calendar.SECOND, secondAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH);
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return StringToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 日期
     */
    public static String getSimpleYearDate(Date date) {
        return DateToString(date, DateStyle.YY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date 日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date 日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期字符串
     * @return 星期
     */
    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    /**
     * 获取日期的星期。失败返回null。
     *
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
                break;
        }
        return week;
    }

    /**
     * 获取两个日期相差的天数
     *
     * @param date      日期字符串
     * @param otherDate 另一个日期字符串
     * @return 相差天数
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));
    }

    /**
     * @param date      日期
     * @param otherDate 另一个日期
     * @return 相差天数
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        date = DateUtils.StringToDate(DateUtils.getDate(date));
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) time / (24 * 60 * 60 * 1000);
    }

    /**
     * @param DATE1 日期
     * @param DATE2 另一个日期
     * @return int 1:2大于1 -1:1大于2 0:1=2
     */
    public static int compareDate(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取时差（标准为服务器端的返回时间）：单位 天
     *
     * @param dateSrc
     * @return
     */
    public static int getGapNumBetweenDate(String dateSrc, String desDate) {
        try {
            // Date date = new Date();
            // date.setTime(Long.parseLong(webTime));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            // String currentDate = format.format(date);
            Date date1 = format.parse(dateSrc);
            Date date2 = format.parse(desDate);
            long secondGap = date1.getTime() - date2.getTime() > 0 ? date1
                    .getTime() - date2.getTime() : date2.getTime()
                    - date1.getTime();
            return (int) (secondGap / 1000 / 3600 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据给定时间（秒）计算出天数
     *
     * @param seconds
     * @return
     */
    public static int getDayBySeconds(String seconds) {
        int days = 0;
        try {
            long time = Long.valueOf(seconds);
            days = (int) (time / (24 * 3600));
        } catch (Exception e) {
        }
        return days;
    }

    /**
     * 根据给定的时间计算出时分秒
     *
     * @param seconds
     * @return
     */
    public static String getTimeBySeconds(String seconds) {
        StringBuilder result = new StringBuilder();
        try {
            long time = Long.valueOf(seconds);
            int hour = (int) (time / 1000 / (3600));
            int minute = (int) (time / 1000 % (3600) / 60);
            int second = (int) (time / 1000 % (60));

            result.append(String.format("%02d", hour)).append(":").append(String.format("%02d", minute)).append(":").append(String.format("%02d", second));
        } catch (Exception e) {
        }
        return result.toString();
    }

    /**
     * 日期枚举类
     */
    public enum DateStyle {

        MM_DD("MM-dd"),
        YYYY_MM("yyyy-MM"),
        YYYY_MM_DD("yyyy-MM-dd"),
        YY_MM_DD("yy-MM-dd"),
        MM_DD_HH_MM("MM-dd HH:mm"),
        MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

        MM_DD_EN("MM/dd"),
        YYYY_MM_EN("yyyy/MM"),
        YYYY_MM_DD_EN("yyyy/MM/dd"),
        MM_DD_HH_MM_EN("MM/dd HH:mm"),
        MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
        YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
        YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

        MM_DD_CN("MM月dd日"),
        YYYY_MM_CN("yyyy年MM月"),
        YYYY_MM_DD_CN("yyyy年MM月dd日"),
        MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
        MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
        YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
        YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),
        /**
         * 瑞士时间显示格式
         */
        YYYY_MM_DD_EN_SWITZERLAND("yyyy.MM.dd"),

        HH_MM("HH:mm"),
        HH_MM_SS("HH:mm:ss");


        private String value;

        DateStyle(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 时间搓转换为String时间显示
     *
     * @param timeStamp
     * @return
     */
    @Nullable
    public static String getDateTimeString(Number timeStamp) {
        if (timeStamp == null) {
            return null;
        }
        Date date = new Date(timeStamp.longValue());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS_EN.getValue());//设置日期格式
        return simpleDateFormat.format(date);
    }

    /**
     * 时间转换日期
     *
     * @param timeStamp
     * @return
     */
    public static String getDateString(Number timeStamp) {
        if (timeStamp == null) {
            return null;
        }
        Date date = new Date(timeStamp.longValue());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateStyle.YYYY_MM_DD_EN.getValue());//设置日期格式
        return simpleDateFormat.format(date);
    }

    public static long parse(String time, DateStyle style) {
        SimpleDateFormat sdf = new SimpleDateFormat(style.getValue());
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean isToday(long time) {
        Calendar today = Calendar.getInstance();
        Calendar theDay = Calendar.getInstance();
        theDay.setTimeInMillis(time);

        return theDay.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && theDay.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && theDay.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);

    }

    public static String getTimeLabel(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        Calendar theDayBeforeYesterday = Calendar.getInstance();
        theDayBeforeYesterday.add(Calendar.DAY_OF_MONTH, -2);


        Calendar showCalendar = Calendar.getInstance();
        showCalendar.setTime(date);

        int showYear = showCalendar.get(Calendar.YEAR);
        int showMonth = showCalendar.get(Calendar.MONTH);
        int showDay = showCalendar.get(Calendar.DAY_OF_MONTH);

        String label = "";
        if (showYear == today.get(Calendar.YEAR) && showMonth == today.get(Calendar.MONTH) && showDay == today.get(Calendar.DAY_OF_MONTH)) {
            //今天
            label = "今天";
        } else if (showYear == yesterday.get(Calendar.YEAR) && showMonth == yesterday.get(Calendar.MONTH) && showDay == yesterday.get(Calendar.DAY_OF_MONTH)) {
            //昨天
            label = "昨天";
        } else if (showYear == theDayBeforeYesterday.get(Calendar.YEAR) && showMonth == theDayBeforeYesterday.get(Calendar.MONTH) && showDay == theDayBeforeYesterday.get(Calendar.DAY_OF_MONTH)) {
            //前天
            label = "前天";
        }

        return label;
    }

    /**
     * 时间搓转为{@link Date}
     *
     * @param millis
     * @return
     */
    public static Date formatToDate(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * @param date
     * @param format
     * @return
     */
    public static String dateToFormat(@NonNull Date date, DateStyle format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format.value);
        return simpleDateFormat.format(date);
    }

}