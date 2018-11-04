package com.shankephone.mi.stock.dao;

import com.shankephone.mi.model.StockInStockDetailEntity;
import com.shankephone.mi.model.StockInStockEntity;
import com.shankephone.mi.stock.dao.provider.InStockProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Repository;

/**
 * @author 赵亮
 * @date 2018-07-12 11:37
 */
@Repository
public interface InStockDao
{
    @InsertProvider(type = InStockProvider.class, method = "insertInStock")
    @Options(useGeneratedKeys = true, keyProperty = "inStockId")
    Integer insertInStock(StockInStockEntity entity);

    @InsertProvider(type = InStockProvider.class, method = "insertInStockDetail")
    @Options(useGeneratedKeys = true, keyProperty = "inStockDetailId")
    Integer insertInStockDetail(StockInStockDetailEntity entity);
}
