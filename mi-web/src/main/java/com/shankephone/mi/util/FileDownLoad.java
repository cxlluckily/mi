package com.shankephone.mi.util;

import com.shankephone.mi.common.enumeration.DateFormatEnum;
import com.shankephone.mi.common.vo.ResultVO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author 赵亮
 * @date 2018-08-29 19:44
 */
public class FileDownLoad
{
    private static final String CHAR_SET = "UTF-8";
    private static final int BUFFER_SIZE = 5 * 1024;


    /**
     * Download json object.
     *
     * @param response     the response
     * @param filePath     the file path
     * @param realFileName the real file name
     * @return the json object
     * @throws IOException the io exception
     */
    public static ResultVO download(HttpServletResponse response, String filePath, String realFileName) throws IOException
    {

        ServletOutputStream outputStream = null;
        InputStream downLoadStream = null;
        BufferedInputStream bufferedInputStream = null;
        File downloadFile=null;
        try
        {
              downloadFile = new File(filePath);
            if (!downloadFile.exists())
            {
                return ResultVOUtil.error(-1, "The file is not exist.");
            }
            else
            {
                response.addHeader("Content-Length", DataSwitch.convertObjectToString(downloadFile.length()));
                String date = "_" + DateUtil.getDateString(DateFormatEnum.YYYYMMDDHHMMSSsss);
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
                try
                {
                    if (downloadFile.exists() && downloadFile.isFile())
                    {
                        downloadFile.delete();
                    }
                }
                catch (Exception ex)
                {

                }
            }
            catch (IOException e)
            {
              //  throw e;
            }

        }
    }
    /**
     * Download json object.
     *
     * @param response     the response
     * @param filePath     the file path
     * @param realFileName the real file name
     * @return the json object
     * @throws IOException the io exception
     */
    public static ResultVO downMubanload(HttpServletResponse response, String filePath, String realFileName) throws IOException
    {

        ServletOutputStream outputStream = null;
        InputStream downLoadStream = null;
        BufferedInputStream bufferedInputStream = null;
        File downloadFile=null;
        try
        {
            downloadFile = new File(filePath);
            if (!downloadFile.exists())
            {
                return ResultVOUtil.error(-1, "The file is not exist.");
            }
            else
            {
                response.addHeader("Content-Length", DataSwitch.convertObjectToString(downloadFile.length()));
                String date ="";// "_" + DateUtil.getDateString(DateFormatEnum.YYYYMMDDHHMMSSsss);
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
                //  throw e;
            }

        }
    }
}
