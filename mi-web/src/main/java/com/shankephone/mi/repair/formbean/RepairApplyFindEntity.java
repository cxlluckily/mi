package com.shankephone.mi.repair.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

import java.util.List;

/**
 * 维修申请查询实体
 *
 * @author 司徒彬
 * @date 2018/8/2 10:16
 */
@Data
public class RepairApplyFindEntity extends BaseFindEntity {
    private Long maintenanceApplyId;
    private Long equipmentId;
    private Long lineId;
    private Long sparePartTypeId;
    private String type;//电脑端 pc 手机端 phone
    private String applyUser;
    private Long sparePartId;
    private String applyStatus;
    private String maintenanceUser;
    private Long stationId;
    private String stationName;
    private String categoryName;
    private List<Long> errors;
    private List<Long> userIds;
    private String wasEvaluate;
    private Long createUserId = 0l;
    private String searchType;
    private String applyNO;

}
