package com.shankephone.mi.supplier.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.StockSupplierEntity;
import com.shankephone.mi.supplier.formbean.SupplierFindEntity;
import com.shankephone.mi.supplier.service.SupplierService;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 供应商
 *
 * @author fengql
 * @date 2018年6月22日 上午10:32:32
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController
{

    @Resource
    private SupplierService supplierService;
    /***
     * 获取供应商列表信息
     * @return ResultVO
     */
    //@RequiresPermissions("gysgl")
    @RequestMapping("/list")
    @ResponseBody
    public ResultVO<StockSupplierEntity> loadData(@RequestBody SupplierFindEntity entity)
    {
        entity.validateUserKey();
        return ResultVOUtil.success(supplierService.getSupplierList(entity));
    }
    /***
     * 新增供应商信息
     * @return ResultVO
     */
    //@RequiresPermissions("gysgl")
    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(@RequestBody StockSupplierEntity entity)
    {
        entity.validateUserKey();
        supplierService.insertOne(entity);
        return ResultVOUtil.success();
    }
    /***
     * 删除供应商信息
     * @return ResultVO
     */
    //@RequiresPermissions("gysgl")
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVO delete(@RequestBody SupplierFindEntity entity)
    {
        entity.validateUserKey();
        supplierService.delete(entity);
        return ResultVOUtil.success();
    }
    /***
     * 修改供应商信息
     * @return ResultVO
     */
    //@RequiresPermissions("gysgl")
    @RequestMapping("/update")
    @ResponseBody
    public ResultVO update(@RequestBody StockSupplierEntity entity)
    {
        entity.validateUserKey();
        supplierService.updateOne(entity);
        return ResultVOUtil.success();
    }

}
