package com.shankephone.mi.sys.service.impl;

import com.shankephone.mi.model.SysFunctionTreeEntity;
import com.shankephone.mi.model.SysUserEntity;
import com.shankephone.mi.sys.dao.SysFunctionTreeDao;
import com.shankephone.mi.sys.formbean.FunctionTreeFindEntity;
import com.shankephone.mi.sys.service.SysFunctionTreeService;
import com.shankephone.mi.sys.vo.FunctionTreeVO;
import com.shankephone.mi.sys.vo.UserTreeFindEntity;
import com.shankephone.mi.util.DataSwitch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵亮
 * @date 2018-06-20 16:34
 */
@Service
@Slf4j
public class SysFunctionTreeServiceImpl implements SysFunctionTreeService
{
    @Resource
    private SysFunctionTreeDao sysFunctionTreeDao;

    private List<SysFunctionTreeEntity> sourceList;

    /**
     * 根据用户ID返回前台需要的数据结构
     *
     * @author：赵亮
     * @date：2018-06-21 11:08
     */
    @Override
    public List<FunctionTreeVO> getUserTree(UserTreeFindEntity findEntity)
    {
        sourceList = sysFunctionTreeDao.findByuserId(findEntity);
        List<SysFunctionTreeEntity> firstEntitys = sourceList.stream().filter(r -> r.getParentTreeId() == -1).collect(Collectors.toList());
        List<FunctionTreeVO> functionTreeVOS = new ArrayList<>();
        for (SysFunctionTreeEntity oneEntity : firstEntitys)
        {
            FunctionTreeVO functionTreeVO = initVOEntity(oneEntity);
            functionTreeVOS.add(functionTreeVO);
        }
        return functionTreeVOS;
    }

    /**
     * 递归获取叶子数据
     *
     * @author：赵亮
     * @date：2018-06-21 11:09
     */
    private List<FunctionTreeVO> getChildTree(SysFunctionTreeEntity entity)
    {
        List<FunctionTreeVO> functionTreeVOS = new ArrayList<>();
        List<SysFunctionTreeEntity> entitys = sourceList.stream().filter(r -> r.getParentTreeId().equals(entity.getFunctionTreeId())).collect(Collectors.toList());
        for (SysFunctionTreeEntity innerEntity : entitys)
        {
            FunctionTreeVO functionTreeVO = initVOEntity(innerEntity);
            functionTreeVOS.add(functionTreeVO);
        }
        return functionTreeVOS;
    }

    /**
     * 给VO实体赋值
     *
     * @author：赵亮
     * @date：2018-06-21 11:08
     */
    private FunctionTreeVO initVOEntity(SysFunctionTreeEntity innerEntity)
    {
        FunctionTreeVO functionTreeVO = new FunctionTreeVO();
        functionTreeVO.setText(innerEntity.getTreeName());
        functionTreeVO.setId(innerEntity.getFunctionTreeId().toString());
        functionTreeVO.setExpanded(true);
        functionTreeVO.setId(innerEntity.getId());
        functionTreeVO.setIconCls("fa-user");
        functionTreeVO.setChildren(getChildTree(innerEntity));
        functionTreeVO.setXclass(innerEntity.getXclass());
        functionTreeVO.setIconCls(innerEntity.getIconCls());
        if (functionTreeVO.getChildren() != null && functionTreeVO.getChildren().size() > 0)
        {
            functionTreeVO.setLeaf(false);
        }
        else
        {
            functionTreeVO.setLeaf(true);
            functionTreeVO.setExpanded(false);
        }
        return functionTreeVO;
    }

	@Override
	public List<Map<String, Object>> getFunctionTree(FunctionTreeFindEntity entity) {
		List<Map<String,Object>> maps = sysFunctionTreeDao.findFunctionTree(entity);
		//key为所有父节点
		Map<Long, List<Long>> idMap = new HashMap<Long, List<Long>>();
		if(maps != null && maps.size() > 0) {
			for(Map<String,Object> map : maps) {
				Long id = (Long)map.get("dataNodeId");
				Long parentId = (Long)map.get("dataParentNodeId");
				List<Long> ids = idMap.get(parentId);
				if(ids == null) {
					ids = new ArrayList<Long>();
				}
				ids.add(id);
				idMap.put(parentId, ids);
			}
			for(Map<String,Object> map : maps) {
				Long id = (Long)map.get("dataNodeId");
				map.put("checked", false);
				if(idMap.containsKey(id)) {
					map.put("leaf", false);
					map.put("expanded", true);
				} else {
					map.put("leaf", true);
				}
			}
		}
		List<Map<String,Object>> tree = DataSwitch.convertListToTree(maps, "-1", "dataNodeId", "dataParentNodeId", "children");
		return tree;
	}
}
