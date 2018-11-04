/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.util;


import com.shankephone.mi.common.enumeration.DateFormatEnum;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtils，时间操作类
 *
 * @author 司徒彬
 * @date 2016年10月27日13:50:56
 */
public class DateUtils
{

    /**
     * 获得当前时间的字符串 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @return the date string
     */
    public static String getDateString()
    {
        return getDateString(new Date(), null);
    }

    /**
     * 根据制指定的格式，获得当前时间的字符串
     *
     * @param dateFormatEnum the date format enum
     * @return the date string
     */
    public static String getDateString(DateFormatEnum dateFormatEnum)
    {
        return getDateString(new Date(), dateFormatEnum);
    }


    /**
     * 根据指定的格式，得到指定时间的字符串
     *
     * @param date           the date
     * @param dateFormatEnum the date format enum
     * @return the date string
     */
    public static String getDateString(Date date, DateFormatEnum dateFormatEnum)
    {
        if (date == null)
        {
            return null;
        }
        dateFormatEnum = dateFormatEnum == null ? DateFormatEnum.YYYY_MM_DD_HH_MM_SS : dateFormatEnum;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatEnum.getValue());
        return simpleDateFormat.format(date);
    }

    public static String getDateString(Timestamp timestamp, DateFormatEnum dateFormatEnum)
    {
        if (timestamp == null)
        {
            return "";
        }
        Date date = new Date(timestamp.getTime());
        return getDateString(date, dateFormatEnum);
    }

    /**
     * 获得当前时间
     *
     * @return the date
     */
    public static Timestamp getNow()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 根据指定的字符串获得时间
     *
     * @param dateStr 时间字符串 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Timestamp getDate(String dateStr) throws ParseException
    {
        return getDate(dateStr, null);
    }

    /**
     * Gets date.
     *
     * @param dateStr        the date str
     * @param dateFormatEnum the date format enum
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Timestamp getDate(String dateStr, DateFormatEnum dateFormatEnum) throws ParseException
    {
        dateFormatEnum = dateFormatEnum == null ? DateFormatEnum.YYYY_MM_DD_HH_MM_SS : dateFormatEnum;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatEnum.getValue());
        Date date = simpleDateFormat.parse(dateStr);
        return new Timestamp(date.getTime());
    }


    /**
     * 获取数据查询日期，格式是：2014-04-02，day是天差，如果传-1是昨天的日期
     *
     * @author：ErebusST @date：2017/4/25 14:19
     */
    public static Timestamp addDay(int day)
    {
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, day);
        Date date = cal.getTime();
        Timestamp currentTimestamp = new Timestamp(date.getTime());
        return currentTimestamp;
    }

    public static Timestamp addDay(Timestamp date, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(Calendar.DATE, day);
        Date result = cal.getTime();
        Timestamp currentTimestamp = new Timestamp(result.getTime());
        return currentTimestamp;
    }


    public static Date getStartTime(int day)
    {
        Calendar todayStart = Calendar.getInstance();
        todayStart.add(Calendar.DATE, day);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    public static Date getEndTime(int day)
    {
        Calendar todayEnd = Calendar.getInstance();

        todayEnd.add(Calendar.DATE, day);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public static String getStartTime(String specifiedDay, int day)
    {
        Calendar todayStart = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(DateFormatEnum.YYYY_MM_DD.getValue()).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        todayStart.setTime(date);
        todayStart.add(Calendar.DATE, day);
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        String datestr = new SimpleDateFormat(DateFormatEnum.YYYY_MM_DD_HH_MM_SS.getValue()).format(todayStart.getTime());
        return datestr;
    }

    public static String getEndTime(String specifiedDay, int day)
    {
        Calendar todayEnd = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(DateFormatEnum.YYYY_MM_DD_HH_MM_SS.getValue()).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        todayEnd.setTime(date);
        todayEnd.add(Calendar.DATE, day);
        todayEnd.set(Calendar.HOUR, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        String datestr = new SimpleDateFormat(DateFormatEnum.YYYY_MM_DD_HH_MM_SS.getValue()).format(todayEnd.getTime());
        return datestr;
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay, String dateFormat)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(dateFormat).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat(dateFormat).format(c.getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, String dateFormat)
    {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try
        {
            date = new SimpleDateFormat(dateFormat).parse(specifiedDay);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat(dateFormat).format(c.getTime());
        return dayAfter;
    }
}
