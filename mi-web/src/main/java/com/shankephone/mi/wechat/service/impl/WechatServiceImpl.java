package com.shankephone.mi.wechat.service.impl;

import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.util.DateUtil;
import com.shankephone.mi.util.PropertyAccessor;
import com.shankephone.mi.util.StringUtils;
import com.shankephone.mi.wechat.formbean.KeFuMessageEntity;
import com.shankephone.mi.wechat.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郝伟州
 * @date 2018年9月26日16:19:22
 */

@Service
@Slf4j
public class WechatServiceImpl implements WechatService
{
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private PendingTaskService pendingTaskService;


    @Override
    public boolean sendKefuMessage(KeFuMessageEntity entity) throws WxErrorException
    {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(entity.getToUser());
        wxMpKefuMessage.setMsgType(WxConsts.KefuMsgType.TEXT);
        wxMpKefuMessage.setContent(entity.getContent());
        return wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
    }


    @Override
    public boolean sendKefuImageMessage(KeFuMessageEntity entity) throws WxErrorException
    {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(entity.getToUser());
        wxMpKefuMessage.setMsgType(WxConsts.KefuMsgType.NEWS);

        List<WxMpKefuMessage.WxArticle> wxArticleList=new ArrayList<>();
        String openurl =PropertyAccessor.getProperty("projecturl.wechatOpenAuthorize");
        String url =PropertyAccessor.getProperty("projecturl.wechatMpAuthorize");

        WxMpKefuMessage.WxArticle wxArticle1=new WxMpKefuMessage.WxArticle();

        wxArticle1.setUrl(openurl);
        wxArticle1.setTitle("闪客蜂通知消息");
        wxArticleList.add(wxArticle1);

        WxMpKefuMessage.WxArticle wxArticle2=new WxMpKefuMessage.WxArticle();
        wxArticle2.setDescription(entity.getContent());
        wxArticle2.setPicUrl(url+"static/images/logo.png");
        wxArticle2.setUrl(openurl);
        wxArticle2.setTitle(entity.getContent());

        wxArticleList.add(wxArticle2);

        wxMpKefuMessage.setArticles(wxArticleList);

        return wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
    }

    @Override
    public boolean sendTemplateMessage(KeFuMessageEntity entity) throws WxErrorException
    {
        String openurl =PropertyAccessor.getProperty("projecturl.wechatOpenAuthorize");
        String weixintemplate =PropertyAccessor.getProperty("message.weixin.template");

        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(weixintemplate);
        templateMessage.setToUser(entity.getToUser());

        if(entity.isSendUrl()&&StringUtils.isNotEmpty(entity.getTaskType()))
        {
            templateMessage.setUrl(openurl + "?state=" + entity.getTaskType() + "@" + entity.getSourceId());
        }
        List<WxMpTemplateData> datas = new ArrayList<>();

        WxMpTemplateData first = new WxMpTemplateData();
        first.setName("first");
        first.setValue(entity.getContent());
        WxMpTemplateData word1 = new WxMpTemplateData();
        word1.setName("keyword1");
        word1.setValue(entity.getTypeName());
        WxMpTemplateData word2 = new WxMpTemplateData();
        word2.setName("keyword2");
        word2.setValue(DateUtil.getDateString());
        datas.add(first);
        datas.add(word1);
        datas.add(word2);
        templateMessage.setData(datas);
        String messageid="";
         try
         {
            messageid=wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
         }
         catch (Exception e)
         {
             log.error(e.getMessage());
             e.printStackTrace();
         }
        return StringUtils.isNotEmpty(messageid);
    }


}
