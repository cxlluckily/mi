package com.shankephone.mi.supersys.dao.provider;

/**
 * @author 赵亮
 * @date 2018-07-23 13:50
 */
public class DefaultSparePartTypeProvider
{
    public String getAllDefaultSparePartList(String status)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM part_default_spare_part_type ");
        sbSql.append(" WHERE status = #{status} ");

        return sbSql.toString();
    }
}
