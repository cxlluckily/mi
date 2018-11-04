package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.SysRangeRoleDetailEntity;
import com.shankephone.mi.model.SysRangeRoleEntity;
import com.shankephone.mi.model.SysUserRoleEntity;
import com.shankephone.mi.sys.formbean.UserPermissionFindEntity;

/**
 * 用户授权 Provider
 *
 * @author 司徒彬
 * @date 2018 /6/27 10:41
 */
public class UserPermissionProvider {

    /**
     * Get role info list string.
     *
     * @param operationSubjectId the operation subject id
     * @return the string
     */
    public String getRoleInfoList(Long operationSubjectId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   roleId, ");
        sbSql.append("   roleName ");
        sbSql.append(" FROM sys_role ");
        sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
        sbSql.append("       AND status = '" + StatusEnum.START_USING.getValue() + "' ");

        return sbSql.toString();
    }

    /**
     * Get warehouse info list string.
     *
     * @param operationSubjectId the operation subject id
     * @return the string
     */
    public String getWarehouseInfoList(Long operationSubjectId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_warehouse ");
        sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
        sbSql.append("       AND status = '" + StatusEnum.START_USING.getValue() + "' ");

        return sbSql.toString();
    }

    /**
     * Get work section list string.
     *
     * @param operationSubjectId the operation subject id
     * @return the string
     */
    public String getWorkSectionList(Long operationSubjectId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   workSectionId, ");
        sbSql.append("   sectionName ");
        sbSql.append(" FROM org_work_section ");
        sbSql.append(" WHERE operationSubjectId = #{operationSubjectId} ");
        sbSql.append("       AND status = '" + StatusEnum.START_USING.getValue() + "' ");

        return sbSql.toString();
    }

    /**
     * Delete role info by user id string.
     *
     * @param userId the user id
     * @return the string
     */
    public String deleteRoleInfoByUserId(Long userId) {
        return "delete from sys_user_role where userId = #{userId}";
    }

    /**
     * Insert user role info string.
     *
     * @param userRoleEntity the user role entity
     * @return the string
     */
    public String insertUserRoleInfo(SysUserRoleEntity userRoleEntity) {
        return "INSERT INTO sys_user_role (roleId, userId) VALUES (#{roleId}, #{userId})";
    }

    /**
     * Delete range role detail by user id string.
     *
     * @param userId the user id
     * @return the string
     */
    public String deleteRangeRoleDetailByUserId(Long userId) {
        return "DELETE FROM sys_range_role_detail where userId = #{userId}";
    }

    /**
     * Delete range role info by user id string.
     *
     * @param userId the user id
     * @return the string
     */
    public String deleteRangeRoleInfoByUserId(Long userId) {
        return "DELETE FROM sys_range_role where userId = #{userId}";
    }

    /**
     * Insert range role info string.
     *
     * @param rangeRoleEntity the range role entity
     * @return the string
     */
    public String insertRangeRoleInfo(SysRangeRoleEntity rangeRoleEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   sys_range_role ");
        sbSql.append("   ( ");
        sbSql.append("      userId, ");
        sbSql.append("      rangeType, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{userId}, ");
        sbSql.append("      #{rangeType}, ");
        sbSql.append("      #{createUser} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Insert range role detail string.
     *
     * @param roleDetailEntity the role detail entity
     * @return the string
     */
    public String insertRangeRoleDetail(SysRangeRoleDetailEntity roleDetailEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   sys_range_role_detail ");
        sbSql.append("   ( ");
        sbSql.append("      rangeRoleId, ");
        sbSql.append("      userId, ");
        sbSql.append("      id ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{rangeRoleId}, ");
        sbSql.append("      #{userId}, ");
        sbSql.append("      #{id} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Get user info by id string.
     *
     * @param userId the user id
     * @return the string
     */
    public String getUserInfoById(Long userId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_user ");
        sbSql.append(" WHERE userId = #{userId} ");

        return sbSql.toString();
    }

    /**
     * Get role info list by user id string.
     *
     * @param userId the user id
     * @return the string
     */
    public String getRoleInfoListByUserId(Long userId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   role.*, ");
        sbSql.append("   CASE WHEN ( ");
        sbSql.append("               SELECT count(1) ");
        sbSql.append("               FROM sys_user_role userRole ");
        sbSql.append("               WHERE userRole.userId = #{userId} ");
        sbSql.append("                     AND role.roleId = userRole.roleId) > 0 ");
        sbSql.append("     THEN TRUE ");
        sbSql.append("   ELSE FALSE END isChecked ");
        sbSql.append(" FROM sys_role role INNER JOIN sys_user user ");
        sbSql.append("     ON user.operationSubjectId = role.operationSubjectId ");
        sbSql.append(" WHERE  role.status='" + StatusEnum.START_USING.getValue() + "' and  user.userId = #{userId} ");

        return sbSql.toString();
    }

    /**
     * Get range role info list string.
     *
     * @param fIndEntity the f ind entity
     * @return the string
     */
    public String getRangeRoleInfoList(UserPermissionFindEntity fIndEntity) {

        if (fIndEntity.getRangeType().equalsIgnoreCase(RangeRoleTypeEnum.WAREHOUSE.getValue())) {
            StringBuilder sbSql = new StringBuilder();
            sbSql.append(" SELECT ");
            sbSql.append("   warehouse.*, ");
            sbSql.append("   CASE WHEN ( ");
            sbSql.append("               SELECT count(1) ");
            sbSql.append("               FROM sys_range_role rangeRole ");
            sbSql.append("                 INNER JOIN sys_range_role_detail rangeRoleDetail ");
            sbSql.append("                   ON rangeRole.rangeRoleId = rangeRoleDetail.rangeRoleId ");
            sbSql.append("               WHERE rangeRole.userId = #{userId} AND rangeRoleDetail.id = warehouse.warehouseId ");
            sbSql.append("                     AND rangeRole.rangeType = #{rangeType} ");
            sbSql.append("             ) > 0 ");
            sbSql.append("     THEN TRUE ");
            sbSql.append("   ELSE FALSE END isChecked ");
            sbSql.append(" FROM stock_warehouse warehouse ");
            sbSql.append("   INNER JOIN sys_user user ON user.operationSubjectId = warehouse.operationSubjectId ");
            sbSql.append(" WHERE user.userId = #{userId} ");
            sbSql.append("       AND warehouse.status='" + StatusEnum.START_USING.getValue() + "' ");

            return sbSql.toString();
        } else {
            StringBuilder sbSql = new StringBuilder();
            sbSql.append(" SELECT ");
            sbSql.append("   section.*, ");
            sbSql.append("   CASE WHEN ( ");
            sbSql.append("               SELECT count(1) ");
            sbSql.append("               FROM sys_range_role rangeRole ");
            sbSql.append("                 INNER JOIN sys_range_role_detail rangeRoleDetail ");
            sbSql.append("                   ON rangeRole.rangeRoleId = rangeRoleDetail.rangeRoleId ");
            sbSql.append("               WHERE rangeRole.userId = #{userId} AND rangeRoleDetail.id = section.workSectionId ");
            sbSql.append("                     AND rangeRole.rangeType = #{rangeType} ");
            sbSql.append("             ) > 0 ");
            sbSql.append("     THEN TRUE ");
            sbSql.append("   ELSE FALSE END isChecked ");
            sbSql.append(" FROM org_work_section section ");
            sbSql.append("   INNER JOIN sys_user user ON user.operationSubjectId = section.operationSubjectId ");
            sbSql.append(" WHERE user.userId = #{userId} ");
            sbSql.append("       AND section.status='" + StatusEnum.START_USING.getValue() + "' ");

            return sbSql.toString();
        }
    }

    public String getUserWarehouseRangeIds(Long userId) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT sys_range_role_detail.id ");
        sbSql.append(" FROM sys_range_role_detail ");
        sbSql.append("   INNER JOIN sys_range_role ");
        sbSql.append("     ON sys_range_role.rangeRoleId = sys_range_role_detail.rangeRoleId ");
        sbSql.append(" WHERE rangeType = '" + RangeRoleTypeEnum.WAREHOUSE.getValue() + "' ");
        sbSql.append("       AND sys_range_role.userId = #{userId} ");

        return sbSql.toString();

    }
}
