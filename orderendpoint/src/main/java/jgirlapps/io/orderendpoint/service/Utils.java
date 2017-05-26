package jgirlapps.io.orderendpoint.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Utils {
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
	
	@Autowired
    private LoadBalancerClient loadBalancer;
	
	public URI getServiceUrl(String serviceId, String fallbackUri) {
		
        URI uri = null;
        try {
            ServiceInstance instance = loadBalancer.choose(serviceId);
            uri = instance.getUri();
            LOG.info("Resolved serviceId '{}' to URL '{}'.", serviceId, uri);

        } catch (RuntimeException e) {
            // Eureka not available, use fallback
            uri = URI.create(fallbackUri);
            LOG.info("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", serviceId, uri);
            LOG.info(e.getMessage());
        }

        return uri;
    }

}
