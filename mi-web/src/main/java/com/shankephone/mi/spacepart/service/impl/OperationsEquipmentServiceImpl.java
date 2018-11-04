package com.shankephone.mi.spacepart.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OperationsEquipmentEntity;
import com.shankephone.mi.model.StockQrCodeEntity;
import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.spacepart.dao.OperationsEquipmentDao;
import com.shankephone.mi.spacepart.formbean.OperationsEquipmentFindEntity;
import com.shankephone.mi.spacepart.service.OperationsEquipmentService;
import com.shankephone.mi.stock.dao.OutStockApplyDao;
import com.shankephone.mi.stock.dao.QRCodeDao;
import com.shankephone.mi.stock.formbean.QRCodeFindEntity;
import com.shankephone.mi.stock.formbean.UpdateStockQrCodeFormBean;
import com.shankephone.mi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 营运设备Service
 *
 * @author 司徒彬
 * @date 2018 /8/20 15:20
 */
@Service
public class OperationsEquipmentServiceImpl implements OperationsEquipmentService
{

    /**
     * The Operations equipment dao.
     */
    @Autowired
    OperationsEquipmentDao operationsEquipmentDao;
    @Autowired
    FieldContentExistsDao fieldContentExistsDao;

    @Override
    public Pager getPagerInfo(OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = operationsEquipmentDao.getPagerInfo(findEntity);
            data.stream().forEach(map -> {
                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
                if (StringUtils.isEmpty(imageUrl))
                {
                    sparePartPicUrl = "";
                }

                map.put("imageUrl", sparePartPicUrl);
            });
            int total = operationsEquipmentDao.getPagerInfoTotal(findEntity);
            return new Pager(total, data);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getStationList(OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> data = operationsEquipmentDao.getStationList(findEntity);
            return data;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private QRCodeDao qrCodeDao;
    @Autowired
    private OutStockApplyDao outStockApplyDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO addEquipment(OperationsEquipmentEntity equipmentEntity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
            filedentity.setUserKey(equipmentEntity.getUserKey());
            filedentity.setTablename("operations_equipment");
            filedentity.setFieldName("equipmentNo");
            filedentity.setFieldValue(equipmentEntity.getEquipmentNo());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "设备编号已存在,请重新填写！");
            }

            if (StringUtils.isNotEmpty(equipmentEntity.getQrCode()))
            {
                //验证二维码是否存在
                QRCodeFindEntity findEntity=new QRCodeFindEntity();
                findEntity.setUserKey(equipmentEntity.getUserKey());
                findEntity.setQrCode(equipmentEntity.getQrCode());
                findEntity.setStatus(StatusEnum.START_USING.getValue());
                List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
                if (entities.size() == 0)
                {
                    return ResultVOUtil.error(201, "二维码无效！");
                }
            }
            //更新二维码状态为不可用
            UpdateStockQrCodeFormBean stockQrCodeFormBean = new UpdateStockQrCodeFormBean();
            stockQrCodeFormBean.setQrCode(equipmentEntity.getQrCode());
            stockQrCodeFormBean.setStockId(0l);
            stockQrCodeFormBean.setSparePartId(equipmentEntity.getSparePartId());
            outStockApplyDao.updateQrCode(stockQrCodeFormBean);

            UserLoginInfo userLoginInfo = equipmentEntity.getUserLoginInfo();
            equipmentEntity.setCreateUser(equipmentEntity.getOperationUserName());
            equipmentEntity.setModifyUser(equipmentEntity.getOperationUserName());
            equipmentEntity.setOperationSubjectId(userLoginInfo.getOperationSubjectId());
            operationsEquipmentDao.addEquipment(equipmentEntity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateEquipment(OperationsEquipmentEntity equipmentEntity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
            filedentity.setUserKey(equipmentEntity.getUserKey());
            filedentity.setTablename("operations_equipment");
            filedentity.setFieldName("equipmentNo");
            filedentity.setFieldValue(equipmentEntity.getEquipmentNo());
            filedentity.setIdName("equipmentId");
            filedentity.setIdValue(equipmentEntity.getEquipmentId());

            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "设备编号已存在,请重新填写！");
            }

            //验证二维码是否存在
            if (StringUtils.isNotEmpty(equipmentEntity.getQrCode()))
            {
                Map<String, Object> sourceEntityMpa = operationsEquipmentDao.getEquipment(equipmentEntity.getEquipmentId());
                String sourceQrCode = DataSwitch.convertObjectToString(sourceEntityMpa.get("qrCode"));
                if(!sourceQrCode.equals(equipmentEntity.getQrCode()))
                {
                    QRCodeFindEntity findEntity = new QRCodeFindEntity();
                    findEntity.setUserKey(equipmentEntity.getUserKey());
                    findEntity.setQrCode(equipmentEntity.getQrCode());
                    findEntity.setStatus(StatusEnum.START_USING.getValue());
                    List<StockQrCodeEntity> entities = qrCodeDao.getQrCodeByQrCodeEntity(findEntity);
                    if (entities.size() == 0)
                    {
                        return ResultVOUtil.error(201, "二维码无效！");
                    }
                }
            }

            //更新二维码状态为不可用
            UpdateStockQrCodeFormBean stockQrCodeFormBean = new UpdateStockQrCodeFormBean();
            stockQrCodeFormBean.setQrCode(equipmentEntity.getQrCode());
            stockQrCodeFormBean.setStockId(0l);
            stockQrCodeFormBean.setSparePartId(equipmentEntity.getSparePartId());
            outStockApplyDao.updateQrCode(stockQrCodeFormBean);

            UserLoginInfo userLoginInfo = equipmentEntity.getUserLoginInfo();
            equipmentEntity.setModifyUser(equipmentEntity.getOperationUserName());
            equipmentEntity.setOperationSubjectId(userLoginInfo.getOperationSubjectId());
            operationsEquipmentDao.updateEquipment(equipmentEntity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipment(OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            operationsEquipmentDao.deleteEquipment(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getEquipment(OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            Long equipmentId = findEntity.getEquipmentId();
            Map<String, Object> equipmentInfo = operationsEquipmentDao.getEquipment(equipmentId);

            List<Map<String, Object>> images = operationsEquipmentDao.getEquipmentImages(equipmentId);
            images.stream().forEach(image -> {
                String imageUrl = DataSwitch.convertObjectToString(image.get("imageUrl"));
                if (StringUtils.isNotEmpty(imageUrl))
                {
                    imageUrl = FdfsClient.getDownloadServer() + imageUrl;//  PathSetting.getSparePartPicUrl(imageUrl);
                }
                image.put("imageUrl", imageUrl);
            });
            List<Map<String, Object>> spareParts = operationsEquipmentDao.getSparePartsInfo(equipmentId);

            spareParts.stream().forEach(image -> {
                String imageUrl = DataSwitch.convertObjectToString(image.get("imageUrl"));
                if (StringUtils.isNotEmpty(imageUrl))
                {
                    imageUrl = FdfsClient.getDownloadServer() + imageUrl;//  PathSetting.getSparePartPicUrl(imageUrl);
                }
                image.put("imageUrl", imageUrl);
            });

            equipmentInfo.put("images", images);
            equipmentInfo.put("spareParts", spareParts);

            return equipmentInfo;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getEquipmentEntity(OperationsEquipmentFindEntity findEntity)
    {
        try
        {
            Map<String, Object> equipmentMap = operationsEquipmentDao.getEquipmentEntity(findEntity);
            //           String equipmentNo = DataSwitch.convertObjectToString(equipmentMap.get("equipmentNo"));
//            if (StringUtils.isNotEmpty(equipmentNo))
//            {
//                equipmentMap.put("equipmentNo", equipmentNo.substring(equipmentNo.length() - 2));
//            }
            return equipmentMap;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importEquipmentList(OperationsEquipmentFindEntity findEntity, List<String[]> list)
    {
            try
            {
                List<String[]> stationlist = list;

                findEntity.setStart(0);
                findEntity.setLimit(Integer.MAX_VALUE);
                // findEntity.setWorkSectionId(StringUtils.listToString(findEntity.getWorkSectionRange()));
                findEntity.setSparePartTypeId("all");
                findEntity.setStationId("all");
                List<Map<String, Object>> messListMap = new ArrayList<>();
                List<OperationsEquipmentEntity> entityList = new ArrayList<OperationsEquipmentEntity>();
                List<Map<String, Object>> lineStationdata = operationsEquipmentDao.getLineStationList(findEntity);
                List<Map<String, Object>> partdata = operationsEquipmentDao.getOperationPartList(findEntity);
                List<Map<String, Object>> allEquipmentdata = this.getAllEquipmentListMap(findEntity);

                for (int i = 0; i < stationlist.size(); i++)
                {

                    // 线路编码 站点编码 设备类型 备件名称 设备编号 所在位置
                    String lineCode = stationlist.get(i)[0].trim();
                    String stationCode = stationlist.get(i)[1].trim();
                    String categoryName = stationlist.get(i)[2].trim();
                    String partName = stationlist.get(i)[3].trim();
                    String equipmentName = stationlist.get(i)[4].trim();
                    String equipmentNo = stationlist.get(i)[5].trim();
                    String location = stationlist.get(i)[6].trim();

                    String str = equipmentName+ equipmentNo + partName + lineCode + stationCode + location;
                    if ("".equals(str))
                    {
                        break;
                    }

                    OperationsEquipmentEntity entity = new OperationsEquipmentEntity();
                    entity.setEquipmentNo(equipmentNo);
                    entity.setLocation(location);
                    entity.setStatus(StatusEnum.START_USING.getValue());
                    entity.setOperationSubjectId(findEntity.getOperationSubjectId());
                    entity.setWorkSectionId(DataSwitch.convertObjectToLong(findEntity.getWorkSectionId()));


                    StringBuilder messb = new StringBuilder();
                    if (ObjectUtils.isEmpty(lineCode))
                    {
                        messb.append("线路编码不能为空;");
                    }
                    if (StringUtils.isEmpty(stationCode))
                    {
                        messb.append("站点编码不能为空;");
                    }
                    if (ObjectUtils.isEmpty(partName))
                    {
                        messb.append("备件名称不能为空;");
                    }
                    if (ObjectUtils.isEmpty(equipmentNo))
                    {
                        messb.append("设备编号不能为空;");
                    }
                    if (equipmentName.length() > 64)
                    {
                        messb.append("设备名称不能超过64个字;");
                    }
                    if (equipmentNo.length() > 64)
                    {
                        messb.append("设备编号不能超过64个字符;");
                    }
                    if (location.length() > 128)
                    {
                        messb.append("所在位置不能超过128个字;");
                    }

                    List<String[]> list1 = list.stream().filter(x -> x[5].trim().equals(equipmentNo)).collect(Collectors.toList());
                    if (list1 != null && list1.size() > 1)
                    {
                        messb.append("execl中设备编号不能重复;");
                    }
                    entity.setEquipmentName(equipmentName);
                    List<Map<String, Object>> lineStationMapList = lineStationdata.parallelStream().filter(linstationmap -> lineCode.equals(linstationmap.get("lineCode").toString()) && stationCode.equals(linstationmap.get("stationCode").toString())).collect(Collectors.toList());
                    if (lineStationMapList != null && lineStationMapList.size() > 0)
                    {
                        entity.setLineId(DataSwitch.convertObjectToLong(lineStationMapList.get(0).get("lineId")));
                        entity.setStationId(DataSwitch.convertObjectToLong(lineStationMapList.get(0).get("stationId")));
                    }
                    else
                    {
                            //messb.append("线路编码和站点编码不对应;");
                        List<Map<String, Object>> stationMapList = lineStationdata.parallelStream().filter(linstationmap -> stationCode.equals(linstationmap.get("stationCode").toString())).collect(Collectors.toList());
                        if (stationMapList != null && stationMapList.size() > 0)
                        {
                            entity.setLineId(DataSwitch.convertObjectToLong(stationMapList.get(0).get("lineId")));
                            entity.setStationId(DataSwitch.convertObjectToLong(stationMapList.get(0).get("stationId")));
                            if(stationMapList.size() > 1 && lineCode.length()>2)
                            {
                                for (int num = 0; num < stationMapList.size(); num++)
                                {
                                    String lineCodestr=DataSwitch.convertObjectToString(stationMapList.get(num).get("lineCode"));
                                    if(lineCodestr.equals(stationCode.substring(0,2)))
                                    {
                                        entity.setLineId(DataSwitch.convertObjectToLong(stationMapList.get(num).get("lineId")));
                                        entity.setStationId(DataSwitch.convertObjectToLong(stationMapList.get(num).get("stationId")));
                                    }

                                }
                            }
                        }
                        else
                        {
                            messb.append("站点编码数据不存在;");
                        }

                    }
                    List<Map<String, Object>> partMapList = partdata.stream().filter(linstationmap -> categoryName.equals(linstationmap.get("categoryName").toString())&&linstationmap.containsKey("partName")&& partName.equals(linstationmap.get("partName").toString())).collect(Collectors.toList());
                    if (partMapList != null && partMapList.size() > 0)
                    {
                        entity.setSparePartId(DataSwitch.convertObjectToLong(partMapList.get(0).get("sparePartId")));
                    }
                    else
                    {
                        messb.append("备件类型和备件名称不对应;");
                    }

                    if (allEquipmentdata != null && allEquipmentdata.size() > 0)
                    {

                        List<Map<String, Object>> list3 = allEquipmentdata.stream().filter(x -> x.get("equipmentNo").toString().equals(equipmentNo)).collect(Collectors.toList());
                        if (list3 != null && list3.size() > 0)
                        {
                            messb.append("设备编号数据已存在;");
                        }
                    }


                    if (StringUtils.isNotEmpty(messb.toString()))
                    {
                        Map<String, Object> mesmap = new HashMap<>();
                        mesmap.put("num", (i + 3));
                        mesmap.put("message", messb.toString());
                        messListMap.add(mesmap);
                    }

                    entityList.add(entity);
                }

                if (messListMap != null && messListMap.size() > 0)
                {

                    ResultVO resultVO = new ResultVO();
                    resultVO.setData(messListMap);
                    resultVO.setStatusCode(-1);
                    resultVO.setResult("fail");
                    resultVO.setMessage("导入失败");
                    return resultVO;

                }

                if (entityList == null || entityList.size() < 1)
                {
                    return ResultVOUtil.error(-1, "导入数据不能为空");
                }

                for (OperationsEquipmentEntity entity : entityList)
                {
                    operationsEquipmentDao.addEquipment(entity);
                }

                return ResultVOUtil.success(entityList.size());

        }
            catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getAllEquipmentListMap(OperationsEquipmentFindEntity findEntity)
    {
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        return operationsEquipmentDao.getPagerInfo(findEntity);
    }
}
