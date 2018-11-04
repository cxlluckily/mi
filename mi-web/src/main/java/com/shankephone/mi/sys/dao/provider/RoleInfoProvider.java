package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.SysRoleEntity;
import com.shankephone.mi.model.SysRoleTreeEntity;
import com.shankephone.mi.sys.formbean.RoleInfoFindEntity;
import com.shankephone.mi.sys.vo.RoleInfoVO;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

/**
 * 角色信息Sql provider
 *
 * @author 司徒彬
 * @date 2018 -06-21 14:22
 */
public class RoleInfoProvider {

    /**
     * Get role pager info string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRolePagerInfo(RoleInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
        if(ObjectUtils.isNotEmpty(findEntity.getRoleName())) {
        	sbSql.append(" AND roleName LIKE concat('%',#{roleName},'%') ");
        }

        if (ObjectUtils.isNotEmpty(findEntity.getStatus())&&!"".equals(findEntity.getStatus())&&!"all".equals(findEntity.getStatus()))
        {
            sbSql.append(" AND  status = #{status} ");
        }

        sbSql.append("   LIMIT #{start}, #{limit} ");
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();

    }

    /**
     * Get role pager total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRolePagerTotal(RoleInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE  operationSubjectId = #{operationSubjectId} ");
        if(ObjectUtils.isNotEmpty(findEntity.getRoleName())) {
            sbSql.append(" AND roleName LIKE concat('%',#{roleName},'%') ");
        }

        if (ObjectUtils.isNotEmpty(findEntity.getStatus())&&!"".equals(findEntity.getStatus())&&!"all".equals(findEntity.getStatus()))
        {
            sbSql.append(" AND  status = #{status} ");
        }
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }

    /**
     * Insert role info string.
     *
     * @param roleInfoVO the role info vo
     * @return the string
     */
    public String insertRoleInfo(RoleInfoVO roleInfoVO) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("            sys_role ");
        sbSql.append("            ( ");
        sbSql.append("               operationSubjectId, ");
        sbSql.append("               roleCode, ");
        sbSql.append("               roleName, ");
        sbSql.append("               status, ");
        sbSql.append("               wasCanDelete, ");
        sbSql.append("               createUser, ");
        sbSql.append("               modifyUser ");
        sbSql.append("            ) ");
        sbSql.append("          VALUES ");
        sbSql.append("            ( ");
        sbSql.append("               #{operationSubjectId}, ");
        sbSql.append("               #{roleCode}, ");
        sbSql.append("               #{roleName}, ");
        sbSql.append("               #{status}, ");
        sbSql.append("               #{wasCanDelete}, ");
        sbSql.append("               #{operationUserName}, ");
        sbSql.append("               #{operationUserName} ");
        sbSql.append("            ) ");

        return sbSql.toString();
    }

    /**
     * Insert role tree info string.
     *
     * @param roleTreeEntity the role tree entity
     * @return the string
     */
    public String insertRoleTreeInfo(SysRoleTreeEntity roleTreeEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("            sys_role_tree ");
        sbSql.append("            ( ");
        sbSql.append("               roleId, ");
        sbSql.append("               functionTreeId ");
        sbSql.append("            ) ");
        sbSql.append("          VALUES ");
        sbSql.append("            ( ");
        sbSql.append("               #{roleId}, ");
        sbSql.append("               #{functionTreeId} ");
        sbSql.append("            ) ");

        return sbSql.toString();
    }

    /**
     * Update role info string.
     *
     * @param roleEntity the role entity
     * @return the string
     */
    public String updateRoleInfo(SysRoleEntity roleEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("            sys_role ");
        sbSql.append("          SET ");
        sbSql.append("            operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("            roleName = #{roleName}, ");
        sbSql.append("            status = #{status}, ");
        sbSql.append("            modifyUser = #{operationUserName}, ");
        sbSql.append("            roleCode = #{roleCode} ");
        sbSql.append("          WHERE ");
        sbSql.append("            roleId = #{roleId} ");

        return sbSql.toString();
    }

    /**
     * Delete role tree string.
     *
     * @param roleId the role id
     * @return the string
     */
    public String deleteRoleTree(Long roleId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM sys_role_tree ");
        sbSql.append(" WHERE roleId = #{roleId} ");
        return sbSql.toString();
    }

    /**
     * Delete role info string.
     *
     * @param roleId the role id
     * @return the string
     */
    public String deleteRoleInfo(Long roleId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_role SET status = 'stop_using' ");
        sbSql.append(" WHERE roleId = #{roleId} ");
        return sbSql.toString();
    }

    /**
     * Gets role info.
     *
     * @param roleId the role id
     * @return the role info
     */
    public String getRoleInfo(Long roleId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE roleId = #{roleId} ");

        return sbSql.toString();
    }

    /**
     * Get tree info string.
     *
     * @param roleId the role id
     * @return the string
     */
    public String getTreeInfo(Long roleId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   tree.functionTreeId dataNodeId, ");
        sbSql.append("   treeName text, ");
        sbSql.append("   tree.parentTreeId dataParentNodeId, ");
        sbSql.append("   tree.treeType, ");
        sbSql.append("   CASE WHEN ( ");
        sbSql.append("               SELECT count(1) ");
        sbSql.append("               FROM sys_role_tree ");
        sbSql.append("               WHERE roleId = #{roleId} AND sys_role_tree.functionTreeId = tree.functionTreeId) > 0 ");
        sbSql.append("     THEN TRUE ");
        sbSql.append("   ELSE FALSE END checked ");
        sbSql.append(" FROM sys_function_tree tree ");
        sbSql.append(" WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" ORDER BY parentTreeId, sort ");
        return sbSql.toString();
    }


    public String validateRoleNameExist(RoleInfoFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE roleName = #{roleName} ");
        sbSql.append("       AND roleId <> #{roleId} ");
        sbSql.append("       AND operationSubjectId = #{operationSubjectId} ");

        return sbSql.toString();
    }
    
    public String getAllRoles(RoleInfoFindEntity findEntity) {
    	StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
        if(null != findEntity.getStatus() && !"".equals(findEntity.getStatus())) {
        	sbSql.append(" and status = #{status} ");
        }
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();
    }
}
