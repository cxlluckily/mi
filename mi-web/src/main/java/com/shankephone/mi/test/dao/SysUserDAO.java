package com.shankephone.mi.test.dao;

import com.shankephone.mi.test.model.SysUser;
import com.shankephone.mi.test.model.SysUserExample;
import org.springframework.stereotype.Repository;

/**
 * SysUserDAO继承基类
 */
@Repository
public interface SysUserDAO extends MyBatisBaseDao<SysUser, Long, SysUserExample> {
}