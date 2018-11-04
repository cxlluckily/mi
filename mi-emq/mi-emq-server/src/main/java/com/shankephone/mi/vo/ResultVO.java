package com.shankephone.mi.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * Created by 廖师兄
 * 2017-05-12 14:13
 */
@Data
public class ResultVO<T> {


    /** 返回码. */
    private Integer statusCode;
    /** 返回总结果. */
    private String result;
    /** 提示信息. */
    private String message;

    /** 具体内容. */
    private T data;
    
}
