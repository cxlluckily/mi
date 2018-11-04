package com.shankephone.mi.sys.service.impl;

import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.model.SysDataDictionaryEntity;
import com.shankephone.mi.sys.dao.DataDictionaryDao;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.sys.service.DataDictionaryService;
import com.shankephone.mi.util.ResultVOUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{
	
	@Resource
	private DataDictionaryDao dataDictionaryDao;

	@Override
	public Pager<Map<String, Object>> getDictPagerInfo(
			DataDictFindEntity findEntity) {
		try {
			List<Map<String, Object>> rows = dataDictionaryDao.getDictPagerInfo(findEntity);
            int total = dataDictionaryDao.getPagerTotal(findEntity);
            Pager<Map<String, Object>> pager = new Pager<>(total, rows);
            return pager;
        } catch (Exception ex) {
            throw ex;
        }
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResultVO addDataDict(SysDataDictionaryEntity entity) {
		try
		{
			if(dataDictionaryDao.GetIsCodeExists(entity)>0)
			{
				return ResultVOUtil.error(-1,"编号重复,请重新填写！");
			}
			dataDictionaryDao.addDataDict(entity);
		   return ResultVOUtil.success();

		}
		catch (Exception ex)
		{
			return ResultVOUtil.error(ex, "添加数据字典出现异常");
		}
	}

	@Override
	public ResultVO updateDataDict(SysDataDictionaryEntity entity)
	{
		try
		{
			if(dataDictionaryDao.GetIsCodeExists(entity)>0)
			{
				return ResultVOUtil.error(-1,"编号重复,请重新填写！");
			}
			dataDictionaryDao.updateDataDict(entity);
			return ResultVOUtil.success();

		}
		catch (Exception ex)
		{
			return ResultVOUtil.error(ex, "更新数据字典出现异常");
		}

	}

	@Override
	public void deleteDataDict(DataDictFindEntity findEntity) {
		dataDictionaryDao.deleteDataDict(findEntity);
	}

	@Override
	public SysDataDictionaryEntity getDataDict(DataDictFindEntity findEntity) {
		return dataDictionaryDao.getDataDict(findEntity);
	}

	@Override
	public void deleteBatch(DataDictFindEntity findEntity) {
		dataDictionaryDao.deleteBatch(findEntity);
	}
	
}
