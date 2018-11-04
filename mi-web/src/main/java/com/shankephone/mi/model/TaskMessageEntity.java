package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * task_message  Bean
 *
 * @author 系统生成
 * @date 2018-08-07 10:05:45
 */
@Data
public class TaskMessageEntity extends BaseModel
{
	
    /**
     * 消息ID
    **/
    private Long messageId;
    /**
     * 接受人ID
    **/
    private Long receiveId;
    /**
     * 消息类型
    **/
    private String messageType;
    /**
     * 消息内容
    **/
    private String content;
    /**
     * 状态
    **/
    private String status;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 来源表ID
     **/
    private  Long sourceId;
}

