package jgirlapps.io.orderendpoint.service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import jgirlapps.io.orderendpoint.models.Order;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OrderEndpointService {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderEndpointService.class);
	
	@Autowired
	Utils utils;
	
	private RestTemplate restTemplate = new RestTemplate();
	Gson gson = new Gson();
	
	
	@RequestMapping("order/{orderId}")
	public ResponseEntity getOrder(@PathVariable("orderId") int orderId,
			                       @RequestHeader(value="Authorization") String authorizationHeader,
			                       Principal currentUser) {
		
		LOG.info("OrderEndpointService: User={}, Auth={}, called with orderId={}", currentUser.getName(), authorizationHeader, orderId);
		URI uri = utils.getServiceUrl("ORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/orderservice/" + orderId;
		
        
        ResponseEntity<Order> orderResult = restTemplate.getForEntity(url, Order.class);
		
		return orderResult;
		
	}

}
