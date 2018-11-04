package com.shankephone.mi.org.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import com.shankephone.mi.org.vo.OrgOrganizationListVO;

/**
 * @author 赵亮
 * @date 2018-06-21 10:37
 */
public interface OrgOrganizationService
{
    /**
     *根据用户表的id返回对应用户的功能树节点
     *@author：赵亮
     *@date：2018-06-21 15:24
     */
    Pager<OrgOrganizationListVO> listOrganization(OrganizationFindEntity findEntity);

    /**
     *添加组织机构
     *@author：赵亮
     *@date：2018-06-21 15:24
     */
    String insertOne(OrgOrganizationEntity entity);

    /**
     *根据主键返回实体
     *@author：赵亮
     *@date：2018-06-21 17:20
     */
    OrgOrganizationEntity findOneById(Long orgId);


    /**
     *根据主键更新组织机构
     *@author：赵亮
     *@date：2018-06-21 19:36
    */
    Integer updateOne(OrgOrganizationEntity entity);

    /**
     *更新组织机构为无效
     *@author：赵亮
     *@date：2018-06-21 19:44
    */
    Integer updateStatusIsStopUsing(Long orgId);
    
    public Pager<OrgOrganizationListVO> tree(OrganizationFindEntity findEntity);
}
