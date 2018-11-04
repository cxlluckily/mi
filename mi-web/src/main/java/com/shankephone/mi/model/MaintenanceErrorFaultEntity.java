package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;


/**
 * maintenance_error_fault  Bean
 *
 * @author 系统生成
 * @date 2018-08-02 13:54:51
 */
@Data
public class MaintenanceErrorFaultEntity extends BaseModel {

    /**
     * 主键ID
     **/
    private Long errorFaultId;
    /**
     * 维修申请单ID
     **/
    private Long maintenanceApplyId;
    /**
     * 备件故障ID
     **/
    private Long breakdownInfoId;

    private String faultType;
}

