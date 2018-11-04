package com.shankephone.mi.spacepart.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartDeviceComposeEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.spacepart.formbean.ComposeListFindEntity;
import com.shankephone.mi.spacepart.formbean.OneTreeDataFindEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeFindEntity;
import com.shankephone.mi.spacepart.service.PartDeviceComposeService;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 设备组成
 *
 * @author 赵亮
 * @date 2018-08-13 11:09
 */
@Controller
@RequestMapping("/deviceCompose")
public class PartDeviceComposeController
{
    @Autowired
    private PartDeviceComposeService partDeviceComposeService;

    /**
     * 新增设备组成
     *
     * @param entity the PartDeviceComposeEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-13
     */
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody PartDeviceComposeBusiEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.insertOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增设备组成出现异常");
        }
    }

    /**
     * 更新设备组成
     *
     * @param entity the PartDeviceComposeEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-13
     */
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody PartDeviceComposeEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新设备组成出现异常");
        }
    }

    /**
     * 查询设备组成列表
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-13
     */
    @ResponseBody
    @RequestMapping(value = "/getComposeList", method = RequestMethod.POST)
    public ResultVO getComposeList(@RequestBody ComposeListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.getComposeList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "查询设备组成列表出现异常");
        }
    }

    /**
     * 根据备件类型pid返回备件下拉框数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-13
     */
    @ResponseBody
    @RequestMapping(value = "/getSparePartByCompose", method = RequestMethod.POST)
    public ResultVO getSparePartByCompose(@RequestBody PartDeviceComposeFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.getSparePartByCompose(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据备件类型pid返回备件下拉框数据出现异常");
        }
    }

    /**
     * 删除设备组成
     *
     * @param entity the PartDeviceComposeEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-13
     */
    @ResponseBody
    @RequestMapping(value = "/deleteOne", method = RequestMethod.POST)
    public ResultVO deleteOne(@RequestBody PartDeviceComposeEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return partDeviceComposeService.deleteOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "删除设备组成出现异常");
        }
    }

    /**
     * 根据主键返回树形数据列表
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-14
     */
    @ResponseBody
    @RequestMapping(value = "/getOneTreeData", method = RequestMethod.POST)
    public ResultVO getOneTreeData(@RequestBody OneTreeDataFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.getOneTreeData(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据主键返回树形数据列表出现异常");
        }
    }

    /**
     * 根据备件分类ID返回对应备件信息
     *
     * @param entity the PartSparePartEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-14
     */
    @ResponseBody
    @RequestMapping(value = "/getPartListBySparePartTypeId", method = RequestMethod.POST)
    public ResultVO getPartListBySparePartTypeId(@RequestBody PartSparePartEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(partDeviceComposeService.getPartListBySparePartTypeId(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据备件分类ID返回对应备件信息出现异常");
        }
    }
}
