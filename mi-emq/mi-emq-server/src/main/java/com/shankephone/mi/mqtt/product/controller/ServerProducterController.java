package com.shankephone.mi.mqtt.product.controller;

import com.shankephone.mi.mqtt.formbean.SendCommandMessageFormBean;
import com.shankephone.mi.mqtt.product.ServerProducter;
import com.shankephone.mi.mqtt.product.service.ServerProductService;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵亮
 * @date 2018-09-18 11:06
 */
@RestController
public class ServerProducterController
{

    @Autowired
    private ServerProducter serverProducter;


    @RequestMapping("/serverProducter/sendMqtt")
    public String sendMqtt(@RequestParam(value = "topic") String topic, @RequestParam(value = "message") String message)
    {
        try
        {
            serverProducter.sendToMqtt(message, topic);


            return "success";
        }
        catch (Exception ex)
        {
            return "fail";
        }

    }

    @Autowired
    private ServerProductService serverProductService;

    /**
     * 发送命令
     *
     * @author：赵亮
     * @date：2018年10月19日 09:56:11
     */
    @RequestMapping(value = "/serverProducter/sendCommandMessage")
    public ResultVO insertOne(@RequestBody SendCommandMessageFormBean formBean)
    {
        try
        {
            return ResultVOUtil.success(serverProductService.sendCommandMessage(formBean));
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "服务端给客户端发送命令消息出现异常");
        }

    }

}
