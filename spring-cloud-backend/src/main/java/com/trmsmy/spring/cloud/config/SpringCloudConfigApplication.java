package com.trmsmy.spring.cloud.config;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudConfigApplication {

	@Bean
	public RequestDumperFilter requestDumperFilter() {
		return new RequestDumperFilter();
	}
	
	@Bean
	public HeaderPropagationFilter headerPropagationFilter() {
		return new HeaderPropagationFilter();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigApplication.class, args);
	}
}
