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

package org.springframework.web.servlet.mvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.web.util.UrlPathHelper;

/**
 * Handler interceptor that checks the request and prepares the response.
 * Checks for supported methods and a required session, and applies the
 * specified {@link CacheControl} builder.
 * See superclass bean properties for configuration options.
 *
 * <p>All the settings supported by this interceptor can also be set on
 * {@link AbstractController}. This interceptor is mainly intended for applying
 * checks and preparations to a set of controllers mapped by a HandlerMapping.
 *
 * @author Juergen Hoeller
 * @author Brian Clozel
 * @since 27.11.2003
 * @see AbstractController
 */
public class WebContentInterceptor extends WebContentGenerator implements HandlerInterceptor {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private PathMatcher pathMatcher = new AntPathMatcher();

	private Map<String, Integer> cacheMappings = new HashMap<String, Integer>();

	private Map<String, CacheControl> cacheControlMappings = new HashMap<String, CacheControl>();


	public WebContentInterceptor() {
		// No restriction of HTTP methods by default,
		// in particular for use with annotated controllers...
		super(false);
	}


	/**
	 * Set if URL lookup should always use full path within current servlet
	 * context. Else, the path within the current servlet mapping is used
	 * if applicable (i.e. in the case of a ".../*" servlet mapping in web.xml).
	 * Default is "false".
	 * <p>Only relevant for the "cacheMappings" setting.
	 * @see #setCacheMappings
	 * @see UrlPathHelper#setAlwaysUseFullPath
	 */
	public void setAlwaysUseFullPath(boolean alwaysUseFullPath) {
		this.urlPathHelper.setAlwaysUseFullPath(alwaysUseFullPath);
	}

	/**
	 * Set if context path and request URI should be URL-decoded.
	 * Both are returned <i>undecoded</i> by the Servlet API,
	 * in contrast to the servlet path.
	 * <p>Uses either the request encoding or the default encoding according
	 * to the Servlet spec (ISO-8859-1).
	 * <p>Only relevant for the "cacheMappings" setting.
	 * @see #setCacheMappings
	 * @see UrlPathHelper#setUrlDecode
	 */
	public void setUrlDecode(boolean urlDecode) {
		this.urlPathHelper.setUrlDecode(urlDecode);
	}

	/**
	 * Set the UrlPathHelper to use for resolution of lookup paths.
	 * <p>Use this to override the default UrlPathHelper with a custom subclass,
	 * or to share common UrlPathHelper settings across multiple HandlerMappings
	 * and MethodNameResolvers.
	 * <p>Only relevant for the "cacheMappings" setting.
	 * @see #setCacheMappings
	 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping#setUrlPathHelper
	 */
	public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
		Assert.notNull(urlPathHelper, "UrlPathHelper must not be null");
		this.urlPathHelper = urlPathHelper;
	}

	/**
	 * Map specific URL paths to specific cache seconds.
	 * <p>Overrides the default cache seconds setting of this interceptor.
	 * Can specify "-1" to exclude a URL path from default caching.
	 * <p>Supports direct matches, e.g. a registered "/test" matches "/test",
	 * and a various Ant-style pattern matches, e.g. a registered "/t*" matches
	 * both "/test" and "/team". For details, see the AntPathMatcher javadoc.
	 * @param cacheMappings a mapping between URL paths (as keys) and
	 * cache seconds (as values, need to be integer-parsable)
	 * @see #setCacheSeconds
	 * @see AntPathMatcher
	 */
	public void setCacheMappings(Properties cacheMappings) {
		this.cacheMappings.clear();
		Enumeration<?> propNames = cacheMappings.propertyNames();
		while (propNames.hasMoreElements()) {
			String path = (String) propNames.nextElement();
			int cacheSeconds = Integer.valueOf(cacheMappings.getProperty(path));
			this.cacheMappings.put(path, cacheSeconds);
		}
	}

	/**
	 * Map specific URL paths to a specific {@link CacheControl}.
	 * <p>Overrides the default cache seconds setting of this interceptor.
	 * Can specify a empty {@link CacheControl} instance
	 * to exclude a URL path from default caching.
	 * <p>Supports direct matches, e.g. a registered "/test" matches "/test",
	 * and a various Ant-style pattern matches, e.g. a registered "/t*" matches
	 * both "/test" and "/team". For details, see the AntPathMatcher javadoc.
	 * @param cacheControl the {@code CacheControl} to use
	 * @param paths URL paths that will map to the given {@code CacheControl}
	 * @see #setCacheSeconds
	 * @see AntPathMatcher
	 * @since 4.2
	 */
	public void addCacheMapping(CacheControl cacheControl, String... paths) {
		for (String path : paths) {
			this.cacheControlMappings.put(path, cacheControl);
		}
	}

	/**
	 * Set the PathMatcher implementation to use for matching URL paths
	 * against registered URL patterns, for determining cache mappings.
	 * Default is AntPathMatcher.
	 * @see #addCacheMapping
	 * @see #setCacheMappings
	 * @see AntPathMatcher
	 */
	public void setPathMatcher(PathMatcher pathMatcher) {
		Assert.notNull(pathMatcher, "PathMatcher must not be null");
		this.pathMatcher = pathMatcher;
	}


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		checkRequest(request);

		String lookupPath = this.urlPathHelper.getLookupPathForRequest(request);
		if (logger.isDebugEnabled()) {
			logger.debug("Looking up cache seconds for [" + lookupPath + "]");
		}

		CacheControl cacheControl = lookupCacheControl(lookupPath);
		Integer cacheSeconds = lookupCacheSeconds(lookupPath);
		if (cacheControl != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Applying CacheControl to [" + lookupPath + "]");
			}
			applyCacheControl(response, cacheControl);
		}
		else if (cacheSeconds != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Applying CacheControl to [" + lookupPath + "]");
			}
			applyCacheSeconds(response, cacheSeconds);
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Applying default cache seconds to [" + lookupPath + "]");
			}
			prepareResponse(response);
		}

		return true;
	}

	/**
	 * Look up a {@link CacheControl} instance for the given URL path.
	 * <p>Supports direct matches, e.g. a registered "/test" matches "/test",
	 * and various Ant-style pattern matches, e.g. a registered "/t*" matches
	 * both "/test" and "/team". For details, see the AntPathMatcher class.
	 * @param urlPath URL the bean is mapped to
	 * @return the associated {@code CacheControl}, or {@code null} if not found
	 * @see AntPathMatcher
	 */
	protected CacheControl lookupCacheControl(String urlPath) {
		// Direct match?
		CacheControl cacheControl = this.cacheControlMappings.get(urlPath);
		if (cacheControl == null) {
			// Pattern match?
			for (String registeredPath : this.cacheControlMappings.keySet()) {
				if (this.pathMatcher.match(registeredPath, urlPath)) {
					cacheControl = this.cacheControlMappings.get(registeredPath);
				}
			}
		}
		return cacheControl;
	}

	/**
	 * Look up a cacheSeconds integer value for the given URL path.
	 * <p>Supports direct matches, e.g. a registered "/test" matches "/test",
	 * and various Ant-style pattern matches, e.g. a registered "/t*" matches
	 * both "/test" and "/team". For details, see the AntPathMatcher class.
	 * @param urlPath URL the bean is mapped to
	 * @return the cacheSeconds integer value, or {@code null} if not found
	 * @see AntPathMatcher
	 */
	protected Integer lookupCacheSeconds(String urlPath) {
		// Direct match?
		Integer cacheSeconds = this.cacheMappings.get(urlPath);
		if (cacheSeconds == null) {
			// Pattern match?
			for (String registeredPath : this.cacheMappings.keySet()) {
				if (this.pathMatcher.match(registeredPath, urlPath)) {
					cacheSeconds = this.cacheMappings.get(registeredPath);
				}
			}
		}
		return cacheSeconds;
	}


	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
