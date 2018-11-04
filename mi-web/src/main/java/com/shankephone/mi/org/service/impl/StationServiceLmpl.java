package com.shankephone.mi.org.service.impl;

import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.model.OrgStationEntity;
import com.shankephone.mi.org.dao.LineDao;
import com.shankephone.mi.org.dao.StationDao;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.org.service.StationService;
import com.shankephone.mi.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-28 9:31
 */
@Service
@Slf4j
public class StationServiceLmpl implements StationService
{
    @Autowired
    private StationDao stationDao;

    @Autowired
    private LineDao lineDao;

    @Autowired
    private FieldContentExistsDao fieldContentExistsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO batchInsert(List<OrgStationEntity> entityList)
    {
        try
        {
            for (OrgStationEntity entity : entityList)
            {

                List<OrgStationEntity> list2 = entityList.stream().filter(entity1 -> entity1.getStationCode().equals(entity.getStationCode())).collect(Collectors.toList());
                if (list2 != null && list2.size() > 1)
                {
                    return ResultVOUtil.error(-1, "站点名称重复,请重新填写！");
                }
                List<OrgStationEntity> list1 = entityList.stream().filter(entity1 -> entity1.getStationCode().equals(entity.getStationCode())).collect(Collectors.toList());
                if (list1 != null && list1.size() > 1)
                {
                    return ResultVOUtil.error(-1, "站点编码重复,请重新填写！");
                }

                FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
                //filedentity.setUserKey(entity.getUserKey());
                filedentity.setTablename("org_station");
                filedentity.setCondition(" and  lineId  = " + entity.getLineId());
                filedentity.setFieldName("stationName");
                filedentity.setFieldValue(entity.getStationName());
                if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
                {
                    return ResultVOUtil.error(-1, "站点名称已存在,请重新填写！");
                }

                filedentity.setFieldName("stationCode");
                filedentity.setFieldValue(entity.getStationCode());
                if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
                {
                    return ResultVOUtil.error(-1, "站点编码已存在,请重新填写！");
                }

                entity.setPinyin(PinYinUtils.getCn2FirstSpell(entity.getStationName()));
                stationDao.insertOne(entity);
            }
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO updateOne(OrgStationEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity = new FieldContentExistsFindEntity();
            //filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("org_station");
            filedentity.setCondition(" and lineId= " + entity.getLineId());
            filedentity.setFieldName("stationName");
            filedentity.setFieldValue(entity.getStationName());
            filedentity.setIdName("stationId");
            filedentity.setIdValue(entity.getStationId());

            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "站点名称已存在,请重新填写！");
            }

            filedentity.setFieldName("stationCode");
            filedentity.setFieldValue(entity.getStationCode());
            if (fieldContentExistsDao.selectFieldExists(filedentity) > 0)
            {
                return ResultVOUtil.error(-1, "站点编码已存在,请重新填写！");
            }


            entity.setPinyin(PinYinUtils.getCn2FirstSpell(entity.getStationName()));
            stationDao.updateOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager<Map<String, Object>> getStationInfo(StationFindEntity findEntity)
    {
        try
        {
            Integer total = stationDao.getStationInfoTotal(findEntity);
            List<Map<String, Object>> sysUserEntities = stationDao.getStationInfo(findEntity);
            return new Pager<>(total, sysUserEntities);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importStationList(StationFindEntity findEntity, List<String[]> list)
    {
        try
        {
        List<String[]> stationlist = list;

        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        findEntity.setStatus("all");
        List<Map<String, Object>> stationlistMap = stationDao.getStationInfo(findEntity);

        List<Map<String, Object>> messListMap = new ArrayList<>();
        List<OrgStationEntity> entityList = new ArrayList<OrgStationEntity>();

        for (int i = 0; i < stationlist.size(); i++)
        {
            //站点编号	站点名称	负责人	电话	是否起始站	是否终点站
            OrgStationEntity entity = new OrgStationEntity();
            String stationCode = stationlist.get(i)[0].trim();
            String stationName = stationlist.get(i)[1].trim();
            String headPerson = stationlist.get(i)[2].trim();
            String phone = stationlist.get(i)[3].trim();
            String wasBegin = stationlist.get(i)[4].trim();
            String wasEnd = stationlist.get(i)[5].trim();
            String str = stationCode + stationName + headPerson + phone + wasBegin + wasEnd;
            if ("".equals(str))
            {
                break;
            }

            entity.setLineId(findEntity.getLineId());
            entity.setStationCode(stationCode);
            entity.setStationName(stationName);
            entity.setHeadPerson(headPerson);
            entity.setPhone(phone);
            entity.setWasBegin("是".equals(wasBegin));
            entity.setWasEnd("是".equals(wasEnd));
            entity.setOperationSubjectId(findEntity.getOperationSubjectId());
            entity.setStatus(StatusEnum.START_USING.getValue());

            entityList.add(entity);

            StringBuilder messb = new StringBuilder();
            if (ObjectUtils.isEmpty(stationCode))
            {
                messb.append("站点编号不能为空;");
            }
            else if (stationCode.length() > 8)
            {
                messb.append("站点编号长度不能超过8位;");
            }

            if (ObjectUtils.isEmpty(stationName))
            {
                messb.append("站点名称不能为空;");
            }
            //            if(StringUtils.isNotEmpty(phone))
            //            {
            //                if (!StringUtils.isphone(phone))
            //                {
            //                    messb.append("电话格式不正确;");
            //                }
            //            }
            if (stationName.length() > 16)
            {
                messb.append("站点名称长度不能超过16个字;");
            }
            if (headPerson.length() > 16)
            {
                messb.append("负责人长度不能超过16个字;");
            }
            if (phone.length() > 30)
            {
                messb.append("电话长度不能超过30个字符;");
            }

            List<String[]> list2 = list.stream().filter(x -> x[0].trim().equals(stationCode)).collect(Collectors.toList());
            if (list2 != null && list2.size() > 1)
            {
                messb.append("execl中站点编号不能重复;");
            }

            List<String[]> list1 = list.stream().filter(x -> x[1].trim().equals(stationName)).collect(Collectors.toList());
            if (list1 != null && list1.size() > 1)
            {
                messb.append("execl中站点名称不能重复;");
            }
            if (stationlistMap != null && stationlistMap.size() > 0)
            {

                List<Map<String, Object>> list3 = stationlistMap.stream().filter(x -> x.containsKey("stationCode") && x.get("stationCode").toString().equals(stationCode)).collect(Collectors.toList());
                if (list3 != null && list3.size() > 0)
                {
                    messb.append("站点编号数据已存在;");
                }


                List<Map<String, Object>> list4 = stationlistMap.stream().filter(x -> x.containsKey("stationName") && x.get("stationName").toString().equals(stationName)).collect(Collectors.toList());
                if (list4 != null && list4.size() > 0)
                {
                    messb.append("站点名称数据已存在;");
                }
            }

            if (StringUtils.isNotEmpty(messb.toString()))
            {
                Map<String, Object> mesmap = new HashMap<>();
                mesmap.put("num", (i + 3));
                mesmap.put("message", messb.toString());
                messListMap.add(mesmap);

            }
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

        for (OrgStationEntity entity : entityList)
        {
            entity.setPinyin(PinYinUtils.getCn2FirstSpell(entity.getStationName()));
            stationDao.insertOne(entity);
        }

        return ResultVOUtil.success(entityList.size());
    }
        catch (Exception ex)
    {
        throw ex;

    }
    }

    @Override
    public List<Map<String, Object>> getAllStationListMap(StationFindEntity findEntity)
    {
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        return stationDao.getStationInfo(findEntity);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importLineStationList(StationFindEntity findEntity, List<String[]> list)
    {
        try
        {
            List<String[]> stationlist = list;
            List<Map<String, Object>> messListMap = new ArrayList<>();
            List<OrgStationEntity> entityList = new ArrayList<OrgStationEntity>();

            List<Map<String, Object>> lineStationListMap = stationDao.getLineStationInfo(findEntity);
            boolean flag = false;
            for (int i = 0; i < stationlist.size(); i++)
            {
                // 线路编号 线路名称 站点编号	站点名称	负责人	电话	是否起始站	是否终点站
                String lineCode = stationlist.get(i)[0].trim();
                String lineName = stationlist.get(i)[1].trim();
                String stationCode = stationlist.get(i)[2].trim();
                String stationName = stationlist.get(i)[3].trim();
                String headPerson = stationlist.get(i)[4].trim();
                String phone = stationlist.get(i)[5].trim();
                String wasBegin = stationlist.get(i)[6].trim();
                String wasEnd = stationlist.get(i)[7].trim();
                String str = lineCode + lineName + stationCode + stationName + headPerson + phone + wasBegin + wasEnd;
                if ("".equals(str))
                {
                    break;
                }
                flag = true;
                StringBuilder messb = new StringBuilder();


                if (ObjectUtils.isEmpty(lineCode))
                {
                    messb.append("线路编号不能为空;");
                }
                else if (lineCode.length() > 8)
                {
                    messb.append("线路编号长度不能超过8位;");
                }

                if (ObjectUtils.isEmpty(lineName))
                {
                    messb.append("线路名称不能为空;");
                }
                else if (lineName.length() > 16)
                {
                    messb.append("线路名称长度不能超过16个字;");
                }

                if (ObjectUtils.isEmpty(stationCode))
                {
                    messb.append("站点编号不能为空;");
                }
                else if (stationCode.length() > 8)
                {
                    messb.append("站点编号长度不能超过8位;");
                }

                if (ObjectUtils.isEmpty(stationName))
                {
                    messb.append("站点名称不能为空;");
                }
                else if (stationName.length() > 16)
                {
                    messb.append("站点名称长度不能超过16个字;");
                }

                if (headPerson.length() > 16)
                {
                    messb.append("负责人长度不能超过16个字;");
                }
                if (phone.length() > 30)
                {
                    messb.append("电话长度不能超过30个字符;");
                }


                List<String[]> list5 = list.stream().filter(x -> (x[0].trim().equals(lineCode) && !x[1].trim().equals(lineName)) || (!x[0].equals(lineCode) && x[1].equals(lineName))).collect(Collectors.toList());
                if (list5 != null && list5.size() > 0)
                {
                    messb.append("execl中线路编号,线路名称对应关系有错误;");
                }

                if (lineStationListMap != null && lineStationListMap.size() > 0)
                {
                    List<Map<String, Object>> list6 = lineStationListMap.stream().filter(map -> (map.containsKey("lineCode") &&map.containsKey("lineName") &&map.get("lineCode").toString().equals(lineCode) && !map.get("lineName").toString().equals(lineName)) || (map.containsKey("lineCode") &&map.containsKey("lineName") &&!map.get("lineCode").toString().equals(lineCode) && map.get("lineName").toString().equals(lineName))).collect(Collectors.toList());
                    if (list6 != null && list6.size() > 0)
                    {
                        messb.append("线路编号,线路名称对应关系有错误;");
                    }
                }

                List<String[]> list2 = list.stream().filter(x -> x[0].trim().equals(lineCode) && x[1].trim().equals(lineName) && x[2].trim().equals(stationCode)).collect(Collectors.toList());
                if (list2 != null && list2.size() > 1)
                {
                    messb.append("execl中线路编号,线路名称和站点编号不能重复;");
                }

                List<String[]> list1 = list.stream().filter(x -> x[0].trim().equals(lineCode) && x[1].trim().equals(lineName) && x[3].trim().equals(stationName)).collect(Collectors.toList());
                if (list1 != null && list1.size() > 1)
                {
                    messb.append("execl中线路编号,线路名称和站点名称不能重复;");
                }


                if (StringUtils.isNotEmpty(messb.toString()))
                {
                    Map<String, Object> mesmap = new HashMap<>();
                    mesmap.put("num", (i + 3));
                    mesmap.put("message", messb.toString());
                    messListMap.add(mesmap);

                }
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
            if (!flag)
            {
                return ResultVOUtil.error(-1, "导入数据不能为空");
            }

            for (int i = 0; i < stationlist.size(); i++)
            {
                //线路编号 线路名称 站点编号	站点名称	负责人	电话	是否起始站	是否终点站
                OrgStationEntity entity = new OrgStationEntity();
                String lineCode = stationlist.get(i)[0].trim();
                String lineName = stationlist.get(i)[1].trim();
                String stationCode = stationlist.get(i)[2].trim();
                String stationName = stationlist.get(i)[3].trim();
                String headPerson = stationlist.get(i)[4].trim();
                String phone = stationlist.get(i)[5].trim();
                String wasBegin = stationlist.get(i)[6].trim();
                String wasEnd = stationlist.get(i)[7].trim();
                String str = lineCode + lineName + stationCode + stationName + headPerson + phone + wasBegin + wasEnd;
                if ("".equals(str))
                {
                    break;
                }



                    List<Map<String, Object>> list1 = lineStationListMap.stream().filter(x -> x.containsKey("lineCode") && x.get("lineCode").toString().equals(lineCode) && x.containsKey("lineName") && x.get("lineName").toString().equals(lineName)).collect(Collectors.toList());
                    if (list1 != null && list1.size() > 0)
                    {
                        entity.setLineId(DataSwitch.convertObjectToLong(list1.get(0).get("lineId")));
                    }
                    else
                    {
                        OrgLineEntity lineentity = new OrgLineEntity();
                        lineentity.setUserKey(findEntity.getUserKey());
                        lineentity.setLineCode(lineCode);
                        lineentity.setLineName(lineName);
                        lineentity.setHeadPerson("");
                        lineentity.setPhone("");
                        lineentity.setStatus(StatusEnum.START_USING.getValue());
                        lineentity.setRemark("");
                        lineDao.insertOne(lineentity);
                        entity.setLineId(lineentity.getLineId());
                        Map<String, Object> lineMap = new HashMap<>();
                        lineMap.put("lineId", lineentity.getLineId());
                        lineMap.put("lineCode", lineCode);
                        lineMap.put("lineName", lineName);
                        lineStationListMap.add(lineMap);
                    }

                if (lineStationListMap != null && lineStationListMap.size() > 0)
                {
                    List<Map<String, Object>> list4 = lineStationListMap.stream().filter(x -> x.containsKey("lineCode") && x.get("lineCode").toString().equals(lineCode) && x.containsKey("lineName") && x.get("lineName").toString().equals(lineName) && x.containsKey("stationName") && x.get("stationName").toString().equals(stationName)).collect(Collectors.toList());
                    if (list4 != null && list4.size() > 0)
                    {
                        entity.setStationId(DataSwitch.convertObjectToLong(list4.get(0).get("stationId")));
                    }
                    else
                    {
                        List<Map<String, Object>> list3 = lineStationListMap.stream().filter(x -> x.containsKey("lineCode") && x.get("lineCode").toString().equals(lineCode) && x.containsKey("lineName") && x.get("lineName").toString().equals(lineName) && x.containsKey("stationCode") && x.get("stationCode").toString().equals(stationCode)).collect(Collectors.toList());
                        if (list3 != null && list3.size() > 0)
                        {
                            entity.setStationId(DataSwitch.convertObjectToLong(list3.get(0).get("stationId")));
                        }
                    }

                }
               // entity.setLineId(findEntity.getLineId());
                entity.setStationCode(stationCode);
                entity.setStationName(stationName);
                entity.setHeadPerson(headPerson);
                entity.setPhone(phone);
                entity.setWasBegin("是".equals(wasBegin));
                entity.setWasEnd("是".equals(wasEnd));
                entity.setOperationSubjectId(findEntity.getOperationSubjectId());
                entity.setStatus(StatusEnum.START_USING.getValue());
                entityList.add(entity);
            }
            for (OrgStationEntity entity : entityList)
            {
                entity.setPinyin(PinYinUtils.getCn2FirstSpell(entity.getStationName()));
                if(StringUtils.isNotEmpty(entity.getStationId()))
                {
                    stationDao.updateOne(entity);
                }
                else
                {
                    stationDao.insertOne(entity);
                }
            }

            ResultVO resultVO = new ResultVO();
            resultVO.setData("xianluchezhandaoru");
            resultVO.setStatusCode(200);
            resultVO.setResult("success");
            resultVO.setMessage("操作成功");
            return  resultVO;

            //return ResultVOUtil.success(entityList.size());
        }
        catch (Exception ex)
        {
            throw ex;

        }

    }
}
