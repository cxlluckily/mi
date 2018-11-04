package com.shankephone.mi.task.service;

import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockInStockEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.TaskMessageEntity;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;

/**
 * @author 郝伟州
 * @date 2018年9月26日09:59:42
 */
public interface TaskMessageService
{
    /**
     * 新增消息
     *
     * @author：郝伟州
     * @date：2018年9月26日09:59:47
     */
    Integer insertOne(TaskMessageEntity entity);
    /**
     *更新消息
     *@author：郝伟州
     *@date：2018年9月26日10:00:01
    */
    Integer updateOne(TaskMessageEntity entity);

    /**
     * 添加消息
     *
     * @param taskMessageEntity
     * @author：郝伟州
     * @date：2018年9月27日09:26:48
     */
    void insertTaskMessage(TaskMessageEntity taskMessageEntity) throws Exception;

    void insertApplyTaskMessage(ApplyInfoVO applyInfoVO) throws Exception;

    void insertInWarehouseTaskMessage(StockInStockEntity stockInStockEntity) throws Exception;

    void insertOutWarehuseTaskMessage(StockOutStockApplyEntity stockOutStockApplyEntity) throws Exception;

    void insertRepairTaskMessage(RepairApplyInfoVO repairInfoVO) throws Exception;

    void insertInventoryTaskMessage(StockInventoryTaskEntity entitytask) throws Exception;
}


