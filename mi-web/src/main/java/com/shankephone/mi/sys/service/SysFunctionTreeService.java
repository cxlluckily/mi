package com.shankephone.mi.sys.service;

import com.shankephone.mi.sys.formbean.FunctionTreeFindEntity;
import com.shankephone.mi.sys.vo.FunctionTreeVO;
import com.shankephone.mi.sys.vo.UserTreeFindEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-20 16:33
 */
public interface SysFunctionTreeService
{
    /**
     *根据用户ID返回功能树节点
     *@author：赵亮
     *@date：2018-06-20 16:34
    */
    List<FunctionTreeVO> getUserTree(UserTreeFindEntity findEntity);
    
    /**
     * 查询不同类型的功能树
     * @param entity
     * @param
     * @return
     */
    List<Map<String,Object>> getFunctionTree(FunctionTreeFindEntity entity);
}
