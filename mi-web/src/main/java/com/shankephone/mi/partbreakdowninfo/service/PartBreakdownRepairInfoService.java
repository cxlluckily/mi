package com.shankephone.mi.partbreakdowninfo.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-02 13:43
 */
public interface PartBreakdownRepairInfoService
{
    /**
     *新增
     *@author：赵亮
     *@date：2018-08-02 9:57
     */
    Integer insertOne(PartBreakdownRepairInfoEntity entity);

    /**
     *修改
     *@author：赵亮
     *@date：2018-08-02 10:03
     */
    Integer updateOne(PartBreakdownRepairInfoEntity entity);

    /**
     *获取列表数据
     *@author：赵亮
     *@date：2018-08-02 10:09
     */
    Pager<Map<String, Object>> getPartBreakdownRepairInfoList(PartBreakdownRepairInfoFindEntity findEntity);
    /**
     *根据查询条件返回配件名称列表
     *@author：赵亮
     *@date：2018-08-02 14:34
     */
    List<PartSparePartEntity> getSparePartDDLList(SparePartListForBreakdownFindEntity findEntity);
    /**
     *根据查询条件返回备件故障列表
     *@author：赵亮
     *@date：2018-08-02 14:34
     */
    List<PartBreakdownInfoEntity> getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity);
}
