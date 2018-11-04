package com.shankephone.mi.batchcommand.controller;

import com.shankephone.mi.batchcommand.formbean.GetExecuteCommandBatchListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqllLogsFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetMqttLogsDeviceListFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOneEquipmentMqttLogsListFindEntity;
import com.shankephone.mi.batchcommand.service.MqttLogsService;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.DeviceMqttCommandBatchEntity;
import com.shankephone.mi.model.DeviceMqttCommandLogsEntity;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Mqtt日志查看
 * @author 赵亮
 * @date 2018-10-22 10:17
 */
@Controller
@RequestMapping("/mqttLogs")
public class MqttLogsController
{
    @Autowired
    private MqttLogsService mqttLogsService;
    /**
     *获取mqtt日志列表
     *@author：赵亮
     *@date：2018-10-22 10:21
    */
    @ResponseBody
    @RequestMapping(value = "/getMqttLogsList", method = RequestMethod.POST)
    public ResultVO getMqttLogsList(@RequestBody GetMqllLogsFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Pager pager = mqttLogsService.getMqttLogsList(findEntity);
            return ResultVOUtil.success(pager);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取mqtt日志列表出现异常");
        }
    }

    /**
     *获取批量执行的其批次下的设备列表
     *@author：赵亮
     *@date：2018-10-22 10:21
     */
    @ResponseBody
    @RequestMapping(value = "/getMqttLogsDeviceList", method = RequestMethod.POST)
    public ResultVO getMqttLogsDeviceList(@RequestBody GetMqttLogsDeviceListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(mqttLogsService.getMqttLogsDeviceList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取批量执行的其批次下的设备列表出现异常");
        }
    }

    /**
     *获取设备命令批次表详细信息
     *@author：赵亮
     *@date：2018-10-22 10:21
     */
    @ResponseBody
    @RequestMapping(value = "/getDeviceMqttCommandBatchDetail", method = RequestMethod.POST)
    public ResultVO getDeviceMqttCommandBatchDetail(@RequestBody DeviceMqttCommandBatchEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(mqttLogsService.getDeviceMqttCommandBatchDetail(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取设备命令批次表详细信息出现异常");
        }
    }

    /**
     *获取该批次的设备某条日志数据
     *@author：赵亮
     *@date：2018-10-22 10:21
     */
    @ResponseBody
    @RequestMapping(value = "/getMqttLogsDeviceListDetail", method = RequestMethod.POST)
    public ResultVO getMqttLogsDeviceListDetail(@RequestBody DeviceMqttCommandLogsEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(mqttLogsService.getMqttLogsDeviceListDetail(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取该批次的设备某条日志数据出现异常");
        }
    }

    /**
     *获取某台设备mqtt日志分页数据
     *@author：赵亮
     *@date：2018-10-22 13:53
    */
    @ResponseBody
    @RequestMapping(value = "/getOneEquipmentMqttLogsList", method = RequestMethod.POST)
    public ResultVO getOneEquipmentMqttLogsList(@RequestBody GetOneEquipmentMqttLogsListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(mqttLogsService.getOneEquipmentMqttLogsList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取某台设备mqtt日志分页数据出现异常");
        }
    }

    /**
     *获取命令窗口左下角那个设备执行命令的列表数据
     * 参数是：List<Long>mqttCommandBatchIds
     *@author：赵亮
     *@date：2018-10-24 16:26
     * 序号、时间、批号、下发、应答、完成、操作（查看）
    */
    @ResponseBody
    @RequestMapping(value = "/getExecuteCommandBatchList", method = RequestMethod.POST)
    public ResultVO getExecuteCommandBatchList(@RequestBody GetExecuteCommandBatchListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(mqttLogsService.getExecuteCommandBatchList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取命令窗口左下角那个设备执行命令的列表数据出现异常");
        }
    }
}
