package com.shankephone.mi.inventory.dao;

import com.shankephone.mi.inventory.dao.provider.WarehouseProvider;
import com.shankephone.mi.inventory.formbean.WarehouseFindEntity;
import com.shankephone.mi.inventory.vo.WarehouseVO;
import com.shankephone.mi.model.StockWarehouseStationEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 库存管理Dao
 *
 * @author 司徒彬
 * @date 2018 /7/2 17:10
 */
@Repository
public interface WarehouseDao
{

    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getPagerInfo")
    List<Map<String, Object>> getPagerInfo(WarehouseFindEntity findEntity);

    /**
     * Gets warehouse info.
     *
     * @param warehouseId the warehouse id
     * @return the warehouse info
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getWarehouseInfo")
    List<Map<String, Object>> getWarehouseInfo(Long warehouseId);

    /**
     * Gets station by work section id.
     *
     * @param workSectionId the work section id
     * @return the station by work section id
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getStationByWorkSectionId")
    List<Map<String, Object>> getStationByWorkSectionId(Long workSectionId);

    /**
     * Gets station by parent warehouse id.
     *
     * @param parentWarehouseId the parent warehouse id
     * @return the station by parent warehouse id
     */
    @SelectProvider(type = WarehouseProvider.class, method = "getStationByParentWarehouseId")
    List<Map<String, Object>> getStationByParentWarehouseId(Long parentWarehouseId);

    /**
     * Insert warehouse.
     *
     * @param warehouse the warehouse
     */
    @InsertProvider(type = WarehouseProvider.class, method = "insertWarehouse")
    @Options(useGeneratedKeys = true, keyProperty = "warehouseId")
    void insertWarehouse(WarehouseVO warehouse);

    /**
     * Insert warehouse station info.
     *
     * @param warehouseStationEntity the warehouse station entity
     */
    @SelectProvider(type = WarehouseProvider.class, method = "insertWarehouseStationInfo")
    void insertWarehouseStationInfo(StockWarehouseStationEntity warehouseStationEntity);

    /**
     * Update warehouse.
     *
     * @param warehouse the warehouse
     */
    @UpdateProvider(type = WarehouseProvider.class, method = "updateWarehouse")
    void updateWarehouse(WarehouseVO warehouse);

    /**
     * Delete warehouse station.
     *
     * @param warehouseId the warehouse id
     */
    @DeleteProvider(type = WarehouseProvider.class, method = "deleteWarehouseStation")
    void deleteWarehouseStation(Long warehouseId);

    @UpdateProvider(type = WarehouseProvider.class, method = "deleteWarehouse")
    void deleteWarehouse(Long warehouseId);

    @SelectProvider(type = WarehouseProvider.class, method = "getSameWarehouseNameCount")
    Integer getSameWarehouseNameCount(WarehouseVO warehouse);

}
