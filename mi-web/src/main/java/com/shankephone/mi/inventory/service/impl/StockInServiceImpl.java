package com.shankephone.mi.inventory.service.impl;

import com.shankephone.mi.common.enumeration.*;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.dao.StockInDao;
import com.shankephone.mi.inventory.formbean.StockInFindEntity;
import com.shankephone.mi.inventory.service.ApplyInfoService;
import com.shankephone.mi.inventory.service.StockInService;
import com.shankephone.mi.inventory.vo.StockInStockDetailVO;
import com.shankephone.mi.inventory.vo.StockInVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.repair.service.MySparePartService;
import com.shankephone.mi.stock.dao.QRCodeDao;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 入库单Service
 *
 * @author 司徒彬
 * @date 2018 /7/17 10:15
 */
@Service
public class StockInServiceImpl implements StockInService
{

    @Autowired
    private StockInDao stockInDao;

    @Autowired
    private TaskMessageService taskMessageService;
    @Autowired
    private ApplyInfoService stockOperationService;

    @Override
    public Pager getPagerInfo(StockInFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = stockInDao.getPagerInfo(findEntity);
            int total = stockInDao.getPagerInfoTotal(findEntity);

            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getStockInInfoById(Long inStockId)
    {
        try
        {
            Map<String, Object> inStockInfo = stockInDao.getStockInInfoById(inStockId);

            List<Long> inStockDetailIds;
            if (DataSwitch.convertObjectToString(inStockInfo.get("inStockType")).equals(InStockTypeEnum.NEW_IN.getValue()))
            {
                inStockInfo.put("splitDetails", stockInDao.getNewStockInDetailByInStockId(inStockId));
            }
            else
            {
                List<Map<String, Object>> details = stockInDao.getStockInDetailByInStockIdAlreadyIn(inStockId);
                if (details.size() == 0)
                {
                    details = stockInDao.getStockInDetailByInStockId(inStockId);
                }
                inStockDetailIds = details.stream().map(map -> DataSwitch.convertObjectToLong(map.get("inStockDetailId"))).collect(Collectors.toList());
                List<Map<String, Object>> splitDetails = new ArrayList<>();
                if (inStockDetailIds.size() > 0)
                {
                    String ids = StringUtils.listToString(inStockDetailIds);
                    splitDetails = stockInDao.getSplitDetailInfo(ids);
                }
                inStockInfo.put("splitDetails", splitDetails);
                inStockInfo.put("details", details);
            }


            return inStockInfo;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Autowired
    private ApplyInfoService applyInfoService;
    @Autowired
    private MySparePartService mySparePartService;
    @Autowired
    private PendingTaskService pendingTaskService;
    @Autowired
    private QRCodeDao qrCodeDao;

    /**
     * Commit stock in.  所有入库都走这个
     *
     * @param stockInVO the stock in vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void commitStockIn(StockInVO stockInVO) throws Exception
    {
        try
        {

            //判断是否已入库
            Map<String, Object> inStockInfo = stockInDao.getStockInInfoById(stockInVO.getInStockId());
            if(StringUtils.isNotEmpty(inStockInfo)&&InStockStatusEnum.ALREADIN.getValue().equals(DataSwitch.convertObjectToString(inStockInfo.get("inStockStatus"))))
            {
                throw new Exception("您的入库单已经是入库完成状态！");
            }

            //首先验证二维码是否可以用
            int index = 0;
            List<StockInStockDetailVO> detailEntities = stockInVO.getDetailEntities();
            for (StockInStockDetailVO detailEntity : detailEntities)
            {
                Integer inStockAcount = detailEntity.getAlreadyInCount();
                for (int i = 0; i < inStockAcount; i++)
                {
                    if (StringUtils.isNotEmpty(detailEntity.getQrCode()))//新品入库和不输入二维码的不做验证
                    {
                        //验证二维码是否存在

                        QRCodeFindEntity findEntity=new QRCodeFindEntity();
                        findEntity.setQrCode(detailEntity.getQrCode());
                        findEntity.setUserKey(stockInVO.getUserKey());

                        List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
                        if (entities.size() == 0)
                        {
                            throw new Exception("第" + (index + 1) + "行二维码无效！");
                        }
                        else
                        {
                            Integer qcCount = stockInDao.getQrCodeInStockId(detailEntity);
                            if (qcCount > 0)
                            {
                                throw new Exception("第" + (index + 1) + "行二维码已经绑定到其他设备！");
                            }
                            qcCount = stockInDao.getQrCodeOnOperationsEquipment(detailEntity);
                            if (qcCount > 0)
                            {
                                throw new Exception("第" + (index + 1) + "行二维码已经绑定到其他设备！");
                            }

                            if(DataSwitch.convertObjectToLong(entities.get(0).getSparePartId())>0)//判断备件类型是否一致
                            {
                                StockInStockDetailVO findentity =new StockInStockDetailVO();
                                List<Long> spartIds=new ArrayList<>();
                                spartIds.add(entities.get(0).getSparePartId());
                                spartIds.add(detailEntity.getSparePartId());
                                findentity.setSparePartIds(spartIds);
                                List<Map<String,Object>> spartTypeList= stockInDao.getSpartTypeList(findentity);
                                if(spartTypeList!=null&&spartTypeList.size()>1)
                                {
                                    throw new Exception("第" + (index + 1) + "行二维码不能绑定其它类型的备件！");
                                }
                            }

                        }
                    }
                }
                index++;
            }
            /**
             * 1、根据唯一标识分组
             * 2、唯一标识，全部为insert新数据
             * 3、非唯一标识，根据货架Id，备件Id，设备状态，厂商Id、库存类型查询是否有库存信息
             *  a、有库存信息，更新数量
             *  b、没有库存信息，添加新数据
             */

            //更新入库申请单状态为已入库
            stockInDao.updateStockInStatus(stockInVO.getInStockId());
            stockInDao.updateStockInEntity(stockInVO);

            //更新对应申请单为over
            Long applyId = stockInVO.getApplyId();
            if (ObjectUtils.isNotEmpty(applyId))
            {
                applyInfoService.updateApplyStatus(applyId, ApplyStatusEnum.OVER_APPLY);
            }

            boolean isInsert = false;
            //处理详情
            index = 0;

            for (StockInStockDetailVO detailEntity : detailEntities)
            {
                StockStockEntity stockEntity = null;
                /**
                 * 唯一标识备件 每次入库都是按照数量为1计算，即 入库十条 ，添加十条库存记录，十条库存流水
                 */
                if (detailEntity.getInventoryType().equalsIgnoreCase(InventoryTypeEnum.UNIQUE.getCode()))
                {
                    Integer inStockAcount = detailEntity.getAlreadyInCount();
                    for (int i = 0; i < inStockAcount; i++)
                    {
                        detailEntity.setAlreadyInCount(1);
                        stockEntity = getNewStockEntity(stockInVO, detailEntity);
                        stockInDao.insertStockInfo(stockEntity);
                        //如果返还入库有二维码需要更新二维码状态是已使用
                        if (StringUtils.isNotEmpty(stockEntity.getQrCode()))
                        {
                            StockQrCodeEntity stockQrCodeEntity = new StockQrCodeEntity();
                            stockQrCodeEntity.setQrCode(stockEntity.getQrCode());
                            stockQrCodeEntity.setSparePartId(stockEntity.getSparePartId());
                            stockQrCodeEntity.setStockId(stockEntity.getStockId());
                            qrCodeDao.updateByQrCode(stockQrCodeEntity);
                        }
                        StockFlowLogEntity stockLogEntity = this.getStockLogEntity(stockEntity, stockInVO, detailEntity);
                        stockInDao.insertStockLog(stockLogEntity);
                        //添加入库库存关系表
                        StockAndStockEntity stockAndStockEntity = new StockAndStockEntity();
                        stockAndStockEntity.setInStockDetailId(detailEntity.getInStockDetailId());
                        stockAndStockEntity.setStockId(stockEntity.getStockId());
                        stockInDao.insertStockAndStock(stockAndStockEntity);
                        continue;

                    }
                    index++;
                }
                else
                {
                    StockInStockDetailVO StockInStockDetailVO =new StockInStockDetailVO();
                    StockInStockDetailVO.setSparePartId(detailEntity.getSparePartId());
                    StockInStockDetailVO.setSupplierId(detailEntity.getSupplierId());
                    StockInStockDetailVO.setStatus(detailEntity.getStatus());
                    StockInStockDetailVO.setInWarehouseId(stockInVO.getInWarehouseId());
                    StockInStockDetailVO.setGoodsShelvesId(detailEntity.getGoodsShelvesId());
                    StockInStockDetailVO.setInventoryType(detailEntity.getInventoryType());

                    stockEntity = stockInDao.getStockInfo(StockInStockDetailVO);
                    if (ObjectUtils.isNull(stockEntity))
                    {
                        isInsert = true;
                        stockEntity = getNewStockEntity(stockInVO, detailEntity);
                    }
                    else
                    {
                        isInsert = false;
                        stockEntity.setAccount(stockEntity.getAccount() + detailEntity.getAlreadyInCount());
                    }
                    if (isInsert)
                    {
                        stockInDao.insertStockInfo(stockEntity);
                    }
                    else
                    {
                        stockInDao.updateStockInfo(stockEntity);
                    }

                    StockFlowLogEntity stockLogEntity = this.getStockLogEntity(stockEntity, stockInVO, detailEntity);
                    stockInDao.insertStockLog(stockLogEntity);
                    //添加入库库存关系表
                    StockAndStockEntity stockAndStockEntity = new StockAndStockEntity();
                    stockAndStockEntity.setInStockDetailId(detailEntity.getInStockDetailId());
                    stockAndStockEntity.setStockId(stockEntity.getStockId());
                    stockInDao.insertStockAndStock(stockAndStockEntity);
                }


            }
            //更新我的设备表里面的数量为0
            if (stockInVO.getInStockType().equals(InStockTypeEnum.USE_IN.getValue()))
            {
                mySparePartService.updateMySqparePartCount(stockInVO.getApplyId());
            }
            if (stockInVO.getInStockType().equals(InStockTypeEnum.USE_IN.getValue()))
            {
                StockBusinessApplyEntity stockBusinessApplyEntity = stockOperationService.getApplyInfoByApplyId(applyId);
                TaskMessageVo taskMessageVo = new TaskMessageVo();
                taskMessageVo.setReceiveId(stockBusinessApplyEntity.getApplyUserId());
                taskMessageVo.setContent(String.format("您的领用申请【单号：%s】归还完毕！", stockBusinessApplyEntity.getApplyNo()));
                taskMessageVo.setSendUrl(false);
                taskMessageVo.setSourceId(stockInVO.getInStockId());
                taskMessageVo.setTypeName("归还入库");
                taskMessageVo.setMessageType(InStockTypeEnum.RETURN_IN.getValue());
                //发送微信消息或者短信
                taskMessageService.insertTaskMessage(taskMessageVo);
            }


            //删除之前任务
            TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
            taskEntity.setUserKey(stockInVO.getUserKey());
            taskEntity.setBusiNo(stockInVO.getInStockApplyNo());
            pendingTaskService.updateTaskOver(taskEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    private StockStockEntity getNewStockEntity(StockInVO stockInVO, StockInStockDetailVO detailEntity)
    {
        StockStockEntity stockEntity = new StockStockEntity();
        stockEntity.setOperationSubjectId(stockInVO.getOperationSubjectId());
        stockEntity.setInStockDetailId(detailEntity.getInStockDetailId());
        stockEntity.setWarehouseId(stockInVO.getInWarehouseId());
        stockEntity.setSupplierId(detailEntity.getSupplierId());
        stockEntity.setGoodsShelvesId(detailEntity.getGoodsShelvesId());
        stockEntity.setSparePartId(detailEntity.getSparePartId());
        stockEntity.setInDate(DateUtils.getNow());
        stockEntity.setEquipmentNO(detailEntity.getEquipmentNO());
        stockEntity.setSerialNumber(detailEntity.getSerialNumber());
        stockEntity.setAccount(detailEntity.getAlreadyInCount());
        stockEntity.setPrice(detailEntity.getPrice());
        stockEntity.setInventoryType(detailEntity.getInventoryType());
        stockEntity.setStatus(detailEntity.getStatus());
        stockEntity.setRemark(detailEntity.getRemark());
        stockEntity.setCreateUser(stockInVO.getOperationUserName());
        stockEntity.setModifyUser(stockInVO.getOperationUserName());
        stockEntity.setQrCode(detailEntity.getQrCode());

        return stockEntity;
    }


    private StockFlowLogEntity getStockLogEntity(StockStockEntity stockEntity, StockInVO stockInVO, StockInStockDetailVO detailEntity)
    {
        //添加流水
        StockFlowLogEntity logEntity = new StockFlowLogEntity();
        logEntity.setStockId(stockEntity.getStockId());
        logEntity.setSparePartId(stockEntity.getSparePartId());
        logEntity.setWarehouseId(stockEntity.getWarehouseId());
        logEntity.setType(stockInVO.getInStockType());
        logEntity.setCount(detailEntity.getAlreadyInCount());
        logEntity.setSourceId(detailEntity.getInStockDetailId());
        logEntity.setCreateUser(stockEntity.getOperationUserName());
        logEntity.setModifyUser(stockEntity.getOperationUserName());
        return logEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNewInApply(StockInVO stockInVO) throws Exception
    {
        try
        {
            /**
             * 1、新品入库，添加 入库申请单以及入库申请单详情
             * 2、调用确认入库方法 直接入库
             */
            stockInVO.setInStockApplyNo(NumberFactory.getInStockApplyNO(ApplyNumberPrefixEnum.NEW_IN.getCode()));
            stockInDao.insertInStockApply(stockInVO);

            Long inStockId = stockInVO.getInStockId();
            List<StockInStockDetailVO> detailEntities = stockInVO.getDetailEntities();
            for (StockInStockDetailVO detailEntity : detailEntities)
            {
                detailEntity.setInStockAcount(detailEntity.getAlreadyInCount());
                detailEntity.setInStockId(inStockId);
                stockInDao.insertInStockApplyDetail(detailEntity);
            }

            commitStockIn(stockInVO);

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


}
