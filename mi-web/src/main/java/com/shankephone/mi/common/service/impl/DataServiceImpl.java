package com.shankephone.mi.common.service.impl;

import com.shankephone.mi.common.dao.DataDao;
import com.shankephone.mi.common.formbean.ValidateIsRepeatFindEntity;
import com.shankephone.mi.common.model.DataEntity;
import com.shankephone.mi.common.model.DataFindEntity;
import com.shankephone.mi.common.service.DataService;
import com.shankephone.mi.sys.formbean.DataDictFindEntity;
import com.shankephone.mi.util.DataSwitch;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
/**
 * 公共数据查询服务
 * @author fengql
 * @date 2018年6月25日 上午10:31:34
 */
@Service
public class DataServiceImpl implements DataService{
	
	@Resource
	private DataDao dataDao;

	@Override
	public List<DataEntity> loadData(DataFindEntity dataEntity) {
		return dataDao.loadData(dataEntity);
	}

	@Override
	public List<Map<String, Object>> loadTreeData(DataFindEntity dataEntity) {
		List<Map<String,Object>> list = dataDao.loadTreeData(dataEntity);
		List<Map<String, Object>> treeList = DataSwitch.convertListToTree(list,-1,"id","parentId","children");
		return treeList;
	}

	@Override
	public Object loadDict(DataDictFindEntity dataEntity) {
		return dataDao.loadDict(dataEntity);
	}

	@Override
	public List<Map<String, Object>> getToUpTree(DataFindEntity dataEntity) {
		List<Map<String,Object>> list = dataDao.loadDataList(dataEntity);
		List<Map<String,Object>> leafs = dataDao.findLeafs(dataEntity);
		Map<Long,Map<String,Object>> map = new HashMap<Long,Map<String,Object>>();
		Map<Long,Long> pIdMap = new HashMap<Long,Long>();
		for(Map<String,Object> m : list) {
			map.put((Long)m.get("id"), m);
			Long parentId = (Long)m.get("parentId");
			pIdMap.put(parentId, (Long)m.get("id"));
		}
		List<Map<String,Object>> treeMap = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> l : leafs) {
			Long parentId = (Long)l.get("parentId");
			getParent(map, treeMap, parentId);
			
		}
		if("tree".equals(dataEntity.getShowMode())) {
			List<Map<String, Object>> treeList = DataSwitch.convertListToTree(treeMap,-1,"id","parentId","children");
			return treeList;
		}
		if("list".equals(dataEntity.getShowMode())) {
			if(treeMap.size() > 1) {
				Collections.sort(treeMap, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						Map<String,Object> m1 = (Map<String,Object>)o1;
						Map<String,Object> m2 = (Map<String,Object>)o2;
						if((Long)m1.get("id") > (Long)m2.get("id")) {
							return 1;
						} 
						if((Long)m1.get("id") < (Long)m2.get("id")) {
							return -1;
						}
						return 0;
					}
				});
			}
			return treeMap; 
		}
		return null;
	}
	
	private List<Map<String, Object>> getParent(Map<Long, Map<String, Object>> map, List<Map<String, Object>> treeMap, Long parentId) {
		Map<String,Object> parent = map.get(parentId);
		if(parent != null) {
			if(!treeMap.contains(parent)) {
				treeMap.add(parent);
			}
			getParent(map, treeMap, (Long)parent.get("parentId"));
		}
		return treeMap;
	}
	
	@Override
	public List<Map<String, Object>> getData(DataFindEntity dataEntity) {
		List<Map<String,Object>> list = dataDao.loadDataList(dataEntity);
		List<Map<String,Object>> leafs = dataDao.findLeafs(dataEntity);
		Map<Long,Map<String,Object>> map = new HashMap<Long,Map<String,Object>>();
		Map<Long,Long> pIdMap = new HashMap<Long,Long>();
		for(Map<String,Object> m : list) {
			map.put((Long)m.get("id"), m);
			Long parentId = (Long)m.get("parentId");
			pIdMap.put(parentId, (Long)m.get("id"));
		}
		List<Map<String,Object>> treeMap = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> l : leafs) {
			Long parentId = (Long)l.get("parentId");
			treeMap.add(l);
			getParent(map, treeMap, parentId);
		}
		if("tree".equals(dataEntity.getShowMode())) {
			List<Map<String, Object>> treeList = DataSwitch.convertListToTree(treeMap,-1,"id","parentId","children");
			return treeList;
		} else {
			return leafs;
		}
	}

	@Override
	public List<Map<String, Object>> getWarehousesByUserId(DataFindEntity dataEntity) {
		List<Map<String,Object>> list = dataDao.loadDataList(dataEntity);
		List<Map<String,Object>> leafs = dataDao.loadTreeData(dataEntity);
		Map<Long,Map<String,Object>> map = new HashMap<Long,Map<String,Object>>();
		Map<Long,Long> pIdMap = new HashMap<Long,Long>();
		for(Map<String,Object> m : list) {
			map.put((Long)m.get("id"), m);
			Long parentId = (Long)m.get("parentId");
			pIdMap.put(parentId, (Long)m.get("id"));
		}
		List<Map<String,Object>> treeMap = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> l : leafs) {
			Long parentId = (Long)l.get("parentId");
			treeMap.add(l);
			getParent(map, treeMap, parentId);
		}
		if("tree".equals(dataEntity.getShowMode())) {
			List<Map<String, Object>> treeList = DataSwitch.convertListToTree(treeMap,-1,"id","parentId","children");
			return treeList;
		} else {
			return leafs;
		}
	}

	@Override
	public Integer getCountByFindEntity(ValidateIsRepeatFindEntity findEntity)
	{
		try
		{
		    return dataDao.getCountByFindEntity(findEntity);
		}
		catch(Exception ex)
		{
		    throw ex;
		}
	}


}
