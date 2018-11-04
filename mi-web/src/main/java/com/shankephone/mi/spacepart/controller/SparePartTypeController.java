package com.shankephone.mi.spacepart.controller;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeFindEntity;
import com.shankephone.mi.spacepart.service.SparePartTypeService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵亮
 * @date 2018-06-28 20:42
 */
@Controller
@RequestMapping("/sparePartType")
public class SparePartTypeController
{
    @Autowired
    SparePartTypeService sparePartTypeService;

    /**
     * 备件类型添加
     *
     * @param entity the PartSparePartTypeEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
    //@RequiresPermissions("bjlx")
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody PartSparePartTypeBusiEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getCategoryName()))
            {
                return ResultVOUtil.paramError("categoryName");
            }
            if (ObjectUtils.isEmpty(entity.getParentPartId()))
            {
                return ResultVOUtil.paramError("parentPartId");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            return sparePartTypeService.insertOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "备件类型添加出现异常");
        }
    }

    /**
     * 备件类型更新
     *
     * @param entity the PartSparePartTypeEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-28
     */
    //@RequiresPermissions("bjlx")
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody PartSparePartTypeEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getCategoryName()))
            {
                return ResultVOUtil.paramError("categoryName");
            }
            if (ObjectUtils.isEmpty(entity.getParentPartId()))
            {
                return ResultVOUtil.paramError("parentPartId");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(entity.getSparePartTypeId()))
            {
                return ResultVOUtil.paramError("sparePartTypeId");
            }
            return sparePartTypeService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "备件类型添加出现异常");
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
    //    //@RequiresPermissions("bjlx")
    @ResponseBody
    @RequestMapping(value = "/getSparePartTypeInfo", method = RequestMethod.POST)
    public ResultVO getSparePartTypeInfo(@RequestBody PartSparePartTypeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(sparePartTypeService.getSparePartTypeInfo(findEntity));
        }
        catch (AuthorizationException ax)
        {
            throw ax;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据查询条件获取备件类型数据出现异常");
        }
    }

    /**
     * 手机端：根据运营主体id获取 备件类型所有数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //    //@RequiresPermissions("bjlx")
    @ResponseBody
    @RequestMapping(value = "/getSparePartTypeKongGeList", method = RequestMethod.POST)
    public ResultVO getSparePartTypeKongGeList(@RequestBody PartSparePartTypeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return ResultVOUtil.success(sparePartTypeService.getSparePartTypeKongGeList(findEntity));
        }
        catch (AuthorizationException ax)
        {
            throw ax;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据查询条件获取备件类型数据出现异常");
        }
    }

}
