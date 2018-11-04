package com.shankephone.mi.mqtt.model;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-12 14:52
 */
@Data
public class HearderBean
{
    public String createTime;
    public String messageType;
    public String wasNeedReply;
}
