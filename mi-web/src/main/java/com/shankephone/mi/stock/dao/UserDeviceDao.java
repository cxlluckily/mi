package com.shankephone.mi.stock.dao;

import com.shankephone.mi.model.StockUserDeviceEntity;
import com.shankephone.mi.stock.dao.provider.UserDeviceProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @author 赵亮
 * @date 2018-08-08 18:19
 */
@Repository
public interface UserDeviceDao
{
    @InsertProvider(type = UserDeviceProvider.class, method = "insertOne")
    @Options(useGeneratedKeys = true, keyProperty = "userDeviceId")
    Integer insertOne(StockUserDeviceEntity entity);

    @UpdateProvider(type = UserDeviceProvider.class, method = "UpdateQrCode")
    Integer UpdateQrCode(StockUserDeviceEntity entity);
}
