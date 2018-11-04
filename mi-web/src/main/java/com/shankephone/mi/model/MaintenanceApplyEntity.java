package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * maintenance_apply  Bean
 *
 * @author 系统生成
 * @date 2018-08-09 20:49:11
 */
@Data
public class MaintenanceApplyEntity extends BaseModel
{
	
    /**
     * 维修申请单ID
    **/
    private Long maintenanceApplyId;
    /**
     * 运营主体ID
    **/
    private Long operationSubjectId;
    /**
     * 运营设备ID
    **/
    private Long equipmentId;
    /**
     * 申请单编号
    **/
    private String applyNO;
    /**
     * 线路ID
    **/
    private Long lineId;
    /**
     * 车站ID
    **/
    private Long stationId;
    /**
     * 设备类型
    **/
    private String categoryName;
    /**
     * 错误代码
    **/
    private String errorCode;
    /**
     * 故障描述
    **/
    private String breakDescribe;
    /**
     * 报修员
    **/
    private String applyUser;
    /**
     * 报修时间
    **/
    private Date applyDate;
    /**
     * 指派人
    **/
    private String appointUser;
    /**
     * 指派人ID
    **/
    private Long appointUserId;
    /**
     * 指派时间
    **/
    private Date appointDate;
    /**
     * 维修人
    **/
    private String maintenanceUser;
    /**
     * 维修人ID
    **/
    private Long maintenanceUserID;
    /**
     * 到达现场时间
    **/
    private Date arrivalTime;
    /**
     * 维修完成时间
    **/
    private Date overTime;
    /**
     * 维修描述
    **/
    private String maintenanceDescribe;
    /**
     * 备注
    **/
    private String remark;
    /**
     * 申请单状态
    **/
    private String applyStatus;
    /**
     * 速度评价
    **/
    private Integer speedAppraise;
    /**
     * 速度评价
    **/
    private Integer serviceAppraise;
    /**
     * 操作规范评价
    **/
    private Integer operationSpecificationAppraise;
    /**
     * 问题是否解决
    **/
    private String wasFinished;
    /**
     * 处理描述
    **/
    private String processDescribe;
    /**
     * 评价描述
    **/
    private String appraiseDescribe;
    /**
     * 未修复原因
    **/
    private String noRepairReason;
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
     * 添加人Id
    **/
    private Long createUserId;
    /**
     * 评价的维修结果
     **/
    private String appraiseWasFinished;

   
}

