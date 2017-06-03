package io.jgirlapps.orderservice.service;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jgirlapps.orderservice.models.AddressType;
import io.jgirlapps.orderservice.models.IncentiveEnumType;
import io.jgirlapps.orderservice.models.Order;
import io.jgirlapps.orderservice.models.OrderStatusEnumType;
import io.jgirlapps.orderservice.models.ResourceEnumType;
import io.jgirlapps.orderservice.models.SpecialtyEnumType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class OrderService {
	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
	
	@RequestMapping("/")
	public ResponseEntity<String> ping() {
		
		LOG.info("in the ping service");
		
        ResponseEntity<String> pingResult = 
        		new ResponseEntity<>("here",HttpStatus.OK);
		
		return pingResult;
		
	}
	
	@RequestMapping("/orderservice/{orderId}")
	public ResponseEntity<Order> getOrder(@PathVariable int orderId,
										  @RequestHeader(value="Authorization") String authorizationHeader,
										  Principal currentUser) {
		
        ResponseEntity<Order> orderResult = 
        		new ResponseEntity<>(getOrder(),HttpStatus.OK);
		
		return orderResult;
		
	}
	
	@RequestMapping("/orderservice")
	public ResponseEntity<String> putOrder(@RequestBody String orderJSON,
										   @RequestHeader(value="Authorization") String authorizationHeader,
										   Principal currentUser) {
		
		LOG.info("orderservice has been called");
		
		String orderId = UUID.randomUUID().toString();
		
		ResponseEntity<String> status = 
				new ResponseEntity<String>(orderId, HttpStatus.OK);
		
		return status;
	}
	
	private Order getOrder() {
		Order order = new Order();
		
		AddressType address = new AddressType();
		address.setCity("Hurst");
		address.setState("TX");
		address.setZip("76053");
		order.setAddress(address);
		
		order.setAgencyId(UUID.randomUUID().toString());
		order.setAgencyRatingId(UUID.randomUUID().toString());
		order.setDateRequired(new Date());
		order.setId(UUID.randomUUID().toString());
		
		List<IncentiveEnumType> incentives = new ArrayList();
		IncentiveEnumType incentive = IncentiveEnumType.BONUS100;
		incentives.add(incentive);
		
		order.setIncentives(incentives);
		
		order.setOrderStatus(OrderStatusEnumType.ASSIGNED);
		order.setReporterId(UUID.randomUUID().toString());
		order.setReporterRatingId(UUID.randomUUID().toString());
		order.setResourceType(ResourceEnumType.COURTREPORTER);
		order.setSpecialRequirements("this is really important");
		order.setSpecialtyType(SpecialtyEnumType.STANDARD);
		
		return order;
	}

}
