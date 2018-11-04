package com.shankephone.mi.util;

/**
 * @author 赵亮
 * @date 2018-06-25 15:29
 */
public class PathSetting
{
    public static String getUserHeadPicUrl(String fileName)
    {
        return PropertyAccessor.getProperty("webService") + PropertyAccessor.getProperty("userPhoto") + fileName;
    }

    public static String getSparePartPicUrl(String fileName)
    {
        return PropertyAccessor.getProperty("webService") + PropertyAccessor.getProperty("sparePartPhoto") + fileName;
    }
}
