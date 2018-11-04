package com.shankephone.mi.mqtt.consumer.dao;

import com.shankephone.mi.mqtt.consumer.dao.provider.ClientDeviceProvider;
import com.shankephone.mi.mqtt.model.ClientDeviceEntity;
import com.shankephone.mi.mqtt.model.ClientMqttLogsEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-16 17:51
 */
@Mapper
@Component
public interface ClientDeviceDao
{
    /**
     *添加客户端设备
     *@author：赵亮
     *@date：2018-10-18 17:20
    */
    @InsertProvider(type = ClientDeviceProvider.class, method = "insertClientDevice")
    @Options(useGeneratedKeys = true, keyProperty = "clientMqttLogId")
    int insertClientDevice(ClientMqttLogsEntity entity);

    /**
     *获取设备详细信息
     *@author：赵亮
     *@date：2018-10-19 10:17
    */
    @SelectProvider(type = ClientDeviceProvider.class, method = "getClientDeviceDetail")
    ClientDeviceEntity getClientDeviceDetail(ClientDeviceEntity deviceEntity );

    @SelectProvider(type = ClientDeviceProvider.class, method = "getClientDeviceList")
    List<Map<String, Object>> getClientDeviceList();
}
