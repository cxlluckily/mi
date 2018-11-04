package com.shankephone.mi.org.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;

/**
 * @author 赵亮
 * @date 2018-06-28 20:17
 */
@Data
public class WorkSectionFIndEntity extends BaseFindEntity
{
    public String status;
    public String headPerson;
    public String sectionName;
    public String sectionCode;
    private Long workSectionId;
}
