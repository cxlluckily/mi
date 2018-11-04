package com.shankephone.mi.stock.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.*;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.dao.ApplyInfoDao;
import com.shankephone.mi.inventory.dao.StockInfoDao;
import com.shankephone.mi.inventory.service.ApplyInfoService;
import com.shankephone.mi.model.*;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.stock.dao.*;
import com.shankephone.mi.stock.enumeration.OutStockStatusEnum;
import com.shankephone.mi.stock.formbean.*;
import com.shankephone.mi.stock.service.OutStockApplyService;
import com.shankephone.mi.stock.vo.ApplyDetailVO;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-07-11 10:39
 */
@Service
public class OutStockApplyServiceImpl implements OutStockApplyService
{
    @Autowired
    private OutStockApplyDao outStockApplyDao;
    @Autowired
    private FLowLogDao fLowLogDao;
    @Autowired
    private InStockDao inStockDao;
    @Autowired
    private TaskMessageService taskMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<Map<String, Object>> getOutStockApplyInfo(OutStockApplyFindEntity findEntity)
    {
        try
        {
            Integer total = outStockApplyDao.getOutStockApplyInfoCount(findEntity);
            List<Map<String, Object>> sysUserEntities = outStockApplyDao.getOutStockApplyInfo(findEntity);
            Pager pager = new Pager<>(total, sysUserEntities);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private ApplyInfoDao applyInfoDao;

    @Override
    public ApplyDetailVO getApplyDetailInfoByoutStockApplyId(Long outStockApplyId)
    {
        try
        {
            ApplyDetailVO applyDetailVO = new ApplyDetailVO();
            applyDetailVO.setApplyEntity(outStockApplyDao.getStockOutStockApplyEntity(outStockApplyId));
            applyDetailVO.setStockBusinessApplyEntity(applyInfoDao.getApplyInfoByApplyId(applyDetailVO.getApplyEntity().getApplyId()));

            List<Map<String, Object>> entientities1=outStockApplyDao.getApplyDetailInfoByoutStockApplyId(outStockApplyId);
            entientities1.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="";
                }
                map.put("imageUrl", sparePartPicUrl);
            });

            applyDetailVO.setOutStockApplyDetailEntities(entientities1);
            List<Map<String, Object>> entientities2=outStockApplyDao.getOutStockDetailByoutStockApplyId(outStockApplyId);
            entientities2.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="";
                }
                map.put("imageUrl", sparePartPicUrl);
            });

            applyDetailVO.setOutStockDetailEntities(entientities2);
            return applyDetailVO;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private ApplyInfoService stockOperationService;
    @Autowired
    private PendingTaskService pendingTaskService;
    @Autowired
    private UserDeviceDao userDeviceDao;
    @Autowired
    private StockInfoDao stockInfoDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized ResultVO useAndTransfeOutStockAndReturn(UseAndTransfeOutStockBusiEntity busiEntity) throws Exception
    {
        try
        {
            //验证库存状态是否是待出库
            StockOutStockApplyEntity applyEntity = outStockApplyDao.getStockOutStockApplyEntity(busiEntity.getApplyEntity().getOutStockApplyId());
            if (!applyEntity.getOutApplyStatus().equals(ApplyStatusEnum.TO_BE_OUT.getCode()))
            {
                return ResultVOUtil.error(ResultEnum.STOCK_OUT_APPLY_STATUS_ERROR.getCode(), ResultEnum.STOCK_OUT_APPLY_STATUS_ERROR.getMessage());
            }
            StockBusinessApplyEntity stockBusinessApplyEntity = stockOperationService.getApplyInfoByApplyId(busiEntity.getApplyEntity().getApplyId());
            //更新出库申请单的状态、出库人、时间等信息
            UserLoginInfo userInfo = SessionMap.getUserInfo(busiEntity.getUserKey());
            busiEntity.getApplyEntity().setOutDate(DateUtils.getNow());
            busiEntity.getApplyEntity().setOutUser(userInfo.getRealName());
            busiEntity.getApplyEntity().setOutUserId(userInfo.getUserId());
            busiEntity.getApplyEntity().setOutApplyStatus(OutStockStatusEnum.ALREADY_OUT.getCode());
            busiEntity.getApplyEntity().setOutDate(DateUtils.getNow());
            outStockApplyDao.outStockUpdateOutApply(busiEntity.getApplyEntity());
            Map<Long, Long> sparePartIdMap = new HashMap<>();
            //循环更新出库申请详情表的已出库数量
            for (StockOutStockApplyDetailEntity applyDetailEntity : busiEntity.getOutStockApplyDetailEntities())
            {
                outStockApplyDao.outStockUpdateApplyDetail(applyDetailEntity);
                sparePartIdMap.put(applyDetailEntity.getOutStockApplyDetailId(), applyDetailEntity.getSparePartId());
            }
            Map<Long, Long> stockIdMap = new HashMap<>();
            //循环新增库存出库详情表，并且减库存,写日志
            for (StockOutStockDetailEntity outStockDetailEntity : busiEntity.getOutStockDetailEntities())
            {
                //验证库存，如果库存不够则抛出异常
                Integer canOutCount = outStockApplyDao.getCanOutCountBystockId(outStockDetailEntity.getStockId());
                if (canOutCount < outStockDetailEntity.getOutCount())
                {
                    throw new Exception("库存不足");
                }
                stockIdMap.put(outStockDetailEntity.getOutStockApplyDetailId(), outStockDetailEntity.getStockId());
                //更新库存
                outStockApplyDao.outStockUpdateStock(outStockDetailEntity);
                //新增库存出库详情表
                outStockApplyDao.insertStockOutStockDetail(outStockDetailEntity);
                //新增库存流水表
                StockFlowLogEntity stockFlowLogEntity = new StockFlowLogEntity();
                stockFlowLogEntity.setCount(outStockDetailEntity.getOutCount());
                stockFlowLogEntity.setWarehouseId(applyEntity.getOutWarehouseId());
                stockFlowLogEntity.setSourceId(applyEntity.getOutStockApplyId());
                stockFlowLogEntity.setFlowDescribe(applyEntity.getOutOrderType());
                stockFlowLogEntity.setSparePartId(sparePartIdMap.get(outStockDetailEntity.getOutStockApplyDetailId()));
                stockFlowLogEntity.setStockId(outStockDetailEntity.getStockId());
                stockFlowLogEntity.setType(applyEntity.getOutOrderType());
                fLowLogDao.insertStockFlowLog(stockFlowLogEntity);
                if (busiEntity.getApplyEntity().getOutOrderType().equals(ApplyTypeEnum.USE_OUT.getCode()))
                {
                    //添加人员持有设备
                    StockStockEntity stockStockEntity = stockInfoDao.getStockInfoById(outStockDetailEntity.getStockId());
                    StockUserDeviceEntity stockUserDeviceEntity = new StockUserDeviceEntity();
                    stockUserDeviceEntity.setCount(outStockDetailEntity.getOutCount());
                    stockUserDeviceEntity.setStockId(outStockDetailEntity.getStockId());
                    stockUserDeviceEntity.setUserId(stockBusinessApplyEntity.getApplyUserId());
                    stockUserDeviceEntity.setSparePartId(stockStockEntity.getSparePartId());
                    stockUserDeviceEntity.setUserKey(busiEntity.getUserKey());
                    userDeviceDao.insertOne(stockUserDeviceEntity);
                }

            }

            //1、添加入库申请单主表
            StockInStockEntity stockInStockEntity = new StockInStockEntity();
            stockInStockEntity.setUserKey(busiEntity.getUserKey());
            stockInStockEntity.setApplyId(busiEntity.getApplyEntity().getApplyId());
            stockInStockEntity.setOutStockApplyNO(applyEntity.getOutStockApplyNO());
            //如果是返还
            if (busiEntity.getApplyEntity().getOutOrderType().equals(ApplyTypeEnum.RETURN_OUT.getCode()))
            {
                stockInStockEntity.setInWarehouseId(stockBusinessApplyEntity.getInWarehouseId());
                stockInStockEntity.setInStockType(ApplyTypeEnum.RETURN_IN.getCode());
                stockInStockEntity.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.RETURN_IN_NO.getCode()));
            }
            else if (busiEntity.getApplyEntity().getOutOrderType().equals(ApplyTypeEnum.TRANSFER_OUT.getCode()))//调拨
            {
                stockInStockEntity.setInWarehouseId(stockBusinessApplyEntity.getInWarehouseId());
                stockInStockEntity.setInStockType(ApplyTypeEnum.TRANSFER_IN.getCode());
                stockInStockEntity.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.TRANSFER_IN_NO.getCode()));
            }
            else if (busiEntity.getApplyEntity().getOutOrderType().equals(ApplyTypeEnum.USE_OUT.getCode()))//领用出库
            {
                stockInStockEntity.setInWarehouseId(stockBusinessApplyEntity.getOutWarehouseId());
                stockInStockEntity.setInStockType(ApplyTypeEnum.USE_IN.getCode());
                stockInStockEntity.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.USE_IN_NO.getCode()));
            }
            stockInStockEntity.setInStockStatus(ApplyStatusEnum.TO_BE_IN.getCode());
            inStockDao.insertInStock(stockInStockEntity);
            //2、添加入库详单
            for (StockOutStockDetailEntity outStockDetailEntity : busiEntity.getOutStockDetailEntities())
            {
                StockInStockDetailEntity stockInStockDetailEntity = new StockInStockDetailEntity();
                stockInStockDetailEntity.setUserKey(busiEntity.getUserKey());
                stockInStockDetailEntity.setInStockId(stockInStockEntity.getInStockId());
                stockInStockDetailEntity.setSparePartId(sparePartIdMap.get(outStockDetailEntity.getOutStockApplyDetailId()));
                stockInStockDetailEntity.setInStockAcount(outStockDetailEntity.getOutCount());
                stockInStockDetailEntity.setStockId(outStockDetailEntity.getStockId());
                inStockDao.insertInStockDetail(stockInStockDetailEntity);
            }
            stockOperationService.updateApplyStatus(busiEntity.getApplyEntity().getApplyId(), ApplyStatusEnum.TO_BE_IN);
            //发送任务消息
            pendingTaskService.insertInWarehouseTask(stockInStockEntity);
            //发送微信或者短信
            taskMessageService.insertInWarehouseTaskMessage(stockInStockEntity);


            if (busiEntity.getApplyEntity().getOutOrderType().equals(ApplyTypeEnum.USE_OUT.getCode()))//领用出库
            {
                TaskMessageVo taskMessageVo=new TaskMessageVo();
                taskMessageVo.setReceiveId(stockBusinessApplyEntity.getApplyUserId());
                taskMessageVo.setContent(String.format("您的领用申请【单号：%s】，备件已出库，请及时领取！",stockBusinessApplyEntity.getApplyNo()) );
                taskMessageVo.setSendUrl(false);
                taskMessageVo.setSourceId(stockInStockEntity.getApplyId());
                taskMessageVo.setTypeName("领用申请");
                taskMessageVo.setMessageType(ApplyTypeEnum.USE_OUT.getCode());
                //发送微信消息或者短信
                taskMessageService.insertTaskMessage(taskMessageVo);
            }


            //删除之前任务
            TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
            taskEntity.setUserKey(applyEntity.getUserKey());
            taskEntity.setBusiNo(applyEntity.getOutStockApplyNO());
            pendingTaskService.updateTaskOver(taskEntity);
            //TODO 发通知消息
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getCanSendGoodsInfo(OutStockApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> canSendGoodsListMap=outStockApplyDao.getCanSendGoodsInfo(findEntity);
            canSendGoodsListMap.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="";
                }
                map.put("imageUrl", sparePartPicUrl);
            });
            return canSendGoodsListMap;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * @author：赵亮
     * @date：2018-07-12 11:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertBorrowOutStock(BorrowOutStockBusiEntity busiEntity) throws Exception
    {
        try
        {
            //初始化库存申请单表数据
            busiEntity.getApplyFormBean().setUserKey(busiEntity.getUserKey());
            StockOutStockApplyEntity applyEntity = getStockOutStockApplyEntity(busiEntity.getApplyFormBean());
            applyEntity.setRemark(busiEntity.getApplyFormBean().getRemark());
            String outDate=busiEntity.getApplyFormBean().getOutDate();
            if(StringUtils.isNotEmpty(outDate))
            {
                applyEntity.setOutDate(DateUtils.getDate(outDate));
            }
            else
            {
                applyEntity.setOutDate(DateUtils.getNow());
            }

            outStockApplyDao.insertOutStockApply(applyEntity);
            //初始化 出库申请详情 表数据
            List<Long> sparePartIds = busiEntity.getDetailFormBeans().stream().map(m -> m.getSparePartId()).distinct().collect(Collectors.toList());
            Map<Long, Long> stockDetailMap = new HashMap<>();

            for (Long sparePartId : sparePartIds)
            {
                List<OutStockApplyDetailFormBean> normal = busiEntity.getDetailFormBeans().stream().filter(m -> (m.getSparePartId().equals(sparePartId) && m.getStockStatus().equals(StockStatusEnum.NORMAL.getCode()))).collect(Collectors.toList());
                List<OutStockApplyDetailFormBean> bad = busiEntity.getDetailFormBeans().stream().filter(m -> (m.getSparePartId().equals(sparePartId) && m.getStockStatus().equals(StockStatusEnum.BAD.getCode()))).collect(Collectors.toList());
                StockOutStockApplyDetailEntity stockApplyDetailEntity = new StockOutStockApplyDetailEntity();
                if (normal.size() > 0)
                {
                    Integer outCount = normal.stream().filter(m -> m.getSparePartId().equals(sparePartId)).mapToInt(OutStockApplyDetailFormBean::getOutCount).sum();
                    stockApplyDetailEntity.setOutCount(outCount);
                    stockApplyDetailEntity.setOutStockApplyId(applyEntity.getOutStockApplyId());
                    stockApplyDetailEntity.setSparePartId(sparePartId);
                    stockApplyDetailEntity.setAlreadyOutCount(outCount);
                    stockApplyDetailEntity.setStockStatus(StockStatusEnum.NORMAL.getCode());
                    outStockApplyDao.insertOutStockApplyDetail(stockApplyDetailEntity);
                    stockDetailMap.put(sparePartId, stockApplyDetailEntity.getOutStockApplyDetailId());
                }
                if (bad.size() > 0)
                {
                    Integer outCount = bad.stream().filter(m -> m.getSparePartId().equals(sparePartId)).mapToInt(OutStockApplyDetailFormBean::getOutCount).sum();
                    stockApplyDetailEntity.setOutCount(outCount);
                    stockApplyDetailEntity.setOutStockApplyId(applyEntity.getOutStockApplyId());
                    stockApplyDetailEntity.setSparePartId(sparePartId);
                    stockApplyDetailEntity.setAlreadyOutCount(outCount);
                    stockApplyDetailEntity.setStockStatus(StockStatusEnum.NORMAL.getCode());
                    outStockApplyDao.insertOutStockApplyDetail(stockApplyDetailEntity);
                    stockDetailMap.put(sparePartId, stockApplyDetailEntity.getOutStockApplyDetailId());
                }
            }
            List<StockOutStockDetailEntity> outStockDetailEntities = new ArrayList<>();
            //库存出库详情 表数据
           // Map<Long, Long> stockIdMap = new HashMap<>();
            List<Long> stoctIdList=new ArrayList<>();
            for (OutStockApplyDetailFormBean detailFormBean : busiEntity.getDetailFormBeans())
            {
                StockOutStockDetailEntity outStockDetailEntity = new StockOutStockDetailEntity();
                outStockDetailEntity.setOutCount(detailFormBean.getOutCount());
                outStockDetailEntity.setStockId(detailFormBean.getStockId());
                outStockDetailEntity.setOutStockApplyDetailId(stockDetailMap.get(detailFormBean.getSparePartId()));
                outStockApplyDao.insertStockOutStockDetail(outStockDetailEntity);
                outStockDetailEntities.add(outStockDetailEntity);
                detailFormBean.setOutStockApplyDetailId(outStockDetailEntity.getOutStockApplyDetailId());
               // stockIdMap.put(outStockDetailEntity.getOutStockApplyDetailId(), outStockDetailEntity.getStockId());
                stoctIdList.add(outStockDetailEntity.getStockId());
            }
            //循环新增库存出库详情表，并且减库存,写日志
            for (StockOutStockDetailEntity outStockDetailEntity : outStockDetailEntities)
            {
                //验证库存，如果库存不够则抛出异常
                Integer canOutCount = outStockApplyDao.getCanOutCountBystockId(outStockDetailEntity.getStockId());
                if (canOutCount < outStockDetailEntity.getOutCount())
                {
                    throw new Exception("库存不足");
                }
                //更新库存
                outStockApplyDao.outStockUpdateStock(outStockDetailEntity);
                //新增库存流水表
                StockFlowLogEntity stockFlowLogEntity = new StockFlowLogEntity();
                stockFlowLogEntity.setCount(outStockDetailEntity.getOutCount());
                stockFlowLogEntity.setWarehouseId(applyEntity.getOutWarehouseId());
                stockFlowLogEntity.setStockId(outStockDetailEntity.getStockId());
                stockFlowLogEntity.setType(applyEntity.getOutOrderType());
                stockFlowLogEntity.setFlowDescribe(applyEntity.getOutOrderType());
                stockFlowLogEntity.setSourceId(0l);
                fLowLogDao.insertStockFlowLog(stockFlowLogEntity);
            }

            //添加待入库数据
            //1、添加入库申请单主表
            StockInStockEntity stockInStockEntity = new StockInStockEntity();
            stockInStockEntity.setUserKey(busiEntity.getUserKey());
            //根据主键返回实体
            stockInStockEntity.setInWarehouseId(applyEntity.getOutWarehouseId());
            stockInStockEntity.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.BORROW_IN_NO.getCode()));
            stockInStockEntity.setInStockType(ApplyTypeEnum.BORROW_IN.getCode());
            stockInStockEntity.setInStockStatus(ApplyStatusEnum.TO_BE_IN.getCode());
            stockInStockEntity.setOutStockApplyNO(applyEntity.getOutStockApplyNO());
            inStockDao.insertInStock(stockInStockEntity);
            //2、添加入库详单
            int index=0;
            for (OutStockApplyDetailFormBean stockApplyDetailEntity : busiEntity.getDetailFormBeans())
            {
                StockInStockDetailEntity stockInStockDetailEntity = new StockInStockDetailEntity();
                stockInStockDetailEntity.setUserKey(busiEntity.getUserKey());
                stockInStockDetailEntity.setInStockId(stockInStockEntity.getInStockId());
                stockInStockDetailEntity.setSparePartId(stockApplyDetailEntity.getSparePartId());
                stockInStockDetailEntity.setInStockAcount(stockApplyDetailEntity.getOutCount());
                //stockInStockDetailEntity.setStockId(stockIdMap.get(stockApplyDetailEntity.getOutStockApplyDetailId()));
                stockInStockDetailEntity.setStockId(stoctIdList.get(index++));
                stockInStockDetailEntity.setSparePartId(stockApplyDetailEntity.getSparePartId());
                inStockDao.insertInStockDetail(stockInStockDetailEntity);
            }
            stockOperationService.updateApplyStatus(applyEntity.getApplyId(), ApplyStatusEnum.TO_BE_IN);
            //发送任务消息
            pendingTaskService.insertInWarehouseTask(stockInStockEntity);
            //发送微信或者短信
            taskMessageService.insertInWarehouseTaskMessage(stockInStockEntity);
            //TODO 发通知消息
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * (暂时未用）
     *
     * @author：赵亮
     * @date：2018-07-27 13:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO returnOutStock(StockOutStockApplyEntity entity)
    {
        try
        {
            //            //验证库存状态是否是待出库
            //            StockOutStockApplyEntity applyEntity = outStockApplyDao.getStockOutStockApplyEntity(entity.getOutStockApplyId());
            //            if (!applyEntity.getOutApplyStatus().equals(ApplyStatusEnum.TO_BE_OUT))
            //            {
            //                return ResultVOUtil.error(ResultEnum.STOCK_OUT_APPLY_STATUS_ERROR.getCode(), ResultEnum.STOCK_OUT_APPLY_STATUS_ERROR.getMessage());
            //            }
            //            List<Map<String, Object>> outStockDetails = outStockApplyDao.getOutStockDetailByoutStockApplyId(entity.getOutStockApplyId());
            //            //循环新增库存出库详情表，并且减库存,写日志
            //            List<OutStockApplyDetailFormBean> outStockApplyDetailFormBeans = new ArrayList<>();
            //            for (Map<String, Object> oneEntity : outStockDetails)
            //            {
            //                StockOutStockDetailEntity outStockDetailEntity = new StockOutStockDetailEntity();
            //                outStockDetailEntity.setStockId(DataSwitch.convertObjectToLong(oneEntity.get("stockId")));
            //                outStockDetailEntity.setOutCount(DataSwitch.convertObjectToInteger(oneEntity.get("outCount")));
            //                outStockDetailEntity.setUserKey(entity.getUserKey());
            //                //验证库存，如果库存不够则抛出异常
            //                Integer canOutCount = outStockApplyDao.getCanOutCountBystockId(outStockDetailEntity.getStockId());
            //                if (canOutCount < outStockDetailEntity.getOutCount())
            //                {
            //                    return ResultVOUtil.error(ResultEnum.INVENTORY_SHORTAGE.getCode(), ResultEnum.INVENTORY_SHORTAGE.getMessage());
            //                }
            //                //更新库存
            //                outStockApplyDao.outStockUpdateStock(outStockDetailEntity);
            //                OutStockApplyDetailFormBean detailFormBean = new OutStockApplyDetailFormBean();
            //                detailFormBean.setOutStockApplyDetailId(DataSwitch.convertObjectToLong(oneEntity.get("outStockApplyDetailId")));
            //                detailFormBean.setOutCount(outStockDetailEntity.getOutCount());
            //                detailFormBean.setSparePartId(DataSwitch.convertObjectToLong(oneEntity.get("sparePartId")));
            //                detailFormBean.setStockId(outStockDetailEntity.getStockId());
            //                outStockApplyDetailFormBeans.add(detailFormBean);
            //                //新增库存流水表
            //                StockFlowLogEntity stockFlowLogEntity = new StockFlowLogEntity();
            //                stockFlowLogEntity.setWarehouseId(applyEntity.getOutWarehouseId());
            //                stockFlowLogEntity.setSourceId(applyEntity.getOutStockApplyId());
            //                stockFlowLogEntity.setFlowDescribe(entity.getOutApplyStatus());
            //                stockFlowLogEntity.setSparePartId(DataSwitch.convertObjectToLong(oneEntity.get("sparePartId")));
            //                stockFlowLogEntity.setCount(outStockDetailEntity.getOutCount());
            //                stockFlowLogEntity.setStockId(outStockDetailEntity.getStockId());
            //                stockFlowLogEntity.setType(entity.getOutOrderType());
            //                stockFlowLogEntity.setFlowDescribe("出库操作：" + entity.toString());
            //                fLowLogDao.insertStockFlowLog(stockFlowLogEntity);
            //            }
            //            //添加待入库数据
            //            //1、添加入库申请单主表
            //            StockInStockEntity stockInStockEntity = new StockInStockEntity();
            //            stockInStockEntity.setUserKey(entity.getUserKey());
            //            stockInStockEntity.setApplyId(applyEntity.getApplyId());
            //            //根据主键返回实体
            //            StockBusinessApplyEntity stockBusinessApplyEntity = stockOperationService.getApplyInfoByApplyId(stockInStockEntity.getApplyId());
            //            stockInStockEntity.setInWarehouseId(stockBusinessApplyEntity.getInWarehouseId());
            //            stockInStockEntity.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.RETURN_IN_NO.getCode()));
            //            stockInStockEntity.setInStockType(ApplyTypeEnum.RETURN_IN.getCode());
            //            stockInStockEntity.setInStockStatus(ApplyStatusEnum.TO_BE_IN.getCode());
            //            inStockDao.insertInStock(stockInStockEntity);
            //            //2、添加入库详单
            //            for (OutStockApplyDetailFormBean stockApplyDetailEntity : outStockApplyDetailFormBeans)
            //            {
            //                StockInStockDetailEntity stockInStockDetailEntity = new StockInStockDetailEntity();
            //                stockInStockDetailEntity.setUserKey(entity.getUserKey());
            //                stockInStockDetailEntity.setInStockId(stockApplyDetailEntity.getStockId());
            //                stockInStockDetailEntity.setSparePartId(stockApplyDetailEntity.getSparePartId());
            //                stockInStockDetailEntity.setInStockAcount(stockApplyDetailEntity.getOutCount());
            //                stockInStockDetailEntity.setStockId(stockApplyDetailEntity.getStockId());
            //                inStockDao.insertInStockDetail(stockInStockDetailEntity);
            //            }
            //            stockOperationService.updateApplyStatus(applyEntity.getApplyId(), ApplyStatusEnum.TO_BE_IN);
            return ResultVOUtil.success();

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getBindqrCodeStockInfo(BindQrCodeFindEntity findEntity)
    {
        try
        {
            findEntity.setInventoryType(InventoryTypeEnum.UNIQUE.getCode());
            return outStockApplyDao.getBindqrCodeStockInfo(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private QRCodeDao qrCodeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateStockQrCode(List<UpdateStockQrCodeFormBean> formBeans)
    {
        try
        {

            int index = 0;
            //验证该二维码是否可用
            for (UpdateStockQrCodeFormBean formBean : formBeans)
            {
                index++;
                if(StringUtils.isEmpty(formBean.getQrCode()))
                {
                    continue;
                }
                QRCodeFindEntity findEntity=new QRCodeFindEntity();
                findEntity.setQrCode(formBean.getQrCode());
                findEntity.setUserKey(formBean.getUserKey());

                List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
                if (entities.size() > 0)
                {
                    if (entities.get(0).getStockId() != null
                            && !formBean.getStockId().equals(entities.get(0).getStockId()))
                    {
                        return ResultVOUtil.error(201, "第" + index + "行二维码已经使用过！请您更换！");
                    }
                }
                else
                {
                    return ResultVOUtil.error(201, "第" + index + "行二维码无效！");
                }

            }
            for (UpdateStockQrCodeFormBean formBean : formBeans)
            {
                outStockApplyDao.updateStockQrCode(formBean);
                if(StringUtils.isEmpty(formBean.getQrCode()))
                {
                    continue;
                }
                //更新二维码
                outStockApplyDao.updateQrCode(formBean);
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
    public ResultVO updateStockOutQrCode(UpdateStockQrCodeFormBean formBean)
    {
        try
        {
            QRCodeFindEntity findEntity=new QRCodeFindEntity();
            findEntity.setQrCode(formBean.getQrCode());
            findEntity.setUserKey(formBean.getUserKey());
            //验证该二维码是否可用
            List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
            if (entities.size() > 0)
            {
                if (entities.get(0).getStockId() != null
                        && !formBean.getStockId().equals(entities.get(0).getStockId()))
                {
                    return ResultVOUtil.error(201, "二维码已经使用过！请您更换！");
                }
            }
            else
            {
                return ResultVOUtil.error(201, "二维码无效！");
            }
            outStockApplyDao.updateStockQrCode(formBean);
            //更新二维码
            outStockApplyDao.updateQrCode(formBean);


            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO getStockSparePartByqrCode(UpdateStockQrCodeFormBean formBean)
    {

        QRCodeFindEntity findEntity=new QRCodeFindEntity();
        findEntity.setQrCode(formBean.getQrCode());
        findEntity.setUserKey(formBean.getUserKey());
        //验证该二维码是否可用
        List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
        if (entities==null || entities.size() <1)
        {
            return ResultVOUtil.error(101, "二维码无效！");
        }

        Map<String,Object> stockOut=outStockApplyDao.getStockSparePartByqrCode(formBean);
        if(StringUtils.isEmpty(stockOut)||!stockOut.containsKey("qrCode"))//二维码未绑定
        {
            if(formBean.isCanBeUse()) {
                return ResultVOUtil.success("canbeuse");
            }
            return ResultVOUtil.error(201, "二维码未绑定！");
        }
        if(DataSwitch.convertObjectToLong(stockOut.get("account"))>0)
        {
            return ResultVOUtil.error(202, "二维码已绑定到其它设备！");
        }
        return ResultVOUtil.success(stockOut);
    }


    private StockOutStockApplyEntity getStockOutStockApplyEntity(OutStockApplyFormBean applyFormBean)
    {
        StockOutStockApplyEntity applyEntity = new StockOutStockApplyEntity();
        applyEntity.setUserKey(applyFormBean.getUserKey());
        applyEntity.setOperationUserId(applyFormBean.getOperationSubjectId());
        applyEntity.setOutWarehouseId(applyFormBean.getOutWarehouseId());
        applyEntity.setOutOrderType(ApplyTypeEnum.BORROW_OUT.getCode());
        applyEntity.setOutStockApplyNO(NumberFactory.getOutStockApplyNO(ApplyNumberPrefixEnum.BORROW_OUT_NO.getCode()));
        applyEntity.setOutApplyStatus(OutStockStatusEnum.ALREADY_OUT.getCode());
        applyEntity.setOutUser(applyFormBean.getOperationUserName());
        applyEntity.setOutUserId(applyFormBean.getOperationUserId());
        applyEntity.setApplyDate(DateUtils.getNow());
        applyEntity.setModifyUser(applyFormBean.getOperationUserName());
        return applyEntity;
    }
}
