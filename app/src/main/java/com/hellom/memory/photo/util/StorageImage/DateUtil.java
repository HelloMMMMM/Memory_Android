package com.hellom.memory.photo.util.StorageImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private DateUtil() {
        throw new UnsupportedOperationException("u can not init me...");
    }

    public static String getDateWithTodayOrYesterday(long timeMillis) {
        String todayOrYesterday = todayOrYesterday(timeMillis);
        return todayOrYesterday == null ? getDate(timeMillis) : todayOrYesterday;
    }

    public static String getDate(long timeMillis) {
        Locale locale = Locale.getDefault();
        SimpleDateFormat dateFormat;
        String language = locale.getLanguage();
        if (Locale.ENGLISH.getLanguage().equals(language)) {
            dateFormat = new SimpleDateFormat("MMM dd , yyyy", locale);
        } else if (Locale.CHINA.getLanguage().equals(language)) {
            dateFormat = new SimpleDateFormat("yyyy年MM月dd日", locale);
        } else {
            dateFormat = new SimpleDateFormat("MMM dd , yyyy", locale);
        }
        return dateFormat.format(new Date(timeMillis));
    }

    public static String todayOrYesterday(long timeMillis) {
        String today, yesterday, target = null;
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        if (Locale.ENGLISH.getLanguage().equals(language)) {
            today = "Today";
            yesterday = "Yesterday";
        } else if (Locale.CHINA.getLanguage().equals(language)) {
            today = "今天";
            yesterday = "昨天";
        } else {
            today = "Today";
            yesterday = "Yesterday";
        }
        if (isToday(timeMillis)) {
            target = today;
        } else if (isYesterday(timeMillis)) {
            target = yesterday;
        }
        return target;
    }

    public static boolean isToday(long timeMillis) {
        DateBean now = getDateBean(timeMillis, true);
        DateBean target = getDateBean(timeMillis, false);
        return now.getYear() == target.getYear() && now.getMonth() == target.getMonth() && now.getDay() == target.getDay();
    }

    public static boolean isYesterday(long timeMillis) {
        DateBean now = getDateBean(timeMillis, true);
        DateBean target = getDateBean(timeMillis, false);
        return now.getYear() == target.getYear() && now.getMonth() == target.getMonth() && (now.getDay() + 1) == target.getDay();
    }

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
