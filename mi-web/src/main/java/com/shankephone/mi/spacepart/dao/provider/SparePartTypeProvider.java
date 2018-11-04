package com.shankephone.mi.spacepart.dao.provider;

import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.formbean.PartDeviceComposeFindEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-06-28 20:50
 */
public class SparePartTypeProvider
{
    public String insertOne(PartSparePartTypeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   part_spare_part_type ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      categoryName, ");
        sbSql.append("      partTypePinYin, ");
        sbSql.append("      parentPartId, ");
        sbSql.append("      sort, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      sparePartTypeIds, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{categoryName}, ");
        sbSql.append("      #{partTypePinYin}, ");
        sbSql.append("      #{parentPartId}, ");
        sbSql.append("      #{sort}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{sparePartTypeIds}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(PartSparePartTypeEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   part_spare_part_type ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   categoryName = #{categoryName}, ");
        sbSql.append("   parentPartId = #{parentPartId}, ");
        sbSql.append("   sort = #{sort}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   sparePartTypeIds = #{sparePartTypeIds}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   sparePartTypeId = #{sparePartTypeId} ");
        return sbSql.toString();
    }

    public String getSparePartTypeInfo(PartSparePartTypeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM part_spare_part_type ");
        sbSql.append(" WHERE 1 = 1 ");
        sbSql.append("       AND operationSubjectId = #{operationSubjectId} ");
        if (StringUtils.isNotEmpty(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        sbSql.append(" ORDER BY sort asc,categoryName asc");
        return sbSql.toString();

    }

    public String getSparePartTypeKongGeList(PartSparePartTypeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM part_spare_part_type ");
        sbSql.append(" WHERE 1 = 1 ");
        sbSql.append("       AND operationSubjectId = #{operationSubjectId} ");
        if (StringUtils.isNotEmpty(findEntity.getStatus()))
        {
            sbSql.append("       AND status = #{status} ");
        }
        sbSql.append("ORDER BY  sparePartTypeIds ASC  ");
        return sbSql.toString();

    }
    public String getSparePartByCompose(PartDeviceComposeFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append("   FROM part_spare_part_type ");
        sbSql.append("   WHERE operationSubjectId = #{operationSubjectId} ");
        sbSql.append("         AND parentPartId = #{parentPartId} ");
        sbSql.append("         AND status = #{status} ");

        return sbSql.toString();

    }
}
