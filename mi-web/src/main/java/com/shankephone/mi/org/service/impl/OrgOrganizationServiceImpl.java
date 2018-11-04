package com.shankephone.mi.org.service.impl;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.model.OrgOrganizationEntity;
import com.shankephone.mi.org.dao.OrgOrganizationDao;
import com.shankephone.mi.org.formbean.OrganizationFindEntity;
import com.shankephone.mi.org.service.OrgOrganizationService;
import com.shankephone.mi.org.vo.OrgOrganizationListVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-21 10:42
 */
@Service
@Slf4j
public class OrgOrganizationServiceImpl implements OrgOrganizationService
{
    @Autowired
    private OrgOrganizationDao orgOrganizationDao;

    private List<OrgOrganizationEntity> sourseList;

    /**
     * 根据运营主体ID返回组织机构列表
     *
     * @author：赵亮
     * @date：2018-06-21 11:09
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<OrgOrganizationListVO> listOrganization(OrganizationFindEntity findEntity)
    {
        sourseList = orgOrganizationDao.listOrganization(findEntity);
        List<OrgOrganizationEntity> firstEntitys = sourseList.stream().filter(r -> r.getParentOrgId() == -1).collect(Collectors.toList());
        List<OrgOrganizationListVO> orgOrganizationListVOS = new ArrayList<>();
        for (OrgOrganizationEntity entity : firstEntitys)
        {
            OrgOrganizationListVO voEntity = initVOEntity(entity);
            voEntity.setWasCanDelete(true);
            orgOrganizationListVOS.add(voEntity);
        }
        int totalCount = orgOrganizationDao.listOrganizationTotalCount(findEntity);
        return new Pager<>(totalCount, orgOrganizationListVOS);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<OrgOrganizationListVO> tree(OrganizationFindEntity findEntity)
    {
        sourseList = orgOrganizationDao.tree(findEntity);
        //List<OrgOrganizationEntity> firstEntitys = sourseList.stream().filter(r -> r.getParentOrgId() == -1).collect(Collectors.toList());
        List<OrgOrganizationListVO> orgOrganizationListVOS = new ArrayList<>();
        for (OrgOrganizationEntity entity : sourseList)
        {
            OrgOrganizationListVO voEntity = initTree(entity);
            voEntity.setWasCanDelete(true);
            orgOrganizationListVOS.add(voEntity);
        }
        return new Pager<>(100, orgOrganizationListVOS);
    }


    /**
     * 添加组织机构
     *
     * @author：赵亮
     * @date：2018-06-21 15:24
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertOne(OrgOrganizationEntity entity)
    {
        try
        {
            //如果是添加同级肯定会有一个内部编号
            String internalNumber = orgOrganizationDao.getTheNextInternalNumber(entity.getParentOrgId());
            //如果是空那就是说肯定是添加下级，并且是下级的第一个
            if (StringUtils.isEmpty(internalNumber))
            {
                internalNumber = orgOrganizationDao.getParentInternalNumber(entity.getParentOrgId()) + "001";
            }
            else
            {
                internalNumber = DataSwitch.getNextNodeCode(internalNumber);
            }
            entity.setWasCanDelete(true);
            entity.setInternalNumber(internalNumber);

            orgOrganizationDao.insertOne(entity);
            return null;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据主键返回实体
     *
     * @author：赵亮
     * @date：2018-06-21 17:20
     */
    @Override
    public OrgOrganizationEntity findOneById(Long orgId)
    {
        try
        {
            return orgOrganizationDao.findOneById(orgId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据主键更新组织机构
     *
     * @author：赵亮
     * @date：2018-06-21 19:45
     */
    @Override
    public Integer updateOne(OrgOrganizationEntity entity)
    {
        try
        {
            return orgOrganizationDao.updateOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新组织机构为无效
     *
     * @author：赵亮
     * @date：2018-06-21 19:45
     */
    @Override
    public Integer updateStatusIsStopUsing(Long orgId)
    {
        try
        {
            return orgOrganizationDao.updateStatusIsStopUsing(orgId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 递归获取叶子数据
     *
     * @author：赵亮
     * @date：2018-06-21 11:09
     */
    private List<OrgOrganizationListVO> getChildTree(OrgOrganizationEntity entity)
    {
        List<OrgOrganizationListVO> orgOrganizationListVOS = new ArrayList<>();
        List<OrgOrganizationEntity> entitys = sourseList.stream().filter(r -> r.getParentOrgId().equals(entity.getOrgId())).collect(Collectors.toList());
        for (OrgOrganizationEntity innerEntity : entitys)
        {
            OrgOrganizationListVO voEntity = initVOEntity(innerEntity);
            orgOrganizationListVOS.add(voEntity);
        }
        return orgOrganizationListVOS;
    }

    /**
     * 给VO实体赋值
     *
     * @author：赵亮
     * @date：2018-06-21 11:08
     */
    private OrgOrganizationListVO initVOEntity(OrgOrganizationEntity innerEntity)
    {
        OrgOrganizationListVO voEntity = new OrgOrganizationListVO();
        voEntity.setContactNumber(innerEntity.getContactNumber());
        voEntity.setDescription(innerEntity.getDescription());
        voEntity.setHeadPerson(innerEntity.getHeadPerson());
        voEntity.setOrgId(innerEntity.getOrgId().toString());
        voEntity.setParentOrgId(innerEntity.getParentOrgId().toString());
        voEntity.setOrgName(innerEntity.getOrgName());
        voEntity.setWasCanDelete(innerEntity.getWasCanDelete());
        voEntity.setChildren(getChildTree(innerEntity));
        voEntity.setStatus(innerEntity.getStatus());
        if (voEntity.getChildren() != null && voEntity.getChildren().size() > 0)
        {
            voEntity.setLeaf(false);
            voEntity.setExpanded(true);
        }
        else
        {
            voEntity.setLeaf(true);
            voEntity.setExpanded(false);
        }
        return voEntity;
    }
    
    /**
     * 给VO实体赋值
     *
     * @author：赵亮
     * @date：2018-06-21 11:08
     */
    private OrgOrganizationListVO initTree(OrgOrganizationEntity innerEntity)
    {
        OrgOrganizationListVO voEntity = new OrgOrganizationListVO();
        voEntity.setContactNumber(innerEntity.getContactNumber());
        voEntity.setDescription(innerEntity.getDescription());
        voEntity.setHeadPerson(innerEntity.getHeadPerson());
        voEntity.setOrgId(innerEntity.getOrgId().toString());
        voEntity.setParentOrgId(innerEntity.getParentOrgId().toString());
        voEntity.setOrgName(innerEntity.getOrgName());
        voEntity.setWasCanDelete(innerEntity.getWasCanDelete());
        voEntity.setChildren(getChildTree(innerEntity));
        voEntity.setStatus(innerEntity.getStatus());
        int count = orgOrganizationDao.treeCount(innerEntity);
        if (count > 0)
        {
            voEntity.setLeaf(false);
            voEntity.setExpanded(true);
        }
        else
        {
            voEntity.setLeaf(true);
            voEntity.setExpanded(false);
        }
        return voEntity;
    }
}
