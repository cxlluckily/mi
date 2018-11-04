package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * part_spare_part_image  Bean
 *
 * @author 系统生成
 * @date 2018-07-09 10:24:12
 */
@Data
public class PartSparePartImageEntity extends BaseModel
{
	
    /**
     * 备件图片ID
    **/
    private Long sparePartImageId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 图片路径
    **/
    private String imageUrl;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;

   
}

