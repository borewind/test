package com.zxtechai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//在SpringBootTest后加上：webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,意思是创建Web应用程序上下文（基于响应或基于servlet）
//原因:websocket是需要依赖tomcat等容器的启动。所以在测试过程中我们要真正的启动一个tomcat作为容器
class ServerApplicationTest {
    @Test
    public void test1(){
        System.out.println("test1");
    }
  
}