package jgirlapps.io.receiveorderservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jgirlapps.io.receiveorderservice.models.Order;

import java.util.Date;
import java.util.List;

@RestController
public class ReceiveOrderService {
	private static final Logger LOG = LoggerFactory.getLogger(ReceiveOrderService.class);

    @Autowired
    ReceiveOrderIntegration integration;

    @Autowired
    Util util;

    @RequestMapping("/")
    public String getOrder() {
        return "{\"timestamp\":\"" + new Date() + "\",\"content\":\"Hello from ReceiveOrderService\"}";
    }

    @RequestMapping("/order")
    public ResponseEntity<String> putOrder(String orderJson) {
    	Gson gson = new Gson();
    	Order order = gson.fromJson(orderJson, Order.class);
       
        ResponseEntity<String> result = integration.putOrder(order);

        if (!result.getStatusCode().is2xxSuccessful()) {
            // We can't proceed, return whatever fault we got from the getProduct call
            return util.createResponse(null, result.getStatusCode());
        }

        return util.createOkResponse("Ok");
    }

}
