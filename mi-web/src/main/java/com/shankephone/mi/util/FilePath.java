package com.shankephone.mi.util;

import com.shankephone.fdfs.FdfsClient;

/**
 * 文件路径工具类
 *
 * @author 司徒彬
 * @date 2018 /8/6 10:32
 */
public class FilePath {
    private static String getRootPath() {
        String savePath = PropertyAccessor.getProperty("baseImagePath");
        return savePath;
    }

    private static String getExcelRootPath() {
        String savePath = PropertyAccessor.getProperty("excel.basePath");
        return savePath;
    }

    /**
     * Gets user photo path.
     *
     * @return the user photo path
     */
    public static String getUserPhotoPath()
    {
        String rootPath = getRootPath();
        String type = PropertyAccessor.getProperty("userPhoto");
        return DataSwitch.getCombineString("", rootPath, type);
    }

    public static String getUserPhotoWebPath()
    {
        String rootPath = getWebPath();
        String type = PropertyAccessor.getProperty("userPhoto");
        return DataSwitch.getCombineString("", rootPath, type);
    }

    /**
     * Gets spare part photo path.
     *
     * @return the spare part photo path
     */
    public static String getSparePartPhotoPath()
    {
        String rootPath = getRootPath();
        String type = PropertyAccessor.getProperty("sparePartPhoto");
        return DataSwitch.getCombineString("", rootPath, type);
    }

    private static String getWebPath() {
        String webService = PropertyAccessor.getProperty("webService");
        return webService;
    }

    /**
     * Gets repair apply physical path.
     *
     * @param fileName the file name
     * @return the repair apply physical path
     */
    public static String getRepairApplyPhysicalPath(String fileName) {
        String rootPath = getRootPath();
        String type = PropertyAccessor.getProperty("repairApplyVirtualPath");
        return DataSwitch.getCombineString("", rootPath, type, fileName);
    }

    /**
     * Get repair web path string.
     *
     * @param fileName the file name
     * @return the string
     */
    public static String getRepairWebPath(String fileName){
    	return FdfsClient.getDownloadServer() + fileName;
        /*String webPath = getWebPath();
        String type = PropertyAccessor.getProperty("repairApplyVirtualPath");
        return DataSwitch.getCombineString("", webPath, type, fileName);*/
    }

    /**
     * Get repair web path string.
     *导入文件临时目录
     * @
     * @return the string
     */
    public static String getImportExcelPath(){
        String rootPath = getExcelRootPath();
        String type = PropertyAccessor.getProperty("excel.tempPath");
        return DataSwitch.getCombineString("", rootPath, type);
    }

    /**
     * Get repair web path string.
     *导入模板下载
     * @
     * @return the string
     */
    public static String getExcelTplPath(){
        String rootPath = getExcelRootPath();
        String type = PropertyAccessor.getProperty("excel.tplPath");
        return DataSwitch.getCombineString("", rootPath, type);
    }
}
