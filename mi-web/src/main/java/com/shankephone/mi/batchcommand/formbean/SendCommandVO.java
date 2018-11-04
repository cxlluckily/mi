package com.shankephone.mi.batchcommand.formbean;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-24 16:31
 */
@Data
public class SendCommandVO
{
    private Long mqttCommandBatchId;
    private String sendJson;
    private String sendTime;
}
