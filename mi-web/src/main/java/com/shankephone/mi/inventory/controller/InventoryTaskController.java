package com.shankephone.mi.inventory.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.formbean.GetInventoryTaskListFindEntity;
import com.shankephone.mi.inventory.formbean.InventoryFindEntity;
import com.shankephone.mi.inventory.formbean.UpdateInventoryTaskFormBean;
import com.shankephone.mi.inventory.service.InventoryTaskService;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.util.ResultVOUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-16 14:17
 */
@Controller
@RequestMapping("/inventoryTask")
public class InventoryTaskController
{
    @Autowired
    private InventoryTaskService inventoryTaskService;

    /**
     * 新增盘库信息
     *
     * @param entity the StockInventoryTaskEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-16
     */
    @ResponseBody
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public ResultVO insertOne(@RequestBody StockInventoryTaskEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return inventoryTaskService.insertOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增盘库信息出现异常");
        }
    }
    /**
     * 修改盘库信息
     *
     * @param entity the StockInventoryTaskEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-16
     */
    @ResponseBody
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public ResultVO updateOne(@RequestBody StockInventoryTaskEntity entity)
    {
        try
        {
            entity.validateUserKey();
            return inventoryTaskService.updateOne(entity);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "新增盘库信息出现异常");
        }
    }
    /**
     * 获取盘库信息列表
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-16
     */
    @ResponseBody
    @RequestMapping(value = "/getInventoryTaskList", method = RequestMethod.POST)
    public ResultVO getInventoryTaskList(@RequestBody GetInventoryTaskListFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(inventoryTaskService.getInventoryTaskList(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "获取盘库信息列表出现异常");
        }
    }

    /**
     * 根据任务Id返回盘库信息
     *
     * @param findEntity the findEntity
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-16
     */
    @ResponseBody
    @RequestMapping(value = "/getInventoryDetailEntity", method = RequestMethod.POST)
    public ResultVO getInventoryDetailEntity(@RequestBody InventoryFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            List<Map<String, Object>> listmap = inventoryTaskService.getInventoryDetailEntity(findEntity);

            List<Map<String, Object>> tastlist = inventoryTaskService.selectInventoryTaskentity(findEntity);
            Map<String, Object> tastmap = tastlist.get(0);

            tastmap.put("listDetail", listmap);

            return ResultVOUtil.success(tastmap);
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "根据任务Id返回盘库信息出现异常");
        }
    }

    /**
     * 更新盘库信息
     *
     * @param entity the UpdateInventoryTaskFormBean
     * @return the ResultVO
     * @author：赵亮
     * @date：2018-08-16
     */
    @ResponseBody
    @RequestMapping(value = "/updateInventoryTask", method = RequestMethod.POST)
    public ResultVO updateInventoryTask(@RequestBody UpdateInventoryTaskFormBean entity)
    {
        try
        {
            entity.validateUserKey();
            return ResultVOUtil.success(inventoryTaskService.updateInventoryTask(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex, "更新盘库信息出现异常");
        }
    }
}
