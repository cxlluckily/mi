package com.shankephone.mi.handler;

import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.vo.ResultVO;
import com.shankephone.mi.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 赵亮
 * @date 2018-06-12 11:25
 */
@ControllerAdvice(basePackages = "com")
@Slf4j
public class MiExceptionHandler
{
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public ResultVO<Map<String, Object>> handlerAuthorizeException()
    {
        log.info("到了异常抓取了");
        return ResultVOUtil.error(ResultEnum.Authentication_Error.getCode(), ResultEnum.Authentication_Error.getMessage());
    }


}
