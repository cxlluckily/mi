package com.shankephone.mi.partbreakdowninfo.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartBreakdownInfoEntity;
import com.shankephone.mi.model.PartBreakdownRepairInfoEntity;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.partbreakdowninfo.dao.PartBreakdownInfoDao;
import com.shankephone.mi.partbreakdowninfo.dao.PartBreakdownRepairInfoDao;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoDDLFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownInfoFindEntity;
import com.shankephone.mi.partbreakdowninfo.formbean.PartBreakdownRepairInfoFindEntity;
import com.shankephone.mi.partbreakdowninfo.service.PartBreakdownInfoService;
import com.shankephone.mi.spacepart.dao.SparePartDao;
import com.shankephone.mi.spacepart.formbean.SparePartListForBreakdownFindEntity;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.ResultVOUtil;
import com.shankephone.mi.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-08-02 9:53
 */
@Service
public class PartBreakdownInfoServiceImpl implements PartBreakdownInfoService
{
    @Autowired
    private PartBreakdownInfoDao partBreakdownInfoDao;
    @Autowired
    private PartBreakdownRepairInfoDao partBreakdownRepairInfoDao;
    /**
     * 新增
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-02 9:57
     */
    @Override
    public Integer insertOne(PartBreakdownInfoEntity entity)
    {
        try
        {
            return partBreakdownInfoDao.insertOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 修改
     *
     * @param entity
     * @author：赵亮
     * @date：2018-08-02 10:03
     */
    @Override
    public Integer updateOne(PartBreakdownInfoEntity entity)
    {
        try
        {
            return partBreakdownInfoDao.updateOne(entity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获取列表数据
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-02 10:09
     */
    @Override
    public Pager<Map<String, Object>> getBreakdownInfoList(PartBreakdownInfoFindEntity findEntity)
    {
        try
        {
            try
            {
                List<Map<String, Object>> entitys = partBreakdownInfoDao.getBreakdownInfoList(findEntity);
                Integer total = partBreakdownInfoDao.getBreakdownInfoListCount(findEntity);
                Pager pager = new Pager<>(total, entitys);
                return pager;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Autowired
    private SparePartDao sparePartDao;

    /**
     * 根据查询条件返回配件名称列表
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-02 14:04
     */
    @Override
    public List<PartSparePartEntity> getSparePartDDLList(SparePartListForBreakdownFindEntity findEntity)
    {
        try
        {
            if(findEntity.getPartName()==null)
            {
                findEntity.setPartName("");
            }
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return sparePartDao.getSparePartListForBreakdown(findEntity);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 根据备件返回故障信息
     *
     * @param findEntity
     * @author：赵亮
     * @date：2018-08-17 15:21
     */
    @Override
    public List<PartBreakdownInfoEntity> getPartBreakdownInfoDDLList(PartBreakdownInfoDDLFindEntity findEntity)
    {
        try
        {
            findEntity.setStatus(StatusEnum.START_USING.getValue());
            return partBreakdownInfoDao.getPartBreakdownInfoDDLList(findEntity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO importPartBreakdownList(PartBreakdownInfoFindEntity findEntity, List<String[]> list)
    {
        try
        {
            List<String[]> breaklist = list;
            List<Map<String, Object>> messListMap = new ArrayList<>();

            findEntity.setStatus("all");
            findEntity.setLimit(Integer.MAX_VALUE);
            findEntity.setStart(0);

            List<Map<String,Object>>  SparePartList = partBreakdownInfoDao.getSparePartList(findEntity);

            List<PartBreakdownInfoEntity> breakdownList=new ArrayList<>();

            List<Map<String,Object>> repaireList=new ArrayList<>();
            for (int i = 0; i < breaklist.size(); i++)
            {
                // 故障简称 设备名称 部件名称
                StringBuilder messb = new StringBuilder();

                String parentSpartName = breaklist.get(i)[0].trim();
                String PartName = breaklist.get(i)[1].trim();
                String breakAbbreviated = breaklist.get(i)[2].trim();

                String breakDescription=breaklist.get(i)[3].trim();


                String str = breakAbbreviated + parentSpartName + PartName + breakDescription;
                if ("".equals(str))
                {
                    continue;
                }
                Map<String,Object> repairemap=new HashMap<>();
                repairemap.put("reason",breaklist.get(i)[4].trim());
                repairemap.put("repairDescription", breaklist.get(i)[5].trim());
                repaireList.add(repairemap);

                Long spartTypeId=0L;
                if(SparePartList!=null)
                {
                    if(PartName.equals(""))
                    {
                        List<Map<String, Object>> spartlist = SparePartList.stream().filter(map -> map.get("PartName").toString().equals(parentSpartName) && map.get("parentPartId").toString().equals("-1")).collect(Collectors.toList());

                        if (spartlist != null && spartlist.size() > 0)
                        {
                            spartTypeId = DataSwitch.convertObjectToLong(spartlist.get(0).get("sparePartId"));
                        }
                    }
                    else
                    {
                        List<Map<String, Object>> spartlist = SparePartList.stream().filter(map -> map.get("parentPartName").toString().equals(parentSpartName) && map.get("PartName").toString().equals(PartName)).collect(Collectors.toList());

                        if (spartlist != null && spartlist.size() > 0)
                        {
                            spartTypeId = DataSwitch.convertObjectToLong(spartlist.get(0).get("parentSparePartId"));
                            if (DataSwitch.convertObjectToLong(spartlist.get(0).get("sparePartId")) > 0)
                            {
                                spartTypeId = DataSwitch.convertObjectToLong(spartlist.get(0).get("sparePartId"));
                            }
                        }
                    }
                }
                if(spartTypeId<1)
                {
                    messb.append("设备名称和部件名称不对应！");
                }
                PartBreakdownInfoEntity breakdownentity=new PartBreakdownInfoEntity();
                breakdownentity.setUserKey(findEntity.getUserKey());
                breakdownentity.setBreakAbbreviated(breakAbbreviated);
                breakdownentity.setBreakDescription(breakDescription);
                breakdownentity.setSparePartId(spartTypeId);
                breakdownentity.setStatus(StatusEnum.START_USING.getValue());
                breakdownList.add(breakdownentity);
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
            if(breakdownList.size()<1)
            {
                return ResultVOUtil.error(-1, "导入数据不能为空");
            }
            List<Map<String, Object>> entitys = partBreakdownInfoDao.getBreakdownInfoList(findEntity);

            PartBreakdownRepairInfoFindEntity RepairInfofindEntity=new PartBreakdownRepairInfoFindEntity();
            RepairInfofindEntity.setStatus("all");
            RepairInfofindEntity.setStart(0);
            RepairInfofindEntity.setLimit(Integer.MAX_VALUE);
            RepairInfofindEntity.setUserKey(findEntity.getUserKey());
            List<Map<String, Object>> RepairInfoentitys = partBreakdownRepairInfoDao.getPartBreakdownRepairInfoList(RepairInfofindEntity);
            int num=0;
            for (PartBreakdownInfoEntity entity:breakdownList)
            {
                 List<Map<String, Object>> entitylist=entitys.stream().filter(map->map.get("sparePartId").toString().equals(entity.getSparePartId().toString())&&entity.getBreakAbbreviated().equals(DataSwitch.convertObjectToString(map.get("breakAbbreviated")))&&entity.getBreakDescription().equals(DataSwitch.convertObjectToString(map.get("breakDescription")))).collect(Collectors.toList());
                 Long breakdownInfoId=0L;
                 if(entitylist!=null&&entitylist.size()>0)
                 {
                     breakdownInfoId=DataSwitch.convertObjectToLong(entitylist.get(0).get("breakdownInfoId"));
                 }
                 else
                 {
                     partBreakdownInfoDao.insertOne(entity);
                     Map<String, Object> map = new HashMap<>();
                     map.put("sparePartId", DataSwitch.convertObjectToLong(map.get("sparePartId")));
                     map.put("breakAbbreviated", DataSwitch.convertObjectToString(map.get("breakAbbreviated")));
                     map.put("breakDescription", entity.getBreakDescription());
                     map.put("breakdownInfoId", entity.getBreakdownInfoId());
                     entitylist.add(map);
                     breakdownInfoId=entity.getBreakdownInfoId();
                 }

                String reason= DataSwitch.convertObjectToString(repaireList.get(num).get("reason"));
                String repairDescription= DataSwitch.convertObjectToString( repaireList.get(num).get("repairDescription"));
                num++;
                if("".equals(reason)&&"".equals(repairDescription))
                {
                    continue;
                }
                String breakdownInfoIdstr=DataSwitch.convertObjectToString(breakdownInfoId);
                List<Map<String, Object>> repaireentitylist=RepairInfoentitys.stream().filter(map->breakdownInfoIdstr.equals(map.get("breakdownInfoId").toString())&&reason.equals(map.get("reason").toString())&&repairDescription.equals(map.get("repairDescription").toString())).collect(Collectors.toList());
                if(repaireentitylist!=null&&repaireentitylist.size()>0)
                {
                    continue;
                }
                PartBreakdownRepairInfoEntity entityRepairInfoEntity = new PartBreakdownRepairInfoEntity();
                entityRepairInfoEntity.setUserKey(findEntity.getUserKey());
                entityRepairInfoEntity.setBreakdownInfoId(breakdownInfoId);
                entityRepairInfoEntity.setStatus(StatusEnum.START_USING.getValue());
                entityRepairInfoEntity.setReason(reason);
                entityRepairInfoEntity.setRepairDescription(repairDescription);
                partBreakdownRepairInfoDao.insertOne(entityRepairInfoEntity);
                Map<String, Object> repairemap = new HashMap<>();
                repairemap.put("repairDescription", entityRepairInfoEntity.getRepairDescription());
                repairemap.put("reason",entityRepairInfoEntity.getReason());
                repairemap.put("breakdownInfoId", entityRepairInfoEntity.getBreakdownInfoId());
                RepairInfoentitys.add(repairemap);
            }

            ResultVO resultVO = new ResultVO();
            resultVO.setData("");
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
}
