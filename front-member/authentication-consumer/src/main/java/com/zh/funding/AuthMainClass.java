package com.zh.funding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthMainClass {
    public static void main(String[] args) {
        SpringApplication.run(AuthMainClass.class,args);
    }
}
