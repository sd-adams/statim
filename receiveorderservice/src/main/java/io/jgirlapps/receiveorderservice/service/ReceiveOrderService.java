package io.jgirlapps.receiveorderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import io.jgirlapps.receiveorderservice.models.Order;

import java.lang.reflect.Type;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
public class ReceiveOrderService {
	private static final Logger LOG = LoggerFactory.getLogger(ReceiveOrderService.class);
	
	@Autowired
	Utils utils;
	
	private RestTemplate restTemplate = new RestTemplate();

	
    @RequestMapping("/receiveorder/{orderId}")
    public ResponseEntity<String> handleOrder(@PathVariable("orderId") int orderId,
    		                                  @RequestHeader(value="Authorization") String authorizationHeader,
                                              Principal currentUser) {
    	//persist order
    	LOG.info("RecieveOrderService called with order {}", orderId);
		URI uri = utils.getServiceUrl("ORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/orderservice/"  + orderId;
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", authorizationHeader);
		
        HttpEntity<String> request = new HttpEntity<String>("", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
   	
    	//publish order
       
        

        return response;
    }
    
	
	/*
	@RequestMapping("/receiveorder")
    public ResponseEntity<String> handleOrder(@RequestBody String orderJSON) {
    	//persist order
    	LOG.info("RecieveOrderService called with order {}", orderJSON);
		URI uri = utils.getServiceUrl("ORDERSERVICE", "http://localhost:8081/");
        String url = uri.toString() + "/orderservice/";
		
        HttpEntity<String> request = new HttpEntity<String>(orderJSON);
        URI location = restTemplate.postForLocation(url, request);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        
        
   	
    	//publish order
       
        

        return response;
    }
    */
	
}
