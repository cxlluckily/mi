package com.shankephone.mi.repair.enumeration;


import lombok.Getter;

/**
 * @author 郝伟州
 * @date 2018年9月3日15:19:13
 */
@Getter
public enum FaultType
{
    REPORT("reported", "上报"),
    REPAIR("repair", "维修");

    private String code;

    private String message;

    FaultType(String code, String message)
    {
        this.code = code;
        this.message = message;
    }
}
