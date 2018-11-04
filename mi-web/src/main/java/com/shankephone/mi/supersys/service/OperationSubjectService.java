package com.shankephone.mi.supersys.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysOperationSubjectEntity;
import com.shankephone.mi.supersys.formbean.OperationSubjectFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 13:36
 */
public interface OperationSubjectService
{
    /**
     * 返回所有地区信息
     *
     * @author：赵亮
     * @date：2018-07-23 13:41
     */
    List<Map<String, Object>> getAllRegion();

    /**
     * 新增运营主体
     *
     * @author：赵亮
     * @date：2018-07-23 13:44
     */
    ResultVO insertOne(SysOperationSubjectEntity entity);

    /**
     * 更新运营主体
     *
     * @author：赵亮
     * @date：2018-07-23 16:09
     */
    Integer updateOne(SysOperationSubjectEntity entity);

    /**
     * 查询list
     *
     * @author：赵亮
     * @date：2018-07-23 16:14
     */
    Pager<Map<String, Object>> getOperationList(OperationSubjectFindEntity findEntity);

    Integer updateAdminPassword(OperationSubjectFindEntity findEntity);
}
