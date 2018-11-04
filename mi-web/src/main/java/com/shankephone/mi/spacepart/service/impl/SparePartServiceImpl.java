package com.shankephone.mi.spacepart.service.impl;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartEntity;
import com.shankephone.mi.model.PartSparePartImageEntity;
import com.shankephone.mi.spacepart.dao.SparePartDao;
import com.shankephone.mi.spacepart.formbean.SparePartFindEntity;
import com.shankephone.mi.spacepart.service.SparePartService;
import com.shankephone.mi.spacepart.vo.SparePartVO;
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
 * @author 赵亮
 * @date 2018-06-29 13:41
 */
@Service
public class SparePartServiceImpl implements SparePartService
{
    @Autowired
    private SparePartDao sparePartDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertOne(PartSparePartEntity entity, List<PartSparePartImageEntity> partImageEntities)
    {
        try
        {
            entity.setPartPinYin(PinYinUtils.getCn2FirstSpell(entity.getPartName()));
            sparePartDao.insertOne(entity);
            //如果有上传图片则添加图片表数据
            if (partImageEntities.size() > 0)
            {
                for (PartSparePartImageEntity imageEntity : partImageEntities)
                {
                    imageEntity.setSparePartId(entity.getSparePartId());
                    sparePartDao.insertPartImage(imageEntity);
                }
            }
            return "success";
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateOne(PartSparePartEntity entity, List<PartSparePartImageEntity> partImageEntities)
    {
        try
        {
            entity.setPartPinYin(PinYinUtils.getCn2FirstSpell(entity.getPartName()));
            //更新备件信息
            sparePartDao.updateOne(entity);
            //重新添加备件图片
            if (partImageEntities.size() > 0)
            {
                //删除所有备件图片
                sparePartDao.deletePartImageBysparePartId(entity.getSparePartId());
                for (PartSparePartImageEntity imageEntity : partImageEntities)
                {
                    imageEntity.setSparePartId(entity.getSparePartId());
                    sparePartDao.insertPartImage(imageEntity);
                }
            }
            return "success";
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager<Map<String, Object>> getSparePartInfo(SparePartFindEntity findEntity)
    {
        try
        {
            try
            {
                Integer total = sparePartDao.getSparePartInfoCount(findEntity);
                List<Map<String, Object>> entitys = sparePartDao.getSparePartInfo(findEntity);
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

    @Override
    public SparePartVO getSparePartDetail(Long sparePartId)
    {
        try
        {
            SparePartVO sparePartVO = new SparePartVO();
            List<SparePartVO> partSparePartEntities = sparePartDao.getSparePartDetail(sparePartId);
            if (null != partSparePartEntities && partSparePartEntities.size() > 0)
            {
                sparePartVO = sparePartDao.getSparePartDetail(sparePartId).get(0);
            }
            List<Map<String, Object>> imagesList=   sparePartDao.getSparePartImagesList(sparePartId);
//            imagesList.stream().forEach(map -> {
//                String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
//                String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
//                if(StringUtils.isEmpty(imageUrl))
//                {
//                    sparePartPicUrl="noImage";
//                }
//                map.put("fullImageUrl", sparePartPicUrl);
//            });
            sparePartVO.setPartSparePartImageEntities(imagesList);

            return sparePartVO;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }



    @Override
    public Integer getSamePartCount(PartSparePartEntity entity)
    {
        try
        {
            return sparePartDao.getSamePartCount(entity);
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importSparePartList(SparePartFindEntity findEntity, List<String[]> list)
    {
        try{
        List<String[]> stationlist=list;

        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);

        List<Map<String, Object>> messListMap=new ArrayList<>();
        List<PartSparePartEntity> entityList=new ArrayList<PartSparePartEntity>();
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        findEntity.setStatus("all");
        List<Map<String, Object>> allSparePartListMap= sparePartDao.getSparePartInfo(findEntity);
        for (int i=0;i<stationlist.size();i++)
        {
            //备件名称	品牌 	规格型号 	物资编码	 尺寸	材质 	单位 	库存上限	 库存下限
            PartSparePartEntity entity=new PartSparePartEntity();
            String partName=stationlist.get(i)[0].trim();
            String brand=stationlist.get(i)[1].trim();
            String specificationModel=stationlist.get(i)[2].trim();
            String materiaCoding=stationlist.get(i)[3].trim();
            String size=stationlist.get(i)[4].trim();
            String material=stationlist.get(i)[5].trim();
            String units=stationlist.get(i)[6].trim();
            String upperLimit=stationlist.get(i)[7].trim();
            String lowerLimit=stationlist.get(i)[8].trim();
            String str=partName+brand+specificationModel+materiaCoding+size+material+units+upperLimit+lowerLimit;
            if("".equals(str))
            {
                break;
            }
            entity.setSparePartTypeId(findEntity.getSparePartTypeId());
            entity.setPartName(partName);
            entity.setBrand(brand);
            entity.setSpecificationModel(specificationModel);
            entity.setMateriaCoding(materiaCoding);
            entity.setSize(size);
            entity.setMaterial(material);
            entity.setUnits(units);
            entity.setUpperLimit(DataSwitch.convertObjectToInteger(upperLimit));
            entity.setLowerLimit(DataSwitch.convertObjectToInteger(lowerLimit));
            entity.setOperationSubjectId(findEntity.getOperationSubjectId());
            entity.setStatus(StatusEnum.START_USING.getValue());

            entityList.add(entity);

            StringBuilder messb = new StringBuilder();
            if(ObjectUtils.isEmpty(partName))
            {
                messb.append("备件名称不能为空;");
            }
//            if(ObjectUtils.isEmpty(brand))
//            {
//                messb.append("品牌不能为空;");
//            }
//            if(StringUtils.isEmpty(specificationModel))
//            {
//                messb.append("规格型号不能为空;");
//            }
            if(specificationModel.length()>60)
            {
                messb.append("规格型号不能超过60个字;");
            }
            if(partName.length()>60)
            {
                messb.append("备件名称不能超过60个字;");
            }

            List<String[]> list1 = list.stream().filter(x -> x[0].trim().equals(partName)).collect(Collectors.toList());
            if (list1 != null && list1.size() > 1)
            {
                messb.append("execl中备件名称不能重复;");
            }

            List<Map<String, Object>> partList=  allSparePartListMap.stream().filter(map->partName.equals(map.get("partName").toString())&&entity.getSparePartTypeId().toString().equals(map.get("sparePartTypeId").toString())).collect(Collectors.toList());
            if(partList!=null&&partList.size()>0)
            {
                messb.append("备件类型下备件名称已存在;");
            }


            if(brand.length()>8)
            {
                messb.append("品牌不能超过16个字;");
            }
            if(materiaCoding.length()>60)
            {
                messb.append("物资编码不能超过60个字符;");
            }

            if(material.length()>16)
            {
                messb.append("材质不能超过16个字符;");
            }
            if(size.length()>16)
            {
                messb.append("尺寸不能超过16个字;");
            }
            if(units.length()>16)
            {
                messb.append("单位不能超过16个字;");
            }
            if(upperLimit.length()>16)
            {
                messb.append("库存上限不能超过16个字符;");
            }
            if(lowerLimit.length()>16)
            {
                messb.append("库存下限不能超过16个字符;");
            }


            if(StringUtils.isNotEmpty(messb.toString()) )
            {
                Map<String,Object> mesmap=new HashMap<>();
                mesmap.put("num",(i+3));
                mesmap.put("message",messb.toString());
                messListMap.add(mesmap);

            }
        }

        if(messListMap!=null&&messListMap.size()>0)
        {

            ResultVO resultVO = new ResultVO();
            resultVO.setData(messListMap);
            resultVO.setStatusCode(-1);
            resultVO.setResult("fail");
            resultVO.setMessage("导入失败");
            return resultVO;

        }
        if(entityList==null||entityList.size()<1)
        {
            return ResultVOUtil.error(-1, "导入数据不能为空");
        }
        for (PartSparePartEntity entity : entityList)
        {
            entity.setPartPinYin(PinYinUtils.getCn2FirstSpell(entity.getPartName()));
            sparePartDao.insertOne(entity);
        }

        return  ResultVOUtil.success(entityList.size());
        }
        catch (Exception ex)
        {
            throw ex;

        }
    }

    @Override
    public List<Map<String, Object>> getAllSparePartListMap(SparePartFindEntity findEntity)
    {
        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        List<Map<String, Object>> partListAndParentType=sparePartDao.getPartListAndParentType(findEntity);
        List<Map<String, Object>> allSparePartListMap= sparePartDao.getSparePartInfo(findEntity);
        allSparePartListMap = allSparePartListMap.stream().map(map -> {
            List<Map<String, Object>> partListAndParentTypeList= partListAndParentType.stream().filter(catemap->   catemap.get("sparePartTypeId").toString().equals(map.get("sparePartTypeId").toString())).collect(Collectors.toList());
            if(partListAndParentTypeList!=null&&partListAndParentTypeList.size()>0)
            {
                map.put("categoryName", partListAndParentTypeList.get(0).get("categoryName").toString());
                map.put("parentCategoryName",  partListAndParentTypeList.get(0).get("parentCategoryName").toString());
            }
            else
            {
                map.put("categoryName", "");
                map.put("parentCategoryName", "");
            }
            return map;
        }).collect(Collectors.toList());

        return   allSparePartListMap.stream().filter(catemap->!"".equals(catemap.get("categoryName").toString())).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO importSparePartTypeList(SparePartFindEntity findEntity, List<String[]> list)
    {
        try{
        List<String[]> stationlist=list;

        findEntity.setStart(0);
        findEntity.setLimit(Integer.MAX_VALUE);
        findEntity.setStatus("all");
        List<Map<String, Object>> messListMap=new ArrayList<>();
        List<PartSparePartEntity> entityList=new ArrayList<PartSparePartEntity>();

        List<Map<String, Object>> partListAndParentType=sparePartDao.getPartListAndParentType(findEntity);
        List<Map<String, Object>> allSparePartListMap= sparePartDao.getSparePartInfo(findEntity);

        for (int i=0;i<stationlist.size();i++)
        {
            //备件名称 备件类型 父级备件类型	品牌 	规格型号 	物资编码	 尺寸	材质 	单位 	库存上限	 库存下限
            PartSparePartEntity entity=new PartSparePartEntity();
            String partName=stationlist.get(i)[0].trim();
            String categoryName=stationlist.get(i)[1].trim();
            String parentCategoryName=stationlist.get(i)[2].trim();
            String brand=stationlist.get(i)[3].trim();
            String specificationModel=stationlist.get(i)[4].trim();
            String materiaCoding=stationlist.get(i)[5].trim();
            String size=stationlist.get(i)[6].trim();
            String material=stationlist.get(i)[7].trim();
            String units=stationlist.get(i)[8].trim();
            String upperLimit=stationlist.get(i)[9].trim();
            String lowerLimit=stationlist.get(i)[10].trim();
            String str=partName+categoryName+parentCategoryName+brand+specificationModel+materiaCoding+size+material+units+upperLimit+lowerLimit;
            if("".equals(str))
            {
                break;
            }

            entity.setPartName(partName);
            entity.setBrand(brand);
            entity.setSpecificationModel(specificationModel);
            entity.setMateriaCoding(materiaCoding);
            entity.setSize(size);
            entity.setMaterial(material);
            entity.setUnits(units);
            entity.setUpperLimit(DataSwitch.convertObjectToInteger(upperLimit));
            entity.setLowerLimit(DataSwitch.convertObjectToInteger(lowerLimit));
            entity.setOperationSubjectId(findEntity.getOperationSubjectId());
            entity.setStatus(StatusEnum.START_USING.getValue());

            StringBuilder messb = new StringBuilder();
            if(ObjectUtils.isEmpty(partName))
            {
                messb.append("备件名称不能为空;");
            }
            if(ObjectUtils.isEmpty(categoryName))
            {
                messb.append("备件类型不能为空;");
            }

            List<String[]> list1 = list.stream().filter(x -> x[0].trim().equals(partName)&& x[1].trim().equals(categoryName)&& x[2].trim().equals(parentCategoryName)).collect(Collectors.toList());
            if (list1 != null && list1.size() > 1)
            {
                messb.append("execl中备件名称、备件类型和父级备件类型不能重复;");
            }

            List<Map<String, Object>> partListAndParentTypeList= partListAndParentType.stream().filter(map-> categoryName.equals(map.get("categoryName").toString())&& parentCategoryName.equals(map.get("parentCategoryName").toString())).collect(Collectors.toList());
            if(partListAndParentTypeList!=null&&partListAndParentTypeList.size()>0)
            {
                entity.setSparePartTypeId(DataSwitch.convertObjectToLong(partListAndParentTypeList.get(0).get("sparePartTypeId")));

                List<Map<String, Object>> partList=  allSparePartListMap.stream().filter(map->partName.equals(map.get("partName").toString())&&entity.getSparePartTypeId().toString().equals(map.get("sparePartTypeId").toString())).collect(Collectors.toList());
                if(partList!=null&&partList.size()>0)
                {
                    messb.append("备件类型下备件名称已存在;");
                }
            }
            else
            {
                messb.append("备件类型和父级备件类型不对应;");
            }

            entityList.add(entity);
//            if(ObjectUtils.isEmpty(brand))
//            {
//                messb.append("品牌不能为空;");
//            }
//            if(StringUtils.isEmpty(specificationModel))
//            {
//                messb.append("规格型号不能为空;");
//            }
            if(specificationModel.length()>60)
            {
                messb.append("规格型号不能超过60个字;");
            }
            if(partName.length()>60)
            {
                messb.append("备件名称不能超过60个字;");
            }
            if(brand.length()>8)
            {
                messb.append("品牌不能超过16个字;");
            }
            if(materiaCoding.length()>60)
            {
                messb.append("物资编码不能超过60个字符;");
            }

            if(material.length()>16)
            {
                messb.append("材质不能超过16个字符;");
            }
            if(size.length()>16)
            {
                messb.append("尺寸不能超过16个字;");
            }
            if(units.length()>16)
            {
                messb.append("单位不能超过16个字;");
            }
            if(upperLimit.length()>16)
            {
                messb.append("库存上限不能超过16个字符;");
            }
            if(lowerLimit.length()>16)
            {
                messb.append("库存下限不能超过16个字符;");
            }


            if(StringUtils.isNotEmpty(messb.toString()) )
            {
                Map<String,Object> mesmap=new HashMap<>();
                mesmap.put("num",(i+3));
                mesmap.put("message",messb.toString());
                messListMap.add(mesmap);

            }
        }

        if(messListMap!=null&&messListMap.size()>0)
        {

            ResultVO resultVO = new ResultVO();
            resultVO.setData(messListMap);
            resultVO.setStatusCode(-1);
            resultVO.setResult("fail");
            resultVO.setMessage("导入失败");
            return resultVO;

        }
        if(entityList==null||entityList.size()<1)
        {
            return ResultVOUtil.error(-1, "导入数据不能为空");
        }
        for (PartSparePartEntity entity : entityList)
        {
            entity.setPartPinYin(PinYinUtils.getCn2FirstSpell(entity.getPartName()));
            sparePartDao.insertOne(entity);
        }

        return  ResultVOUtil.success(entityList.size());
        }
        catch (Exception ex)
        {
            throw ex;

        }
    }

    @Override
    public List<Map<String, Object>> getSparePartImagesList(Long sparePartId)
    {

        List<Map<String, Object>> imageList=sparePartDao.getSparePartImagesList(sparePartId);
        imageList.stream().forEach(map -> {
            String imageUrl = DataSwitch.convertObjectToString(map.get("imageUrl"));
            String sparePartPicUrl = FdfsClient.getDownloadServer() + imageUrl;
            if(StringUtils.isEmpty(imageUrl))
            {
                sparePartPicUrl="";
            }
            map.put("imageUrl", sparePartPicUrl);
        });

        return imageList;
    }
}
