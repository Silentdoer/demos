/*
 * Copyright 2002-2016 the original author or authors.
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

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.portlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.w3c.dom.Element;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.Ordered;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.resource.CachingResourceResolver;
import org.springframework.web.servlet.resource.CachingResourceTransformer;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.FixedVersionStrategy;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceTransformer;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.ResourceUrlProviderExposingInterceptor;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

/**
 * {@link BeanDefinitionParser} that parses a
 * {@code resources} element to register a {@link ResourceHttpRequestHandler} and
 * register a {@link SimpleUrlHandlerMapping} for mapping resource requests,
 * and a {@link HttpRequestHandlerAdapter}. Will also create a resource handling
 * chain with {@link ResourceResolver}s and {@link ResourceTransformer}s.
 *
 * @author Keith Donald
 * @author Jeremy Grelle
 * @author Brian Clozel
 * @since 3.0.4
 */
// TODO <mvc resources mapping="" location=""
class ResourcesBeanDefinitionParser implements BeanDefinitionParser {

	private static final String RESOURCE_CHAIN_CACHE = "spring-resource-chain-cache";

	private static final String VERSION_RESOLVER_ELEMENT = "version-resolver";

	private static final String VERSION_STRATEGY_ELEMENT = "version-strategy";

	private static final String FIXED_VERSION_STRATEGY_ELEMENT = "fixed-version-strategy";

	private static final String CONTENT_VERSION_STRATEGY_ELEMENT = "content-version-strategy";

	private static final String RESOURCE_URL_PROVIDER = "mvcResourceUrlProvider";

	private static final boolean isWebJarsAssetLocatorPresent = ClassUtils.isPresent(
			"org.webjars.WebJarAssetLocator", ResourcesBeanDefinitionParser.class.getClassLoader());


	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		Object source = parserContext.extractSource(element);

		registerUrlProvider(parserContext, source);

		//TODO 为resources 生成 HttpRequestHandler bean，并为其处理location ，然后返回beanName
		String resourceHandlerName = registerResourceHandler(parserContext, element, source);
		if (resourceHandlerName == null) {
			return null;
		}

		Map<String, String> urlMap = new ManagedMap<String, String>();
		String resourceRequestPath = element.getAttribute("mapping");
		if (!StringUtils.hasText(resourceRequestPath)) {
			parserContext.getReaderContext().error("The 'mapping' attribute is required.", parserContext.extractSource(element));
			return null;
		}
		// TODO resourceRequestPath 的值 就是如 /static/bb/**
		// TODO 原来如此，是由SimpleUrlHandlerMapping 通过 请求的URL 匹配到一个 HttpRequestHandler 对象
		// 再由这个对象 通过 如/static/**来找到 所有的location，然后通过后面的具体文件 去所有location里找
		urlMap.put(resourceRequestPath, resourceHandlerName);

		RuntimeBeanReference pathMatcherRef = MvcNamespaceUtils.registerPathMatcher(null, parserContext, source);
		RuntimeBeanReference pathHelperRef = MvcNamespaceUtils.registerUrlPathHelper(null, parserContext, source);

		// TODO 还要再次看看 重复 注册 BeanDefinition 的情况。（这里应该会有特殊处理吧，不可能说生成多个 同样的 HandlerMapping对象
		//FIXME 这一块可以改进
		RootBeanDefinition handlerMappingDef = new RootBeanDefinition(SimpleUrlHandlerMapping.class);
		handlerMappingDef.setSource(source);
		handlerMappingDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		handlerMappingDef.getPropertyValues().add("urlMap", urlMap);
		handlerMappingDef.getPropertyValues().add("pathMatcher", pathMatcherRef).add("urlPathHelper", pathHelperRef);

		String order = element.getAttribute("order");
		// Use a default of near-lowest precedence, still allowing for even lower precedence in other mappings（max值-1）
		handlerMappingDef.getPropertyValues().add("order", StringUtils.hasText(order) ? order : Ordered.LOWEST_PRECEDENCE - 1);

		RuntimeBeanReference corsConfigurationsRef = MvcNamespaceUtils.registerCorsConfigurations(null, parserContext, source);
		handlerMappingDef.getPropertyValues().add("corsConfigurations", corsConfigurationsRef);

		String beanName = parserContext.getReaderContext().generateBeanName(handlerMappingDef);

		// TODO 看一下这里对重复注册BeanDefinition是怎么处理的。
		parserContext.getRegistry().registerBeanDefinition(beanName, handlerMappingDef);
		parserContext.registerComponent(new BeanComponentDefinition(handlerMappingDef, beanName));

		// Ensure BeanNameUrlHandlerMapping (SPR-8289) and default HandlerAdapters are not "turned off"
		// Register HttpRequestHandlerAdapter
		// TODO 注意，这里会注册 HttpRequestHandlerAdapter 重要，之前一直以为没有注册（但会有判断不会注册多个）
		MvcNamespaceUtils.registerDefaultComponents(parserContext, source);

		return null;
	}

	private void registerUrlProvider(ParserContext parserContext, Object source) {
		if (!parserContext.getRegistry().containsBeanDefinition(RESOURCE_URL_PROVIDER)) {
			// TODO 这个应该是将 比如 /static/mm/** 转换 为 /res/的工具？？
			RootBeanDefinition urlProvider = new RootBeanDefinition(ResourceUrlProvider.class);
			urlProvider.setSource(source);
			urlProvider.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			// TODO 存入beanFactory，beanName：mvcResourceUrlProvider
			parserContext.getRegistry().registerBeanDefinition(RESOURCE_URL_PROVIDER, urlProvider);
			parserContext.registerComponent(new BeanComponentDefinition(urlProvider, RESOURCE_URL_PROVIDER));

			RootBeanDefinition interceptor = new RootBeanDefinition(ResourceUrlProviderExposingInterceptor.class);
			interceptor.setSource(source);
			interceptor.getConstructorArgumentValues().addIndexedArgumentValue(0, urlProvider);

			RootBeanDefinition mappedInterceptor = new RootBeanDefinition(MappedInterceptor.class);
			mappedInterceptor.setSource(source);
			mappedInterceptor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			mappedInterceptor.getConstructorArgumentValues().addIndexedArgumentValue(0, (Object) null);
			mappedInterceptor.getConstructorArgumentValues().addIndexedArgumentValue(1, interceptor);
			String mappedInterceptorName = parserContext.getReaderContext().registerWithGeneratedName(mappedInterceptor);
			parserContext.registerComponent(new BeanComponentDefinition(mappedInterceptor, mappedInterceptorName));
		}
	}

	private String registerResourceHandler(ParserContext parserContext, Element element, Object source) {
		String locationAttr = element.getAttribute("location");
		if (!StringUtils.hasText(locationAttr)) {
			parserContext.getReaderContext().error("The 'location' attribute is required.", parserContext.extractSource(element));
			return null;
		}

		ManagedList<String> locations = new ManagedList<String>();
		// TODO 可见location="/aaa/,/mmm/" 是可以的（不要有空格）
		locations.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(locationAttr)));

		// TODO 这个是和 DefaultServletHttpRequestHandler 继承同一个 HttpRequestHandler 基类，由SimpleUrlHandlerMapping查找
		// TODO 也就是说每一个 <mvc:resources 都对应 一个ResourceHttpRequestHandler 对象
		// TODO 注意没有实现 Ordered 那么是通过 ResourceUrlProvider 来精确匹配的吗？
		RootBeanDefinition resourceHandlerDef = new RootBeanDefinition(ResourceHttpRequestHandler.class);
		resourceHandlerDef.setSource(source);
		resourceHandlerDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

		MutablePropertyValues values = resourceHandlerDef.getPropertyValues();
		values.add("locations", locations);

		String cacheSeconds = element.getAttribute("cache-period");
		if (StringUtils.hasText(cacheSeconds)) {
			values.add("cacheSeconds", cacheSeconds);
		}

		Element cacheControlElement = DomUtils.getChildElementByTagName(element, "cache-control");
		if (cacheControlElement != null) {
			CacheControl cacheControl = parseCacheControl(cacheControlElement);
			values.add("cacheControl", cacheControl);
		}

		Element resourceChainElement = DomUtils.getChildElementByTagName(element, "resource-chain");
		if (resourceChainElement != null) {
			parseResourceChain(resourceHandlerDef, parserContext, resourceChainElement, source);
		}

		Object manager = MvcNamespaceUtils.getContentNegotiationManager(parserContext);
		if (manager != null) {
			values.add("contentNegotiationManager", manager);
		}

		String beanName = parserContext.getReaderContext().generateBeanName(resourceHandlerDef);

		// TODO 每个HttpRequestHandler 对象都会注册到 beanFactory里（这点和HandlerMethod不一样）
		parserContext.getRegistry().registerBeanDefinition(beanName, resourceHandlerDef);
		parserContext.registerComponent(new BeanComponentDefinition(resourceHandlerDef, beanName));
		return beanName;
	}


	private void parseResourceChain(RootBeanDefinition resourceHandlerDef, ParserContext parserContext,
			Element element, Object source) {

		String autoRegistration = element.getAttribute("auto-registration");
		boolean isAutoRegistration = !(StringUtils.hasText(autoRegistration) && "false".equals(autoRegistration));

		ManagedList<? super Object> resourceResolvers = new ManagedList<Object>();
		resourceResolvers.setSource(source);
		ManagedList<? super Object> resourceTransformers = new ManagedList<Object>();
		resourceTransformers.setSource(source);

		parseResourceCache(resourceResolvers, resourceTransformers, element, source);
		parseResourceResolversTransformers(isAutoRegistration, resourceResolvers, resourceTransformers,
				parserContext, element, source);

		if (!resourceResolvers.isEmpty()) {
			resourceHandlerDef.getPropertyValues().add("resourceResolvers", resourceResolvers);
		}
		if (!resourceTransformers.isEmpty()) {
			resourceHandlerDef.getPropertyValues().add("resourceTransformers", resourceTransformers);
		}
	}

	private CacheControl parseCacheControl(Element element) {
		CacheControl cacheControl = CacheControl.empty();
		if ("true".equals(element.getAttribute("no-cache"))) {
			cacheControl = CacheControl.noCache();
		}
		else if ("true".equals(element.getAttribute("no-store"))) {
			cacheControl = CacheControl.noStore();
		}
		else if (element.hasAttribute("max-age")) {
			cacheControl = CacheControl.maxAge(Long.parseLong(element.getAttribute("max-age")), TimeUnit.SECONDS);
		}
		if ("true".equals(element.getAttribute("must-revalidate"))) {
			cacheControl = cacheControl.mustRevalidate();
		}
		if ("true".equals(element.getAttribute("no-transform"))) {
			cacheControl = cacheControl.noTransform();
		}
		if ("true".equals(element.getAttribute("cache-public"))) {
			cacheControl = cacheControl.cachePublic();
		}
		if ("true".equals(element.getAttribute("cache-private"))) {
			cacheControl = cacheControl.cachePrivate();
		}
		if ("true".equals(element.getAttribute("proxy-revalidate"))) {
			cacheControl = cacheControl.proxyRevalidate();
		}
		if (element.hasAttribute("s-maxage")) {
			cacheControl = cacheControl.sMaxAge(Long.parseLong(element.getAttribute("s-maxage")), TimeUnit.SECONDS);
		}
		if (element.hasAttribute("stale-while-revalidate")) {
			cacheControl = cacheControl.staleWhileRevalidate(
					Long.parseLong(element.getAttribute("stale-while-revalidate")), TimeUnit.SECONDS);
		}
		if (element.hasAttribute("stale-if-error")) {
			cacheControl = cacheControl.staleIfError(
					Long.parseLong(element.getAttribute("stale-if-error")), TimeUnit.SECONDS);
		}
		return cacheControl;
	}

	private void parseResourceCache(ManagedList<? super Object> resourceResolvers,
			ManagedList<? super Object> resourceTransformers, Element element, Object source) {

		String resourceCache = element.getAttribute("resource-cache");
		if ("true".equals(resourceCache)) {
			ConstructorArgumentValues cavs = new ConstructorArgumentValues();

			RootBeanDefinition cachingResolverDef = new RootBeanDefinition(CachingResourceResolver.class);
			cachingResolverDef.setSource(source);
			cachingResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			cachingResolverDef.setConstructorArgumentValues(cavs);

			RootBeanDefinition cachingTransformerDef = new RootBeanDefinition(CachingResourceTransformer.class);
			cachingTransformerDef.setSource(source);
			cachingTransformerDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			cachingTransformerDef.setConstructorArgumentValues(cavs);

			String cacheManagerName = element.getAttribute("cache-manager");
			String cacheName = element.getAttribute("cache-name");
			if (StringUtils.hasText(cacheManagerName) && StringUtils.hasText(cacheName)) {
				RuntimeBeanReference cacheManagerRef = new RuntimeBeanReference(cacheManagerName);
				cavs.addIndexedArgumentValue(0, cacheManagerRef);
				cavs.addIndexedArgumentValue(1, cacheName);
			}
			else {
				ConstructorArgumentValues cacheCavs = new ConstructorArgumentValues();
				cacheCavs.addIndexedArgumentValue(0, RESOURCE_CHAIN_CACHE);
				RootBeanDefinition cacheDef = new RootBeanDefinition(ConcurrentMapCache.class);
				cacheDef.setSource(source);
				cacheDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				cacheDef.setConstructorArgumentValues(cacheCavs);
				cavs.addIndexedArgumentValue(0, cacheDef);
			}
			resourceResolvers.add(cachingResolverDef);
			resourceTransformers.add(cachingTransformerDef);
		}
	}


	private void parseResourceResolversTransformers(boolean isAutoRegistration,
			ManagedList<? super Object> resourceResolvers, ManagedList<? super Object> resourceTransformers,
			ParserContext parserContext, Element element, Object source) {

		Element resolversElement = DomUtils.getChildElementByTagName(element, "resolvers");
		if (resolversElement != null) {
			for (Element beanElement : DomUtils.getChildElements(resolversElement)) {
				if (VERSION_RESOLVER_ELEMENT.equals(beanElement.getLocalName())) {
					RootBeanDefinition versionResolverDef = parseVersionResolver(parserContext, beanElement, source);
					versionResolverDef.setSource(source);
					resourceResolvers.add(versionResolverDef);
					if (isAutoRegistration) {
						RootBeanDefinition cssLinkTransformerDef = new RootBeanDefinition(CssLinkResourceTransformer.class);
						cssLinkTransformerDef.setSource(source);
						cssLinkTransformerDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
						resourceTransformers.add(cssLinkTransformerDef);
					}
				}
				else {
					Object object = parserContext.getDelegate().parsePropertySubElement(beanElement, null);
					resourceResolvers.add(object);
				}
			}
		}

		if (isAutoRegistration) {
			if (isWebJarsAssetLocatorPresent) {
				RootBeanDefinition webJarsResolverDef = new RootBeanDefinition(WebJarsResourceResolver.class);
				webJarsResolverDef.setSource(source);
				webJarsResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				resourceResolvers.add(webJarsResolverDef);
			}
			RootBeanDefinition pathResolverDef = new RootBeanDefinition(PathResourceResolver.class);
			pathResolverDef.setSource(source);
			pathResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
			resourceResolvers.add(pathResolverDef);
		}

		Element transformersElement = DomUtils.getChildElementByTagName(element, "transformers");
		if (transformersElement != null) {
			for (Element beanElement : DomUtils.getChildElementsByTagName(transformersElement, "bean", "ref")) {
				Object object = parserContext.getDelegate().parsePropertySubElement(beanElement, null);
				resourceTransformers.add(object);
			}
		}
	}

	private RootBeanDefinition parseVersionResolver(ParserContext parserContext, Element element, Object source) {
		ManagedMap<String, ? super Object> strategyMap = new ManagedMap<String, Object>();
		strategyMap.setSource(source);
		RootBeanDefinition versionResolverDef = new RootBeanDefinition(VersionResourceResolver.class);
		versionResolverDef.setSource(source);
		versionResolverDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		versionResolverDef.getPropertyValues().addPropertyValue("strategyMap", strategyMap);

		for (Element beanElement : DomUtils.getChildElements(element)) {
			String[] patterns = StringUtils.commaDelimitedListToStringArray(beanElement.getAttribute("patterns"));
			Object strategy = null;
			if (FIXED_VERSION_STRATEGY_ELEMENT.equals(beanElement.getLocalName())) {
				ConstructorArgumentValues cavs = new ConstructorArgumentValues();
				cavs.addIndexedArgumentValue(0, beanElement.getAttribute("version"));
				RootBeanDefinition strategyDef = new RootBeanDefinition(FixedVersionStrategy.class);
				strategyDef.setSource(source);
				strategyDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				strategyDef.setConstructorArgumentValues(cavs);
				strategy = strategyDef;
			}
			else if (CONTENT_VERSION_STRATEGY_ELEMENT.equals(beanElement.getLocalName())) {
				RootBeanDefinition strategyDef = new RootBeanDefinition(ContentVersionStrategy.class);
				strategyDef.setSource(source);
				strategyDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
				strategy = strategyDef;
			}
			else if (VERSION_STRATEGY_ELEMENT.equals(beanElement.getLocalName())) {
				Element childElement = DomUtils.getChildElementsByTagName(beanElement, "bean", "ref").get(0);
				strategy = parserContext.getDelegate().parsePropertySubElement(childElement, null);
			}
			for (String pattern : patterns) {
				strategyMap.put(pattern, strategy);
			}
		}

		return versionResolverDef;
	}

}
