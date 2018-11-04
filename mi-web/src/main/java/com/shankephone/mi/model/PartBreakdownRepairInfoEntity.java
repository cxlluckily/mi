package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * part_breakdown_repair_info  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:28:38
 */
@Data
public class PartBreakdownRepairInfoEntity extends BaseModel
{
	
    private Long repairInfoId;
    /**
     * 备件故障ID
    **/
    private Long breakdownInfoId;
    private String cueCode;
    private String reason;
    private String repairDescription;
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
    private String status;
   
}

