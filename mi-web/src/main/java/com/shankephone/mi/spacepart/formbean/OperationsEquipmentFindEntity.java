package com.shankephone.mi.spacepart.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * 营运设备查询实体
 *
 * @author 司徒彬
 * @date 2018/8/20 15:23
 */
@Data
public class OperationsEquipmentFindEntity  extends BaseFindEntity {
    private String lineId;
    private String workSectionId;
    private String stationId;
    private String stationCode;
    private String sparePartTypeId;
    private String partName;
    private String materiaCoding;
    private String specificationModel;
    private String brand;
    private String qrCode;
    private Long equipmentId;
    private boolean isHavePic;
    private String equipmentNo;
    private String equipmentName;
}
