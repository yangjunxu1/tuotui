package com.zycw.common.util.wxwUtil;

import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
    private static final String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    public static final String STRING_MONDAY = "星期一";
    public static final String STRING_TUESDAY = "星期二";
    public static final String STRING_WEDNESDAY = "星期三";
    public static final String STRING_THURSDAY = "星期四";
    public static final String STRING_FRIDAY = "星期五";
    public static final String STRING_SATURDAY = "星期六";
    public static final String STRING_SUNDAY = "星期日";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SSSS = "yyyyMMddHHmmssss";

    public DateUtil() {
    }

    public static String parseDateToStr(Date date, String parsePatterns) {
        return DateFormatUtils.format(date, parsePatterns);
    }

    public static Integer parseDateToInteger(Date date, String parsePatterns) {
        String parseStr = DateFormatUtils.format(date, parsePatterns);
        return Integer.parseInt(parseStr);
    }

    public static String parseDateToStr(java.sql.Date date, String parsePatterns) {
        return DateFormatUtils.format(date, parsePatterns);
    }

    public static Calendar parseDateToCalendar(Date date) {
        return DateUtils.toCalendar(date);
    }

    public static String parseDateToStr(Timestamp timestamp, String parsePatterns) {
        return DateFormatUtils.format(timestamp, parsePatterns);
    }


    public static Date parseStrToDate(String str) {
        if (str == null) {
            return null;
        } else {
            try {
                return DateUtils.parseDate(str, parsePatterns);
            } catch (ParseException var2) {
                return null;
            }
        }
    }

    public static String parseLongToStr(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }

    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    public static Date addMonth(Date date, int monthNum) {
        return DateUtils.addMonths(date, monthNum);
    }

    public static String addMonthStr(Date date, int monthNum, String parsePatterns) {
        Date addDate = addMonth(date, monthNum);
        return parseDateToStr(addDate, parsePatterns);
    }

    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        return DateUtils.isSameDay(cal1, cal2);
    }

    private static long parseDateToLong(Date date) {
        Calendar calendar = DateUtils.toCalendar(date);
        return calendar.getTimeInMillis();
    }

    public static boolean isDateBetweenAAndB(Date date, long startTime, long endTime) {
        long dateLong = parseDateToLong(date);
        return dateLong >= startTime && dateLong <= endTime;
    }

    public static boolean isDateBetweenAAndB(Date date, Date startDate, Date endDate) {
        long dateLong = parseDateToLong(date);
        long startLong = parseDateToLong(startDate);
        long endLong = parseDateToLong(endDate);
        return dateLong >= startLong && dateLong <= endLong;
    }

    public static boolean isDateBetweenAAndB(Timestamp timeStamp, long startTime, long endTime) {
        long dateLong = timeStamp.getTime();
        return dateLong >= startTime && dateLong <= endTime;
    }

    public static String getWeeek(Date date) {
        Calendar calendar = parseDateToCalendar(date);
        int dayOfWeek = calendar.get(7);
        switch(dayOfWeek) {
        case 1:
            return "星期日";
        case 2:
            return "星期一";
        case 3:
            return "星期二";
        case 4:
            return "星期三";
        case 5:
            return "星期四";
        case 6:
            return "星期五";
        default:
            return "星期六";
        }
    }

    public static boolean isLaterHours(int hour) {
        Calendar calendar = parseDateToCalendar(new Date());
        int dateHour = calendar.get(11);
        return dateHour >= hour;
    }

    public static void main(String[] args) {
        System.out.println(isLaterHours(15));
    }
}
