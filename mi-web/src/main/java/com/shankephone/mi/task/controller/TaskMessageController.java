package com.shankephone.mi.task.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.SendSms;
import com.shankephone.mi.wechat.formbean.KeFuMessageEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 消息
 * @author 郝伟州
 * @date 2018年9月26日09:53:45
 */
@Controller
@RequestMapping("/taskMessage")
public class TaskMessageController
{
    /**
     * 微信发消息
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日15:46:32
     */
    @ResponseBody
    @RequestMapping(value = "/sendSms", method = RequestMethod.POST)
    public ResultVO sendKefuMessage(@RequestBody KeFuMessageEntity entity)
    {
        try
        {
            SendSms.sendSms(entity.getToUser(),entity.getContent());
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, " 发短信出现异常");
        }
    }
}