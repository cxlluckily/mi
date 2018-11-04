package com.shankephone.mi.supplier.dao.provider;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.model.StockSupplierEntity;
import com.shankephone.mi.supplier.formbean.SupplierFindEntity;

public class SupplierProvider
{

    public String getPageInfo(SupplierFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select * ");
        sbSql.append(" from  stock_supplier ");
        sbSql.append(" where operationSubjectId = #{operationSubjectId} ");
        if (entity != null)
        {
            if (entity.getSupplierName() != null && !"".equals(entity.getSupplierName()))
            {
                sbSql.append(" and  supplierName like concat('%', #{supplierName}, '%') ");
            }
            if (entity.getBrandName() != null && !"".equals(entity.getBrandName()))
            {
                sbSql.append(" and  brandName like concat('%', #{brandName}, '%') ");
            }
            if (entity.getContacts() != null && !"".equals(entity.getContacts()))
            {
                sbSql.append(" and  contacts = #{contacts} ");
            }
            if (entity.getContactInfo() != null && !"".equals(entity.getContactInfo()))
            {
                sbSql.append(" and  contactInfo = #{contactInfo} ");
            }
            if (!"all".equals(entity.getStatus()))
            {
                sbSql.append(" and  status = #{status} ");
            }
        }
        sbSql.append(" order by createTime desc limit #{start}, #{limit} ");
        return sbSql.toString();
    }

    public String getPageInfoTotal(SupplierFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" select count(1) ");
        sbSql.append(" from  stock_supplier ");
        sbSql.append(" where status = '" + StatusEnum.START_USING.getValue() + "' and operationSubjectId = #{operationSubjectId} ");
        if (entity != null)
        {
            if (entity.getSupplierName() != null && !"".equals(entity.getSupplierName()))
            {
                sbSql.append(" and  supplierName = #{supplierName} ");
            }
            if (entity.getBrandName() != null && !"".equals(entity.getBrandName()))
            {
                sbSql.append(" and  brandName = #{brandName} ");
            }
            if (entity.getContacts() != null && !"".equals(entity.getContacts()))
            {
                sbSql.append(" and  contacts = #{contacts} ");
            }
            if (entity.getContactInfo() != null && !"".equals(entity.getContactInfo()))
            {
                sbSql.append(" and  contactInfo = #{contactInfo} ");
            }
            if (!"all".equals(entity.getStatus()))
            {
                sbSql.append(" and  status = #{status} ");
            }
        }
        return sbSql.toString();
    }

    public String insertOne(StockSupplierEntity entity)
    {
        String status = entity.getStatus();
        if (status == null || "".equals(status))
        {
            status = "start_using";
            entity.setStatus(status);
        }
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" INSERT INTO ");
        sbSql.append("   stock_supplier ");
        sbSql.append("   ( ");
        sbSql.append("      operationSubjectId, ");
        sbSql.append("      supplierName, ");
        sbSql.append("      abbreviation, ");
        sbSql.append("      brandName, ");
        sbSql.append("      contacts, ");
        sbSql.append("      contactInfo, ");
        sbSql.append("      address, ");
        sbSql.append("      email, ");
        sbSql.append("      taxpayerNumber, ");
        sbSql.append("      bankOfDeposit, ");
        sbSql.append("      bankAccount, ");
        sbSql.append("      accountName, ");
        sbSql.append("      remark, ");
        sbSql.append("      status, ");
        sbSql.append("      createUser, ");
        sbSql.append("      modifyUser ");
        sbSql.append("   ) ");
        sbSql.append(" VALUES ");
        sbSql.append("   ( ");
        sbSql.append("      #{operationSubjectId}, ");
        sbSql.append("      #{supplierName}, ");
        sbSql.append("      #{abbreviation}, ");
        sbSql.append("      #{brandName}, ");
        sbSql.append("      #{contacts}, ");
        sbSql.append("      #{contactInfo}, ");
        sbSql.append("      #{address}, ");
        sbSql.append("      #{email}, ");
        sbSql.append("      #{taxpayerNumber}, ");
        sbSql.append("      #{bankOfDeposit}, ");
        sbSql.append("      #{bankAccount}, ");
        sbSql.append("      #{accountName}, ");
        sbSql.append("      #{remark}, ");
        sbSql.append("      #{status}, ");
        sbSql.append("      #{createUser}, ");
        sbSql.append("      #{modifyUser} ");
        sbSql.append("   ) ");
        return sbSql.toString();
    }

    public String updateOne(StockSupplierEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_supplier ");
        sbSql.append(" SET ");
        sbSql.append("   operationSubjectId = #{operationSubjectId}, ");
        sbSql.append("   supplierName =  #{supplierName}, ");
        sbSql.append("   abbreviation =   #{abbreviation}, ");
        sbSql.append("   brandName =   #{brandName}, ");
        sbSql.append("   contacts =   #{contacts}, ");
        sbSql.append("   contactInfo =   #{contactInfo}, ");
        sbSql.append("   address =   #{address}, ");
        sbSql.append("   email =   #{email}, ");
        sbSql.append("   taxpayerNumber =   #{taxpayerNumber}, ");
        sbSql.append("   bankOfDeposit =   #{bankOfDeposit}, ");
        sbSql.append("   bankAccount =   #{bankAccount}, ");
        sbSql.append("   accountName =   #{accountName}, ");
        sbSql.append("   remark =   #{remark}, ");
        sbSql.append("   status =   #{status}, ");
        sbSql.append("   createUser =   #{createUser}, ");
        sbSql.append("   modifyUser =   #{modifyUser} ");
        sbSql.append(" WHERE ");
        sbSql.append("   supplierId = #{supplierId} ");
        return sbSql.toString();
    }

    public String delete(SupplierFindEntity entity)
    {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" UPDATE ");
        sbSql.append("   stock_supplier ");
        sbSql.append(" SET ");
        sbSql.append("   status =   #{status} ");
        sbSql.append(" WHERE ");
        sbSql.append("   supplierId = #{supplierId} ");
        return sbSql.toString();
    }

}
