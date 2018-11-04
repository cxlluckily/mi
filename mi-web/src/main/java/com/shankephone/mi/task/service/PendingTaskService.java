package com.shankephone.mi.task.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockInStockEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.vo.PendingTaskListVO;

import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 10:29
 */
public interface PendingTaskService
{
    /**
     * 新增任务消息
     *
     * @author：赵亮
     * @date：2018-08-06 10:51
     */
    Integer insertOne(TaskPendingTaskEntity entity);
    /**
     *更新任务消息
     *@author：赵亮
     *@date：2018-08-06 10:53
    */
    Integer updateOne(TaskPendingTaskEntity entity);

    /**
     *根据当前登录人返回待办任务数据
     *@author：赵亮
     *@date：2018-08-06 11:06
    */
    Pager<Map<String, Object>> getPendingTaskList(PendingTaskFindEntity findEntity);

    /**
     *添加申请的任务消息
     *@author：赵亮
     *@date：2018-08-07 10:26
    */
    void insertApplyTask(ApplyInfoVO applyInfoVO);

    /**
     *删除申请的任务
     *@author：赵亮
     *@date：2018-08-07 11:14
    */
    void deleteApplyTask(ApplyInfoVO entity);

    /**
     *添加入库的任务消息
     *@author：赵亮
     *@date：2018-08-07 11:38
    */
    void insertInWarehouseTask(StockInStockEntity stockInStockEntity);

    /**
     *审核审核新增出库任务
     *@author：赵亮
     *@date：2018-08-07 14:08
    */
    void insertOutWarehuseTask(StockOutStockApplyEntity entity);

    /**
     *维修的任务
     *@author：赵亮
     *@date：2018-08-07 14:17
    */
    void insertRepairTask(RepairApplyInfoVO repairInfoVO);

    /**
     *更新任务状态为完结
     *@author：赵亮
     *@date：2018-09-06 11:01
    */
    void updateTaskOver(TaskPendingTaskEntity entity);
    /**
     *待接单任务
     *@author：赵亮
     *@date：2018-08-07 14:18
    */
    //void inertOrderReceive();

    /**
     *待评价任务
     *@author：赵亮
     *@date：2018-08-07 14:19
    */
    //void insertEvaluate();

    /**
     *更新任务状态为已阅读
     *@author：郝伟州
     *@date：2018年10月8日15:39:59
     */
    Integer updateTaskSatuts(TaskPendingTaskEntity entity);

    /**
     * 添加盘库任务消息
     *
     * @param entity
     * @author：郝伟州
     * @date：2018年10月18日16:50:22
     */
    void insertInventoryTask(StockInventoryTaskEntity entity);

    void setTaskUrl(PendingTaskListVO entity);
}
