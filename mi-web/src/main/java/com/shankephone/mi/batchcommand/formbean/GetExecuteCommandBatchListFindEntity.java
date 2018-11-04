package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

import java.util.List;

/**
 *
 * @author 赵亮
 * @date 2018-10-24 17:50
 */
@Data
public class GetExecuteCommandBatchListFindEntity extends BaseFindEntity
{
    private List<Long> mqttCommandBatchIds;
}
