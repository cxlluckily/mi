package com.shankephone.mi.spacepart.dao;

import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.spacepart.dao.provider.OperationsEquipmentProvider;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 营运设备Dao
 *
 * @author 司徒彬
 * @date 2018 /8/20 15:21
 */
@Repository
public interface OperationsEquipmentDao {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getPagerInfo")
    List<Map<String, Object>> getPagerInfo(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets pager info total.
     *
     * @param findEntity the find entity
     * @return the pager info total
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getPagerInfoTotal")
    int getPagerInfoTotal(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets station list.
     *
     * @param findEntity the find entity
     * @return the station list
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getStationList")
    List<Map<String, Object>> getStationList(OperationsEquipmentFindEntity findEntity);

    /**
     * Add equipment.
     *
     * @param equipmentEntity the equipment entity
     */
    @InsertProvider(type = OperationsEquipmentProvider.class, method = "addEquipment")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentId")
    void addEquipment(OperationsEquipmentEntity equipmentEntity);

    /**
     * Update equipment.
     *
     * @param equipmentEntity the equipment entity
     */
    @UpdateProvider(type = OperationsEquipmentProvider.class, method = "updateEquipment")
    void updateEquipment(OperationsEquipmentEntity equipmentEntity);

    /**
     * Delete equipment.
     *
     * @param findEntity the find entity
     */
    @UpdateProvider(type = OperationsEquipmentProvider.class, method = "deleteEquipment")
    void deleteEquipment(OperationsEquipmentFindEntity findEntity);


    /**
     * Gets equipment.
     *
     * @param equipmentId the equipment id
     * @return the equipment
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getEquipment")
    Map<String, Object> getEquipment(Long equipmentId);

    /**
     * Gets equipment.
     *
     * @param findEntity the equipment id
     * @return the equipment
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getEquipmentEntity")
    Map<String, Object> getEquipmentEntity(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets equipment images.
     *
     * @param equipmentId the equipment id
     * @return the equipment images
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getEquipmentImages")
    List<Map<String, Object>> getEquipmentImages(Long equipmentId);


    /**
     * Gets spare parts info.
     *
     * @param equipmentId the equipment id
     * @return the spare parts info
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getSparePartsInfo")
    List<Map<String, Object>> getSparePartsInfo(Long equipmentId);

    /**
     * Gets station list.
     *获取可以导入车站和站点信息
     * @param findEntity the find entity
     * @return the station list
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getLineStationList")
    List<Map<String, Object>> getLineStationList(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets station list.
     * 获取可以导入的备件信息
     * @param findEntity the find entity
     * @return the station list
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getOperationPartList")
    List<Map<String, Object>> getOperationPartList(OperationsEquipmentFindEntity findEntity);
    /**
     * Gets station list.
     * 获取可以导入的备件信息（库存管理）
     * @param findEntity the find entity
     * @return the station list
     */
    @SelectProvider(type = OperationsEquipmentProvider.class, method = "getOperationPartListForInventory")
    List<Map<String, Object>> getOperationPartListForInventory(OperationsEquipmentFindEntity findEntity);

}
