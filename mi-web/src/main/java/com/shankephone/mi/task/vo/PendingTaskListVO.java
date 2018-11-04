package com.shankephone.mi.task.vo;

import com.shankephone.mi.model.TaskPendingTaskEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-06 11:11
 */
@Data
public class PendingTaskListVO extends TaskPendingTaskEntity
{
    private String manageUrl;
    private String phoneUrl;
}
