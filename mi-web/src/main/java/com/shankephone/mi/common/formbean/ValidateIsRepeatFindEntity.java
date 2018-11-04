package com.shankephone.mi.common.formbean;

import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-08-08 14:19
 */
@Data
public class ValidateIsRepeatFindEntity
{
    private String tableName;
    private String keyName;
    private Long keyValue;
    private String validateName;
    private String validateValue;
    private Long operationSubjectId;
    private Boolean isHaveSubject=true;

    public ValidateIsRepeatFindEntity(String tableName, String keyName, Long keyValue, String validateName, String validateValue, Long operationSubjectId,boolean isHaveSubject)
    {
        this.tableName = tableName;
        this.keyName = keyName;
        this.keyValue = keyValue;
        this.validateName = validateName;
        this.validateValue = validateValue;
        this.operationSubjectId = operationSubjectId;
        this.isHaveSubject = isHaveSubject;
    }
}
