package com.shankephone.mi.util;

import com.shankephone.mi.common.model.ExportSettingEntity;
import com.shankephone.mi.common.model.ExportSheetEntity;
import com.shankephone.mi.common.model.ExportTitleEntity;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.lang.Boolean;
import java.math.BigDecimal;
import java.util.*;

/**
 * The type Excel util.
 *
 * @author 司徒彬
 * @date 2016年10月12日 下午2 :55:04
 */
public class ExcelUtil
{

    private static final String TEMP_PATH =FilePath.getImportExcelPath();


    /**
     * Export excel string.
     *
     * @param exportType  the export type
     * @param contentList the content list 此处如果没有参数可传的话 不要传入null 空着就可以
     * @return the string
     * @throws Exception the exception
     */
    public static String exportExcel(String exportType, List<Map<String, Object>>... contentList) throws Exception
    {
        return exportExcel(exportType, true, true, contentList);
    }

    /**
     * Export excel string.
     *
     * @param exportType   the export type
     * @param isHasTitle   the is has title
     * @param isHasNo      the is has no
     * @param contentLists the content lists 此处如果没有参数可传的话 不要传入null 空着就可以
     * @return the string
     * @throws Exception the exception
     */
    public static String exportExcel(String exportType, boolean isHasTitle, boolean isHasNo, List<Map<String, Object>>... contentLists) throws Exception
    {
        try
        {
            String filePath = TEMP_PATH + UUID.randomUUID().toString() + "tmp.xls";
            //String filePath = TEMP_PATH + UUID.randomUUID().toString() + ".tmp.xls";
            File file = new File(filePath);
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            ExportSettingEntity settingEntity = XmlUtil.getExportSetting(exportType);
            return exportExcel(settingEntity, filePath, false, isHasTitle, 0, isHasNo, contentLists);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Export excel string.
     *
     * @param exportType   the export type
     * @param templatePath the template path
     * @param contentLists the content lists
     * @return the string
     * @throws Exception the exception
     */
    public static String exportExcel(String exportType, String templatePath, List<Map<String, Object>>... contentLists) throws Exception
    {
        try
        {

            String filePath = TEMP_PATH + UUID.randomUUID().toString() + ".tmp.xls";
            if (!FileUtil.isExist(templatePath))
            {
                throw new Exception("模板文件不存在");
            }
            FileUtil.copyFile(templatePath, filePath);
            ExportSettingEntity settingEntity = XmlUtil.getExportSetting(exportType);
            return exportExcel(settingEntity, filePath, true, false, 1, false, contentLists);
        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    /**
     * Export excel string.
     *
     * @param settingEntity the setting entity
     * @param contentLists  the content lists
     * @return the string
     * @throws Exception the exception
     */
    public static String exportExcel(ExportSettingEntity settingEntity, List<Map<String, Object>>... contentLists) throws Exception
    {
        String filePath = TEMP_PATH + UUID.randomUUID().toString() + ".tmp.xls";
        return exportExcel(settingEntity, filePath, false, true, 0, false, contentLists);
    }

    /**
     * Export excel string.
     *
     * @param settingEntity the setting entity 导出设置实体
     * @param filePath      the file path 结果文件的路径
     * @param useTemplate   the use template
     * @param isHasTitle    the is has title  是否显示表头
     * @param rowSkipNumber the row skip number 跳过的行 标识从第几行开始写入数据
     * @param isHasNo       the is has no 是否包含行号
     * @param contentLists  the content lists 此处如果没有参数可传的话 不要传入null 空着就可以
     * @return the string
     * @throws Exception the exception
     */
    public static String exportExcel(ExportSettingEntity settingEntity, String filePath, boolean useTemplate, boolean isHasTitle, int rowSkipNumber, boolean isHasNo, List<Map<String, Object>>... contentLists)
            throws Exception
    {
        WritableWorkbook workBook = null;

        try
        {
            Integer noWidth = 10;
            File file = new File(filePath);
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
                // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象

            }

            if (useTemplate)
            {
                Workbook wb = Workbook.getWorkbook(new File(filePath));
                workBook = Workbook.createWorkbook(file, wb);
            }
            else
            {
                workBook = Workbook.createWorkbook(file);
            }


            //写入内容
            //region 样式设置
            // 表头 字体样式

            WritableFont titleFont = new WritableFont(WritableFont.createFont("黑体"), 13, WritableFont.NO_BOLD, false);
            WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
            titleCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            titleCellFormat.setAlignment(Alignment.CENTRE);
            titleCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            titleCellFormat.setBackground(Colour.GRAY_25);

            WritableFont totalFont = new WritableFont(WritableFont.createFont("黑体"), 13, WritableFont.BOLD, false);

            WritableCellFormat totalCellFormat = new WritableCellFormat(totalFont);
            totalCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            totalCellFormat.setAlignment(Alignment.CENTRE);
            totalCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            //totalCellFormat.setBackground(Colour.GREY_25_PERCENT);

            WritableCellFormat totalNumberFormat = new WritableCellFormat(NumberFormats.FLOAT);
            totalNumberFormat.setFont(totalFont); //设置字体
            totalNumberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            totalNumberFormat.setAlignment(Alignment.CENTRE);

            //内容样式
            WritableFont contentFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

            WritableCellFormat contentCellFormat = new WritableCellFormat(contentFont);
            contentCellFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            contentCellFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT); //定义一个浮点数单元格样式
            floatFormat.setFont(contentFont); //设置字体
            floatFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            floatFormat.setAlignment(Alignment.CENTRE);

            WritableCellFormat integerFormat = new WritableCellFormat(NumberFormats.INTEGER); //定义整型一个单元格样式
            integerFormat.setFont(contentFont); //设置字体
            integerFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            integerFormat.setAlignment(Alignment.CENTRE);

            //endregion

            List<ExportSheetEntity> exportSheetEntities = settingEntity.getExportSheetEntities();

            int sheetCount = exportSheetEntities.size();
            for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++)
            {
                ExportSheetEntity sheetEntity = exportSheetEntities.get(sheetIndex);

                Boolean hastTotalRow = sheetEntity.getHasTotalRow();

                int colSkip = 0;
                int rowSkip = rowSkipNumber;
                // 创建一个可写入的工作表
                //创建sheet

                WritableSheet sheet;
                //如果使用模板 则获得对应 sheet 如果不使用模板 这创建对应的sheet
                if (useTemplate)
                {
                    sheet = workBook.getSheet(sheetIndex);
                }
                else
                {
                    // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
                    String sheetName = ObjectUtils.isNotEmpty(sheetEntity.getSheetName()) ? sheetEntity.getSheetName() : "sheet" + (sheetIndex + 1);
                    sheet = workBook.createSheet(sheetName, sheetIndex);
                }

                List<List<ExportTitleEntity>> exportTitleRowsEntities = sheetEntity.getExportTitleRowsEntities();


                //合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
                //写入标题
                if (isHasTitle)
                {
                    String title = ObjectUtils.isNotEmpty(sheetEntity.getSheetName()) ? sheetEntity.getSheetName() : settingEntity.getExportName();
                    Label label = new Label(0, 0, title, titleCellFormat);
                    sheet.addCell(label);
                    int merge = exportTitleRowsEntities.get(0).stream().mapToInt(entity -> {
                        int colSpan = entity.getColSpan();
                        if (colSpan == 0)
                        {
                            return 1;
                        }
                        else
                        {
                            return colSpan;
                        }
                    }).sum();
                    if (!isHasNo)
                    {
                        merge--;
                    }
                    sheet.mergeCells(colSkip, rowSkip, merge, rowSkip);
                    rowSkip++;
                }


                //写入表头

                if (isHasNo)
                {
                   // if (useTemplate)
                    if (!useTemplate)
                    {
                        String content = "序号";
                        Label label = new Label(colSkip, rowSkip, content, titleCellFormat);
                        sheet.addCell(label);
                        sheet.setColumnView(colSkip, noWidth);
                        int merge = exportTitleRowsEntities.size();
                        sheet.mergeCells(colSkip, rowSkip, colSkip, merge + rowSkip - 1);
                    }
                    colSkip++;
                    //rowSkip = merge + rowSkip - 1;
                }


                List<ExportTitleEntity> dataKeySetting = new ArrayList<>();
                //合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
                int rowIndex = rowSkip;
                int titleRowsCount = exportTitleRowsEntities.size();
                for (int i = 0; i < titleRowsCount; i++)
                {
                    List<ExportTitleEntity> exportTitleEntities = exportTitleRowsEntities.get(i);

                    int colIndex = colSkip;
                    int titleCount = exportTitleEntities.size();
                    for (int j = 0; j < titleCount; j++)
                    {
                        ExportTitleEntity titleEntity = exportTitleEntities.get(j);

                        String displayMember = titleEntity.getDisplayMember();
                        String valueMember = titleEntity.getValueMember();
                        Integer width = titleEntity.getWidth();
                        width = getWidth(width);
                        Integer colSpan = titleEntity.getColSpan();
                        Integer rowSpan = titleEntity.getRowSpan();
                        Integer skip = titleEntity.getSkip();
                        String defaultValue = titleEntity.getDefaultValue();
                        if (rowSpan > 0)
                        {
                            rowSpan = rowSpan - 1;
                        }

                        if (colSpan > 0)
                        {
                            colSpan = colSpan - 1;
                        }

                        if (skip > 0)
                        {
                            colIndex = skip + colIndex - 1;
                        }
                        if (valueMember.length() != 0 || defaultValue.length() != 0)
                        {
                            ExportTitleEntity exportTitleEntity = new ExportTitleEntity(valueMember, defaultValue, colIndex);
                            dataKeySetting.add(exportTitleEntity);
                        }
                        if (!useTemplate)
                        {
                            Label label = new Label(colIndex, rowIndex, displayMember, titleCellFormat);
                            sheet.addCell(label);
                            sheet.setColumnView(colIndex, width);
                            sheet.mergeCells(colIndex, rowIndex, colIndex + colSpan, rowIndex + rowSpan);
                        }
                        colIndex = colIndex + 1 + colSpan;
                    }
                    rowIndex = rowSkip + 1;
                }

                if (useTemplate)
                {
                    rowSkip = rowSkip - 1;
                }
                else
                {
                    rowSkip = rowSkip + exportTitleRowsEntities.size() - 1;
                }

                List<Map<String, Object>> contentList;
                if (contentLists.length < sheetIndex + 1)
                {
                    contentList = new ArrayList<>();
                }
                else
                {
                    contentList = contentLists[sheetIndex];
                }

                String blankValue = sheetEntity.getBlankValue();

                int contentSize = contentList.size();
                int skipNoCount = 0;
                String totalKey = sheetEntity.getTotalKey();
                Integer totalColSpan = sheetEntity.getTotalColSpan();
                String totalName = "总计";
                String totalNameKey = sheetEntity.getTotalNameKey();

                for (int i = 0; i < contentSize; i++)
                {
                    Map<String, Object> contentMap = contentList.get(i);

                    int columnCount = sheet.getColumns();
                    int startColumn = 0;

                    boolean isTotalRow = false;

                    if (hastTotalRow && contentMap.containsKey(totalKey))
                    {
                        //合并单元格，第一个参数：要合并的单元格最左上角的列号，第二个参数：要合并的单元格最左上角的行号，第三个参数：要合并的单元格最右角的列号，第四个参数：要合并的单元格最右下角的行号，
                        if (contentMap.containsKey(totalNameKey) && ObjectUtils.isNotEmpty(contentMap.get(totalNameKey)))
                        {
                            totalName = DataSwitch.convertObjectToString(contentMap.get(totalNameKey));
                        }
                        Label label = new Label(0, i + rowSkip + 1, totalName, totalCellFormat);
                        sheet.addCell(label);
                        sheet.mergeCells(0, i + rowSkip + 1, totalColSpan - 1, i + rowSkip + 1);
                        startColumn = titleRowsCount;
                        isTotalRow = true;
                        skipNoCount++;
                    }
                    else
                    {
                        if (isHasNo)
                        {
                            int no = i + 1 - skipNoCount;
                            Number label = new Number(0, i + rowSkip + 1, no, contentCellFormat);
                            sheet.addCell(label);
                            sheet.setColumnView(0, noWidth);
                            startColumn++;
                        }
                    }


                    for (int j = startColumn; j < columnCount; j++)
                    {
                        int finalJ = j;
                        Optional<ExportTitleEntity> exportTitleEntityOptional = dataKeySetting.stream().filter(dataKey -> finalJ == dataKey.getColIndex()).findFirst();
                        Object content = blankValue;
                        if (!exportTitleEntityOptional.equals(Optional.empty()))
                        {
                            String key = exportTitleEntityOptional.get().getValueMember();
                            String defaultValue = exportTitleEntityOptional.get().getDefaultValue();
                            if (defaultValue.length() != 0)
                            {
                                content = defaultValue;

                            }
                            else if (contentMap.containsKey(key))
                            {
                                content = contentMap.get(key);
                            }
                        }

                        if (content == null)
                        {
                            content = "-";
                        }
                        Class type = content.getClass();

                        if (type.equals(BigDecimal.class) || type.equals(Float.class) || type.equals(Double.class))
                        {
                            Number number = new Number(j, i + rowSkip + 1, DataSwitch.convertObjectToDouble(content));
                            if (isTotalRow)
                            {
                                number.setCellFormat(totalNumberFormat);
                            }
                            else
                            {
                                number.setCellFormat(floatFormat);
                            }
                            sheet.addCell(number);
                        }
                        else if (type.equals(Integer.class) || type.equals(Long.class))
                        {
                            Number number = new Number(j, i + rowSkip + 1, DataSwitch.convertObjectToLong(content));
                            if (isTotalRow)
                            {
                                number.setCellFormat(totalNumberFormat);
                            }
                            else
                            {
                                number.setCellFormat(integerFormat);
                            }
                            sheet.addCell(number);
                        }
                        else
                        {

                            Label label = new Label(j, i + rowSkip + 1, DataSwitch.convertObjectToString(content));
                            if (isTotalRow)
                            {
                                label.setCellFormat(totalCellFormat);
                            }
                            else
                            {
                                label.setCellFormat(contentCellFormat);
                            }
                            sheet.addCell(label);
                        }
                    }

                }
            }

            workBook.write();
            return filePath;
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            workBook.close();
        }
    }


    private static int getWidth(int width)
    {
        return width + 2;
        //return (8 * width / 18);
    }


    /**
     * Read excel list.
     *
     * @param filePath the file path
     * @return the list
     * @throws Exception the exception
     */
    public List<String[]> readExcel(String filePath) throws Exception
    {
        try
        {
            return readExcel(filePath, 0, 0);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Read excel list.
     *
     *
     * @param filePath the file path
     * @param rowSkip  the row skip
     * @param colSkip  the col skip
     * @return the list
     * @throws Exception the exception
     */
    public static List<String[]> readExcel(String filePath, int rowSkip, int colSkip) throws Exception
    {
        try
        {
            return readExcel(filePath, 1, rowSkip, colSkip);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Read excel list.
     *
     * @param filePath   the file path
     * @param sheetIndex the sheet index ：sheetIndex 从1开始
     * @param rowSkip    the row skip
     * @param colSkip    the col skip
     * @return the list
     * @throws Exception the exception
     */
    public static List<String[]> readExcel(String filePath, int sheetIndex, int rowSkip, int colSkip) throws Exception
    {
        Workbook workBook = null;
        try
        {
            List<String[]> contentList = new ArrayList<>();
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new Exception("路径[" + filePath + "]文件不存在!");
            }
            workBook = Workbook.getWorkbook(file);
            Sheet[] sheets = workBook.getSheets();
            if (sheets.length < sheetIndex)
            {
                if (!file.exists())
                {
                    throw new Exception("路径[" + filePath + "]文件不存在 弟 [" + sheetIndex + "] 个 sheet!");
                }
            }

            Sheet sheet = sheets[sheetIndex - 1];
            int rowCount = sheet.getRows();
            int colCount = sheet.getColumns();

            for (int i = rowSkip; i < rowCount; i++)
            {
                List<String> row = new ArrayList<>();
                for (int j = colSkip; j < colCount; j++)
                {
                    Cell cell = sheet.getCell(j, i);
                    @SuppressWarnings("unused")
                    String content = cell.getContents();
                    row.add(content);
                }

                contentList.add(row.toArray(new String[row.size()]));
            }
            try
            {

                if (file.exists() && file.isFile())
                {
                    file.delete();
                }
            }
            catch (Exception ex)
            {


            }

            return contentList;
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            workBook.close();
        }
    }


}
