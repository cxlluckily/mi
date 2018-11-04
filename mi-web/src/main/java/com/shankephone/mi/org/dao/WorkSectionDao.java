package com.shankephone.mi.org.dao;

import com.shankephone.mi.model.OrgWorkSectionEntity;
import com.shankephone.mi.model.OrgWorkSectionStationEntity;
import com.shankephone.mi.org.dao.provider.WorkSectionProvider;
import com.shankephone.mi.org.formbean.WorkSectionFIndEntity;
import com.shankephone.mi.org.vo.LineAndStationListVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 工区
 *
 * @author 赵亮
 * @date 2018-06-28 11:01
 */
@Repository
public interface WorkSectionDao
{
    @InsertProvider(type = WorkSectionProvider.class, method = "insertWorkSection")
    @Options(useGeneratedKeys = true, keyProperty = "workSectionId")
    Integer insertWorkSection(OrgWorkSectionEntity entity);

    @InsertProvider(type = WorkSectionProvider.class, method = "insertWorkSectionStation")
    @Options(useGeneratedKeys = true, keyProperty = "workSectionStationId")
    Integer insertWorkSectionStation(OrgWorkSectionStationEntity entity);

    @SelectProvider(type = WorkSectionProvider.class, method = "getLineAndStationInfo")
    List<LineAndStationListVO> getLineAndStationInfo(WorkSectionFIndEntity findEntity);

    @UpdateProvider(type = WorkSectionProvider.class, method = "updateOne")
    Integer updateOne(OrgWorkSectionEntity entity);

    @DeleteProvider(type = WorkSectionProvider.class, method = "deleteWorkSectionStationByworkSectionId")
    Integer deleteWorkSectionStationByworkSectionId(Long workSectionId);

    @SelectProvider(type = WorkSectionProvider.class, method = "getWorkSectionInfo")
    List<Map<String, Object>> getWorkSectionInfo(WorkSectionFIndEntity findEntity);

    @SelectProvider(type = WorkSectionProvider.class, method = "selectWorkSectionStationByworkSectionId")
    List<Map<String, Object>> selectWorkSectionStationByworkSectionId(Long operationSubjectId);



    @SelectProvider(type = WorkSectionProvider.class, method = "getWorkSectionInfoTotal")
    Integer getWorkSectionInfoTotal(WorkSectionFIndEntity findEntity);

    @SelectProvider(type = WorkSectionProvider.class, method = "GetIsCodeExists")
    Integer GetIsCodeExists(OrgWorkSectionEntity entity);

    @SelectProvider(type = WorkSectionProvider.class, method = "GetIsNameExists")
    Integer GetIsNameExists(OrgWorkSectionEntity entity);
}
