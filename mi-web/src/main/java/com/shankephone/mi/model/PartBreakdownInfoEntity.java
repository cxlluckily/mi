package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.sql.Timestamp;


/**
 * part_breakdown_info  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:28:38
 */
@Data
public class PartBreakdownInfoEntity extends BaseModel
{

    /**
     * 备件故障ID
     **/
    private Long breakdownInfoId;
    /**
     * 备件ID
     **/
    private Long sparePartId;
    /**
     * 故障ID
     **/
    private String breakdownKey;
    /**
     * 故障简称
     **/
    private String breakAbbreviated;
    /**
     * 故障描述
     **/
    private String breakDescription;
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

