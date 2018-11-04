package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.StockInFindEntity;
import com.shankephone.mi.inventory.service.StockInService;
import com.shankephone.mi.inventory.vo.StockInVO;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 入库单Controller
 *
 * @author 司徒彬
 * @date 2018/7/17 10:14
 */
@Controller
@RequestMapping("/stockIn")
public class StockInController
{
    @Autowired
    private StockInService stockInService;

    /**
     * 获得入库申请分页数据
     *
     * @param findEntity the stockInVO
     * @return the json object
     */
    //////@RequiresPermissions("rqgl")
    @ResponseBody
    @RequestMapping(value = "/getPagerInfo", method = RequestMethod.POST)
    public ResultVO getPagerInfo(@RequestBody StockInFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getInStockStatus()))
            {
                return ResultVOUtil.paramError("inStockStatus");
            }
            else
            {
                Pager pager = stockInService.getPagerInfo(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获得入库申请分页数据出现异常");
        }
    }

    /**
     * 根据Id获取入库申请信息
     *
     * @param findEntity the stockInVO
     * @return the json object
     */
    ////@RequiresPermissions("rqgl")
    @ResponseBody
    @RequestMapping(value = "/getStockInInfoById", method = RequestMethod.POST)
    public ResultVO getStockInInfoById(@RequestBody StockInFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getInStockId()))
            {
                return ResultVOUtil.paramError("inStockId");
            }
            else
            {
                Map<String, Object> result = stockInService.getStockInInfoById(findEntity.getInStockId());
                return ResultVOUtil.success(result);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据Id获取入库申请信息出现异常");
        }
    }

    /**
     * 新品入库添加
     *
     * @param stockInVO the stockInVO
     * @return the json object
     */
    ////@RequiresPermissions("rqgl")
    @ResponseBody
    @RequestMapping(value = "/addNewInApply", method = RequestMethod.POST)
    public ResultVO addNewInApply(@RequestBody StockInVO stockInVO)
    {
        try
        {
            stockInVO.validateUserKey();
            if (ObjectUtils.isEmpty(stockInVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                stockInService.addNewInApply(stockInVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新品入库添加出现异常");
        }
    }

    /**
     * 确认入库
     *
     * @param stockInVO the stockInVO
     * @return the json object
     */
    ////@RequiresPermissions("rqgl")
    @ResponseBody
    @RequestMapping(value = "/commitStockIn", method = RequestMethod.POST)
    public ResultVO commitStockIn(@RequestBody StockInVO stockInVO)
    {
        try
        {
            stockInVO.validateUserKey();
            if (ObjectUtils.isEmpty(stockInVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                stockInService.commitStockIn(stockInVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            if (ex.getMessage().indexOf("二维码") > 0||ex.getMessage().indexOf("入库") > 0)
            {
                return ResultVOUtil.error(ex, ex.getMessage());
            }
            else
            {
                return ResultVOUtil.error(ex, "新品入库添加出现异常");
            }
        }
    }
}