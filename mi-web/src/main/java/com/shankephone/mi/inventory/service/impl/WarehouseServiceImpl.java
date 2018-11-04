package com.shankephone.mi.inventory.service.impl;

import com.shankephone.mi.inventory.dao.WarehouseDao;
import com.shankephone.mi.inventory.formbean.WarehouseFindEntity;
import com.shankephone.mi.inventory.service.WarehouseService;
import com.shankephone.mi.inventory.vo.WarehouseVO;
import com.shankephone.mi.model.StockWarehouseStationEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.SessionMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 库存管理Service
 *
 * @author 司徒彬
 * @date 2018 /7/2 17:09
 */
@Service
public class WarehouseServiceImpl implements WarehouseService
{
    /**
     * The Warehouse dao.
     */
    @Autowired
    WarehouseDao warehouseDao;
    private Long parentWarehouseId;

    @Override
    public List<Map<String, Object>> getPagerInfo(WarehouseFindEntity findEntity)
    {
        try
        {
            if (findEntity.getWorkSectionId() != null && findEntity.getWorkSectionId().intValue() == 0)
            {
                UserLoginInfo userInfo = SessionMap.getUserInfo(findEntity.getUserKey());
                findEntity.setWorkSectionIds(userInfo.getWorkSections());
            }
            else
            {
                findEntity.setWorkSectionIds(Arrays.asList(findEntity.getWorkSectionId()));
            }

            List<Map<String, Object>> list = warehouseDao.getPagerInfo(findEntity);
            list = DataSwitch.convertListToTree(list, -1, "warehouseId", "parentWarehouseId", "children");
            return list;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getWarehouseInfo(WarehouseFindEntity findEntity) throws Exception
    {
        /**
         * 1、当天
         */
        try
        {
            Long warehouseId = findEntity.getWarehouseId();
            Map<String, Object> warehouse;
            Long parentWarehouseId;
            //修改
            List<Map<String, Object>> data = warehouseDao.getWarehouseInfo(warehouseId);
            if (data.size() == 0 && warehouseId != 0)
            {
                throw new Exception(String.format("无法找到id为 %s 的仓库", warehouseId));
            }

            List<Long> selectedStationIds;
            if (warehouseId != 0)
            {
                warehouse = data.get(0);
                parentWarehouseId = DataSwitch.convertObjectToLong(warehouse.get("parentWarehouseId"));
                selectedStationIds = data.stream().map(map -> DataSwitch.convertObjectToLong(map.get("stationId"))).collect(Collectors.toList());
            }
            else
            {
                warehouse = new HashMap<>();
                warehouse.put("workSectionId",findEntity.getWorkSectionId());
                parentWarehouseId = findEntity.getParentWarehouseId();
                selectedStationIds = new ArrayList<>();
            }


            List<Map<String, Object>> stationList;
            //如果是根节点，则展示所有站点
            if (parentWarehouseId == -1)
            {
                //如果是父Id是-1 则代表为一级库 需要获得对应工区下的所有站点
                Long workSectionId = DataSwitch.convertObjectToLong(warehouse.get("workSectionId"));
                stationList = warehouseDao.getStationByWorkSectionId(workSectionId);
            }
            else
            {
                //如果不是-1 则代表为非一级库，需要获取对应父节点负责的所有站点
                stationList = warehouseDao.getStationByParentWarehouseId(parentWarehouseId);
            }

            if (selectedStationIds.size() != 0)
            {
                stationList.stream().forEach(station -> {
                    Long stationId = DataSwitch.convertObjectToLong(station.get("stationId"));
                    boolean isChecked = selectedStationIds.stream()
                            .filter(id -> id.equals(stationId))
                            .findFirst()
                            .isPresent();
                    station.put("isChecked", isChecked);
                });
            }


            warehouse.put("stationList", stationList);
            warehouse.remove("stationId");

            return warehouse;

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addWarehouseInfo(WarehouseVO warehouse)
    {
        try
        {
            //添加仓库主表信息
            warehouseDao.insertWarehouse(warehouse);
            Long warehouseId = warehouse.getWarehouseId();
            //添加仓库车站关系
            for (Long stationId : warehouse.getSelectedStationIdList())
            {
                StockWarehouseStationEntity warehouseStationEntity = new StockWarehouseStationEntity();
                warehouseStationEntity.setStationId(stationId);
                warehouseStationEntity.setWarehouseId(warehouseId);
                warehouseDao.insertWarehouseStationInfo(warehouseStationEntity);
            }
            return;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWarehouseInfo(WarehouseVO warehouse)
    {
        try
        {
            warehouseDao.updateWarehouse(warehouse);
            Long warehouseId = warehouse.getWarehouseId();
            //删除仓库站点信息
            warehouseDao.deleteWarehouseStation(warehouseId);
            //添加仓库车站关系
            warehouse.getSelectedStationIdList()
                    .stream()
                    .forEach(stationId -> {
                        StockWarehouseStationEntity warehouseStationEntity = new StockWarehouseStationEntity();
                        warehouseStationEntity.setStationId(stationId);
                        warehouseStationEntity.setWarehouseId(warehouseId);
                        warehouseDao.insertWarehouseStationInfo(warehouseStationEntity);
                    });
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteWarehouseInfo(Long warehouseId)
    {
        try
        {
            warehouseDao.deleteWarehouse(warehouseId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getStationInfo(WarehouseFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> stationList;
            Long parentWarehouseId = findEntity.getParentWarehouseId();
            if (parentWarehouseId == -1)
            {
                //根据工区获取站点信息
                Long workSectionId = findEntity.getWorkSectionId();
                stationList = warehouseDao.getStationByWorkSectionId(workSectionId);
            }
            else
            {
                stationList = warehouseDao.getStationByParentWarehouseId(parentWarehouseId);
            }

            return stationList;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
