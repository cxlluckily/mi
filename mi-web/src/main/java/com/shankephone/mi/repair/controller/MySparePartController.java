package com.shankephone.mi.repair.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.repair.service.MySparePartService;
import com.shankephone.mi.repair.vo.MySparePartFindEntity;
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
 * 我的配件
 * @author 赵亮
 * @date 2018-08-06 9:34
 */
@Controller
@RequestMapping("/mySparePart")
public class MySparePartController
{
    @Autowired
    private MySparePartService mySparePartService;
    
    /**
     * 返回我的备件列表数据
     * @author：赵亮
     * @date：2018-08-06 
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getMySparePartList", method = RequestMethod.POST)
    public ResultVO getMySparePartList(@RequestBody MySparePartFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            if(ObjectUtils.isEmpty(findEntity.getUserKey())){
                return ResultVOUtil.paramError("userKey");
            }
            
            return ResultVOUtil.success(mySparePartService.getMySparePartList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"返回我的备件列表数据出现异常");
        }
    }
}
