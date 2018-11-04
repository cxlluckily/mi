package com.shankephone.mi.supplier.service;

import java.util.List;
import java.util.Map;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.StockSupplierEntity;
import com.shankephone.mi.supplier.formbean.SupplierFindEntity;

public interface SupplierService {

	Pager<Map<String,Object>> getSupplierList(SupplierFindEntity entity);

	void insertOne(StockSupplierEntity entity);

	void delete(SupplierFindEntity entity);

	void updateOne(StockSupplierEntity entity);

}
