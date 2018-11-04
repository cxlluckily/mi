package com.shankephone.mi.supersys.dao;

import com.shankephone.mi.model.SysAdminEntity;
import com.shankephone.mi.supersys.dao.provider.SuperAdminProvider;
import com.shankephone.mi.supersys.formbean.DeleteAdminFormBean;
import com.shankephone.mi.supersys.formbean.SuperAdminFindEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-07-23 9:18
 */
@Repository
public interface SuperAdminDao
{
    @InsertProvider(type = SuperAdminProvider.class, method = "insertAdmin")
    @Options(useGeneratedKeys = true, keyProperty = "adminId")
    Integer insertAdmin(SysAdminEntity entity);

    @UpdateProvider(type = SuperAdminProvider.class, method = "updateAdmin")
    Integer updateAdmin(SysAdminEntity entity);

    @DeleteProvider(type = SuperAdminProvider.class, method = "deleteAdmin")
    Integer deleteAdmin(DeleteAdminFormBean entity);

    @SelectProvider(type = SuperAdminProvider.class, method = "getSuperAdminList")
    List<Map<String,Object>> getSuperAdminList(SuperAdminFindEntity findEntity);

    @SelectProvider(type = SuperAdminProvider.class, method = "getSuperAdminListCount")
    Integer getSuperAdminListCount(SuperAdminFindEntity findEntity);

    @UpdateProvider(type = SuperAdminProvider.class, method = "initPassword")
    Integer initPassword(SuperAdminFindEntity findEntity);

    @SelectProvider(type = SuperAdminProvider.class, method = "findCountByUserName")
    Integer findCountByUserName(String userName);

    @SelectProvider(type = SuperAdminProvider.class, method = "getAdminEntityByUserName")
    SysAdminEntity getAdminEntityByUserName(String userName);
}
