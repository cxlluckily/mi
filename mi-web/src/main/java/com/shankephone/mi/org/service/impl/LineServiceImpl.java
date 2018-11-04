package com.shankephone.mi.org.service.impl;

import com.shankephone.mi.common.dao.FieldContentExistsDao;
import com.shankephone.mi.common.model.FieldContentExistsFindEntity;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.OrgLineEntity;
import com.shankephone.mi.org.dao.LineDao;
import com.shankephone.mi.org.formbean.LineFindEntity;
import com.shankephone.mi.org.service.LineService;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-25 15:40
 */
@Service
public class LineServiceImpl implements LineService
{

    @Autowired
    private LineDao lineDao;

    @Autowired
    private FieldContentExistsDao fieldContentExistsDao;

    @Override
    public ResultVO insertOne(OrgLineEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("org_line");
            filedentity.setFieldName("linename");
            filedentity.setFieldValue(entity.getLineName());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"线路名称已存在,请重新填写！");
            }
            filedentity.setTablename("org_line");
            filedentity.setFieldName("linecode");
            filedentity.setFieldValue(entity.getLineCode());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"线路编码已存在,请重新填写！");
            }
            lineDao.insertOne(entity);
            return ResultVOUtil.success();

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ResultVO updateOne(OrgLineEntity entity)
    {
        try
        {
            FieldContentExistsFindEntity filedentity=new FieldContentExistsFindEntity();
            filedentity.setUserKey(entity.getUserKey());
            filedentity.setTablename("org_line");
            filedentity.setIdName("lineId");
            filedentity.setIdValue(entity.getLineId());
            filedentity.setFieldName("linename");
            filedentity.setFieldValue(entity.getLineName());

            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"线路名称已存在,请重新填写！");
            }
            filedentity.setTablename("org_line");
            filedentity.setFieldName("linecode");
            filedentity.setFieldValue(entity.getLineCode());
            if( fieldContentExistsDao.selectFieldExists(filedentity)>0)
            {
                return ResultVOUtil.error(-1,"线路编码已存在,请重新填写！");
            }
            lineDao.updateOne(entity);
            return ResultVOUtil.success();
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public Pager<Map<String, Object>> getLineInfo(LineFindEntity findEntity)
    {
        try
        {
            Integer total = 0;
            List<Map<String, Object>> sysUserEntities = lineDao.getLineInfo(findEntity);
            return new Pager<>(total, sysUserEntities);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

}
