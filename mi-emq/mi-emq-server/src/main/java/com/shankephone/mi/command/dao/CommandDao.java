package com.shankephone.mi.command.dao;


import com.shankephone.mi.command.dao.provider.CommandProvider;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;


/**
 * @author 郝伟州
 * @date 2018年10月16日11:18:21
 */
@Mapper
@Component(value = "commandDao")
public interface CommandDao
{
    /**
     * 更新设备调用命令日志表状态
     *
     * @author：赵亮
     * @date：2018-10-19 13:38
     */
    @UpdateProvider(type = CommandProvider.class, method = "updateMqttCommandLogStatus")
    Integer updateMqttCommandLogStatus(DeviceMqttCommandLogsEntity entity);

    /**
     *设备返回执行结果，把更新结果存到数据库中
     *@author：赵亮
     *@date：2018-10-19 14:00
    */
    @UpdateProvider(type = CommandProvider.class, method = "updateMqttCommandLogStatusAndResultJson")
    Integer updateMqttCommandLogStatusAndResultJson(DeviceMqttCommandLogsEntity entity);

    @UpdateProvider(type = CommandProvider.class, method = "updateMqttCommandLogStatusAndSendJson")
    Integer  updateMqttCommandLogStatusAndSendJson(DeviceMqttCommandLogsEntity entity);
}
