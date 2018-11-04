package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.util.ObjectUtils;

/**
 * 个人信息 provider
 *
 * @author 司徒彬
 * @date 2018 /6/28 10:08
 */
public class PersonalInformationProvider {
    /**
     * Ger personal info string.
     *
     * @param userId the user id
     * @return the string
     */
    public String gerPersonalInfo(Long userId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   user.*, ");
        sbSql.append("   subject.subjectName, ");
        sbSql.append("   organization.orgName, ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT group_concat(roleName) ");
        sbSql.append("     FROM sys_role role INNER JOIN sys_user_role userRole ");
        sbSql.append("         ON userRole.roleId = role.roleId ");
        sbSql.append("     WHERE userRole.userId = user.userId) roleName ");
        sbSql.append(" FROM sys_user user ");
        sbSql.append("   INNER JOIN sys_operation_subject subject ON subject.operationSubjectId = user.operationSubjectId ");
        sbSql.append("   INNER JOIN org_organization organization ON organization.orgId = user.orgId ");
        sbSql.append(" WHERE userId = #{userId} ");

        return sbSql.toString();
    }

    /**
     * Update personal info string.
     *
     * @param user the user
     * @return the string
     */
    public String updatePersonalInfo(SysUserEntity user) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE sys_user ");
        sbSql.append(" SET sex      = #{sex}, email = #{email}, ");
        sbSql.append("   phone = #{phone}, remark = #{remark}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        if (ObjectUtils.isNotEmpty(user.getPhotoUrl())) {
            sbSql.append("   , photoUrl = #{photoUrl} ");
        }
        sbSql.append(" WHERE userId = #{operationUserId} ");
        return sbSql.toString();
    }

    public String getUserInfo(Long userId){
        return "select password from sys_user where userId = #{userId}";
    }

    public String modifyPassword(SysUserEntity userEntity){
        return "update sys_user set password = #{newPassword},openId='' where userId = #{operationUserId}";
    }
}
