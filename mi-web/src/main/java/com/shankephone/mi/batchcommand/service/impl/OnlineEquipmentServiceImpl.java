package com.shankephone.mi.batchcommand.service.impl;

import com.shankephone.mi.batchcommand.dao.OnlineEquipmentDao;
import com.shankephone.mi.batchcommand.formbean.DeviceNoRegistFindEntity;
import com.shankephone.mi.batchcommand.formbean.GetOnlineEquipmentListFindEntity;
import com.shankephone.mi.batchcommand.service.OnlineEquipmentService;
import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.OrgLineEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 赵亮
 * @date 2018-10-23 9:43
 */
@Service
public class OnlineEquipmentServiceImpl implements OnlineEquipmentService
{
    @Autowired
    private OnlineEquipmentDao onlineEquipmentDao;
    /**
     * 返回线上设备列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-23 9:49
     */
    @Override
    public Pager getOnlineEquipmentList(GetOnlineEquipmentListFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list = onlineEquipmentDao.getOnlineEquipmentList(findEntity);
            int total = onlineEquipmentDao.getOnlineEquipmentListTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取所有线路
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-10-23 10:15
     */
    @Override
    public List<Map<String, Object>> getAllLine(BaseFindEntity findEntity)
    {
        try
        {
            return onlineEquipmentDao.getAllLine(findEntity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取线路下车站
     * @param entity
     * @author：赵亮
     * @date：2018-10-23 10:19
     */
    @Override
    public List<Map<String, Object>> getStationListByLineId(OrgLineEntity entity)
    {
        try
        {
            return onlineEquipmentDao.getStationListByLineId(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager getDeviceNoRegistList(DeviceNoRegistFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> list=onlineEquipmentDao.getDeviceNoRegistList(findEntity);
            int total = onlineEquipmentDao.getDeviceNoRegistListTotal(findEntity);
            Pager pager = new Pager(total, list);
            return pager;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOperationsEquipment(OperationsEquipmentEntity entity)
    {
        try
        {
            onlineEquipmentDao.updateOperationsEquipment(entity);
            onlineEquipmentDao.deleteDeviceNoRegist(entity.getDeviceuId());
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
