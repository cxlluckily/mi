package com.shankephone.mi.task.service.impl;

import com.shankephone.mi.common.enumeration.ApplyTypeEnum;
import com.shankephone.mi.common.enumeration.RepairApplyStatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.enumeration.InventoryTaskEnum;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.StockInStockEntity;
import com.shankephone.mi.model.StockInventoryTaskEntity;
import com.shankephone.mi.model.StockOutStockApplyEntity;
import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.repair.dao.RepairApplyDao;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.task.dao.PendingTaskDao;
import com.shankephone.mi.task.enumeration.PendTaskStatus;
import com.shankephone.mi.task.enumeration.PendTaskTypeEnum;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.vo.PendingTaskListVO;
import com.shankephone.mi.task.vo.StockWarehouseEntityVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 10:29
 */
@Service
public class PendingTaskServiceImpl implements PendingTaskService
{
    @Autowired
    private PendingTaskDao pendingTaskDao;
    @Autowired
    private RepairApplyDao repairApplyDao;
    /**
     * 新增任务消息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-06 10:51
     */
    @Override
    public Integer insertOne(TaskPendingTaskEntity entity)
    {
        try
        {
            return insertOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新任务消息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-06 10:53
     */
    @Override
    public Integer updateOne(TaskPendingTaskEntity entity)
    {
        try
        {
            return pendingTaskDao.updateOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据当前登录人返回待办任务数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-06 11:06
     */
    @Override
    public Pager<Map<String, Object>> getPendingTaskList(PendingTaskFindEntity findEntity)
    {
        try
        {
            try
            {
                Integer total = pendingTaskDao.getPendingTaskListCount(findEntity);
                List<PendingTaskListVO> entitys = pendingTaskDao.getPendingTaskList(findEntity);
                for (PendingTaskListVO entity : entitys)
                {
                    setTaskUrl(entity);
                }
                Pager pager = new Pager<>(total, entitys);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 添加申请的任务消息
     *
     * @param applyInfoVO
     * @author：赵亮
     * @date：2018-08-07 10:26
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertApplyTask(ApplyInfoVO applyInfoVO)
    {
        try
        {
            String applyType = applyInfoVO.getApplyType();
            TaskPendingTaskEntity entity = new PendingTaskListVO();
            Long warehouseId = 0l;
            if (ApplyTypeEnum.USE.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.USE_APPLY.getCode());
                entity.setTitle(PendTaskTypeEnum.USE_APPLY.getMessage());
                warehouseId = applyInfoVO.getOutWarehouseId();
            }
            else if (ApplyTypeEnum.TRANSFER.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.TRANSFER.getCode());
                entity.setTitle(PendTaskTypeEnum.TRANSFER.getMessage());
                warehouseId = applyInfoVO.getOutWarehouseId();
            }
            else if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.RETURN.getCode());
                entity.setTitle(PendTaskTypeEnum.RETURN.getMessage());
                warehouseId = applyInfoVO.getInWarehouseId();
            }
            entity.setSourceId(applyInfoVO.getApplyId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setBusiNo(applyInfoVO.getApplyNo());

            entity.setTaskDescribe(applyInfoVO.getOperationUserName() + "提交的申请需要您进行审核，点击进行操作！");
            entity.setUserKey(applyInfoVO.getUserKey());
            //获取接受消息的人员列表
            StockWarehouseEntityVO stockWarehouseEntity = new StockWarehouseEntityVO();
            List<String> codelist=new ArrayList<>();
            codelist.add("'sqsh'");
            codelist.add("'spgl'");
            stockWarehouseEntity.setPermissionCodes(codelist);
            stockWarehouseEntity.setWarehouseId(warehouseId);
            List<Long> users = pendingTaskDao.getReceivePersonList(stockWarehouseEntity);
            for (Long userId : users)
            {
                entity.setTaskPersonId(userId);
                pendingTaskDao.insertOne(entity);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 删除申请的任务
     *
     * @param applyInfoVO
     * @author：赵亮
     * @date：2018-08-07 11:14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApplyTask(ApplyInfoVO applyInfoVO)
    {
        try
        {
            TaskPendingTaskEntity entity = new TaskPendingTaskEntity();
            if (ApplyTypeEnum.USE.getCode().equalsIgnoreCase(applyInfoVO.getApplyType()))
            {
                entity.setTaskType(PendTaskTypeEnum.USE_APPLY.getCode());
            }
            else if (ApplyTypeEnum.TRANSFER.getCode().equalsIgnoreCase(applyInfoVO.getApplyType()))
            {
                entity.setTaskType(PendTaskTypeEnum.TRANSFER.getCode());
            }
            else if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(applyInfoVO.getApplyType()))
            {
                entity.setTaskType(PendTaskTypeEnum.RETURN.getCode());
            }
            entity.setSourceId(applyInfoVO.getApplyId());
            pendingTaskDao.deleteTask(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 添加入库的任务消息
     *
     * @param stockInStockEntity
     * @author：赵亮
     * @date：2018-08-07 11:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInWarehouseTask(StockInStockEntity stockInStockEntity)
    {
        try
        {
            try
            {
                String applyType = stockInStockEntity.getInStockType();
                TaskPendingTaskEntity entity = new PendingTaskListVO();
                if (ApplyTypeEnum.NEW_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setTaskType(PendTaskTypeEnum.NEW_IN.getCode());
                    entity.setTitle(PendTaskTypeEnum.NEW_IN.getMessage());
                }
                else if (ApplyTypeEnum.TRANSFER_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setTaskType(PendTaskTypeEnum.TRANSFER_IN.getCode());
                    entity.setTitle(PendTaskTypeEnum.TRANSFER_IN.getMessage());
                }
                else if (ApplyTypeEnum.BORROW_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setTaskType(PendTaskTypeEnum.BORROW_IN.getCode());
                    entity.setTitle(PendTaskTypeEnum.BORROW_IN.getMessage());
                }
                else if (ApplyTypeEnum.RETURN_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setTaskType(PendTaskTypeEnum.RETURN_IN.getCode());
                    entity.setTitle(PendTaskTypeEnum.RETURN_IN.getMessage());
                }
                else if (ApplyTypeEnum.USE_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setTaskType(PendTaskTypeEnum.USE_IN.getCode());
                    entity.setTitle(PendTaskTypeEnum.USE_IN.getMessage());
                }
                entity.setSourceId(stockInStockEntity.getInStockId());
                entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
                entity.setBusiNo(stockInStockEntity.getInStockApplyNo());
                entity.setTitle("备件入库");
                entity.setUserKey(stockInStockEntity.getUserKey());
                entity.setTaskDescribe("您有一条待入库的任务，点击进行操作！");
                //获取接受消息的人员列表
                StockWarehouseEntityVO stockWarehouseEntity = new StockWarehouseEntityVO();
                List<String> codelist=new ArrayList<>();
                codelist.add("'rk'");
                codelist.add("'rkgl'");
                stockWarehouseEntity.setPermissionCodes(codelist);
                stockWarehouseEntity.setWarehouseId(stockInStockEntity.getInWarehouseId());
                List<Long> users = pendingTaskDao.getReceivePersonList(stockWarehouseEntity);
                for (Long userId : users)
                {
                    entity.setTaskPersonId(userId);
                    pendingTaskDao.insertOne(entity);

                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 审核审核新增出库任务
     *
     * @param stockOutStockApplyEntity
     * @author：赵亮
     * @date：2018-08-07 14:08
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOutWarehuseTask(StockOutStockApplyEntity stockOutStockApplyEntity)
    {
        try
        {
            String applyType = stockOutStockApplyEntity.getOutOrderType();
            TaskPendingTaskEntity entity = new PendingTaskListVO();
            if (ApplyTypeEnum.TRANSFER_OUT.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.TRANSFER_OUT.getCode());
                entity.setTitle(PendTaskTypeEnum.TRANSFER_OUT.getMessage());
            }
            else if (ApplyTypeEnum.BORROW_OUT.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.BORROW_OUT.getCode());
                entity.setTitle(PendTaskTypeEnum.BORROW_OUT.getMessage());
            }
            else if (ApplyTypeEnum.RETURN_OUT.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.RETURN_OUT.getCode());
                entity.setTitle(PendTaskTypeEnum.RETURN_OUT.getMessage());
            }
            else if (ApplyTypeEnum.USE_OUT.getCode().equalsIgnoreCase(applyType))
            {
                entity.setTaskType(PendTaskTypeEnum.USE_OUT.getCode());
                entity.setTitle(PendTaskTypeEnum.USE_OUT.getMessage());
            }
            entity.setSourceId(stockOutStockApplyEntity.getOutStockApplyId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setBusiNo(stockOutStockApplyEntity.getOutStockApplyNO());
            entity.setTaskDescribe("您有一条待出库的任务，点击进行操作！");
            entity.setUserKey(stockOutStockApplyEntity.getUserKey());
            //获取接受消息的人员列表
            StockWarehouseEntityVO stockWarehouseEntity = new StockWarehouseEntityVO();
            List<String> codelist=new ArrayList<>();
            codelist.add("'ck'");
            codelist.add("'ckgl'");
            stockWarehouseEntity.setPermissionCodes(codelist);
            stockWarehouseEntity.setWarehouseId(stockOutStockApplyEntity.getOutWarehouseId());
            List<Long> users = pendingTaskDao.getReceivePersonList(stockWarehouseEntity);
            for (Long userId : users)
            {
                entity.setTaskPersonId(userId);
                pendingTaskDao.insertOne(entity);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 维修的任务
     *
     * @param repairInfoVO
     * @author：赵亮
     * @date：2018-08-07 14:17
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRepairTask(RepairApplyInfoVO repairInfoVO)
    {
        try
        {
            TaskPendingTaskEntity entity = new PendingTaskListVO();
            //获取维修主表信息
            Map<String, Object> applyInfo = repairApplyDao.getApplyInfoById(repairInfoVO.getMaintenanceApplyId());
            if(StringUtils.isNotEmpty(applyInfo))
            {
                repairInfoVO.setApplyNO(DataSwitch.convertObjectToString(applyInfo.get("applyNO")));
                repairInfoVO.setCreateUserId(DataSwitch.convertObjectToLong(applyInfo.get("createUserId")));
                repairInfoVO.setMaintenanceUserID(DataSwitch.convertObjectToLong(applyInfo.get("maintenanceUserID")));
                repairInfoVO.setAppointUserId(DataSwitch.convertObjectToLong(applyInfo.get("appointUserId")));
                repairInfoVO.setStationId(DataSwitch.convertObjectToLong(applyInfo.get("stationId")));
                TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
                taskEntity.setUserKey(repairInfoVO.getUserKey());
                taskEntity.setBusiNo(DataSwitch.convertObjectToString(applyInfo.get("applyNO")));
                this.updateTaskOver(taskEntity);
            }
            entity.setSourceId(repairInfoVO.getMaintenanceApplyId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setBusiNo(repairInfoVO.getApplyNO());
           // entity.setTaskDescribe("您有一条待派单的任务，点击进行操作！");
            entity.setUserKey(repairInfoVO.getUserKey());

            if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.toBeDispatch.getValue()))//待派单
            {
                entity.setTaskDescribe("您有一条待派单的任务，点击进行操作！");
                entity.setTaskType(PendTaskTypeEnum.TO_BE_DISPATCH.getCode());
                entity.setTitle(PendTaskTypeEnum.TO_BE_DISPATCH.getMessage());
                PendingTaskFindEntity findEntity = new PendingTaskFindEntity();
                findEntity.setPermissionCode("'bxpg','wxgl'");//报修派工节点权限

                findEntity.setStationId(DataSwitch.convertObjectToString(repairInfoVO.getStationId()));
                List<Map<String, Object>> daipaidanUserList = pendingTaskDao.getPaiDuanRenYuanList(findEntity);
                if (daipaidanUserList != null)
                {
                    for (Map<String, Object> daipaidanUserMap : daipaidanUserList)
                    {
                        entity.setTaskPersonId(DataSwitch.convertObjectToLong(daipaidanUserMap.get("userId")));
                        pendingTaskDao.insertOne(entity);
                    }
                }
                return;
            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.toBeRepair.getValue()))//待接修
            {
                entity.setTaskDescribe("您有一条待接修的任务，点击进行操作！");
                entity.setTaskType(PendTaskTypeEnum.TO_BE_REPAIR.getCode());
                entity.setTitle(PendTaskTypeEnum.TO_BE_REPAIR.getMessage());
                entity.setTaskPersonId(repairInfoVO.getMaintenanceUserID());
            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.repaired.getValue()))//待评价
            {
                entity.setTaskDescribe("您有一条待评价的任务，点击进行操作！");
                entity.setTaskType(PendTaskTypeEnum.TO_BE_EVALUATE.getCode());
                entity.setTitle(PendTaskTypeEnum.TO_BE_EVALUATE.getMessage());
                entity.setTaskPersonId(repairInfoVO.getCreateUserId());

                StockWarehouseEntityVO stockWarehouseEntity = new StockWarehouseEntityVO();
                List<String> codelist=new ArrayList<>();
                codelist.add("'hfpj'");
                stockWarehouseEntity.setPermissionCodes(codelist);
                stockWarehouseEntity.setPermissionId(repairInfoVO.getCreateUserId());
                List<Long> userIdArr=  pendingTaskDao.getPersonPermissionCode(stockWarehouseEntity);
                if(StringUtils.isEmpty(userIdArr) || userIdArr.size()<1)
                {
                    return;
                }

            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.noRepair.getValue()))//未修复
            {


//                entity.setTaskDescribe("您有一条未修复的任务，点击进行操作！");
//                entity.setTaskType(PendTaskTypeEnum.NO_REPAIR.getCode());
//                entity.setTitle(PendTaskTypeEnum.NO_REPAIR.getMessage());
//                entity.setTaskPersonId(repairInfoVO.getCreateUserId());
                entity.setTaskDescribe("您有一条待派单的任务，点击进行操作！");
                entity.setTaskType(PendTaskTypeEnum.TO_BE_DISPATCH.getCode());
                entity.setTitle(PendTaskTypeEnum.TO_BE_DISPATCH.getMessage());
                PendingTaskFindEntity findEntity = new PendingTaskFindEntity();
                findEntity.setPermissionCode("'bxpg','wxgl'");//报修派工节点权限
                findEntity.setStationId(DataSwitch.convertObjectToString(repairInfoVO.getStationId()));
                List<Map<String, Object>> daipaidanUserList = pendingTaskDao.getPaiDuanRenYuanList(findEntity);
                if (daipaidanUserList != null)
                {
                    for (Map<String, Object> daipaidanUserMap : daipaidanUserList)
                    {
                        entity.setTaskPersonId(DataSwitch.convertObjectToLong(daipaidanUserMap.get("userId")));
                        pendingTaskDao.insertOne(entity);
                    }
                }
                return;
            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.maintenance.getValue()))//待录入维修结果
            {
                entity.setTaskType(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getCode());
                entity.setTitle(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getMessage());
                entity.setTaskPersonId(repairInfoVO.getMaintenanceUserID());
                entity.setTaskDescribe("您有一条待录入维修结果任务，点击进行操作！");
            }
            pendingTaskDao.insertOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新任务状态为完结
     *
     * @param entity
     * @author：赵亮
     * @date：2018-09-06 11:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTaskOver(TaskPendingTaskEntity entity)
    {
        try
        {
            pendingTaskDao.updateTaskOver(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新任务状态为完结
     *
     * @param entity
     * @author：赵亮
     * @date：2018-09-06 11:01
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateTaskSatuts(TaskPendingTaskEntity entity)
    {
        try
        {
           return pendingTaskDao.updateTaskSatuts(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public void setTaskUrl(PendingTaskListVO entity)
    {
        if (entity.getTaskType().equals(PendTaskTypeEnum.USE_APPLY.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.USE_APPLY.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.USE_APPLY.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?applyId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TRANSFER.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TRANSFER.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.TRANSFER.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?applyId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.RETURN.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.RETURN.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.RETURN.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?applyId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.NEW_IN.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.NEW_IN.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.NEW_IN.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?inStockId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TRANSFER_IN.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TRANSFER_IN.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.TRANSFER_IN.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?inStockId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.BORROW_IN.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.BORROW_IN.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.BORROW_IN.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?inStockId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.RETURN_IN.getCode()))
        {
            entity.setManageUrl(PendTaskTypeEnum.RETURN_IN.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.RETURN_IN.getPhoneUrl() + "?inStockId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.USE_IN.getCode()))
        {

            entity.setManageUrl(PendTaskTypeEnum.USE_IN.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.USE_IN.getPhoneUrl() + "?inStockId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.USE_OUT.getCode()))
        {
            entity.setManageUrl(PendTaskTypeEnum.USE_OUT.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.USE_OUT.getPhoneUrl() + "?outStockApplyId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TRANSFER_OUT.getCode()))
        {

            entity.setManageUrl(PendTaskTypeEnum.TRANSFER_OUT.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.TRANSFER_OUT.getPhoneUrl() + "?outStockApplyId=" + entity.getSourceId());

            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.BORROW_OUT.getCode()))
        {
            entity.setManageUrl(PendTaskTypeEnum.BORROW_OUT.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.BORROW_OUT.getPhoneUrl() + "?outStockApplyId=" + entity.getSourceId());

            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.RETURN_OUT.getCode()))
        {
            entity.setManageUrl(PendTaskTypeEnum.RETURN_OUT.getManageUrl());
            entity.setPhoneUrl(PendTaskTypeEnum.RETURN_OUT.getPhoneUrl() + "?outStockApplyId=" + entity.getSourceId());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TO_BE_DISPATCH.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TO_BE_DISPATCH.getPhoneUrl()+"/" + entity.getSourceId());
            entity.setManageUrl(PendTaskTypeEnum.TO_BE_DISPATCH.getManageUrl());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TO_BE_REPAIR.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TO_BE_REPAIR.getPhoneUrl()+"/" + entity.getSourceId());
            entity.setManageUrl(PendTaskTypeEnum.TO_BE_REPAIR.getManageUrl());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.TO_BE_EVALUATE.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TO_BE_EVALUATE.getPhoneUrl()+"/" + entity.getSourceId());
            entity.setManageUrl(PendTaskTypeEnum.TO_BE_EVALUATE.getManageUrl());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.NO_REPAIR.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.NO_REPAIR.getPhoneUrl()+"/" + entity.getSourceId());
            entity.setManageUrl(PendTaskTypeEnum.NO_REPAIR.getManageUrl());
            return;
        }
        if (entity.getTaskType().equals(PendTaskTypeEnum.BEFORE.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.BEFORE.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.BEFORE.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?taskId=" + entity.getSourceId());
            return;
        }

        if (entity.getTaskType().equals(PendTaskTypeEnum.REVIEW_NO_PASS.getCode()))
        {
            entity.setPhoneUrl(PendTaskTypeEnum.REVIEW_NO_PASS.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.REVIEW_NO_PASS.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() + "?applyId=" + entity.getSourceId());
            return;
        }

     if (entity.getTaskType().equals(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getCode()))//待录入维修结果
        {
            entity.setPhoneUrl(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getPhoneUrl());
            entity.setManageUrl(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getManageUrl());
            entity.setPhoneUrl(entity.getPhoneUrl() +"/" + entity.getSourceId());
            return;
        }
    }
    /**
     * 添加盘库任务消息
     *
     * @param entitytask
     * @author：郝伟州
     * @date：2018年10月18日16:50:22
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInventoryTask(StockInventoryTaskEntity entitytask)
    {
        try
        {
            TaskPendingTaskEntity entity = new PendingTaskListVO();

            entity.setSourceId(entitytask.getInventoryTaskId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setBusiNo(entitytask.getBatchNo());
            entity.setUserKey(entitytask.getUserKey());
            entity.setTaskDescribe("您有一条待盘库的任务，点击进行操作！");
            entity.setTaskType(InventoryTaskEnum.BEFORE.getCode());
            entity.setTitle(InventoryTaskEnum.BEFORE.getValue());
            entity.setTaskPersonId(entitytask.getHeadPersonUserId());
            pendingTaskDao.insertOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
