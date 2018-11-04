package com.shankephone.mi.util;

import com.shankephone.mi.common.enumeration.DateFormatEnum;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateUtil，时间操作类
 *
 * @author 司徒彬
 * @date 2016年10月27日13:50:56
 */
public class DateUtil
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

    /**
     * 根据指定的格式，得到指定时间的字符串
     *
     * @param date           the date
     * @param dateFormatEnum the date format enum
     * @return the date string
     */
    public static String getDateShortString(Date date, DateFormatEnum dateFormatEnum)
    {
        if (date == null)
        {
            return null;
        }
        dateFormatEnum = dateFormatEnum == null ? DateFormatEnum.YYYYMMDD : dateFormatEnum;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatEnum.getValue());
        return simpleDateFormat.format(date);
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


}
