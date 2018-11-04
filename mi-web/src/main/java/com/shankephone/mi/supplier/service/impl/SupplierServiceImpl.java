package com.shankephone.mi.supplier.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.StockSupplierEntity;
import com.shankephone.mi.supplier.dao.SupplierDao;
import com.shankephone.mi.supplier.formbean.SupplierFindEntity;
import com.shankephone.mi.supplier.service.SupplierService;
import com.shankephone.mi.util.PinYinUtils;

@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Resource
	private SupplierDao supplierDao;

	@Override
	public Pager<Map<String,Object>> getSupplierList(SupplierFindEntity entity) {
		Integer total = supplierDao.getPageInfoTotal(entity);
        List<Map<String, Object>> sysUserEntities = supplierDao.getSupplierList(entity);
        Pager pager = new Pager<>(total, sysUserEntities);
        return pager;
	}

	@Override
	public void insertOne(StockSupplierEntity entity) {
		entity.setAbbreviation(PinYinUtils.getCn2FirstSpell(entity.getSupplierName()));
		supplierDao.save(entity);
	}

	@Override
	public void delete(SupplierFindEntity entity) {
		supplierDao.delete(entity);
	}

	@Override
	public void updateOne(StockSupplierEntity entity) {
		entity.setAbbreviation(PinYinUtils.getCn2FirstSpell(entity.getSupplierName()));
		supplierDao.update(entity);
	}
	
	

}
