package com.shankephone.mi.org.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgWorkSectionStationEntity;
import com.shankephone.mi.org.dao.StationDao;
import com.shankephone.mi.org.dao.WorkSectionDao;
import com.shankephone.mi.org.formbean.StationFindEntity;
import com.shankephone.mi.org.formbean.WorkSectionFIndEntity;
import com.shankephone.mi.org.formbean.WorkSectionFormBean;
import com.shankephone.mi.org.service.WorkSectionService;
import com.shankephone.mi.org.vo.LineAndStationListVO;
import com.shankephone.mi.org.vo.LineVO;
import com.shankephone.mi.org.vo.StationVO;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-28 10:58
 */
@Service
public class WorkSectionServiceImpl implements WorkSectionService
{
    @Autowired
    private WorkSectionDao workSectionDao;
    @Autowired
    private StationDao stationDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertOne(WorkSectionFormBean entity)
    {
        try
        {
            if(workSectionDao.GetIsCodeExists(entity.getOrgWorkSectionEntity())>0)
            {
                return ResultVOUtil.error(-1,"工区编码重复,请重新填写！");
            }
            if(workSectionDao.GetIsNameExists(entity.getOrgWorkSectionEntity())>0)
            {
                return ResultVOUtil.error(-1,  "工区名称重复,请重新填写！");
            }

            //新增工区表
            workSectionDao.insertWorkSection(entity.getOrgWorkSectionEntity());
            //批量新增工区站点
            for (OrgWorkSectionStationEntity stationEntity : entity.getStationEntityList())
            {
                stationEntity.setWorkSectionId(entity.getOrgWorkSectionEntity().getWorkSectionId());
                workSectionDao.insertWorkSectionStation(stationEntity);
            }
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO updateOne(WorkSectionFormBean entity)
    {
        try
        {
            if(workSectionDao.GetIsCodeExists(entity.getOrgWorkSectionEntity())>0)
            {
                return ResultVOUtil.error(-1,"工区编号重复,请重新填写！");
            }
            if(workSectionDao.GetIsNameExists(entity.getOrgWorkSectionEntity())>0)
            {
                return ResultVOUtil.error(-1,  "工区名称重复,请重新填写！");
            }
            //更新工区表
            workSectionDao.updateOne(entity.getOrgWorkSectionEntity());
            //删除工区下所有车站
            workSectionDao.deleteWorkSectionStationByworkSectionId(entity.getOrgWorkSectionEntity().getWorkSectionId());
            //批量新增工区站点
            for (OrgWorkSectionStationEntity stationEntity : entity.getStationEntityList())
            {
                stationEntity.setWorkSectionId(entity.getOrgWorkSectionEntity().getWorkSectionId());
                workSectionDao.insertWorkSectionStation(stationEntity);
            }
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<LineVO> getLineAndStationInfo(WorkSectionFIndEntity findEntity)
    {
        try
        {
            List<LineVO> lineVOList = new ArrayList<>();
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            List<LineAndStationListVO> workStationList = workSectionDao.getLineAndStationInfo(findEntity);
            List<LineAndStationListVO> lineList = workStationList.parallelStream().filter(distinctByKey(LineAndStationListVO::getLineId)).collect(Collectors.toList());
            lineList.stream().forEach(r -> {
                LineVO lineVO = new LineVO();
                lineVO.setLineId(r.getLineId());
                lineVO.setLineName(r.getLineName());
                lineVO.setStationList(workStationList.stream().filter(station -> station.getLineId().equals(r.getLineId())).map(map -> new StationVO(map.getStationId(), map.getStationName(), map.getIsCheck())).collect(Collectors.toList()));
                lineVOList.add(lineVO);
            });
            return lineVOList;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }



    @Override
    public Pager<Map<String, Object>> getWorkSectionInfo(WorkSectionFIndEntity findEntity)
    {
        try
        {
            Integer total = workSectionDao.getWorkSectionInfoTotal(findEntity);
            List<Map<String, Object>> entitys = workSectionDao.getWorkSectionInfo(findEntity);
            return new Pager<>(total, entitys);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importWorkSectionLineStationList(StationFindEntity findEntity, List<String[]> list)
    {
        try
        {
            List<String[]> stationlist = list;
            List<Map<String, Object>> messListMap = new ArrayList<>();

            WorkSectionFIndEntity workFindEntity=new WorkSectionFIndEntity();
            workFindEntity.setUserKey(findEntity.getUserKey());
            workFindEntity.setStatus("all");
            workFindEntity.setLimit(Integer.MAX_VALUE);
            workFindEntity.setStart(0);
            List<Map<String, Object>> workStationList = workSectionDao.getWorkSectionInfo(workFindEntity);

            List<Map<String, Object>> lineStationListMap = stationDao.getLineStationInfo(findEntity);
            boolean flag = false;
            for (int i = 0; i < stationlist.size(); i++)
            {
                // 工区编码 工区名称 线路编号 线路名称 站点编号	站点名称
                String sectionCode = stationlist.get(i)[0].trim();
                String sectionName = stationlist.get(i)[1].trim();
                String lineCode = stationlist.get(i)[2].trim();
                String lineName = stationlist.get(i)[3].trim();
                String stationCode = stationlist.get(i)[4].trim();
                String stationName = stationlist.get(i)[5].trim();
                String str =sectionCode+sectionName+ lineCode + lineName + stationCode + stationName ;
                if ("".equals(str))
                {
                    break;
                }
                flag = true;
                StringBuilder messb = new StringBuilder();

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

            List<Map<String, Object>> listmap= workSectionDao.selectWorkSectionStationByworkSectionId(findEntity.getOperationSubjectId());

            for (int i = 0; i < stationlist.size(); i++)
            {

                OrgWorkSectionStationEntity entity =new OrgWorkSectionStationEntity();
                String sectionCode = stationlist.get(i)[0].trim();
                String sectionName = stationlist.get(i)[1].trim();
                String lineCode = stationlist.get(i)[2].trim();
                String lineName = stationlist.get(i)[3].trim();
                String stationCode = stationlist.get(i)[4].trim();
                String stationName = stationlist.get(i)[5].trim();
                if (workStationList != null && workStationList.size() > 0)
                {
                    List<Map<String, Object>> list2 = workStationList.stream().filter(x -> x.containsKey("sectionCode") && x.get("sectionCode").toString().equals(sectionCode)).collect(Collectors.toList());
                    if (list2 != null && list2.size() > 0)
                    {
                        entity.setWorkSectionId(DataSwitch.convertObjectToLong(list2.get(0).get("workSectionId")));
                    }
                    List<Map<String, Object>> list1 = workStationList.stream().filter(x -> x.containsKey("sectionCode") && x.get("sectionCode").toString().equals(sectionCode) && x.containsKey("sectionName") && x.get("sectionName").toString().equals(sectionName)).collect(Collectors.toList());
                    if (list1 != null && list1.size() > 0)
                    {
                        entity.setWorkSectionId(DataSwitch.convertObjectToLong(list1.get(0).get("workSectionId")));
                    }

                }

                if (lineStationListMap != null && lineStationListMap.size() > 0)
                {
                    List<Map<String, Object>> list5 = lineStationListMap.stream().filter(x -> x.containsKey("stationCode") && x.get("stationCode").toString().equals(stationCode)).collect(Collectors.toList());
                    if (list5 != null && list5.size() > 0)
                    {
                        entity.setStationId(DataSwitch.convertObjectToLong(list5.get(0).get("stationId")));
                    }
                    List<Map<String, Object>> list3 = lineStationListMap.stream().filter(x -> x.containsKey("lineCode") && x.get("lineCode").toString().equals(lineCode) && x.containsKey("stationCode") && x.get("stationCode").toString().equals(stationCode)).collect(Collectors.toList());
                    if (list3 != null && list3.size() > 0)
                    {
                        entity.setStationId(DataSwitch.convertObjectToLong(list3.get(0).get("stationId")));
                    }
                    List<Map<String, Object>> list4 = lineStationListMap.stream().filter(x -> x.containsKey("lineCode") && x.get("lineCode").toString().equals(lineCode) && x.containsKey("lineName") && x.get("lineName").toString().equals(lineName) && x.containsKey("stationName") && x.get("stationName").toString().equals(stationName)).collect(Collectors.toList());
                    if (list4 != null && list4.size() > 0)
                    {
                        entity.setStationId(DataSwitch.convertObjectToLong(list4.get(0).get("stationId")));
                    }
                }
                if(listmap!=null&&listmap.size()>0)
                {
                    List<Map<String, Object>> list7 = listmap.stream().filter(x -> x.containsKey("stationId") && x.get("stationId").toString().equals(entity.getStationId()) && x.containsKey("workSectionId") && x.get("workSectionId").toString().equals(entity.getWorkSectionId())).collect(Collectors.toList());
                    if(list7!=null&&list7.size()>0)
                    {
                        continue;
                    }
                    else
                    {
                        Map<String, Object> map=new HashMap<>();
                        map.put("stationId",entity.getStationId());
                        map.put("workSectionId",entity.getWorkSectionId());
                        listmap.add(map);
                    }

                }

                if(StringUtils.isNotEmpty(entity.getStationId())&&StringUtils.isNotEmpty(entity.getWorkSectionId()))
                {

                    workSectionDao.insertWorkSectionStation(entity);
                }
            }

            ResultVO resultVO = new ResultVO();
            resultVO.setData("xianluchezhandaoru");
            resultVO.setStatusCode(200);
            resultVO.setResult("success");
            resultVO.setMessage("操作成功");
            return  resultVO;

        }
        catch (Exception ex)
        {
            throw ex;

        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor)
    {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
