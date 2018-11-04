package com.shankephone.mi.partbreakdowninfo.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.partbreakdowninfo.dao.PartBreakdownInfoDao;
import com.shankephone.mi.partbreakdowninfo.dao.PartBreakdownRepairInfoDao;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import com.shankephone.mi.partbreakdowninfo.service.PartBreakdownRepairInfoService;
import com.shankephone.mi.spacepart.dao.SparePartDao;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-02 13:43
 */
@Service
public class PartBreakdownRepairInfoServiceImpl implements PartBreakdownRepairInfoService
{
    @Autowired
    private PartBreakdownRepairInfoDao partBreakdownRepairInfoDao;
    /**
     * 新增
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-02 9:57
     */
    @Override
    public Integer insertOne(PartBreakdownRepairInfoEntity entity)
    {
       try
       {
           return partBreakdownRepairInfoDao.insertOne(entity);
       }
       catch(Exception ex)
       {
           throw ex;
       }
    }

    /**
     * 修改
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-02 10:03
     */
    @Override
    public Integer updateOne(PartBreakdownRepairInfoEntity entity)
    {
        try
        {
            return partBreakdownRepairInfoDao.updateOne(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取列表数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-02 10:09
     */
    @Override
    public Pager<Map<String, Object>> getPartBreakdownRepairInfoList(PartBreakdownRepairInfoFindEntity findEntity)
    {
        try
        {
            try
            {
                List<Map<String, Object>> entitys = partBreakdownRepairInfoDao.getPartBreakdownRepairInfoList(findEntity);
                Integer total = partBreakdownRepairInfoDao.getPartBreakdownRepairInfoListCount(findEntity);
                Pager pager = new Pager<>(total, entitys);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private SparePartDao sparePartDao;

    /**
     * 根据查询条件返回配件名称列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-02 14:04
     */
    @Override
    public List<PartSparePartEntity> getSparePartDDLList(SparePartListForBreakdownFindEntity findEntity)
    {
        try
        {
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return sparePartDao.getSparePartListForBreakdown(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private PartBreakdownInfoDao partBreakdownInfoDao;

    /**
     * 根据查询条件返回备件故障列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-02 14:34
     */
    @Override
    public List<PartBreakdownInfoEntity> getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity)
    {
        try
        {
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return partBreakdownInfoDao.getPartBreakdownInfoDDLList(findEntity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
}
