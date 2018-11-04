package com.shankephone.mi.org.dao.provider;

import com.shankephone.mi.model.OrgWorkSectionEntity;
import com.shankephone.mi.org.formbean.WorkSectionFIndEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * @author 赵亮
 * @date 2018-06-28 10:35
 */
public class WorkSectionProvider
{
    /**
     * 工区新增
     *
     * @author：赵亮
     * @date：2018-06-28 11:10
     */
    public String insertWorkSection()
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   org_work_section ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      sectionCode, ");
        sbSql.append("      sectionName, ");
        sbSql.append("      headPerson, ");
        sbSql.append("      phone, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{sectionCode}, ");
        sbSql.append("      #{sectionName}, ");
        sbSql.append("      #{headPerson}, ");
        sbSql.append("      #{phone}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    /**
     * 新增工区下站点
     *
     * @author：赵亮
     * @date：2018-06-28 11:10
     */
    public String insertWorkSectionStation()
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   org_work_section_station ");
        sbSql.append("   ( ");
        sbSql.append("      stationId, ");
        sbSql.append("      workSectionId ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{stationId}, ");
        sbSql.append("      #{workSectionId} ");
        sbSql.append("   ) ");
        return sbSql.toString();

    }

    public String getLineAndStationInfo(WorkSectionFIndEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("   tempLineStation.lineId, ");
        sbSql.append("   tempLineStation.lineCode, ");
        sbSql.append("   tempLineStation.lineName, ");
        sbSql.append("   tempLineStation.stationId, ");
        sbSql.append("   tempLineStation.stationCode, ");
        sbSql.append("   tempLineStation.stationName, ");
        sbSql.append("   CASE ifnull(tempMyStation.stationId, 0) ");
        sbSql.append("   WHEN 0 ");
        sbSql.append("     THEN 'false' ");
        sbSql.append("   ELSE 'true' END AS isCheck ");
        sbSql.append(" FROM ( ");
        sbSql.append("        SELECT ");
        sbSql.append("          org_line.lineId, ");
        sbSql.append("          lineCode, ");
        sbSql.append("          lineName, ");
        sbSql.append("          stationId, ");
        sbSql.append("          stationCode, ");
        sbSql.append("          stationName ");
        sbSql.append("        FROM org_line ");
        sbSql.append("          INNER JOIN org_station ");
        sbSql.append("            ON org_station.lineId = org_line.lineId ");
        sbSql.append("        WHERE org_station.status = #{status} ");
        sbSql.append("              AND operationSubjectId = #{operationSubjectId}");
        sbSql.append("              AND org_line.status = #{status}");
        sbSql.append("      ) AS tempLineStation ");
        sbSql.append("   LEFT JOIN ");
        sbSql.append("   ( ");
        sbSql.append("     SELECT * ");
        sbSql.append("     FROM org_work_section_station ");
        sbSql.append("     WHERE workSectionId = #{workSectionId} ");
        sbSql.append("   ) AS tempMyStation ");
        sbSql.append("     ON tempLineStation.stationId = tempMyStation.stationId ");
        sbSql.append("  ORDER BY tempLineStation.lineCode ASC,tempLineStation.stationCode ASC ");
        return sbSql.toString();

    }

    public String updateOne(OrgWorkSectionEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   org_work_section ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   sectionCode = #{sectionCode}, ");
        sbSql.append("   sectionName = #{sectionName}, ");
        sbSql.append("   headPerson = #{headPerson}, ");
        sbSql.append("   phone = #{phone}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   workSectionId = #{workSectionId} ");

        return sbSql.toString();
    }


    public String selectWorkSectionStationByworkSectionId(Long operationSubjectId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select org_work_section_station.* FROM org_work_section_station ");
        sbSql.append(" inner join org_work_section on org_work_section_station.workSectionId=org_work_section.workSectionId ");
        sbSql.append(" WHERE org_work_section.operationSubjectId = #{operationSubjectId} ");

        return sbSql.toString();

    }

    public String deleteWorkSectionStationByworkSectionId(Long workSectionId)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" DELETE FROM org_work_section_station ");
        sbSql.append(" WHERE workSectionId = #{workSectionId} ");

        return sbSql.toString();

    }


    ///判读编号是否重复
    public  String GetIsCodeExists(OrgWorkSectionEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) ");
        sbSql.append("   FROM org_work_section where    ");
        sbSql.append("  sectionCode= #{sectionCode} ");
        sbSql.append(" and operationSubjectId = #{operationSubjectId} ");
        if(entity.getWorkSectionId()!=null && entity.getWorkSectionId()>0)
        {
            sbSql.append(" and workSectionId <> #{workSectionId} ");
        }
        return sbSql.toString();
    }

    ///判读名称是否重复
    public  String GetIsNameExists(OrgWorkSectionEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) ");
        sbSql.append("   FROM org_work_section where   ");
        sbSql.append("  sectionName= #{sectionName} ");
        sbSql.append(" and operationSubjectId = #{operationSubjectId} ");
        if(entity.getWorkSectionId()!=null && entity.getWorkSectionId()>0)
        {
            sbSql.append(" and workSectionId <> #{workSectionId} ");
        }
        return  sbSql.toString();
    }


    public String getWorkSectionInfo(WorkSectionFIndEntity fIndEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT * ");
        sbSql.append("   FROM org_work_section ");
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(fIndEntity.getOperationSubjectId()))
        {
            sbSql.append("         AND operationSubjectId = #{operationSubjectId} ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getSectionCode()))
        {
            sbSql.append("         AND sectionCode LIKE concat('%', #{sectionCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getSectionName()))
        {
            sbSql.append("         AND sectionName LIKE concat('%', #{sectionName}, '%') ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getHeadPerson()))
        {
            sbSql.append("         AND headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if (!"all".equals(fIndEntity.getStatus()))
        {
            sbSql.append("         AND status = #{status} ");
        }
        sbSql.append(" LIMIT #{start}, #{limit} ");
        return sbSql.toString();
    }

    public String getWorkSectionInfoTotal(WorkSectionFIndEntity fIndEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) AS totalCount ");
        sbSql.append("   FROM org_work_section ");
        sbSql.append("   WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(fIndEntity.getOperationSubjectId()))
        {
            sbSql.append("         AND operationSubjectId = #{operationSubjectId} ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getSectionCode()))
        {
            sbSql.append("         AND sectionCode LIKE concat('%', #{sectionCode}, '%') ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getSectionName()))
        {
            sbSql.append("         AND sectionName LIKE concat('%', #{sectionName}, '%') ");
        }
        if (StringUtils.isNotEmpty(fIndEntity.getHeadPerson()))
        {
            sbSql.append("         AND headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if (!"all".equals(fIndEntity.getStatus()))
        {
            sbSql.append("         AND status = #{status} ");
        }

        return sbSql.toString();
    }
}
