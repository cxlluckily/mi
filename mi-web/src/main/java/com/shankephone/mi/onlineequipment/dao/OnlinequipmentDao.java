package com.shankephone.mi.onlineequipment.dao;


import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.onlineequipment.dao.provider.OnlinequipmentProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 郝伟州
 * @date 2018/8/23 10:44
 */
@Repository
public interface OnlinequipmentDao
{
    /**
     * Gets list info.
     *
     * @param entity the find entity
     * @return the list info
     */
    @SelectProvider(type = OnlinequipmentProvider.class, method = "getOnlinequipmentList")
    List<Map<String, Object>> getOnlinequipmentList(OperationsEquipmentEntity entity);


    /**
     * Update shelves info.
     *
     * @param entity the  entity
     */
    @UpdateProvider(type = OnlinequipmentProvider.class, method = "updateOnlineequipment")
    Integer updateOnlineequipment(OperationsEquipmentEntity entity);

    @SelectProvider(type = OnlinequipmentProvider.class, method = "getOnlinequipmentDetail")
    OperationsEquipmentEntity getOnlinequipmentDetail(OperationsEquipmentEntity entity);

}
