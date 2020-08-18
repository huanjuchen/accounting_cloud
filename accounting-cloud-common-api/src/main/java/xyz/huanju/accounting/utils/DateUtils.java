package xyz.huanju.accounting.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HuanJu
 * @date 2020/8/18 0:35
 */
public final class DateUtils {

    private final static String PATTERN = "yyyy-MM-dd";


    public static Date getDate(String dateStr) {
        try {
            return new SimpleDateFormat().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }


    public static String[] monthStartEnd(Date date) {
        String dateStr = new SimpleDateFormat().format(date);
        return monthStartEnd(dateStr);
    }

    public static String[] monthStartEnd(String dateStr) {
        String[] ymd = dateStr.split("-");
        String yearStr = ymd[0];
        String monthStr = ymd[1];
        int lastDay = getMonthLastDay(dateStr);
        String start = yearStr +
                '-' +
                monthStr +
                "-01";
        String end = yearStr +
                '-' +
                monthStr +
                '-' +
                lastDay;
        return new String[]{start, end};
    }


    public static int getMonthLastDay(String dateStr) {
        String[] ymd = dateStr.split("-");
        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        return isLeapYear(year) ? 29 : 28;
    }

    /**
     * 查看year是否为闰年
     */
    public static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        } else {
            return year % 4 == 0 && year % 100 != 0;
        }
    }

}
