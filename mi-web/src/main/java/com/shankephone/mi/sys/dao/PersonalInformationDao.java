package com.shankephone.mi.sys.dao;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.dao.provider.PersonalInformationProvider;
import com.shankephone.mi.sys.vo.UserInfoVO;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 个人信息Dao
 *
 * @author 司徒彬
 * @date 2018 /6/28 10:07
 */
@Repository
public interface PersonalInformationDao {

    /**
     * Ger personal info map.
     *
     * @param userId the user id
     * @return the map
     */
    @SelectProvider(type = PersonalInformationProvider.class, method = "gerPersonalInfo")
    Map<String,Object> gerPersonalInfo(Long userId);

    /**
     * Update personal info.
     *
     * @param user the user
     */
    @UpdateProvider(type = PersonalInformationProvider.class, method = "updatePersonalInfo")
    void updatePersonalInfo(SysUserEntity user);

    /**
     * Gets user info.
     *
     * @param userId the user id
     * @return the user info
     */
    @SelectProvider(type = PersonalInformationProvider.class, method = "getUserInfo")
    SysUserEntity getUserInfo(Long userId);


    /**
     * Modify password boolean.
     *
     * @param userEntity the user entity
     * @return the boolean
     */
    @UpdateProvider(type = PersonalInformationProvider.class, method = "modifyPassword")
    boolean modifyPassword(UserInfoVO userEntity);
}
