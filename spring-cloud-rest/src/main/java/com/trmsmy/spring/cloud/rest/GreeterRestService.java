package com.trmsmy.spring.cloud.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@Component
@RestController
public class GreeterRestService {

    private static final Logger logger = LoggerFactory.getLogger(GreeterRestService.class);
	
    @Value("${greeter.name}")
    String greeter;

    @Value("${message.greeting.template}")
    String messageTemplate;

    @Autowired
    BackendServiceClient serviceClient;

    @Value("${spring.application.name}")
	private String appName;
    
    @RequestMapping(value = "/ping", produces = "text/plain")
    public String ping() {
    	logger.info("I am in Ping Service");
    	
    	return "Ping Success, app " + appName + " is up and running." ; 
    	
    }
    
    

    @RequestMapping(value = "/echo", produces = "text/plain")
    public String echo() {

    	logger.info("Reached Greeter Service in [Service1], About to call Service2");
    	
        String greet = serviceClient.getGreeting();
        
    	logger.info("Got response from Service2 [{}], About to call Service3", greet);
        
        String addtnGreet = serviceClient.getAdditionalGreeting();
        return greet + ", " + addtnGreet;
    }

    /*
     * @RequestMapping(value = "/setdelay", produces = "text/plain") public void
     * setDelay(@RequestParam("delay") String delay) { this.delay = NumberUtils.parseNumber(delay,
     * Integer.class); }
     */
}
