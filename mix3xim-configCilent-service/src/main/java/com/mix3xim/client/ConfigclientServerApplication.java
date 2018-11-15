package com.mix3xim.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConfigclientServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigclientServerApplication.class,args);
    }

    @Value("${usp.datasource.type}")
    String type;

    @RequestMapping(value = "/hi")
    public String hi(){
        return type;
    }
}
