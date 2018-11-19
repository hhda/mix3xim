package com.mix3xim.eureka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka2ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(Eureka2ServerApplication.class,args);
    }
}
