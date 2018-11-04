package com.shankephone.mi.partbreakdowninfo.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoFindEntity;
import com.shankephone.mi.partbreakdowninfo.service.PartBreakdownInfoService;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
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
 * 备件故障
 * @author 赵亮
 * @date 2018-08-02 9:51
 */
@Controller
@RequestMapping("/partBreakdownInfo")
public class PartBreakdownInfoController
{
    @Autowired
    private PartBreakdownInfoService partBreakdownInfoService;
    
    /**
     * 新增
     * @author：赵亮
     * @date：2018-08-02 
     * @param entity  the entity
     * @return the ResultVO
     */
    ////@RequiresPermissions("gzxx")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody PartBreakdownInfoEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partBreakdownInfoService.insertOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"新增出现异常");
        }
    }

    /**
     * 修改
     * @author：赵亮
     * @date：2018-08-02
     * @param entity  the entity
     * @return the ResultVO
     */
    //@RequiresPermissions("gzxx")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody PartBreakdownInfoEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partBreakdownInfoService.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"修改出现异常");
        }
    }

    /**
     * 获得列表信息
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("gzxx")
    @ResponseBody
    @RequestMapping(value = "/getBreakdownInfoList", method = RequestMethod.POST)
    public ResultVO getBreakdownInfoList(@RequestBody PartBreakdownInfoFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownInfoService.getBreakdownInfoList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"获得列表信息出现异常");
        }
    }

    /**
     * 根据查询条件返回配件名称列表
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("gzxx")
    @ResponseBody
    @RequestMapping(value = "/getSparePartDDLList", method = RequestMethod.POST)
    public ResultVO getSparePartDDLList(@RequestBody SparePartListForBreakdownFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownInfoService.getSparePartDDLList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"根据查询条件返回配件名称列表出现异常");
        }
    }

    /**
     * 根据备件返回故障信息
     * @author：赵亮
     * @date：2018-08-02
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("gzxx")
    @ResponseBody
    @RequestMapping(value = "/getPartBreakdownInfoDDLList", method = RequestMethod.POST)
    public ResultVO getPartBreakdownInfoDDLList(@RequestBody PartBreakdownInfoDDLFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partBreakdownInfoService.getPartBreakdownInfoDDLList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"根据备件返回故障信息出现异常");
        }
    }

    /***
     * "导入故障简称列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importPartBreakdownList", method = RequestMethod.POST)
    public ResultVO importPartBreakdownList(@RequestParam(value="importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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

                if(!"设备名称部件名称故障简称".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1,"故障简称信息导入模板出现错误");

                }
                list.remove(0);
                if(list==null||list.size()<1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                PartBreakdownInfoFindEntity findEntity= DataSwitch.convertRequestToEntity(PartBreakdownInfoFindEntity.class,request);
                findEntity.validateUserKey();
                if(ObjectUtils.isEmpty(findEntity.getUserKey())){
                    return ResultVOUtil.paramError("userKey");
                }
                return  partBreakdownInfoService.importPartBreakdownList(findEntity,list);

            }

            return ResultVOUtil.success(1);
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"故障简称导入列表信息出现异常");
        }
    }
}
