package com.zh.funding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients //不加会报错
@EnableDiscoveryClient
@SpringBootApplication
public class AuthMainClass {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainClass.class,args);
    }
}
