package com.shankephone.mi.common.controller;

import com.shankephone.mi.common.model.DataEntity;
import com.shankephone.mi.common.model.DataFindEntity;
import com.shankephone.mi.common.service.DataService;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.SessionMap;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 下拉列表数据加载
 *
 * @author fengql
 * @date 2018年6月22日 上午10:32:32
 */
@Controller
@RequestMapping("/data")
public class DataController
{

    @Resource
    private DataService dataService;

    @RequestMapping("/load")
    @ResponseBody
    public ResultVO<DataEntity> loadData(@RequestBody DataFindEntity dataEntity)
    {
        dataEntity.validateUserKey();
        List<DataEntity> datas = dataService.loadData(dataEntity);
        return ResultVOUtil.success(datas);
    }

    /**
     * 加载数据，支持树形和列表
     *
     * @param dataEntity
     * @return
     */
    @RequestMapping("/getData")
    @ResponseBody
    public ResultVO getData(@RequestBody DataFindEntity dataEntity)
    {
        return ResultVOUtil.success(dataService.getData(dataEntity));
    }

    @RequestMapping("/getWarehousesByUserId")
    @ResponseBody
    public ResultVO getWarehousesByUserId(@RequestBody DataFindEntity dataEntity)
    {
        dataEntity.validateUserKey();
        UserLoginInfo userInfo = SessionMap.getUserInfo(dataEntity.getUserKey());
        dataEntity.setEntityId(userInfo.getUserId());
        return ResultVOUtil.success(dataService.getWarehousesByUserId(dataEntity));
    }

    @RequestMapping("/getWarehousesByUserinfo")
    @ResponseBody
    public ResultVO getWarehousesByUserinfo(@RequestBody DataFindEntity dataEntity)
    {
        dataEntity.validateUserKey();

        return ResultVOUtil.success(dataService.getWarehousesByUserId(dataEntity));
    }

    @RequestMapping("/loadUpTree")
    @ResponseBody
    public ResultVO loadUpTree(@RequestBody DataFindEntity dataEntity)
    {
        dataEntity.validateUserKey();
        ResultVO result;
        try
        {
            result = ResultVOUtil.success(dataService.getToUpTree(dataEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResultVOUtil.error(e, "加载上级树失败！");
        }
        return result;
    }

    @RequestMapping("/loadTree")
    @ResponseBody
    public ResultVO loadTreeData(@RequestBody DataFindEntity dataEntity)
    {
        dataEntity.validateUserKey();
        return ResultVOUtil.success(dataService.loadTreeData(dataEntity));
    }

    @RequestMapping("/loadDict")
    @ResponseBody
    public ResultVO<DataEntity> loadDict(@RequestBody DataDictFindEntity dataEntity)
    {
        dataEntity.validateUserKey();
        return ResultVOUtil.success(dataService.loadDict(dataEntity));
    }

}
