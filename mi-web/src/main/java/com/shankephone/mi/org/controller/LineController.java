package com.shankephone.mi.org.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.org.formbean.LineFindEntity;
import com.shankephone.mi.org.service.LineService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵亮
 * @date 2018-06-25 15:40
 */
@Controller
@RequestMapping("/line")
public class LineController
{
    @Autowired
    private LineService lineService;

    /**
     * 线路添加
     *
     * @param entity the OrgLineEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/insertLine", method = RequestMethod.POST)
    public ResultVO insertLine(@RequestBody OrgLineEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(entity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            if (ObjectUtils.isEmpty(entity.getLineName()))
            {
                return ResultVOUtil.paramError("lineName");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isNull(entity.getLineCode()))
            {
                return ResultVOUtil.paramError("lineCode");
            }


            return lineService.insertOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "线路添加出现异常");
        }
    }

    /**
     * 更新线路
     *
     * @param entity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-06-25
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/updateLine", method = RequestMethod.POST)
    public ResultVO updateLine(@RequestBody OrgLineEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isEmpty(entity.getUserKey()))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(entity.getOperationSubjectId()))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            if (ObjectUtils.isEmpty(entity.getLineName()))
            {
                return ResultVOUtil.paramError("lineName");
            }
            if (ObjectUtils.isEmpty(entity.getStatus()))
            {
                return ResultVOUtil.paramError("status");
            }
            if (ObjectUtils.isEmpty(entity.getLineId()))
            {
                return ResultVOUtil.paramError("lineId");
            }
            return  lineService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新线路出现异常");
        }
    }

    /**
     * 根据查询条件获取线路数据
     * @author：赵亮
     * @date：2018-06-25
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    //@RequiresPermissions("xlgl")
    @ResponseBody
    @RequestMapping(value = "/getLineInfo", method = RequestMethod.POST)
    public ResultVO getLineInfo(@RequestBody LineFindEntity findEntity)
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
            return ResultVOUtil.success(lineService.getLineInfo(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"根据查询条件获取线路数据出现异常");
        }
    }
}
