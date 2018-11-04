package com.shankephone.mi.mqtt.product;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author 赵亮
 * @date 2018-09-18 11:49
 */
@Component
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface ServerProducter
{

    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}