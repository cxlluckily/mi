package com.shankephone.mi.supersys.dao.provider;

/**
 * @author 赵亮
 * @date 2018-07-23 13:37
 */
public class RegionProvider
{
    public String getAllRegion(String status)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_region ");
        sbSql.append(" WHERE status = #{status} ");

        return sbSql.toString();
    }
}
