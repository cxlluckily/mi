package com.shankephone.mi.mqtt.consumer;

import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.consumer.impl.DeviceRegistrationReplyConsumer;
import com.shankephone.mi.mqtt.consumer.impl.ExecuteCommandConsumer;
import com.shankephone.mi.util.SpringUtil;

/**
 * 工厂
 * @author 赵亮
 * @date 2018-10-12 14:42
 */
public class ConsumerFactory
{
    public static ConsumerAbs createConsumer(String type)
    {
        ConsumerAbs consumerAbs;
        switch (type)
        {
            case "DeviceRegistrationReply":
            {
                consumerAbs = SpringUtil.getBean(DeviceRegistrationReplyConsumer.class);
                consumerAbs.setMessageType("DeviceRegistrationReply");
                break;
            }
            case "ExecuteCommand":
            {
                consumerAbs = SpringUtil.getBean(ExecuteCommandConsumer.class);
                consumerAbs.setMessageType("ExecuteCommand");
                break;
            }
            default:
                return null;
        }
        return consumerAbs;
    }
}
