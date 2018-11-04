/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.common.enumeration;


/**
 * 状态 枚举
 *
 * @author 司徒彬
 * @date 2018-06-20 15:08
 */
public enum StatusEnum
{
    START_USING("start_using"), STOP_USING("stop_using"),DELETE_USING("delete_using");

    private String value;

    StatusEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
