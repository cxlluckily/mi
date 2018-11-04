package com.shankephone.mi.org.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.org.formbean.LineFindEntity;

import java.util.Map;

/**
 * 线路管理
 * @author 赵亮
 * @date 2018-06-25 15:40
 */

public interface LineService
{
    /**
     *新增线路
     *@author：赵亮
     *@date：2018-06-25 16:13
    */
    ResultVO insertOne(OrgLineEntity entity);

    /**
     *更新线路
     *@author：赵亮
     *@date：2018-06-25 16:13
    */
    ResultVO updateOne(OrgLineEntity entity);

    Pager<Map<String, Object>> getLineInfo(LineFindEntity findEntity);

}
