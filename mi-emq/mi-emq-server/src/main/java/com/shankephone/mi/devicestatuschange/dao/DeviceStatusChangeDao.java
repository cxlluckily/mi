package com.shankephone.mi.devicestatuschange.dao;

import com.shankephone.mi.devicestatuschange.dao.provider.DeviceStatusChangeDaoProvider;
import com.shankephone.mi.model.DeviceStatusMonitorLogsEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

/**
 * @author 赵亮
 * @date 2018-10-17 10:43
 */
@Mapper
@Component
public interface DeviceStatusChangeDao
{
    /**
     *新增设备状态修改日志
     *@author：赵亮
     *@date：2018-10-17 11:17
    */
    @InsertProvider(type = DeviceStatusChangeDaoProvider.class, method = "insertDeviceStatusMonitorLogsEntity")
    @Options(useGeneratedKeys = true, keyProperty = "deviceStatusMonitorLogId")
    void insertDeviceStatusMonitorLogsEntity(DeviceStatusMonitorLogsEntity entity);

    /**
     *更新设备状态
     *@author：赵亮
     *@date：2018-10-17 15:08
    */
    @UpdateProvider(type = DeviceStatusChangeDaoProvider.class, method = "updateOperationsEquipmentStatus")
    void updateOperationsEquipmentStatus(OperationsEquipmentEntity entity);
}
