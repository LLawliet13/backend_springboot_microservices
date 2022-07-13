package com.example.mock2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Mock2Application {

    public static void main(String[] args) {
        SpringApplication.run(Mock2Application.class, args);

    }

}
