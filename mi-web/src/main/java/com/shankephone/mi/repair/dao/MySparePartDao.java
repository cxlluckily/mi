package com.shankephone.mi.repair.dao;

import com.shankephone.mi.repair.dao.provider.MySparePartProvider;
import com.shankephone.mi.repair.formbean.UpdateMySqparePartCountFormBean;
import com.shankephone.mi.repair.vo.MySparePartFindEntity;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-08-06 9:34
 */
@Repository
public interface MySparePartDao
{
    @SelectProvider(type = MySparePartProvider.class, method = "getMySparePartListTotal")
    Integer getMySparePartListTotal(MySparePartFindEntity findEntity);

    @SelectProvider(type = MySparePartProvider.class, method = "getMySparePartList")
    List<Map<String,Object>> getMySparePartList(MySparePartFindEntity findEntity);

    @UpdateProvider(type = MySparePartProvider.class, method = "updateMySqparePartCount")
    Integer updateMySqparePartCount(UpdateMySqparePartCountFormBean entity);
}
