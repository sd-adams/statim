package io.jgirlapps.orderendpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


//http://blog.abhijitsarkar.org/technical/netflix-eureka/

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableResourceServer
public class OrderEndpointApplication {

	public static void main(String[] args) {
        SpringApplication.run(OrderEndpointApplication.class, args);
    }
}
