package com.shankephone.mi.repair.vo;

import com.shankephone.mi.model.MaintenanceSolutionEntity;
import com.shankephone.mi.util.SessionMap;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;
import org.apache.shiro.authz.AuthorizationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/8/2 17:13
 */
@Data
public class RepairApplyInfoVO {
    private String userKey;
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
     * 是否已评价
     **/
    private String wasEvaluate;
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
     * 维修故障方案id（repair）
     */

    private List<Long> errors =  new ArrayList<>();
    /**
     * 维修故障方案id（reported）
     */

    private List<Long> reportedErrors =  new ArrayList<>();

    private  List<Long> sparePartIds;
    private List<Long> userIds;
    private String type;
    private List<String> images;

    private List<MaintenanceSolutionEntity> solutionEntities;

    private List<ReplaceSparePartVO> changeSparePartEntities;

    private boolean isHavePic;

    private String appraiseWasFinished;

    public void validateUserKey()
    {
        if(StringUtils.isEmpty(this.userKey) || !SessionMap.checkUserKeyIsHave(this.userKey))
        {
            throw new AuthorizationException();
        }
    }

}
