package com.shankephone.mi.org.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 赵亮
 * @date 2018-06-21 10:42
 */
@Data
public class OrgOrganizationListVO
{
    private String orgId;
    private String orgName;
    private boolean expanded;
    private String description;
    private boolean wasCanDelete;
    private String headPerson;
    private String contactNumber;
    private boolean leaf;
    private String parentOrgId;
    private String status;
    List<OrgOrganizationListVO> children;

}
