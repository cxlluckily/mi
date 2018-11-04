package com.shankephone.mi.task.vo;

import com.shankephone.mi.model.TaskMessageEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018/10/18 10:37
 */
@Data
public class TaskMessageVo extends TaskMessageEntity
{
    private String typeName="";
    //发送微信是否有链接
    private boolean sendUrl=true;
}
