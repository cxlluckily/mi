package com.shankephone.mi.batchcommand.controller;

import com.shankephone.mi.batchcommand.formbean.SendCommandFormBean;
import com.shankephone.mi.batchcommand.service.CommandService;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author 赵亮
 * @date 2018-10-22 14:22
 */
@Controller
@RequestMapping("/command")
public class CommandController
{
    @Autowired
    private CommandService commandService;
    /**
     * 返回预设命令ddl数据
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-10-22
     */
    @ResponseBody
    @RequestMapping(value = "/getPreinstallCommandDDL", method = RequestMethod.POST)
    public ResultVO getPreinstallCommandDDL(@RequestBody BaseFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(commandService.getPreinstallCommandDDL());
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "返回预设命令ddl数据出现异常");
        }
    }

    /**
     * 发送命令
     * @author：赵亮
     * @date：2018-10-24
     * @param entity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/sendCommand", method = RequestMethod.POST)
    public ResultVO sendCommand(@RequestBody SendCommandFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(commandService.sendCommand(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"发送命令出现异常");
        }
    }
}
