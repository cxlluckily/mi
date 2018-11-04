package com.shankephone.mi.spacepart.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartDeviceComposeEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.formbean.ComposeListFindEntity;
import com.shankephone.mi.spacepart.formbean.OneTreeDataFindEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-13 11:08
 */
public interface PartDeviceComposeService
{
    /**
     *新增设备组成
     *@author：赵亮
     *@date：2018-08-13 11:11
    */
    Long insertOne(PartDeviceComposeBusiEntity entity);

    /**
     *更新
     *@author：赵亮
     *@date：2018-08-13 11:24
    */
    Integer updateOne(PartDeviceComposeEntity entity);

    /**
     *根据查询条件返回树形结构数据
     *@author：赵亮
     *@date：2018-08-13 13:23
    */
    Pager<Map<String, Object>> getComposeList(ComposeListFindEntity findEntity);

    /**
     *根据备件类型pid返回备件下拉框数据
     *@author：赵亮
     *@date：2018-08-13 13:23
    */
    List<PartSparePartTypeEntity> getSparePartByCompose(PartDeviceComposeFindEntity findEntity);

    /**
     *删除设备组成
     *@author：赵亮
     *@date：2018-08-13 18:13
    */
    ResultVO deleteOne(PartDeviceComposeEntity entity);
    /**
     *根据主键返回树形数据列表
     *@author：赵亮
     *@date：2018-08-14 10:47
    */
    List<Map<String, Object>>getOneTreeData(OneTreeDataFindEntity findEntity);

    /**
     *根据备件分类ID返回对应备件信息
     *@author：赵亮
     *@date：2018-08-14 11:45
    */
    List<Map<String, Object>> getPartListBySparePartTypeId(PartSparePartEntity entity);
}
