package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Date;


/**
 * part_default_spare_part_type  Bean
 *
 * @author 系统生成
 * @date 2018-06-20 19:05:24
 */
@Data
public class PartDefaultSparePartTypeEntity extends BaseModel
{
	
    /**
     * 默认备件类型ID
    **/
    private Long defaultSparePartTypeId;
    /**
     * 分类名称
    **/
    private String partTypeName;
    /**
     * 父级ID
    **/
    private Long parentPartId;
    /**
     * 备注
    **/
    private String remark;

    /**
     * 状态
    **/
    private String status;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;

   
}

