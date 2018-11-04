package com.shankephone.mi.supersys.service.impl;

import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.enumeration.UserTypeEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysAdminEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.security.shiro.CustomShiroRealm;
import com.shankephone.mi.supersys.dao.SuperAdminDao;
import com.shankephone.mi.supersys.formbean.DeleteAdminFormBean;
import com.shankephone.mi.supersys.formbean.SuperAdminFindEntity;
import com.shankephone.mi.supersys.service.SuperAdminService;
import com.shankephone.mi.util.CookieUtils;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.SessionMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 9:14
 */
@Service
public class SuperAdminServiceImpl implements SuperAdminService
{
    @Autowired
    private SuperAdminDao superAdminDao;

    /**
     * 超级管理员添加
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-23 9:15
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertSuperAdmin(SysAdminEntity entity)
    {
        try
        {
            //验证用户名是否重复
            Integer total = superAdminDao.findCountByUserName(entity.getUserName());
            if (total > 0)
            {
                return ResultVOUtil.error(ResultEnum.USER_NAME_REPEAT.getCode(), ResultEnum.USER_NAME_REPEAT.getMessage());
            }
            //设定安全的密码
            if (StringUtils.isNotBlank(entity.getPassword()))
            {
                String password = CustomShiroRealm.encrypt(entity.getPassword());
                entity.setPassword(password);
            }
            superAdminDao.insertAdmin(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 更新超级管理员
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-23 9:26
     */
    @Override
    public Integer updateSuperAdmin(SysAdminEntity entity)
    {
        try
        {
            return superAdminDao.updateAdmin(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 查询管理员list
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-23 9:32
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pager<Map<String, Object>> getSuperAdminList(SuperAdminFindEntity findEntity)
    {
        try
        {
            try
            {
                Integer total = superAdminDao.getSuperAdminListCount(findEntity);
                List<Map<String, Object>> sysUserEntities = superAdminDao.getSuperAdminList(findEntity);
                Pager pager = new Pager<>(total, sysUserEntities);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 初始化密码
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-07-23 9:40
     */
    @Override
    public Integer initPassword(SuperAdminFindEntity findEntity)
    {
        try
        {
            findEntity.setPassword(CustomShiroRealm.encrypt("123456"));
            return superAdminDao.initPassword(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 管理员登陆
     *
     * @param entity
     * @author：赵亮
     * @date：2018-07-23 10:39
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO superAdminLogin(SysAdminEntity entity, HttpServletRequest request, HttpServletResponse response)
    {
        String validatePassword = CustomShiroRealm.encrypt(entity.getPassword());
        //获取管理员数据
        entity = superAdminDao.getAdminEntityByUserName(entity.getUserName());
        //如果主键是null则没有这个用户
        if (entity == null || entity.getAdminId() == 0)
        {
            return ResultVOUtil.error(ResultEnum.SUPER_LOGIN_ERROR.getCode(), ResultEnum.SUPER_LOGIN_ERROR.getMessage());
        }
        //验证密码是否正确
        if (!validatePassword.equals(entity.getPassword()))
        {
            return ResultVOUtil.error(ResultEnum.SUPER_LOGIN_ERROR.getCode(), ResultEnum.SUPER_LOGIN_ERROR.getMessage());
        }
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserType(UserTypeEnum.SUPER_ADMIN);
        userLoginInfo.setUserName(entity.getUserName());
        userLoginInfo.setPassword(entity.getPassword());
        userLoginInfo.setUserId(entity.getAdminId());
        String userKey = SessionMap.putUserInfo(userLoginInfo);
        UsernamePasswordToken token = new UsernamePasswordToken(
                entity.getUserName() + "@" + UserTypeEnum.SUPER_ADMIN.getValue(), entity.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return ResultVOUtil.error(uae, "账户不存在！");
        } catch (IncorrectCredentialsException ice) {
            return ResultVOUtil.error(ice, "密码错误！");
        } catch (LockedAccountException lae) {
            return ResultVOUtil.error(lae, "账户已锁定！");
        } catch (AuthenticationException ae) {
            return ResultVOUtil.error(ae, "用户名或密码错误！");
        }
        request.getSession().setAttribute("userId", entity.getAdminId());
        request.getSession().setAttribute("userKey", userKey);
        request.getSession().setAttribute("userName", entity.getUserName());
        request.getSession().setAttribute("realName", entity.getRealName());
        request.getSession().setAttribute("userType", "sysAdmin");
        
        

        return ResultVOUtil.success(userKey);
    }

    /**
     * 批量删除管理员
     *
     * @param
     * @author：郝伟州
     * @date：2018-08-14 19:26
     */
    @Override
    public Integer deleteAdmin(DeleteAdminFormBean entity)
    {
        try
        {
            return superAdminDao.deleteAdmin(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
