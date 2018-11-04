package com.shankephone.mi.util;

import java.util.Date;
import java.util.Random;

/**
 * @author 赵亮
 * @date 2018-07-12 9:51
 */
public class NumberFactory
{
    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     */
    public static synchronized String genUniqueKey()
    {
        Random random = new Random();
        Integer number = random.nextInt(90000) + 10000;

        return DateUtil.getDateShortString(new Date(), null) + String.valueOf(number);
    }


    public static String getbatchNo(String prefix)
    {
        return prefix + genUniqueKey();
    }


}
