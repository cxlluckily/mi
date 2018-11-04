package com.shankephone.mi.onlineequipment.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.onlineequipment.service.OnlineequipmentService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 郝伟州
 * @date 2018/8/23 10:10
 */
@Controller
@RequestMapping("/onlineequipment")
public class OnlinequipmentController
{
    @Autowired
    private OnlineequipmentService onlineequipmentService;

    /**
     * 根据所属站点查询设备信息接口，手机端接口
     *
     * @param entity the entity
     * @return the json object
     */
    //    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/getOnlinequipmentList", method = RequestMethod.POST)
    public ResultVO getOnlinequipmentList(@RequestBody OperationsEquipmentEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(onlineequipmentService.getOnlinequipmentList(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "手机端接口，根据所属站点查询设备信息接口出现异常");
        }
    }


    /**
     * 更新运营设备信息，手机端接口
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年8月23日13:38:49
     */
    //    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/updateOnlineequipment", method = RequestMethod.POST)
    public ResultVO updateOnlineequipment(@RequestBody OperationsEquipmentEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getEquipmentId()))
            {
                return ResultVOUtil.paramError("euipmentId");
            }
            return onlineequipmentService.updateOnlineequipment(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "手机端接口，更新运营设备信息出现异常");
        }
    }
}
