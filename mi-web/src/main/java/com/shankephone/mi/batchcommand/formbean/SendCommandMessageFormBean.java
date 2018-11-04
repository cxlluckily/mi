package com.shankephone.mi.batchcommand.formbean;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-10-19 9:57
 */
@Data
public class SendCommandMessageFormBean
{
    private List<DeviceAndBusiIdFormBean> deviceAndBusiIdFormBeans;
    /**
     *命令名称
     *@author：赵亮
     *@date：2018-10-19 10:01
    */
    private  String commandName;
    /**
     *命令类型
     *@author：赵亮
     *@date：2018-10-19 10:01
    */
    private String commandType;
    /**
     *执行脚本的内容
     *@author：赵亮
     *@date：2018-10-19 10:01
    */
    private String executedCommand;
}
