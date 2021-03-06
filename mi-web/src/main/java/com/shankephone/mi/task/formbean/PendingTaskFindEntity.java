package com.shankephone.mi.task.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-06 10:44
 */
@Data
public class PendingTaskFindEntity extends BaseFindEntity
{
    private String status;
    private String permissionCode;
    private String userId;
    private String stationId;
    private String workSectionId;
}
