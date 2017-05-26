package jgirlapps.io.orderendpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//http://blog.abhijitsarkar.org/technical/netflix-eureka/

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class OrderEndpointApplication {

	public static void main(String[] args) {
        SpringApplication.run(OrderEndpointApplication.class, args);
    }
}
