package com.shankephone.mi.model;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;
import java.sql.Timestamp;


/**
 * stock_warehouse  Bean
 *
 * @author 系统生成
 * @date 2018-07-05 15:47:07
 */
@Data
public class StockWarehouseEntity extends BaseModel
{
	
    /**
     * 仓库ID
    **/
    private Long warehouseId;
    /**
     * 运营主体ID
    **/
    //private Long operationSubjectId;
    /**
     * 工区ID
    **/
    private Long workSectionId;
    /**
     * 父ID
    **/
    private Long parentWarehouseId;
    /**
     * 仓库名称
    **/
    private String warehouseName;
    /**
     * 仓库等级
    **/
    private String description;
    /**
     * 内部编号
    **/
    private String internalNumber;
    /**
     * 负责人
    **/
    private String headPerson;
    /**
     * 联系电话
    **/
    private String contactNumber;
    /**
     * 库存管理员
    **/
    private String warehouseManager;
    /**
     * 备      注
    **/
    private String remark;
    /**
     * 所在位置
    **/
    private String location;
    /**
     * 状态
    **/
    private String status;
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

