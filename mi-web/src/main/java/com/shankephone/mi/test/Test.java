package com.shankephone.mi.test;

/**
 * @author 赵亮
 * @date 2018-08-14 17:53
 */
public class Test
{
    @org.junit.Test
    public void sss()
    {
        String aa = "1@23@33";
        System.out.println(aa.substring(0,aa.lastIndexOf("@")));
    }
}
