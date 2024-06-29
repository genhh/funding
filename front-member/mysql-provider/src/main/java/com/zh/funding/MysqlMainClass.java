package com.zh.funding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zh.funding.mapper")
@SpringBootApplication
public class MysqlMainClass {
    public static void main(String[] args) {
        SpringApplication.run(MysqlMainClass.class, args);
    }
}
