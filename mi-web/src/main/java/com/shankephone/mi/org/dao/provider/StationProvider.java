package com.shankephone.mi.org.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.OrgStationEntity;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.util.StringUtils;

/**
 * 车站
 *
 * @author 赵亮
 * @date 2018-06-25 17:21
 */
public class StationProvider
{
    public String insertOne(OrgStationEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   org_station ");
        sbSql.append("   ( ");
        sbSql.append("      lineId, ");
        sbSql.append("      stationCode, ");
        sbSql.append("      stationName, ");
        sbSql.append("      headPerson, ");
        sbSql.append("      phone, ");
        sbSql.append("      remark, ");
        sbSql.append("      pinyin, ");
        sbSql.append("      wasBegin, ");
        sbSql.append("      wasEnd, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{lineId}, ");
        sbSql.append("      #{stationCode}, ");
        sbSql.append("      #{stationName}, ");
        sbSql.append("      #{headPerson}, ");
        sbSql.append("      #{phone}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{pinyin}, ");
        sbSql.append("      #{wasBegin}, ");
        sbSql.append("      #{wasEnd}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{operationUserName}, ");
        sbSql.append("      #{operationUserName} ");
        sbSql.append("   ) ");

        return sbSql.toString();
    }

    public String updateOne(OrgStationEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   org_station ");
        sbSql.append(" SET ");
        sbSql.append("   lineId = #{lineId}, ");
        sbSql.append("   stationCode = #{stationCode}, ");
        sbSql.append("   stationName = #{stationName}, ");
        sbSql.append("   headPerson = #{headPerson}, ");
        sbSql.append("   phone = #{phone}, ");
        sbSql.append("   remark = #{remark}, ");
        sbSql.append("   pinyin = #{pinyin}, ");
        sbSql.append("   wasBegin = #{wasBegin}, ");
        sbSql.append("   wasEnd = #{wasEnd}, ");
        sbSql.append("   status = #{status}, ");
        sbSql.append("   modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE ");
        sbSql.append("   stationId = #{stationId} ");

        return sbSql.toString();
    }

    public String getStationInfo(StationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT *, ");
        sbSql.append(" org_line.lineCode, ");
        sbSql.append(" org_line.lineName, ");
        sbSql.append("   CASE org_station.status ");
        sbSql.append("   WHEN '" + StatusEnum.START_USING.getValue() + "' ");
        sbSql.append("     THEN '可用' ");
        sbSql.append("   ELSE '不可用' END AS statusText,");
        sbSql.append(" CASE  wasBegin when 1 THEN  '是' ELSE '' end as wasBeginName,");
        sbSql.append(" CASE  wasEnd when 1 THEN  '是' ELSE '' end as wasEndName");
        sbSql.append(" FROM org_station ");
        sbSql.append(" RIGHT JOIN org_line on org_line.lineId=org_station.lineId");
        sbSql.append(" WHERE org_line.operationSubjectId = #{operationSubjectId}");
        if(StringUtils.isNotEmpty(findEntity.getLineId()))
        {

            if(StringUtils.isNotEmpty(findEntity.getIsExprot())&&"1".equals(findEntity.getIsExprot()))//导出查询
            {
                sbSql.append(" AND org_line.lineId = #{lineId} ");
            }
            else
            {
                sbSql.append(" AND org_station.lineId = #{lineId} ");
            }
        }
        if(StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("       AND stationCode LIKE concat('%', #{stationCode}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getStationName()))
        {
            sbSql.append("       AND (stationName LIKE concat('%', #{stationName}, '%') OR pinyin LIKE concat('%', #{stationName}, '%')) ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHeadPerson()))
        {
            sbSql.append("       AND headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status LIKE concat('%', #{status}, '%') ");
        }
        sbSql.append(" ORDER BY lineCode,lineName,stationCode,stationName ");
        sbSql.append(" LIMIT #{start}, #{limit} ");

        return sbSql.toString();

    }

    public String getStationInfoTotal(StationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(*) AS totalCount ");
        sbSql.append(" FROM org_station ");
        sbSql.append(" RIGHT JOIN org_line on org_line.lineId=org_station.lineId");
        sbSql.append(" WHERE org_line.operationSubjectId = #{operationSubjectId}");
        if(StringUtils.isNotEmpty(findEntity.getLineId()))
        {
            if(StringUtils.isNotEmpty(findEntity.getIsExprot())&&"1".equals(findEntity.getIsExprot()))//导出查询
            {
                sbSql.append(" AND org_line.lineId = #{lineId} ");
            }
            else
            {
                sbSql.append(" AND org_station.lineId = #{lineId} ");
            }
        }
        if(StringUtils.isNotEmpty(findEntity.getStationCode()))
        {
            sbSql.append("       AND stationCode LIKE concat('%', #{stationCode}, '%') ");
        }
        if(StringUtils.isNotEmpty(findEntity.getStationName()))
        {
            sbSql.append("       AND (stationName LIKE concat('%', #{stationName}, '%') OR pinyin LIKE concat('%', #{stationName}, '%')) ");
        }
        if(StringUtils.isNotEmpty(findEntity.getHeadPerson()))
        {
            sbSql.append("       AND headPerson LIKE concat('%', #{headPerson}, '%') ");
        }
        if(!"all".equals(findEntity.getStatus()))
        {
            sbSql.append("       AND status LIKE concat('%', #{status}, '%') ");
        }

        return sbSql.toString();

    }


    public String getLineStationInfo(StationFindEntity findEntity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select ");
        sbSql.append("  org_line.lineId,org_line.lineCode,org_line.lineName, ");
        sbSql.append(" org_station.stationId,org_station.stationCode,org_station.stationName ");
        sbSql.append(" from org_line ");
        sbSql.append(" LEFT join org_station on org_line.lineId=org_station.lineId ");
        sbSql.append(" where org_line.operationSubjectId = #{operationSubjectId} ");
        return sbSql.toString();
    }
}
