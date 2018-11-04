package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.dao.WarehouseDao;
import com.shankephone.mi.inventory.formbean.WarehouseFindEntity;
import com.shankephone.mi.inventory.service.WarehouseService;
import com.shankephone.mi.inventory.vo.WarehouseVO;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 仓库管理Controller
 *
 * @author 司徒彬
 * @date 2018/7/2 17:08
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController
{
    @Autowired
    private WarehouseService warehouseService;

    /**
     * 获取分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    ////@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/getPagerInfo", method = RequestMethod.POST)
    public ResultVO getPagerInfo(@RequestBody WarehouseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                List<Map<String, Object>> list = warehouseService.getPagerInfo(findEntity);
                return ResultVOUtil.success(list);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取分页数据出现异常");
        }
    }

    /**
     * 添加页面初始化数据
     * <p>
     * 根据传入的父节点Id 获取初始化数据
     * 如果不是一级库的时候的话 要根据父Id 获取父节点对应的工区设置的站点，新添加的节点选择的站点只能在父节点的范围内
     * 如果是一级库，则可以自行选择工区，初始化后工区下的站点
     * <p>
     * pId 如果为-1的话 是不需要传到后台调用此方法的
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/getStationInfo", method = RequestMethod.POST)
    public ResultVO getStationInfo(@RequestBody WarehouseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getParentWarehouseId()))
            {
                return ResultVOUtil.paramError("parentWarehouseId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                List<Map<String, Object>> data = warehouseService.getStationInfo(findEntity);
                return ResultVOUtil.success(data);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加页面初始化数据出现异常");
        }
    }

    /**
     * 根据Id获取仓库信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/getWarehouseInfo", method = RequestMethod.POST)
    public ResultVO getWarehouseInfo(@RequestBody WarehouseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            else
            {
                Map<String, Object> warehouseEntity = warehouseService.getWarehouseInfo(findEntity);
                return ResultVOUtil.success(warehouseEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据Id获取仓库信息出现异常");
        }
    }

    @Autowired
    private WarehouseDao warehouseDao;

    /**
     * 添加仓库信息
     *
     * @param warehouse the findEntity
     * @return the json object
     */
    //@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/addWarehouseInfo", method = RequestMethod.POST)
    public ResultVO addWarehouseInfo(@RequestBody WarehouseVO warehouse)
    {
        try
        {
            warehouse.validateUserKey();
            if (ObjectUtils.isEmpty(warehouse.getWarehouseName()))
            {
                return ResultVOUtil.paramError("warehouseName");
            }
            else if (ObjectUtils.isEmpty(warehouse.getParentWarehouseId()))
            {
                return ResultVOUtil.paramError("parentWarehouseId");
            }
            else if (ObjectUtils.isEmpty(warehouse.getWorkSectionId()))
            {
                return ResultVOUtil.paramError("workSectionId");
            }
            else if (ObjectUtils.isEmpty(warehouse.getSelectedStationIdList()))
            {
                return ResultVOUtil.paramError("selectedStationIdList");
            }
            else if (ObjectUtils.isEmpty(warehouse.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                warehouse.setWarehouseId(0L);
                Integer count = warehouseDao.getSameWarehouseNameCount(warehouse);
                if (count > 0)
                {
                    return ResultVOUtil.error(0, "仓库名称重复，请修改！");
                }
                else
                {
                    warehouseService.addWarehouseInfo(warehouse);
                    return ResultVOUtil.success();
                }
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加仓库信息出现异常");
        }
    }

    /**
     * 更新仓库信息
     *
     * @param warehouse the warehouse
     * @return the json object
     */
    //@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/updateWarehouseInfo", method = RequestMethod.POST)
    public ResultVO updateWarehouseInfo(@RequestBody WarehouseVO warehouse)
    {
        try
        {
            warehouse.validateUserKey();
            if (ObjectUtils.isEmpty(warehouse.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(warehouse.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            else if (ObjectUtils.isEmpty(warehouse.getWarehouseName()))
            {
                return ResultVOUtil.paramError("warehouseName");
            }
            else if (ObjectUtils.isEmpty(warehouse.getParentWarehouseId()))
            {
                return ResultVOUtil.paramError("parentWarehouseId");
            }
            else if (ObjectUtils.isEmpty(warehouse.getSelectedStationIdList()))
            {
                return ResultVOUtil.paramError("selectedStationIdList");
            }
            else if (ObjectUtils.isEmpty(warehouse.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                Integer count = warehouseDao.getSameWarehouseNameCount(warehouse);
                if (count > 0)
                {
                    return ResultVOUtil.error(0, "仓库名称重复，请修改！");
                }
                else
                {
                    warehouseService.updateWarehouseInfo(warehouse);
                    return ResultVOUtil.success();
                }
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新仓库信息出现异常");
        }
    }

    /**
     * 删除仓库信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("ckgl")
    @ResponseBody
    @RequestMapping(value = "/deleteWarehouseInfo", method = RequestMethod.POST)
    public ResultVO deleteWarehouseInfo(@RequestBody WarehouseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            else
            {
                warehouseService.deleteWarehouseInfo(findEntity.getWarehouseId());
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除仓库信息出现异常");
        }
    }


}