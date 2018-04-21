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

package org.springframework.web.servlet.config;

import java.util.Map;

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

/**
 * {@link BeanDefinitionParser} that parses a {@code default-servlet-handler} element to
 * register a {@link DefaultServletHttpRequestHandler}.  Will also register a
 * {@link SimpleUrlHandlerMapping} for mapping resource requests, and a
 * {@link HttpRequestHandlerAdapter}.
 *
 * @author Jeremy Grelle
 * @author Rossen Stoyanchev
 * @since 3.0.4
 */
// TODO <mvc:default-servlet-handler
class DefaultServletHandlerBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);

		String defaultServletName = element.getAttribute("default-servlet-name");
		RootBeanDefinition defaultServletHandlerDef = new RootBeanDefinition(DefaultServletHttpRequestHandler.class);
		defaultServletHandlerDef.setSource(source);
		defaultServletHandlerDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		if (StringUtils.hasText(defaultServletName)) {
			defaultServletHandlerDef.getPropertyValues().add("defaultServletName", defaultServletName);
		}
		String defaultServletHandlerName = parserContext.getReaderContext().generateBeanName(defaultServletHandlerDef);

		// TODO 将DefaultServletHttpRequestHandler 注册到了beanFactory里（这个东西类似HandlerMethod ？？，实现HttpRequestHandler接口）
		parserContext.getRegistry().registerBeanDefinition(defaultServletHandlerName, defaultServletHandlerDef);
		parserContext.registerComponent(new BeanComponentDefinition(defaultServletHandlerDef, defaultServletHandlerName));

		Map<String, String> urlMap = new ManagedMap<String, String>();

		// TODO 原来如此，defaultServletHandlerName 注册到HandlerMapping里，如果RequestMappingHandlerMapping找不到（或没配）则由
		// TODO SimpleUrlHandlerMapping去 找匹配，发现路径匹配 /** ，然后找出对应的对照（比如/res/），把这个作为参数调用handler？
		urlMap.put("/**", defaultServletHandlerName);  // TODO 这里的 / 应该也是相对于 context 而言的？？

		// TODO 重要，这个是 HandlerMapping
		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(SimpleUrlHandlerMapping.class);
		handlerMappingDef.setSource(source);
		handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		// TODO 注意urlMap
		handlerMappingDef.getPropertyValues().add("urlMap", urlMap);

		String handlerMappingBeanName = parserContext.getReaderContext().generateBeanName(handlerMappingDef);

		// TODO 也注册进去了（如果重复注册但是beanName相同且role相同那么后者覆盖前者）
		parserContext.getRegistry().registerBeanDefinition(handlerMappingBeanName, handlerMappingDef);
		parserContext.registerComponent(new BeanComponentDefinition(handlerMappingDef, handlerMappingBeanName));
		//org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
		// TODO 是不是少了个 HandlerAdapter ？？没少，下面那句就会注册了 （如果有annotation-driven那么需要
		// TODO 自己注册org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter，否则默认策略会自动注册这个，这个应该就是HandlerAdapter
		// Ensure BeanNameUrlHandlerMapping (SPR-8289) and default HandlerAdapters are not "turned off"
		// TODO 这里会注册 HttpRequestHandlerAdapter
		MvcNamespaceUtils.registerDefaultComponents(parserContext, source);

		return null;
	}

}
