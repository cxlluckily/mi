package com.shankephone.mi.batchcommand.service;

import com.shankephone.mi.batchcommand.formbean.GetExecuteCommandBatchListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqllLogsFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqttLogsDeviceListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOneEquipmentMqttLogsListFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-22 10:20
 */
public interface MqttLogsService
{

    /**
     *获取mqtt日志列表
     *@author：赵亮
     *@date：2018-10-22 10:40
    */
    Pager getMqttLogsList(GetMqllLogsFindEntity findEntity);

    /**
     *获取批量执行的其批次下的设备列表
     *@author：赵亮
     *@date：2018-10-22 11:27
    */
    Pager getMqttLogsDeviceList(GetMqttLogsDeviceListFindEntity findEntity);

    /**
     *获取该批次的设备某条日志数据
     *@author：赵亮
     *@date：2018-10-22 13:48
    */
    DeviceMqttCommandLogsEntity getMqttLogsDeviceListDetail(DeviceMqttCommandLogsEntity entity);

    /**
     *获取某台设备mqtt日志分页数据
     *@author：赵亮
     *@date：2018-10-22 13:58
    */
    Pager getOneEquipmentMqttLogsList(GetOneEquipmentMqttLogsListFindEntity findEntity);

    /**
     *获取设备命令批次表详细信息
     *@author：赵亮
     *@date：2018-10-23 17:05
    */
    DeviceMqttCommandBatchEntity getDeviceMqttCommandBatchDetail(DeviceMqttCommandBatchEntity entity);

    /**
     *获取命令窗口左下角那个设备执行命令的列表数据
     *@author：赵亮
     *@date：2018-10-24 17:51
    */
    List<Map<String,Object>> getExecuteCommandBatchList(GetExecuteCommandBatchListFindEntity findEntity);
}
