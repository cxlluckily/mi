package com.shankephone.mi.mqtt.product;


import com.shankephone.mi.util.PropertiesUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


/**
 * @author 赵亮
 * @date 2018-09-18 11:40
 */
@Configuration
public class MQTTConfig
{
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttClientPersistence persistence = new MemoryPersistence();
        factory.setServerURIs(PropertiesUtil.MQTT_HOST);
        factory.setUserName(PropertiesUtil.MQTT_USER_NAME);
        factory.setPassword(PropertiesUtil.MQTT_PASSWORD);
        factory.setCleanSession(false);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(PropertiesUtil.MQTT_SERVER_PRODUCT_CLIENTID, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(PropertiesUtil.MQTT_DEFAULT_QOS);
        messageHandler.setDefaultTopic(PropertiesUtil.MQTT_DEFAULT_TOPIC);
//        messageHandler.setDefaultRetained(true);//如果设置了就会保留最后一次发送的任务
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
}
