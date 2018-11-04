package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * maintenance_logs  Bean
 *
 * @author 系统生成
 * @date 2018-08-02 09:46:25
 */
@Data
public class MaintenanceLogsEntity extends BaseModel
{


    private Long maintenanceApplyId;
    /**
     * 状态（已上报、已派单、已接修）
     **/
    private String status;
    /**
     * 发起人ID
     **/
    private Long initiatorId;
    /**
     * 发起人
     **/
    private String initiator;
    /**
     * 目标人ID
     **/
    private Long targetPersonId;
    /**
     * 目标人
     **/
    private String targetPerson;
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

