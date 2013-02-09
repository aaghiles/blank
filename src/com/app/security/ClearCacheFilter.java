package com.app.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class ClearCacheFilter
 */
@WebFilter(urlPatterns = "/*")
public class ClearCacheFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		httpResponse.setHeader("Expires", "0");
		httpResponse.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		httpResponse.addHeader("Cache-Control", "post-check=0, pre-check=0");
		httpResponse.setHeader("Pragma", "no-cache");

		httpResponse.setCharacterEncoding("UTF-8");
		httpRequest.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}
