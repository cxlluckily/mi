package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;



/**
 * maintenance_copy  Bean
 *
 * @author 系统生成
 * @date 2018-08-02 09:46:24
 */
@Data
public class MaintenanceCopyEntity extends BaseModel
{
	
    /**
     * 抄送人ID
    **/
    private Long copyId;
    /**
     * 维修申请单ID
    **/
    private Long maintenanceApplyId;
    /**
     * 抄送人
    **/
    private String copyToUser;
    /**
     * 抄送人ID
    **/
    private Long copyToUserId;
    /**
     * 内容
    **/
    private String content;

   
}

