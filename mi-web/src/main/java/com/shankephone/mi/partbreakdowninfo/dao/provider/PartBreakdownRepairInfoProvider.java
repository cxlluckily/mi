package com.shankephone.mi.partbreakdowninfo.dao.provider;

import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-08-02 13:43
 */
public class PartBreakdownRepairInfoProvider
{
    public String getPartBreakdownRepairInfoList(PartBreakdownRepairInfoFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     part_breakdown_repair_info.*, ");
        sbSql.append("     parttype.categoryName, ");
        sbSql.append("     (select categoryName from  part_spare_part_type where  sparePartTypeId =parttype.parentPartId  limit 1) parentCategoryName , ");
        sbSql.append("     partName, ");
        sbSql.append("     breakdownKey, ");
        sbSql.append("     breakAbbreviated, ");
        sbSql.append("     DATE_FORMAT(part_breakdown_info.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append("   FROM part_breakdown_repair_info ");
        sbSql.append("     INNER JOIN part_breakdown_info ");
        sbSql.append("       ON part_breakdown_repair_info.breakdownInfoId = part_breakdown_info.breakdownInfoId ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_breakdown_info.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type parttype ");
        sbSql.append("       ON parttype.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getCategoryName()))
        {
            sbSql.append("         AND parttype.categoryName LIKE concat('%', #{categoryName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("         AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBreakAbbreviated()))
        {
            sbSql.append("         AND breakAbbreviated LIKE concat('%', #{breakAbbreviated}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBreakdownKey()))
        {
            sbSql.append("         AND breakdownKey LIKE concat('%', #{breakdownKey}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getCueCode()))
        {
            sbSql.append("         AND cueCode LIKE concat('%', #{cueCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getReason()))
        {
            sbSql.append("         AND reason LIKE concat('%', #{reason}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus())) {
            sbSql.append("         AND part_breakdown_repair_info.status =#{status} ");
        }
        sbSql.append("         AND parttype.operationSubjectId = #{operationSubjectId}");
        sbSql.append(" ORDER BY parttype.categoryName,partName,breakAbbreviated,cueCode,reason ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getPartBreakdownRepairInfoListCount(PartBreakdownRepairInfoFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append("   FROM part_breakdown_repair_info ");
        sbSql.append("     INNER JOIN part_breakdown_info ");
        sbSql.append("       ON part_breakdown_repair_info.breakdownInfoId = part_breakdown_info.breakdownInfoId ");
        sbSql.append("     INNER JOIN part_spare_part ");
        sbSql.append("       ON part_breakdown_info.sparePartId = part_spare_part.sparePartId ");
        sbSql.append("     INNER JOIN part_spare_part_type ");
        sbSql.append("       ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId ");
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getCategoryName()))
        {
            sbSql.append("         AND categoryName LIKE concat('%', #{categoryName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("         AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBreakAbbreviated()))
        {
            sbSql.append("         AND breakAbbreviated LIKE concat('%', #{breakAbbreviated}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBreakdownKey()))
        {
            sbSql.append("         AND breakdownKey LIKE concat('%', #{breakdownKey}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getCueCode()))
        {
            sbSql.append("         AND cueCode LIKE concat('%', #{cueCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getReason()))
        {
            sbSql.append("         AND reason LIKE concat('%', #{reason}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus())) {
            sbSql.append("         AND part_breakdown_repair_info.status =#{status} ");
        }
        sbSql.append("         AND part_spare_part_type.operationSubjectId = #{operationSubjectId}");
        return sbSql.toString();

    }

    public String insertOne(PartBreakdownRepairInfoEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_breakdown_repair_info ");
        sbSql.append("   ( ");
        sbSql.append("      breakdownInfoId, ");
        sbSql.append("      cueCode, ");
        sbSql.append("      reason, ");
        sbSql.append("      repairDescription, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{breakdownInfoId}, ");
        sbSql.append("      #{cueCode}, ");
        sbSql.append("      #{reason}, ");
        sbSql.append("      #{repairDescription}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(PartBreakdownRepairInfoEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   part_breakdown_repair_info ");
        sbSql.append(" SET ");
        sbSql.append("   breakdownInfoId = #{breakdownInfoId}, ");
        sbSql.append("   cueCode = #{cueCode}, ");
        sbSql.append("   reason = #{reason}, ");
        sbSql.append("   repairDescription = #{repairDescription}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   repairInfoId = #{repairInfoId} ");

        return sbSql.toString();
    }
}
