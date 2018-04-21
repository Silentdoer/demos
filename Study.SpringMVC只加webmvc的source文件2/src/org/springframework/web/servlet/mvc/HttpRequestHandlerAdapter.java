/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Adapter to use the plain {@link HttpRequestHandler}
 * interface with the generic {@link org.springframework.web.servlet.DispatcherServlet}.
 * Supports handlers that implement the {@link LastModified} interface.
 *
 * <p>This is an SPI class, not used directly by application code.
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see org.springframework.web.servlet.DispatcherServlet
 * @see HttpRequestHandler
 * @see LastModified
 * @see SimpleControllerHandlerAdapter
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {

	// 这个是不能用注解的，而是继承自HttpRequestHandler，然后配置xml将它作为bean
	// 如果配置了driven，又配置了default-servlet-handler那么就需要配置这个HandlerAdapter
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof HttpRequestHandler);
	}

	// 对于DefaultServletHttpRequestHandler 而言，就直接将参数重新传给 default 这个servlet，调用其service方法
	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO 这句话其实就是将request的URL转换为aaa这个路径（应该是相对于servlet而言？），然后forward就是调用service一样
		// TODO 经过源码分析 以/ 开头则是相对于context，否则相对于servlet（类似AA.class.getResource("/")和没有/
		//request.getRequestDispatcher("/aaa").forward(req,resp);
		((HttpRequestHandler) handler).handleRequest(request, response);
		return null;
	}

	@Override
	public long getLastModified(HttpServletRequest request, Object handler) {
		if (handler instanceof LastModified) {
			return ((LastModified) handler).getLastModified(request);
		}
		return -1L;
	}

}
