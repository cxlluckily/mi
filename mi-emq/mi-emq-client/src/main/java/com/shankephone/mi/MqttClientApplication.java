package com.shankephone.mi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com")  //设置托管spring类的默认扫描范围
//@MapperScan(basePackages = {"com.shankephone.mi.mqtt.consumer.dao"})
public class MqttClientApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MqttClientApplication.class, args);
    }
}
