package com.shankephone.mi.supersys.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import com.shankephone.mi.util.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-07-23 9:31
 */
@Data
public class OperationSubjectFindEntity extends BaseFindEntity
{
    private String subjectCode;
    private String subjectName;
    private String contacts;
    private String status;
    private  String password;
    private List<Long> operationSubjectIds;

    public String getOperationSubjectIds()
    {
        return StringUtils.listToString(operationSubjectIds);
    }
}
