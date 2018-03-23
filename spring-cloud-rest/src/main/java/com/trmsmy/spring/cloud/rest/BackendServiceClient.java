package com.trmsmy.spring.cloud.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackendServiceClient {
	
    private static final String SERVICE3_URL = "/service3";
	private static final String SERVICE2_URL = "/service2";
	private static final Logger logger = LoggerFactory.getLogger(BackendServiceClient.class);

	@Value("${backend.service.uri}")
	private String backendHostPort;
	
	@Autowired
	private RestTemplate restTemplate;
	
    String getGreeting() {
        return restTemplate.getForObject(backendHostPort + SERVICE2_URL, String.class);
    }

    String defaultGreeting() {
    	
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        	logger.error("Exception while forced waiting", e);
        }
        return "Hi User"+(1000)+"!";
    }
    
    String getAdditionalGreeting() {
        return restTemplate.getForObject(backendHostPort + SERVICE3_URL, String.class);
    }
    
    String defaultAdditionalGreeting() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        	logger.error("failed at forced wait for additioanl greeting", e);
        }
        return "Good Day("+1000+")!";
        
    }

}
