package com.shankephone.mi.batchcommand.service.impl;

import com.shankephone.mi.batchcommand.dao.MqttLogsDao;
import com.shankephone.mi.batchcommand.formbean.GetExecuteCommandBatchListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqllLogsFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqttLogsDeviceListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOneEquipmentMqttLogsListFindEntity;
import com.shankephone.mi.batchcommand.service.MqttLogsService;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-22 10:21
 */
@Service
public class MqttLogsServiceImpl implements MqttLogsService
{
    @Autowired
    private MqttLogsDao mqttLogsDao;

    /**
     * 获取mqtt日志列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-22 10:40
     */
    @Override
    public Pager getMqttLogsList(GetMqllLogsFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = mqttLogsDao.getMqttLogsList(findEntity);
            int total = mqttLogsDao.getMqttLogsListTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取批量执行的其批次下的设备列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-22 11:20
     */
    @Override
    public Pager getMqttLogsDeviceList(GetMqttLogsDeviceListFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = mqttLogsDao.getMqttLogsDeviceList(findEntity);
            int total = mqttLogsDao.getMqttLogsDeviceListTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取该批次的设备某条日志数据
     *
     * @param entity
     * @author：赵亮
     * @date：2018-10-22 11:20
     */
    @Override
    public DeviceMqttCommandLogsEntity getMqttLogsDeviceListDetail(DeviceMqttCommandLogsEntity entity)
    {
        try
        {
            entity = mqttLogsDao.getMqttLogsDeviceListDetail(entity);
            return entity;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取某台设备mqtt日志分页数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-22 13:58
     */
    @Override
    public Pager getOneEquipmentMqttLogsList(GetOneEquipmentMqttLogsListFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = mqttLogsDao.getOneEquipmentMqttLogsList(findEntity);
            int total = mqttLogsDao.getOneEquipmentMqttLogsListTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取设备命令批次表详细信息
     * @param entity
     * @author：赵亮
     * @date：2018-10-23 10:35
     */
    @Override
    public DeviceMqttCommandBatchEntity getDeviceMqttCommandBatchDetail(DeviceMqttCommandBatchEntity entity)
    {
        try
        {
            return mqttLogsDao.getDeviceMqttCommandBatchDetail(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取命令窗口左下角那个设备执行命令的列表数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-24 17:51
     */
    @Override
    public List<Map<String, Object>> getExecuteCommandBatchList(GetExecuteCommandBatchListFindEntity findEntity)
    {
        try
        {
           return mqttLogsDao.getExecuteCommandBatchList(findEntity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }


}
