package com.shankephone.mi.batchcommand.dao;

import com.shankephone.mi.batchcommand.dao.provider.CommandDaoProvider;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-22 14:26
 */
@Repository
public interface CommandDao
{
    @SelectProvider(type = CommandDaoProvider.class, method = "getPreinstallCommandDDL")
    List<Map<String, Object>> getPreinstallCommandDDL();

    @InsertProvider(type = CommandDaoProvider.class, method = "insertDeviceMqttCommandBatchEntity")
    @Options(useGeneratedKeys = true, keyProperty = "mqttCommandBatchId")
    int insertDeviceMqttCommandBatchEntity(DeviceMqttCommandBatchEntity entity);

    @InsertProvider(type = CommandDaoProvider.class, method = "insertDeviceMqttCommandLogsEntity")
    @Options(useGeneratedKeys = true, keyProperty = "mqttCommandLogId")
    int insertDeviceMqttCommandLogsEntity(DeviceMqttCommandLogsEntity entity);

    @UpdateProvider(type = CommandDaoProvider.class, method = "updateDeviceMqttCommandLogsEntity")
    int updateDeviceMqttCommandLogsEntity(DeviceMqttCommandLogsEntity entity);
    
    @UpdateProvider(type = CommandDaoProvider.class, method = "updateCommandBatchSendJosn")
    int updateCommandBatchSendJosn(DeviceMqttCommandBatchEntity entity);

}
