package com.zxtechai.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Controller
@EnableConfigurationProperties({ServerProperties.class})
public class ErrorPagesController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    private final ServerProperties serverProperties;

    public ErrorPagesController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        this.errorAttributes=errorAttributes;
        this.serverProperties=serverProperties;
    }

    @RequestMapping("/404")
    public ModelAndView errorHtml404(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        Map<String, Object> model = getErrorAttributes(webRequest, getErrorAttributeOptions());
        model.put("queryString", request.getQueryString());
        log.info("model:{}",model);
        ModelAndView modelAndView = new ModelAndView("error/404", model);
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"zhangsan","lisi","wangwu");
        modelAndView.addObject("lists",list);
        modelAndView.addObject("queryString",request.getQueryString());
        modelAndView.addObject("model",model);
        modelAndView.addObject("HttpStatus",HttpStatus.NOT_FOUND.value());
        modelAndView.addObject("test","测试文字");
        return modelAndView;
    }

    @RequestMapping("/403")
    public ModelAndView errorHtml403(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Map<String, Object> model = getErrorAttributes(webRequest, getErrorAttributeOptions());
        model.put("queryString", request.getQueryString());
        if (!String.valueOf(model.get("path")).contains(".")) {
            model.put("status", HttpStatus.FORBIDDEN.value());
        }
        return new ModelAndView("error/403", model);
    }

    @RequestMapping("/400")
    public ModelAndView errorHtml400(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        Map<String, Object> model = getErrorAttributes(webRequest, getErrorAttributeOptions());
        model.put("queryString", request.getQueryString());
        return new ModelAndView("error/400", model);
    }

    @RequestMapping("/401")
    public ModelAndView errorHtml401(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> model = getErrorAttributes(webRequest, getErrorAttributeOptions());
        model.put("queryString", request.getQueryString());
        return new ModelAndView("error/401", model);
    }

    @RequestMapping("/500")
    public ModelAndView errorHtml500(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Map<String, Object> model = getErrorAttributes(webRequest, getErrorAttributeOptions());
        model.put("queryString", request.getQueryString());
        return new ModelAndView("error/500", model);
    }

    /**
     *
     * @return ErrorAttributeOptions
     */
    protected ErrorAttributeOptions getErrorAttributeOptions() {
        Set<ErrorAttributeOptions.Include> includes = new HashSet<>();
        ErrorProperties error = this.serverProperties.getError();
        if (error.getIncludeStacktrace() == ErrorProperties.IncludeAttribute.ALWAYS) {
            includes.add(ErrorAttributeOptions.Include.STACK_TRACE);
        }
        if(error.getIncludeMessage() == ErrorProperties.IncludeAttribute.ALWAYS){
            includes.add(ErrorAttributeOptions.Include.MESSAGE);
        }
        if(error.isIncludeException()){
            includes.add(ErrorAttributeOptions.Include.EXCEPTION);
        }
        if(error.getIncludeBindingErrors() == ErrorProperties.IncludeAttribute.ALWAYS){
            includes.add(ErrorAttributeOptions.Include.BINDING_ERRORS);
        }
        return ErrorAttributeOptions.of(includes);
    }

    /**
     * 获取错误的信息
     *
     * @param webRequest
     *      web请求
     * @param options
     *      错误参数选项
     * @return  Map 错误参数
     */
    private Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        return this.errorAttributes.getErrorAttributes(webRequest, options);
    }
}

