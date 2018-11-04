package com.shankephone.mi.model;

import lombok.Data;
import com.shankephone.mi.common.model.BaseModel;

import java.util.Date;


/**
 * task_pending_task  Bean
 *
 * @author 系统生成
 * @date 2018-08-07 10:05:46
 */
@Data
public class TaskPendingTaskEntity extends BaseModel
{
	
    /**
     * 待办任务ID
    **/
    private Long pendingTaskId;
    /**
     * 任务类型
    **/
    private String taskType;
    /**
     * 来源表ID
    **/
    private Long sourceId;
    /**
     * 任务状态
    **/
    private String status;
    /**
     * 任务执行人ID
    **/
    private Long taskPersonId;
    /**
     * 标题
    **/
    private String title;
    /**
     * 业务单号
    **/
    private String busiNo;
    /**
     * 任务描述
    **/
    private String taskDescribe;
    /**
     * 添加人
    **/
    private String createUser;
    /**
     * 创建时间
    **/
    private Date createTime;
    /**
     * 修改人
    **/
    private String modifyUser;
    /**
     * 修改时间
    **/
    private Date modifyTime;

   
}

