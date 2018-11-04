package com.shankephone.mi.task.dao.provider;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.StockWarehouseEntity;
import com.shankephone.mi.model.TaskPendingTaskEntity;
import com.shankephone.mi.task.enumeration.PendTaskStatus;
import com.shankephone.mi.task.formbean.PendingTaskFindEntity;
import com.shankephone.mi.task.vo.StockWarehouseEntityVO;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-08-06 10:28
 */
public class PendingTaskProvider
{
    public String insertOne(TaskPendingTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   task_pending_task ");
        sbSql.append("   ( ");
        sbSql.append("      taskType, ");
        sbSql.append("      sourceId, ");
        sbSql.append("      status, ");
        sbSql.append("      taskPersonId, ");
        sbSql.append("      title, ");
        sbSql.append("      busiNo, ");
        sbSql.append("      taskDescribe, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{taskType}, ");
        sbSql.append("      #{sourceId}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{taskPersonId}, ");
        sbSql.append("      #{title}, ");
        sbSql.append("      #{busiNo}, ");
        sbSql.append("      #{taskDescribe}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOne(TaskPendingTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   task_pending_task ");
        sbSql.append(" SET ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   pendingTaskId = #{pendingTaskId} ");
        return sbSql.toString();
    }

    public String updateTaskOver(TaskPendingTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   task_pending_task ");
        sbSql.append(" SET ");
        sbSql.append("   status = '" + PendTaskStatus.OVER.getCode() + "', ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   busiNo = #{busiNo} and status<>'"+ PendTaskStatus.OVER.getCode()+"' ");
        return sbSql.toString();
    }

    public String updateTaskSatuts(TaskPendingTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   task_pending_task ");
        sbSql.append(" SET ");
        sbSql.append("   status =  #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   busiNo = #{busiNo} ");
        sbSql.append("  AND taskType = #{taskType} ");
        return sbSql.toString();
    }


    public String getPendingTaskList(PendingTaskFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT *,DATE_FORMAT(task_pending_task.createTime, '%Y-%m-%d') AS addTime ");
        sbSql.append(" FROM task_pending_task ");
        sbSql.append(" WHERE taskPersonId = #{operationUserId} ");
        sbSql.append("       AND status!='" + PendTaskStatus.OVER.getCode() + "'");
        //TODO 目前就查询维修申请几个任务类型，记得删除 下面这句代码 PendTaskTypeEnum
        //sbSql.append("       AND taskType IN ('useApply','toBeDispatch','toBeRepair','toBeEvaluate','noRepair') " );
        sbSql.append(" ORDER BY createTime DESC  ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getPendingTaskListCount(PendingTaskFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount  ");
        sbSql.append(" FROM task_pending_task ");
        sbSql.append(" WHERE taskPersonId = #{operationUserId} ");
        sbSql.append("       AND status!='" + PendTaskStatus.OVER.getCode() + "'");
        return sbSql.toString();

    }

    public String getApplyReceivePersonList(StockWarehouseEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT distinct sys_range_role.userId ");
        sbSql.append(" FROM sys_range_role ");
        sbSql.append("   INNER JOIN sys_range_role_detail ");
        sbSql.append("     ON sys_range_role_detail.rangeRoleId = sys_range_role.rangeRoleId ");
        sbSql.append("   INNER JOIN sys_user ");
        sbSql.append("     ON sys_user.userId = sys_range_role.userId ");
        sbSql.append(" WHERE rangeType = '" + RangeRoleTypeEnum.WAREHOUSE.getValue() + "' ");
        sbSql.append("       AND sys_range_role_detail.id = #{warehouseId} ");
        sbSql.append("       AND sys_user.status = '" + StatusEnum.START_USING.getValue() + "' ");

        return sbSql.toString();

    }



    public String getReceivePersonList(StockWarehouseEntityVO entity)
    {
        StringBuilder sbSql = new StringBuilder();

        sbSql.append(" SELECT distinct  sys_range_role.userId from sys_function_tree ");
        sbSql.append(" INNER JOIN sys_role_tree on (sys_function_tree.permissionCode in ("+StringUtils.listToString(entity.getPermissionCodes()) +") and  sys_function_tree.functionTreeId=sys_role_tree.functionTreeId) ");
        sbSql.append(" INNER JOIN sys_user_role on sys_role_tree.roleId =sys_user_role.roleId ");
        sbSql.append(" INNER  JOIN sys_user on sys_user_role.userId=sys_user.userId ");
        sbSql.append(" INNER  JOIN sys_range_role on (sys_range_role.userId=sys_user.userId and sys_range_role.rangeType='" + RangeRoleTypeEnum.WAREHOUSE.getValue() + "') ");

        sbSql.append("   INNER JOIN sys_range_role_detail ");
        sbSql.append("     ON sys_range_role_detail.rangeRoleId = sys_range_role.rangeRoleId ");

        sbSql.append(" WHERE sys_range_role_detail.id = #{warehouseId} ");
        sbSql.append("       AND sys_user.status = '" + StatusEnum.START_USING.getValue() + "' ");

        return sbSql.toString();

    }


    public String getPersonPermissionCode(StockWarehouseEntityVO entity)
    {
        StringBuilder sbSql = new StringBuilder();

        sbSql.append(" SELECT distinct  sys_user.userId from sys_function_tree ");
        sbSql.append(" INNER JOIN sys_role_tree on (sys_function_tree.permissionCode in ("+StringUtils.listToString(entity.getPermissionCodes()) +") and  sys_function_tree.functionTreeId=sys_role_tree.functionTreeId) ");
        sbSql.append(" INNER JOIN sys_user_role on sys_role_tree.roleId =sys_user_role.roleId ");
        sbSql.append(" INNER  JOIN sys_user on sys_user_role.userId=sys_user.userId ");

        sbSql.append(" WHERE   sys_user.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" AND   sys_user.userId =  #{permissionId} ");

        return sbSql.toString();

    }

    public String deleteTask(TaskPendingTaskEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM task_pending_task ");
        sbSql.append(" WHERE sourceId = #{sourceId} ");
        sbSql.append("       AND taskType = #{taskType} ");

        return sbSql.toString();

    }


    public String getPaiDuanRenYuanList(PendingTaskFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT distinct sys_user.userId from sys_function_tree ");
        sbSql.append(" INNER JOIN sys_role_tree on (sys_function_tree.permissionCode in("+findEntity.getPermissionCode()+") and  sys_function_tree.functionTreeId=sys_role_tree.functionTreeId) ");
        sbSql.append(" INNER JOIN sys_user_role on sys_role_tree.roleId =sys_user_role.roleId ");
        sbSql.append(" INNER  JOIN sys_user on sys_user_role.userId=sys_user.userId ");
        sbSql.append(" INNER  JOIN sys_range_role on (sys_range_role.userId=sys_user.userId and sys_range_role.rangeType='" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "') ");
        sbSql.append(" INNER  JOIN sys_range_role_detail on sys_range_role.rangeRoleId = sys_range_role_detail.rangeRoleId ");

        sbSql.append(" INNER  JOIN org_work_section_station on org_work_section_station.workSectionId = sys_range_role_detail.id ");
        // sbSql.append(" WHERE sys_user.operationSubjectId=#{operationSubjectId} ");
        //sbSql.append(" AND org_work_section_station.workSectionId=#{workSectionId} ");
        sbSql.append(" WHERE org_work_section_station.stationId=#{stationId} ");
        return sbSql.toString();

    }
}
