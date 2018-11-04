package com.shankephone.mi.wechat.config;

import com.shankephone.mi.util.PropertyAccessor;

/**
 * Created by 赵亮
 * 2017-07-30 11:43
 */

public  class ProjectUrlConfig
{

    /**
     * 微信公众平台授权url
     */
    public final static String wechatMpAuthorize = PropertyAccessor.getProperty("projecturl.wechatMpAuthorize");

    /**
     * 微信开放平台授权url
     */
    public final static String wechatOpenAuthorize = PropertyAccessor.getProperty("projecturl.wechatOpenAuthorize");

    /**
     * 数字运维系统
     */
    public final static String mi = PropertyAccessor.getProperty("projecturl.mi");

    /**
     * 数字运维系统手机端绑定用户页
     */
    public final static String phoneBindUrl=PropertyAccessor.getProperty("projecturl.phoneBindUser");
    public final static String phoneHome=PropertyAccessor.getProperty("projecturl.phoneHome");
    public final static String mqttServer=PropertyAccessor.getProperty("mqtt.server.url");
}
