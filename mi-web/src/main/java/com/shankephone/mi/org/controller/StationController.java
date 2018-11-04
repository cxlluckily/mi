package com.shankephone.mi.org.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgStationEntity;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.org.formbean.StationFormBean;
import com.shankephone.mi.org.service.StationService;
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
 * @author 赵亮
 * @date 2018-06-25 17:52
 */
@Controller
@RequestMapping("/station")
public class StationController
{
    @Autowired
    private StationService stationService;

    /**
     * 车站批量添加
     *
     * @param entity the StationFormBean
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public ResultVO batchInsert(@RequestBody StationFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            return stationService.batchInsert(entity.getStationList());
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "车站批量添加出现异常");
        }
    }

    /**
     * 更新车站信息
     *
     * @param entity the OrgStationEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody OrgStationEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getLineId()))
            {
                return ResultVOUtil.paramError("lineId");
            }
            if (ObjectUtils.isEmpty(entity.getStationId()))
            {
                return ResultVOUtil.paramError("stationId");
            }
            if (ObjectUtils.isEmpty(entity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            return stationService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新车站信息出现异常");
        }
    }

    /**
     * 根据查询条件获取数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/getStationInfo", method = RequestMethod.POST)
    public ResultVO getStationInfo(@RequestBody StationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            String userKey = findEntity.getUserKey();
            Long operationSubjectId = findEntity.getOperationSubjectId();

            if (ObjectUtils.isEmpty(operationSubjectId))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            else if (ObjectUtils.isEmpty(userKey))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(findEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(findEntity.getLineId()))
            {
                return ResultVOUtil.paramError("lindId");
            }
            return ResultVOUtil.success(stationService.getStationInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据查询条件获取车站数据出现异常");
        }
    }


    /***
     * "导入车站列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importStationList", method = RequestMethod.POST)
    public ResultVO importStationList(@RequestParam(value = "importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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
                if (!"站点编号站点名称负责人电话是否起始站是否终点站".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1, "车站导入模板出现错误");

                }
                list.remove(0);
                if (list == null || list.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                StationFindEntity findEntity = DataSwitch.convertRequestToEntity(StationFindEntity.class, request);
                findEntity.validateUserKey();
                return stationService.importStationList(findEntity, list);

            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入车站列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出车站信息
     * @return
     */
    @RequestMapping(value = "/stationExport")
    @ResponseBody
    public ResultVO stationExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            StationFindEntity findEntity = DataSwitch.convertRequestToEntity(StationFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = stationService.getAllStationListMap(findEntity);
            String path = ExcelUtil.exportExcel("stationListExport", listmap);
            return FileDownLoad.download(response, path, "车站导出.xls");

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
     * "导入线路车站列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importLineStationList", method = RequestMethod.POST)
    public ResultVO importLineStationList(@RequestParam(value="importfileUrl", required = false) CommonsMultipartFile importfileUrl,HttpServletRequest request)
    {
        try{
            if (importfileUrl.getFileItem() != null && importfileUrl.getFileItem().getSize()>0)
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
                List<String[]> list= ExcelUtil.readExcel(savePath,1,0);
                StringBuilder messagesb = new StringBuilder();
                messagesb.append(list.get(0)[0].trim());
                messagesb.append(list.get(0)[1].trim());
                messagesb.append(list.get(0)[2].trim());
                messagesb.append(list.get(0)[3].trim());
                messagesb.append(list.get(0)[4].trim());
                messagesb.append(list.get(0)[5].trim());
                messagesb.append(list.get(0)[6].trim());
                messagesb.append(list.get(0)[7].trim());
                if(!"线路编号线路名称站点编号站点名称负责人电话是否起始站是否终点站".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1,"线路车站导入模板出现错误");

                }
                list.remove(0);
                if(list==null||list.size()<1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                StationFindEntity findEntity= DataSwitch.convertRequestToEntity(StationFindEntity.class,request);
                findEntity.validateUserKey();
                if(ObjectUtils.isEmpty(findEntity.getUserKey())){
                    return ResultVOUtil.paramError("userKey");
                }
                return  stationService.importLineStationList(findEntity,list);

            }

            return ResultVOUtil.success(1);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"导入车站列表信息出现异常");
        }
    }


    /***
     * 根据查询条件导出车站信息
     * @return
     */
    @RequestMapping(value = "/lineStationExport")
    @ResponseBody
    public ResultVO lineStationExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            StationFindEntity findEntity=DataSwitch.convertRequestToEntity(StationFindEntity.class,request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap=  stationService.getAllStationListMap(findEntity);
            String path=  ExcelUtil.exportExcel("lineStationListExport",listmap);
            return   FileDownLoad.download(response,path,"线路车站导出.xls");

        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }
}
