package com.shankephone.mi.util;

import com.shankephone.mi.common.enumeration.ApplyNumberPrefixEnum;

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

    /**
     * 获取出库申请单的单据号
     *
     * @author：赵亮
     * @date：2018-07-12 9:54
     */
    public static String getOutStockApplyNO(String prefix)
    {
        return prefix + genUniqueKey();
    }

    public static String getInStockApplyNO(String prefix)
    {
        return prefix + genUniqueKey();
    }

    public static String getApplyNo(ApplyNumberPrefixEnum prefixEnum)
    {
        return getApplyNo(prefixEnum.getCode());
    }

    public static String getApplyNo(String prefix)
    {
        return prefix + genUniqueKey();
    }

    public static String getInventTaskNo(String prefix)
    {
        return prefix + genUniqueKey();
    }

    public static String getCommandBatchNo(String prefix)
    {
        return prefix + genUniqueKey();
    }
}
