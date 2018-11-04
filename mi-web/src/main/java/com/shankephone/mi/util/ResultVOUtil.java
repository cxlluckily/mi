package com.shankephone.mi.util;

import com.shankephone.mi.common.enumeration.ResultEnum;
import com.shankephone.mi.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Created by 赵亮
 * 2018年6月12日 09:48:00
 * 返回到前端的对象
 */
@Slf4j
public class ResultVOUtil {
    /**
     * Success result vo.
     *
     * @param object the object
     * @return the result vo
     */
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setStatusCode(200);
        resultVO.setResult("success");
        resultVO.setMessage("操作成功");
        return resultVO;
    }

    /**
     * Success result vo.
     *
     * @return the result vo
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * Error result vo.
     *
     * @param ex       the ex
     * @param messages the messages
     * @return the result vo
     */
    public static ResultVO error(Exception ex, String... messages) {
        Arrays.stream(messages).forEach(message -> log.error(message));
        ex.printStackTrace();
        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(ResultEnum.ERROR.getCode());
        resultVO.setResult("failure");
        if(messages.length == 0){
        	resultVO.setMessage(ResultEnum.ERROR.getMessage());
        } else {
        	resultVO.setMessage(messages[0]); 
        }
        return resultVO;
    }

    /**
     * Error result vo.
     *
     * @param code the code
     * @param msg  the msg
     * @return the result vo
     */
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(code);
        resultVO.setResult("failure");
        resultVO.setMessage(msg);
        log.error(msg);
        return resultVO;
    }

    /**
     * Param error result vo.
     *
     * @param param the param
     * @return the result vo
     */
    public static ResultVO paramError(String param) {

        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(ResultEnum.Parameter_Error.getCode());
        resultVO.setResult("failure");
        String error = String.format("%s , [%s] 参数不能为空 !"
                , ResultEnum.Parameter_Error.getMessage(), param);
        log.error(error);
        resultVO.setMessage(error);
        return resultVO;
    }

    /**
     * Exist result vo.
     *
     * @return the result vo
     */
    public static ResultVO exist(){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(200);
        resultVO.setResult("exist");
        resultVO.setMessage(" 验证的字段已存在");
        return resultVO;
    }

    /**
     * Not exist result vo.
     *
     * @return the result vo
     */
    public static ResultVO notExist(){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(200);
        resultVO.setResult("not_exist");
        resultVO.setMessage(" 验证的字段不存在");
        return resultVO;
    }

    public static ResultVO fileNotExist(){
        ResultVO resultVO = new ResultVO();
        resultVO.setStatusCode(200);
        resultVO.setResult("file_not_exist");
        resultVO.setMessage(" 文件不存在");
        return resultVO;
    }
}
