package com.trmsmy.spring.cloud.config;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class BandendRestService {

    private static final Logger logger = LoggerFactory.getLogger(BandendRestService.class);
	
    @Value("${greeter.name}")
    String greeter;

    @Value("${message.greeting.template}")
    String messageTemplate;

    private int delayFor2;
    private int delayFor3;

    @RequestMapping(value = "/", produces = "application/json")
    public List<String> index() {

        String msg = MessageFormat.format(messageTemplate, greeter);
        return Arrays.asList(msg);
    }


    @RequestMapping(value = "/service2", produces = "text/plain")
    public String echo() {

    	logger.info("In Service 2, about to wait for set deplay={} ", delayFor2);
    	
        try {
            Thread.sleep(delayFor2);
        } catch (Exception e) {
            logger.error("thread sleep failed" , e );
        }

        return MessageFormat.format(messageTemplate, greeter) + "(" + delayFor2 + ")";
    }

    @RequestMapping(value = "/service3", produces = "text/plain")
    public String echo2() {

    	logger.info("In Service 3, about to wait for set deplay={} ", delayFor3);
    	
        try {
            Thread.sleep(delayFor3);
        } catch (Exception e) {
            logger.error("thread sleep failed" , e );
        }

        return "Happy Monday("+delayFor3+")!!";
    }

    @RequestMapping(value = "/setdelay2", produces = "text/plain")
    public void setDelay(@RequestParam("delay") String delay) {
        this.delayFor2 = NumberUtils.parseNumber(delay, Integer.class);
    }

    @RequestMapping(value = "/setdelay3", produces = "text/plain")
    public void setDelay2(@RequestParam("delay") String delay) {
        this.delayFor3 = NumberUtils.parseNumber(delay, Integer.class);
    }
}
