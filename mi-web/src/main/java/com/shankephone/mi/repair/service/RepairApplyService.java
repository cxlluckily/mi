package com.shankephone.mi.repair.service;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.repair.formbean.GetDrviceInforByQrcodeFindEntity;
import com.shankephone.mi.repair.formbean.RepairApplyFindEntity;
import com.shankephone.mi.repair.util.ErrorQrCodeException;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 维修申请
 *
 * @author 司徒彬
 * @date 2018 /8/2 10:09
 */
public interface RepairApplyService {
    /**
     * Gets line and station info.
     *
     * @param findEntity the find entity
     * @return the line and station info
     */
    List<Map<String, Object>> getLineAndStationInfo(RepairApplyFindEntity findEntity);

    /**
     * Gets apply info.
     *
     * @param findEntity the find entity
     * @return the apply info
     */
    Map<String, Object> getApplyInfo(RepairApplyFindEntity findEntity);

    /**
     * Add repair apply info.
     *
     * @param photoUrl     the photo url
     * @param repairInfoVO the repair info vo
     * @throws IOException the io exception
     */
    void addRepairApplyInfo(List<CommonsMultipartFile> photoUrl, RepairApplyInfoVO repairInfoVO) throws Exception;

    /**
     * Gets equipment type.
     *
     * @param findEntity the find entity
     * @return the equipment type
     */
    List<Map<String, Object>> getEquipmentType(RepairApplyFindEntity findEntity);

    /**
     * Gets error phenomenon by equipment id.
     *
     * @param equipmentId the equipment id
     * @return the error phenomenon by equipment id
     */
    List<Map<String, Object>> getErrorPhenomenonByEquipmentId(Long equipmentId);

    /**
     * Gets equipment list.
     *
     * @param findEntity the find entity
     * @return the equipment list
     */
    List<Map<String, Object>> getEquipmentList(RepairApplyFindEntity findEntity);

    /**
     * Gets concat people list.
     *
     * @param findEntity the find entity
     * @return the concat people list
     */
    List<Map<String, Object>> getConcatPeopleList(RepairApplyFindEntity findEntity);

    /**
     * Sets concat people.
     *
     * @param findEntity the find entity
     */
    void setConcatPeople(RepairApplyInfoVO findEntity) throws Exception;

    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    Pager getRepairApplyPageInfo(RepairApplyFindEntity findEntity);


    /**
     * Gets pager info.
     *
     * @param findEntity the find entity
     * @return the pager info
     */
    Pager getPhoneRepairApplyPageInfo(RepairApplyFindEntity findEntity);

    /**
     * Gets repairer info.
     *
     * @param findEntity the find entity
     * @return the repairer info
     */
    List<Map<String, Object>> getRepairerInfo(RepairApplyFindEntity findEntity);

    /**
     * Appoint repair info.
     *
     * @param applyInfoVO the apply info vo
     */
    ResultVO appointRepairInfo(RepairApplyInfoVO applyInfoVO) throws Exception;

    /**
     * Gets repair info pager.
     *
     * @param findEntity the find entity
     * @return the repair info pager
     */
    Pager getRepairInfoPager(RepairApplyFindEntity findEntity);

    /**
     * Gets spare parts in bag.
     *
     * @param findEntity the find entity
     * @return the spare parts in bag
     */
    List<Map<String, Object>> getSparePartsInBag(RepairApplyFindEntity findEntity);

    /**
     * Gets treatment info list.
     *
     * @param findEntity the find entity
     * @return the treatment info list
     */
    List<Map<String, Object>> getTreatmentInfoList(RepairApplyFindEntity findEntity);

    /**
     * Repair check in.
     *
     * @param repairApplyInfoVO the repair apply info vo
     */
    void repairCheckIn(RepairApplyInfoVO repairApplyInfoVO) throws Exception;

    /**
     * Repair equipment.
     *
     * @param photoUrl          the photo url
     * @param repairApplyInfoVO the repair apply info vo
     * @throws IOException the io exception
     */
    void repairEquipment(List<CommonsMultipartFile> photoUrl, RepairApplyInfoVO repairApplyInfoVO) throws Exception;

    /**
     * Repair evaluation.
     *
     * @param applyInfoVO the apply info vo
     */
    void repairEvaluation(RepairApplyInfoVO applyInfoVO) throws Exception;

    /**
     * Gets list info.
     * 功能描述：根据备件IDs获取别见故障线信息
     * 创建人：郝伟州
     * 时间：2018年8月24日10:07:38
     * @param applyInfoVO the find entity
     * @return the list info
     */
    List<Map<String, Object>> getBreakdownList(RepairApplyInfoVO applyInfoVO);

    /**
     *根据二维码返回设备基本信息
     *@author：赵亮
     *@date：2018-08-27 11:02
    */
    ResultVO getDrviceInforByQrCode(GetDrviceInforByQrcodeFindEntity findEntity);
}
