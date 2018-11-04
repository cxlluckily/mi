package com.shankephone.mi.inventory.service;

import com.shankephone.mi.inventory.formbean.WarehouseFindEntity;
import com.shankephone.mi.inventory.vo.WarehouseVO;

import java.util.List;
import java.util.Map;

/**
 * 库存管理 Service接口
 *
 * @author 司徒彬
 * @date 2018 /7/2 17:08
 */
public interface WarehouseService {

    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    List<Map<String,Object>> getPagerInfo(WarehouseFindEntity findEntity);

    /**
     * Gets warehouse info.
     *
     * @param findEntity the find entity
     * @return the warehouse info
     * @throws Exception the exception
     */
    Map<String, Object> getWarehouseInfo(WarehouseFindEntity findEntity) throws Exception;


    /**
     * Add warehouse info.
     *
     * @param warehouse the warehouse
     */
    void addWarehouseInfo(WarehouseVO warehouse);

    /**
     * Update warehouse info.
     *
     * @param warehouse the warehouse
     */
    void updateWarehouseInfo(WarehouseVO warehouse);

    /**
     * Delete warehouse info.
     *
     * @param warehouseId the warehouse id
     */
    void deleteWarehouseInfo(Long warehouseId);

    /**
     * Gets station info.
     *
     * @param findEntity the find entity
     * @return the station info
     */
    List<Map<String,Object>> getStationInfo(WarehouseFindEntity findEntity);
}
