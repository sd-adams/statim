package io.jgirlapps.receiveorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;



@SpringBootApplication
@EnableDiscoveryClient
@EnableResourceServer
public class ReceiveOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReceiveOrderServiceApplication.class, args);
    }
}
