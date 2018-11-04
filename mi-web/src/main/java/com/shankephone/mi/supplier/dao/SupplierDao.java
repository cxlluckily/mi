package com.shankephone.mi.supplier.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.shankephone.mi.model.StockSupplierEntity;
import com.shankephone.mi.supplier.dao.provider.SupplierProvider;
import com.shankephone.mi.supplier.formbean.SupplierFindEntity;

public interface SupplierDao {

	@SelectProvider(type = SupplierProvider.class, method = "getPageInfo")
	List<Map<String,Object>> getSupplierList(SupplierFindEntity entity);
	
	@SelectProvider(type = SupplierProvider.class, method = "getPageInfoTotal")
    Integer getPageInfoTotal(SupplierFindEntity findEntity);

	@InsertProvider(type = SupplierProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "supplierId")
	void save(StockSupplierEntity entity);

	@UpdateProvider(type = SupplierProvider.class, method = "delete")
	void delete(SupplierFindEntity entity);

	@UpdateProvider(type = SupplierProvider.class, method = "updateOne")
	void update(StockSupplierEntity entity);

}
