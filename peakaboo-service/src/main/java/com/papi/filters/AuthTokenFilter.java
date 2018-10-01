//package com.papi.filters;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//@WebFilter("/*")
//public class AuthTokenFilter implements Filter {
//
//	public class AuthTokenFilter 
//
//	    @Override
//	    public void destroy() {
//	        // ...
//	    }
//
//	    @Override
//	    public void init(FilterConfig filterConfig) throws ServletException {
//	        //
//	    }
//
//	    @Override
//	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//	        String xHeader = ((HttpServletRequest)request).getHeader("X-Auth-Token");
//	        if(getPermission(xHeader)) {
//	            chain.doFilter(request, response);
//	        } else {
//	            request.getRequestDispatcher("401.html").forward(request, response);
//	        }
//	    }
//	}