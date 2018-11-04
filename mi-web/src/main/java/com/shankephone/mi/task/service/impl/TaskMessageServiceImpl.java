package com.shankephone.mi.task.service.impl;

import com.shankephone.mi.common.enumeration.ApplyTypeEnum;
import com.shankephone.mi.common.enumeration.RepairApplyStatusEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.inventory.dao.ApplyInfoDao;
import com.shankephone.mi.inventory.enumeration.InventoryTaskEnum;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.repair.dao.RepairApplyDao;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.sys.dao.SysUserDao;
import com.shankephone.mi.task.dao.PendingTaskDao;
import com.shankephone.mi.task.dao.TaskMessageDao;
import com.shankephone.mi.task.enumeration.PendTaskStatus;
import com.shankephone.mi.task.enumeration.PendTaskTypeEnum;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.StockWarehouseEntityVO;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.PropertyAccessor;
import com.shankephone.mi.util.SendSms;
import com.shankephone.mi.util.StringUtils;
import com.shankephone.mi.wechat.formbean.KeFuMessageEntity;
import com.shankephone.mi.wechat.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 郝伟州
 * @date 2018年9月26日10:03:26
 */
@Service
public class TaskMessageServiceImpl implements TaskMessageService
{
    @Autowired
    private TaskMessageDao taskMessageDao;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private PendingTaskDao pendingTaskDao;
    @Autowired
    private ApplyInfoDao applyInfoDao;
    @Autowired
    private RepairApplyDao repairApplyDao;
    /**
     * 新增消息
     *
     * @param entity
     * @author：郝伟州
     * @date：2018年9月26日10:03:19
     */
    @Override
    public Integer insertOne(TaskMessageEntity entity)
    {
        try
        {
            return taskMessageDao.insertOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新消息
     *
     * @param entity
     * @author：郝伟州
     * @date：2018年9月26日10:03:13
     */
    @Override
    public Integer updateOne(TaskMessageEntity entity)
    {
        try
        {
            return taskMessageDao.updateOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 添加申请发送消息
     *
     * @param taskMessageEntity
     * @author：郝伟州
     * @date：2018年9月27日09:26:48
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTaskMessage(TaskMessageEntity taskMessageEntity) throws Exception
    {
        try
        {
            TaskMessageVo taskMessageVo=(TaskMessageVo)taskMessageEntity;
            taskMessageDao.insertOne(taskMessageEntity);
            String weixin = PropertyAccessor.getProperty("message.weixin");
            String duanxin = PropertyAccessor.getProperty("message.duanxin");
            SysUserEntity sysUserEntity = sysUserDao.findUserByuserId(taskMessageEntity.getReceiveId());
            if(sysUserEntity==null||!StatusEnum.START_USING.getValue().equals(sysUserEntity.getStatus()))
            {
                return;
            }
            boolean flag = false;
            if (DataSwitch.convertObjectToBoolean(weixin) && StringUtils.isNotEmpty(sysUserEntity.getOpenId()))
            {
                try
                {
                    KeFuMessageEntity keFuMessageEntity = new KeFuMessageEntity();
                    keFuMessageEntity.setToUser(sysUserEntity.getOpenId());
                    keFuMessageEntity.setContent(taskMessageEntity.getContent());
                    keFuMessageEntity.setTypeName(taskMessageVo.getTypeName());
                    keFuMessageEntity.setTaskType(taskMessageEntity.getMessageType());
                    keFuMessageEntity.setSourceId(taskMessageEntity.getSourceId());
                    keFuMessageEntity.setSendUrl(taskMessageVo.isSendUrl());
                    flag=wechatService.sendTemplateMessage(keFuMessageEntity);
                }
                catch (Exception e)
                {
                     e.printStackTrace();
                }
            }
            if (!flag && DataSwitch.convertObjectToBoolean(duanxin) && StringUtils.isphone(sysUserEntity.getPhone()))
            {
                SendSms.sendSms(sysUserEntity.getPhone(), taskMessageEntity.getContent());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw ex;

        }
    }


    /**
     * 添加申请的任务消息
     *
     * @param applyInfoVO
     * @author：郝伟州
     * @date：2018年9月27日14:12:29
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertApplyTaskMessage(ApplyInfoVO applyInfoVO) throws Exception
    {
        try
        {
            String applyType = applyInfoVO.getApplyType();
            TaskMessageVo entity = new TaskMessageVo();
            Long warehouseId = 0l;
            if (ApplyTypeEnum.USE.getCode().equalsIgnoreCase(applyType))
            {
                entity.setMessageType(PendTaskTypeEnum.USE_APPLY.getCode());
                entity.setTypeName(PendTaskTypeEnum.USE_APPLY.getMessage());
                warehouseId = applyInfoVO.getOutWarehouseId();
            }
            else if (ApplyTypeEnum.TRANSFER.getCode().equalsIgnoreCase(applyType))
            {
                entity.setMessageType(PendTaskTypeEnum.TRANSFER.getCode());
                entity.setTypeName(PendTaskTypeEnum.TRANSFER.getMessage());
                warehouseId = applyInfoVO.getOutWarehouseId();
            }
            else if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(applyType))
            {
                entity.setMessageType(PendTaskTypeEnum.RETURN.getCode());
                entity.setTypeName(PendTaskTypeEnum.RETURN.getMessage());
                warehouseId = applyInfoVO.getInWarehouseId();
            }
            entity.setSourceId(applyInfoVO.getApplyId());

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
                entity.setReceiveId(userId);
                entity.setContent("您有一条待审核的工单，请关注数字运维微信公众号进行处理！");
                this.insertTaskMessage(entity);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 审核新增出库任务消息
     *
     * @param stockOutStockApplyEntity
     * @author：郝伟州
     * @date：2018年9月27日14:12:48
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOutWarehuseTaskMessage(StockOutStockApplyEntity stockOutStockApplyEntity) throws Exception
    {
        try
        {
            String applyType = stockOutStockApplyEntity.getOutOrderType();
            TaskMessageVo entity = new TaskMessageVo();
            entity.setSourceId(stockOutStockApplyEntity.getOutStockApplyId());
            entity.setUserKey(stockOutStockApplyEntity.getUserKey());
            if (ApplyTypeEnum.TRANSFER_OUT.getCode().equalsIgnoreCase(applyType))//调拨
            {
                entity.setMessageType(PendTaskTypeEnum.TRANSFER_OUT.getCode());
                entity.setTypeName(PendTaskTypeEnum.TRANSFER_OUT.getMessage());
            }
            else if (ApplyTypeEnum.BORROW_OUT.getCode().equalsIgnoreCase(applyType))//借用
            {
                entity.setMessageType(PendTaskTypeEnum.BORROW_OUT.getCode());
                entity.setTypeName(PendTaskTypeEnum.BORROW_OUT.getMessage());
            }
            else if (ApplyTypeEnum.RETURN_OUT.getCode().equalsIgnoreCase(applyType))//返还
            {
                entity.setMessageType(PendTaskTypeEnum.RETURN_OUT.getCode());
                entity.setTypeName(PendTaskTypeEnum.RETURN_OUT.getMessage());
            }
            else if (ApplyTypeEnum.USE_OUT.getCode().equalsIgnoreCase(applyType))//领用
            {

//                ApplyInfoVO applyInfoVO = applyInfoDao.getApplyInfoByApplyId(stockOutStockApplyEntity.getApplyId());
//                TaskMessageVo taskMessageVo=new TaskMessageVo();
//                taskMessageVo.setReceiveId(applyInfoVO.getApplyUserId());
//                taskMessageVo.setContent("您有一条待领取的工单，请关注数字运维微信公众号进行处理！" );
//                taskMessageVo.setSendUrl(false);
//                taskMessageVo.setSourceId(stockOutStockApplyEntity.getApplyId());
//                taskMessageVo.setTypeName("领用申请");
//                taskMessageVo.setMessageType(PendTaskTypeEnum.USE_OUT.getCode());
//                this.insertTaskMessage(taskMessageVo);

                entity.setMessageType(PendTaskTypeEnum.USE_OUT.getCode());
                entity.setTypeName(PendTaskTypeEnum.USE_OUT.getMessage());
            }

            //获取接受消息的人员列表
            StockWarehouseEntityVO stockWarehouseEntity = new StockWarehouseEntityVO();
            List<String> codelist=new ArrayList<>();
            codelist.add("'ck'");
            codelist.add("'ckgl'");
            stockWarehouseEntity.setWarehouseId(stockOutStockApplyEntity.getOutWarehouseId());
            stockWarehouseEntity.setPermissionCodes(codelist);

            List<Long> users = pendingTaskDao.getReceivePersonList(stockWarehouseEntity);
            for (Long userId : users)
            {
                entity.setReceiveId(userId);
                entity.setContent("您有一条待出库的工单，请关注数字运维微信公众号进行处理！");
                this.insertTaskMessage(entity);
            }
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
     * @author：郝伟州
     * @date：2018年9月27日14:12:38
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertInWarehouseTaskMessage(StockInStockEntity stockInStockEntity) throws Exception
    {
        try
        {
            try
            {
                String applyType = stockInStockEntity.getInStockType();
                TaskMessageVo entity = new TaskMessageVo();
                if (ApplyTypeEnum.NEW_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setMessageType(PendTaskTypeEnum.NEW_IN.getCode());
                    entity.setTypeName(PendTaskTypeEnum.NEW_IN.getMessage());
                }
                else if (ApplyTypeEnum.TRANSFER_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setMessageType(PendTaskTypeEnum.TRANSFER_IN.getCode());
                    entity.setTypeName(PendTaskTypeEnum.TRANSFER_IN.getMessage());
                }
                else if (ApplyTypeEnum.BORROW_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setMessageType(PendTaskTypeEnum.BORROW_IN.getCode());
                    entity.setTypeName(PendTaskTypeEnum.BORROW_IN.getMessage());
                }
                else if (ApplyTypeEnum.RETURN_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setMessageType(PendTaskTypeEnum.RETURN_IN.getCode());
                    entity.setTypeName(PendTaskTypeEnum.RETURN_IN.getMessage());
                }
                else if (ApplyTypeEnum.USE_IN.getCode().equalsIgnoreCase(applyType))
                {
                    entity.setMessageType(PendTaskTypeEnum.USE_IN.getCode());
                    entity.setTypeName(PendTaskTypeEnum.USE_IN.getMessage());


                }
                entity.setSourceId(stockInStockEntity.getInStockId());
                entity.setUserKey(stockInStockEntity.getUserKey());
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
                    entity.setReceiveId(userId);
                    entity.setContent("您有一条待入库的工单，请关注数字运维微信公众号进行处理！");
                    this.insertTaskMessage(entity);
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
     * 维修的任务
     *
     * @param repairInfoVO
     * @author：郝伟州
     * @date：2018年9月27日14:12:56
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRepairTaskMessage(RepairApplyInfoVO repairInfoVO) throws Exception
    {
        try
        {
            TaskMessageVo entity = new TaskMessageVo();
            //获取维修主表信息
            Map<String, Object> applyInfo = repairApplyDao.getApplyInfoById(repairInfoVO.getMaintenanceApplyId());
            if(StringUtils.isNotEmpty(applyInfo))
            {
                repairInfoVO.setApplyNO(DataSwitch.convertObjectToString(applyInfo.get("applyNO")));
                repairInfoVO.setCreateUserId(DataSwitch.convertObjectToLong(applyInfo.get("createUserId")));
                repairInfoVO.setMaintenanceUserID(DataSwitch.convertObjectToLong(applyInfo.get("maintenanceUserID")));
                repairInfoVO.setAppointUserId(DataSwitch.convertObjectToLong(applyInfo.get("appointUserId")));
                repairInfoVO.setStationId(DataSwitch.convertObjectToLong(applyInfo.get("stationId")));
            }
            entity.setSourceId(repairInfoVO.getMaintenanceApplyId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setUserKey(repairInfoVO.getUserKey());
            if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.toBeDispatch.getValue()))//待派单
            {
                entity.setContent("您有一条待派单的工单，请关注数字运维微信公众号进行处理！");
                entity.setMessageType(PendTaskTypeEnum.TO_BE_DISPATCH.getCode());
                entity.setTypeName(PendTaskTypeEnum.TO_BE_DISPATCH.getMessage());
                PendingTaskFindEntity findEntity = new PendingTaskFindEntity();
                findEntity.setPermissionCode("'bxpg','wxgl'");//报修派工节点权限
                findEntity.setStationId(DataSwitch.convertObjectToString(repairInfoVO.getStationId()));
                List<Map<String, Object>> daipaidanUserList = pendingTaskDao.getPaiDuanRenYuanList(findEntity);
                if (daipaidanUserList != null)
                {
                    //假如自己就是保修员，就不发任务；其它有派单权限的人全部都发
                    for (Map<String, Object> daipaidanUserMap : daipaidanUserList)
                    {
                        entity.setReceiveId(DataSwitch.convertObjectToLong(daipaidanUserMap.get("userId")));
                        this.insertTaskMessage(entity);
                    }
                }
                return;
            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.toBeRepair.getValue()))//待接修
            {
                entity.setContent("您有一条待接修的工单，请关注数字运维微信公众号进行处理！");
                entity.setMessageType(PendTaskTypeEnum.TO_BE_REPAIR.getCode());
                entity.setTypeName(PendTaskTypeEnum.TO_BE_REPAIR.getMessage());
                entity.setReceiveId(repairInfoVO.getMaintenanceUserID());
            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.repaired.getValue()))//待评价
            {
                entity.setContent("您有一条待评价的工单，请关注数字运维微信公众号进行处理！");
                entity.setMessageType(PendTaskTypeEnum.TO_BE_EVALUATE.getCode());
                entity.setTypeName(PendTaskTypeEnum.TO_BE_EVALUATE.getMessage());
                entity.setReceiveId(repairInfoVO.getCreateUserId());
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
//                TaskMessageVo taskMessageVo=new TaskMessageVo();
//                taskMessageVo.setReceiveId(repairInfoVO.getAppointUserId());
//                taskMessageVo.setContent(String.format("您指派申请单【单号：%s】未修复，请关注数字运维微信公众号进行处理！",repairInfoVO.getApplyNO()) );
//                taskMessageVo.setSendUrl(false);
//                taskMessageVo.setSourceId(repairInfoVO.getMaintenanceApplyId());
//                taskMessageVo.setTypeName(PendTaskTypeEnum.NO_REPAIR.getMessage());
//                taskMessageVo.setMessageType(PendTaskTypeEnum.NO_REPAIR.getCode());
//                //发送微信消息或者短信
//                this.insertTaskMessage(taskMessageVo);

                entity.setContent("您有一条待派单的工单，请关注数字运维微信公众号进行处理！");
                entity.setMessageType(PendTaskTypeEnum.TO_BE_DISPATCH.getCode());
                entity.setTypeName(PendTaskTypeEnum.TO_BE_DISPATCH.getMessage());
                PendingTaskFindEntity findEntity = new PendingTaskFindEntity();
                findEntity.setPermissionCode("'bxpg','wxgl'");//报修派工节点权限
                findEntity.setStationId(DataSwitch.convertObjectToString(repairInfoVO.getStationId()));
                List<Map<String, Object>> daipaidanUserList = pendingTaskDao.getPaiDuanRenYuanList(findEntity);
                if (daipaidanUserList != null)
                {
                    //假如自己就是保修员，就不发任务；其它有派单权限的人全部都发
                    for (Map<String, Object> daipaidanUserMap : daipaidanUserList)
                    {
                        entity.setReceiveId(DataSwitch.convertObjectToLong(daipaidanUserMap.get("userId")));
                        this.insertTaskMessage(entity);
                    }
                }
                return;

            }
            else if (repairInfoVO.getApplyStatus().equals(RepairApplyStatusEnum.maintenance.getValue()))//待录入维修结果
            {
                //                entity.setMessageType(PendTaskTypeEnum.TO_BE_INPUT_RESULT.getCode());
                //                entity.setReceiveId(repairInfoVO.getMaintenanceUserID());
                //                entity.setContent("您有一条待录入维修结果的工单，请关注数字运维微信公众号进行处理！");
                return;
            }
            this.insertTaskMessage(entity);

        }
        catch (Exception ex)
        {
            throw ex;
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
    public void insertInventoryTaskMessage(StockInventoryTaskEntity entitytask) throws Exception
    {
        try
        {
            TaskMessageVo entity = new TaskMessageVo();
            entity.setSourceId(entitytask.getInventoryTaskId());
            entity.setStatus(PendTaskStatus.TO_BE_READ.getCode());
            entity.setUserKey(entitytask.getUserKey());
            entity.setContent("您有一条待盘库的任务，点击进行操作！");
            entity.setMessageType(InventoryTaskEnum.BEFORE.getCode());
            entity.setTypeName(InventoryTaskEnum.BEFORE.getValue());
            entity.setReceiveId(entitytask.getHeadPersonUserId());
            this.insertTaskMessage(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}


