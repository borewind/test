package com.zxtechai.handler;

import com.zxtechai.constant.MessageConstant;
import com.zxtechai.exception.BaseException;
import com.zxtechai.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex 异常信息
     * @return 错误信息返回结果{code:0,msg:ex,data:null}
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /**
     * 处理SQL异常
     * @param ex 异常信息
     * @return 错误信息返回结果{code:0,msg:ex,data:null}
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'qweqwe' for key 'employee.username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }else{
//            return Result.error(MessageConstant.UNKNOWN_ERROR);//未知错误
            return Result.error(message);//错误信息
        }
    }

}
