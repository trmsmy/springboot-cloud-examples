package com.trmsmy.spring.cloud.rest;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import brave.propagation.Propagation.Factory;
import brave.spring.beans.TracingFactoryBean;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudRestApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	

	@Bean
	public Factory propagationFactory() {
		return brave.propagation.ExtraFieldPropagation.newFactory(brave.propagation.B3Propagation.FACTORY,
				"x-vcap-request-id", "x-vcap-group-id");
	}

	@Bean
	public TracingFactoryBean tracing() {
		return new TracingFactoryBean();
	}
	
	@Bean
	public FilterRegistrationBean requestDumperFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		Filter requestDumperFilter = new RequestDumperFilter();
		registration.setFilter(requestDumperFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudRestApplication.class, args);
	}
}
