package com.trmsmy.spring.cloud.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/setdelay2");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterAfter(siteMinderHeaderFilter(), AbstractPreAuthenticatedProcessingFilter.class)
		.authorizeRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
		.and().csrf().disable();
	}
	
	
	@Bean
	public RequestHeaderAuthenticationFilter siteMinderHeaderFilter() {
		RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setCheckForPrincipalChanges(true);

		return filter;
	}

	@Bean
	protected AuthenticationManager authenticationManager() {
		final List<AuthenticationProvider> providers = new ArrayList<>(1);
		providers.add(preauthAuthProvider());
		return new ProviderManager(providers);
	}

	@Bean
	public AuthenticationProvider preauthAuthProvider() {
		PreAuthenticatedAuthenticationProvider provider = new org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider();
		UserDetailsByNameServiceWrapper uds = new UserDetailsByNameServiceWrapper();
		uds.setUserDetailsService(passThroughUserDetailsService());
		provider.setPreAuthenticatedUserDetailsService(uds);

		return provider;
	}

	@Bean
	public UserDetailsService passThroughUserDetailsService() {
		return new PassThroughUserDetailsService();
	}
	
	@Bean
	public Http403ForbiddenEntryPoint entryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}

class PassThroughUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String ul = Optional.ofNullable(username).map(String::toLowerCase).orElse("");
		return new User(ul, "N/A", new ArrayList<GrantedAuthority>());
	}

}