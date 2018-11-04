package com.shankephone.mi.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-20 16:27
 */
@Data
public class FunctionTreeVO
{
    private String text;
    private boolean expanded;
    private String iconCls;
    private String xclass;
    private String id;
    private boolean leaf;
    private List<FunctionTreeVO> children;
}
