/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * DataSwitch 操作类
 *
 * @author 司徒彬
 * @date 2017年1月12日12 :06:33
 */
public class DataSwitch
{

    /**
     * 将json格式的字符串转换成目标实体
     *
     * @param <T>   the type parameter
     * @param json  ：json格式的字符串
     * @param clazz ：实体
     * @return the t
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static <T> T convertJsonToEntity(JsonObject json, Class<T> clazz) {
        try {
            Gson gson = new Gson();
            T entity = gson.fromJson(json, clazz);
            return entity;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 将对象转换成Long对象，如果传入对象为 null 或 空，返回 0l
     *
     * @param value ： 传入参数值
     * @return the long
     */
    public static Long convertObjectToLong(Object value) {
        try {
            if (null != value) {
                return NumberUtils.toLong(value.toString(), 0);
            } else {
                return 0l;
            }
        } catch (Exception e) {
            return 0l;
        }
    }
}
