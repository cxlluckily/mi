package com.shankephone.mi.org.controller;

import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import com.shankephone.mi.org.service.OrgOrganizationService;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.SessionMap;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵亮
 * @date 2018-06-21 11:12
 * 组织机构管理
 */
@Controller
@RequestMapping("/organization")
public class OrgOrganizationController
{

    @Autowired
    private OrgOrganizationService orgOrganizationService;

    /**
     * 根据用户表的id返回对应用户的功能树节点
     *
     * @author：赵亮
     * @date：2018-06-21 15:58
     */
    //@RequiresPermissions("zzjg")
    @RequestMapping(value = "/listOrganization")
    @ResponseBody
    public ResultVO listOrganization(@RequestBody OrganizationFindEntity findEntity)
    {
        try
        {
            findEntity.validateUserKey();
            return ResultVOUtil.success(orgOrganizationService.listOrganization(findEntity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    //	//@RequiresPermissions("zzjg")
    //    @RequestMapping(value = "/tree")
    //    @ResponseBody
    //    public ResultVO tree(@RequestBody OrganizationFindEntity findEntity)
    //    {
    //        try
    //        {
    //            Integer pageNumber = findEntity.getPageNumber();
    //            Integer pageSize = findEntity.getPageSize();
    //            String userKey = findEntity.getUserKey();
    //
    //            if (ObjectUtils.isEmpty(pageNumber))
    //            {
    //                return ResultVOUtil.paramError("pageNumber");
    //            }
    //            else if (ObjectUtils.isEmpty(pageSize))
    //            {
    //                return ResultVOUtil.paramError("pageSize");
    //            }
    //            else if (ObjectUtils.isEmpty(userKey))
    //            {
    //                return ResultVOUtil.paramError("userKey");
    //            }
    //            else
    //            {
    //                return ResultVOUtil.success(orgOrganizationService.tree(findEntity));
    //            }
    //        }
    //        catch (Exception ex)
    //        {
    //            return ResultVOUtil.error(ex);
    //        }
    //    }

    /**
     * 添加组织机构
     *
     * @author：赵亮
     * @date：2018-06-21 15:59
     */
    //@RequiresPermissions("zzjg")
    @RequestMapping(value = "/insertOne")
    @ResponseBody
    public ResultVO insertOne(@RequestBody OrgOrganizationEntity entity)
    {
        try
        {
            entity.validateUserKey();
            Long parentId = entity.getParentOrgId();
            String orgName = entity.getOrgName();
            String headPerson = entity.getHeadPerson();
            String contactNumber = entity.getContactNumber();
            String description = entity.getDescription();
            String userKey = entity.getUserKey();
            Long operationSubjectId = entity.getOperationSubjectId();

            if (ObjectUtils.isEmpty(parentId))
            {
                return ResultVOUtil.paramError("parentId");
            }
            else if (ObjectUtils.isEmpty(userKey))
            {
                return ResultVOUtil.paramError("userKey");
            }
            else if (ObjectUtils.isEmpty(operationSubjectId))
            {
                return ResultVOUtil.paramError("operationSubjectId");
            }
            else if (ObjectUtils.isEmpty(orgName))
            {
                return ResultVOUtil.paramError("orgName");
            }
            else if (headPerson == null)
            {
                return ResultVOUtil.paramError("headPerson");
            }
            else if (contactNumber == null)
            {
                return ResultVOUtil.paramError("contactNumber");
            }
            else if (description == null)
            {
                return ResultVOUtil.paramError("description");
            }
            else
            {
                UserLoginInfo userLoginInfo = SessionMap.getUserInfo(entity.getUserKey());
                entity.setOperationSubjectId(userLoginInfo.getOperationSubjectId());
                entity.setCreateUser(userLoginInfo.getUserName());
                entity.setModifyUser(userLoginInfo.getUserName());
                return ResultVOUtil.success(orgOrganizationService.insertOne(entity));
            }
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }

    }

    /**
     * 根据ID查询组织机构实体
     *
     * @author：赵亮
     * @date：2018-06-21 15:59
     */
    //@RequiresPermissions("zzjg")
    @RequestMapping(value = "/findOneById")
    @ResponseBody
    public ResultVO findOneById(@RequestBody OrgOrganizationEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isNull(entity.getOrgId()))
            {
                return ResultVOUtil.paramError("orgId");
            }
            return ResultVOUtil.success(orgOrganizationService.findOneById(entity.getOrgId()));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 根据主键更新组织机构
     *
     * @author：赵亮
     * @date：2018-06-21 15:59
     */
    //@RequiresPermissions("zzjg")
    @RequestMapping(value = "/updateOne")
    @ResponseBody
    public ResultVO updateOne(@RequestBody OrgOrganizationEntity entity)
    {
        try
        {
            entity.validateUserKey();
            String orgName = entity.getOrgName();
            String headPerson = entity.getHeadPerson();
            String contactNumber = entity.getContactNumber();
            String description = entity.getDescription();
            String status = entity.getStatus();
            String userKey = entity.getUserKey();
            Long orgId = entity.getOrgId();

            if (ObjectUtils.isEmpty(orgName))
            {
                return ResultVOUtil.paramError("orgName");
            }
            else if (ObjectUtils.isEmpty(userKey))
            {
                return ResultVOUtil.paramError("userKey");
            }
            if (ObjectUtils.isEmpty(orgId))
            {
                return ResultVOUtil.paramError("orgId");
            }
            else if (headPerson == null)
            {
                return ResultVOUtil.paramError("headPerson");
            }
            else if (status == null)
            {
                return ResultVOUtil.paramError("status");
            }
            else if (contactNumber == null)
            {
                return ResultVOUtil.paramError("contactNumber");
            }
            else if (description == null)
            {
                return ResultVOUtil.paramError("description");
            }
            UserLoginInfo userLoginInfo = SessionMap.getUserInfo(entity.getUserKey());
            entity.setOperationSubjectId(userLoginInfo.getOperationSubjectId());
            entity.setCreateUser(userLoginInfo.getUserName());
            entity.setModifyUser(userLoginInfo.getUserName());
            return ResultVOUtil.success(orgOrganizationService.updateOne(entity));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }

    /**
     * 更新组织机构为无效
     *
     * @author：赵亮
     * @date：2018-06-21 15:59
     */
    //@RequiresPermissions("zzjg")
    @RequestMapping(value = "/updateStatusIsStopUsing")
    @ResponseBody
    public ResultVO updateStatusIsStopUsing(@RequestBody OrgOrganizationEntity entity)
    {
        try
        {
            entity.validateUserKey();
            if (ObjectUtils.isNull(entity.getOrgId()))
            {
                return ResultVOUtil.paramError("orgId");
            }
            UserLoginInfo userLoginInfo = SessionMap.getUserInfo(entity.getUserKey());
            entity.setModifyUser(userLoginInfo.getUserName());
            return ResultVOUtil.success(orgOrganizationService.updateStatusIsStopUsing(entity.getOrgId()));
        }
        catch (AuthorizationException ae)
        {
            throw ae;
        }
        catch (Exception ex)
        {
            return ResultVOUtil.error(ex);
        }
    }
}
