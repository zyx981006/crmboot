package com.bjpowernode.crmboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bjpowernode.crmboot.demos.web")
public class CrmbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmbootApplication.class, args);
    }

}
