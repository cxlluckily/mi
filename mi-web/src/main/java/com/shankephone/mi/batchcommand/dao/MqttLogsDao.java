package com.shankephone.mi.batchcommand.dao;

import com.shankephone.mi.batchcommand.dao.provider.MqttLogsDaoProvider;
import com.shankephone.mi.batchcommand.formbean.GetExecuteCommandBatchListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqllLogsFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqttLogsDeviceListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOneEquipmentMqttLogsListFindEntity;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-22 10:42
 */
@Repository
public interface MqttLogsDao
{
    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getMqttLogsList")
    List<Map<String,Object>> getMqttLogsList(GetMqllLogsFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getMqttLogsListTotal")
    int getMqttLogsListTotal(GetMqllLogsFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getMqttLogsDeviceList")
    List<Map<String,Object>> getMqttLogsDeviceList(GetMqttLogsDeviceListFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getMqttLogsDeviceListTotal")
    int getMqttLogsDeviceListTotal(GetMqttLogsDeviceListFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getMqttLogsDeviceListDetail")
    DeviceMqttCommandLogsEntity getMqttLogsDeviceListDetail(DeviceMqttCommandLogsEntity entity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getOneEquipmentMqttLogsList")
    List<Map<String,Object>> getOneEquipmentMqttLogsList(GetOneEquipmentMqttLogsListFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getOneEquipmentMqttLogsListTotal")
    int getOneEquipmentMqttLogsListTotal(GetOneEquipmentMqttLogsListFindEntity findEntity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getDeviceMqttCommandBatchDetail")
    DeviceMqttCommandBatchEntity getDeviceMqttCommandBatchDetail(DeviceMqttCommandBatchEntity entity);

    @SelectProvider(type = MqttLogsDaoProvider.class, method = "getExecuteCommandBatchList")
    List<Map<String,Object>> getExecuteCommandBatchList(GetExecuteCommandBatchListFindEntity findEntity);
}
