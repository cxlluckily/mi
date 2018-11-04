package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * sys_region  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:26
 */
@Data
public class SysRegionEntity  extends BaseModel
{
	
    /**
     * 地区ID
    **/
    private Long regionId;
    /**
     * 区域名称
    **/
    private String areaName;
    /**
     * 区域编码
    **/
    private String areaCode;
    /**
     * 区域类型
    **/
    private String areaType;
    /**
     * 内部编号
    **/
    private String internalNumber;
    /**
     * 父级ID
    **/
    private Long parentId;
    /**
     * 是否显示
    **/
    private Boolean wasShow;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Timestamp createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Timestamp modifyTime;

   
}

