package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.ShelvesFindEntity;
import com.shankephone.mi.inventory.service.ShelvesService;
import com.shankephone.mi.inventory.vo.ShelvesVO;
import com.shankephone.mi.model.StockGoodsShelvesEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 货架Controller
 *
 * @author 司徒彬
 * @date 2018/7/5 17:10
 */
@Controller
@RequestMapping("/shelves")
public class ShelvesController
{
    @Autowired
    private ShelvesService shelvesService;

    /**
     * 获取分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/getPagerList", method = RequestMethod.POST)
    public ResultVO getPagerList(@RequestBody ShelvesFindEntity findEntity)
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
                Pager pager = new Pager(0, new ArrayList());
                return ResultVOUtil.success(pager);
            }
            else if (ObjectUtils.isEmpty(findEntity.getContainerType()))
            {
                return ResultVOUtil.paramError("containerType");
            }
            else if (ObjectUtils.isEmpty(findEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                Pager pager = shelvesService.getPagerList(findEntity);
                return ResultVOUtil.success(pager);
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
     * 根据Id获取货架
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/getShelvesInfo", method = RequestMethod.POST)
    public ResultVO getShelvesInfo(@RequestBody ShelvesFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getGoodsShelvesId()))
            {
                return ResultVOUtil.paramError("goodsShelvesId");
            }
            else
            {
                Map<String, Object> stockGoodsShelvesEntity = shelvesService.getShelvesInfo(findEntity);
                return ResultVOUtil.success(stockGoodsShelvesEntity);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据Id获取货架出现异常");
        }
    }

    /**
     * 添加货架信息
     *
     * @param shelvesVO the shelvesVO
     * @return the json object
     */
    //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/addShelvesInfo", method = RequestMethod.POST)
    public ResultVO addShelvesInfo(@RequestBody ShelvesVO shelvesVO)
    {
        try
        {
            shelvesVO.validateUserKey();
            if (ObjectUtils.isEmpty(shelvesVO.getWarehouseId()))
            {
                return ResultVOUtil.error(-1, "请选择所属仓库！");
                // return ResultVOUtil.paramError("warehouseId");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getHouseNo()))
            {
                return ResultVOUtil.paramError("houseNo");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getContainerType()))
            {
                return ResultVOUtil.paramError("containerType");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getCellNumber()))
            {
                return ResultVOUtil.paramError("cellNumber");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getLayerNumber()))
            {
                return ResultVOUtil.paramError("layerNumber");
            }
            else if (ObjectUtils.isEmpty(shelvesVO.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                shelvesService.addShelvesInfo(shelvesVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加货架信息出现异常");
        }
    }

    /**
     * 修改货架信息
     *
     * @param shelvesEntity the shelvesEntity
     * @return the json object
     */
    //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/updateShelvesInfo", method = RequestMethod.POST)
    public ResultVO updateShelvesInfo(@RequestBody StockGoodsShelvesEntity shelvesEntity)
    {
        try
        {
            shelvesEntity.validateUserKey();
            if (ObjectUtils.isEmpty(shelvesEntity.getGoodsShelvesId()))
            {
                return ResultVOUtil.paramError("goodsShelvesId");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getWarehouseId()))
            {
                // return ResultVOUtil.paramError("warehouseId");
                return ResultVOUtil.error(-1, "请选择所属仓库！");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getHouseNo()))
            {
                return ResultVOUtil.paramError("houseNo");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getContainerType()))
            {
                return ResultVOUtil.paramError("containerType");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getShelfNumber()))
            {
                return ResultVOUtil.paramError("shelfNumber");
            }
            else if (ObjectUtils.isEmpty(shelvesEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            else
            {
                shelvesService.updateShelvesInfo(shelvesEntity);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "修改货架信息出现异常");
        }
    }

    /**
     * 删除货架信息
     *
     * @param shelvesEntity the shelvesEntity
     * @return the json object
     */
    //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/deleteShelvesInfo", method = RequestMethod.POST)
    public ResultVO deleteShelvesInfo(@RequestBody StockGoodsShelvesEntity shelvesEntity)
    {
        try
        {
            shelvesEntity.validateUserKey();
            if (ObjectUtils.isEmpty(shelvesEntity.getGoodsShelvesId()))
            {
                return ResultVOUtil.paramError("goodsShelvesId");
            }
            else
            {
                shelvesService.deleteShelvesInfo(shelvesEntity.getGoodsShelvesId());
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除货架信息出现异常");
        }
    }

    /**
     * 为房间和货架提供模糊查询数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getShelvesInfoBySearchContent", method = RequestMethod.POST)
    public ResultVO getShelvesInfoBySearchContent(@RequestBody ShelvesFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            List<Map<String, Object>> shelvesInfo = shelvesService.getShelvesInfoBySearchContent(findEntity);

            return ResultVOUtil.success(shelvesInfo);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "为房间和货架提供模糊查询数据出现异常");
        }
    }


    /**
     * 获取仓库下房间号信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
   // //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/getShelvesHouseList", method = RequestMethod.POST)
    public ResultVO getShelvesHouseList(@RequestBody ShelvesFindEntity findEntity)
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
                 return ResultVOUtil.paramError("仓库不能为空");
            }
            return ResultVOUtil.success(shelvesService.getShelvesHouseList(findEntity));

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
     * 根据仓库ID和房间号获取货架信息（按照货架分组）
     *
     * @param findEntity the findEntity
     * @return the json object
     */
   // //@RequiresPermissions("hjgl")
    @ResponseBody
    @RequestMapping(value = "/getShelvesMap", method = RequestMethod.POST)
    public ResultVO getShelvesMap(@RequestBody ShelvesFindEntity findEntity)
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
                return ResultVOUtil.paramError("仓库不能为空");
            }
            else if (ObjectUtils.isEmpty(findEntity.getHouseNo()))
            {
                return ResultVOUtil.paramError("房间号不能为空");
            }
            return ResultVOUtil.success(shelvesService.getShelvesMap(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据仓库ID和房间号获取货架数据出现异常");
        }
    }
}