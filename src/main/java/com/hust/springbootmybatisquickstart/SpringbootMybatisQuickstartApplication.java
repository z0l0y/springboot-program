package com.hust.springbootmybatisquickstart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:63342")
@ServletComponentScan//K开启了对Servlet组件的支持
@SpringBootApplication//启动类
@MapperScan("com.hust.springbootmybatisquickstart.mapper")
public class SpringbootMybatisQuickstartApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisQuickstartApplication.class, args);
    }

}
