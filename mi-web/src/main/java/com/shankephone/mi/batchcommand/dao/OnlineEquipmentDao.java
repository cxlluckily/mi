package com.shankephone.mi.batchcommand.dao;

import com.shankephone.mi.batchcommand.dao.provider.OnlineEquipmentDaoProvider;
import com.shankephone.mi.batchcommand.formbean.DeviceNoRegistFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOnlineEquipmentListFindEntity;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OrgLineEntity;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-23 9:50
 */
@Repository
public interface OnlineEquipmentDao
{
    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getOnlineEquipmentList")
    List<Map<String,Object>> getOnlineEquipmentList(GetOnlineEquipmentListFindEntity findEntity);
    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getOnlineEquipmentListTotal")
    int getOnlineEquipmentListTotal(GetOnlineEquipmentListFindEntity findEntity);
    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getAllLine")
    List<Map<String,Object>> getAllLine(BaseFindEntity findEntity);
    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getStationListByLineId")
    List<Map<String,Object>> getStationListByLineId(OrgLineEntity entity);
    /**
     *获取未注册成功的设备信息
     *@author：郝伟州
     *@date：2018年10月31日13:52:46
     */
    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getDeviceNoRegistList")
    List<Map<String,Object>> getDeviceNoRegistList(DeviceNoRegistFindEntity findEntity);

    @SelectProvider(type = OnlineEquipmentDaoProvider.class, method = "getDeviceNoRegistListTotal")
    int getDeviceNoRegistListTotal(DeviceNoRegistFindEntity findEntity);

    /**
     *更新线上设备的注册信息
     */
    @UpdateProvider(type = OnlineEquipmentDaoProvider.class, method = "updateOperationsEquipment")
    void updateOperationsEquipment(OperationsEquipmentEntity entity);

    /**
     *删除未注册成功的设备根据设备唯一ID
     */
    @DeleteProvider(type = OnlineEquipmentDaoProvider.class, method = "deleteDeviceNoRegist")
    Integer deleteDeviceNoRegist(String deviceuId);
}
