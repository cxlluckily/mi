package com.shankephone.mi.filter;

import com.shankephone.mi.security.model.UserLoginInfo;
import com.shankephone.mi.util.ObjectUtils;
import com.shankephone.mi.util.SessionMap;
import com.shankephone.mi.util.StringUtils;
import org.apache.shiro.authz.AuthorizationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 赵亮
 * @date 2018-09-10 20:51
 */
public class LoginFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        if (servletRequest instanceof HttpServletRequest)
        {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String requestUrl = request.getRequestURL().toString().trim();
            requestUrl = requestUrl.replace("//", "/");
            if (requestUrl.indexOf(".do") > 0)
            {
                try
                {
                    if(requestUrl.indexOf("login") == -1)
                    {
                        String userKey = request.getHeader("userKey");
                        if(StringUtils.isNotEmpty(userKey))
                        {
                            UserLoginInfo userInfo = SessionMap.getUserInfo(userKey);
                            if(ObjectUtils.isEmpty(userInfo))
                            {
                                throw new AuthorizationException();
                            }
                        }
                        else
                        {
                            throw new AuthorizationException();
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy()
    {

    }
}
