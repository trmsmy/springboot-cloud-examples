package com.trmsmy.spring.cloud.config;

import javax.servlet.Filter;

import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigApplication {
	@Bean
	public FilterRegistrationBean requestDumperFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		Filter requestDumperFilter = new RequestDumperFilter();
		registration.setFilter(requestDumperFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigApplication.class, args);
	}
}
