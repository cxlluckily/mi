package com.shankephone.mi.sys.service;

import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.vo.UserInfoVO;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 个人信息服务接口
 *
 * @author 司徒彬
 * @date 2018 /6/28 10:07
 */
public interface PersonalInformationService {

    /**
     * Ger personal info map.
     *
     * @param operationUserId the operation user id
     * @return the map
     */
    Map<String, Object> gerPersonalInfo(Long operationUserId);

    /**
     * Update personal info.
     *  @param photo the photo
     * @param user  the user
     */
    void updatePersonalInfo(CommonsMultipartFile photo, SysUserEntity user) throws IOException;

    boolean modifyPassword(UserInfoVO userEntity);

    boolean modifyPasswordbyphone(UserInfoVO userEntity);
}
