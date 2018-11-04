package com.shankephone.mi.common.dao.provider;

import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.util.StringUtils;


/**
 * 公共数据SQL Provider
 *
 * @author fengql
 * @date 2018年6月25日 上午10:37:05
 */
public class FieldContentExistsProvider
{

    public String selectFieldExists(FieldContentExistsFindEntity findEntity)
    {

        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) from  ");
        sbSql.append(findEntity.getTablename());
        sbSql.append(" where 1=1 ");

        if (!StringUtils.isEmpty(findEntity.getOperationSubjectId()))
        {
            sbSql.append(" and operationSubjectId = ").append(findEntity.getOperationSubjectId());
        }
        sbSql.append(" and ").append(findEntity.getFieldName()).append(" = ").append("#{fieldValue}");
        if (!StringUtils.isEmpty(findEntity.getIdValue()))
        {
            sbSql.append(" and ").append(findEntity.getIdName()).append(" <> ").append("#{idValue}");
        }

        if(StringUtils.isNotEmpty(findEntity.getCondition()))
        {
            sbSql.append(" ");
            sbSql.append(findEntity.getCondition());
        }

        return sbSql.toString();


    }


}
