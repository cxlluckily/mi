package com.shankephone.mi.supersys.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysAdminEntity;
import com.shankephone.mi.supersys.formbean.DeleteAdminFormBean;
import com.shankephone.mi.supersys.formbean.SuperAdminFindEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 9:14
 */
public interface SuperAdminService
{
    /**
     *超级管理员添加
     *@author：赵亮
     *@date：2018-07-23 9:15
    */
    ResultVO insertSuperAdmin(SysAdminEntity entity);

    /**
     *更新超级管理员
     *@author：赵亮
     *@date：2018-07-23 9:26
    */
    Integer updateSuperAdmin(SysAdminEntity entity);
    /**
     *查询管理员list
     *@author：赵亮
     *@date：2018-07-23 9:32
    */
    Pager<Map<String, Object>> getSuperAdminList(SuperAdminFindEntity findEntity);

    /**
     *初始化密码
     *@author：赵亮
     *@date：2018-07-23 10:03
    */
    Integer initPassword(SuperAdminFindEntity findEntity);

    /**
     *管理员登陆
     *@author：赵亮
     *@date：2018-07-23 10:39
    */
    ResultVO superAdminLogin(SysAdminEntity entity,HttpServletRequest request,HttpServletResponse response);

    /**
     * 批量删除管理员
     *
     * @param int[]
     * @author：郝伟州
     * @date：2018-08-14 19:26
     */

      Integer deleteAdmin(DeleteAdminFormBean entity);
}
