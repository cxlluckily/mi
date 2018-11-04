package com.shankephone.mi.sys.controller;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysDataDictionaryEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.sys.service.DataDictionaryService;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 数据字典
 *
 * @author fengql
 * @date 2018年6月27日 上午10:16:54
 */
@Controller
@RequestMapping("/dataDict")
public class DataDictionaryController
{

    @Resource
    private DataDictionaryService dataDictionaryService;

    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResultVO<Pager<Map<String, Object>>> getDictPagerInfo(@RequestBody DataDictFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            Pager<Map<String, Object>> pager = dataDictionaryService.getDictPagerInfo(findEntity);
            return ResultVOUtil.success(pager);
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

    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/addDataDict", method = RequestMethod.POST)
    public ResultVO add(@RequestBody SysDataDictionaryEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            String code = findEntity.getCode();
            String name = findEntity.getName();
            String label = findEntity.getDataLabel();
            if (ObjectUtils.isEmpty(code))
            {
                return ResultVOUtil.paramError("code");
            }
            else if (ObjectUtils.isEmpty(name))
            {
                return ResultVOUtil.paramError("name");
            }
            else if (ObjectUtils.isEmpty(label))
            {
                return ResultVOUtil.paramError("label");
            }
            else
            {

                return dataDictionaryService.addDataDict(findEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            return ResultVOUtil.error(e);
        }
    }

    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/getDataDict", method = RequestMethod.POST)
    public ResultVO getDataDict(@RequestBody DataDictFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getDataDictionaryId()))
            {
                return ResultVOUtil.paramError("dataDictionaryId");
            }
            else
            {
                SysDataDictionaryEntity dataDict = dataDictionaryService.getDataDict(findEntity);
                return ResultVOUtil.success(dataDict);
            }

        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据 Id 查询数据字典信息出现异常");
        }
    }


    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/updateDataDict", method = RequestMethod.POST)
    public ResultVO update(@RequestBody SysDataDictionaryEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            String code = findEntity.getCode();
            String name = findEntity.getName();
            String label = findEntity.getDataLabel();
            if (ObjectUtils.isEmpty(code))
            {
                return ResultVOUtil.paramError("code");
            }
            else if (ObjectUtils.isEmpty(name))
            {
                return ResultVOUtil.paramError("name");
            }
            else if (ObjectUtils.isEmpty(label))
            {
                return ResultVOUtil.paramError("label");
            }
            else
            {

                return dataDictionaryService.updateDataDict(findEntity);
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            return ResultVOUtil.error(e);
        }
    }

    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/deleteDataDict", method = RequestMethod.POST)
    public ResultVO delete(@RequestBody DataDictFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if (ObjectUtils.isEmpty(findEntity.getDataDictionaryId()))
            {
                return ResultVOUtil.paramError("dataDictionaryId");
            }
            else
            {
                dataDictionaryService.deleteDataDict(findEntity);
                return ResultVOUtil.success();
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            return ResultVOUtil.success(e);
        }
    }

    //@RequiresPermissions("sjzd")
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public ResultVO deleteBatch(@RequestBody DataDictFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            dataDictionaryService.deleteBatch(findEntity);
            return ResultVOUtil.success();
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            return ResultVOUtil.success(e);
        }
    }
}
