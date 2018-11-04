package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-22 13:54
 */
@Data
public class GetOneEquipmentMqttLogsListFindEntity extends BaseFindEntity
{
    private String commandType;
    private String commandCategory;
    private String executeStatus;
    private String sendBeginTime;
    private String sendEndTime;
    private String receiveBeginTime;
    private String receiveEndTime;
    private String resultBeginTime;
    private String resultEndTime;
    private Long equipmentId;
}
