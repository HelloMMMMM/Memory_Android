package com.hellom.mediastore.util;

import android.content.Context;

import com.hellom.mediastore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil() {
        throw new UnsupportedOperationException("u cannot init me!");
    }

    /**
     * 由于MediaStore时间戳只到秒级，所以转换为毫秒级
     *
     * @param timeMillis 时间戳
     * @return 毫秒级时间戳
     */
    public static long convertMs(long timeMillis) {
        return timeMillis * 1000;
    }

    /**
     * 根据时间戳返回日期字符串，默认常见逻辑
     * 1.无法取得日期，设置未知日期
     * 2.今天或者昨天的判断
     * 3.是否为今年的判断
     *
     * @param timeMillis 时间戳
     * @return 日期字符串
     */
    public static String getDefaultDate(Context context, long timeMillis) {
        String target;
        if (isUnknownTime(timeMillis)) {
            target = unknownTime(timeMillis);
        } else {
            String todayOrYesterday = todayOrYesterday(context, timeMillis);
            if (todayOrYesterday == null) {
                target = isThisYear(timeMillis) ? getDateWithoutYear(context, timeMillis) : getDate(context, timeMillis);
            } else {
                target = todayOrYesterday;
            }
        }
        return target;
    }

    /**
     * 根据时间戳返回日期字符串
     *
     * @param timeMillis 时间戳
     * @return 指定格式的日期字符串
     */
    public static String getDate(Context context, long timeMillis) {
        String pattern = context.getResources().getString(R.string.date_with_year);
        return format(pattern, timeMillis);
    }

    /**
     * 返回不带年份的日期
     *
     * @param timeMillis 时间戳
     * @return 不带年份的日期
     */
    public static String getDateWithoutYear(Context context, long timeMillis) {
        String pattern = context.getResources().getString(R.string.date_without_year);
        return format(pattern, timeMillis);
    }

    /**
     * 返回格式化的时间（年月日时分）
     *
     * @param timeMillis 时间戳
     * @return 格式化的-年月日时分
     */
    public static String getTime(Context context, long timeMillis) {
        String pattern = context.getResources().getString(R.string.time);
        return format(pattern, timeMillis);
    }

    private static String format(String pattern, long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(new Date(timeMillis));
    }

    /**
     * 如果不是今天与昨天，返回null；是今天或者昨天返回相应字符串；
     *
     * @param timeMillis 时间戳
     * @return 今天或昨天的字符串，都不是为null
     */
    public static String todayOrYesterday(Context context, long timeMillis) {
        String today = context.getResources().getString(R.string.today);
        String yesterday = context.getResources().getString(R.string.yesterday);
        String target = null;
        if (isToday(timeMillis)) {
            target = today;
        } else if (isYesterday(timeMillis)) {
            target = yesterday;
        }
        return target;
    }

    /**
     * 资源添加时间戳一般都大于0，若不大于0，表示未提取出时间戳，为未知时间
     *
     * @param timeMillis 时间戳
     * @return 是否未提取出时间戳，时间戳为0
     */
    public static boolean isUnknownTime(long timeMillis) {
        return timeMillis <= 0;
    }

    /**
     * 返回未知的日期字符串
     *
     * @param timeMillis 时间戳
     * @return 未知日期字符串
     */
    public static String unknownTime(long timeMillis) {
        String unknownTime;
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        if (Locale.ENGLISH.getLanguage().equals(language)) {
            unknownTime = "Unknown Date";
        } else if (Locale.CHINA.getLanguage().equals(language)) {
            unknownTime = "未知日期";
        } else {
            unknownTime = "Unknown Date";
        }
        return unknownTime;
    }

    /**
     * 是否是今天
     *
     * @param timeMillis 时间戳
     * @return 是否是今天
     */
    public static boolean isToday(long timeMillis) {
        DateBean now = getDateBean(timeMillis, true);
        DateBean target = getDateBean(timeMillis, false);
        return now.getYear() == target.getYear() && now.getMonth() == target.getMonth() && now.getDay() == target.getDay();
    }

    /**
     * 是否是昨天
     *
     * @param timeMillis 时间戳
     * @return 日期是否是昨天
     */
    public static boolean isYesterday(long timeMillis) {
        DateBean now = getDateBean(timeMillis, true);
        DateBean target = getDateBean(timeMillis, false);
        return now.getYear() == target.getYear() && now.getMonth() == target.getMonth() && (now.getDay() - 1) == target.getDay();
    }

    /**
     * 是否为同一年
     *
     * @param timeMillis 时间戳
     * @return 是否为同一年
     */
    public static boolean isThisYear(long timeMillis) {
        DateBean now = getDateBean(timeMillis, true);
        DateBean target = getDateBean(timeMillis, false);
        return now.getYear() == target.getYear();
    }

    /**
     * 返回年月日日期实体
     *
     * @param timeMillis 时间戳
     * @param isNow      是否取现在的时间
     * @return 年月日日期实体
     */
    private static DateBean getDateBean(long timeMillis, boolean isNow) {
        Calendar calendar = Calendar.getInstance();
        if (!isNow) {
            calendar.setTime(new Date(timeMillis));
        }
        DateBean dateBean = new DateBean();
        dateBean.setYear(calendar.get(Calendar.YEAR));
        dateBean.setMonth(calendar.get(Calendar.MONTH));
        dateBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        return dateBean;
    }

    /**
     * 年月日日期类
     */
    private static class DateBean {
        private int year;
        private int month;
        private int day;

        int getYear() {
            return year;
        }

        void setYear(int year) {
            this.year = year;
        }

        int getMonth() {
            return month;
        }

        void setMonth(int month) {
            this.month = month;
        }

        int getDay() {
            return day;
        }

        void setDay(int day) {
            this.day = day;
        }
    }
}
