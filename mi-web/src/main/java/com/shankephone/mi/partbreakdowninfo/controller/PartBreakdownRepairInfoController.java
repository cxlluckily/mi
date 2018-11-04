package com.shankephone.mi.partbreakdowninfo.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import com.shankephone.mi.partbreakdowninfo.service.PartBreakdownRepairInfoService;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 维修信息
 * @author 赵亮
 * @date 2018-08-02 13:36
 */
@Controller
@RequestMapping("/partBreakdownRepairInfo")
public class PartBreakdownRepairInfoController
{
    @Autowired
    private PartBreakdownRepairInfoService partBreakdownRepairInfoService;

    /**
     * 新增
     * @author：赵亮
     * @date：2018-08-02
     * @param entity  the entity
     * @return the ResultVO
     */
    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody PartBreakdownRepairInfoEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partBreakdownRepairInfoService.insertOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"新增出现异常");
        }
    }

    /**
     * 修改
     * @author：赵亮
     * @date：2018-08-02
     * @param entity  the entity
     * @return the ResultVO
     */
    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody PartBreakdownRepairInfoEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partBreakdownRepairInfoService.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"修改出现异常");
        }
    }

    /**
     * 获得列表信息
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/getPartBreakdownRepairInfoList", method = RequestMethod.POST)
    public ResultVO getPartBreakdownRepairInfoList(@RequestBody PartBreakdownRepairInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownRepairInfoService.getPartBreakdownRepairInfoList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获得列表信息出现异常");
        }
    }

    /**
     * 根据查询条件返回配件名称列表
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/getSparePartDDLList", method = RequestMethod.POST)
    public ResultVO getSparePartDDLList(@RequestBody SparePartListForBreakdownFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownRepairInfoService.getSparePartDDLList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"根据查询条件返回配件名称列表出现异常");
        }
    }

    /**
     * 根据查询条件返回备件故障列表
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/getSparePartRepairDDLList", method = RequestMethod.POST)
    public ResultVO getSparePartRepairDDLList(@RequestBody PartBreakdownInfoDDLFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownRepairInfoService.getPartBreakdownInfoDDLList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"根据查询条件返回备件故障列表出现异常");
        }
    }
}
