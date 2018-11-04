package com.shankephone.mi.mqtt.consumer;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shankephone.mi.mqtt.consumer.abs.ConsumerAbs;
import com.shankephone.mi.mqtt.model.HearderBean;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;


/**
 * @author 赵亮
 * @date 2018-09-18 11:40
 */
@Configuration
@Component
@Slf4j
public class DeviceConsumer
{
    @Bean
    public MqttPahoClientFactory mqttClientFactory()
    {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setServerURIs(PropertiesUtil.MQTT_HOST);
        factory.setUserName(PropertiesUtil.MQTT_USER_NAME);
        factory.setPassword(PropertiesUtil.MQTT_PASSWORD);
        factory.setCleanSession(false);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound()
    {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(PropertiesUtil.MQTT_PRODUCT_CLIENTID, mqttClientFactory(), PropertiesUtil.MQTT_DEFAULT_TOPIC);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(PropertiesUtil.MQTT_DEFAULT_QOS);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     *处理接收到的待消费信息
     *@author：赵亮
     *@date：2018-10-18 9:19
    */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handlerTest()
    {
        return new MessageHandler()
        {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException
            {
                try
                {
                    //事例代码
                    String json = message.getPayload().toString();
                    JsonParser jsonParser = new JsonParser();
                    JsonObject object = jsonParser.parse(json).getAsJsonObject();
                    HearderBean header = DataSwitch.convertJsonToEntity(object.get("header").getAsJsonObject(),HearderBean.class);
                    ConsumerAbs consumer = ConsumerFactory.createConsumer(header.getMessageType());
                    consumer.exec(object);
                }
                catch (MessagingException ex)
                {
                    log.info(ex.getMessage());
                }
                catch (Exception e)
                {
                    log.info(e.getMessage());
                }
            }
        };
    }
}
