package com.shankephone.mi.repair.dao;

import com.shankephone.mi.model.*;
import com.shankephone.mi.repair.dao.provider.RepairApplyProvider;
import com.shankephone.mi.repair.formbean.GetDrviceInforByQrcodeFindEntity;
import com.shankephone.mi.repair.formbean.RepairApplyFindEntity;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.repair.vo.ReplaceSparePartVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 维修申请Dao
 *
 * @author 司徒彬
 * @date 2018 /8/2 10:09
 */
@Repository
public interface RepairApplyDao
{
    /**
     * Gets line and station info.
     *
     * @param findEntity the find entity
     * @return the line and station info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getLineAndStationInfo")
    List<Map<String, Object>> getLineAndStationInfo(RepairApplyFindEntity findEntity);

    /**
     * Insert repair apply info.
     *
     * @param repairInfoVO the repair info vo
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertRepairApplyInfo")
    @Options(useGeneratedKeys = true, keyProperty = "maintenanceApplyId")
    void insertRepairApplyInfo(RepairApplyInfoVO repairInfoVO);

    /**
     * Insert error fault.
     *
     * @param faultEntity the fault entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertErrorFault")
    @Options(useGeneratedKeys = true, keyProperty = "errorFaultId")
    void insertErrorFault(MaintenanceErrorFaultEntity faultEntity);

    /**
     * Insert repair apply pic.
     *
     * @param picEntity the pic entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertRepairApplyPic")
    @Options(useGeneratedKeys = true, keyProperty = "mainApplyPicId")
    void insertRepairApplyPic(MaintenanceApplyPicEntity picEntity);

    /**
     * Gets apply info by id.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the apply info by id
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getApplyInfoById")
    Map<String, Object> getApplyInfoById(Long maintenanceApplyId);

    /**
     * Gets equipment type.
     *
     * @param operationSubjectId the operation subject id
     * @return the equipment type
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getEquipmentType")
    List<Map<String, Object>> getEquipmentType(Long operationSubjectId);

    /**
     * Gets error phenomenon by equipment id.
     *
     * @param equipmentId the equipment id
     * @return the error phenomenon by equipment id
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getErrorPhenomenonByEquipmentId")
    List<Map<String, Object>> getErrorPhenomenonByEquipmentId(Long equipmentId);

    /**
     * Gets equipment list.
     *
     * @param findEntity the find entity
     * @return the equipment list
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getEquipmentList")
    List<Map<String, Object>> getEquipmentList(RepairApplyFindEntity findEntity);

    /**
     * Gets concat people list.
     *
     * @param findEntity the find entity
     * @return the concat people list
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getConcatPeopleList")
    List<Map<String, Object>> getConcatPeopleList(RepairApplyFindEntity findEntity);

    /**
     * Delete copy info.
     *
     * @param maintenanceApplyId the maintenance apply id
     */
    @DeleteProvider(type = RepairApplyProvider.class, method = "deleteCopyInfo")
    void deleteCopyInfo(Long maintenanceApplyId);

    /**
     * Delete copy info.
     * 删除错误故障信息
     *
     * @param maintenanceApplyId the maintenance apply id
     */
    @DeleteProvider(type = RepairApplyProvider.class, method = "deleteMmaintenanceErrorFault")
    void deleteMmaintenanceErrorFault(Long maintenanceApplyId);

    /**
     * Delete copy info.
     * 删除维修方案信息
     *
     * @param maintenanceApplyId the maintenance apply id
     */
    @DeleteProvider(type = RepairApplyProvider.class, method = "deleteMaintenanceSolution")
    void deleteMaintenanceSolution(Long maintenanceApplyId);

    /**
     * Insert copy info.
     *
     * @param copyEntity the copy entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertCopyInfo")
    @Options(useGeneratedKeys = true, keyProperty = "copyId")
    void insertCopyInfo(MaintenanceCopyEntity copyEntity);

    /**
     * Gets error phenomenon by apply id.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the error phenomenon by apply id
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getErrorPhenomenonByApplyId")
    List<Map<String, Object>> getErrorPhenomenonByApplyId(Long maintenanceApplyId);

    /**
     * Gets repair images.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the repair images
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairImages")
    List<Map<String, Object>> getRepairImages(Long maintenanceApplyId);

    /**
     * Gets repair apply page info.
     *
     * @param findEntity the find entity
     * @return the repair apply page info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairApplyPageInfo")
    List<Map<String, Object>> getRepairApplyPageInfo(RepairApplyFindEntity findEntity);




    /**
     * Gets repair apply page info total.
     *
     * @param findEntity the find entity
     * @return the repair apply page info total
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairApplyPageInfoTotal")
    int getRepairApplyPageInfoTotal(RepairApplyFindEntity findEntity);

    /**
     * Insert repair log.
     *
     * @param logsEntity the logs entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertRepairLog")
    @Options(useGeneratedKeys = true, keyProperty = "maintenanceLogsId")
    void insertRepairLog(MaintenanceLogsEntity logsEntity);

    /**
     * Gets repair logs.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the repair logs
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairLogs")
    List<Map<String, Object>> getRepairLogs(Long maintenanceApplyId);

    /**
     * Gets repairer info.
     *
     * @param stationId the station id
     * @return the repairer info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairerInfo")
    List<Map<String, Object>> getRepairerInfo(Long stationId);

    /**
     * Appoint repair info.
     *
     * @param applyInfoVO the apply info vo
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "appointRepairInfo")
    void appointRepairInfo(RepairApplyInfoVO applyInfoVO);

    /**
     * Gets repair info pager.
     *
     * @param findEntity the find entity
     * @return the repair info pager
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairInfoPager")
    List<Map<String, Object>> getRepairInfoPager(RepairApplyFindEntity findEntity);

    /**
     * Gets repair info pager total.
     *
     * @param findEntity the find entity
     * @return the repair info pager total
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getRepairInfoPagerTotal")
    int getRepairInfoPagerTotal(RepairApplyFindEntity findEntity);

    /**
     * Gets line id by station id.
     *
     * @param stationId the station id
     * @return the line id by station id
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getLineIdByStationId")
    Long getLineIdByStationId(Long stationId);

    /**
     * Gets treatment info list.
     *
     * @param findEntity the find entity
     * @return the treatment info list
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getTreatmentInfoList")
    List<Map<String, Object>> getTreatmentInfoList(RepairApplyFindEntity findEntity);


    /**
     * Gets copy people by apply ids.
     *
     * @param maintenanceApplyIds the maintenance apply ids
     * @return the copy people by apply ids
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getCopyPeopleByApplyIds")
    List<Map<String, Object>> getCopyPeopleByApplyIds(String maintenanceApplyIds);

    /**
     * Gets copy people by apply id.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the copy people by apply id
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getCopyPeopleByApplyId")
    List<Map<String, Object>> getCopyPeopleByApplyId(Long maintenanceApplyId);

    /**
     * Update apply status.
     *
     * @param repairApplyInfoVO the repair apply info vo
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "repairCheckIn")
    void repairCheckIn(RepairApplyInfoVO repairApplyInfoVO);

    /**
     * Gets spare parts in bag.
     *
     * @param findEntity the find entity
     * @return the spare parts in bag
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getSparePartsInBag")
    List<Map<String, Object>> getSparePartsInBag(RepairApplyFindEntity findEntity);

    /**
     * Repaired equipment.
     *
     * @param repairApplyInfoVO the repair apply info vo
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "repairedEquipment")
    void repairedEquipment(RepairApplyInfoVO repairApplyInfoVO);

    /**
     * Insert solution info.
     *
     * @param solutionEntity the solution entity
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "insertSolutionInfo")
    void insertSolutionInfo(MaintenanceSolutionEntity solutionEntity);

    /**
     * Insert change spare part info.
     *
     * @param changeSparePartEntity the change spare part entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertChangeSparePartInfo")
    @Options(useGeneratedKeys = true, keyProperty = "changeSparePartId")
    void insertChangeSparePartInfo(MaintenanceChangeSparePartEntity changeSparePartEntity);


    /**
     * Gets spare parts in bag for repaired.
     *
     * @param changeSparePartEntity the change spare part entity
     * @return the spare parts in bag for repaired
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getSparePartsInBagForRepaired")
    Map<String, Object> getSparePartsInBagForRepaired(ReplaceSparePartVO changeSparePartEntity);


    /**
     * Repair evaluation.
     *
     * @param applyInfoVO the apply info vo
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "repairEvaluation")
    void repairEvaluation(RepairApplyInfoVO applyInfoVO);

    /**
     * Gets spare part change info.
     *
     * @param maintenanceApplyId the maintenance apply id
     * @return the spare part change info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getSparePartChangeInfo")
    List<Map<String, Object>> getSparePartChangeInfo(Long maintenanceApplyId);

    /**
     * Insert online equipment spare part.
     *
     * @param messagePartsEntity the operations equipment parts entity
     */
    @InsertProvider(type = RepairApplyProvider.class, method = "insertOnlineEquipmentSparePart")
    @Options(useGeneratedKeys = true, keyProperty = "equipmentPartsId")
    void insertOnlineEquipmentSparePart(OperationsEquipmentPartsEntity messagePartsEntity);

    /**
     * Update equipment parts to offline.
     *
     * @param changeSparePartEntity the change spare part entity
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "updateEquipmentPartsToOffline")
    void updateEquipmentPartsToOffline(ReplaceSparePartVO changeSparePartEntity);

    /**
     * Update spare parts in my bag.
     *
     * @param changeSparePartEntity the change spare part entity
     */
    @UpdateProvider(type = RepairApplyProvider.class, method = "updateSparePartsInMyBag")
    void updateSparePartsInMyBag(ReplaceSparePartVO changeSparePartEntity);

    @SelectProvider(type = RepairApplyProvider.class, method = "getSparePartsByQrCode")
    List<Map<String, Object>> getSparePartsByQrCode(String qrCodes);

    @SelectProvider(type = RepairApplyProvider.class, method = "getSolutionInfo")
    List<Map<String, Object>> getSolutionInfo(Long maintenanceApplyId);


    /**
     * Gets list info.
     * 功能描述：根据备件IDs获取别见故障线信息
     * 创建人：郝伟州
     * 时间：2018年8月24日10:07:38
     *
     * @param applyInfoVO the find entity
     * @return the list info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getBreakdownList")
    List<Map<String, Object>> getBreakdownList(RepairApplyInfoVO applyInfoVO);

    @SelectProvider(type = RepairApplyProvider.class, method = "getDrviceInforByQrCode")
    List<Map<String, Object>> getDrviceInforByQrCode(GetDrviceInforByQrcodeFindEntity findEntity);

    //维修完成的时候如果上传了图片就把之前的删除掉
    @DeleteProvider(type = RepairApplyProvider.class, method = "deleteRepairedImage")
    void deleteRepairedImage(Long maintenanceApplyId);

    @SelectProvider(type = RepairApplyProvider.class, method = "getStockEntityByQrCode")
    String getStockEntityByQrCode(String qrCode);

    @SelectProvider(type = RepairApplyProvider.class, method = "getStockSparePartTypeIdByStockId")
    String getStockSparePartTypeIdByStockId(Long stockId);

    /**
     * 根据维修申请点ID,获取维修申请单信息
     *
     * @param maintenanceApplyId the change spare part entity
     * @return the spare parts in bag for repaired
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getMaintenanceByMaintenanceApplyId")
    Map<String, Object> getMaintenanceByMaintenanceApplyId(Long maintenanceApplyId);


    /**
     * Gets repair apply page info.手机端
     *
     * @param findEntity the find entity
     * @return the repair apply page info
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getPhoneRepairApplyPageInfo")
    List<Map<String, Object>> getPhoneRepairApplyPageInfo(RepairApplyFindEntity findEntity);
    /**
     * Gets repair apply page info total.手机端
     *
     * @param findEntity the find entity
     * @return the repair apply page info total
     */
    @SelectProvider(type = RepairApplyProvider.class, method = "getPhoneRepairApplyPageInfoTotal")
    int getPhoneRepairApplyPageInfoTotal(RepairApplyFindEntity findEntity);
}
