package com.shankephone.mi.org.formbean;

import com.shankephone.mi.common.model.BaseFindEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 赵亮
 * @date 2018-06-21 10:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationFindEntity extends BaseFindEntity
{
	private Long orgId;

	private Long parentOrgId;

}
