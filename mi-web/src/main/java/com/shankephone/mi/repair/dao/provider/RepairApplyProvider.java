package com.shankephone.mi.repair.dao.provider;

import com.shankephone.mi.common.enumeration.RangeRoleTypeEnum;
import com.shankephone.mi.common.enumeration.RepairApplyStatusEnum;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.*;
import com.shankephone.mi.repair.enumeration.FaultType;
import com.shankephone.mi.repair.formbean.GetDrviceInforByQrcodeFindEntity;
import com.shankephone.mi.repair.formbean.RepairApplyFindEntity;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.repair.vo.ReplaceSparePartVO;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.StringUtils;

import java.util.stream.Collectors;

/**
 * 维修申请Provider
 *
 * @author 司徒彬
 * @date 2018 /8/2 10:10
 */
public class RepairApplyProvider
{

    /**
     * Get line and station info string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getLineAndStationInfo(RepairApplyFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   line.lineId, ");
        sbSql.append("   lineName, ");
        sbSql.append("   lineCode, ");
        sbSql.append("   stationId, ");
        sbSql.append("   stationName, ");
        sbSql.append("   stationCode ");
        sbSql.append(" FROM org_line line ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON line.lineId = station.lineId ");
        sbSql.append(" WHERE stationName LIKE concat('%', ifnull(#{searchContent}, ''), '%') ");
        sbSql.append("   AND line.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND station.status = '" + StatusEnum.START_USING.getValue() + "'");
        sbSql.append("   AND line.operationSubjectId = #{operationSubjectId}");
        sbSql.append("  ORDER BY lineCode ASC  ");
        return sbSql.toString();
    }

    /**
     * Maintenance apply id string.
     *
     * @param repairInfoVO the repair info vo
     * @return the string
     */
    public String insertRepairApplyInfo(RepairApplyInfoVO repairInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_apply ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      equipmentId, ");
        sbSql.append("      applyNO, ");
        sbSql.append("      lineId, ");
        sbSql.append("      stationId, ");
        sbSql.append("      categoryName, ");
        sbSql.append("      errorCode, ");
        sbSql.append("      breakDescribe, ");
        sbSql.append("      applyUser, ");
        sbSql.append("      applyDate, ");
        sbSql.append("      appointUser, ");
        sbSql.append("      appointUserId, ");
        sbSql.append("      appointDate, ");
        sbSql.append("      maintenanceUser, ");
        sbSql.append("      maintenanceUserID, ");
        sbSql.append("      arrivalTime, ");
        sbSql.append("      overTime, ");
        sbSql.append("      maintenanceDescribe, ");
        sbSql.append("      remark, ");
        sbSql.append("      applyStatus, ");
        sbSql.append("      speedAppraise, ");
        sbSql.append("      serviceAppraise, ");
        sbSql.append("      operationSpecificationAppraise, ");
        sbSql.append("      wasFinished, ");
        sbSql.append("      appraiseDescribe, ");
        sbSql.append("      noRepairReason, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser, ");
        sbSql.append("      appraiseWasFinished, ");
        sbSql.append("      createUserId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{equipmentId}, ");
        sbSql.append("      #{applyNO}, ");
        sbSql.append("      #{lineId}, ");
        sbSql.append("      #{stationId}, ");
        sbSql.append("      #{categoryName}, ");
        sbSql.append("      #{errorCode}, ");
        sbSql.append("      #{breakDescribe}, ");
        sbSql.append("      #{applyUser}, ");
        sbSql.append("      #{applyDate}, ");
        sbSql.append("      #{appointUser}, ");
        sbSql.append("      #{appointUserId}, ");
        sbSql.append("      #{appointDate}, ");
        sbSql.append("      #{maintenanceUser}, ");
        sbSql.append("      #{maintenanceUserID}, ");
        sbSql.append("      #{arrivalTime}, ");
        sbSql.append("      #{overTime}, ");
        sbSql.append("      #{maintenanceDescribe}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{applyStatus}, ");
        sbSql.append("      #{speedAppraise}, ");
        sbSql.append("      #{serviceAppraise}, ");
        sbSql.append("      #{operationSpecificationAppraise}, ");
        sbSql.append("      #{wasFinished}, ");
        sbSql.append("      #{appraiseDescribe}, ");
        sbSql.append("      #{noRepairReason}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser}, ");
        sbSql.append("      #{appraiseWasFinished}, ");
        sbSql.append("      #{createUserId} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Insert error fault string.
     *
     * @param faultEntity the fault entity
     * @return the string
     */
    public String insertErrorFault(MaintenanceErrorFaultEntity faultEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_error_fault ");
        sbSql.append("   ( ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      breakdownInfoId, ");
        sbSql.append("      faultType ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{breakdownInfoId}, ");
        sbSql.append("      #{faultType} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    /**
     * Insert repair apply pic string.
     *
     * @param picEntity the pic entity
     * @return the string
     */
    public String insertRepairApplyPic(MaintenanceApplyPicEntity picEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_apply_pic ");
        sbSql.append("   ( ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      picUrl, ");
        sbSql.append("      type, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{picUrl}, ");
        sbSql.append("      #{type}, ");
        sbSql.append("      #{createUser} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Get apply info by id string.
     *
     * @param applyId the apply id
     * @return the string
     */
    public String getApplyInfoById(Long applyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   line.lineId, ");
        sbSql.append("   lineName, ");
        sbSql.append("   apply.stationId, ");
        sbSql.append("   stationName, ");
        sbSql.append("   sparePart.sparePartTypeId, ");
        sbSql.append("   partType.categoryName, ");
        sbSql.append("   equipment.equipmentNO, ");
        sbSql.append("   apply.* ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN sys_operation_subject subject ");
        sbSql.append("          ON subject.operationSubjectId = apply.operationSubjectId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part sparePart ");
        sbSql.append("          ON sparePart.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type partType ");
        sbSql.append("          ON partType.sparePartTypeId = sparePart.sparePartTypeId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = station.lineId ");
        sbSql.append(" WHERE apply.maintenanceApplyId = #{applyId} ");

        return sbSql.toString();
    }

    /**
     * Get error phenomenon by apply id string.
     *
     * @param maintenanceApplyId the maintenanceApplyId
     * @return the string
     */
    public String getErrorPhenomenonByApplyId(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   errorFaultId, ");
        sbSql.append("   faultType, ");
        sbSql.append("   breakdown.* ");
        sbSql.append(" FROM maintenance_error_fault fault ");
        sbSql.append("        INNER JOIN part_breakdown_info breakdown ");
        sbSql.append("          ON fault.breakdownInfoId = breakdown.breakdownInfoId ");
        sbSql.append(" WHERE status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Get repair images string.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String getRepairImages(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   image.* ");
        sbSql.append(" FROM maintenance_apply_pic image ");
        sbSql.append(" WHERE image.maintenanceApplyId = #{maintenanceApplyId} ");
        sbSql.append(" ORDER BY createTime DESC ");

        return sbSql.toString();
    }

    public String deleteRepairedImage(Long maintenanceApplyId)
    {
        return "DELETE FROM maintenance_apply_pic WHERE maintenanceApplyId= #{maintenanceApplyId}  AND type= '" + FaultType.REPAIR.getCode() + "' ";
    }

    /**
     * Get copy people by apply id string.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String getCopyPeopleByApplyId(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("  maintenanceApplyId,");
        sbSql.append("   user.userId copyToUserId, ");
        sbSql.append("   realName copyToUser ");
        sbSql.append(" FROM maintenance_copy copy ");
        sbSql.append("        INNER JOIN sys_user user ");
        sbSql.append("          ON user.userId = copy.copyToUserId ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId}");

        return sbSql.toString();
    }

    /**
     * Get spare part change info string.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String getSparePartChangeInfo(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   oldQrCode, ");
        sbSql.append("   oldPart.partName       oldPartName, ");
        sbSql.append("   oldStock.serialNumber  oldSerialNumber, ");
        sbSql.append("   oldStock.inventoryType oldInventoryType, ");
        sbSql.append("   changeInfo.replaceCount , ");
        sbSql.append("   newQrCode, ");
        sbSql.append("   newPart.sparePartId, ");
        sbSql.append("   newPart.partName       newPartName, ");
        sbSql.append("   newStock.serialNumber  newSerialNumber, ");
        sbSql.append("   newStock.inventoryType  ");
        sbSql.append(" FROM maintenance_change_spare_part changeInfo ");
        sbSql.append("        LEFT JOIN part_spare_part oldPart ");
        sbSql.append("          ON changeInfo.oldSparePartId = oldPart.sparePartId ");
        sbSql.append("        LEFT JOIN stock_stock oldStock ");
        sbSql.append("          ON changeInfo.oldStockId = oldStock.stockId ");
        sbSql.append("        LEFT JOIN part_spare_part newPart ");
        sbSql.append("          ON changeInfo.newSparePartId = newPart.sparePartId ");
        sbSql.append("        LEFT JOIN stock_stock newStock ");
        sbSql.append("          ON changeInfo.newStockId = newStock.stockId ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }


    /**
     * Get copy people by apply ids string.
     *
     * @param idStr the id str
     * @return the string
     */
    public String getCopyPeopleByApplyIds(String idStr)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("  maintenanceApplyId,");
        sbSql.append("   user.userId copyToUserId, ");
        sbSql.append("   userName copyToUser ");
        sbSql.append(" FROM maintenance_copy copy ");
        sbSql.append("        INNER JOIN sys_user user ");
        sbSql.append("          ON user.userId = copy.copyToUserId ");
        sbSql.append(" WHERE maintenanceApplyId in ( " + idStr + " )");

        return sbSql.toString();
    }

    /**
     * Get equipment type string.
     *
     * @param operationSubjectId the operation subject id
     * @return the string
     */
    public String getEquipmentType(Long operationSubjectId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   sparePartTypeId, ");
        sbSql.append("   categoryName ");
        sbSql.append(" FROM part_spare_part_type ");
        sbSql.append(" WHERE parentPartId = -1 ");
        sbSql.append("   AND operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   AND status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" ORDER BY sort asc,categoryName asc");

        return sbSql.toString();
    }

    /**
     * Get equipment list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getEquipmentList(RepairApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   equipment.* ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append(" WHERE part.sparePartTypeId = #{sparePartTypeId} ");

        //sbSql.append("   AND lineId = #{lineId} ");
        sbSql.append("   AND stationId = #{stationId} ");
        sbSql.append("  AND equipment.status = '" + StatusEnum.START_USING.getValue() + "'");
        sbSql.append("  ORDER BY equipmentNo ASC ");
        return sbSql.toString();
    }

    /**
     * Get error phenomenon by equipment id string.
     *
     * @param equipmentId the equipment id
     * @return the string
     */
    public String getErrorPhenomenonByEquipmentId(Long equipmentId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   breakdown.* ");
        sbSql.append(" FROM operations_equipment equipment ");
        sbSql.append("        INNER JOIN part_breakdown_info breakdown ");
        sbSql.append("          ON equipment.sparePartId = breakdown.sparePartId ");
        sbSql.append(" WHERE breakdown.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND equipment.equipmentId = #{equipmentId} ");

        return sbSql.toString();
    }

    /**
     * Get concat people list string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getConcatPeopleList(RepairApplyFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   userId, ");
        sbSql.append("   realName, ");
        sbSql.append("   realNameAllPinYin, ");
        sbSql.append("   realNamePinYin, ");
        sbSql.append("   email, ");
        sbSql.append("   phone, ");
        sbSql.append("   position, ");
        sbSql.append("   workNumber, ");
        sbSql.append("   sex, ");
        sbSql.append("   photoUrl, ");
        sbSql.append("   orgName ");
        sbSql.append(" FROM sys_user user ");
        sbSql.append("        LEFT JOIN org_organization organization ");
        sbSql.append("          ON organization.orgId = user.orgId ");
        sbSql.append(" WHERE (realName LIKE concat('%', #{searchContent}, '%') ");
        sbSql.append("          OR realNameAllPinYin LIKE concat('%', #{searchContent}, '%') ");
        sbSql.append("          OR realNamePinYin LIKE concat('%', #{searchContent}, '%') ");
        sbSql.append("           ) ");
        sbSql.append("   AND user.operationSubjectId = #{operationSubjectId} ");
        sbSql.append("   AND user.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" ORDER BY ");
        sbSql.append("   realNameAllPinYin ");

        return sbSql.toString();
    }

    /**
     * Delete copy info string.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String deleteCopyInfo(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE ");
        sbSql.append(" FROM maintenance_copy ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Delete copy info string.
     * 删除错误故障信息
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String deleteMmaintenanceErrorFault(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE ");
        sbSql.append(" FROM maintenance_error_fault ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }


    /**
     * Delete copy info string.
     * 删除维修方案信息
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String deleteMaintenanceSolution(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE ");
        sbSql.append(" FROM maintenance_solution ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");
        return sbSql.toString();
    }

    /**
     * Insert copy info string.
     *
     * @param copyEntity the copy entity
     * @return the string
     */
    public String insertCopyInfo(MaintenanceCopyEntity copyEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_copy ");
        sbSql.append("   ( ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      copyToUser, ");
        sbSql.append("      copyToUserId, ");
        sbSql.append("      content ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{copyToUser}, ");
        sbSql.append("      #{copyToUserId}, ");
        sbSql.append("      #{content} ");
        sbSql.append("   ) ");


        return sbSql.toString();
    }

    /**
     * Get repair apply page info string.
     * 维修、评价来这页
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRepairApplyPageInfo(RepairApplyFindEntity findEntity)
    {
        String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
                .map(status -> "'" + status + "'").collect(Collectors.joining(","));
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   lineName,equipmentNo, ");
        sbSql.append("   stationName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   equipment.location, ");
        sbSql.append("   apply.*, ");
        sbSql.append("   ( SELECT group_concat(breakdown.breakAbbreviated) ");
        sbSql.append("     FROM maintenance_error_fault fault ");
        sbSql.append("            INNER JOIN part_breakdown_info breakdown ");
        sbSql.append("              ON fault.breakdownInfoId = breakdown.breakdownInfoId ");
        sbSql.append("     WHERE fault.maintenanceApplyId = apply.maintenanceApplyId ) errorPhenomenon ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        if ("pc".equalsIgnoreCase(findEntity.getType()))
        {
            sbSql.append(" WHERE applyDate BETWEEN #{beginTime} AND #{endTime} ");
            if (ObjectUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("   AND type.categoryName  LIKE concat('%', #{categoryName}, '%')  ");
            }

            if (ObjectUtils.isNotEmpty(findEntity.getMaintenanceUser()))
            {
                sbSql.append("   AND ifnull(maintenanceUser, '') LIKE concat('%', #{maintenanceUser}, '%') ");
            }
            if (findEntity.getCreateUserId().intValue() != 0)
            {
                sbSql.append("   AND apply.createUserId = #{createUserId} ");
            }
            if (ObjectUtils.isNotEmpty(findEntity.getStationName()))
            {
                sbSql.append("   AND ifnull(station.stationName, '') LIKE concat('%', #{stationName}, '%') ");
            }
            sbSql.append("   AND apply.applyStatus IN (" + applyStatus + ") ");

            if (findEntity.getCreateUserId().intValue() == 0)
            {
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
            }
        }
        else
        {
            sbSql.append(" WHERE 1=1  ");
            if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
            {
                sbSql.append(" AND apply.applyDate >= #{beginTime} ");
            }
            if(StringUtils.isNotEmpty(findEntity.getEndTime()))
            {
                sbSql.append(" AND apply.applyDate  <= #{endTime} ");
            }
            if(StringUtils.isNotEmpty(findEntity.getApplyNO()))
            {
                sbSql.append("   AND apply.applyNO LIKE concat('%', #{applyNO}, '%')  ");
            }
            if (findEntity.getSearchType().equals("pj"))//评价历史记录
            {

                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.rated.getValue() + "')");
            }
            else if (findEntity.getSearchType().equals("pjtj"))//评价
            {
                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "')");
            }
            else if (findEntity.getSearchType().equals("bx"))
            {
                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
            }
            else if (findEntity.getSearchType().equals("wx"))
            {
                sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

            }
            else if (findEntity.getSearchType().equals("wxywx"))
            {
                sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "')");

            }
            else if (findEntity.getSearchType().equals("pd"))//派单
            {
                sbSql.append("   AND apply.appointUserId =  #{operationUserId} ");
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "','" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");



            }
            else if (findEntity.getSearchType().equals("pdtj"))
            {
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeDispatch.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "')");
            }

        }


        //sbSql.append("   AND createUserId = #{operationUserId} ");
        sbSql.append(" ORDER BY modifyTime DESC,applyDate DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Get repair apply page info total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRepairApplyPageInfoTotal(RepairApplyFindEntity findEntity)
    {
        String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
                .map(status -> "'" + status + "'").collect(Collectors.joining(","));
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        if ("pc".equalsIgnoreCase(findEntity.getType()))
        {
            sbSql.append(" WHERE applyDate BETWEEN #{beginTime} AND #{endTime} ");
            if (ObjectUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("   AND type.categoryName  LIKE concat('%', #{categoryName}, '%')  ");
            }

            if (ObjectUtils.isNotEmpty(findEntity.getMaintenanceUser()))
            {
                sbSql.append("   AND ifnull(maintenanceUser, '') LIKE concat('%', #{maintenanceUser}, '%') ");
            }
            if (findEntity.getCreateUserId().intValue() != 0)
            {
                sbSql.append("   AND apply.createUserId = #{createUserId} ");
            }
            if (ObjectUtils.isNotEmpty(findEntity.getStationName()))
            {
                sbSql.append("   AND ifnull(station.stationName, '') LIKE concat('%', #{stationName}, '%') ");
            }
            sbSql.append("   AND apply.applyStatus IN (" + applyStatus + ") ");

            if (findEntity.getCreateUserId().intValue() == 0)
            {
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
            }
        }
        else
        {
            sbSql.append(" WHERE 1=1  ");
            if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
            {
                sbSql.append(" AND apply.applyDate >= #{beginTime} ");
            }
            if(StringUtils.isNotEmpty(findEntity.getEndTime()))
            {
                sbSql.append(" AND apply.applyDate  <= #{endTime} ");
            }
            if (findEntity.getSearchType().equals("pj"))
            {
                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.rated.getValue() + "')");
            }
            else if (findEntity.getSearchType().equals("pjtj"))
            {
                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "')");
            }
            else if (findEntity.getSearchType().equals("bx"))
            {
                sbSql.append("   AND apply.createUserId = #{operationUserId} ");
            }
            else if (findEntity.getSearchType().equals("wx"))
            {
                sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

            }
            else if (findEntity.getSearchType().equals("wxywx"))
            {
                sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "')");

            }
            else if (findEntity.getSearchType().equals("pd"))
            {
                sbSql.append("   AND apply.appointUserId =  #{operationUserId} ");
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "','" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");
            }
            else if (findEntity.getSearchType().equals("pdtj"))
            {
                sbSql.append("   AND apply.stationId IN ( ");
                sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
                sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
                sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
                sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
                sbSql.append(" ) ");
                sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeDispatch.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "')");
            }
        }

        // sbSql.append("   AND createUserId = #{operationUserId} ");
        return sbSql.toString();
    }
    /**
     * Get repair apply page info string.
     * 维修、评价来这页 手机端
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPhoneRepairApplyPageInfo(RepairApplyFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   lineName,equipmentNo, ");
        sbSql.append("   stationName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   equipment.location, ");
        sbSql.append("   apply.*, ");
        sbSql.append("   ( SELECT group_concat(breakdown.breakAbbreviated) ");
        sbSql.append("     FROM maintenance_error_fault fault ");
        sbSql.append("            INNER JOIN part_breakdown_info breakdown ");
        sbSql.append("              ON fault.breakdownInfoId = breakdown.breakdownInfoId ");
        sbSql.append("     WHERE fault.maintenanceApplyId = apply.maintenanceApplyId ) errorPhenomenon ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append(" WHERE 1=1  ");
        if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append(" AND apply.applyDate >= #{beginTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getEndTime()))
        {
            sbSql.append(" AND apply.applyDate <= #{endTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getApplyNO()))
        {
            sbSql.append("   AND apply.applyNO LIKE concat('%', #{applyNO}, '%')  ");
        }
        if (findEntity.getSearchType().equals("pj"))
        {
            //sbSql.append("   AND apply.createUserId = #{operationUserId} ");
            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.rated.getValue() + "')");
        }

        else if (findEntity.getSearchType().equals("bx"))
        {
           // sbSql.append("   AND apply.createUserId = #{operationUserId} ");
        }
        else if (findEntity.getSearchType().equals("wx"))
        {
           // sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

        }

        else if (findEntity.getSearchType().equals("pd"))//派单
        {
           // sbSql.append("   AND apply.appointUserId =  #{operationUserId} ");

            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "','" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

        }
        sbSql.append("   AND apply.stationId IN ( ");
        sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
        sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
        sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
        sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
        sbSql.append(" ) ");
        sbSql.append(" ORDER BY modifyTime DESC,applyDate DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }
    /**
     * Get repair apply page info total string.手机端
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getPhoneRepairApplyPageInfoTotal(RepairApplyFindEntity findEntity)
    {
        //String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
        //        .map(status -> "'" + status + "'").collect(Collectors.joining(","));
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");
        sbSql.append(" WHERE 1=1  ");
        if(StringUtils.isNotEmpty(findEntity.getBeginTime()))
        {
            sbSql.append(" AND apply.applyDate >= #{beginTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getEndTime()))
        {
            sbSql.append(" AND apply.applyDate  <= #{endTime} ");
        }
        if(StringUtils.isNotEmpty(findEntity.getApplyNO()))
        {
            sbSql.append("   AND apply.applyNO LIKE concat('%', #{applyNO}, '%')  ");
        }
        if (findEntity.getSearchType().equals("pj"))
        {
            //sbSql.append("   AND apply.createUserId = #{operationUserId} ");
            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.rated.getValue() + "')");
        }

        else if (findEntity.getSearchType().equals("bx"))
        {
            // sbSql.append("   AND apply.createUserId = #{operationUserId} ");
        }
        else if (findEntity.getSearchType().equals("wx"))
        {
            // sbSql.append("   AND apply.maintenanceUserID = #{operationUserId} ");
            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.noRepair.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

        }

        else if (findEntity.getSearchType().equals("pd"))//派单
        {
            // sbSql.append("   AND apply.appointUserId =  #{operationUserId} ");

            sbSql.append("   AND apply.applyStatus IN ('" + RepairApplyStatusEnum.toBeRepair.getValue() + "','" + RepairApplyStatusEnum.maintenance.getValue() + "','" + RepairApplyStatusEnum.repaired.getValue() + "','" + RepairApplyStatusEnum.rated.getValue() + "')");

        }
        sbSql.append("   AND apply.stationId IN ( ");
        sbSql.append(" SELECT owss.stationId FROM  sys_range_role srr ");
        sbSql.append(" INNER JOIN sys_range_role_detail srrd ON ( srr.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
        sbSql.append("  AND srr.userid=#{operationUserId} AND srr.rangeRoleId=srrd.rangeRoleId  ) ");
        sbSql.append(" INNER  join org_work_section_station owss on (owss.workSectionId=srrd.id) ");
        sbSql.append(" ) ");

        // sbSql.append("   AND createUserId = #{operationUserId} ");
        return sbSql.toString();
    }
    /**
     * Insert repair log string.
     *
     * @param logsEntity the logs entity
     * @return the string
     */
    public String insertRepairLog(MaintenanceLogsEntity logsEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_logs ");
        sbSql.append("   ( ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      status, ");
        sbSql.append("      initiatorId, ");
        sbSql.append("      initiator, ");
        sbSql.append("      targetPersonId, ");
        sbSql.append("      targetPerson, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{initiatorId}, ");
        sbSql.append("      #{initiator}, ");
        sbSql.append("      #{targetPersonId}, ");
        sbSql.append("      #{targetPerson}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    /**
     * Get repair logs string.
     *
     * @return the string
     */
    public String getRepairLogs()
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   log.*, ");
        sbSql.append("   CASE ");
        sbSql.append("     WHEN status = 'Dispatched' ");
        sbSql.append("             THEN ( SELECT group_concat(copyToUser) ");
        sbSql.append("                    FROM maintenance_copy copy ");
        sbSql.append("                    WHERE copy.maintenanceApplyId = log.maintenanceLogsId ) ");
        sbSql.append("     ELSE NULL END copyInfo ");
        sbSql.append(" FROM maintenance_logs log ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceLogsId} ");
        sbSql.append(" ORDER BY log.maintenanceLogsId DESC ");

        return sbSql.toString();
    }

    /**
     * Get repairer info string.
     *
     * @param stationId the station id
     * @return the string
     */
    public String getRepairerInfo(Long stationId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT DISTINCT ");
        sbSql.append("   user.* ");
        sbSql.append(" FROM sys_user user ");
        sbSql.append("        INNER JOIN sys_range_role rangeRole ");
        sbSql.append("          ON user.userId = rangeRole.userId ");
        sbSql.append("        INNER JOIN sys_range_role_detail rangeRoleDetail ");
        sbSql.append("          ON rangeRoleDetail.rangeRoleId = rangeRole.rangeRoleId ");
        sbSql.append("        INNER JOIN org_work_section section ");
        sbSql.append("          ON section.workSectionId = rangeRoleDetail.id ");
        sbSql.append("        INNER JOIN org_work_section_station sectionStation ");
        sbSql.append("          ON section.workSectionId = sectionStation.workSectionId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = sectionStation.stationId ");
        sbSql.append("        INNER JOIN sys_user_role userRole ");
        sbSql.append("          ON userRole.userId = user.userId ");
        sbSql.append("        INNER JOIN sys_role role ");
        sbSql.append("          ON role.roleId = userRole.roleId ");
        sbSql.append("        INNER JOIN sys_role_tree roleTree ");
        sbSql.append("          ON roleTree.roleId = userRole.roleId ");
        sbSql.append("        INNER JOIN sys_function_tree tree ");
        sbSql.append("          ON tree.functionTreeId = roleTree.functionTreeId ");
        sbSql.append(" WHERE rangeRole.rangeType = '" + RangeRoleTypeEnum.WORK_SECTION.getValue() + "' ");
        sbSql.append("   AND station.stationId = #{stationId} ");
        //sbSql.append("   AND tree.id = 'maintenanceApplyRepair' ");
         sbSql.append("   AND tree.permissionCode in ('jxgl','sbwx') ");
        sbSql.append("   AND user.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" ORDER BY ");
        sbSql.append("   realNameAllPinYin ");

        return sbSql.toString();
    }

    /**
     * Appoint repair info string.
     *
     * @param applyInfoVO the apply info vo
     * @return the string
     */
    public String appointRepairInfo(RepairApplyInfoVO applyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE maintenance_apply ");
        sbSql.append(" SET ");
        sbSql.append("   appointUserId = #{appointUserId}, ");
        sbSql.append("   appointUser = #{appointUser}, ");
        sbSql.append("   maintenanceUserID = #{maintenanceUserID}, ");
        sbSql.append("   maintenanceUser = #{maintenanceUser}, ");
        sbSql.append("   applyStatus = #{applyStatus} ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Get repair info pager string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRepairInfoPager(RepairApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   lineName,equipmentNo, ");
        sbSql.append("   stationName, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   equipment.location, ");
        sbSql.append("   apply.*, ");
        sbSql.append("   ( SELECT group_concat(breakdown.breakAbbreviated) ");
        sbSql.append("     FROM maintenance_error_fault fault ");
        sbSql.append("            INNER JOIN part_breakdown_info breakdown ");
        sbSql.append("              ON fault.breakdownInfoId = breakdown.breakdownInfoId ");
        sbSql.append("     WHERE fault.maintenanceApplyId = apply.maintenanceApplyId ) errorPhenomenon ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");

        if ("pc".equalsIgnoreCase(findEntity.getType()))
        {
            sbSql.append(" WHERE applyDate BETWEEN #{beginTime} AND #{endTime} ");
            if (ObjectUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("   AND type.categoryName  LIKE concat('%', #{categoryName}, '%')  ");
            }
            if (ObjectUtils.isNotEmpty(findEntity.getApplyUser()))
            {
                sbSql.append("   AND applyUser LIKE concat('%', #{applyUser}, '%') ");
            }

            if (ObjectUtils.isNotEmpty(findEntity.getStationName()))
            {
                sbSql.append("   AND ifnull(station.stationName, '') LIKE concat('%', #{stationName}, '%') ");
            }
            String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
                    .map(status -> "'" + status + "'").collect(Collectors.joining(","));
            sbSql.append("   AND apply.applyStatus IN (" + applyStatus + ") ");
        }
        else
        {
            if (findEntity.getSearchType().equals("wx"))
            {
                String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
                        .map(status -> "'" + status + "'").collect(Collectors.joining(","));
                sbSql.append("   AND apply.applyStatus IN (" + applyStatus + ") ");
            }
            else
            {
                sbSql.append("   AND (apply.applyStatus = 'maintenance' OR apply.applyStatus = 'toBeRepair' )");
            }
        }

        sbSql.append("   AND maintenanceUserID = #{operationUserId} ");
        sbSql.append(" ORDER BY applyDate DESC ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();
    }

    /**
     * Get repair info pager total string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getRepairInfoPagerTotal(RepairApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   count(1) ");
        sbSql.append(" FROM maintenance_apply apply ");
        sbSql.append("        INNER JOIN org_line line ");
        sbSql.append("          ON line.lineId = apply.lineId ");
        sbSql.append("        INNER JOIN org_station station ");
        sbSql.append("          ON station.stationId = apply.stationId ");
        sbSql.append("        INNER JOIN operations_equipment equipment ");
        sbSql.append("          ON equipment.equipmentId = apply.equipmentId ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON part.sparePartId = equipment.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON type.sparePartTypeId = part.sparePartTypeId ");

        if ("pc".equalsIgnoreCase(findEntity.getType()))
        {
            if (ObjectUtils.isNotEmpty(findEntity.getCategoryName()))
            {
                sbSql.append("   AND type.categoryName  LIKE concat('%', #{categoryName}, '%')  ");
            }
            if (ObjectUtils.isNotEmpty(findEntity.getApplyUser()))
            {
                sbSql.append("   AND applyUser LIKE concat('%', #{applyUser}, '%') ");
            }

            if (ObjectUtils.isNotEmpty(findEntity.getStationName()))
            {
                sbSql.append("   AND ifnull(station.stationName, '') LIKE concat('%', #{stationName}, '%') ");
            }
            String applyStatus = StringUtils.splitToList(findEntity.getApplyStatus(), ",").stream()
                    .map(status -> "'" + status + "'").collect(Collectors.joining(","));
            sbSql.append("   AND apply.applyStatus IN (" + applyStatus + ") ");
        }
        else
        {
            sbSql.append("   AND (apply.applyStatus = 'maintenance' OR apply.applyStatus = 'toBeRepair' )");
        }


        sbSql.append("   AND maintenanceUserID = #{operationUserId} ");
        sbSql.append(" ORDER BY applyDate DESC ");

        return sbSql.toString();
    }

    /**
     * Get line id by station id string.
     *
     * @param stationId the station id
     * @return the string
     */
    public String getLineIdByStationId(Long stationId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   lineId ");
        sbSql.append(" FROM org_station ");
        sbSql.append(" WHERE stationId = #{stationId} ");

        return sbSql.toString();
    }

    /**
     * Update apply status string.
     *
     * @param repairApplyInfoVO the repair apply info vo
     * @return the string
     */
    public String repairCheckIn(RepairApplyInfoVO repairApplyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE maintenance_apply ");
        sbSql.append(" SET ");
        sbSql.append("   applyStatus = #{applyStatus}, ");
        sbSql.append("   arrivalTime = #{arrivalTime}, ");
        sbSql.append("   modifyUser = #{modifyUser} ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Gets treatment info list.
     *
     * @param findEntity the find entity
     * @return the treatment info list
     */
    public String getTreatmentInfoList(RepairApplyFindEntity findEntity)
    {
        String errorIds = StringUtils.listToString(findEntity.getErrors().stream().distinct().collect(Collectors.toList()));
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT *,repairDescription as processMode ");
        sbSql.append(" FROM part_breakdown_repair_info ");
        sbSql.append(" WHERE breakdownInfoId IN (" + errorIds + ") ");
        if (ObjectUtils.isNotEmpty(findEntity.getSearchContent()))
        {
            sbSql.append("  AND repairDescription like concat('%',#{searchContent},'%') ");
        }
        return sbSql.toString();
    }

    /**
     * Get spare parts in bag string.
     *
     * @param findEntity the find entity
     * @return the string
     */
    public String getSparePartsInBag(RepairApplyFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   device.userDeviceId, ");
        sbSql.append("   device.count, ");
        sbSql.append("   part.sparePartId, ");
        sbSql.append("   part.partName, ");
        sbSql.append("   partPinYin, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   stock.inventoryType, ");
        sbSql.append("   stock.qrCode, ");
        sbSql.append("   stock.stockId, ");
        sbSql.append("   stock.serialNumber ");
        sbSql.append(" FROM stock_user_device device ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON device.sparePartId = part.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON part.sparePartTypeId = type.sparePartTypeId ");
        sbSql.append("        LEFT JOIN stock_stock stock ");
        sbSql.append("          ON device.stockId = stock.stockId ");
        sbSql.append(" WHERE device.userId = #{operationUserId} AND device.count > 0 ");

        return sbSql.toString();
    }

    /**
     * Repaired equipment string.
     *
     * @param repairApplyInfoVO the repair apply info vo
     * @return the string
     */
    public String repairedEquipment(RepairApplyInfoVO repairApplyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE maintenance_apply ");
        sbSql.append(" SET ");
        sbSql.append("   applyStatus = #{applyStatus}, ");
        sbSql.append("   overTime = #{overTime}, ");
        sbSql.append("   maintenanceDescribe = #{maintenanceDescribe}, ");
        sbSql.append("   wasFinished = #{wasFinished}, ");
        sbSql.append("   processDescribe = #{processDescribe}, ");
        sbSql.append("   noRepairReason = #{noRepairReason}, ");
        sbSql.append("   modifyUser = #{modifyUser} ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Insert solution info string.
     *
     * @param solutionEntity the solution entity
     * @return the string
     */
    public String insertSolutionInfo(MaintenanceSolutionEntity solutionEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_solution ");
        sbSql.append("   ( ");
        sbSql.append("      repairInfoId, ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      processMode ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{repairInfoId}, ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{processMode} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Insert change spare part info string.
     *
     * @param maintenanceChangeSparePartEntity the maintenance change spare part entity
     * @return the string
     */
    public String insertChangeSparePartInfo(MaintenanceChangeSparePartEntity maintenanceChangeSparePartEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   maintenance_change_spare_part ");
        sbSql.append("   ( ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      newStockId, ");
        sbSql.append("      oldStockId, ");
        sbSql.append("      oldQrCode, ");
        sbSql.append("      oldSparePartId, ");
        sbSql.append("      newQrCode, ");
        sbSql.append("      newSparePartId, ");
        sbSql.append("      replaceCount, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{newStockId}, ");
        sbSql.append("      #{oldStockId}, ");
        sbSql.append("      #{oldQrCode}, ");
        sbSql.append("      #{oldSparePartId}, ");
        sbSql.append("      #{newQrCode}, ");
        sbSql.append("      #{newSparePartId}, ");
        sbSql.append("      #{replaceCount}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Get spare parts in bag for repaired string.
     *
     * @param replaceSparePartVO the replace spare part vo
     * @return the string
     */
    public String getSparePartsInBagForRepaired(ReplaceSparePartVO replaceSparePartVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_user_device device ");
        sbSql.append(" WHERE device.stockId = #{oldStockId} ");
        sbSql.append("   AND status = 'bad' ");
        sbSql.append("   AND deviceStatus = 'hold' ");
        sbSql.append("   AND sparePartId = #{oldSparePartId} ");
        sbSql.append(" LIMIT 1 ");

        return sbSql.toString();
    }


    /**
     * Repair evaluation string.
     *
     * @param applyInfoVO the apply info vo
     * @return the string
     */
    public String repairEvaluation(RepairApplyInfoVO applyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE maintenance_apply ");
        sbSql.append(" SET ");
        sbSql.append("   speedAppraise = #{speedAppraise}, ");
        sbSql.append("   serviceAppraise = #{serviceAppraise}, ");
        sbSql.append("   operationSpecificationAppraise = #{operationSpecificationAppraise}, ");
        sbSql.append("   applyStatus = 'rated', ");
        sbSql.append("   appraiseWasFinished = #{appraiseWasFinished}, ");
        sbSql.append("   appraiseDescribe = #{appraiseDescribe} ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    /**
     * Insert online equipment spare part string.
     *
     * @param equipmentPartsEntity the equipment parts entity
     * @return the string
     */
    public String insertOnlineEquipmentSparePart(OperationsEquipmentPartsEntity equipmentPartsEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   operations_equipment_parts ");
        sbSql.append("   ( ");
        sbSql.append("      equipmentId, ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      stockId, ");
        sbSql.append("      maintenanceApplyId, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{equipmentId}, ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{stockId}, ");
        sbSql.append("      #{maintenanceApplyId}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{createUser} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * Update equipment parts to offline string.
     *
     * @param replaceSparePartVO the replace spare part vo
     * @return the string
     */
    public String updateEquipmentPartsToOffline(ReplaceSparePartVO replaceSparePartVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE operations_equipment_parts ");
        sbSql.append(" SET ");
        sbSql.append("   status = 'offline' ");
        sbSql.append(" WHERE stockId = ( SELECT stockId FROM stock_stock WHERE qrCode = #{oldQrCode} LIMIT 1 ) ");

        return sbSql.toString();
    }

    /**
     * Update spare parts in my bag string.
     *
     * @param replaceSparePartVO the replaceSparePartVO
     * @return the string
     */
    public String updateSparePartsInMyBag(ReplaceSparePartVO replaceSparePartVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE stock_user_device ");
        sbSql.append(" SET ");
        sbSql.append("   count = count - #{replaceCount} ");
        sbSql.append(" WHERE userDeviceId = #{userDeviceId} ");

        return sbSql.toString();

    }

    /**
     * Gets spare parts by qr code.
     *
     * @param qrCodes the qr codes
     * @return the spare parts by qr code
     */
    public String getSparePartsByQrCode(String qrCodes)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM stock_stock ");
        sbSql.append(" WHERE qrCode IN (" + qrCodes + ") ");

        return sbSql.toString();
    }


    /**
     * Get solution info string.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the string
     */
    public String getSolutionInfo(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM maintenance_solution solution ");
        sbSql.append(" WHERE maintenanceApplyId = #{maintenanceApplyId} ");

        return sbSql.toString();
    }

    public String getBreakdownList(RepairApplyInfoVO applyInfoVO)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM part_breakdown_info   ");
        sbSql.append(" WHERE  status='" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append(" AND sparePartId in ( " + StringUtils.listToString(applyInfoVO.getSparePartIds().stream().distinct().collect(Collectors.toList())) + " )");
        return sbSql.toString();
    }

    public String getDrviceInforByQrCode(GetDrviceInforByQrcodeFindEntity findEntity)
    {
        //       StringBuilder sbSql = new StringBuilder();
        //        sbSql.append(" SELECT ");
        //        sbSql.append("   categoryName, ");
        //        sbSql.append("   partName, ");
        //        sbSql.append("   qrCode, ");
        //        sbSql.append("   account, ");
        //        sbSql.append("   stockId ");
        //        sbSql.append(" FROM stock_stock ");
        //        sbSql.append("   INNER JOIN part_spare_part ");
        //        sbSql.append("     ON part_spare_part.sparePartId = stock_stock.sparePartId ");
        //        sbSql.append("   INNER JOIN part_spare_part_type ");
        //        sbSql.append("     ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        //        sbSql.append(" WHERE qrCode = #{qrCode} ");

        //        StringBuilder sbSql = new StringBuilder();
        //        sbSql.append(" select ");
        //        sbSql.append("   stock_stock.qrCode, ");
        //        sbSql.append("   stock_user_device.userDeviceId, ");
        //        sbSql.append("   stock_user_device.count, ");
        //        sbSql.append("   part_spare_part.partName, ");
        //        sbSql.append("   part_spare_part.partPinYin, ");
        //        sbSql.append("   part_spare_part.sparePartId, ");
        //        sbSql.append("   part_spare_part_type.categoryName ");
        //        sbSql.append(" from stock_user_device ");
        //        sbSql.append(" inner join part_spare_part on stock_user_device.sparePartId=stock_user_device.sparePartId ");
        //        sbSql.append(" inner join part_spare_part_type on part_spare_part.sparePartTypeId=part_spare_part_type.sparePartTypeId ");
        //        sbSql.append(" inner join stock_stock on stock_stock.sparePartId=part_spare_part.sparePartId ");
        //        sbSql.append(" where stock_stock.qrCode = #{qrCode}");


        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   device.userDeviceId, ");
        sbSql.append("   device.userId, ");
        sbSql.append("   device.count, ");
        sbSql.append("   part.sparePartId, ");
        sbSql.append("   part.partName, ");
        sbSql.append("   partPinYin, ");
        sbSql.append("   type.categoryName, ");
        sbSql.append("   stock.inventoryType, ");
        sbSql.append("   stock.qrCode, ");
        sbSql.append("   stock.stockId, ");
        sbSql.append("   stock.serialNumber ");
        sbSql.append(" FROM stock_user_device device ");
        sbSql.append("        INNER JOIN part_spare_part part ");
        sbSql.append("          ON device.sparePartId = part.sparePartId ");
        sbSql.append("        INNER JOIN part_spare_part_type type ");
        sbSql.append("          ON part.sparePartTypeId = type.sparePartTypeId ");
        sbSql.append("        LEFT JOIN stock_stock stock ");
        sbSql.append("          ON device.stockId = stock.stockId ");
        sbSql.append(" WHERE stock.qrCode = #{qrCode}  AND device.count > 0  LIMIT 1 ");

        return sbSql.toString();

    }

    public String getStockEntityByQrCode(String qrCode)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT sparePartTypeId ");
        sbSql.append("   FROM stock_stock ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_spare_part.sparePartId = stock_stock.sparePartId ");
        sbSql.append("   WHERE qrCode = #{qrCode} ");
        sbSql.append(" ORDER BY stockId DESC ");
        sbSql.append(" LIMIT 1 ");//因为出入库不删除原来的数据，会有旧数据在里面，所以取最后一个

        return sbSql.toString();

    }

    public String getStockSparePartTypeIdByStockId(Long stockId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     sparePartTypeId ");
        sbSql.append("   FROM stock_stock ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_spare_part.sparePartId = stock_stock.sparePartId ");
        sbSql.append("   WHERE stockId = #{stockId} ");

        return sbSql.toString();

    }

    public String getMaintenanceByMaintenanceApplyId(Long maintenanceApplyId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     * ");
        sbSql.append("   FROM maintenance_apply ");
        sbSql.append("   WHERE maintenanceApplyId = #{maintenanceApplyId}  ");

        return sbSql.toString();

    }
}
