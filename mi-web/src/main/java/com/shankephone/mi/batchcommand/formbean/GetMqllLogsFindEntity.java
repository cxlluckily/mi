package com.shankephone.mi.batchcommand.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author  赵亮
 * @date 2018-10-22 10:19
 */
@Data
public class GetMqllLogsFindEntity extends BaseFindEntity
{
    private String commandType;
    private String commandCategory;
    private String createUser;
}
