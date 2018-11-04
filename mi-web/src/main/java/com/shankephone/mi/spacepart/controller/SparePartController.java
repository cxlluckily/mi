package com.shankephone.mi.spacepart.controller;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartImageEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartEntityVO;
import com.shankephone.mi.spacepart.formbean.SparePartFindEntity;
import com.shankephone.mi.spacepart.service.SparePartService;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-29 13:40
 */
@Controller
@RequestMapping("/sparePart")
public class SparePartController
{
    @Autowired
    private SparePartService sparePartService;


    /**
     * 新增
     *
     * @param request
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-02
     */
    //@RequiresPermissions("bjgl")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(HttpServletRequest request)
    {
        try
        {
            PartSparePartEntityVO entity = DataSwitch.convertHttpServletRequestObjToEntity(PartSparePartEntityVO.class, request);
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            if (ObjectUtils.isEmpty(entity.getPartName()))
            {
                return ResultVOUtil.paramError("partName");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            Integer repeatCount = sparePartService.getSamePartCount(entity);
            if (repeatCount > 0)
            {
                return ResultVOUtil.error(0, "备件名称不能重复，请重新输入");
            }
            List<CommonsMultipartFile> photoUrls = FileUtil.getRequestFiles(request, "photoUrl");
            List<PartSparePartImageEntity> partImageEntities = new ArrayList<>();
            //如果有图片就上传图片
            if (entity.isHavePic() == true && null != photoUrls && photoUrls.size() > 0)
            {
                partImageEntities = getInsertImage(photoUrls);
            }
            return ResultVOUtil.success(sparePartService.insertOne(entity, partImageEntities));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增出现异常");
        }
    }

    /**
     * 更新备件
     *
     * @param
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-02
     */
    //@RequiresPermissions("bjgl")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            PartSparePartEntityVO entity = DataSwitch.convertHttpServletRequestObjToEntity(PartSparePartEntityVO.class, request);
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            if (ObjectUtils.isEmpty(entity.getPartName()))
            {
                return ResultVOUtil.paramError("partName");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(entity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            Integer repeatCount = sparePartService.getSamePartCount(entity);
            if (repeatCount > 0)
            {
                return ResultVOUtil.error(0, "备件名称不能重复，请重新输入");
            }
            //这块改成获取更新前的图片内容
            List<CommonsMultipartFile> photoUrls = FileUtil.getRequestFiles(request, "photoUrl");
            List<PartSparePartImageEntity> partImageEntities = new ArrayList<>();
            //如果有图片就上传图片
            if (entity.isHavePic() == true && null != photoUrls && photoUrls.size() > 0)
            {
                partImageEntities = getInsertImage(photoUrls);
            }

            return ResultVOUtil.success(sparePartService.updateOne(entity, partImageEntities));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新备件出现异常");
        }
    }

    private List<PartSparePartImageEntity> getInsertImage(List<CommonsMultipartFile> photoUrls) throws IOException
    {
        List<PartSparePartImageEntity> partImageEntities = new ArrayList<>();
        for (CommonsMultipartFile photoUrl : photoUrls)
        {
            if (photoUrl.getFileItem() != null && photoUrl.getSize() > 0)
            {
                String name = photoUrl.getFileItem().getName();
                String extName = name.substring(name.lastIndexOf(".") + 1);
                byte[] bytes = null;
                bytes = photoUrl.getBytes(); //将文件转换成字节流形式
                String fileId = FdfsClient.upload(bytes, extName, null);
                System.out.println(FdfsClient.getDownloadServer() + fileId);
                PartSparePartImageEntity imageEntity = new PartSparePartImageEntity();
                imageEntity.setImageUrl(fileId);
                partImageEntities.add(imageEntity);
            }
        }
        return partImageEntities;
    }

    /**
     * 根据查询条件获取数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("bjgl")
    @ResponseBody
    @RequestMapping(value = "/getSparePartInfo", method = RequestMethod.POST)
    public ResultVO getSparePartInfo(@RequestBody SparePartFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(findEntity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            return ResultVOUtil.success(sparePartService.getSparePartInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据查询条件获取备件数据出现异常");
        }
    }

    /**
     * 返回组件详细信息
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-07-09
     */
    //@RequiresPermissions("bjgl")
    @ResponseBody
    @RequestMapping(value = "/getSparePartDetail", method = RequestMethod.POST)
    public ResultVO getSparePartDetail(@RequestBody SparePartFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            return ResultVOUtil.success(sparePartService.getSparePartDetail(findEntity.getSparePartId()));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "返回组件详细信息出现异常");
        }
    }


    /***
     * "导入备件列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importSparePartList", method = RequestMethod.POST)
    public ResultVO importSparePartList(@RequestParam(value = "importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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
                messagesb.append(list.get(0)[7].trim());
                messagesb.append(list.get(0)[8].trim());

                if (!"备件名称品牌规格型号物资编码材质尺寸单位库存上限库存下限".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1, "备件导入模板出现错误");
                }
                list.remove(0);
                if (list == null || list.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                SparePartFindEntity findEntity = DataSwitch.convertRequestToEntity(SparePartFindEntity.class, request);
                findEntity.validateUserKey();
                return sparePartService.importSparePartList(findEntity, list);

            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入备件列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出备件信息
     * @return
     */
    @RequestMapping(value = "/sparePartExport")
    @ResponseBody
    public ResultVO sparePartExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            SparePartFindEntity findEntity = DataSwitch.convertRequestToEntity(SparePartFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = sparePartService.getAllSparePartListMap(findEntity);
            String path = ExcelUtil.exportExcel("sparePartListExport", listmap);
            return FileDownLoad.download(response, path, "备件导出.xls");
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
     * "导入备件type列表信息
     * @return
     */
    ////@RequiresPermissions("yhgl")
    @ResponseBody
    @RequestMapping(value = "/importSparePartTypeList", method = RequestMethod.POST)
    public ResultVO importSparePartTypeList(@RequestParam(value = "importfileUrl", required = false) CommonsMultipartFile importfileUrl, HttpServletRequest request)
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
                messagesb.append(list.get(0)[7].trim());
                messagesb.append(list.get(0)[8].trim());
                messagesb.append(list.get(0)[9].trim());
                messagesb.append(list.get(0)[10].trim());

                if (!"备件名称备件类型父级备件类型品牌规格型号物资编码材质尺寸单位库存上限库存下限".equals(messagesb.toString()))
                {
                    return ResultVOUtil.error(-1, "备件导入模板出现错误");
                }
                list.remove(0);
                if (list == null || list.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }
                SparePartFindEntity findEntity = DataSwitch.convertRequestToEntity(SparePartFindEntity.class, request);
                findEntity.validateUserKey();
                return sparePartService.importSparePartTypeList(findEntity, list);

            }

            return ResultVOUtil.success(1);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "导入备件列表信息出现异常");
        }
    }

    /***
     * 根据查询条件导出备件信息
     * @return
     */
    @RequestMapping(value = "/sparePartTypeExport")
    @ResponseBody
    public ResultVO sparePartTypeExport(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            SparePartFindEntity findEntity = DataSwitch.convertRequestToEntity(SparePartFindEntity.class, request);
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = sparePartService.getAllSparePartListMap(findEntity);
            String path = ExcelUtil.exportExcel("sparePartListTypeExport", listmap);
            return FileDownLoad.download(response, path, "备件导出.xls");
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
     * 获取备件图片信息列表
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：郝伟州
     * @date：2018-09-17
     */
    ////@RequiresPermissions("bjgl")
    @ResponseBody
    @RequestMapping(value = "/getSparePartImagesList", method = RequestMethod.POST)
    public ResultVO getSparePartImagesList(@RequestBody SparePartFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getSparePartId()))
            {
                return ResultVOUtil.paramError("sparePartId");
            }
            return ResultVOUtil.success(sparePartService.getSparePartImagesList(findEntity.getSparePartId()));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "返回获取备件图片信息列表出现异常");
        }
    }
}
