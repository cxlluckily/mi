package com.shankephone.mi.command.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018/10/15 15:39
 */
@Data
public class CommandEntityVo
{
    private List<String> deviceIds;
    private  String commandType;
    private String commandCategory;
    private String message;

}
