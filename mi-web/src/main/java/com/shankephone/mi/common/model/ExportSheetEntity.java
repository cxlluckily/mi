package com.shankephone.mi.common.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2017-11-04 21:37
 */
@Getter
@Setter
public class ExportSheetEntity
{
    private String sheetName;
    private String blankValue;
    private Boolean hasTotalRow;
    private String totalKey;
    private String totalNameKey;
    private Integer totalColSpan;
    private List<List<ExportTitleEntity>> exportTitleRowsEntities;
}
