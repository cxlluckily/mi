package com.shankephone.mi.org.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.org.formbean.WorkSectionFIndEntity;
import com.shankephone.mi.org.formbean.WorkSectionFormBean;
import com.shankephone.mi.org.service.WorkSectionService;
import com.shankephone.mi.util.*;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 工区管理
 *
 * @author 赵亮
 * @date 2018-06-28 10:55
 */
@Controller
@RequestMapping("/workSection")
public class WorkSectionController
{
    @Autowired
    private WorkSectionService workSectionService;

    /**
     * 新增工区
     *
     * @param entity the WorkSectionFormBean
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
  //  //@RequiresPermissions("gqgl")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody WorkSectionFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            return workSectionService.insertOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增工区出现异常");
        }
    }

    /**
     * 获取线路和站点信息
     *
     * @param findEntity the WorkSectionFIndEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
//    //@RequiresPermissions("gqgl")
    @ResponseBody
    @RequestMapping(value = "/getLineAndStationInfo", method = RequestMethod.POST)
    public ResultVO getLineAndStationInfo(@RequestBody WorkSectionFIndEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getWorkSectionId()))
            {
                return ResultVOUtil.paramError("workSectionId");
            }
            if (ObjectUtils.isEmpty(findEntity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            return ResultVOUtil.success(workSectionService.getLineAndStationInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取线路和站点信息出现异常");
        }
    }

    /**
     * 更新工区
     *
     * @param entity the WorkSectionFormBean
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
  //  //@RequiresPermissions("gqgl")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody WorkSectionFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getOrgWorkSectionEntity().getWorkSectionId()))
            {
                return ResultVOUtil.paramError("workSectionId");
            }
            if (ObjectUtils.isEmpty(entity.getOrgWorkSectionEntity().getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
//            //验证工区编码不能重复
//            Integer repeatCount = dataService.getCountByFindEntity(new ValidateIsRepeatFindEntity("org_work_section", "workSectionId", entity.getOrgWorkSectionEntity().getWorkSectionId().toString(), "sectionCode", entity.getOrgWorkSectionEntity().getSectionCode()));
//            if (repeatCount > 0)
//            {
//                ResultVOUtil.error(0, "工区编码已存在，请重新输入");
//            }

            return workSectionService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新工区出现异常");
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
//    //@RequiresPermissions("gqgl")
    @ResponseBody
    @RequestMapping(value = "/getWorkSectionInfo", method = RequestMethod.POST)
    public ResultVO getWorkSectionInfo(@RequestBody WorkSectionFIndEntity findEntity)
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
            return ResultVOUtil.success(workSectionService.getWorkSectionInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据查询条件获取工区数据出现异常");
        }
    }

    /***
     * "导入工区线路车站列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importWorkSectionLineStationList", method = RequestMethod.POST)
    public ResultVO importLineStationList(@RequestParam(value="importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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

                if(!"工区编码工区名称线路编号线路名称站点编号站点名称".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1,"工区线路车站导入模板出现错误");

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
                return  workSectionService.importWorkSectionLineStationList(findEntity,list);

            }

            return ResultVOUtil.success(1);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"工区线路车站导入列表信息出现异常");
        }
    }
}
