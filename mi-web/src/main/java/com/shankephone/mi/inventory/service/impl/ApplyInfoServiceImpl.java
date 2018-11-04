package com.shankephone.mi.inventory.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.ApplyNumberPrefixEnum;
import com.shankephone.mi.common.enumeration.ApplyStatusEnum;
import com.shankephone.mi.common.enumeration.ApplyTypeEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.dao.ApplyInfoDao;
import com.shankephone.mi.inventory.formbean.StockOperationFindEntity;
import com.shankephone.mi.inventory.service.ApplyInfoService;
import com.shankephone.mi.inventory.vo.ApplyInfoVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.stock.dao.OutStockApplyDao;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 库存操作Service实现类
 *
 * @author 司徒彬
 * @date 2018 /7/12 09:56
 */
@Service
public class ApplyInfoServiceImpl implements ApplyInfoService
{

    @Autowired
    ApplyInfoDao applyInfoDao;

    @Override
    public Pager getPagerInfoByUser(StockOperationFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = applyInfoDao.getPagerInfoByUser(findEntity);
            int total = applyInfoDao.getPagerInfoByUserTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
    @Override
    public Pager getPagerInfoByWarehouse(StockOperationFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = applyInfoDao.getPagerInfoByWarehouse(findEntity);
            int total = applyInfoDao.getPagerInfoByWarehouseTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    public Pager getPagerInfoForAudit(StockOperationFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = applyInfoDao.getPagerInfoForAudit(findEntity);
            int total = applyInfoDao.getPagerInfoForAuditTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    public ApplyInfoVO getApplyInfoByApplyId(Long applyId)
    {
        try
        {
            ApplyInfoVO applyInfoVO = applyInfoDao.getApplyInfoByApplyId(applyId);
            List<Map<String, Object>> detailEntities = applyInfoDao.getApplyDetailInfoByApplyId(applyId);
            List<Map<String, Object>> sendEntitys = applyInfoDao.getApplySendOutDetail(applyId);
            detailEntities.stream().forEach(oneData -> {
                        if (!oneData.get("imageUrl").toString().equals("noImage"))
                        {
                            oneData.put("imageUrl", FdfsClient.getDownloadServer() + oneData.get("imageUrl").toString());
                        }
                        Optional<Map<String, Object>> result = sendEntitys.stream().filter(r -> (r.get("sparePartId").equals(oneData.get("sparePartId")) && (r.get("status").equals(oneData.get("stockStatus"))))).findFirst();
                        if (result.isPresent())
                        {
                            oneData.put("alreadyOutCount", result == null ? 0 : result.get().get("alreadyOutCount").toString());
                        }
                        else
                        {
                            oneData.put("alreadyOutCount", 0);
                        }
                    }
            );
            applyInfoVO.setDetails(detailEntities);
            applyInfoVO.setInfoList(getAllInfoList(applyId));
            return applyInfoVO;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private OutStockApplyDao outStockApplyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO auditApplyInfo(ApplyInfoVO applyEntity) throws Exception
    {
        try
        {
            ApplyInfoVO applyInfoVO = this.getApplyInfoByApplyId(applyEntity.getApplyId());
            if (!applyInfoVO.getApplyStatus().equalsIgnoreCase(ApplyStatusEnum.TO_BE_REVIEW.getCode()))
            {
                return ResultVOUtil.error(1, "只有单据为待审核状态时，才可进行审核通过或审核不通过操作!");
            }
            ApplyStatusEnum statusEnum = ApplyStatusEnum.REVIEW_PASS.getCode().equalsIgnoreCase(applyEntity.getApplyStatus())
                    ? ApplyStatusEnum.REVIEW_PASS : ApplyStatusEnum.REVIEW_NO_PASS;

            applyEntity.setAuditUser(applyEntity.getOperationUserName());
            applyEntity.setAuditTime(DateUtils.getNow());
            applyEntity.setAuditRemark(applyEntity.getAuditRemark());
            applyEntity.setAuditUserId(applyEntity.getOperationUserId());
            this.updateApplyStatus(applyEntity);
            //领用 调拨 返还
            /**
             * 领用，调拨，返还，审核通过后添加出库申请单
             */
            //添加库存申请单
            ApplyTypeEnum typeEnum;
            ApplyNumberPrefixEnum prefixEnum;
            String applyType = applyEntity.getApplyType();
            if (ApplyTypeEnum.USE.getCode().equalsIgnoreCase(applyType))
            {
                typeEnum = ApplyTypeEnum.USE_OUT;
                prefixEnum = ApplyNumberPrefixEnum.USE_OUT_NO;
            }
            else if (ApplyTypeEnum.TRANSFER.getCode().equalsIgnoreCase(applyType))
            {
                typeEnum = ApplyTypeEnum.TRANSFER_OUT;
                prefixEnum = ApplyNumberPrefixEnum.TRANSFER_OUT_NO;
            }
            else if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(applyType))
            {
                typeEnum = ApplyTypeEnum.RETURN_OUT;
                prefixEnum = ApplyNumberPrefixEnum.RETURN_OUT_NO;
            }
            else
            {
                throw new Exception(String.format("The apply type named $1 is not allowed", applyType));
            }
            if (statusEnum.equals(ApplyStatusEnum.REVIEW_PASS))
            {
                StockOutStockApplyEntity entity = new StockOutStockApplyEntity();
                entity.setApplyId(applyEntity.getApplyId());
                entity.setOutWarehouseId(applyEntity.getOutWarehouseId());
                entity.setOutOrderType(typeEnum.getCode());
                entity.setOutStockApplyNO(NumberFactory.getOutStockApplyNO(prefixEnum.getCode()));
                entity.setOutApplyStatus(ApplyStatusEnum.TO_BE_OUT.getCode());
                entity.setApplyDate(DateUtils.getNow());
                entity.setUserKey(applyEntity.getUserKey());
                outStockApplyDao.insertOutStockApply(entity);
                Long outStockApplyId = entity.getOutStockApplyId();
                List<StockBusinessApplyDetailEntity> detailEntities = applyEntity.getDetailEntities();
                for (StockBusinessApplyDetailEntity detailEntity : detailEntities)
                {
                    StockOutStockApplyDetailEntity outStockDetailEntity = new StockOutStockApplyDetailEntity();
                    outStockDetailEntity.setOutStockApplyId(outStockApplyId);
                    outStockDetailEntity.setSparePartId(detailEntity.getSparePartId());
                    outStockDetailEntity.setOutCount(detailEntity.getApplyCount());
                    outStockDetailEntity.setAlreadyOutCount(0);
                    outStockDetailEntity.setStockStatus(detailEntity.getStockStatus());
                    outStockApplyDao.insertOutStockApplyDetail(outStockDetailEntity);
                }
                //发任务
                pendingTaskService.insertOutWarehuseTask(entity);
                //发送微信或者短信
                taskMessageService.insertOutWarehuseTaskMessage(entity);
                //删除之前任务
                TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
                taskEntity.setUserKey(applyEntity.getUserKey());
                taskEntity.setBusiNo(applyEntity.getApplyNo());
                pendingTaskService.updateTaskOver(taskEntity);
                //TODO 发通知消息
            }
            else
            {

                TaskMessageVo taskMessageVo=new TaskMessageVo();
                taskMessageVo.setReceiveId(applyEntity.getApplyUserId());
                taskMessageVo.setContent(String.format("您的%s【单号：%s】审批未通过，请重新处理！",typeEnum.getMessage(),applyInfoVO.getApplyNo()) );
                taskMessageVo.setSendUrl(true);
                taskMessageVo.setSourceId(applyEntity.getApplyId());
                taskMessageVo.setTypeName("领用申请");
                taskMessageVo.setMessageType(ApplyStatusEnum.REVIEW_NO_PASS.getCode());//审批不通过微信跳转页面
                //发送微信消息或者短信
                taskMessageService.insertTaskMessage(taskMessageVo);

            }
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplyStatus(Long applyId, ApplyStatusEnum statusEnum)
    {
        try
        {
            StockBusinessApplyEntity applyEntity = new StockBusinessApplyEntity();
            applyEntity.setApplyId(applyId);
            applyEntity.setApplyStatus(statusEnum.getCode());
            updateApplyStatus(applyEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplyStatus(StockBusinessApplyEntity applyEntity)
    {
        try
        {
            applyInfoDao.updateApplyStatus(applyEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private PendingTaskService pendingTaskService;

    @Autowired
    private TaskMessageService taskMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addApplyInfo(ApplyInfoVO applyInfoVO) throws Exception
    {
        try
        {
            //领用 调拨 返还
            String applyType = applyInfoVO.getApplyType();
            String prefix;
            if (ApplyTypeEnum.USE.getCode().equalsIgnoreCase(applyType))
            {
                prefix = ApplyNumberPrefixEnum.USE_OUT_NO.getCode();
            }
            else if (ApplyTypeEnum.TRANSFER.getCode().equalsIgnoreCase(applyType))
            {
                prefix = ApplyNumberPrefixEnum.TRANSFER_OUT_NO.getCode();
            }
            else if (ApplyTypeEnum.RETURN.getCode().equalsIgnoreCase(applyType))
            {
                prefix = ApplyNumberPrefixEnum.RETURN_OUT_NO.getCode();
            }
            else
            {
                throw new Exception(String.format("The apply type named %s is not allowed", applyType));
            }
            applyInfoVO.setApplyNo(NumberFactory.getApplyNo(prefix));
            applyInfoVO.setApplyTime(DateUtils.getNow());
            applyInfoVO.setApplyStatus(ApplyStatusEnum.TO_BE_REVIEW.getCode());
            applyInfoDao.insertApplyInfo(applyInfoVO);
            Long applyId = applyInfoVO.getApplyId();
            List<StockBusinessApplyDetailEntity> detailEntities = applyInfoVO.getDetailEntities();
            for (StockBusinessApplyDetailEntity detailEntity : detailEntities)
            {
                detailEntity.setApplyId(applyId);
                applyInfoDao.insertApplyDetail(detailEntity);
            }
            //新增任务消息 赵亮
            pendingTaskService.insertApplyTask(applyInfoVO);
            //新增发送微信或者短信
            taskMessageService.insertApplyTaskMessage(applyInfoVO);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplyInfo(ApplyInfoVO applyInfoVO) throws Exception
    {
        try
        {
            Long applyId = applyInfoVO.getApplyId();
            applyInfoDao.updateApplyInfo(applyInfoVO);
            applyInfoDao.deleteApplyDetail(applyId);
            List<StockBusinessApplyDetailEntity> detailEntities = applyInfoVO.getDetailEntities();
            for (StockBusinessApplyDetailEntity detailEntity : detailEntities)
            {
                detailEntity.setApplyId(applyId);
                applyInfoDao.insertApplyDetail(detailEntity);
            }
            //先删除消息
            pendingTaskService.deleteApplyTask(applyInfoVO);
            //重新发任务消息
            pendingTaskService.insertApplyTask(applyInfoVO);
            //发送微信或者短信
            taskMessageService.insertApplyTaskMessage(applyInfoVO);

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteApplyInfo(Long applyId)
    {
        try
        {
            //删除任务
            ApplyInfoVO applyInfoVO = getApplyInfoByApplyId(applyId);
            pendingTaskService.deleteApplyTask(applyInfoVO);

            applyInfoDao.deleteApplyDetail(applyId);
            applyInfoDao.deleteApplyInfo(applyId);

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public List<Map<String, Object>> getAllInfoList(Long applyId)
    {
        List<Map<String, Object>> allInfoList = applyInfoDao.getAllInfoList(applyId);
        List<Map<String, Object>> infoList = new ArrayList<>();
        //申请人
        if (StringUtils.isNotEmpty(allInfoList.get(0).get("applyUser")))
        {
            Map<String, Object> newValue = new HashMap<>();
            newValue.put("user", allInfoList.get(0).get("applyUser"));
            newValue.put("time", allInfoList.get(0).get("applyTime"));
            newValue.put("remark", allInfoList.get(0).get("applyRemark"));
            newValue.put("type",  ApplyStatusEnum.TO_BE_REVIEW.getCode());//提交
            //newValue.put("type", "toBeReview");//提交
            infoList.add(newValue);
        }
        //审核人
        if (StringUtils.isNotEmpty(allInfoList.get(0).get("auditUser")))
        {
            Map<String, Object> newValue = new HashMap<>();
            newValue.put("user", allInfoList.get(0).get("auditUser"));
            newValue.put("time", allInfoList.get(0).get("auditTime"));
            newValue.put("remark", allInfoList.get(0).get("auditRemark"));

            if(DataSwitch.convertObjectToString(allInfoList.get(0).get("applyStatus")).equals(ApplyStatusEnum.REVIEW_NO_PASS.getCode()))
            {
                newValue.put("type", ApplyStatusEnum.REVIEW_NO_PASS.getCode());//审核不通过
            }
            else
            {
                newValue.put("type",ApplyStatusEnum.REVIEW_PASS.getCode());//审核通过
            }
            infoList.add(newValue);
        }
        //出库人
        if (StringUtils.isNotEmpty(allInfoList.get(0).get("outUser")))
        {
            Map<String, Object> newValue = new HashMap<>();
            newValue.put("user", allInfoList.get(0).get("outUser"));
            newValue.put("time", allInfoList.get(0).get("outDate"));
            newValue.put("remark", allInfoList.get(0).get("outRemark"));
            //newValue.put("type", "toBeIn");//已出库（待入库）
            newValue.put("type",ApplyStatusEnum.TO_BE_IN.getCode());//已出库（待入库）
            infoList.add(newValue);
        }
        //入库人
        if (StringUtils.isNotEmpty(allInfoList.get(0).get("inStockUser")))
        {
            Map<String, Object> newValue = new HashMap<>();
            newValue.put("user", allInfoList.get(0).get("inStockUser"));
            newValue.put("time", allInfoList.get(0).get("inDate"));
            newValue.put("remark", allInfoList.get(0).get("inRemark"));
            //newValue.put("type", "over");//已完成（已入库）
            newValue.put("type", ApplyStatusEnum.OVER_APPLY.getCode());//已完成（已入库）
            infoList.add(newValue);
        }
        return infoList;
    }

}
