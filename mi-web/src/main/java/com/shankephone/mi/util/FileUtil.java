package com.shankephone.mi.util;

import com.shankephone.fdfs.FdfsClient;
import com.shankephone.mi.common.enumeration.DateFormatEnum;
import com.shankephone.mi.common.vo.ResultVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type File util.
 *
 * @author 赵亮
 * @date 2018 -06-25 14:58
 */
public class FileUtil extends org.apache.commons.io.FileUtils
{ // 默认设置文件缓存大小 5M
    private static final int BUFFER_SIZE = 5 * 1024;
    private static final String CHAR_SET = "UTF-8";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    private static final String FILE_SEPARATOR = "/";// System.getProperty("file.separator", "/");

    /**
     * Test.
     */
    @Test
    public void test()
    {
        try
        {
            String file = "D:\\QQDownload";

            System.out.println("isDirectory(file) = " + isDirectory(file));

            File file1 = new File(file);
            System.out.println("file1.length() = " + file1.length());
            System.out.println("countDirectorySize(file) = " + countFileSizeInDirectory(file));
            System.out.println("countFileNumInDir(file) = " + countFileNumberInDirectory(file));
            file = "D:\\a\\a";
            String target = "d:/a/a1/";
            copyFile(file, target);

            deleteFile(target);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * 如果传入是文件夹，则直接创建文件夹 如果传入的文件，则创建该文件的父文件夹 <p> 文件夹必须以 / 或者 \\ 结尾
     *
     * @param filePath the path
     * @throws IOException the io exception
     */
    public static void createDirectory(String filePath) throws IOException
    {
        Path file = Paths.get(filePath);

        //获取 父文件夹，判断父文件夹是否存在，如果不存在，则创建父文件夹
        Path parentRootPath = file.getParent();

        if (!Files.exists(parentRootPath))
        {
            Files.createDirectories(parentRootPath);
        }

        if (isDirectory(filePath) && !Files.exists(file))
        {
            Files.createDirectory(file);
        }

    }

    /**
     * Test 12.
     */
    @Test
    public void test12()
    {
        String fileName = "D:/iisweb/imageWeb/ueditor/ueditor/upload/image/201718041/122121/";
        System.out.println("getParentDirPath(fileName) = " + getParentDirPath(fileName));
    }

    /**
     * Gets parent dir path.
     *
     * @param file the file
     * @return the parent dir path
     */
    public static String getParentDirPath(String file)
    {
        Path path = Paths.get(file);
        String dirPath = path.getParent().toString();
        dirPath = formatDirectoryPath(dirPath);
        return dirPath;
    }

    /**
     * Create file.
     *
     * @param filePath the file path
     * @throws IOException the io exception
     */
    public static void createFile(String filePath) throws IOException
    {
        try
        {
            createDirectory(filePath);
            Path file = Paths.get(filePath);
            if (!Files.exists(file))
            {
                Files.createFile(file);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Is exist boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean isExist(String filePath)
    {
        try
        {
            String temp = System.getProperty("os.name").contains("indow") ? filePath.substring(1) : filePath;
            Path path = Paths.get(temp);
            return Files.exists(path);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Is directory boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    public static boolean isDirectory(String filePath)
    {
        try
        {
            return Files.isDirectory(Paths.get(filePath)) || filePath.endsWith(FILE_SEPARATOR);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获得文件或文件夹的占用空间的大小，单位字节
     *
     * @param dirPath the dir path
     * @return the long
     * @throws Exception the exception
     */
    public static long countFileSizeInDirectory(String dirPath) throws Exception
    {
        long size;
        File directory = new File(dirPath);
        Path path = Paths.get(dirPath);
        if (!Files.exists(path))
        {
            throw new Exception("路径 [" + dirPath + "] 不存在!");
        }

        if (isDirectory(dirPath))
        {
            File[] files = directory.listFiles();
            size = Arrays.stream(files).filter(File::isFile).mapToLong(File::length).sum();
            List<String> errorList = new ArrayList<>();
            Stream<String> dirList = Arrays.stream(files).filter(File::isDirectory).map(File::getAbsolutePath);
            size += dirList.mapToLong(dir ->
            {
                try
                {
                    return countFileSizeInDirectory(dir);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    errorList.add(e.getMessage());
                    return 0;
                }
            }).sum();
            if (errorList.size() > 0)
            {
                throw new Exception(DataSwitch.getCombineString(LINE_SEPARATOR, errorList));
            }
        }
        else
        {
            size = directory.length();
        }
        return size;
    }


    /**
     * 递归求取目录文件个数
     *
     * @param dirPath the dir path
     * @return the file count in dir
     * @throws Exception the exception
     */
    public static long countFileNumberInDirectory(String dirPath) throws Exception
    {
        File directory = new File(dirPath);
        if (!directory.exists())
        {
            throw new Exception("路径 [" + dirPath + "] 不存在!");
        }
        long count;
        if (isDirectory(dirPath))
        {
            File[] files = directory.listFiles();
            count = Arrays.stream(files).filter(File::isFile).count();
            List<String> errorList = new ArrayList<>();
            count += Arrays.stream(files).filter(File::isDirectory).map(File::getAbsolutePath).mapToLong(file ->
            {
                try
                {
                    return countFileNumberInDirectory(file);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    errorList.add(e.getMessage());
                    return 0;
                }
            }).sum();
            if (errorList.size() > 0)
            {
                throw new Exception(DataSwitch.getCombineString(LINE_SEPARATOR, errorList));
            }
        }
        else
        {
            count = 1;
        }
        return count;
    }

    /**
     * 列出某文件夹下所有文件列表，不包含传入的pathStr
     *
     * @param pathStr the path str
     * @return the list
     * @throws IOException the io exception
     */
    public static List<Path> listFiles(String pathStr) throws IOException
    {
        try
        {
            List<Path> fileList = listFilesContainSelf(pathStr);
            fileList.remove(Paths.get(pathStr));
            return fileList;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获得某文件夹下所有文件列表，包含传去的pathStr
     *
     * @param pathStr the path str
     * @return the list
     * @throws IOException the io exception
     */
    public static List<Path> listFilesContainSelf(String pathStr) throws IOException
    {
        try
        {
            List<Path> fileList = new ArrayList<>();

            Path path = Paths.get(pathStr);
            if (isDirectory(path.toString()))
            {
                List<Path> dirInDirPath = Files.list(path).collect(Collectors.toList());
                fileList.add(path);
                for (Path pathInDir : dirInDirPath)
                {
                    fileList.addAll(listFilesContainSelf(pathInDir.toString()));

                }
            }
            else
            {
                fileList.add(path);
            }


            return fileList;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size the size
     * @return the string
     */
    public static String formatSize(long size)
    {
        // 转换文件大小
        DecimalFormat df = new DecimalFormat("0.00");
        if (size < 1024)
        {
            return df.format((double) size) + "B";
        }
        else if (size < 1048576)
        {
            return df.format((double) size / 1024) + "KB";
        }
        else if (size < 1073741824)
        {
            return df.format((double) size / 1048576) + "MB";
        }
        else
        {
            return df.format((double) size / 1073741824) + "GB";
        }
    }

    /**
     * 复制文件到指定目录 <p> 需指定文件名
     *
     * @param sourceFilePath the source file path 源路径
     * @param targetFilePath the target file path 目标路径
     * @param fileName       the file name 文件名
     * @return the boolean
     * @throws Exception the exception
     */
    public static boolean copyFile(String sourceFilePath, String targetFilePath, String fileName) throws Exception
    {
        try
        {
            return copyFile(sourceFilePath, targetFilePath + FILE_SEPARATOR + fileName);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 复制文件或文件夹到指定目录 <p> 如果复制文件，则 sourceFilePath和targetFilePath都是具体的文件路径 <p> 如果复制文件夹，则 sourceFilePath和targetFilePath都是具体的文件夹路径
     *
     * @param sourceFilePath the source file path
     * @param targetFilePath the target file path
     * @return the boolean
     * @throws Exception the exception
     */
    public static boolean copyFile(String sourceFilePath, String targetFilePath) throws Exception
    {
        try
        {
            if (targetFilePath.trim().equals(sourceFilePath))
            {
                return true;
            }
            Path sourceFile = Paths.get(sourceFilePath);
            Path targetFile = Paths.get(targetFilePath);
            if (isDirectory(sourceFilePath))
            {
                List<String> errorList = new ArrayList<>();
                if (!isDirectory(targetFilePath))
                {
                    throw new Exception("源路径 [" + sourceFilePath + "] 是一个文件夹，目标路径 [" + targetFilePath + "] 必须也是文件夹!");
                }
                File[] files = sourceFile.toFile().listFiles();
                Arrays.stream(files).map(File::getAbsolutePath).forEach(sourcePath ->
                {
                    Path tempTargetFile = Paths.get(targetFilePath, sourcePath.replace(sourceFilePath, ""));
                    try
                    {
                        createDirectory(tempTargetFile.toString() + FILE_SEPARATOR);
                        copyFile(sourcePath, tempTargetFile.toString());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        errorList.add(e.getMessage());
                    }
                });
                if (errorList.size() > 0)
                {
                    throw new Exception(DataSwitch.getCombineString(LINE_SEPARATOR, errorList));
                }
            }
            else
            {
                createDirectory(targetFilePath);
                Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
            }

            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Clear directory boolean.
     * <p>
     * 如果文件夹不存在则创建一个文件夹
     *
     * @param filePath the file path
     * @return the boolean
     * @throws Exception the exception
     */
    public static boolean clearDirectory(String filePath) throws Exception
    {
        try
        {
            createDirectory(filePath);
            filePath = formatDirectoryPath(filePath);
            deleteFile(filePath);
            createDirectory(filePath);
            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Delete file boolean.
     *
     * @param filePaths the file paths
     * @return the boolean
     */
    public static boolean deleteFile(List<String> filePaths)
    {
        try
        {
            return deleteFile(filePaths.toArray(new String[filePaths.size()]));
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Delete file boolean.
     *
     * @param filePaths the file paths 此处如果没有参数可传的话 不要传入null 空着就可以
     * @return the boolean
     * @throws Exception the exception
     */
    public static boolean deleteFile(String... filePaths)
    {
        try
        {

            for (String filePath : filePaths)
            {
                if (StringUtils.isEmpty(filePath))
                {
                    continue;
                }
                Path file = Paths.get(filePath);

                if (isDirectory(filePath))
                {
                    File[] files = file.toFile().listFiles();
                    if (files != null)
                    {
                        for (File tempPath : files)
                        {
                            deleteFile(tempPath.getAbsolutePath());
                        }
                    }


                    file.toFile().delete();
                }
                else
                {
                    file.toFile().delete();

                }
            }

            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * 获得文件扩展名 <p> 2016年10月25日23:37:57
     *
     * @param fileName the filename
     * @return the extension name 返回格式 ex: .exe .jpg .png
     */
    public static String getFileType(String fileName)
    {
        if (fileName.contains("."))
        {
            return StringUtils.substring(fileName, fileName.lastIndexOf("."));
        }
        else
        {
            return "";
        }

    }


    /**
     * 获得不带扩展的文件名
     * <p>
     * 2016年10月25日23:38:19
     *
     * @param fileName the fileName
     * @return the file name no ex
     */
    public static String getFileName(String fileName)
    {
        int start = formatFilePath(fileName).lastIndexOf(FILE_SEPARATOR);
        start = start == -1 ? 0 : start + 1;
        int lastIndex = fileName.lastIndexOf(".");
        lastIndex = lastIndex == -1 ? fileName.length() : lastIndex;
        return StringUtils.substring(fileName, start, lastIndex);
    }

    /**
     * Format file path string.
     *
     * @param path the path
     * @return the string
     */
    public static String formatFilePath(String path)
    {
        return StringUtils.replace(path, "\\", FILE_SEPARATOR);
    }

    /**
     * Format directory path string.
     *
     * @param path the path
     * @return the string
     */
    public static String formatDirectoryPath(String path)
    {
        if (path.length() == 0)
        {
            return "";
        }
        path = formatFilePath(path);
        return path.endsWith(FILE_SEPARATOR) ? path : path + FILE_SEPARATOR;
    }

    /**
     * Gets file item from request.
     *
     * @param request the request
     * @return the file item from request
     * @throws FileUploadException the file upload exception
     */
    public static FileItem getFileItemFromRequest(HttpServletRequest request) throws FileUploadException
    {
        try
        {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            Optional<FileItem> fileItemOptional = upload.parseRequest(request).stream().filter(fileItem -> !fileItem.isFormField()).findFirst();
            return fileItemOptional.equals(Optional.empty()) ? null : fileItemOptional.get();

        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * One file upload string. 单文件上传
     *
     * @param request the request
     * @return the fileName string List
     * @throws Exception the exception
     */
    public static List<String> fileUpload(HttpServletRequest request) throws Exception
    {
        List<String> result = new ArrayList<>();


        //获取所有文件列表
        Map<String, MultipartFile> fileMap = ((MultipartHttpServletRequest) request).getFileMap();

        for (MultipartFile item : fileMap.values())
        {
            CommonsMultipartFile fileItem = (CommonsMultipartFile) item;
            if (fileItem.getFileItem() != null && fileItem.getFileItem().getSize() > 0)
            {

                String name = fileItem.getFileItem().getName();
                String extName = name.substring(name.lastIndexOf(".") + 1);
                byte[] bytes = null;
                bytes = fileItem.getBytes(); //将文件转换成字节流形式
                String fileId = FdfsClient.upload(bytes, extName, null);
                System.out.println(FdfsClient.getDownloadServer() + fileId);
                result.add(fileId);
            }
        	
           /* CommonsMultipartFile fileItem = (CommonsMultipartFile) item;
            String filedName = fileItem.getFileItem().getName();
            //InputStream inputStream = item.getInputStream();
            //FileUtil.copyInputStreamToFile(inputStream, new File(repairApplyPhysicalPath));
            //String filedName = item.getName();
            //文件名
            String fileType = FileUtil.getFileType(filedName);
            String uuid = DataSwitch.getUUID();
            String saveFileName = uuid + fileType;
            String repairApplyPhysicalPath = FilePath.getRepairApplyPhysicalPath(saveFileName);
            if (!FileUtil.isExist(repairApplyPhysicalPath)) {
                FileUtil.createDirectory(repairApplyPhysicalPath);
            }
            //写入文件
            File file = new File(repairApplyPhysicalPath);
            item.transferTo(file);
            result.add(saveFileName);*/

        }
        return result;
    }


    /**
     * Download file by url boolean.
     *
     * @param url      the url
     * @param savePath the save path
     * @param saveFile the save file
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean downloadFileByUrl(String url, String savePath, String saveFile) throws IOException
    {

        try
        {
            savePath = formatDirectoryPath(savePath);
            return downloadFileByUrl(url, savePath + saveFile);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Download file by url boolean.
     *
     * @param urlStr       the url str
     * @param saveFilePath the save file path
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean downloadFileByUrl(String urlStr, String saveFilePath) throws IOException
    {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try
        {
            //urlStr = StringUtils.substring(urlStr, 0, urlStr.indexOf("?"));
            saveFilePath = formatFilePath(saveFilePath);
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            inputStream = conn.getInputStream();
            FileUtil.createFile(saveFilePath);

            outputStream = new FileOutputStream(saveFilePath);
            //缓存数组
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }

            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            if (outputStream != null)
            {
                outputStream.close();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
            if (conn != null)
            {
                conn.disconnect();
            }
        }
    }

    /**
     * Write content to txt boolean.
     *
     * @param pathStr the path str
     * @param content the content
     * @return the boolean
     * @throws IOException the io exception
     */
    public static boolean writeContentToTxt(String pathStr, String content) throws IOException
    {
        try
        {
            Path path = Paths.get(pathStr);
            createFile(pathStr);
            List<CharSequence> lineList = new ArrayList<>();
            lineList.add(content);
            Files.write(path, lineList, Charset.forName(CHAR_SET));
            return true;
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Download json object.
     *
     * @param response     the response controller中的response
     * @param filePath     the file path 服务器上待下载的文件路径
     * @param realFileName the real file name 下载后的文件名称
     * @return the json object
     * @throws IOException the io exception
     */
    public ResultVO download(HttpServletResponse response, String filePath, String realFileName) throws IOException, InterruptedException
    {
        ServletOutputStream outputStream = null;
        InputStream downLoadStream = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            File downloadFile = new File(filePath);
            if (!downloadFile.exists())
            {
                return ResultVOUtil.notExist();
            }
            else
            {
                response.addHeader("Content-Length", DataSwitch.convertObjectToString(downloadFile.length()));
                String date = "_" + DateUtils.getDateString(DateFormatEnum.YYYYMMDDHHMMSS);
                response.setContentType("application/octet-stream");
                if (realFileName.contains("."))
                {
                    String fileName = realFileName.substring(0, realFileName.lastIndexOf("."));
                    String type = realFileName.substring(realFileName.lastIndexOf(".") + 1);
                    realFileName = fileName + date + "." + type;
                }
                else
                {
                    realFileName += date;
                }
                realFileName = URLEncoder.encode(realFileName, CHAR_SET);
                response.addHeader("Content-Disposition", "attachment;filename=" + realFileName);
                response.setHeader("Accept-Ranges", "bytes");

                outputStream = response.getOutputStream();

                downLoadStream = new FileInputStream(downloadFile);
                bufferedInputStream = new BufferedInputStream(downLoadStream);

                byte[] content = new byte[BUFFER_SIZE];
                int length;

                while ((length = bufferedInputStream.read(content, 0, content.length)) != -1)
                {
                    outputStream.write(content, 0, length);
                }

                return ResultVOUtil.success();

            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            try
            {
                Thread.sleep(1000);
                if (downLoadStream != null)
                {
                    downLoadStream.close();
                }
                if (bufferedInputStream != null)
                {
                    bufferedInputStream.close();
                }
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
            catch (IOException e)
            {
                throw e;
            }
            catch (InterruptedException e)
            {
                throw e;
            }

        }
    }

    /**
     * 把file转换成byte[]
     *
     * @author：赵亮
     * @date：2018-08-30 10:44
     */
    public static byte[] file2buf(File fobj) throws IOException
    {
        byte[] buffer = null;
        try
        {
            if (!fobj.exists())
            {
                return null;
            }

            FileInputStream fis = new FileInputStream(fobj);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while ((len = fis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
        return buffer;
    }

    /**
     * 获取request里面的file流
     *
     * @author：赵亮
     * @date：2018-09-07 15:17
     */
    public static CommonsMultipartFile getRequestOneFile(HttpServletRequest request, String key)
    {
        MultipartFile fileParameter = ((DefaultMultipartHttpServletRequest) request).getFile(key);
        CommonsMultipartFile photoUrl = null;
        if (ObjectUtils.isNotNull(fileParameter))
        {
            photoUrl = (CommonsMultipartFile) fileParameter;
        }
        return photoUrl;
    }

    /**
     * 获取request里面的file流
     *
     * @author：赵亮
     * @date：2018-09-07 15:17
     */
    public static List<CommonsMultipartFile> getRequestFiles(HttpServletRequest request, String key)
    {
        List<MultipartFile> fileParameters = ((DefaultMultipartHttpServletRequest) request).getFiles(key);
        List<CommonsMultipartFile> photoUrl = new ArrayList<>();
        if (ObjectUtils.isNotNull(fileParameters))
        {
            for (MultipartFile fileParameter : fileParameters)
            {
                photoUrl.add((CommonsMultipartFile) fileParameter);
            }
        }
        return photoUrl;
    }

}
