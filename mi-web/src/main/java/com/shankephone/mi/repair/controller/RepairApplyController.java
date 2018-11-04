package com.shankephone.mi.repair.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.repair.formbean.GetDrviceInforByQrcodeFindEntity;
import com.shankephone.mi.repair.formbean.RepairApplyFindEntity;
import com.shankephone.mi.repair.service.RepairApplyService;
import com.shankephone.mi.repair.util.ErrorQrCodeException;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 维修申请Controller
 *
 * @author 司徒彬
 * @date 2018 /8/2 10:08
 */
@Controller
@RequestMapping("/repairApply")
public class RepairApplyController
{
    @Autowired
    private RepairApplyService repairApplyService;


    /**
     * 添加维修申请
     *
     * @param request  the request
     * @param response the response
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/addRepairApplyInfo", method = RequestMethod.POST)
    public ResultVO addRepairApplyInfo(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {

            String formData = request.getParameter("formData");
            RepairApplyInfoVO repairInfoVO = DataSwitch.convertJsonStringToEntity(formData, RepairApplyInfoVO.class);
            repairInfoVO.validateUserKey();
            List<CommonsMultipartFile> photoUrl = null;
            if (repairInfoVO.isHavePic())
            {
                photoUrl = FileUtil.getRequestFiles(request, "photoUrl");
            }
            repairApplyService.addRepairApplyInfo(photoUrl, repairInfoVO);
            response.setStatus(HttpStatus.OK.value());
            return ResultVOUtil.success();

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加维修申请出现异常");
        }
    }

    /**
     * 添加维修申请手机端
     *
     * @param request  the request
     * @param response the response
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/addRepairApplyInfoH5", method = RequestMethod.POST)
    public ResultVO addRepairApplyInfoH5(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            //RepairApplyInfoVO repairInfoVO = DataSwitch.convertRequestToEntity(RepairApplyInfoVO.class, request);
            String formData = request.getParameter("formData");
            RepairApplyInfoVO repairInfoVO = DataSwitch.convertJsonStringToEntity(formData, RepairApplyInfoVO.class);
            repairInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(repairInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                repairApplyService.addRepairApplyInfo(null, repairInfoVO);
                response.setStatus(HttpStatus.OK.value());
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加维修申请出现异常");
        }
    }


    /**
     * Add repair apply info phone result vo.
     *
     * @param repairInfoVO the repair info vo
     * @return the result vo
     */
    @ResponseBody
    @RequestMapping(value = "/addRepairApplyInfoPhone", method = RequestMethod.POST)
    public ResultVO addRepairApplyInfoPhone(@RequestBody RepairApplyInfoVO repairInfoVO)
    {
        try
        {
            repairInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(repairInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                repairApplyService.addRepairApplyInfo(null, repairInfoVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "添加维修申请出现异常");
        }
    }

    /**
     * 对手机端文件上传
     *
     * @param request  the request
     * @param response the response
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public ResultVO fileUpload(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            List<String> uploadedFiles = FileUtil.fileUpload(request);
            List<Map<String, Object>> data = uploadedFiles.stream().map(url -> {
                Map<String, Object> map = new HashMap<>();
                map.put("webUrl", FilePath.getRepairWebPath(url));
                map.put("saveValue", url);
                return map;
            }).collect(Collectors.toList());
            response.setStatus(HttpStatus.OK.value());
            return ResultVOUtil.success(data);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "对手机端文件上传出现异常");
        }
    }


    /**
     * 获取路线及站点数据
     * <p>
     * 必传字段
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getLineAndStationInfo", method = RequestMethod.POST)
    public ResultVO getLineAndStationInfo(@RequestBody RepairApplyFindEntity findEntity)
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
                List<Map<String, Object>> linesInfo = repairApplyService.getLineAndStationInfo(findEntity);
                return ResultVOUtil.success(linesInfo);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取路线及站点数据 出现异常");
        }
    }

    /**
     * 获取设备类型，只获取一级节点
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getEquipmentType", method = RequestMethod.POST)
    public ResultVO getEquipmentType(@RequestBody RepairApplyFindEntity findEntity)
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
                List<Map<String, Object>> equipmentTypes = repairApplyService.getEquipmentType(findEntity);
                return ResultVOUtil.success(equipmentTypes);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取设备类型，只获取一级节点出现异常");
        }
    }

    /**
     * 获取设备列表
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getEquipmentList", method = RequestMethod.POST)
    public ResultVO getEquipmentList(@RequestBody RepairApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            else if (ObjectUtils.isEmpty(findEntity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            else
            {
                List<Map<String, Object>> equipmentList = repairApplyService.getEquipmentList(findEntity);
                return ResultVOUtil.success(equipmentList);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取设备列表出现异常");
        }
    }

    /**
     * 根据设备获取故障现象
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getErrorPhenomenonByEquipmentId", method = RequestMethod.POST)
    public ResultVO getErrorPhenomenonByEquipmentId(@RequestBody RepairApplyFindEntity findEntity)
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
                List<Map<String, Object>> errorPhenomenons = repairApplyService.getErrorPhenomenonByEquipmentId(findEntity.getEquipmentId());
                return ResultVOUtil.success(errorPhenomenons);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据设备获取故障现象出现异常");
        }
    }

    /**
     * 获取抄送人信息列表
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getConcatPeopleList", method = RequestMethod.POST)
    public ResultVO getConcatPeopleList(@RequestBody RepairApplyFindEntity findEntity)
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
                List<Map<String, Object>> concatPeopleList = repairApplyService.getConcatPeopleList(findEntity);
                return ResultVOUtil.success(concatPeopleList);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取抄送人信息列表出现异常");
        }
    }

    /**
     * 设置联系人Id
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/setConcatPeople", method = RequestMethod.POST)
    public ResultVO setConcatPeople(@RequestBody RepairApplyInfoVO findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else
            {
                repairApplyService.setConcatPeople(findEntity);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "设置联系人Id出现异常");
        }
    }

    /**
     * 获取维修单分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getRepairApplyPageInfo", method = RequestMethod.POST)
    public ResultVO getRepairApplyPageInfo(@RequestBody RepairApplyFindEntity findEntity)
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
                Pager pager = repairApplyService.getRepairApplyPageInfo(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取维修单分页数据出现异常");
        }
    }

    /**
     * 获取申请单详情信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getApplyInfo", method = RequestMethod.POST)
    public ResultVO getApplyInfo(@RequestBody RepairApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Map<String, Object> map = repairApplyService.getApplyInfo(findEntity);
            return ResultVOUtil.success(map);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取申请单详情信息出现异常");
        }
    }

    /**
     * 获取维修人员
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getRepairerInfo", method = RequestMethod.POST)
    public ResultVO getRepairerInfo(@RequestBody RepairApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            else
            {
                List<Map<String, Object>> userList = repairApplyService.getRepairerInfo(findEntity);
                return ResultVOUtil.success(userList);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取维修人员出现异常");
        }
    }

    /**
     * 派单
     *
     * @param applyInfoVO the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/appointRepairInfo", method = RequestMethod.POST)
    public ResultVO appointRepairInfo(@RequestBody RepairApplyInfoVO applyInfoVO)
    {
        try
        {
            applyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(applyInfoVO.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getMaintenanceUser()))
            {
                return ResultVOUtil.paramError("maintenanceUser");
            }
            else if (ObjectUtils.isEmpty(applyInfoVO.getMaintenanceUserID()))
            {
                return ResultVOUtil.paramError("maintenanceUserID");
            }
            else
            {
                return repairApplyService.appointRepairInfo(applyInfoVO);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "派单出现异常");
        }
    }

    /**
     * 获取维修人员维修分页数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getRepairInfoPager", method = RequestMethod.POST)
    public ResultVO getRepairInfoPager(@RequestBody RepairApplyFindEntity findEntity)
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
                Pager pager = repairApplyService.getRepairInfoPager(findEntity);
                return ResultVOUtil.success(pager);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取维修人员分页数据出现异常");
        }
    }

    /**
     * 维修人员接单
     *
     * @param repairApplyInfoVO the repairApplyInfoVO
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/repairCheckIn", method = RequestMethod.POST)
    public ResultVO repairCheckIn(@RequestBody RepairApplyInfoVO repairApplyInfoVO)
    {
        try
        {
            repairApplyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(repairApplyInfoVO.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else if (ObjectUtils.isEmpty(repairApplyInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else
            {
                repairApplyService.repairCheckIn(repairApplyInfoVO);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "维修人员接单出现异常");
        }
    }

    /**
     * 根据故障现象获取处理措施数据
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getTreatmentInfoList", method = RequestMethod.POST)
    public ResultVO getTreatmentInfoList(@RequestBody RepairApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(findEntity.getErrors()))
            {
                return ResultVOUtil.paramError("errors");
            }
            else
            {
                List<Map<String, Object>> data = repairApplyService.getTreatmentInfoList(findEntity);
                return ResultVOUtil.success(data);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据故障现象获取处理措施数据出现异常");
        }
    }

    /**
     * 维修设备
     *
     * @param request  the request
     * @param response the response
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/repairEquipment", method = RequestMethod.POST)
    public ResultVO repairEquipment(HttpServletRequest request, HttpServletResponse response)
    {

        try
        {
            String formData = request.getParameter("formData");
            RepairApplyInfoVO repairInfoVO = DataSwitch.convertJsonStringToEntity(formData, RepairApplyInfoVO.class);
            repairInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(repairInfoVO.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(repairInfoVO.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else
            {
                List<CommonsMultipartFile> photoUrl = null;
                if (repairInfoVO.isHavePic())
                {
                    photoUrl = FileUtil.getRequestFiles(request, "photoUrl");
                }
                repairApplyService.repairEquipment(photoUrl, repairInfoVO);
                response.setStatus(HttpStatus.OK.value());
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (ErrorQrCodeException errorCode)
        {
            return ResultVOUtil.error(1001, errorCode.getMessage());
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "维修出现异常");
        }
    }


    /**
     * 维修
     *
     * @param repairApplyInfoVO the repairApplyInfoVO
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/repairEquipmentForPhone", method = RequestMethod.POST)
    public ResultVO repairEquipmentForPhone(@RequestBody RepairApplyInfoVO repairApplyInfoVO)
    {
        try
        {
            repairApplyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(repairApplyInfoVO.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else
            {
                repairApplyService.repairEquipment(null, repairApplyInfoVO);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (ErrorQrCodeException errorCode)
        {
            return ResultVOUtil.error(1001, errorCode.getMessage());
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "维修出现异常");
        }
    }


    /**
     * 获得维修员工背包中对应的设备
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getSparePartsInBag", method = RequestMethod.POST)
    public ResultVO getSparePartsInBag(@RequestBody RepairApplyFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            List<Map<String, Object>> data = repairApplyService.getSparePartsInBag(findEntity);
            return ResultVOUtil.success(data);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获得维修员工背包中对应的设备出现异常");
        }
    }


    /**
     * 维修评价
     *
     * @param applyInfoVO the applyInfoVO
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/repairEvaluation", method = RequestMethod.POST)
    public ResultVO repairEvaluation(@RequestBody RepairApplyInfoVO applyInfoVO)
    {
        try
        {
            applyInfoVO.validateUserKey();
            if (ObjectUtils.isEmpty(applyInfoVO.getMaintenanceApplyId()))
            {
                return ResultVOUtil.paramError("maintenanceApplyId");
            }
            else
            {
                repairApplyService.repairEvaluation(applyInfoVO);
                return ResultVOUtil.success();
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "维修评价出现异常");
        }
    }

    /**
     * Gets list info.
     * 功能描述：根据备件IDs获取别见故障线信息
     * 创建人：郝伟州
     * 时间：2018年8月24日10:07:38
     *
     * @param entity the find entity
     * @return the list info
     */
    //    //@RequiresPermissions("wxxx")
    @ResponseBody
    @RequestMapping(value = "/getBreakdownList", method = RequestMethod.POST)
    public ResultVO getBreakdownList(@RequestBody RepairApplyInfoVO entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(repairApplyService.getBreakdownList(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "手机端接口，根据所属站点查询设备信息接口出现异常");
        }
    }

    /**
     * 根据二维码返回设备基本信息
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-27
     */
    @ResponseBody
    @RequestMapping(value = "/getDrviceInforByQrCode", method = RequestMethod.POST)
    public ResultVO getDrviceInforByQrCode(@RequestBody GetDrviceInforByQrcodeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return repairApplyService.getDrviceInforByQrCode(findEntity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据二维码返回设备基本信息出现异常");
        }
    }


    /**
     * 获取手机端报修记录 派单记录 维修记录，评价记录信息
     *
     * @param findEntity the findEntity
     * @return the json object
     */
    @ResponseBody
    @RequestMapping(value = "/getPhoneRepairApplyPageInfo", method = RequestMethod.POST)
    public ResultVO getPhoneRepairApplyPageInfo(@RequestBody RepairApplyFindEntity findEntity)
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
                Pager pager = repairApplyService.getPhoneRepairApplyPageInfo(findEntity);
                return ResultVOUtil.success(pager);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取维修单分页数据出现异常");
        }
    }
}