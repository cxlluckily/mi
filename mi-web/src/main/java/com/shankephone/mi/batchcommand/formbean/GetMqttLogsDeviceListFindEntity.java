package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-22 11:18
 */
@Data
public class GetMqttLogsDeviceListFindEntity extends BaseFindEntity
{
    private String executeStatus;
    private String partName;
    private String categoryName;
    private Long lineId;
    private Long stationId;
    private String deviceuId;
    private Long mqttCommandBatchId;
}
