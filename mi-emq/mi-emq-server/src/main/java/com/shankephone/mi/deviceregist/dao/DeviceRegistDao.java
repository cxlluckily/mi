package com.shankephone.mi.deviceregist.dao;

import com.shankephone.mi.deviceregist.dao.provider.DeviceRegistDaoProvider;
import com.shankephone.mi.model.DeviceMqttRegistLogsEntity;
import com.shankephone.mi.model.DeviceNoRegistEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OperationsEquipmentModifyLogEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author 赵亮
 * @date 2018-10-17 10:43
 */
@Mapper
@Component
public interface DeviceRegistDao
{
    /**
     *根据设备唯一ID查询线上设备信息
     *@author：赵亮
     *@date：2018-10-17 11:17
    */
    @SelectProvider(type = DeviceRegistDaoProvider.class, method = "getOperationsEquipmentDetail")
    OperationsEquipmentEntity getOperationsEquipmentDetail(String deviceuId);

    /**
     *新增未注册设备数据
     *@author：赵亮
     *@date：2018-10-17 11:17
    */
    @InsertProvider(type = DeviceRegistDaoProvider.class, method = "insertDeviceNoRegistEntity")
    @Options(useGeneratedKeys = true, keyProperty = "deviceNoRegistId")
    void insertDeviceNoRegistEntity(DeviceNoRegistEntity entity);

    /**
     *更新线上设备的注册信息
     *@author：赵亮
     *@date：2018-10-17 11:17
    */
    @UpdateProvider(type = DeviceRegistDaoProvider.class, method = "updateOperationsEquipmentStatus")
    void updateOperationsEquipmentStatus(OperationsEquipmentEntity entity);

    /**
     *新增注册日志表
     *@author：赵亮
     *@date：2018-10-17 11:24
    */
    @InsertProvider(type = DeviceRegistDaoProvider.class, method = "insertDeviceMqttRegistLogsEntity")
    @Options(useGeneratedKeys = true, keyProperty = "mqttRegistLogId")
    void insertDeviceMqttRegistLogsEntity(DeviceMqttRegistLogsEntity entity);

    /**
     *新增营运设备修改日志
     *@author：赵亮
     *@date：2018-10-17 11:17
     */
    @InsertProvider(type = DeviceRegistDaoProvider.class, method = "insertOperationsEquipmentModifyLogEntity")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentModifyLogId")
    void insertOperationsEquipmentModifyLogEntity(OperationsEquipmentModifyLogEntity entity);

    /**
     *获取单个未注册设备信息
     *@author：赵亮
     *@date：2018-10-17 15:11
    */
    @SelectProvider(type = DeviceRegistDaoProvider.class, method = "getOneDeviceNoRegistEntity")
    DeviceNoRegistEntity getOneDeviceNoRegistEntity(String deviceuId);
}
