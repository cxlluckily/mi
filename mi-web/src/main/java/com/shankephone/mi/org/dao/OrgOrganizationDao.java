package com.shankephone.mi.org.dao;

import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.org.dao.provider.OrgOrganizationProvider;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-21 14:04
 */
@Repository
public interface OrgOrganizationDao
{

    /**
     *根据用户表的id返回对应用户的功能树节点
     *@author：赵亮
     *@date：2018-06-21 15:24
    */
    @SelectProvider(type = OrgOrganizationProvider.class, method = "listOrganization")
    List<OrgOrganizationEntity> listOrganization(OrganizationFindEntity findEntity);

    @SelectProvider(type = OrgOrganizationProvider.class, method = "listOrganizationTotalCount")
    Integer listOrganizationTotalCount(OrganizationFindEntity findEntity);

    /**
     *添加组织机构
     *@author：赵亮
     *@date：2018-06-21 15:24
    */
    @InsertProvider(type = OrgOrganizationProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "orgId")
    Long insertOne(OrgOrganizationEntity entity);
    /**
     *获取平级的最后一个内部编号
     *@author：赵亮
     *@date：2018-06-21 15:32
     */
    @SelectProvider(type = OrgOrganizationProvider.class, method = "getTheNextInternalNumber")
    String getTheNextInternalNumber(Long parentOrgId);
    /**
     *获取当前数据的internalNumber
     *@author：赵亮
     *@date：2018-06-21 15:48
     */
    @SelectProvider(type = OrgOrganizationProvider.class, method = "getParentInternalNumber")
    String getParentInternalNumber(long parentOrgId);

    /**
     *根据主键返回实体
     *@author：赵亮
     *@date：2018-06-21 17:20
     */
    @SelectProvider(type = OrgOrganizationProvider.class, method = "findOneById")
    OrgOrganizationEntity findOneById(Long orgId);
    /**
     *根据主键更新组织机构
     *@author：赵亮
     *@date：2018-06-21 19:36
    */
    @UpdateProvider(type = OrgOrganizationProvider.class, method = "updateOne")
    Integer updateOne(OrgOrganizationEntity entity);

    /**
     *更新组织机构为无效
     *@author：赵亮
     *@date：2018-06-21 19:44
    */
    @UpdateProvider(type = OrgOrganizationProvider.class, method = "updateStatusIsStopUsing")
    Integer updateStatusIsStopUsing(Long orgId);

    @SelectProvider(type = OrgOrganizationProvider.class, method = "tree")
	List<OrgOrganizationEntity> tree(OrganizationFindEntity findEntity);
    
    @SelectProvider(type = OrgOrganizationProvider.class, method = "treeCount")
    Integer treeCount(OrgOrganizationEntity findEntity);

}
