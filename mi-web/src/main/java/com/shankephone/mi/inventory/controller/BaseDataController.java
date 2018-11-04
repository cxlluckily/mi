package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.BaseDataFindEntity;
import com.shankephone.mi.inventory.service.BaseDataService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 基础数据Controller
 *
 * @author 司徒彬
 * @date 2018 /7/11 14:49
 */
@Controller
@RequestMapping("/baseData")
public class BaseDataController
{
    @Autowired
    private BaseDataService baseDataService;

    /**
     * 查询备件信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getSparePartList", method = RequestMethod.POST)
    public ResultVO getSparePartList(@RequestBody BaseDataFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                List<Map<String, Object>> data = baseDataService.getSparePartList(findEntity);
                return ResultVOUtil.success(data);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "查询备件信息出现异常");
        }
    }

    /**
     * 获取库存中商品信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getSparePartInWarehouse", method = RequestMethod.POST)
    public ResultVO getSparePartInWarehouse(@RequestBody BaseDataFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            else
            {
                List<Map<String, Object>> data = baseDataService.getSparePartInWarehouse(findEntity);
                return ResultVOUtil.success(data);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取库存中商品信息出现异常");
        }
    }
}