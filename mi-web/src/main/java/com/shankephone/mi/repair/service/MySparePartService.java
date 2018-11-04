package com.shankephone.mi.repair.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.repair.vo.MySparePartFindEntity;

import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 9:34
 */
public interface MySparePartService
{
    /**
     *返回我的备件列表数据
     *@author：赵亮
     *@date：2018-08-06 9:37
    */
    Pager<Map<String, Object>> getMySparePartList(MySparePartFindEntity findEntity);

    /**
     *返还入库时候更新我的设备表里的数据为零
     *@author：赵亮
     *@date：2018-08-15 19:40
    */
    void updateMySqparePartCount(Long applyId);
}
