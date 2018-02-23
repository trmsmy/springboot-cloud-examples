package com.trmsmy.spring.cloud.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class BackendServiceClient {
	
    private static final String SERVICE3_URL = "http://localhost:8888/service3";
	private static final String SERVICE2_URL = "http://localhost:8888/service2";
	private static final Logger logger = LoggerFactory.getLogger(BackendServiceClient.class);

	@Autowired
	private RestTemplate restTemplate;
	
    @HystrixCommand(groupKey = "GreetingService", commandKey = "getGreeting", fallbackMethod = "defaultGreeting",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),

                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            },
            commandProperties = {
                    
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "100"),
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                    
            }
            
            )
    String getGreeting() {
        return restTemplate.getForObject(SERVICE2_URL, String.class);
    }


    String defaultGreeting() {
    	
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        	logger.error("Exception while forced waiting", e);
        }
        return "Hi User"+(1000)+"!";
    }
    
    @HystrixCommand(groupKey = "GreetingService", commandKey = "getAdditionalGreeting", 
            fallbackMethod = "defaultAdditionalGreeting",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "101"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),

                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            },
            commandProperties = {
                    
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "100"),
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10")
            }
            )
    String getAdditionalGreeting() {
        return restTemplate.getForObject(SERVICE3_URL, String.class);
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
