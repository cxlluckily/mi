package com.shankephone.mi.spacepart.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;
import com.shankephone.mi.spacepart.service.OperationsEquipmentService;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
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
 * 营运设备Controller
 *
 * @author 司徒彬
 * @date 2018/8/20 15:19
 */
@Controller
@RequestMapping("/operationsEquipment")
public class OperationsEquipmentController
{
    @Autowired
    private OperationsEquipmentService operationsEquipmentService;

    /**
     * 根据线路和工区获取站点信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getStationList", method = RequestMethod.POST)
    public ResultVO getStationList(@RequestBody OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getLineId()))
            {
                return ResultVOUtil.paramError("lineId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getWorkSectionId()))
            {
                return ResultVOUtil.paramError("workSectionId");
            }
            else
            {
                List<Map<String, Object>> stationList = operationsEquipmentService.getStationList(findEntity);
                return ResultVOUtil.success(stationList);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据线路和工区获取站点信息出现异常");
        }
    }

    /**
     * 获取线上设备分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getPagerInfo", method = RequestMethod.POST)
    public ResultVO getPagerInfo(@RequestBody OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            else
            {
                Pager pager = operationsEquipmentService.getPagerInfo(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取线上设备分页数据出现异常");
        }
    }

    /**
     * 添加营运设备
     *
     * @param equipmentEntity the equipmentEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/addEquipment", method = RequestMethod.POST)
    public ResultVO addEquipment(@RequestBody OperationsEquipmentEntity equipmentEntity)
    {
        try
        {
            equipmentEntity.validateUserKey();
            if (ObjectUtils.isEmpty(equipmentEntity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            else if (ObjectUtils.isEmpty(equipmentEntity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            else
            {
                return operationsEquipmentService.addEquipment(equipmentEntity);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加营运设备出现异常");
        }
    }

    /**
     * 更新营运设备
     *
     * @param equipmentEntity the equipmentEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/updateEquipment", method = RequestMethod.POST)
    public ResultVO updateEquipment(@RequestBody OperationsEquipmentEntity equipmentEntity)
    {
        try
        {
            equipmentEntity.validateUserKey();
            if (ObjectUtils.isEmpty(equipmentEntity.getEquipmentId()))
            {
                return ResultVOUtil.paramError("equipmentId");
            }
            else if (ObjectUtils.isEmpty(equipmentEntity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            else if (ObjectUtils.isEmpty(equipmentEntity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            else
            {
                return operationsEquipmentService.updateEquipment(equipmentEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新营运设备出现异常");
        }
    }

    /**
     * 删除运营设备
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/deleteEquipment", method = RequestMethod.POST)
    public ResultVO deleteEquipment(@RequestBody OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getEquipmentId()))
            {
                return ResultVOUtil.paramError("equipmentId");
            }
            else
            {
                operationsEquipmentService.deleteEquipment(findEntity);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除运营设备出现异常");
        }
    }

    /**
     * 根据Id获取营运设备信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getEquipment", method = RequestMethod.POST)
    public ResultVO getEquipment(@RequestBody OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getEquipmentId()))
            {
                return ResultVOUtil.paramError("equipmentId");
            }
            else
            {
                Map<String, Object> data = operationsEquipmentService.getEquipment(findEntity);
                return ResultVOUtil.success(data);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据Id获取营运设备信息出现异常");
        }
    }

    /**
     * 根据qrCode获取营运设备信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getEquipmentEntity", method = RequestMethod.POST)
    public ResultVO getEquipmentEntity(@RequestBody OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Map<String, Object> data = operationsEquipmentService.getEquipmentEntity(findEntity);
            return ResultVOUtil.success(data);


        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据二维码获取营运设备信息出现异常");
        }
    }

    /***
     * "导入备件列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importEquipmentList", method = RequestMethod.POST)
    public ResultVO importEquipmentList(@RequestParam(value = "importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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
                messagesb.append(list.get(0)[3].trim());
                messagesb.append(list.get(0)[4].trim());
                messagesb.append(list.get(0)[5].trim());
                messagesb.append(list.get(0)[6].trim());

                if (!"线路编码站点编码备件名称设备名称设备编号所在位置".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1, "线上设备导入模板出现错误");
                }
                list.remove(0);
                if (list == null || list.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                OperationsEquipmentFindEntity findEntity = DataSwitch.convertRequestToEntity(OperationsEquipmentFindEntity.class, request);
                findEntity.validateUserKey();
                return operationsEquipmentService.importEquipmentList(findEntity, list);

            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入在线设备列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出在线设备信息
     * @return
     */
    @RequestMapping(value = "/equipmentExport")
    @ResponseBody
    public ResultVO equipmentExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            OperationsEquipmentFindEntity findEntity = DataSwitch.convertRequestToEntity(OperationsEquipmentFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = operationsEquipmentService.getAllEquipmentListMap(findEntity);
            String path = ExcelUtil.exportExcel("operationsListExport", listmap);
            return FileDownLoad.download(response, path, "在线设备导出.xls");
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