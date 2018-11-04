package com.shankephone.mi.wechat.formbean;

import lombok.Data;

/**
 * @author 郝伟州
 * @date 2018-09-11 13:25
 */
@Data
public class KeFuMessageEntity
{
    private String toUser="";
    private String content="";
    private String templateid="";
    private String typeName="";
    private String taskType;
    private Long sourceId=0L;
    //发送消息是否有链接 true 代表有链接
    private boolean sendUrl=true;
}
