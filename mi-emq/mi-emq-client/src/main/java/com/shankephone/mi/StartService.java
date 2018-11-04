package com.shankephone.mi;

import com.shankephone.mi.Mqttthred.ClientMQTT;
import com.shankephone.mi.Mqttthred.ServerMQTT;
import com.shankephone.mi.mqtt.consumer.dao.ClientDeviceDao;
import com.shankephone.mi.mqtt.enumeration.DeviceStatusEnum;
import com.shankephone.mi.mqtt.product.service.DeviceProductService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018/10/31 16:11
 */
@Component
@Order(value = 1)
public class StartService implements ApplicationRunner
{
    @Autowired
    private ClientDeviceDao clientDeviceDao;
    @Override
    public void run(ApplicationArguments args) throws MqttException
    {

        List<Map<String, Object>> decevice=clientDeviceDao.getClientDeviceList();
        for(Map<String, Object> map:decevice)
        {
           new RunnableDemo(map.get("deviceUid").toString(), map.get("deviceCode").toString(), map.get("stationCode").toString(),clientDeviceDao).start();
        }
    }

}

class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;
    private DeviceProductService deviceProductService=new DeviceProductService();
    private ServerMQTT server ;
    ClientDeviceDao clientDeviceDao;
    RunnableDemo(String name,String deviceCode,String stationCode,ClientDeviceDao clientDeviceDao) throws MqttException
    {
        this.clientDeviceDao=clientDeviceDao;
        threadName = name;
        server = new ServerMQTT(name);
        server.message = new MqttMessage();
        server.message.setQos(1);//保证消息能到达一次
        server.message.setRetained(false);
        server.message.setPayload(deviceProductService.onLineRegist(threadName,deviceCode,stationCode, DeviceStatusEnum.INSERVICE.getCode()).getBytes());
        server.publish(server.topic11 , server.message);



    }

    public void run() {
        System.out.println("Running " +  threadName );
        try {
           while (true)
           {
               ClientMQTT client = new ClientMQTT(threadName,threadName);
               client.start();
               Thread.sleep(2000);
               System.out.println("每隔2秒钟发送一下状态 " +  threadName );
               //每隔5秒钟发送一下状态

               List<Map<String, Object>> decevice=clientDeviceDao.getClientDeviceList();
               List<Map<String, Object>> decevicelist= decevice.stream().filter(map->threadName.equals(map.get("deviceUid").toString())).collect(Collectors.toList());
               String deviceStatus="";
               if(decevicelist!=null&&decevicelist.size()>0)
               {
                   deviceStatus=decevicelist.get(0).get("deviceStatus").toString();
               }

               server.message = new MqttMessage();
               server.message.setQos(1);//保证消息能到达一次
               server.message.setRetained(false);
               server.message.setPayload(deviceProductService.deviceStatusChange(threadName, deviceStatus).getBytes());
               server.publish(server.topic11 , server.message);

               System.out.println("每隔2秒钟发送一下状态完毕 " +  threadName );
           }
        }catch (Exception e) {
            System.out.println("Thread " +  threadName + " interrupted.");
            this.run();
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
