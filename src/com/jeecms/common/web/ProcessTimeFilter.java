package com.jeecms.common.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 执行时间过滤器
 * 每次请求(包括服务器转发的请求)开始在request中放入_start_time这次请求开始时间
 * 请求结束后，debug输出这次请求的耗时时间
 */
public class ProcessTimeFilter implements Filter {
	protected final Logger log = LoggerFactory
			.getLogger(ProcessTimeFilter.class);
	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";

	public void destroy() {
	}

	/* 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		long time = System.currentTimeMillis();
		long startTime = time;
		request.setAttribute(START_TIME, time);
		chain.doFilter(request, response);
		time = System.currentTimeMillis() - time;
		long endTime = System.currentTimeMillis();
		log.error("process in {} ms: {}", time, request.getRequestURI());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss24");
		System.out.println("来源地址:"+ request.getHeader("Referer") +",开始时间:" + sdf.format(new Date(startTime)) + ",结束时间:" + sdf.format(new Date(endTime)) + ",耗时:" + time + "毫秒");
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
