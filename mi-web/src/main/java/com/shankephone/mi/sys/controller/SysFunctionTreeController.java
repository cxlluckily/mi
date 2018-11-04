package com.shankephone.mi.sys.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.sys.formbean.FunctionTreeFindEntity;
import com.shankephone.mi.sys.service.SysFunctionTreeService;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author fengql
 * @date 2018年6月27日 上午10:16:54
 */
@Controller
@RequestMapping("/function")
public class SysFunctionTreeController
{

    @Resource
    private SysFunctionTreeService sysFunctionTreeService;

    @RequestMapping(value = "/tree")
    @ResponseBody
    public ResultVO getFunctionTree(@RequestBody FunctionTreeFindEntity entity)
    {
        entity.validateUserKey();
        List<Map<String, Object>> maps = sysFunctionTreeService.getFunctionTree(entity);
        return ResultVOUtil.success(maps);
    }

}
