package com.shankephone.mi.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 赵亮
 * @date 2018-09-18 9:46
 */
@Component
@Data
public class PropertiesUtil
{
    public static int MQTT_DEFAULT_QOS;
    public static String MQTT_HOST;
    public static String MQTT_SERVER_CLIENTID;
    public static String MQTT_SERVER_PRODUCT_CLIENTID;
    public static String MQTT_USER_NAME;
    public static String MQTT_PASSWORD;
    public static int MQTT_TIMEOUT;
    public static int MQTT_KEEP_ALIVE;
    public static String MQTT_DEFAULT_TOPIC;

    static {
        MQTT_HOST =  loadMqttProperties().getProperty("MQTT_HOST");
        MQTT_SERVER_CLIENTID = loadMqttProperties().getProperty("MQTT_SERVER_CLIENTID");
        MQTT_SERVER_PRODUCT_CLIENTID = loadMqttProperties().getProperty("MQTT_SERVER_PRODUCT_CLIENTID");
        MQTT_USER_NAME = loadMqttProperties().getProperty("MQTT_USER_NAME");
        MQTT_PASSWORD = loadMqttProperties().getProperty("MQTT_PASSWORD");
        MQTT_TIMEOUT = Integer.valueOf(loadMqttProperties().getProperty("MQTT_TIMEOUT"));
        MQTT_KEEP_ALIVE = Integer.valueOf(loadMqttProperties().getProperty("MQTT_KEEP_ALIVE"));
        MQTT_DEFAULT_TOPIC = loadMqttProperties().getProperty("MQTT_DEFAULT_TOPIC");
        MQTT_DEFAULT_QOS = Integer.valueOf(loadMqttProperties().getProperty("MQTT_DEFAULT_QOS"));

    }

    private static Properties loadMqttProperties() {
        InputStream inputstream = PropertiesUtil.class.getResourceAsStream("/mqtt.yml");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
