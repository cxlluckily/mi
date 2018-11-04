package com.shankephone.mi.stock.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.stock.formbean.QRCodeFormBean;
import com.shankephone.mi.stock.service.QRCodeService;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-24 10:09
 */
@Controller
@RequestMapping("/code")
public class QRCodeController
{
    @Autowired
    private QRCodeService qrCodeService;

    /**
     * 批量添加二维码
     *
     * @param entity the QRCodeFormBean
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-24
     */
    //@RequiresPermissions("ewmgl")
    @ResponseBody
    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public ResultVO batchInsert(@RequestBody QRCodeFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(qrCodeService.batchInsert(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量添加二维码出现异常");
        }
    }

    /**
     * 查询二维码
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-24
     */
    //@RequiresPermissions("ewmgl")
    @ResponseBody
    @RequestMapping(value = "/getQRCodeList", method = RequestMethod.POST)
    public ResultVO getQRCodeList(@RequestBody QRCodeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(qrCodeService.getQRCodeList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "查询二维码出现异常");
        }
    }

    /**
     * 批量删除
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-24
     */
    //@RequiresPermissions("ewmgl")
    @ResponseBody
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public ResultVO batchDelete(@RequestBody QRCodeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(qrCodeService.batchDelete(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "批量删除出现异常");
        }
    }

    /***
     * 根据查询条件导出二维码信息
     * @return
     */
    @RequestMapping(value = "/qrCodeExport")
    @ResponseBody
    public ResultVO qrCodeExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            QRCodeFindEntity findEntity = DataSwitch.convertRequestToEntity(QRCodeFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = qrCodeService.getAllqrCodeListMap(findEntity);
            String path = ExcelUtil.exportExcel("qrCodeListExport", listmap);
            return FileDownLoad.download(response, path, "二维码导出.xls");
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


    /***
     * 根据qrCode导出二维码信息
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getQrCodeByQrCode", method = RequestMethod.POST)
    public ResultVO getQrCodeByQrCode(@RequestBody QRCodeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return qrCodeService.getQrCodeByQrCodeEntity(findEntity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据qrCode导出二维码信息出现异常");
        }
    }
}
