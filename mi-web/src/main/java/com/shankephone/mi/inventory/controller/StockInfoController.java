package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.GetPagerDetailInfoVO;
import com.shankephone.mi.inventory.formbean.StockInfoFindEntity;
import com.shankephone.mi.inventory.service.StockInfoService;
import com.shankephone.mi.inventory.vo.StockInfoVO;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * 库存管理Controller
 *
 * @author 司徒彬
 * @date 2018/7/26 20:03
 */
@Controller
@RequestMapping("/stockInfo")
public class StockInfoController
{
    @Autowired
    private StockInfoService stockInfoService;

    /**
     * 获取库存管理汇总分页数据
     * <p>
     * 查询条件：仓库（下拉）、备件名称（文本）、状态（好件、坏件 下拉） 、是否唯一标识（下拉)
     *
     * @param findEntity the stockInfoVO
     * @return the json object
     */
    // //@RequiresPermissions("kcgl")
    @ResponseBody
    @RequestMapping(value = "/getPagerInfo", method = RequestMethod.POST)
    public ResultVO getPagerInfo(@RequestBody StockInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Pager pager = stockInfoService.getPagerInfo(findEntity);
            return ResultVOUtil.success(pager);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取库存管理汇总分页数据出现异常");
        }
    }

    /**
     * 获取库存管理详细信息
     *
     * @param findEntity the stockInfoVO
     * @return the json object
     */
    ////@RequiresPermissions("kcgl")
    @ResponseBody
    @RequestMapping(value = "/getPagerDetailInfo", method = RequestMethod.POST)
    public ResultVO getPagerDetailInfo(@RequestBody StockInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Pager pager = stockInfoService.getPagerDetailInfo(findEntity);
            return ResultVOUtil.success(pager);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取库存管理详细信息出现异常");
        }
    }

    /**
     * 更新库存信息
     *
     * @param stockInfoVO the stockInfoVO
     * @return the json object
     */
    //@RequiresPermissions("kcgl")
    @ResponseBody
    @RequestMapping(value = "/updateStockInfo", method = RequestMethod.POST)
    public ResultVO updateStockInfo(@RequestBody StockInfoVO stockInfoVO)
    {
        try
        {
            stockInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(stockInfoVO.getStockEntities()))
            {
                return ResultVOUtil.paramError("stockEntities");
            }
            else if (ObjectUtils.isEmpty(stockInfoVO.getStockId()))
            {
                return ResultVOUtil.paramError("stockId");
            }
            else if (ObjectUtils.isEmpty(stockInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                stockInfoService.updateStockInfo(stockInfoVO);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新库存信息出现异常");
        }
    }

    /**
     * 获取我的背包详细信息
     *
     * @param findEntity the stockInfoVO
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/GetMyPartPagerDetailInfo", method = RequestMethod.POST)
    public ResultVO GetMyPartPagerDetailInfo(@RequestBody StockInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            GetPagerDetailInfoVO getPagerDetailInfoVO = stockInfoService.getPagermyPartDetailInfo(findEntity);

            return ResultVOUtil.success(getPagerDetailInfoVO);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取库存管理汇总分页数据出现异常");
        }
    }
    /**
     * 获取库存备件详细信息
     *
     * @param findEntity the stockInfoVO
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getPagerstockPartDetailInfo", method = RequestMethod.POST)
    public ResultVO getPagerstockPartDetailInfo(@RequestBody StockInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            GetPagerDetailInfoVO getPagerDetailInfoVO = stockInfoService.getPagerstockPartDetailInfo(findEntity);

            return ResultVOUtil.success(getPagerDetailInfoVO);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取库存管理汇总分页数据出现异常");
        }
    }
    /***
     * "导入库存列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importStockList", method = RequestMethod.POST)
    public ResultVO importStockList(@RequestParam(value = "importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
    {
        try
        {
            if (importfileUrl.getFileItem() != null && importfileUrl.getFileItem().getSize() > 0)
            {
                String savePath = FilePath.getImportExcelPath();
                String name = importfileUrl.getFileItem().getName();
                //检查文件后缀格式
                String fileType = FileUtil.getFileType(name);
                String uuid = DataSwitch.getUUID();
                String saveFileName = uuid + fileType;
                savePath = savePath + saveFileName;
                FileUtil.createDirectory(savePath);
                FileUtil.copyInputStreamToFile(importfileUrl.getInputStream(), new File(savePath));
                List<String[]> list = ExcelUtil.readExcel(savePath, 1, 0);
                StringBuilder messagesb = new StringBuilder();
                messagesb.append(list.get(0)[0].trim());
                messagesb.append(list.get(0)[1].trim());
                messagesb.append(list.get(0)[2].trim());
                messagesb.append(list.get(0)[3].trim());
                messagesb.append(list.get(0)[4].trim());
                messagesb.append(list.get(0)[5].trim());
                messagesb.append(list.get(0)[6].trim());
                if (!"仓库名称备件名称备件数量库存类型设备状态房间号货架编号".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1, "库存导入模板出现错误");

                }
                list.remove(0);
                if (list == null || list.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                StockInfoFindEntity findEntity = DataSwitch.convertRequestToEntity(StockInfoFindEntity.class, request);
                findEntity.validateUserKey();
                return stockInfoService.importSotckList(findEntity, list);

            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            if (ex.getMessage().equals(""))
            {

            }
            return ResultVOUtil.error(ex, "导入库存列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出库存信息
     * @return
     */
    @RequestMapping(value = "/stockExport")
    @ResponseBody
    public ResultVO stationExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            StockInfoFindEntity findEntity = DataSwitch.convertRequestToEntity(StockInfoFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = null;// stockInfoService.getAllStationListMap(findEntity);
            String path = ExcelUtil.exportExcel("stockListExport", listmap);
            return FileDownLoad.download(response, path, "库存导出.xls");

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
}