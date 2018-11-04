package com.shankephone.mi.spacepart.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartDeviceComposeEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.dao.PartDeviceComposeDao;
import com.shankephone.mi.spacepart.dao.SparePartDao;
import com.shankephone.mi.spacepart.dao.SparePartTypeDao;
import com.shankephone.mi.spacepart.formbean.ComposeListFindEntity;
import com.shankephone.mi.spacepart.formbean.OneTreeDataFindEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeFindEntity;
import com.shankephone.mi.spacepart.service.PartDeviceComposeService;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-13 11:08
 */
@Service
public class PartDeviceComposeServiceImpl implements PartDeviceComposeService
{
    @Autowired
    private PartDeviceComposeDao partDeviceComposeDao;

    /**
     * 新增设备组成
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-13 11:11
     */
    @Override
    public Long insertOne(PartDeviceComposeBusiEntity entity)
    {
        try
        {
            partDeviceComposeDao.insertOne(entity);
            if (entity.getFlag().equals("addFirst"))
            {
                entity.setComposePids(entity.getDeviceComposeId().toString());
            }
            else if (entity.getFlag().equals("add"))//同级
            {
                entity.setComposePids(entity.getComposePids().substring(0, entity.getComposePids().lastIndexOf("@")) + "@" + +entity.getDeviceComposeId());
            }
            else if (entity.getFlag().equals("addChild"))//下级
            {
                entity.setComposePids(entity.getComposePids() + "@" + entity.getDeviceComposeId());
            }
            partDeviceComposeDao.updateOne(entity);
            return entity.getDeviceComposeId();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-13 11:24
     */
    @Override
    public Integer updateOne(PartDeviceComposeEntity entity)
    {
        try
        {
            return partDeviceComposeDao.updateOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager<Map<String, Object>> getComposeList(ComposeListFindEntity findEntity)
    {
        try
        {
            try
            {
                Integer total = partDeviceComposeDao.getComposeListCount(findEntity);
                List<Map<String, Object>> entityList = partDeviceComposeDao.getComposeList(findEntity);
                Pager pager = new Pager<>(total, entityList);
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
    private SparePartTypeDao sparePartTypeDao;
    @Autowired
    private SparePartDao sparePartDao;

    /**
     * 根据备件类型pid返回备件下拉框数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-13 13:23
     */
    @Override
    public List<PartSparePartTypeEntity> getSparePartByCompose(PartDeviceComposeFindEntity findEntity)
    {
        try
        {
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return sparePartTypeDao.getSparePartByCompose(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 删除设备组成
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-13 18:13
     */
    @Override
    public ResultVO deleteOne(PartDeviceComposeEntity entity)
    {
        try
        {
            Integer useCount = partDeviceComposeDao.getUserIDCount(entity);
            if (useCount > 0)
            {
                ResultVOUtil.error(-1, "该设备组成数据已被使用，不能删除");
            }
            partDeviceComposeDao.deleteOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据主键返回树形数据列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-14 10:47
     */
    @Override
    public List<Map<String, Object>> getOneTreeData(OneTreeDataFindEntity findEntity)
    {
        try
        {
            try
            {
                List<Map<String, Object>> entitys = partDeviceComposeDao.getOneTreeData(findEntity);
                entitys = DataSwitch.convertListToTree(entitys, -1, "deviceComposeId", "composePid", "children");
                return entitys;
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

    /**
     * 根据备件分类ID返回对应备件信息
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-14 11:45
     */
    @Override
    public List<Map<String, Object>> getPartListBySparePartTypeId(PartSparePartEntity entity)
    {
        try
        {
            return sparePartDao.getPartListBySparePartTypeId(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
