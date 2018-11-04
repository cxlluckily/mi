package com.shankephone.mi.spacepart.dao.provider;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartImageEntity;
import com.shankephone.mi.spacepart.formbean.SparePartFindEntity;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-06-29 13:43
 */
public class SparePartProvider
{
    public String insertOne(PartSparePartEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_spare_part ");
        sbSql.append("   ( ");
        sbSql.append("      sparePartTypeId, ");
        sbSql.append("      partName, ");
        sbSql.append("      partPinYin, ");
        sbSql.append("      brand, ");
        sbSql.append("      materiaCoding, ");
        sbSql.append("      specificationModel, ");
        sbSql.append("      size, ");
        sbSql.append("      material, ");
        sbSql.append("      units, ");
        sbSql.append("      upperLimit, ");
        sbSql.append("      lowerLimit, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{sparePartTypeId}, ");
        sbSql.append("      #{partName}, ");
        sbSql.append("      #{partPinYin}, ");
        sbSql.append("      #{brand}, ");
        sbSql.append("      #{materiaCoding}, ");
        sbSql.append("      #{specificationModel}, ");
        sbSql.append("      #{size}, ");
        sbSql.append("      #{material}, ");
        sbSql.append("      #{units}, ");
        sbSql.append("      #{upperLimit}, ");
        sbSql.append("      #{lowerLimit}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(PartSparePartEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   part_spare_part ");
        sbSql.append(" SET ");
        sbSql.append("   sparePartTypeId = #{sparePartTypeId}, ");
        sbSql.append("   partName = #{partName}, ");
        sbSql.append("   partPinYin = #{partPinYin}, ");
        sbSql.append("   brand = #{brand}, ");
        sbSql.append("   materiaCoding = #{materiaCoding}, ");
        sbSql.append("   specificationModel = #{specificationModel}, ");
        sbSql.append("   size = #{size}, ");
        sbSql.append("   material = #{material}, ");
        sbSql.append("   units = #{units}, ");
        sbSql.append("   upperLimit = #{upperLimit}, ");
        sbSql.append("   lowerLimit = #{lowerLimit}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   sparePartId = #{sparePartId} ");
        return sbSql.toString();

    }

    public String getSparePartInfo(SparePartFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT *,");
        sbSql.append("   CASE  status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用' ");
        sbSql.append("   ELSE '不可用' END AS statusText ");

        sbSql.append(" FROM part_spare_part ");
        sbSql.append(" WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getSparePartTypeId()))
        {
            sbSql.append("       AND sparePartTypeId = #{sparePartTypeId} ");
        }
        else
        {
            sbSql.append("       AND exists(select 1 from part_spare_part_type where sparePartTypeId=part_spare_part.sparePartTypeId and operationSubjectId=#{operationSubjectId}) ");

        }
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBrand()))
        {
            sbSql.append("       AND brand LIKE concat('%', #{brand}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getMateriaCoding()))
        {
            sbSql.append("       AND materiaCoding LIKE concat('%', #{materiaCoding}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getSpecificationModel()))
        {
            sbSql.append("       AND specificationModel LIKE concat('%', #{specificationModel}, '%') ");
        }
        sbSql.append("   LIMIT #{start}, #{limit} ");
        return sbSql.toString();

    }

    public String getSparePartInfoCount(SparePartFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append(" FROM part_spare_part ");
        sbSql.append(" WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(findEntity.getSparePartTypeId()))
        {
            sbSql.append("       AND sparePartTypeId = #{sparePartTypeId} ");
        }
        else
        {
            sbSql.append("       AND exists(select 1 from part_spare_part_type where sparePartTypeId=part_spare_part.sparePartTypeId and operationSubjectId=#{operationSubjectId} ) ");

        }
        if (!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        if (StringUtils.isNotEmpty(findEntity.getPartName()))
        {
            sbSql.append("       AND partName LIKE concat('%', #{partName}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getBrand()))
        {
            sbSql.append("       AND brand LIKE concat('%', #{brand}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getMateriaCoding()))
        {
            sbSql.append("       AND materiaCoding LIKE concat('%', #{materiaCoding}, '%') ");
        }
        if (StringUtils.isNotEmpty(findEntity.getSpecificationModel()))
        {
            sbSql.append("       AND specificationModel LIKE concat('%', #{specificationModel}, '%') ");
        }
        return sbSql.toString();

    }

    public String insertPartImage(PartSparePartImageEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_spare_part_image ");
        sbSql.append("   ( ");
        sbSql.append("      sparePartId, ");
        sbSql.append("      imageUrl, ");
        sbSql.append("      createUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{sparePartId}, ");
        sbSql.append("      #{imageUrl}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String deletePartImageBysparePartId(Long sparePartId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM part_spare_part_image ");
        sbSql.append(" WHERE sparePartId = #{sparePartId} ");
        return sbSql.toString();
    }

    public String getSparePartDetail(Long sparePartId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM part_spare_part ");
        sbSql.append("   WHERE sparePartId = #{sparePartId} ");

        return sbSql.toString();
    }

    public String getSparePartImagesList(Long sparePartId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT *, concat('" + FdfsClient.getDownloadServer() + "',imageUrl) AS fullImageUrl ");
        sbSql.append(" FROM part_spare_part_image ");
        sbSql.append(" WHERE sparePartId = #{sparePartId} ");

        return sbSql.toString();

    }


    public String getSparePartListForBreakdown(SparePartListForBreakdownFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM part_spare_part ");
        sbSql.append("   INNER JOIN part_spare_part_type");
        sbSql.append("   ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId");
        sbSql.append("   WHERE (partName LIKE concat('%', #{partName}, '%') OR partPinYin LIKE concat('%', #{partName}, '%') )");
        sbSql.append("   AND part_spare_part.status = #{status}");
        sbSql.append("   AND part_spare_part.sparePartTypeId = #{sparePartTypeId}");
        sbSql.append("   AND operationSubjectId = #{operationSubjectId}");

        return sbSql.toString();
    }

    public String getPartListBySparePartTypeId(PartSparePartEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM part_spare_part ");
        sbSql.append("   INNER JOIN part_spare_part_type");
        sbSql.append("   ON part_spare_part_type.sparePartTypeId = part_spare_part.sparePartTypeId");
        sbSql.append("   WHERE part_spare_part.sparePartTypeId = #{sparePartTypeId} ");
        sbSql.append("         AND part_spare_part.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("         AND operationSubjectId = #{operationSubjectId}");
        return sbSql.toString();
    }

    public String getSamePartCount(PartSparePartEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append("   FROM part_spare_part ");
        sbSql.append("   WHERE sparePartId <> #{sparePartId} ");
        sbSql.append("         AND partName = #{partName} ");
        sbSql.append("         AND sparePartTypeId = #{sparePartTypeId} ");
        return sbSql.toString();

    }

    public String getPartListAndParentType(SparePartFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT part_spare_part_type.*,ifnull(parenttype.categoryName,'') parentCategoryName ");
        sbSql.append("  FROM   part_spare_part_type ");
        sbSql.append("  LEFT JOIN   part_spare_part_type  parenttype ");
        sbSql.append("   ON part_spare_part_type.parentPartId = parenttype.sparePartTypeId");
        sbSql.append("   WHERE    part_spare_part_type.status = '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("   AND (part_spare_part_type.parentPartId='-1' or parenttype.status = '" + StatusEnum.START_USING.getValue() + "') ");
        sbSql.append("         AND part_spare_part_type.operationSubjectId = #{operationSubjectId}");
        return sbSql.toString();
    }

}
