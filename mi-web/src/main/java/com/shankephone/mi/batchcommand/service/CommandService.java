package com.shankephone.mi.batchcommand.service;

import com.shankephone.mi.batchcommand.formbean.SendCommandFormBean;
import com.shankephone.mi.batchcommand.formbean.SendCommandVO;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-10-22 14:25
 */
public interface CommandService
{
    /**
     *返回预设命令ddl数据
     *@author：赵亮
     *@date：2018-10-22 14:43
    */
    List<Map<String, Object>> getPreinstallCommandDDL();

    /**
     *发送命令
     *@author：赵亮
     *@date：2018-10-24 14:31
    */
    SendCommandVO sendCommand(SendCommandFormBean entity) throws Exception;
}
