package com.shankephone.mi.mqtt.consumer;

import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.consumer.impl.CommandServerReceivedConsumer;
import com.shankephone.mi.mqtt.consumer.impl.DeviceStatusChangeConsumer;
import com.shankephone.mi.mqtt.consumer.impl.ExecuteCommandResultConsumer;
import com.shankephone.mi.mqtt.consumer.impl.RegistConsumer;
import com.shankephone.mi.util.SpringUtil;

/**
 * @author 赵亮
 * @date 2018-10-12 14:42
 */
public class ConsumerFactory
{
    public static ConsumerAbs createConsumer(String type)
    {
        ConsumerAbs consumerAbs = null;
        switch (type)
        {
            case "Command":
            {
                consumerAbs = SpringUtil.getBean(ExecuteCommandResultConsumer.class);
                consumerAbs.setMessageType("Command");
                break;
            }
            case "CommandServerReceived":
            {
                consumerAbs = SpringUtil.getBean(CommandServerReceivedConsumer.class);
                consumerAbs.setMessageType("CommandServerReceived");
                break;
            }
            case "ExecuteCommandResult":
            {
                consumerAbs = SpringUtil.getBean(ExecuteCommandResultConsumer.class);
                consumerAbs.setMessageType("ExecuteCommandResult");
                break;
            }
            case "Regist":
            {
                consumerAbs = SpringUtil.getBean(RegistConsumer.class);
                consumerAbs.setMessageType("Regist");
                break;
            }
            case "StatusChange":
            {
                consumerAbs = SpringUtil.getBean(DeviceStatusChangeConsumer.class);
                consumerAbs.setMessageType("StatusChange");
                break;
            }
            default:
                return null;
        }
        return consumerAbs;
    }
}
