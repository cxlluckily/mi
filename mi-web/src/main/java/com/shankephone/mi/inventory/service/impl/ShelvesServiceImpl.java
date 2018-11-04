package com.shankephone.mi.inventory.service.impl;

import com.shankephone.mi.common.enumeration.StatusEnum;
import com.shankephone.mi.common.model.Pager;
import com.shankephone.mi.inventory.dao.ShelvesDao;
import com.shankephone.mi.inventory.formbean.ShelvesFindEntity;
import com.shankephone.mi.inventory.service.ShelvesService;
import com.shankephone.mi.inventory.vo.ShelvesVO;
import com.shankephone.mi.model.StockGoodsShelvesEntity;
import com.shankephone.mi.util.BeanUtils;
import com.shankephone.mi.util.DataSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 货架 service
 *
 * @author 司徒彬
 * @date 2018 /7/5 17:11
 */
@Service
public class ShelvesServiceImpl implements ShelvesService {

    @Autowired
    private ShelvesDao shelvesDao;

    @Override
    public Pager getPagerList(ShelvesFindEntity findEntity) {
        try {
            List<Map<String, Object>> rows = shelvesDao.getPagerList(findEntity);
            int count = shelvesDao.getPagerCount(findEntity);
            Pager pager = new Pager(count,rows);
            return pager;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Map<String, Object> getShelvesInfo(ShelvesFindEntity findEntity) {
        try {
            Map<String, Object> shelvesInfo = shelvesDao.getShelvesInfo(findEntity.getGoodsShelvesId());
            return shelvesInfo;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addShelvesInfo(ShelvesVO shelvesVO) throws Exception {
        try {
            ShelvesFindEntity findEntity=new ShelvesFindEntity();

            findEntity.setWarehouseId(shelvesVO.getWarehouseId().toString());
            findEntity.setHouseNo("");
            findEntity.setShelfNumber("");
            findEntity.setStatus("all");
            findEntity.setContainerType("all");
            findEntity.setStart(0);
            findEntity.setLimit(Integer.MAX_VALUE);
            List<Map<String, Object>> huoguirows = shelvesDao.getPagerList(findEntity);

            Integer cellNumber = shelvesVO.getCellNumber();
            Integer layerNumber = shelvesVO.getLayerNumber();
            for (int i = 0; i < layerNumber; i++) {
                for (int j = 0; j < cellNumber; j++) {
                    String shelfNumber = shelvesVO.getCode() +"_"+ String.valueOf(i + 1) + "_" + (j + 1);
                    if(huoguirows!=null&&huoguirows.size()>0)
                    {
                        List<Map<String, Object>> list1 = huoguirows.stream().filter(x ->x.get("shelfNumber").toString().equals(shelfNumber)&&x.get("houseNo").toString().equals(shelvesVO.getHouseNo())).collect(Collectors.toList());
                        if(list1!=null&&list1.size()>0)
                        {
                           continue;
                        }
                    }

                    StockGoodsShelvesEntity shelvesEntity = BeanUtils.copyBean(shelvesVO, StockGoodsShelvesEntity.class);
                    shelvesEntity.setShelfNumber(shelfNumber);
                    shelvesEntity.setCreateUser(shelvesVO.getOperationUserName());
                    shelvesEntity.setModifyUser(shelvesVO.getOperationUserName());
                    shelvesEntity.setRemark(shelvesVO.getRemark());
                    shelvesDao.insertShelvesInfo(shelvesEntity);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateShelvesInfo(StockGoodsShelvesEntity shelvesEntity) {
        try {
            shelvesEntity.setModifyUser(shelvesEntity.getOperationUserName());
            shelvesDao.updateShelvesInfo(shelvesEntity);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteShelvesInfo(Long goodsShelvesId) {
        try {
            shelvesDao.deleteShelvesInfo(goodsShelvesId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getShelvesInfoBySearchContent(ShelvesFindEntity findEntity) {

        try {
            List<Map<String, Object>> shelvesInfo = shelvesDao.getShelvesInfoBySearchContent(findEntity);
            return shelvesInfo;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Map<String, Object>> getShelvesHouseList(ShelvesFindEntity findEntity)
    {
        findEntity.setStatus(StatusEnum.START_USING.getValue());
        findEntity.setContainerType("all");
        List<Map<String, Object>> shelvesHouseList=shelvesDao.getShelvesList(findEntity);

        List<Map<String, Object>>  HouseList=shelvesHouseList.stream().map(map->{
            Map<String, Object> housemap = new HashMap<>();
            housemap.put("houseNo", DataSwitch.convertObjectToString(map.get("houseNo")));
            return housemap;
        }).distinct().collect(Collectors.toList());

        return HouseList;
    }

    @Override
    public  Map<String, Object> getShelvesMap(ShelvesFindEntity findEntity)
    {
        findEntity.setStatus(StatusEnum.START_USING.getValue());
        findEntity.setContainerType("all");

        List<Map<String, Object>> shelvesList=shelvesDao.getShelvesList(findEntity);

        Map<String, Object> shelvesmap = new HashMap<>();
        List<Map<String, Object>>  ShelfList=shelvesList.stream().filter(map->"Shelf".equals(DataSwitch.convertObjectToString(map.get("containerType"))) ).map(map->{
            Map<String, Object> shelfmap = new HashMap<>();
            shelfmap.put("goodsShelvesId", DataSwitch.convertObjectToString(map.get("goodsShelvesId")));
            shelfmap.put("shelfNumber", DataSwitch.convertObjectToString(map.get("shelfNumber")));
            return shelfmap;
        }).distinct().collect(Collectors.toList());

        shelvesmap.put("Shelf",ShelfList);

        List<Map<String, Object>>  containerList=shelvesList.stream().filter(map->"Container".equals(DataSwitch.convertObjectToString(map.get("containerType"))) ).map(map->{
            Map<String, Object> shelfmap = new HashMap<>();
            shelfmap.put("goodsShelvesId", DataSwitch.convertObjectToString(map.get("goodsShelvesId")));
            shelfmap.put("shelfNumber", DataSwitch.convertObjectToString(map.get("shelfNumber")));
            return shelfmap;
        }).distinct().collect(Collectors.toList());

        shelvesmap.put("Container",containerList);

        return shelvesmap;
    }
}
