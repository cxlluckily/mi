package com.shankephone.mi.batchcommand.controller;

import com.shankephone.mi.batchcommand.formbean.DeviceNoRegistFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOnlineEquipmentListFindEntity;
import com.shankephone.mi.batchcommand.service.OnlineEquipmentService;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OrgLineEntity;
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
 * Mqtt部分线上设备管理
 * @author 赵亮
 * @date 2018-10-23 9:33
 */
@Controller
@RequestMapping("/onlineEquipment")
public class OnlineEquipmentController
{
    @Autowired
    private OnlineEquipmentService onlineEquipmentService;
    /**
     * 返回线上设备列表
     * @author：赵亮
     * @date：2018-10-23 
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getOnlineEquipmentList", method = RequestMethod.POST)
    public ResultVO getOnlineEquipmentList(@RequestBody GetOnlineEquipmentListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(onlineEquipmentService.getOnlineEquipmentList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"返回线上设备列表出现异常");
        }
    }

    /**
     * 获取所有线路
     * @author：赵亮
     * @date：2018-10-23
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getAllLine", method = RequestMethod.POST)
    public ResultVO getAllLine(@RequestBody BaseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(onlineEquipmentService.getAllLine(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获取所有线路出现异常");
        }
    }

    /**
     * 获取线路下车站
     * @author：赵亮
     * @date：2018-10-23
     * @param entity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getStationListByLineId", method = RequestMethod.POST)
    public ResultVO getStationListByLineId(@RequestBody OrgLineEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(onlineEquipmentService.getStationListByLineId(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获取线路下车站出现异常");
        }
    }

    /**
     * 获取未注册成功的设备信息
     * @author：郝伟州
     * @date：2018-10-23
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getDeviceNoRegistList", method = RequestMethod.POST)
    public ResultVO getDeviceNoRegistList(@RequestBody DeviceNoRegistFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(onlineEquipmentService.getDeviceNoRegistList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获取未注册成功的设备信息出现异常");
        }
    }

    /**
     * 未注册的设备信息，绑定在线运营设备
     * @author：郝伟州
     * @date：2018-10-23
     * @param entity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/updateOperationsEquipment", method = RequestMethod.POST)
    public ResultVO updateOperationsEquipment(@RequestBody OperationsEquipmentEntity entity)
    {
        try
        {
            entity.validateUserKey();
            onlineEquipmentService.updateOperationsEquipment(entity);
            return ResultVOUtil.success();
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"未注册的设备信息，绑定在线运营设备出现异常");
        }
    }
}
