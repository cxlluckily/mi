/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.shankephone.mi.stock.formbean;

import com.shankephone.mi.common.model.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2018/7/23 15:44
 */
@Data
public class UpdateStockQrCodeBusiEntity extends BaseModel
{
    private List<UpdateStockQrCodeFormBean> formBeans;
}
