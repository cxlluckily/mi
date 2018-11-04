package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_flow_log  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:06
 */
@Data
public class StockFlowLogEntity extends BaseModel
{
	
    /**
     * 库存流水表ID
    **/
    private Long flowId;
    /**
     * 库存ID
    **/
    private Long stockId;
    /**
     * 仓库ID
     **/
    private Long warehouseId;
    /**
     * 备件ID
     **/
    private Long sparePartId;
    /**
     * 来源表ID
     **/
    private Long sourceId;
    /**
     * 类型
    **/
    private String type;
    /**
     * 数量
    **/
    private Integer count;
    /**
     * 描述
    **/
    private String flowDescribe;
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

   
}

