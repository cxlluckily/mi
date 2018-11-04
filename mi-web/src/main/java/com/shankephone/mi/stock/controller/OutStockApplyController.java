package com.shankephone.mi.stock.controller;

import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.stock.formbean.*;
import com.shankephone.mi.stock.service.OutStockApplyService;
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
 * @author 赵亮
 * @date 2018-07-11 10:42
 */
@Controller
@RequestMapping("/outStockApply")
public class OutStockApplyController
{
    @Autowired
    private OutStockApplyService outStockApplyService;

    /**
     * 根据查询条件返回出库申请单数据
     *
     * @author：赵亮
     * @date：2018-07-11 10:43
     */

    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @RequestMapping(value = "/getOutStockApplyInfo")
    @ResponseBody
    public ResultVO<Map<String, String>> getOutStockApplyInfo(@RequestBody OutStockApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getLimit()))
            {
                return ResultVOUtil.paramError("limit");
            }
            if (ObjectUtils.isEmpty(findEntity.getStart()))
            {
                return ResultVOUtil.paramError("start");
            }
            if (ObjectUtils.isNull(findEntity.getApplyUser()))
            {
                return ResultVOUtil.paramError("applyUser");
            }
            if (ObjectUtils.isEmpty(findEntity.getOutApplyStatus()))
            {
                return ResultVOUtil.paramError("outApplyStatus");
            }
            if (ObjectUtils.isNull(findEntity.getOutStockApplyNO()))
            {
                return ResultVOUtil.paramError("outStockApplyNO");
            }
            if (ObjectUtils.isEmpty(findEntity.getOutWarehouseId()))
            {
                return ResultVOUtil.paramError("outWarehouseId");
            }
            if (ObjectUtils.isEmpty(findEntity.getOutOrderType()))
            {
                return ResultVOUtil.paramError("outOrderType");
            }
            if (ObjectUtils.isEmpty(findEntity.getBeginTime()))
            {
                return ResultVOUtil.paramError("beginTime");
            }
            if (ObjectUtils.isEmpty(findEntity.getEndTime()))
            {
                return ResultVOUtil.paramError("endTime");
            }
            return ResultVOUtil.success(outStockApplyService.getOutStockApplyInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 根据出库申请单ID返回待出库详单数据列表
     *
     * @param entity the StockOutStockApplyEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-11
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getApplyDetailInfoByoutStockInfo", method = RequestMethod.POST)
    public ResultVO getApplyDetailInfoByoutStockInfo(@RequestBody StockOutStockApplyEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getOutStockApplyId()))
            {
                return ResultVOUtil.paramError("outStockApplyId");
            }
            return ResultVOUtil.success(outStockApplyService.getApplyDetailInfoByoutStockApplyId(entity.getOutStockApplyId()));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据出库申请单ID返回待出库详单数据列表出现异常");
        }
    }

    /**
     * 根据备件ID和仓库ID返回库存中可以发放的数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-11
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getCanSendGoodsInfo", method = RequestMethod.POST)
    public ResultVO getCanSendGoodsInfo(@RequestBody OutStockApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getWarehouseId()))
            {
                return ResultVOUtil.paramError("warehouseId");
            }
            if (ObjectUtils.isEmpty(findEntity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            return ResultVOUtil.success(outStockApplyService.getCanSendGoodsInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据备件ID返回库存中可以发放的数据出现异常");
        }
    }

    /**
     * 出库（领用出库和调拨出库和返还）
     *
     * @param busiEntity the UseAndTransfeOutStockBusiEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-11
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/useAndTransfeOutStockAndReturn", method = RequestMethod.POST)
    public ResultVO useAndTransfeOutStockAndReturn(@RequestBody UseAndTransfeOutStockBusiEntity busiEntity)
    {
        try
        {
            busiEntity.validateUserKey();
            if (ObjectUtils.isEmpty(busiEntity.getApplyEntity().getOutStockApplyId()))
            {
                return ResultVOUtil.paramError("outStockApplyId");
            }
            if (ObjectUtils.isEmpty(busiEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            return outStockApplyService.useAndTransfeOutStockAndReturn(busiEntity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            if (ex.getMessage().equals(ResultEnum.INVENTORY_SHORTAGE.getMessage()))
            {
                return ResultVOUtil.error(ResultEnum.INVENTORY_SHORTAGE.getCode(), ResultEnum.INVENTORY_SHORTAGE.getMessage());
            }
            else
            {
                return ResultVOUtil.error(ex, "返还出库新增入库信息出现异常");
            }
        }
    }

    /**
     * 借用出库
     *
     * @param busiEntity the BorrowOutStockBusiEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-12
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/insertBorrowOutStock", method = RequestMethod.POST)
    public ResultVO insertBorrowOutStock(@RequestBody BorrowOutStockBusiEntity busiEntity)
    {
        try
        {
            busiEntity.validateUserKey();
            if (ObjectUtils.isEmpty(busiEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }

            return ResultVOUtil.success(outStockApplyService.insertBorrowOutStock(busiEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            if (ex.getMessage().equals(ResultEnum.INVENTORY_SHORTAGE.getMessage()))
            {
                return ResultVOUtil.error(ResultEnum.INVENTORY_SHORTAGE.getCode(), ResultEnum.INVENTORY_SHORTAGE.getMessage());
            }
            else
            {
                return ResultVOUtil.error(ex, "返还出库新增入库信息出现异常");
            }
        }
    }

    //    /**
    //     * 借用出库（暂时未用）
    //     * @author：赵亮
    //     * @date：2018-07-12
    //     * @param entity  the StockOutStockApplyEntity
    //     * @return the ResultVO
    //     */
    //    //@RequiresPermissions("ckgl")
    //    @ResponseBody
    //    @RequestMapping(value = "/returnOutStock", method = RequestMethod.POST)
    //    public ResultVO returnOutStock(@RequestBody StockOutStockApplyEntity entity)
    //    {
    //        try
    //        {
    //            if(ObjectUtils.isEmpty(entity.getOutStockApplyId())){
    //                return ResultVOUtil.paramError("outStockApplyId");
    //            }
    //            if(ObjectUtils.isEmpty(entity.getOutOrderType())){
    //                return ResultVOUtil.paramError("outOrderType");
    //            }
    //            return ResultVOUtil.success(outStockApplyService.returnOutStock(entity));
    //        }
    //        catch (Exception ex)
    //        {
    //            return ResultVOUtil.error(ex,"返还出库出现异常");
    //        }
    //    }

    /**
     * 获取出库设备里面待绑定二维码的数据
     *
     * @param findEntity the BindQrCodeFindEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-12
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getBindqrCodeStockInfo", method = RequestMethod.POST)
    public ResultVO getBindqrCodeStockInfo(@RequestBody BindQrCodeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getOutStockApplyId()))
            {
                return ResultVOUtil.paramError("outStockApplyId");
            }
            return ResultVOUtil.success(outStockApplyService.getBindqrCodeStockInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取出库设备里面待绑定二维码的数据出现异常");
        }
    }

    /**
     * 更新出库的设备的二维码，需要每一个里面传
     *
     * @param formBean the UpdateStockQrCodeBusiEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-12
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/updateStockQrCode", method = RequestMethod.POST)
    public ResultVO updateStockQrCode(@RequestBody UpdateStockQrCodeBusiEntity formBean)
    {
        try
        {
            formBean.validateUserKey();
            if (ObjectUtils.isEmpty(formBean.getFormBeans()))
            {
                return ResultVOUtil.paramError("List<StockStockEntity> stockEntities");
            }
            return outStockApplyService.updateStockQrCode(formBean.getFormBeans());
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新出库的设备的二维码出现异常");
        }
    }

    /**
     * 更新出库的设备的二维码
     * @param formBean the UpdateStockQrCodeBusiEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日14:38:39
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/updateStockOutQrCode", method = RequestMethod.POST)
    public ResultVO updateStockQrCode(@RequestBody UpdateStockQrCodeFormBean formBean)
    {
        try
        {
            formBean.validateUserKey();

            return outStockApplyService.updateStockOutQrCode(formBean);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新出库的设备的二维码出现异常");
        }
    }

    /**
     * 根据qrcode获取库存备件信息
     * @param formBean the UpdateStockQrCodeBusiEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018年9月26日14:38:39
     */
    ////@RequiresPermissions(value = {"ckgl","ck" }, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getStockSparePartByqrCode", method = RequestMethod.POST)
    public ResultVO getStockSparePartByqrCode(@RequestBody UpdateStockQrCodeFormBean formBean)
    {
        try
        {
            formBean.validateUserKey();

            return outStockApplyService.getStockSparePartByqrCode(formBean);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, " 根据二维码获取库存备件信息出现异常");
        }
    }
}
