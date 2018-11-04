package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.StockOperationFindEntity;
import com.shankephone.mi.inventory.service.ApplyInfoService;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
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
 * 库存操作申请单据Controller
 * <p>
 * 领用 调拨 返还
 *
 * @author 司徒彬
 * @date 2018/7/12 09:56
 */
@Controller
@RequestMapping("/applyInfo")
public class ApplyInfoController
{
    @Autowired
    private ApplyInfoService stockOperationService;

    /**
     * 根据用户获取自己 申请的分页数据
     * <p>
     * 按照applyType执行不同逻辑
     * <p>
     * 如果是调拨的话 需要查询
     *
     * @param findEntity the findEntity
     * @return the json object
     */
//    //@RequiresPermissions(value = {"sqgl", "sqly"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getPagerInfoByUser", method = RequestMethod.POST)
    public ResultVO getPagerInfoByUser(@RequestBody StockOperationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getBeginTime()))
            {
                return ResultVOUtil.paramError("beginTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getEndTime()))
            {
                return ResultVOUtil.paramError("endTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getApplyType()))
            {
                return ResultVOUtil.paramError("applyType");
            }
            else
            {
                Pager pager = stockOperationService.getPagerInfoByUser(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取申请的分页数据出现异常");
        }
    }

    /**
     * 根据仓库获取申请的分页数据 手机端获取（领用记录、调拨记录、返还记录）
     * <p>
     * 按照applyType执行不同逻辑
     * <p>
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getPagerInfoByWarehouse", method = RequestMethod.POST)
    public ResultVO getPagerInfoByWarehouse(@RequestBody StockOperationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getBeginTime()))
            {
                return ResultVOUtil.paramError("beginTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getEndTime()))
            {
                return ResultVOUtil.paramError("endTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getApplyType()))
            {
                return ResultVOUtil.paramError("applyType");
            }
            else
            {
                Pager pager = stockOperationService.getPagerInfoByWarehouse(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据仓库获取申请的分页数据出现异常");
        }
    }

    /**
     * 为审核页面获取申请的分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
//    //@RequiresPermissions("sqsh")
    @ResponseBody
    @RequestMapping(value = "/getPagerInfoForAudit", method = RequestMethod.POST)
    public ResultVO getPagerInfoForAudit(@RequestBody StockOperationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getBeginTime()))
            {
                return ResultVOUtil.paramError("beginTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getEndTime()))
            {
                return ResultVOUtil.paramError("endTime");
            }
            else if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getApplyType()))
            {
                return ResultVOUtil.paramError("applyType");
            }
            else
            {
                Pager pager = stockOperationService.getPagerInfoForAudit(findEntity);
                return ResultVOUtil.success(pager);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "为审核页面获取申请的分页数据出现异常");
        }
    }

    /**
     * 添加申请单
     *
     * @param applyInfoVO the applyInfoVO
     * @return the json object
     */
//    //@RequiresPermissions(value = {"sqgl", "sqly"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/addApplyInfo", method = RequestMethod.POST)
    public ResultVO addApplyInfo(@RequestBody ApplyInfoVO applyInfoVO)
    {
        try
        {
            applyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(applyInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getApplyType()))
            {
                return ResultVOUtil.paramError("applyType");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getDetailEntities()))
            {
                return ResultVOUtil.paramError("detailEntities");
            }
            else
            {
                stockOperationService.addApplyInfo(applyInfoVO);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加申请单出现异常");
        }
    }

    /**
     * 修改申请单
     *
     * @param applyInfoVO the applyInfoVO
     * @return the json object
     */
//    //@RequiresPermissions(value = {"sqgl", "sqly"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/updateApplyInfo", method = RequestMethod.POST)
    public ResultVO updateApplyInfo(@RequestBody ApplyInfoVO applyInfoVO)
    {
        try
        {
            applyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(applyInfoVO.getApplyId()))
            {
                return ResultVOUtil.paramError("applyId");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getApplyType()))
            {
                return ResultVOUtil.paramError("applyType");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getDetailEntities()))
            {
                return ResultVOUtil.paramError("detailEntities");
            }
            else
            {
                stockOperationService.updateApplyInfo(applyInfoVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "修改申请单出现异常");
        }
    }

    /**
     * 删除申请单
     *
     * @param applyInfoVO the applyInfoVO
     * @return the json object
     */
//    //@RequiresPermissions(value = {"sqgl", "sqly"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/deleteApplyInfo", method = RequestMethod.POST)
    public ResultVO deleteApplyInfo(@RequestBody ApplyInfoVO applyInfoVO)
    {
        try
        {
            applyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(applyInfoVO.getApplyId()))
            {
                return ResultVOUtil.paramError("applyId");
            }
            else
            {
                stockOperationService.deleteApplyInfo(applyInfoVO.getApplyId());
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除申请单出现异常");
        }
    }

    /**
     * 根据申请单Id获取申请单信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
//    //@RequiresPermissions(value = {"sqgl", "sqly", "sqsh"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping(value = "/getApplyInfoByApplyId", method = RequestMethod.POST)
    public ResultVO getApplyInfoByApplyId(@RequestBody StockOperationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getApplyId()))
            {
                return ResultVOUtil.paramError("applyId");
            }
            else
            {
                ApplyInfoVO applyInfoVO = stockOperationService.getApplyInfoByApplyId(findEntity.getApplyId());
                return ResultVOUtil.success(applyInfoVO);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据申请单Id获取申请单信息出现异常");
        }
    }

    /**
     * 申请单审核
     *
     * @param applyEntity the applyEntity
     * @return the json object
     */
//    //@RequiresPermissions("sqsh")
    @ResponseBody
    @RequestMapping(value = "/auditApplyInfo", method = RequestMethod.POST)
    public ResultVO auditApplyInfo(@RequestBody ApplyInfoVO applyEntity)
    {
        try
        {
            applyEntity.validateUserKey();
            if (ObjectUtils.isEmpty(applyEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(applyEntity.getApplyId()))
            {
                return ResultVOUtil.paramError("applyId");
            }
            else if (ObjectUtils.isEmpty(applyEntity.getApplyStatus()))
            {
                return ResultVOUtil.paramError("applyStatus");
            }
            else
            {
                return stockOperationService.auditApplyInfo(applyEntity);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "申请单审核出现异常");
        }
    }
}