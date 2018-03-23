package com.trmsmy.spring.cloud.rest;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.Propagation.Factory;

@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class SpringCloudRestApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RequestDumperFilter requestDumperFilter() {
		return new RequestDumperFilter();
	}
	
	@Bean
	public Factory propagationFactory() {
		return ExtraFieldPropagation.newFactory(B3Propagation.FACTORY,
				"brequestId", "bgroupId", "requestId", "groupId");
	}

	@Bean
	public HeaderPropagationFilter headerPropagationFilter() {
		return new HeaderPropagationFilter();
	}

	public static void main(String[] args) {
		
		SpringApplication.run(SpringCloudRestApplication.class, args);
	}
	
	
}
