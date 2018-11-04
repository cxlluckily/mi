package com.shankephone.mi.common.model;

import java.util.List;

/**
 * ${description}
 *
 * @author 司徒彬
 * @date 2017-11-04 21:44
 */
public class ExportSettingEntity
{
    private String exportName;
    private List<ExportSheetEntity> exportSheetEntities;

    public String getExportName()
    {
        return exportName;
    }

    public void setExportName(String exportName)
    {
        this.exportName = exportName;
    }

    public List<ExportSheetEntity> getExportSheetEntities()
    {
        return exportSheetEntities;
    }

    public void setExportSheetEntities(List<ExportSheetEntity> exportSheetEntities)
    {
        this.exportSheetEntities = exportSheetEntities;
    }
}
