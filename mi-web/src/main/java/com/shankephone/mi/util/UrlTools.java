package com.shankephone.mi.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shankephone.mi.common.enumeration.ContentTypeEnum;
import com.shankephone.mi.common.enumeration.RequestTypeEnum;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ErebusST
 * @date 2016/12/23 13:25
 */
public class UrlTools
{

    private final static String charset = "UTF-8";

    /**
     * 功能简介：根据Url和参数返回被调用url的内容，GET形式
     *
     * @param url the url
     * @return the string
     * @throws IOException the io exception
     */
    public static String get(String url) throws IOException
    {
        try
        {
            return call(url, "", RequestTypeEnum.Get, ContentTypeEnum.application_x_www_form_urlencoded);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    public static String get(String url, ContentTypeEnum contentTypeEnum) throws IOException
    {
        try
        {
            String data = "";
            if (contentTypeEnum.equals(ContentTypeEnum.other))
            {
                data = null;
            }
            return call(url, data, RequestTypeEnum.Get, contentTypeEnum);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }


    /**
     * Post Json对象.
     *
     * @param url  the url
     * @param data the data
     * @return the string
     * @throws IOException the io exception
     */
    public static String post(String url, JsonElement data) throws IOException
    {
        try
        {
            return call(url, data.toString(), RequestTypeEnum.Post, ContentTypeEnum.application_json);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Post string.
     *
     * @param url  the url
     * @param data the data
     * @return the string
     * @throws IOException the io exception
     */
    public static String post(String url, String data) throws IOException
    {
        try
        {
            return call(url, data, RequestTypeEnum.Post, ContentTypeEnum.text_xml);
        }
        catch (Exception ex)
        {
            throw ex;
        }
    }

    /**
     * Call string.
     *
     * @param url             the url
     * @param data            the data
     * @param requestTypeEnum the request type enum
     * @param contentTypeEnum the content type enum
     * @return the string
     * @throws IOException the io exception
     */
    public static String call( String url, String data,  RequestTypeEnum requestTypeEnum, ContentTypeEnum contentTypeEnum)
            throws IOException
    {
        HttpURLConnection connect = null;
        try
        {
            String encoding = "utf-8";
            connect = (HttpURLConnection) (new URL(url).openConnection());

            connect.setRequestMethod(requestTypeEnum.getValue());
            String contentType = "";
            if (contentTypeEnum != null)
            {
                contentType = contentTypeEnum.getValue() + ";";
            }
            contentType += "charset=" + charset;
            connect.setRequestProperty("Content-Type", contentType);
            connect.setRequestProperty("cache-control", "no-cache");

            connect.setConnectTimeout(1000 * 10);
            connect.setReadTimeout(1000 * 80);

            connect.setDoOutput(true);
            connect.setDoInput(true);

            // 连接
            connect.connect();


            if (data != null)
            {
                OutputStream outputStream = null;
                // 发送数据
                try
                {
                    outputStream = connect.getOutputStream();
                    outputStream.write(data.getBytes(charset));
                    outputStream.flush();
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
                }
            }


            // 接收数据
            int responseCode = connect.getResponseCode();
            StringBuffer response = new StringBuffer();
            BufferedReader in = null;
            try
            {
                String inputLine;
                in = new BufferedReader(new InputStreamReader(connect.getInputStream(), encoding));
                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (in != null)
                {
                    in.close();
                }
            }
            //接受错误数据
            if (responseCode != HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream = null;
                InputStreamReader reader = null;

                try
                {
                    inputStream = connect.getErrorStream();
                    reader = new InputStreamReader(inputStream);

                    in = new BufferedReader(reader);
                    String errorLine;
                    response = new StringBuffer();
                    while ((errorLine = in.readLine()) != null)
                    {
                        response.append(errorLine);
                    }
                    JsonObject result = new JsonObject();
                    result.addProperty("result", "fail");
                    result.addProperty("responseCode", responseCode);
                    result.addProperty("errorInfo", response.toString());
                    response = new StringBuffer(response.toString());
                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    if (in != null)
                    {
                        in.close();
                    }
                    if (reader != null)
                    {
                        reader.close();
                    }
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }
            }

            return response.toString();
        }
        catch (Exception ex)
        {
            if (connect != null)
            {
                JsonObject result = new JsonObject();
                int responseCode = connect.getResponseCode();
                BufferedReader in = null;
                InputStream inputStream = null;
                InputStreamReader reader = null;
                try
                {
                    inputStream = connect.getInputStream();
                    reader = new InputStreamReader(inputStream);
                    in = new BufferedReader(reader);
                    String errorLine;
                    StringBuffer response = new StringBuffer();
                    while ((errorLine = in.readLine()) != null)
                    {
                        response.append(errorLine);
                    }
                    result.addProperty("result", "fail");
                    result.addProperty("responseCode", responseCode);
                    result.addProperty("errorInfo", response.toString());
                    return result.toString();
                }
                catch (Exception e)
                {
                    throw e;
                }
                finally
                {
                    if (in != null)
                    {
                        in.close();
                    }
                    if (reader != null)
                    {
                        reader.close();
                    }
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }

            }
            throw ex;
        }
        finally
        {
            if (connect != null)
            {
                // 关闭连接
                connect.disconnect();
            }
        }
    }

    @Test
    public void test()
    {
        try
        {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fieldName", "loginName");
            jsonObject.addProperty("fieldValue", "e");
            //String s = UrlTools.post("http://meizu.baiducloud.top/api/1.0/baidiDuAI/runScoreResult", jsonObject);
            //String s = UrlTools.post("http://180.76.245.65/api/1.0/baidiDuAI/runScoreResult", jsonObject);
            String s = UrlTools.post("http://192.168.0.8:8084/api/1.0/baidiDuAI/runScoreResult", jsonObject);
            System.out.println("s = " + s);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


}
