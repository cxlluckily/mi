package com.shankephone.mi.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 赵亮
 * @date 2018-06-12 11:08
 */
@Controller
@RequestMapping("/error")
@Slf4j
public class ErrorController
{
    @RequestMapping(value = "/noAuth")
    public String noAuth()
    {
        log.info("来到了错误action了");
        return "error";
    }
}
