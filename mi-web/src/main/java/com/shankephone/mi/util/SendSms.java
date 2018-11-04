package com.shankephone.mi.util;


import com.cssweb.corpay.sms.outer.inf.SmsService;
import com.cssweb.corpay.sms.outer.vo.RequestSendSmsVO;
import com.cssweb.corpay.sms.outer.vo.SmsResponseBase;

import java.util.Date;

/**
 * @program: mall-omp
 * @description: 发送手机短信
 * @author: Zhang.Bo
 * @create: 2018-08-22 14:52
 **/
public class SendSms {


//    private final static String md5Key = "fKECrZykQZj5DAfemKjn36xRN6nfcfKe";
    private final static String md5Key = "yQPRDedAwNhCdhymsRDXfhEDJjh4Yexx";

    private final static String url = "http://172.10.2.226:8180/cssweb-sms/ci/sms/requestSendSms";

    /**
     * 发送手机短信
     */
    public static void sendSms(String phone,String context)throws Exception{
        RequestSendSmsVO vo = new RequestSendSmsVO();
        vo.setContent(context);  //发送内容
        vo.setManagerId("");          //可空
        vo.setMsisdn(phone);       //手机号，尽量真实，否则失败多了可能被封
        vo.setRequestId(new Date().getTime()+"");  //唯一ID
        vo.setSenderId("10007");      //短信OMA后台配置
        vo.setSmsSign("【闪客蜂】");  //需报备
        SmsResponseBase smsResponse = SmsService.sendSms(vo, url, md5Key);
    }


}
