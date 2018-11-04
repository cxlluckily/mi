package com.shankephone.mi.partbreakdowninfo.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoFindEntity;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-02 9:53
 */
public interface PartBreakdownInfoService
{
    /**
     *新增
     *@author：赵亮
     *@date：2018-08-02 9:57
    */
    Integer insertOne(PartBreakdownInfoEntity entity);

    /**
     *修改
     *@author：赵亮
     *@date：2018-08-02 10:03
    */
    Integer updateOne(PartBreakdownInfoEntity entity);

    /**
     *获取列表数据
     *@author：赵亮
     *@date：2018-08-02 10:09
    */
    Pager<Map<String, Object>> getBreakdownInfoList(PartBreakdownInfoFindEntity findEntity);

    /**
     *根据查询条件返回配件名称列表
     *@author：赵亮
     *@date：2018-08-02 14:34
    */
    List<PartSparePartEntity> getSparePartDDLList(SparePartListForBreakdownFindEntity findEntity);

    /**
     *根据备件返回故障信息
     *@author：赵亮
     *@date：2018-08-17 15:21
    */
    List<PartBreakdownInfoEntity> getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity);

    /**
     *导入故障维修信息
     *@author：郝伟州
     *@date：2018年10月26日11:12:24
     */
    ResultVO importPartBreakdownList(PartBreakdownInfoFindEntity findEntity, List<String[]> list);
}
