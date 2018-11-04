package com.shankephone.mi.org.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-06-21 14:05
 */
public class OrgOrganizationProvider
{
    public String listOrganization(OrganizationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} ");
//        sbSql.append("   LIMIT #{start}, #{limit} ");
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }
    
    public String tree(OrganizationFindEntity findEntity)
    {
    	Long parentId = findEntity.getOrgId();
    	if(parentId == null){
    		findEntity.setOrgId(-1l);
    	}
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} and parentOrgId = #{orgId}");
//        sbSql.append("   LIMIT #{start}, #{limit} ");
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }
    
    public String treeCount(OrgOrganizationEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) AS totalCount ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} and parentOrgId = #{orgId} ");
        return sbSql.toString();
    }

    public String listOrganizationTotalCount(OrganizationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) AS totalCount ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} ");
        return sbSql.toString();
    }
    
    /*public String tree(OrganizationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }*/


    public String insertOne(OrgOrganizationEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("    org_organization ");
        sbSql.append("    ( ");
        //        sbSql.append("       orgId, ");
        sbSql.append("       operationSubjectId, ");
        sbSql.append("       parentOrgId, ");
        sbSql.append("       orgName, ");
        sbSql.append("       internalNumber, ");
        sbSql.append("       headPerson, ");
        sbSql.append("       contactNumber, ");
        sbSql.append("       description, ");
        sbSql.append("       wasCanDelete, ");
        sbSql.append("       status, ");
        sbSql.append("       createUser, ");
        sbSql.append("       modifyUser ");
        sbSql.append("    ) ");
        sbSql.append("  VALUES ");
        sbSql.append("    ( ");
        //        sbSql.append("       #{orgId}, ");
        sbSql.append("       #{operationSubjectId}, ");
        sbSql.append("       #{parentOrgId}, ");
        sbSql.append("       #{orgName}, ");
        sbSql.append("       #{internalNumber}, ");
        sbSql.append("       #{headPerson}, ");
        sbSql.append("       #{contactNumber}, ");
        sbSql.append("       #{description}, ");
        sbSql.append("       #{wasCanDelete}, ");
        sbSql.append("       #{status}, ");
        sbSql.append("       #{createUser}, ");
        sbSql.append("       #{modifyUser} ");
        sbSql.append("    ) ");
        return sbSql.toString();

    }

    /**
     * 获取平级的最后一个内部编号
     *
     * @author：赵亮
     * @date：2018-06-21 15:32
     */
    public String getTheSameLevelLastInternalNumber(Long parentOrgId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT internalNumber ");
        sbSql.append(" FROM org_organization ");
        sbSql.append(" WHERE parentOrgId = #{parentOrgId} ");
        sbSql.append(" ORDER BY internalNumber DESC ");
        sbSql.append(" LIMIT 1 ");

        return sbSql.toString();

    }

    /**
     * 获取平级的最后一个内部编号
     *
     * @author：赵亮
     * @date：2018-06-21 15:32
     */
    public String getTheNextInternalNumber(Long parentOrgId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT internalNumber ");
        sbSql.append(" FROM org_organization ");
        sbSql.append(" WHERE parentOrgId = #{parentOrgId} ");
        sbSql.append(" ORDER BY internalNumber DESC ");
        sbSql.append(" LIMIT 1 ");

        return sbSql.toString();

    }

    /**
     * 获取当前数据的internalNumber
     *
     * @author：赵亮
     * @date：2018-06-21 15:56
     */
    public String getParentInternalNumber(long parentOrgId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT internalNumber ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE orgId = #{parentOrgId} ");

        return sbSql.toString();

    }

    /**
     * 根据主键返回实体
     *
     * @author：赵亮
     * @date：2018-06-21 17:20
     */
    public String findOneById(Long orgId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM org_organization ");
        sbSql.append("   WHERE orgId = #{orgId} ");
        return sbSql.toString();
    }

    /**
     * 根据主键更新组织机构
     *
     * @author：赵亮
     * @date：2018-06-21 19:36
     */
    public String updateOne(OrgOrganizationEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("     org_organization ");
        sbSql.append("   SET ");
        sbSql.append("     orgName = #{orgName}, ");
        sbSql.append("     headPerson = #{headPerson}, ");
        sbSql.append("     contactNumber = #{contactNumber}, ");
        sbSql.append("     description = #{description}, ");
        sbSql.append("     modifyUser = #{modifyUser}, ");
        sbSql.append("     status = #{status} ");
        sbSql.append("   WHERE ");
        sbSql.append("     orgId = #{orgId} ");

        return sbSql.toString();

    }

    /**
     * 更新组织机构为无效
     *
     * @author：赵亮
     * @date：2018-06-21 19:44
     */
    public String updateStatusIsStopUsing(Long orgId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("     org_organization ");
        sbSql.append("   SET ");
        sbSql.append("     modifyUser = #{modifyUser}, ");
        sbSql.append("     status = '"+ StatusEnum.STOP_USING.getValue() +"' ");
        sbSql.append("   WHERE ");
        sbSql.append("     orgId = #{orgId} ");
        return sbSql.toString();
    }
}
