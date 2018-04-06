package com.trmsmy.spring.cloud.rest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.sleuth.instrument.web.TraceFilter;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import brave.propagation.ExtraFieldPropagation;



@Order(TraceFilter.ORDER - 1)
public class HeaderPropagationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) req;
		
		String br = httpRequest.getHeader("brequestId");
		String bg = httpRequest.getHeader("bgroupId");
		String r = httpRequest.getHeader("requestId");
		String g = httpRequest.getHeader("groupId");

		ExtraFieldPropagation.set("brequestId", br);
		ExtraFieldPropagation.set("bgroupId", bg);
		ExtraFieldPropagation.set("requestId", r);
		ExtraFieldPropagation.set("groupId", g);
		
		System.out.println("header[brequestId]:" + br);
		System.out.println("header[bgroupId]:" + bg);
		System.out.println("header[requestId]:" + r);
		System.out.println("header[groupId]:" + g);
		
		chain.doFilter(req, resp);
		
	}

	/*
	// Only modify requests that match this pattern
	private static final Pattern REQUEST_PATTERN = Pattern.compile("^/v1/workflow/(?<crumbId>[0-9a-f]+)$");
	// Key of the request attribute that TraceFilter searches for
	private static final String TRACE_REQUEST_ATTR = TraceFilter.class.getName() + ".TRACE";
	// Used to construct the span name
	private static final String SPAN_NAME_BASE = "http:/v1/workflow/";

	private final Random random = new Random(System.currentTimeMillis());

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String breadcrumbId = extractBreadcrumbId(httpRequest);
		if (breadcrumbId != null) {
			// Set up a span with this breadcrumb ID as the trace ID
			httpRequest.setAttribute(TRACE_REQUEST_ATTR, spanForId(breadcrumbId));
			chain.doFilter(httpRequest, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	*//**
	 * Returns the breadcrumb ID if a GET request matches the pattern, otherwise
	 * null.
	 *//*
	private String extractBreadcrumbId(final HttpServletRequest httpRequest) {
		return "92e9013d25cca084";
	}

	*//**
	 * Constructs a span for the specified trace ID with a random span ID.
	 *//*
	private Span spanForId(final String traceId) {
		return AutoValue_RealSpan.;
				
				
	}
*/}