package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-23 9:34
 */
@Data
public class GetOnlineEquipmentListFindEntity extends BaseFindEntity
{
    private Long lineId;
    private Long stationId;
    private String stationCode;
    private String partName;
    private String deviceStatus;
    private String deviceuId;
    private Long sparePartTypeId;
    private String registType;
}
