package com.shankephone.mi.repair.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.*;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.*;
import com.shankephone.mi.repair.dao.RepairApplyDao;
import com.shankephone.mi.repair.enumeration.FaultType;
import com.shankephone.mi.repair.formbean.GetDrviceInforByQrcodeFindEntity;
import com.shankephone.mi.repair.formbean.RepairApplyFindEntity;
import com.shankephone.mi.repair.service.RepairApplyService;
import com.shankephone.mi.repair.util.ErrorQrCodeException;
import com.shankephone.mi.repair.vo.RepairApplyInfoVO;
import com.shankephone.mi.repair.vo.ReplaceSparePartVO;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.task.service.PendingTaskService;
import com.shankephone.mi.task.service.TaskMessageService;
import com.shankephone.mi.task.vo.TaskMessageVo;
import com.shankephone.mi.util.*;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 维修申请
 *
 * @author 司徒彬
 * @date 2018 /8/2 10:09
 */
@Service
public class RepairApplyServiceImpl implements RepairApplyService
{

    /**
     * The Repair apply dao.
     */
    @Autowired
    RepairApplyDao repairApplyDao;

    @Autowired
    private PendingTaskService pendingTaskService;

    @Autowired
    private TaskMessageService taskMessageService;

    @Override
    public List<Map<String, Object>> getLineAndStationInfo(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> lineInfo = repairApplyDao.getLineAndStationInfo(findEntity);
            if (ObjectUtils.isNotEmpty(findEntity.getSearchContent()))
            {
                return lineInfo;
            }
            else
            {
                List<Map<String, Object>> result = new ArrayList<>();
                lineInfo.stream().collect(Collectors.groupingBy(map -> DataSwitch.convertObjectToLong(map.get("lineId")))).entrySet().stream().forEach(entry -> {
                    Map<String, Object> line = new HashMap<>();
                    List<Map<String, Object>> values = entry.getValue();
                    line.put("lineId", values.get(0).get("lineId"));
                    line.put("lineName", values.get(0).get("lineName"));
                    line.put("lineCode", values.get(0).get("lineCode"));
                    line.put("code", values.get(0).get("lineId"));
                    line.put("name", values.get(0).get("lineName"));
                    List<Map<String, Object>> stations = new ArrayList<>();
                    values.stream().forEach(value -> {
                        Map<String, Object> station = new HashMap<>();
                        station.put("stationId", value.get("stationId"));
                        station.put("stationName", value.get("stationName"));
                        station.put("stationCode", value.get("stationCode"));
                        station.put("code", value.get("stationId"));
                        station.put("name", value.get("stationName"));
                        stations.add(station);
                    });
                    line.put("stations", stations);
                    result.add(line);
                });

                return  result.stream().sorted((str1, str2) -> str1.get("lineCode").toString().compareTo(str2.get("lineCode").toString())).collect(Collectors.toList());
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getApplyInfo(RepairApplyFindEntity findEntity)
    {
        try
        {

            Long maintenanceApplyId = findEntity.getMaintenanceApplyId();
            //获取维修主表信息
            Map<String, Object> applyInfo = repairApplyDao.getApplyInfoById(maintenanceApplyId);
            //获取故障现象
            Long equipmentId = DataSwitch.convertObjectToLong(applyInfo.get("equipmentId"));
            List<Map<String, Object>> errorPhenomenon = repairApplyDao.getErrorPhenomenonByEquipmentId(equipmentId);
            // List<Map<String, Object>> reproterrorPhenomenon = BeanUtils.deepCopy(errorPhenomenon);


            List<Map<String, Object>> selectedErrorPhenomenon = repairApplyDao.getErrorPhenomenonByApplyId(maintenanceApplyId);
            //上报数据
            List<Map<String, Object>> reportData =
                    selectedErrorPhenomenon.stream().filter(map -> DataSwitch.convertObjectToString(map.get("faultType")).equalsIgnoreCase(FaultType.REPORT.getCode())).collect(Collectors.toList());

            //维修数据
            List<Map<String, Object>> repairData =
                    selectedErrorPhenomenon.stream().filter(map -> DataSwitch.convertObjectToString(map.get("faultType")).equalsIgnoreCase(FaultType.REPAIR.getCode())).collect(Collectors.toList());

            List<Map<String, Object>> reportErrorPhenomenon = errorPhenomenon.stream().map(error -> {
                Long breakdownInfoId = DataSwitch.convertObjectToLong(error.get("breakdownInfoId"));
                boolean isChecked = reportData.stream().filter(map -> DataSwitch.convertObjectToLong(map.get("breakdownInfoId")).equals(breakdownInfoId)).count() > 0;
                error.put("faultType", FaultType.REPORT.getCode());
                error.put("checked", isChecked);
                return error;
            }).collect(Collectors.toList());


            applyInfo.put("reportErrorPhenomenon", reportErrorPhenomenon);


            //            List<Map<String, Object>> repairErrorPhenomenon;
            //            if (repairData.size() > 0)
            //            {
            //                repairErrorPhenomenon = errorPhenomenon.stream().map(error -> {
            //                    Long breakdownInfoId = DataSwitch.convertObjectToLong(error.get("breakdownInfoId"));
            //                    boolean isChecked = selectedErrorPhenomenon.stream().filter(map -> DataSwitch.convertObjectToLong(map.get("breakdownInfoId")).equals(breakdownInfoId)).count() > 0;
            //                    error.put("faultType", FaultType.REPAIR.getCode());
            //                    error.put("checked", isChecked);
            //                    return error;
            //                }).collect(Collectors.toList());
            //            }
            //            else
            //            {
            //                repairErrorPhenomenon = new ArrayList<>();
            //            }
            //            selectedErrorPhenomenon.stream().collect(Collectors.groupingBy(map -> DataSwitch.convertObjectToString("faultType")));
            //            errorPhenomenon.stream().forEach(error -> {
            //                Long breakdownInfoId = DataSwitch.convertObjectToLong(error.get("breakdownInfoId"));
            //                boolean isChecked = selectedErrorPhenomenon.stream().filter(map -> DataSwitch.convertObjectToLong(map.get("breakdownInfoId")).equals(breakdownInfoId)).count() > 0;
            //                error.put("checked", isChecked);
            //            });

            //获取维修图片
            List<Map<String, Object>> repairImages = repairApplyDao.getRepairImages(maintenanceApplyId);
            repairImages.stream().forEach(map -> {
                String picUrl = DataSwitch.convertObjectToString(map.get("picUrl"));
                String repairWebPath = FdfsClient.getDownloadServer() + picUrl;
                map.put("picUrl", repairWebPath);
            });

            //获取日志
            List<Map<String, Object>> logs = repairApplyDao.getRepairLogs(maintenanceApplyId);

            //获取抄送人
            List<Map<String, Object>> copyInfo = repairApplyDao.getCopyPeopleByApplyId(maintenanceApplyId);
            applyInfo.put("copyInfo", copyInfo);
            //获取维修方案
            applyInfo.put("images", repairImages);
            if (repairImages != null)
            {
                applyInfo.put("reportedImages", repairImages.stream().filter(r -> DataSwitch.convertObjectToString(r.get("type")).equals("reported")).collect(Collectors.toList()));
                applyInfo.put("repairImages", repairImages.stream().filter(r -> DataSwitch.convertObjectToString(r.get("type")).equals("repair")).collect(Collectors.toList()));
            }

            applyInfo.put("logs", logs);

            //获取处理措施(获取上报和维修的 错误故障信息)
            List<Map<String, Object>> solution = repairApplyDao.getSolutionInfo(maintenanceApplyId);

            List<Long> errrolist = new ArrayList<>();
            List<Map<String, Object>> solutionmessge = new ArrayList<>();
            if (reportData != null && reportData.size() > 0)
            {
                for (int i = 0; i < reportData.size(); i++)
                {
                    errrolist.add(DataSwitch.convertObjectToLong(reportData.get(i).get("breakdownInfoId").toString()));
                }
            }
            if (repairData != null && repairData.size() > 0)
            {
                for (int i = 0; i < repairData.size(); i++)
                {
                    errrolist.add(DataSwitch.convertObjectToLong(repairData.get(i).get("breakdownInfoId").toString()));
                }

            }
            if (errrolist != null && errrolist.size() > 0)
            {
                findEntity.setErrors(errrolist);
                solutionmessge = repairApplyDao.getTreatmentInfoList(findEntity);
                if (solutionmessge != null)
                {
                    for (int i = 0; i < solutionmessge.size(); i++)
                    {
                        Map<String, Object> selectmap = solutionmessge.get(i);
                        long repairInfoId = DataSwitch.convertObjectToLong(selectmap.get("repairInfoId"));
                        for (int n = 0; n < solution.size(); n++)
                        {
                            long repairInfoId1 = DataSwitch.convertObjectToLong(solution.get(n).get("repairInfoId"));
                            if (repairInfoId == repairInfoId1)
                            {
                                selectmap.put("processMode", DataSwitch.convertObjectToString(solution.get(n).get("processMode")));
                                selectmap.put("checked", true);
                                break;
                            }

                        }

                    }
                }

            }
            if (solution != null)
            {
                for (int i = 0; i < solution.size(); i++)
                {
                    if ("0".equals(DataSwitch.convertObjectToString(solution.get(i).get("repairInfoId"))))
                    {
                        solutionmessge.add(solution.get(i));
                    }
                }
            }

            applyInfo.put("solution", solutionmessge);
            //获取配件更换信息
            List<Map<String, Object>> changeInfo = repairApplyDao.getSparePartChangeInfo(maintenanceApplyId);
            List<Long> sparePartIds = new ArrayList<>();
            sparePartIds.add(0L);
            if (changeInfo != null)
            {
                for (Map<String, Object> map : changeInfo)
                {
                    sparePartIds.add(DataSwitch.convertObjectToLong(map.get("sparePartId")));
                }
            }
            RepairApplyInfoVO applyInfoVO = new RepairApplyInfoVO();
            applyInfoVO.setSparePartIds(sparePartIds);
            List<Map<String, Object>> repairerrorPhenomenon = repairApplyDao.getBreakdownList(applyInfoVO);
            //维修数据
            List<Map<String, Object>> repairErrorPhenomenon = repairerrorPhenomenon.stream().map(error -> {
                Long breakdownInfoId = DataSwitch.convertObjectToLong(error.get("breakdownInfoId"));
                boolean isChecked = repairData.stream().filter(map -> DataSwitch.convertObjectToLong(map.get("breakdownInfoId")).equals(breakdownInfoId)).count() > 0;
                error.put("faultType", FaultType.REPAIR.getCode());
                error.put("checked", isChecked);
                return error;
            }).collect(Collectors.toList());

            applyInfo.put("repairErrorPhenomenon", repairErrorPhenomenon);


            applyInfo.put("changeInfo", changeInfo);
            return applyInfo;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private WxMpService wxMpService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRepairApplyInfo(List<CommonsMultipartFile> photos, RepairApplyInfoVO repairInfoVO) throws Exception
    {
        try
        {
            String userKey = repairInfoVO.getUserKey();
            UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
            String realName = userInfo.getRealName();
            if (StringUtils.isEmpty(realName))
            {
                realName = userInfo.getUserName();
            }

            Long operationSubjectId = userInfo.getOperationSubjectId();
            Long userId = userInfo.getUserId();
            //添加主表信息
            if (ObjectUtils.isNull(repairInfoVO.getLineId()))
            {
                Long lineId = repairApplyDao.getLineIdByStationId(repairInfoVO.getStationId());
                repairInfoVO.setLineId(lineId);
            }
            repairInfoVO.setApplyNO(NumberFactory.getApplyNo(ApplyNumberPrefixEnum.REPAIR));
            repairInfoVO.setApplyUser(realName);
            repairInfoVO.setApplyDate(DateUtils.getNow());
            repairInfoVO.setOperationSubjectId(operationSubjectId);
            repairInfoVO.setCreateUserId(userId);
            repairInfoVO.setCreateUser(realName);
            repairInfoVO.setModifyUser(realName);
            repairInfoVO.setWasFinished("none");
            repairInfoVO.setAppraiseWasFinished("yes");
            repairInfoVO.setApplyStatus(RepairApplyStatusEnum.toBeDispatch.getValue());


            //如果是提交人有维修权限，则为自修，添加人为自己，指派人为自己，维修人为自己，状态为维修中
            boolean isSelf = userInfo.getPermissions().contains("jxgl");
            if (isSelf)
            {
                repairInfoVO.setAppointUser(realName);
                repairInfoVO.setAppointUserId(userId);
                repairInfoVO.setAppointDate(DateUtils.getNow());
                repairInfoVO.setMaintenanceUser(realName);
                repairInfoVO.setMaintenanceUserID(userId);
                repairInfoVO.setApplyStatus(RepairApplyStatusEnum.maintenance.getValue());
            }
            repairApplyDao.insertRepairApplyInfo(repairInfoVO);
            Long maintenanceApplyId = repairInfoVO.getMaintenanceApplyId();

            //添加错误信息
            List<Long> errors = repairInfoVO.getErrors();
            for (Long errorId : errors)
            {
                MaintenanceErrorFaultEntity faultEntity = new MaintenanceErrorFaultEntity();
                faultEntity.setBreakdownInfoId(errorId);
                faultEntity.setMaintenanceApplyId(maintenanceApplyId);
                faultEntity.setFaultType(FaultType.REPORT.getCode());
                repairApplyDao.insertErrorFault(faultEntity);
            }
            //添加维修申请现场图片信息
            if (photos != null)//电脑端
            {
                int size = photos.size();
                for (int i = 0; i < size; i++)
                {
                    CommonsMultipartFile commonsMultipartFile = photos.get(i);

                    if (commonsMultipartFile.getFileItem() != null && commonsMultipartFile.getFileItem().getSize() > 0)
                    {
                        String name = commonsMultipartFile.getFileItem().getName();
                        if (ObjectUtils.isEmpty(name))
                        {
                            continue;
                        }
                        String extName = name.substring(name.lastIndexOf(".") + 1);
                        byte[] bytes = commonsMultipartFile.getBytes(); //将文件转换成字节流形式
                        String fileId = FdfsClient.upload(bytes, extName, null);
                        MaintenanceApplyPicEntity picEntity = new MaintenanceApplyPicEntity();
                        picEntity.setCreateUser(realName);
                        picEntity.setMaintenanceApplyId(maintenanceApplyId);
                        picEntity.setPicUrl(fileId);
                        picEntity.setType(FaultType.REPORT.getCode());
                        repairApplyDao.insertRepairApplyPic(picEntity);
                    }
                }
            }
            else //手机端
            {
                List<String> images = repairInfoVO.getImages();
                if (StringUtils.isNotEmpty(images))
                {
                    for (String image : images)
                    {
                        File file = wxMpService.getMaterialService().mediaDownload(image);
                        String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                        String fileId = FdfsClient.upload(FileUtil.file2buf(file), extName, null);
                        MaintenanceApplyPicEntity picEntity = new MaintenanceApplyPicEntity();
                        picEntity.setCreateUser(realName);
                        picEntity.setMaintenanceApplyId(maintenanceApplyId);
                        picEntity.setPicUrl(fileId);
                        picEntity.setType(FaultType.REPORT.getCode());
                        repairApplyDao.insertRepairApplyPic(picEntity);
                    }
                }
            }

            //添加日志
            MaintenanceLogsEntity logsEntity = new MaintenanceLogsEntity();
            logsEntity.setMaintenanceApplyId(maintenanceApplyId);
            logsEntity.setStatus(RepairApplyLogEnum.Reported.getValue());
            logsEntity.setInitiatorId(userId);
            logsEntity.setInitiator(realName);
            logsEntity.setCreateTime(DateUtils.getNow());
            logsEntity.setModifyTime(DateUtils.getNow());
            logsEntity.setCreateUser(realName);
            logsEntity.setModifyUser(realName);
            repairApplyDao.insertRepairLog(logsEntity);

            if (isSelf)
            {
                logsEntity = new MaintenanceLogsEntity();
                logsEntity.setMaintenanceApplyId(maintenanceApplyId);
                logsEntity.setStatus(RepairApplyLogEnum.Dispatched.getValue());
                logsEntity.setInitiatorId(userId);
                logsEntity.setInitiator(realName);
                logsEntity.setTargetPersonId(userId);
                logsEntity.setTargetPerson(realName);
                logsEntity.setCreateTime(DateUtils.getNow());
                logsEntity.setModifyTime(DateUtils.getNow());
                logsEntity.setCreateUser(realName);
                logsEntity.setModifyUser(realName);
                repairApplyDao.insertRepairLog(logsEntity);
            }
            else
            {
                pendingTaskService.insertRepairTask(repairInfoVO);
            }
            //发送微信或者短信消息
            taskMessageService.insertRepairTaskMessage(repairInfoVO);
        }
        catch (Exception ex)
        {
            System.out.println("异常来了=-==================================================");
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getEquipmentType(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getEquipmentType(findEntity.getOperationSubjectId());
            return data;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getErrorPhenomenonByEquipmentId(Long equipmentId)
    {
        try
        {
            return repairApplyDao.getErrorPhenomenonByEquipmentId(equipmentId);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getEquipmentList(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> maps = repairApplyDao.getEquipmentList(findEntity);
            //            maps.stream().forEach(r -> {
            //                String equipmentNo = DataSwitch.convertObjectToString(r.get("equipmentNo"));
            //                equipmentNo = equipmentNo.substring(equipmentNo.length() - 2);
            //                r.put("equipmentNo", equipmentNo);
            //            });
            return maps;


        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getConcatPeopleList(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getConcatPeopleList(findEntity);

            if ("pc".equalsIgnoreCase(findEntity.getType()))
            {
                data.stream().forEach(child -> {
                    String photoUrl = DataSwitch.convertObjectToString(child.get("photoUrl"));
                    if (StringUtils.isNotEmpty(photoUrl))
                    {
                        photoUrl = FdfsClient.getDownloadServer() + photoUrl;
                    }
                    child.put("photoUrl", photoUrl);
                });
                return data;
            }
            else
            {
                List<Map<String, Object>> result = new ArrayList<>();
                data.stream().map(child -> {
                    String photoUrl = DataSwitch.convertObjectToString(child.get("photoUrl"));
                    if (StringUtils.isNotEmpty(photoUrl))
                    {
                        photoUrl = FdfsClient.getDownloadServer() + photoUrl;
                    }
                    child.put("photoUrl", photoUrl);
                    return child;
                }).collect(Collectors.groupingBy(map -> {
                    String realNamePinYin = DataSwitch.convertObjectToString(map.get("realNamePinYin"));
                    if (ObjectUtils.isNotEmpty(realNamePinYin))
                    {
                        return realNamePinYin.substring(0, 1).toUpperCase();
                    }
                    else
                    {
                        return "";
                    }
                })).entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> {
                    Map<String, Object> row = new HashMap<>();
                    row.put("index", entry.getKey());
                    List<Map<String, Object>> children =
                            entry.getValue().stream().sorted(Comparator.comparing(map -> DataSwitch.convertObjectToString(map.get("realNameAllPinYin")))).collect(Collectors.toList());
                    row.put("children", children);
                    result.add(row);
                });
                return result;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setConcatPeople(RepairApplyInfoVO findEntity) throws Exception
    {
        try
        {
            repairApplyDao.deleteCopyInfo(findEntity.getMaintenanceApplyId());
            List<Long> userIds = findEntity.getUserIds();
            for (Long userId : userIds)
            {
                //TODO 添加抄送人消息
                MaintenanceCopyEntity copyEntity = new MaintenanceCopyEntity();
                copyEntity.setMaintenanceApplyId(findEntity.getMaintenanceApplyId());
                copyEntity.setCopyToUserId(userId);
                repairApplyDao.insertCopyInfo(copyEntity);

                TaskMessageEntity taskMessageEntity=new TaskMessageEntity();
                taskMessageEntity.setUserKey(findEntity.getUserKey());
                taskMessageEntity.setMessageType(RepairApplyLogEnum.Dispatched.getValue());
                taskMessageEntity.setReceiveId(userId);
                taskMessageEntity.setContent("您有一条新的工单信息，请及时处理！");
                taskMessageService.insertTaskMessage(taskMessageEntity);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager getRepairApplyPageInfo(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getRepairApplyPageInfo(findEntity);

            //获取抄送人
            if (data.size() > 0)
            {
                List<Long> maintenanceApplyIds = data.stream().map(map -> DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"))).collect(Collectors.toList());
                String idStr = StringUtils.listToString(maintenanceApplyIds);
                List<Map<String, Object>> copyInfo = repairApplyDao.getCopyPeopleByApplyIds(idStr);
                data = data.stream().map(map -> {
                    Long maintenanceApplyId = DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"));
                    List<Map<String, Object>> copyPeople =
                            copyInfo.stream().filter(copy -> DataSwitch.convertObjectToLong(copy.get("maintenanceApplyId")).equals(maintenanceApplyId)).collect(Collectors.toList());
                    map.put("copyInfo", copyPeople);
                    return map;
                }).collect(Collectors.toList());
            }
            int total = repairApplyDao.getRepairApplyPageInfoTotal(findEntity);
            Pager pager = new Pager(total, data);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager getPhoneRepairApplyPageInfo(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getPhoneRepairApplyPageInfo(findEntity);

            //获取抄送人
            if (data.size() > 0)
            {
                List<Long> maintenanceApplyIds = data.stream().map(map -> DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"))).collect(Collectors.toList());
                String idStr = StringUtils.listToString(maintenanceApplyIds);
                List<Map<String, Object>> copyInfo = repairApplyDao.getCopyPeopleByApplyIds(idStr);
                data = data.stream().map(map -> {
                    Long maintenanceApplyId = DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"));
                    List<Map<String, Object>> copyPeople =
                            copyInfo.stream().filter(copy -> DataSwitch.convertObjectToLong(copy.get("maintenanceApplyId")).equals(maintenanceApplyId)).collect(Collectors.toList());
                    map.put("copyInfo", copyPeople);
                    return map;
                }).collect(Collectors.toList());
            }
            int total = repairApplyDao.getPhoneRepairApplyPageInfoTotal(findEntity);
            Pager pager = new Pager(total, data);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getRepairerInfo(RepairApplyFindEntity findEntity)
    {
        try
        {
            return repairApplyDao.getRepairerInfo(findEntity.getStationId());
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO appointRepairInfo(RepairApplyInfoVO applyInfoVO) throws Exception
    {
        try
        {
            //获取维修主表信息
            Map<String, Object> applyInfo = repairApplyDao.getApplyInfoById(applyInfoVO.getMaintenanceApplyId());
            String state = DataSwitch.convertObjectToString(applyInfo.get("applyStatus"));
            if(!RepairApplyStatusEnum.toBeDispatch.getValue().equals(state) && !RepairApplyStatusEnum.noRepair.getValue().equals(state))
            {
                return ResultVOUtil.error(401, "维修单已派单！");
            }

            String userKey = applyInfoVO.getUserKey();
            UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
            String userName = userInfo.getRealName();
            Long userId = userInfo.getUserId();
            applyInfoVO.setAppointDate(DateUtils.getNow());
            applyInfoVO.setAppointUserId(userId);
            applyInfoVO.setAppointUser(userName);
            applyInfoVO.setApplyStatus(RepairApplyStatusEnum.toBeRepair.getValue());
            applyInfoVO.setModifyUser(userName);
            repairApplyDao.appointRepairInfo(applyInfoVO);

            Long maintenanceApplyId = applyInfoVO.getMaintenanceApplyId();
            List<Long> userIds = applyInfoVO.getUserIds();

            for (Long copyUserId : userIds)
            {
                //TODO 添加抄送人消息
                MaintenanceCopyEntity copyEntity = new MaintenanceCopyEntity();
                copyEntity.setMaintenanceApplyId(maintenanceApplyId);
                copyEntity.setCopyToUserId(copyUserId);
                repairApplyDao.insertCopyInfo(copyEntity);

                TaskMessageEntity taskMessageEntity=new TaskMessageEntity();
                taskMessageEntity.setUserKey(userKey);
                taskMessageEntity.setMessageType(RepairApplyLogEnum.Dispatched.getValue());
                taskMessageEntity.setReceiveId(userId);
                taskMessageEntity.setContent("您有一条新的工单信息，请及时处理！");
                taskMessageService.insertTaskMessage(taskMessageEntity);
            }
            //添加日志
            MaintenanceLogsEntity logsEntity = new MaintenanceLogsEntity();
            logsEntity.setMaintenanceApplyId(applyInfoVO.getMaintenanceApplyId());
            logsEntity.setStatus(RepairApplyLogEnum.Dispatched.getValue());
            logsEntity.setInitiatorId(userId);
            logsEntity.setInitiator(userName);
            logsEntity.setTargetPersonId(applyInfoVO.getMaintenanceUserID());
            logsEntity.setTargetPerson(applyInfoVO.getMaintenanceUser());
            logsEntity.setCreateTime(DateUtils.getNow());
            logsEntity.setModifyTime(DateUtils.getNow());
            logsEntity.setCreateUser(userName);
            logsEntity.setModifyUser(userName);
            repairApplyDao.insertRepairLog(logsEntity);
            pendingTaskService.insertRepairTask(applyInfoVO);
            //发送微信或者短信消息
            taskMessageService.insertRepairTaskMessage(applyInfoVO);
            return ResultVOUtil.success();

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager getRepairInfoPager(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getRepairInfoPager(findEntity);
            //获取抄送人
            if (data.size() > 0)
            {
                List<Long> maintenanceApplyIds = data.stream().map(map -> DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"))).collect(Collectors.toList());
                String idStr = StringUtils.listToString(maintenanceApplyIds);
                List<Map<String, Object>> copyInfo = repairApplyDao.getCopyPeopleByApplyIds(idStr);
                data = data.stream().map(map -> {
                    Long maintenanceApplyId = DataSwitch.convertObjectToLong(map.get("maintenanceApplyId"));
                    List<Map<String, Object>> copyPeople =
                            copyInfo.stream().filter(copy -> DataSwitch.convertObjectToLong(copy.get("maintenanceApplyId")).equals(maintenanceApplyId)).collect(Collectors.toList());
                    map.put("copyInfo", copyPeople);
                    return map;
                }).collect(Collectors.toList());
            }
            int total = repairApplyDao.getRepairInfoPagerTotal(findEntity);

            Pager pager = new Pager(total, data);
            return pager;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getTreatmentInfoList(RepairApplyFindEntity findEntity)
    {
        try
        {
            return repairApplyDao.getTreatmentInfoList(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    public List<Map<String, Object>> getSparePartsInBag(RepairApplyFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = repairApplyDao.getSparePartsInBag(findEntity);
            return data;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repairCheckIn(RepairApplyInfoVO applyInfoVO) throws Exception
    {
        try
        {
            String userKey = applyInfoVO.getUserKey();
            UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
            String userName = userInfo.getRealName();
            Long userId = userInfo.getUserId();

            //更新申请状态
            applyInfoVO.setApplyStatus(RepairApplyStatusEnum.maintenance.getValue());
            applyInfoVO.setArrivalTime(DateUtils.getNow());
            applyInfoVO.setModifyUser(userName);

            repairApplyDao.repairCheckIn(applyInfoVO);


            Map<String, Object> miantenceMap = repairApplyDao.getMaintenanceByMaintenanceApplyId(applyInfoVO.getMaintenanceApplyId());
            applyInfoVO.setApplyNO(DataSwitch.convertObjectToString(miantenceMap.get("applyNO")));
            //更新之前的任务状态是完成
            TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
            taskEntity.setUserKey(applyInfoVO.getUserKey());
            taskEntity.setBusiNo(applyInfoVO.getApplyNO());
            pendingTaskService.updateTaskOver(taskEntity);

            //applyInfoVO.setApplyStatus(RepairApplyStatusEnum.maintenance.getValue());
            pendingTaskService.insertRepairTask(applyInfoVO);
            //发送微信或者短信消息
            taskMessageService.insertRepairTaskMessage(applyInfoVO);
            //添加日志
            MaintenanceLogsEntity logsEntity = new MaintenanceLogsEntity();
            logsEntity.setMaintenanceApplyId(applyInfoVO.getMaintenanceApplyId());
            logsEntity.setStatus(RepairApplyLogEnum.Repairing.getValue());
            logsEntity.setInitiatorId(userId);
            logsEntity.setInitiator(userName);
            logsEntity.setCreateTime(DateUtils.getNow());
            logsEntity.setModifyTime(DateUtils.getNow());
            logsEntity.setCreateUser(userName);
            logsEntity.setModifyUser(userName);
            repairApplyDao.insertRepairLog(logsEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void repairEquipment(List<CommonsMultipartFile> photos, RepairApplyInfoVO repairApplyInfoVO) throws Exception
    {
        try
        {
            {
                //验证旧验证码是否存在
                List<ReplaceSparePartVO> changeSparePartEntities = repairApplyInfoVO.getChangeSparePartEntities();
                List<String> oldQrCodes = changeSparePartEntities.stream().map(ReplaceSparePartVO::getOldQrCode).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
                List<String> newQrCodes = changeSparePartEntities.stream().map(ReplaceSparePartVO::getNewQrCode).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
                oldQrCodes.addAll(newQrCodes);//合并2个二维码

                if (oldQrCodes.size() > 0)
                {
                    String qrCodes = StringUtils.listToString(oldQrCodes.stream().map(str -> "'" + str + "'").collect(Collectors.toList()));
                    List<Map<String, Object>> spareParts = repairApplyDao.getSparePartsByQrCode(qrCodes);//从数据库中找到新旧件的数据
                    for (int i = 0; i < changeSparePartEntities.size(); i++)
                    {
                        ReplaceSparePartVO sparePartVO = changeSparePartEntities.get(i);
                        //验证新旧二维码是否一样
                        if (StringUtils.isNotEmpty(sparePartVO.getNewQrCode()) && StringUtils.isNotEmpty(sparePartVO.getOldQrCode()))
                        {
                            if (sparePartVO.getNewQrCode().equals(sparePartVO.getOldQrCode()) && StringUtils.isNotEmpty(sparePartVO.getOldQrCode()))
                            {
                                throw new ErrorQrCodeException("第 " + (i + 1) + "新旧二维码不能一样!");
                            }
                        }
                        //验证旧二维码是否可用
                        String oldQrCode = sparePartVO.getOldQrCode();
                        if (StringUtils.isNotEmpty(oldQrCode))
                        {
                            Optional<Map<String, Object>> oldSparePart = spareParts.stream().filter(map -> oldQrCode.equalsIgnoreCase(DataSwitch.convertObjectToString(map.get("qrCode")))).findFirst();

                            if (Optional.empty().equals(oldSparePart))
                            {
                                throw new ErrorQrCodeException("第 " + (i + 1) + "行旧部件二维码库存中不存在!");
                            }
                            sparePartVO.setOldInventoryType(DataSwitch.convertObjectToString(oldSparePart.get().get("inventoryType")));
                            sparePartVO.setOldStockId(DataSwitch.convertObjectToLong(oldSparePart.get().get("stockId")));
                            sparePartVO.setOldSparePartId(DataSwitch.convertObjectToLong(oldSparePart.get().get("sparePartId")));

                        }
                        //验证新二维码是否可用
                        String newQrCode = sparePartVO.getNewQrCode();
                        if (StringUtils.isNotEmpty(newQrCode))
                        {
                            spareParts = repairApplyDao.getSparePartsByQrCode(qrCodes);//从数据库中找到新旧件的数据
                            Optional<Map<String, Object>> sparePart = spareParts.stream().filter(map -> newQrCode.equalsIgnoreCase(DataSwitch.convertObjectToString(map.get("qrCode")))).findFirst();

                            if (Optional.empty().equals(sparePart))
                            {
                                throw new ErrorQrCodeException("第 " + (i + 1) + "行新部件二维码库存中不存在!");
                            }
                        }
                        //验证旧部件的备件类型必须跟新换上的备件类型一样
                        if (StringUtils.isNotEmpty(sparePartVO.getOldQrCode()))
                        {
                            String oleStockSparePartTypeId = repairApplyDao.getStockEntityByQrCode(sparePartVO.getOldQrCode());
                            String newStockSparePartTypeId = repairApplyDao.getStockSparePartTypeIdByStockId(sparePartVO.getNewStockId());
                            if (!newStockSparePartTypeId.equals(oleStockSparePartTypeId))
                            {
                                throw new ErrorQrCodeException("第 " + (i + 1) + "行数据的备件类型不一致!");
                            }
                        }
                    }
                }


                String userKey = repairApplyInfoVO.getUserKey();
                UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
                String userName = userInfo.getRealName();
                Long userId = userInfo.getUserId();
                Long maintenanceApplyId = repairApplyInfoVO.getMaintenanceApplyId();
                //更新申请单状态
                repairApplyInfoVO.setOverTime(DateUtils.getNow());
                repairApplyInfoVO.setApplyStatus(repairApplyInfoVO.getWasFinished()
                        .equalsIgnoreCase("no") ? RepairApplyStatusEnum.noRepair.getValue()
                        : RepairApplyStatusEnum.repaired.getValue());
                repairApplyDao.repairedEquipment(repairApplyInfoVO);
                //删除处理故障现象补充deleteMmaintenanceErrorFault
                repairApplyDao.deleteMmaintenanceErrorFault(maintenanceApplyId);
                //删除维修方案信息
                repairApplyDao.deleteMaintenanceSolution(maintenanceApplyId);
                //添加处理措施
                List<MaintenanceSolutionEntity> solutionEntities = repairApplyInfoVO.getSolutionEntities();
                for (MaintenanceSolutionEntity solutionEntity : solutionEntities)
                {
                    solutionEntity.setMaintenanceApplyId(maintenanceApplyId);
                    repairApplyDao.insertSolutionInfo(solutionEntity);
                }
                //删除处理故障现象补充deleteMmaintenanceErrorFault
                //repairApplyDao.deleteMmaintenanceErrorFault(maintenanceApplyId);
                //错误故障 维修
                List<Long> errors = repairApplyInfoVO.getErrors();
                if (errors != null)
                {
                    for (Long errorId : errors)
                    {
                        MaintenanceErrorFaultEntity faultEntity = new MaintenanceErrorFaultEntity();
                        faultEntity.setBreakdownInfoId(errorId);
                        faultEntity.setMaintenanceApplyId(maintenanceApplyId);
                        faultEntity.setFaultType(FaultType.REPAIR.getCode());
                        repairApplyDao.insertErrorFault(faultEntity);
                    }
                }
                //错误故障 上报
                List<Long> reportedErrors = repairApplyInfoVO.getReportedErrors();
                if (reportedErrors != null)
                {
                    for (Long errorId : reportedErrors)
                    {
                        MaintenanceErrorFaultEntity faultEntity = new MaintenanceErrorFaultEntity();
                        faultEntity.setBreakdownInfoId(errorId);
                        faultEntity.setMaintenanceApplyId(maintenanceApplyId);
                        faultEntity.setFaultType(FaultType.REPORT.getCode());
                        repairApplyDao.insertErrorFault(faultEntity);
                    }
                }

                repairApplyInfoVO.getImages();

                //添加维修申请现场图片信息
                if (photos != null)//电脑端
                {
                    //维修完成的时候如果上传了图片就把之前的删除掉
                    repairApplyDao.deleteRepairedImage(repairApplyInfoVO.getMaintenanceApplyId());
                    int size = photos.size();
                    for (int i = 0; i < size; i++)
                    {
                        CommonsMultipartFile commonsMultipartFile = photos.get(i);
                        if (commonsMultipartFile.getFileItem() != null && commonsMultipartFile.getFileItem().getSize() > 0)
                        {
                            String name = commonsMultipartFile.getFileItem().getName();
                            if (ObjectUtils.isEmpty(name))
                            {
                                continue;
                            }
                            String extName = name.substring(name.lastIndexOf(".") + 1);
                            byte[] bytes = null;
                            bytes = commonsMultipartFile.getBytes(); //将文件转换成字节流形式
                            String fileId = FdfsClient.upload(bytes, extName, null);
                            MaintenanceApplyPicEntity picEntity = new MaintenanceApplyPicEntity();
                            picEntity.setCreateUser(userName);
                            picEntity.setMaintenanceApplyId(maintenanceApplyId);
                            picEntity.setPicUrl(fileId);
                            picEntity.setType(FaultType.REPAIR.getCode());
                            repairApplyDao.insertRepairApplyPic(picEntity);
                        }
                    }
                }
                else
                {
                    if (repairApplyInfoVO.getImages() != null && repairApplyInfoVO.getImages().size() > 0)
                    {
                        //维修完成的时候如果上传了图片就把之前的删除掉
                        repairApplyDao.deleteRepairedImage(repairApplyInfoVO.getMaintenanceApplyId());
                    }
                    List<String> images = repairApplyInfoVO.getImages();
                    if (ObjectUtils.isNotEmpty(images))
                    {
                        for (String image : images)
                        {
                            File file = wxMpService.getMaterialService().mediaDownload(image);
                            String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                            String fileId = FdfsClient.upload(FileUtil.file2buf(file), extName, null);
                            MaintenanceApplyPicEntity picEntity = new MaintenanceApplyPicEntity();
                            picEntity.setCreateUser(userName);
                            picEntity.setMaintenanceApplyId(maintenanceApplyId);
                            picEntity.setPicUrl(fileId);
                            picEntity.setType(FaultType.REPAIR.getCode());
                            repairApplyDao.insertRepairApplyPic(picEntity);
                        }
                    }
                }

                //处理更换设备

                for (ReplaceSparePartVO changeSparePartEntity : changeSparePartEntities)
                {
                    changeSparePartEntity.setMaintenanceApplyId(maintenanceApplyId);
                    repairApplyDao.insertChangeSparePartInfo(changeSparePartEntity);

                    //维护线上设备备件
                    if (InventoryTypeEnum.UNIQUE.getCode().equalsIgnoreCase(changeSparePartEntity.getInventoryType()))
                    {
                        //添加线上设备的备件信息,状态为online
                        //repairApplyDao.insertOnlineEquipmentSparePart()
                        OperationsEquipmentPartsEntity operationsEquipmentPartsEntity = new OperationsEquipmentPartsEntity();
                        operationsEquipmentPartsEntity.setEquipmentId(repairApplyInfoVO.getEquipmentId());
                        operationsEquipmentPartsEntity.setSparePartId(changeSparePartEntity.getNewSparePartId());
                        operationsEquipmentPartsEntity.setStockId(changeSparePartEntity.getNewStockId());
                        operationsEquipmentPartsEntity.setMaintenanceApplyId(maintenanceApplyId);
                        operationsEquipmentPartsEntity.setStatus("online");
                        operationsEquipmentPartsEntity.setCreateUser(userName);
                        repairApplyDao.insertOnlineEquipmentSparePart(operationsEquipmentPartsEntity);
                    }
                    //如果更换下设备扫了二维码，更新线上设备的备件信息的状态为offline
                    if (ObjectUtils.isNotEmpty(changeSparePartEntity.getOldQrCode()))
                    {
                        repairApplyDao.updateEquipmentPartsToOffline(changeSparePartEntity);
                    }

                    //维护我的背包中的数据，只维护换上去的设备，要减去背包中的数量，换下来的不放入我的设备表
                    Long userDeviceId = changeSparePartEntity.getUserDeviceId();
                    if (ObjectUtils.isNotEmpty(userDeviceId))
                    {
                        repairApplyDao.updateSparePartsInMyBag(changeSparePartEntity);
                    }

                }

                // 添加日志
                MaintenanceLogsEntity logsEntity = new MaintenanceLogsEntity();
                logsEntity.setMaintenanceApplyId(maintenanceApplyId);
                if (repairApplyInfoVO.getWasFinished().equalsIgnoreCase("yes"))
                {
                    logsEntity.setStatus(RepairApplyLogEnum.Repaired.getValue());
                }
                else
                {
                    logsEntity.setStatus(RepairApplyLogEnum.Failed.getValue());
                }

                logsEntity.setInitiatorId(userId);
                logsEntity.setInitiator(userName);
                logsEntity.setCreateTime(DateUtils.getNow());
                logsEntity.setModifyTime(DateUtils.getNow());
                logsEntity.setCreateUser(userName);
                logsEntity.setModifyUser(userName);
                repairApplyDao.insertRepairLog(logsEntity);
                //更新之前的任务状态是完成
                TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
                taskEntity.setUserKey(repairApplyInfoVO.getUserKey());
                taskEntity.setBusiNo(repairApplyInfoVO.getApplyNO());
                pendingTaskService.updateTaskOver(taskEntity);
                //TODO 给维修单的添加人发任务消息，告诉他需要去评价
                //repairApplyInfoVO.setApplyStatus(RepairApplyStatusEnum.repaired.getValue());
                pendingTaskService.insertRepairTask(repairApplyInfoVO);
                //发送微信或者短信消息
                taskMessageService.insertRepairTaskMessage(repairApplyInfoVO);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    @Override
    public void repairEvaluation(RepairApplyInfoVO repairApplyInfoVO) throws Exception
    {
        try
        {
            String userKey = repairApplyInfoVO.getUserKey();
            UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
            String userName = userInfo.getRealName();
            Long userId = userInfo.getUserId();
            Long maintenanceApplyId = repairApplyInfoVO.getMaintenanceApplyId();

            repairApplyDao.repairEvaluation(repairApplyInfoVO);
            // 添加日志
            MaintenanceLogsEntity logsEntity = new MaintenanceLogsEntity();
            logsEntity.setMaintenanceApplyId(maintenanceApplyId);
            logsEntity.setStatus(RepairApplyLogEnum.Rated.getValue());

            logsEntity.setInitiatorId(userId);
            logsEntity.setInitiator(userName);
            logsEntity.setCreateTime(DateUtils.getNow());
            logsEntity.setModifyTime(DateUtils.getNow());
            logsEntity.setCreateUser(userName);
            logsEntity.setModifyUser(userName);
            repairApplyDao.insertRepairLog(logsEntity);

            //更新之前的任务状态是完成
            Map<String, Object> miantenceMap = repairApplyDao.getMaintenanceByMaintenanceApplyId(maintenanceApplyId);
            TaskPendingTaskEntity taskEntity = new TaskPendingTaskEntity();
            taskEntity.setUserKey(repairApplyInfoVO.getUserKey());
            taskEntity.setBusiNo(DataSwitch.convertObjectToString(miantenceMap.get("applyNO")));
            pendingTaskService.updateTaskOver(taskEntity);

            TaskMessageVo taskMessageVo=new TaskMessageVo();
            taskMessageVo.setReceiveId(DataSwitch.convertObjectToLong(miantenceMap.get("maintenanceUserID")));
            taskMessageVo.setContent(String.format("您的维修单【单号：%s】已评价！",DataSwitch.convertObjectToString(miantenceMap.get("applyNO"))) );
            taskMessageVo.setSendUrl(false);
            taskMessageVo.setSourceId(maintenanceApplyId);
            taskMessageVo.setTypeName("评价完成");
            //发送微信消息或者短信
            taskMessageService.insertTaskMessage(taskMessageVo);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getBreakdownList(RepairApplyInfoVO applyInfoVO)
    {
        return repairApplyDao.getBreakdownList(applyInfoVO);
    }

    /**
     * 根据二维码返回设备基本信息
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-27 11:02
     */
    @Override
    public ResultVO getDrviceInforByQrCode(GetDrviceInforByQrcodeFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> QrListMap = repairApplyDao.getDrviceInforByQrCode(findEntity);
            if (QrListMap != null && QrListMap.size() > 0)
            {
                List<Map<String, Object>> QrDrviceListMap = QrListMap.stream().filter(map -> map.get("userId").toString().equals(findEntity.getOperationUserId().toString())).collect(Collectors.toList());
                if (QrDrviceListMap != null && QrDrviceListMap.size() > 0)
                {
                    return ResultVOUtil.success(QrDrviceListMap);
                }
                else
                {
                    return ResultVOUtil.error(-201, "该备件为其他用户持有!");
                }
            }
            return ResultVOUtil.error(-202, "您的背包未找到该备件信息!");
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}
