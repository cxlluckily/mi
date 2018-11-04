package com.shankephone.mi.onlineequipment.service;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 郝伟州
 * @date 2018/8/23 10:42
 */
@Repository
public interface OnlineequipmentService
{
    List<Map<String, Object>> getOnlinequipmentList(OperationsEquipmentEntity entity);

    ResultVO updateOnlineequipment(OperationsEquipmentEntity entity);

}
