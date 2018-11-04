package com.shankephone.mi.task.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 消息任务
 * @author 赵亮
 * @date 2018-08-06 10:29
 */
@Controller
@RequestMapping("/pendingTask")
public class PendingTaskController
{
    @Autowired
    private PendingTaskService pendingTaskService;

    /**
     * 查询待办任务列表
     * @author：赵亮
     * @date：2018-09-07
     * @param findEntity  the findEntity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/getPendingTaskList", method = RequestMethod.POST)
    public ResultVO getPendingTaskList(@RequestBody PendingTaskFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(pendingTaskService.getPendingTaskList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"查询待办任务列表出现异常");
        }
    }

    /**
     * 更新任务状态为已阅读
     * @author：郝伟州
     * @date：2018年10月8日15:43:06
     * @param entity  the entity
     * @return the ResultVO
     */
    @ResponseBody
    @RequestMapping(value = "/updateTaskSatuts", method = RequestMethod.POST)
    public ResultVO getPendingTaskList(@RequestBody TaskPendingTaskEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(pendingTaskService.updateTaskSatuts(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex,"更新任务状态为已阅读出现异常");
        }
    }
}