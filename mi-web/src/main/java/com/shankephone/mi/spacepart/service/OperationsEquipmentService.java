package com.shankephone.mi.spacepart.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;

import java.util.List;
import java.util.Map;

/**
 * 营运设备
 *
 * @author 司徒彬
 * @date 2018 /8/20 15:20
 */
public interface OperationsEquipmentService {
    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    Pager getPagerInfo(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets station list.
     *
     * @param findEntity the find entity
     * @return the station list
     */
    List<Map<String, Object>> getStationList(OperationsEquipmentFindEntity findEntity);

    /**
     * Add equipment.
     *
     * @param equipmentEntity the equipment entity
     */
    ResultVO addEquipment(OperationsEquipmentEntity equipmentEntity);

    /**
     * Update equipment.
     *
     * @param equipmentEntity the equipment entity
     */
    ResultVO updateEquipment(OperationsEquipmentEntity equipmentEntity);

    /**
     * Delete equipment.
     *
     * @param findEntity the find entity
     */
    void deleteEquipment(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets equipment.
     *
     * @param findEntity the find entity
     * @return the equipment
     */
    Map<String, Object> getEquipment(OperationsEquipmentFindEntity findEntity);

    /**
     * Gets equipment.
     *
     * @param findEntity the find entity
     * @return the equipment
     */
    Map<String, Object> getEquipmentEntity(OperationsEquipmentFindEntity findEntity);

    /**
     *导入车站信息列表
     *@author：郝伟州
     *@date：2018年8月30日20:32:58
     */
    ResultVO importEquipmentList(OperationsEquipmentFindEntity findEntity, List<String[]> list);
    List<Map<String, Object>> getAllEquipmentListMap(OperationsEquipmentFindEntity findEntity);

}
