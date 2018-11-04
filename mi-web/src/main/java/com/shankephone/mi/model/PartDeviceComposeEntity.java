package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * part_device_compose  Bean
 *
 * @author 系统生成
 * @date 2018-08-13 16:43:55
 */
@Data
public class PartDeviceComposeEntity extends BaseModel
{
	
    /**
     * 主键
    **/
    private Long deviceComposeId;
    /**
     * 备件ID
    **/
    private Long sparePartId;
    /**
     * 品牌
    **/
    private String brand;
    /**
     * 名称
    **/
    private String name;
    /**
     * 父节点ID
    **/
    private Long composePid;
    /**
     * 型号
    **/
    private String composeModel;
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
    /**
     * 父节点ID
    **/
    private String composePids;

    private String status;

   private Long sparePartTypeId;
}

