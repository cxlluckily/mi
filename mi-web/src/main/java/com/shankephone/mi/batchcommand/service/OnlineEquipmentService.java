package com.shankephone.mi.batchcommand.service;

import com.shankephone.mi.batchcommand.formbean.DeviceNoRegistFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOnlineEquipmentListFindEntity;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OrgLineEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-23 9:42
 */
public interface OnlineEquipmentService
{

    /**
     *返回线上设备列表
     *@author：赵亮
     *@date：2018-10-23 9:49
    */
    Pager getOnlineEquipmentList(GetOnlineEquipmentListFindEntity findEntity);

    /**
     *获取所有线路
     *@author：赵亮
     *@date：2018-10-23 10:18
    */
    List<Map<String,Object>> getAllLine(BaseFindEntity findEntity);

    /**
     *获取线路下车站
     *@author：赵亮
     *@date：2018-10-23 10:19
    */
    List<Map<String,Object>> getStationListByLineId(OrgLineEntity entity);


    /**
     *获取未注册成功的设备信息
     *@author：郝伟州
     *@date：2018年10月31日13:52:46
     */
    Pager getDeviceNoRegistList(DeviceNoRegistFindEntity findEntity);

    void updateOperationsEquipment(OperationsEquipmentEntity entity);
}
