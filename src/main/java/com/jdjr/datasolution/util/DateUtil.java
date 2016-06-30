package com.jdjr.datasolution.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Description: DateUtil
 * Author: wangbo
 * Create: 2014-09-03 19:20
 */
public class DateUtil {
    /**
     * 获得指定日期的零点零分零秒
     *
     * @param date
     * @return
     */
    public static Date getDateStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }

    /**
     * 获得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 获得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 返回yyyy-MM-dd格式的日期字符串
     *
     * @param d
     * @return
     */
    public static String getStringDateShort(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(d);
        return dateString;
    }

    /**
     * 根据yyyy-MM-dd的字符串返回Date对象
     *
     * @param s
     * @return
     */
    public static Date getDateShortFromString(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据format的字符串返回Date对象
     *
     * @param s
     * @param format
     * @return
     */
    public static Date getDateByFormatFromString(String s, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回format指定格式的日期字符串
     *
     * @param d
     * @param format
     * @return
     */
    public static String getStringDateByFormat(Date d, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(d);
        return dateString;
    }

    /**
     * 获得n天后的Timestamp
     *
     * @param ts
     * @param n
     * @return
     */
    public static Timestamp getNDaysAfterTimestamp(Timestamp ts, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        cal.add(Calendar.DAY_OF_WEEK, n);
        return (new Timestamp(cal.getTime().getTime()));
    }

    /**
     * 拿到WEEK数
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR)+1;
    }

    /**
     * 拿到WEEK数
     * @param date
     * @return
     */
    public static int getWeekOfYear(String date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(getDateShortFromString(date));
        return c.get(Calendar.WEEK_OF_YEAR)+1;
    }

    /**
     * 将毫秒数，按照format的格式，转化成日期的字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String getStringFromLongByFormat(long time, String format) {
        DateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

    /**
     * 将时间字符串转化成毫秒
     *
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static long getMillisFromStringByFormat(String date, String format) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(format).parse(date));
        return c.getTimeInMillis();
    }


    /**
     * 得到年
     * @param d
     * @return
     */
    public static int getYear(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 得到月
     * @param d
     * @return
     */
    public static int getMonth(Date d){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        return now.get(Calendar.MONTH);
    }

    public static void main(String args[]){
        String aa = DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd");
        System.out.println(aa);
    }
}