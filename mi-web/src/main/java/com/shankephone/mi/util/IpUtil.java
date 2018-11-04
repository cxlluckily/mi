package com.shankephone.mi.util;

import javax.servlet.http.HttpServletRequest;

/**
 * IP Util
 *
 * @author 司徒彬
 * @date 2017-03-10 13:55
 */
public class IpUtil
{
    /**
     * 获取访问端IP ErebusST 2017年2月15日23:43:57
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip.split(":").length >= 2)
        {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
