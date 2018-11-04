package com.shankephone.mi.common.model;

import lombok.Data;

import java.util.List;

/**
 * 分页实体
 *
 * @author 司徒彬
 * @date 2018-06-21 11:17
 */
@Data
public class Pager<T>
{
    private int totalCount;
    private List<T> rows;

    public Pager(int totalCount, List<T> rows)
    {
        this.totalCount = totalCount;
        this.rows = rows;
    }
}
