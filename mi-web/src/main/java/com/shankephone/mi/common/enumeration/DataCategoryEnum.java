package com.shankephone.mi.common.enumeration;

import java.util.HashMap;

public enum DataCategoryEnum {
	
	//运营主体
	OPERATION_SUBJECT("operation_subject"),
	//线路
	LINE("line"),
	//站点
	STATION("station"),
	//工区
	WORK_SECTION("work_section"),
	//仓库
	WAREHOUSE("warehouse"),
	//部门
	ORGANIZATION("organization"),
	//区域
	REGION("region"),
	//角色
	ROLE("角色"),
	//设备类型
	DEVICE_TYPE("device_type"),
	//供应商
	SUPPLIER("supplier")
	
	;
	
	
	
	public static final HashMap<String, String> dataCategoryTableMap = new HashMap<String,String>();
	static {
		dataCategoryTableMap.put(DataCategoryEnum.OPERATION_SUBJECT.getValue(), "sys_operation_subject");
		dataCategoryTableMap.put(DataCategoryEnum.LINE.getValue(), "org_line");
		dataCategoryTableMap.put(DataCategoryEnum.STATION.getValue(), "org_station");
		dataCategoryTableMap.put(DataCategoryEnum.WORK_SECTION.getValue(), "org_work_section");
		dataCategoryTableMap.put(DataCategoryEnum.ORGANIZATION.getValue(), "org_organization");
		dataCategoryTableMap.put(DataCategoryEnum.WAREHOUSE.getValue(), "stock_warehouse");
		dataCategoryTableMap.put(DataCategoryEnum.REGION.getValue(), "sys_region");
		dataCategoryTableMap.put(DataCategoryEnum.ROLE.getValue(), "sys_role");
		dataCategoryTableMap.put(DataCategoryEnum.DEVICE_TYPE.getValue(), "part_spare_part_type");
		dataCategoryTableMap.put(DataCategoryEnum.SUPPLIER.getValue(), "stock_supplier");
	}
	
	private String value;

	DataCategoryEnum(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

}
