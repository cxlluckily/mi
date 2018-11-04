package com.shankephone.mi.common.model;


/**
 * @文件名称: ExportTitleEntity
 * @包: com.gooal.model.businessentity.toolsentity
 * @创建人：ErebusST
 * @创建时间：2016/10/17 22:40
 * @功能简介：
 */
public class ExportTitleEntity
{
    private String valueMember;
    private String displayMember;
    private Integer width;
    private Integer rowSpan;
    private Integer colSpan;
    private Integer skip;
    private String defaultValue;
    private Integer colIndex;

    /**
     * Instantiates a new Export title toolsentity.
     *
     * @param valueMember   the value member
     * @param defaultValue the defaultValue
     * @param colIndex         the colIndex
     */
    public ExportTitleEntity(String valueMember, String defaultValue, Integer colIndex)
    {
        this.valueMember = valueMember;
        this.defaultValue = defaultValue;
        this.colIndex = colIndex;
    }



    public ExportTitleEntity(String valueMember, String displayMember, Integer width, Integer rowSpan, Integer colSpan, Integer skip, String defaultValue)
    {
        this.valueMember = valueMember;
        this.displayMember = displayMember;
        this.width = width;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.skip = skip;
        this.defaultValue = defaultValue;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public Integer getWidth()
    {

        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(Integer width)
    {
        this.width = width;
    }


    /**
     * Gets value member.
     *
     * @return the value member
     */
    public String getValueMember()
    {
        return valueMember;
    }

    /**
     * Sets value member.
     *
     * @param valueMember the value member
     */
    public void setValueMember(String valueMember)
    {
        this.valueMember = valueMember;
    }

    /**
     * Gets display member.
     *
     * @return the display member
     */
    public String getDisplayMember()
    {
        return displayMember;
    }

    /**
     * Sets display member.
     *
     * @param displayMember the display member
     */
    public void setDisplayMember(String displayMember)
    {
        this.displayMember = displayMember;
    }

    public Integer getRowSpan()
    {
        return rowSpan;
    }

    public void setRowSpan(Integer rowSpan)
    {
        this.rowSpan = rowSpan;
    }

    public Integer getColSpan()
    {
        return colSpan;
    }

    public void setColSpan(Integer colSpan)
    {
        this.colSpan = colSpan;
    }

    public Integer getSkip()
    {
        return skip;
    }

    public void setSkip(Integer skip)
    {
        this.skip = skip;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public Integer getColIndex()
    {
        return colIndex;
    }

    public void setColIndex(Integer colIndex)
    {
        this.colIndex = colIndex;
    }
}
