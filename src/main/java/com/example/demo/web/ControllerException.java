package com.example.demo.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuyf90 on 2018/12/18.
 */
@ControllerAdvice
@Controller
@RequestMapping(value = "/error")
public class ControllerException {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String httpRequestMethodNotSupportedException(HttpServletResponse response) {
        response.setStatus(200);
        return "405 method 方法不支持";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String httpRequestMethodAccessDeniedException(HttpServletResponse response) {
        response.setStatus(200);
        return "401";
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public String httpMediaTypeNotSupportedException(HttpServletResponse response) {
        response.setStatus(200);
        return "415不支持媒体类型";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundPage404(HttpServletResponse response) {
        response.setStatus(200);
        return "404 没有找到访问资源";
    }

    @RequestMapping(value = "/401", produces = {"application/json;charset=UTF-8"})
    public String forbidden401(HttpServletResponse response) {
        response.setStatus(200);
        System.out.println("401");
        return "401 没有访问权限";
    }

    @RequestMapping(value = "/403", produces = {"application/json;charset=UTF-8"})
    public String forbidden403(HttpServletResponse response) {
        response.setStatus(200);
        System.out.println("403");
        return "403没有访问权限";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String defaultException(HttpServletResponse response) {
        response.setStatus(200);
        return "500 网络异常";
    }

}
