package com.shankephone.mi.common.model;

/**
 * @author 赵亮
 * @date 2018-06-25 14:57
 */
public class UploadFileEntity
{
    private String filedName;
    private String fileName;
    private String fileType;
    private String savePath;
    private String saveFileName;

    public String getFiledName()
    {
        return filedName;
    }

    public void setFiledName(String filedName)
    {
        this.filedName = filedName;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    public String getSavePath()
    {
        return savePath;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }

    public String getSaveFileName()
    {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName)
    {
        this.saveFileName = saveFileName;
    }
}
