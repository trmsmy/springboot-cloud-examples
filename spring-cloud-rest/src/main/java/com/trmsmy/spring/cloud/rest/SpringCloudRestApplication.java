package com.trmsmy.spring.cloud.rest;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import brave.propagation.Propagation.Factory;
import brave.spring.beans.TracingFactoryBean;

@SpringBootApplication
// @EnableHystrixDashboard
public class SpringCloudRestApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public HystrixCommandAspect hystrixAspect() {
		return new HystrixCommandAspect();
	}

	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream/*");
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
