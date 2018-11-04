/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ObjectUtils
 *
 * @author 司徒彬
 * @date 2017-03-30 14:03
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils
{
    public static boolean isEmpty(Object object)
    {
        return !isNotEmpty(object);
    }

    public static boolean isNotEmpty(Object object)
    {
        boolean isNotEmpty = StringUtils.isNotEmpty(object);
        if (object == null)
        {
            isNotEmpty = false;
        }
        else if (object != null && (object.getClass().equals(List.class) || object.getClass().equals(ArrayList.class)))
        {
            isNotEmpty = isNotEmpty && ((List) object).size() != 0;
        }

        return isNotEmpty;
    }

    public static boolean isNull(Object object)
    {
        return object == null;

    }

    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }
}
