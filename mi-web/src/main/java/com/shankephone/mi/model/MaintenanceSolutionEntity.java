package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;



/**
 * maintenance_solution  Bean
 *
 * @author 系统生成
 * @date 2018-08-09 20:49:12
 */
@Data
public class MaintenanceSolutionEntity extends BaseModel
{
	
    /**
     * 维修方案信息ID
    **/
    private Long solutionId;
    private Long repairInfoId;
    /**
     * 维修申请单ID
    **/
    private Long maintenanceApplyId;
    /**
     * 处理措施
    **/
    private String processMode;

   
}

