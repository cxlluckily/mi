package com.shankephone.mi.wechat.service;

import com.shankephone.mi.wechat.formbean.KeFuMessageEntity;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author 郝伟州
 * @date 2018年9月26日16:14:06
 */
public interface WechatService
{
    /**
     *微信给用户发消息
     *@author：郝伟州
     *@date：2018年9月26日16:20:12
     */
    boolean sendKefuMessage(KeFuMessageEntity entity) throws WxErrorException;
    boolean sendKefuImageMessage(KeFuMessageEntity entity) throws WxErrorException;
    /**
     *微信给用户发模板消息
     *@author：郝伟州
     *@date：2018年10月16日15:06:54
     */
    boolean sendTemplateMessage(KeFuMessageEntity entity) throws WxErrorException;
}
