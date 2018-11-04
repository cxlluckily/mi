package com.shankephone.mi.util;

/**
 * StringUtils重载方法
 *
 * @author 司徒彬
 * @date 2017 -03-15 10:56
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * Is empty boolean.
     *
     * @param cs the cs
     * @return the boolean
     */
    public static boolean isEmpty(CharSequence cs) {
        return !isNotEmpty(cs);
    }

    /**
     * Is not empty boolean.
     *
     * @param cs the cs
     * @return the boolean
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return isNotEmpty((Object) cs);
    }

    /**
     * Is empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmpty(Object str) {
        return !isNotEmpty(str);
    }

    /**
     * Is not empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotEmpty(Object str) {
        return str != null && str.toString().trim().length() != 0;
    }

}
