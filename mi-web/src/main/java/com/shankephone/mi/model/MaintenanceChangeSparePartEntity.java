package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * maintenance_change_spare_part  Bean
 *
 * @author 系统生成
 * @date 2018-08-02 09:46:24
 */
@Data
public class MaintenanceChangeSparePartEntity extends BaseModel
{


    private Long changeSparePartId;
    /**
     * 维修申请单ID
     **/
    private Long maintenanceApplyId;
    /**
     * 新件库存id
     **/
    private Long newStockId;
    /**
     * 旧件库存id
     **/
    private Long oldStockId;
    /**
     * 旧部件编码
     **/
    private String oldQrCode;
    /**
     * 旧部件备件ID
     **/
    private Long oldSparePartId;
    /**
     * 新部件编码
     **/
    private String newQrCode;
    /**
     * 新部件备件ID
     **/
    private Long newSparePartId;
    /**
     * 数量
     **/
    private Integer replaceCount;
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

