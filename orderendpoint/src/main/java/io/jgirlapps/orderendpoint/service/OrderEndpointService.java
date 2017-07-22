package io.jgirlapps.orderendpoint.service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import io.jgirlapps.orderendpoint.models.Order;

import java.lang.reflect.Type;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;

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
		/*
		URI uri = utils.getServiceUrl("ORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/orderservice/" + orderId;
        */
		
		URI uri = utils.getServiceUrl("RECEIVEORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/receiveorder/" + orderId;
		
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", authorizationHeader);
        
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        
        ResponseEntity<Order> orderResult =
        restTemplate.exchange(url, HttpMethod.GET, requestEntity, Order.class);
        
		return orderResult;
		
	}
	
	@RequestMapping("order")
	public ResponseEntity putOrder(@RequestBody String orderJSON,
			                       @RequestHeader(value="Authorization") String authorizationHeader,
                                   Principal currentUser ) {
		
		LOG.info("OrderEndpointService: putOrder called");
		LOG.info("OrderEndpointService: User={}, Auth={}, called with orderId={}", currentUser.getName(), authorizationHeader, orderJSON);
		LOG.info("the authorizationHeader was {}",authorizationHeader);
		URI uri = utils.getServiceUrl("RECEIVEORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/receiveorder";
		
        /*
        Gson gson = new GsonBuilder()
        .registerTypeAdapter(java.util.Date.class, new JsonDeserializer<java.util.Date>() {
        			public java.util.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        				return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
        			}
        		})		
        .create();        
        
        Order order = gson.fromJson(orderJSON, Order.class);
        */
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", authorizationHeader);
        
        HttpEntity<String> request = new HttpEntity<String>(orderJSON, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
		return response;
	}

}
