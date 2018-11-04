package com.shankephone.mi.sys.dao.provider;

import com.shankephone.mi.model.SysDataDictionaryEntity;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.util.StringUtils;

public class DataDictionaryProvider {
	
	/**
	 * 数据字典分页
	 * @param findEntity
	 * @return
	 */
	public String getPagerInfo(DataDictFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_data_dictionary ");
        sbSql.append(" WHERE  1 = 1 ");
        sbSql.append(" and operationSubjectId = #{operationSubjectId} ");
        if(null != findEntity.getDataLabel() && !"".equals(findEntity.getDataLabel())){
            sbSql.append(" and dataLabel  LIKE concat('%',#{dataLabel}, '%') ");
        }
        if(null != findEntity.getName() && !"".equals(findEntity.getName())){
            sbSql.append(" and name  LIKE concat('%',#{name}, '%')  ");
        }
        if(null != findEntity.getCode() && !"".equals(findEntity.getCode())){
            sbSql.append(" and code  LIKE concat('%',#{code}, '%')  ");
        }
        if(null != findEntity.getStatus() && !"".equals(findEntity.getStatus())&& !"all".equals(findEntity.getStatus())){
        	sbSql.append(" and status = #{status} ");
        }
        sbSql.append(" order by dataDictionaryId desc ");
        sbSql.append("   LIMIT #{start}, #{limit} ");
        sbSql.append(StringUtils.getSortStr(findEntity));
        return sbSql.toString();

    }

    /**
     * 总条数
     * @param findEntity
     * @return
     */
    public String getPagerTotal(DataDictFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT count(1) ");
        sbSql.append(" FROM sys_data_dictionary ");
        sbSql.append(" WHERE 1 = 1 ");
        if(null != findEntity.getDataLabel() && !"".equals(findEntity.getDataLabel())){
            sbSql.append(" and dataLabel  LIKE concat('%',#{dataLabel}, '%') ");
        }
        if(null != findEntity.getName() && !"".equals(findEntity.getName())){
            sbSql.append(" and name  LIKE concat('%',#{name}, '%')  ");
        }
        if(null != findEntity.getCode() && !"".equals(findEntity.getCode())){
            sbSql.append(" and code  LIKE concat('%',#{code}, '%')  ");
        }
        if(null != findEntity.getStatus() && !"".equals(findEntity.getStatus())&& !"all".equals(findEntity.getStatus())){
            sbSql.append(" and status = #{status} ");
        }
        return sbSql.toString();
    }
    
    /**
     * 获取单个数据字典项
     * @param findEntity
     * @return
     */
    public String getDataDict(DataDictFindEntity findEntity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT * ");
        sbSql.append(" FROM sys_data_dictionary ");
        sbSql.append(" WHERE dataDictionaryId = #{dataDictionaryId} ");
        return sbSql.toString();
    }
    
    /**
     * 添加数据字典
     * @param entity
     * @return
     */
    public String addDataDict(SysDataDictionaryEntity entity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" insert into sys_data_dictionary(operationSubjectId,code,name,dataLabel,");
        sbSql.append("status,sort,remark,createUser,modifyUser) ");
        sbSql.append(" values(#{operationSubjectId}, #{code},#{name},#{dataLabel},#{status},");
        sbSql.append("#{sort},#{remark},#{operationUserName},#{operationUserName}) ");
        return sbSql.toString();
    }
    
    /**
     * 更新数据字典
     * @param entity
     * @return
     */
    public String updateDataDict(SysDataDictionaryEntity entity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" update sys_data_dictionary ");
        sbSql.append(" set code = #{code},status = #{status},sort = #{sort},dataLabel=#{dataLabel},");
        sbSql.append("operationSubjectId = #{operationSubjectId}, remark = #{remark},modifyUser = #{operationUserName} ");
        sbSql.append(" WHERE dataDictionaryId = #{dataDictionaryId} ");
        return sbSql.toString();
    }
    
    /*
     * 删除数据字典
     * @param entity
     * @return
     */
    public String deleteDataDict(DataDictFindEntity entity) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" update sys_data_dictionary set status = 'stop_using' ");
        sbSql.append(" WHERE dataDictionaryId = #{dataDictionaryId}");
        return sbSql.toString();
    }
    
    /*
     * 删除数据字典
     * @param entity
     * @return
     */
    public String deleteBatch(DataDictFindEntity entity) {
        StringBuilder sbSql = new StringBuilder();
        /*sbSql.append(" update sys_data_dictionary set status = 'stop_using' ");*/
        sbSql.append(" delete from sys_data_dictionary  ");
        if(entity.getIds() != null && !"".equals(entity.getIds())) {
        	sbSql.append(" WHERE dataDictionaryId in (" + entity.getIds() + ")");
        } else {
        	sbSql.append(" WHERE dataDictionaryId = -1 ");
        }
        
        return sbSql.toString();
    }


    ///判读编号是否重复
    public  String GetIsCodeExists(SysDataDictionaryEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("   SELECT count(*) ");
        sbSql.append("   FROM sys_data_dictionary where    ");
        sbSql.append("  code= #{code} ");
        sbSql.append(" and operationSubjectId = #{operationSubjectId} ");
        if(entity.getDataDictionaryId()!=null && entity.getDataDictionaryId()>0)
        {
            sbSql.append(" and dataDictionaryId <> #{dataDictionaryId} ");
        }
        return sbSql.toString();
    }


}
