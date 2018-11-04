package com.shankephone.mi.spacepart.service.impl;

import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.PartSparePartTypeEntity;
import com.shankephone.mi.spacepart.dao.SparePartTypeDao;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeBusiEntity;
import com.shankephone.mi.spacepart.formbean.PartSparePartTypeFindEntity;
import com.shankephone.mi.spacepart.service.SparePartTypeService;
import com.shankephone.mi.util.DataSwitch;
import com.shankephone.mi.util.PinYinUtils;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-28 20:48
 */
@Service
public class SparePartTypeServiceImpl implements SparePartTypeService
{
    @Autowired
    private SparePartTypeDao sparePartTypeDao;


    @Autowired
    private FieldContentExistsDao fieldContentExistsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insertOne(PartSparePartTypeBusiEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("part_spare_part_type");
            filedentity.setFieldName("categoryName");
            filedentity.setFieldValue(entity.getCategoryName());
            //filedentity.setOperationSubjectId(entity.getOperationSubjectId());
            filedentity.setCondition(" and parentPartId= "+entity.getParentPartId());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
               return ResultVOUtil.error(-1,"分类名称已存在,请重新填写！");
            }

            entity.setPartTypePinYin(PinYinUtils.getCn2FirstSpell(entity.getCategoryName()));
            sparePartTypeDao.insertOne(entity);
            if (entity.getFlag().equals("addFirst"))
            {
                entity.setSparePartTypeIds(entity.getSparePartTypeId().toString()+"@");
            }
            else if (entity.getFlag().equals("add"))//同级
            {
                entity.setSparePartTypeIds(entity.getSparePartTypeIds().substring(0, entity.getSparePartTypeIds().lastIndexOf("@")) +"@" + +entity.getSparePartTypeId()+ "@");
            }
            else if (entity.getFlag().equals("addChild"))//下级
            {
                entity.setSparePartTypeIds(entity.getSparePartTypeIds() + entity.getSparePartTypeId()+ "@" );
            }
            sparePartTypeDao.updateOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO updateOne(PartSparePartTypeEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("part_spare_part_type");
            filedentity.setFieldName("categoryName");
            filedentity.setFieldValue(entity.getCategoryName());

            filedentity.setIdName("sparePartTypeId");
            filedentity.setIdValue(entity.getSparePartTypeId());
            //filedentity.setOperationSubjectId(entity.getOperationSubjectId());
            filedentity.setCondition(" and parentPartId= "+entity.getParentPartId());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"分类名称已存在,请重新填写！");
            }

            entity.setPartTypePinYin(PinYinUtils.getCn2FirstSpell(entity.getCategoryName()));
            sparePartTypeDao.updateOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getSparePartTypeInfo(PartSparePartTypeFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> entitys = sparePartTypeDao.getSparePartTypeInfo(findEntity);
            entitys = DataSwitch.convertListToTree(entitys, -1, "sparePartTypeId", "parentPartId", "children");
            return entitys;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    public List<Map<String, Object>> getSparePartTypeKongGeList(PartSparePartTypeFindEntity findEntity)
    {
        try
        {
            List<Map<String, Object>> entitys = sparePartTypeDao.getSparePartTypeKongGeList(findEntity);

            entitys.stream().forEach(map -> {
                String sparePartTypeIds = DataSwitch.convertObjectToString(map.get("sparePartTypeIds"));
               int strlength=sparePartTypeIds.split("@").length;

                String categoryName = DataSwitch.convertObjectToString(map.get("categoryName"));

                for(int i=0;i<strlength-2;i++)
                {
                    categoryName="---"+categoryName;
                }
                map.put("categoryNameKongGe", categoryName);
            });

            return entitys;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }
}
