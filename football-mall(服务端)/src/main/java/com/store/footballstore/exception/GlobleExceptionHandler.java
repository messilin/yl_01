package com.store.footballstore.exception;

import com.store.footballstore.entity.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author yuanlin
 * @create 2023/8/27 0027 18:19
 */
//@ControllerAdvice和@ExceptionHandler对各层的异常捕获
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public R exceptionHandler(HttpServletRequest httpServletRequest,Exception e){
        System.out.println("全局异常真无语");
        return R.error("服务端异常,请联系管理员"+e.getMessage());
    }
}
