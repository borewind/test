package com.zxtechai.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
//错误页面配置
@Configuration
public class ErrorConfiguration implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[4];
        // 添加错误页面进行映射 当404时会跳转到/404的请求
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND,"/404");
        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        errorPages[2] = new ErrorPage(HttpStatus.BAD_REQUEST,"/400");
        errorPages[3] = new ErrorPage(HttpStatus.UNAUTHORIZED,"/401");
        registry.addErrorPages(errorPages);
    }
}
