package com.shankephone.mi.partbreakdowninfo.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-08-02 9:52
 */
public class PartBreakdownInfoProvider
{
    public String insertOne(PartBreakdownInfoEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_breakdown_info ");
        sbSql.append("   ( ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      breakdownKey, ");
        sbSql.append("      breakAbbreviated, ");
        sbSql.append("      breakDescription, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{breakdownKey}, ");
        sbSql.append("      #{breakAbbreviated}, ");
        sbSql.append("      #{breakDescription}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOne(PartBreakdownInfoEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   part_breakdown_info ");
        sbSql.append(" SET ");
        sbSql.append("   sparePartId = #{sparePartId}, ");
        sbSql.append("   breakdownKey = #{breakdownKey}, ");
        sbSql.append("   breakAbbreviated = #{breakAbbreviated}, ");
        sbSql.append("   breakDescription = #{breakDescription}, ");
        sbSql.append("   modifyUser = #{operationUserName}, ");
        sbSql.append("   status = #{status} ");
        sbSql.append(" WHERE ");
        sbSql.append("   breakdownInfoId = #{breakdownInfoId} ");

        return sbSql.toString();
    }

    public String getBreakdownInfoList(PartBreakdownInfoFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     part_breakdown_info.*, ");
        sbSql.append("     parttype.categoryName, ");
        sbSql.append("     (select categoryName from  part_spare_part_type where  sparePartTypeId =parttype.parentPartId  limit 1) parentCategoryName , ");
        sbSql.append("     partName,parttype.sparePartTypeId, ");
        sbSql.append("     DATE_FORMAT(part_breakdown_info.createTime, '%Y-%m-%d') AS insertDate ");
        sbSql.append("   FROM part_breakdown_info ");
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
        if(!"all".equals(findEntity.getStatus())) {
            sbSql.append("         AND part_breakdown_info.status = #{status} ");
        }
        sbSql.append("         AND parttype.operationSubjectId = #{operationSubjectId}");
        sbSql.append(" ORDER BY parttype.categoryName,partName,breakAbbreviated ");
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getBreakdownInfoListCount(PartBreakdownInfoFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append("   FROM part_breakdown_info ");
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
        if(!"all".equals(findEntity.getStatus())) {
            sbSql.append("         AND part_breakdown_info.status = #{status} ");
        }
        sbSql.append("         AND part_spare_part_type.operationSubjectId = #{operationSubjectId}");
        return sbSql.toString();

    }

    public String getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM ");
        sbSql.append("     part_breakdown_info ");
        sbSql.append("   WHERE 1 = 1 ");
        if(StringUtils.isNotEmpty(findEntity.getBreakAbbreviated()))
        {
            sbSql.append("         AND (breakAbbreviated LIKE concat('%', #{breakAbbreviated}, '%') ");
            sbSql.append("         OR breakdownKey LIKE concat('%', #{breakdownKey}, '%')) ");
        }
        sbSql.append("         AND status = #{status} ");
        sbSql.append("         AND sparePartId = #{sparePartId} ");

        return sbSql.toString();
    }


    public String getSparePartList(PartBreakdownInfoFindEntity entity)
    {

        StringBuilder sbSql = new StringBuilder();

        //StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT  ifnull(part_spare_part.partName,'') PartName , ");
        sbSql.append("    ifnull(part_spare_part.sparePartId,0) sparePartId , ");
        sbSql.append("    ifnull(part.partName,'') parentPartName, ");
        sbSql.append("    ifnull(part.sparePartId,0) parentSparePartId ,");
        sbSql.append("    part_spare_part_type.parentPartId ");
        sbSql.append("  FROM   part_spare_part_type ");
        sbSql.append(" inner join part_spare_part  on  part_spare_part_type.sparePartTypeId=part_spare_part.sparePartTypeId ");
        sbSql.append("  LEFT JOIN   part_spare_part_type  parenttype ");
        sbSql.append("   ON part_spare_part_type.parentPartId = parenttype.sparePartTypeId");
        sbSql.append(" LEFT join part_spare_part part  on  parenttype.sparePartTypeId=part.sparePartTypeId ");
        sbSql.append("   WHERE    part_spare_part_type.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND (part_spare_part_type.parentPartId='-1' or parenttype.status = '" + StatusEnum.START_USING.getValue() + "') ");
        sbSql.append("         AND part_spare_part_type.operationSubjectId = #{operationSubjectId}");
        return sbSql.toString();

    }



}
