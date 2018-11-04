package com.shankephone.mi.mqtt.consumer.abs;

import com.google.gson.JsonObject;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-10-12 14:42
 */
@Data
public abstract class ConsumerAbs
{
    private String messageType;
    public abstract void exec(JsonObject jsonObject) throws Exception;
}
