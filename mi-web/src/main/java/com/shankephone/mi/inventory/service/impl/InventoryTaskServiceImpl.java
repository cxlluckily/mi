package com.shankephone.mi.inventory.service.impl;

import com.shankephone.mi.common.enumeration.ApplyNumberPrefixEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.dao.InventoryTaskDao;
import com.shankephone.mi.inventory.enumeration.InventoryTaskEnum;
import com.shankephone.mi.inventory.formbean.GetInventoryTaskListFindEntity;
import com.shankephone.mi.inventory.formbean.InventoryFindEntity;
import com.shankephone.mi.inventory.formbean.UpdateInventoryTaskFormBean;
import com.shankephone.mi.inventory.service.InventoryTaskService;
import com.shankephone.mi.model.StockInventoryDetailEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.NumberFactory;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-16 14:17
 */
@Service
public class InventoryTaskServiceImpl implements InventoryTaskService
{
    @Autowired
    private InventoryTaskDao inventoryTaskDao;

    @Autowired
    private PendingTaskService pendingTaskService;

    @Autowired
    private TaskMessageService taskMessageService;

    /**
     * 新增盘库信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-16 14:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertOne(StockInventoryTaskEntity entity) throws Exception
    {
        try
        {
            entity.setBatchNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.INVENTORY_TASK.getCode()));
            entity.setStatus(InventoryTaskEnum.BEFORE.getCode());
            List<Map<String, Object>> detailList = inventoryTaskDao.getInsertDetailList(entity.getWarehouseId());
            if(detailList!=null && detailList.size()==0)
            {
                return ResultVOUtil.error(0,"您将要盘库的仓库中没有备件！");
            }
            inventoryTaskDao.insertOne(entity);
            for (Map<String, Object> oneMap : detailList)
            {
                StockInventoryDetailEntity detailEntity = new StockInventoryDetailEntity();
                detailEntity.setUserKey(entity.getUserKey());
                detailEntity.setInventoryTaskId(entity.getInventoryTaskId());
                detailEntity.setSparePartId(DataSwitch.convertObjectToLong(oneMap.get("sparePartId")));
                detailEntity.setBeforeAccount(DataSwitch.convertObjectToInteger(oneMap.get("account")));
                detailEntity.setAftierAccount(0);
                detailEntity.setStatus(DataSwitch.convertObjectToString(oneMap.get("status")));
                detailEntity.setInventoryDescribe("");
                inventoryTaskDao.insertInventoryDetail(detailEntity);
            }
            //添加任务消息
            pendingTaskService.insertInventoryTask(entity);
            //发送微信消息或者短信
            taskMessageService.insertInventoryTaskMessage(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO updateOne(StockInventoryTaskEntity entity) throws Exception
    {
        try
        {
            inventoryTaskDao.updateOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 返回盘库分页数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-16 16:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<Map<String, Object>> getInventoryTaskList(GetInventoryTaskListFindEntity findEntity)
    {
        try
        {
            Integer total = inventoryTaskDao.getInventoryTaskListCount(findEntity);
            List<Map<String, Object>> entitys = inventoryTaskDao.getInventoryTaskList(findEntity);
            return new Pager<>(total, entitys);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据任务Id返回盘库信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-16 16:26
     */
    @Override
    public List<Map<String, Object>> getInventoryDetailEntity(InventoryFindEntity entity)
    {
        try
        {
            return inventoryTaskDao.getInventoryDetailEntity(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新盘库信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-16 16:32
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateInventoryTask(UpdateInventoryTaskFormBean entity) throws Exception
    {
        try
        {
            //更新库存状态
            StockInventoryTaskEntity taskEntity = new StockInventoryTaskEntity();
            taskEntity.setInventoryTaskId(entity.getInventoryTaskId());
            taskEntity.setStatus(entity.getStatus());
            inventoryTaskDao.updateInventoryTaskStatus(taskEntity);

            //更新任务消息为为完成
            if(InventoryTaskEnum.ALREADY.getCode().equals(entity.getStatus()))
            {
                TaskPendingTaskEntity taskPendingTaskEntity = new TaskPendingTaskEntity();
                taskPendingTaskEntity.setUserKey(entity.getUserKey());

                InventoryFindEntity findEntity=new InventoryFindEntity();
                findEntity.setInventoryTaskId(entity.getInventoryTaskId());
                List<Map<String, Object>> inentorylist=inventoryTaskDao.selectInventoryTaskentity(findEntity);
                if(inentorylist!=null&&inentorylist.size()>0)
                {
                    taskPendingTaskEntity.setBusiNo(DataSwitch.convertObjectToString(inentorylist.get(0).get("batchNo")));
                    taskPendingTaskEntity.setTaskPersonId(DataSwitch.convertObjectToLong(inentorylist.get(0).get("createUserId")));
                    taskPendingTaskEntity.setSourceId(DataSwitch.convertObjectToLong(inentorylist.get(0).get("inventoryTaskId")));

                }
                pendingTaskService.updateTaskOver(taskPendingTaskEntity);

                TaskMessageVo taskMessageVo=new TaskMessageVo();
                taskMessageVo.setReceiveId(taskPendingTaskEntity.getTaskPersonId());
                taskMessageVo.setContent(String.format("您的盘库任务【批次号：%s】已完成盘点。",taskPendingTaskEntity.getBusiNo()) );
                taskMessageVo.setSendUrl(false);
                taskMessageVo.setSourceId(taskPendingTaskEntity.getSourceId());
                taskMessageVo.setTypeName(InventoryTaskEnum.ALREADY.getValue());
                taskMessageVo.setMessageType(InventoryTaskEnum.ALREADY.getCode());
                //发送微信消息或者短信
                taskMessageService.insertTaskMessage(taskMessageVo);
            }

            //循环更新盘库详单状态
            for (StockInventoryDetailEntity detailEntity : entity.getEntities())
            {
                inventoryTaskDao.updateInventoryDetail(detailEntity);
            }
            return "success";
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> selectInventoryTaskentity(InventoryFindEntity entity)
    {
        try
        {
            return inventoryTaskDao.selectInventoryTaskentity(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


}
