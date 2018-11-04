package com.shankephone.mi.sys.service.impl;

import com.shankephone.mi.supersys.dao.FunctionTreeDao;
import com.shankephone.mi.sys.dao.RolePermissionDao;
import com.shankephone.mi.sys.service.RolePermissionService;
import com.shankephone.mi.sys.vo.UserPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RolePermissionServiceImpl implements RolePermissionService
{

    @Resource
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private FunctionTreeDao functionTreeDao;


    public List<Map<String, Object>> findRolePermissions(String roleIds)
    {
        return rolePermissionDao.findRolePermission(roleIds);
    }

    @Override
    public List<String> getUserPermissionList(Long userId)
    {
        try
        {
            return functionTreeDao.getUserPermissionList(userId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> findUserByPermission(UserPermissionVo entity)
    {
        return rolePermissionDao.findUserByPermission(entity);
    }

    @Override
    public List<Map<String, Object>> findUserByWarehousePermission(UserPermissionVo entity)
    {
        return rolePermissionDao.findUserByWarehousePermission(entity);
    }

}
