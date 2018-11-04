package com.shankephone.mi.inventory.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.ApplyTypeEnum;
import com.shankephone.mi.common.enumeration.InventoryTypeEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.enumeration.StockStatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.inventory.dao.ShelvesDao;
import com.shankephone.mi.inventory.dao.StockInDao;
import com.shankephone.mi.inventory.dao.StockInfoDao;
import com.shankephone.mi.inventory.formbean.GetPagerDetailInfoVO;
import com.shankephone.mi.inventory.formbean.StockInfoFindEntity;
import com.shankephone.mi.inventory.service.StockInfoService;
import com.shankephone.mi.inventory.vo.StockInfoVO;
import com.shankephone.mi.model.StockFlowLogEntity;
import com.shankephone.mi.model.StockGoodsShelvesEntity;
import com.shankephone.mi.model.StockStockEntity;
import com.shankephone.mi.spacepart.dao.OperationsEquipmentDao;
import com.shankephone.mi.spacepart.dao.SparePartDao;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;
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
 * 库存管理实现类
 *
 * @author 司徒彬
 * @date 2018/7/26 20:04
 */
@Service
public class StockInfoServiceImpl implements StockInfoService
{
    @Autowired
    private ShelvesDao shelvesDao;

    @Autowired
    StockInfoDao stockInfoDao;

    @Autowired
    SparePartDao sparePartDao;
    @Autowired
    OperationsEquipmentDao operationsEquipmentDao;

    @Override
    public Pager getPagerInfo(StockInfoFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = stockInfoDao.getPagerInfo(findEntity);

            data.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if(StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl="";
                }
                map.put("imageUrl", sparePartPicUrl);
            });


            Integer total = stockInfoDao.getPagerInfoTotal(findEntity);

            Pager pager = new Pager(total, data);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager getPagerDetailInfo(StockInfoFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = stockInfoDao.getPagerDetailInfo(findEntity);
            int total = stockInfoDao.getPagerDetailInfoTotal(findEntity);

            Pager pager = new Pager(total, data);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }




    @Autowired
    StockInDao stockInDao;

    /**
     * 可以修改 供应商 设备编号、设备序列号、存放位置、设备状态、数量
     *
     * @param stockInfoVO the stock info vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStockInfo(StockInfoVO stockInfoVO) throws Exception
    {
        try
        {
            /**
             * 1、按照是否唯一标识分别处理
             * 2、唯一标识，直接更新数据 供应商、设备编号、设备序列号、存放位置、设备状态、数量
             * 3、非唯一标识
             *  a、修改了存放位置
             *   a）判断新存放位置是否有该备件数据，如果有、更新数量、如果没有、新加数据
             *   b）更新的字段包括、供应商、设备编号、设备序列号、存放位置、设备状态、数量
             *  b、未修改存放位置
             *   a）直接更新数据，更新的字段包括、供应商、设备编号、设备序列号、存放位置、设备状态、数量
             */
            Long stockId = stockInfoVO.getStockId();
            StockStockEntity stockEntity = stockInfoDao.getStockInfoById(stockId);
            //先清空数据库中的该条库存信息的库存数量，为拆分做准备
            Integer account = stockEntity.getAccount();
            stockEntity.setAccount(0);
            stockInfoDao.updateStockInfo(stockEntity);
            insertStockLogEntity(stockEntity);

            List<StockStockEntity> stockEntities = stockInfoVO.getStockEntities();
            int sum = stockEntities.stream().mapToInt(StockStockEntity::getAccount).sum();
            if (sum != account)
            {
                throw new Exception("修改库存信息时，库存数量与未修改前相比不能变化");
            }

            Long warehouseId = stockEntities.stream().filter(stock -> ObjectUtils.isNotNull(stock.getWarehouseId())).findFirst().get().getWarehouseId();
            for (StockStockEntity entity : stockEntities)
            {
                entity.setWarehouseId(warehouseId);
                if (entity.getInventoryType().equalsIgnoreCase(InventoryTypeEnum.UNIQUE.getCode()))
                {
                    StockStockEntity stock = BeanUtils.copyBean(stockEntity);
                    stock.setAccount(account);
                    stock.setSupplierId(entity.getSupplierId());
                    stock.setEquipmentNO(entity.getEquipmentNO());
                    stock.setSerialNumber(entity.getSerialNumber());
                    stock.setGoodsShelvesId(entity.getGoodsShelvesId());
                    stock.setStatus(entity.getStatus());
                    stockInfoDao.updateStockInfo(stock);
                    insertStockLogEntity(stock);
                }
                else
                {
                    StockStockEntity stock = stockInfoDao.getStockInfoByInfo(entity);

                    if (ObjectUtils.isNull(stock))
                    {
                        //insert
                        stockInfoDao.insertStockInfo(entity);
                        insertStockLogEntity(entity);
                    }
                    else
                    {
                        //update
                        stock.setAccount(stock.getAccount() + entity.getAccount());
                        stockInfoDao.updateStockInfo(stock);
                        stock.setAccount(entity.getAccount());
                        insertStockLogEntity(stock);
                    }
                }
            }

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public GetPagerDetailInfoVO getPagermyPartDetailInfo(StockInfoFindEntity findEntity)
    {

        GetPagerDetailInfoVO getPagerDetailInfoVO=new GetPagerDetailInfoVO();
        if("2".equals(findEntity.getType()))
        {
            getPagerDetailInfoVO.setPager(this.getPagerDetailInfo(findEntity));
        }
        getPagerDetailInfoVO.setPartSparePartEntity(stockInfoDao.getSparePartEntity(findEntity));

        getPagerDetailInfoVO.setPartSparePartImageEntities( sparePartDao.getSparePartImagesList(findEntity.getSparePartId()));

        return getPagerDetailInfoVO;
    }

    @Override
    public GetPagerDetailInfoVO getPagerstockPartDetailInfo(StockInfoFindEntity findEntity)
    {
        GetPagerDetailInfoVO getPagerDetailInfoVO=new GetPagerDetailInfoVO();

        getPagerDetailInfoVO.setPartSparePartEntity(stockInfoDao.getStocKSparePartEntity(findEntity));

        getPagerDetailInfoVO.setPartSparePartImageEntities( sparePartDao.getSparePartImagesList(findEntity.getSparePartId()));

        return getPagerDetailInfoVO;
    }

    private void insertStockLogEntity(StockStockEntity stockEntity)
    {
        //添加流水
        StockFlowLogEntity logEntity = new StockFlowLogEntity();
        logEntity.setStockId(stockEntity.getStockId());
        logEntity.setSparePartId(stockEntity.getSparePartId());
        logEntity.setWarehouseId(stockEntity.getWarehouseId());
        logEntity.setType(ApplyTypeEnum.STOCK_MODIFY.getCode());
        logEntity.setCount(stockEntity.getAccount());
        logEntity.setSourceId(stockEntity.getStockId());
        logEntity.setCreateUser(stockEntity.getOperationUserName());
        logEntity.setModifyUser(stockEntity.getOperationUserName());
        stockInDao.insertStockLog(logEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importSotckList(StockInfoFindEntity findEntity, List<String[]> list)
    {
        try
        {
            List<String[]> stationlist=list;
            List<Map<String, Object>> messListMap=new ArrayList<>();
            OperationsEquipmentFindEntity beijianfindEntity=new OperationsEquipmentFindEntity();
            beijianfindEntity.setUserKey(findEntity.getUserKey());
            List<Map<String, Object>> partdata = operationsEquipmentDao.getOperationPartListForInventory(beijianfindEntity);
            List<Map<String, Object>> warehouseandshelf =stockInfoDao.getWarhouseAndshelves(findEntity.getOperationSubjectId());
            Boolean flag=false;//无数据
            for (int i=0;i<stationlist.size();i++)
            {
                //仓库名称  备件名称 备件数量 库存类型 设备状态 房间号 货架编号
                String warehouseName=stationlist.get(i)[0].trim();
                String partName=stationlist.get(i)[1].trim();
                String account=stationlist.get(i)[2].trim();
                String inventoryType=stationlist.get(i)[3].trim();
                String status=stationlist.get(i)[4].trim();
                String houseNo=stationlist.get(i)[5].trim();
                String shelfNumber=stationlist.get(i)[6].trim();
                String str=warehouseName+partName+account+inventoryType+status+houseNo+shelfNumber;
                if("".equals(str))
                {
                    break;
                }
                flag=true;
                StringBuilder messb = new StringBuilder();
                if(ObjectUtils.isEmpty(warehouseName))
                {
                    messb.append("仓库名称不能为空;");
                }
                if(ObjectUtils.isEmpty(partName))
                {
                    messb.append("备件名称不能为空;");
                }
                if(DataSwitch.convertObjectToInteger(account)<1)
                {
                    messb.append("备件数量必须大于0;");
                }

                if(ObjectUtils.isEmpty(inventoryType))
                {
                    messb.append("库存类型不能为空;");
                }
                if(ObjectUtils.isEmpty(status))
                {
                    messb.append("设备状态不能为空;");
                }

                if(ObjectUtils.isNotEmpty(shelfNumber)&&ObjectUtils.isEmpty(houseNo))
                {
                    messb.append("房间号不能为空;");
                }
                if(shelfNumber.length()>32)
                {
                    messb.append("货架编号不能超过32个字符;");
                }

                if(inventoryType.equals("唯一标识"))
                {
                }
                else if(inventoryType.equals("非唯一标识"))
                {
                }
                else
                {
                    messb.append("库存类型不正确;");
                }

                if(status.equals("好件"))
                {
                }
                else if(status.equals("坏件"))
                {
                }
                else
                {
                    messb.append("设备状态不正确;");
                }

                if(ObjectUtils.isNotEmpty(warehouseName)&&ObjectUtils.isNotEmpty(houseNo)&&ObjectUtils.isNotEmpty(shelfNumber))
                {
                        List<Map<String, Object>> waseList = warehouseandshelf.parallelStream().filter(linstationmap -> warehouseName.equals(linstationmap.get("warehouseName").toString())&&houseNo.equals(linstationmap.get("houseNo").toString())&&shelfNumber.equals(linstationmap.get("shelfNumber").toString())).collect(Collectors.toList());
                        if (waseList != null && waseList.size() > 0)
                        {

                        }
                        else
                        {
                            messb.append("仓库名称、房间号和货架编号数据不对应;");
                        }
                }
                else if(ObjectUtils.isNotEmpty(warehouseName)&&ObjectUtils.isNotEmpty(houseNo))
                {
                    List<Map<String, Object>> waseList = warehouseandshelf.parallelStream().filter(linstationmap -> warehouseName.equals(linstationmap.get("warehouseName").toString())&&houseNo.equals(linstationmap.get("houseNo").toString())).collect(Collectors.toList());
                    if (waseList != null && waseList.size() > 0)
                    {

                    }
                    else
                    {
                        messb.append("仓库名称和房间号数据不对应;");
                    }
                }
                else if(ObjectUtils.isNotEmpty(warehouseName))
                {
                    List<Map<String, Object>> waseList = warehouseandshelf.parallelStream().filter(linstationmap -> warehouseName.equals(linstationmap.get("warehouseName").toString())).collect(Collectors.toList());
                    if (waseList != null && waseList.size() > 0)
                    {

                    }
                    else
                    {
                        messb.append("仓库名称数据不存在;");
                    }

                }




                List<Map<String, Object>> partMapList=   partdata.parallelStream().filter(linstationmap-> partName.equals(linstationmap.get("partName").toString()) ).collect(Collectors.toList());
                if(partMapList!=null&&partMapList.size()>0)
                {

                }
                else
                {
                    messb.append("备件类型和备件名称不对应;");
                }

                if(StringUtils.isNotEmpty(messb.toString()) )
                {
                    Map<String,Object> mesmap=new HashMap<>();
                    mesmap.put("num",(i+3));
                    mesmap.put("message",messb.toString());
                    messListMap.add(mesmap);

                }
            }
            if(!flag)
            {
                return ResultVOUtil.error(-1, "导入数据不能为空");
            }

            if(messListMap!=null&&messListMap.size()>0)
            {
                ResultVO resultVO = new ResultVO();
                resultVO.setData(messListMap);
                resultVO.setStatusCode(-1);
                resultVO.setResult("fail");
                resultVO.setMessage("导入失败");
                return resultVO;

            }
            List<StockStockEntity> entityList=new ArrayList<>();

            for (int i=0;i<stationlist.size();i++)
            {
                //仓库名称  备件名称 备件数量 库存类型 设备状态 房间号 货架编号
                StockStockEntity entity=new StockStockEntity();
                String warehouseName=stationlist.get(i)[0].trim();
                String partName=stationlist.get(i)[1].trim();
                String account=stationlist.get(i)[2].trim();
                String inventoryType=stationlist.get(i)[3].trim();
                String status=stationlist.get(i)[4].trim();
                String houseNo=stationlist.get(i)[5].trim();
                String shelfNumber=stationlist.get(i)[6].trim().equals("")?"XXXX":stationlist.get(i)[6].trim();

                String str=warehouseName+partName+account+inventoryType+status+houseNo;
                if("".equals(str))
                {
                    break;
                }
                entity.setOperationSubjectId(findEntity.getOperationSubjectId());
                if(inventoryType.equals("唯一标识"))
                {
                    entity.setInventoryType(InventoryTypeEnum.UNIQUE.getCode());
                }
                else if(inventoryType.equals("非唯一标识"))
                {

                    entity.setInventoryType(InventoryTypeEnum.NOT_UNIQUE.getCode());
                }
                if(status.equals("好件"))
                {
                    entity.setStatus(StockStatusEnum.NORMAL.getCode());
                }
                else if(status.equals("坏件"))
                {
                    entity.setStatus(StockStatusEnum.BAD.getCode());
                }

                List<Map<String, Object>> waseshelfList = warehouseandshelf.parallelStream().filter(linstationmap -> warehouseName.equals(linstationmap.get("warehouseName").toString())&&houseNo.equals(linstationmap.get("houseNo").toString())&&shelfNumber.equals(linstationmap.get("shelfNumber").toString())).collect(Collectors.toList());
                if(waseshelfList!=null&&waseshelfList.size()>0)
                {
                    entity.setWarehouseId(DataSwitch.convertObjectToLong(waseshelfList.get(0).get("warehouseId")));
                    entity.setGoodsShelvesId(DataSwitch.convertObjectToLong(waseshelfList.get(0).get("goodsShelvesId")));
                }
                else
                {

                    List<Map<String, Object>> waseList = warehouseandshelf.parallelStream().filter(linstationmap -> warehouseName.equals(linstationmap.get("warehouseName").toString())).collect(Collectors.toList());
                    Long warehouseId=0L;
                    if(waseList!=null&&waseList.size()>0)
                    {
                        warehouseId=DataSwitch.convertObjectToLong(waseList.get(0).get("warehouseId"));
                        entity.setWarehouseId(warehouseId);
                    }
                    if(StringUtils.isNotEmpty(houseNo))
                    {
                        StockGoodsShelvesEntity shelvesEntity = new StockGoodsShelvesEntity();
                        shelvesEntity.setContainerType("Shelf");
                        shelvesEntity.setRemark("");
                        shelvesEntity.setStatus(StatusEnum.START_USING.getValue());
                        shelvesEntity.setHouseNo(houseNo);
                        shelvesEntity.setShelfNumber(shelfNumber);
                        shelvesEntity.setWarehouseId(warehouseId);
                        shelvesEntity.setCreateUser(findEntity.getOperationUserName());
                        shelvesEntity.setModifyUser(findEntity.getOperationUserName());
                        shelvesDao.insertShelvesInfo(shelvesEntity);

                        entity.setWarehouseId(warehouseId);
                        entity.setGoodsShelvesId(shelvesEntity.getGoodsShelvesId());

                        Map<String, Object> warehouseandshelfMap = new HashMap<>();
                        warehouseandshelfMap.put("warehouseId", warehouseId);
                        warehouseandshelfMap.put("warehouseName", warehouseName);
                        warehouseandshelfMap.put("goodsShelvesId", shelvesEntity.getGoodsShelvesId());
                        warehouseandshelfMap.put("houseNo", houseNo);
                        warehouseandshelfMap.put("shelfNumber", shelfNumber);
                        warehouseandshelf.add(warehouseandshelfMap);
                    }
                }
                List<Map<String, Object>> partMapList= partdata.parallelStream().filter(linstationmap-> partName.equals(linstationmap.get("partName").toString())).collect(Collectors.toList());
                if(partMapList!=null&&partMapList.size()>0)
                {
                    entity.setSparePartId(DataSwitch.convertObjectToLong(partMapList.get(0).get("sparePartId")));
                }
                entity.setInDate(DateUtils.getNow());
                if(inventoryType.equals("唯一标识"))
                {
                    entity.setAccount(1);
                    for (int n=0;n<DataSwitch.convertObjectToInteger(account);n++ )
                    {
                        entityList.add(entity);
                    }

                }
                else
                {
                    entity.setAccount(DataSwitch.convertObjectToInteger(account));
                    entityList.add(entity);
                }
            }
            for (StockStockEntity entity : entityList)
            {
                stockInfoDao.insertStockInfo(entity);
                StockStockEntity stockEntity = stockInfoDao.getStockInfoById(entity.getStockId());
                insertStockLogEntity(stockEntity);

            }
            return  ResultVOUtil.success(entityList.size());
        }
        catch (Exception ex)
        {
            throw ex;

        }

    }

    @Override
    public List<Map<String, Object>> getAllSotckListMap(StockInfoFindEntity findEntity)
    {
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        return    stockInfoDao.getPagerInfo(findEntity);
    }
}
