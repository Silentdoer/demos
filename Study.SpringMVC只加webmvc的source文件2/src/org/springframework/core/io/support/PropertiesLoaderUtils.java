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

package org.springframework.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarInputStream;

import org.apache.catalina.loader.WebappClassLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ResourceUtils;

/**
 * Convenient utility methods for loading of {@code java.util.Properties},
 * performing standard handling of input streams.
 *
 * <p>For more configurable properties loading, including the option of a
 * customized encoding, consider using the PropertiesLoaderSupport class.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 2.0
 * @see PropertiesLoaderSupport
 */
public abstract class PropertiesLoaderUtils {

	private static final String XML_FILE_EXTENSION = ".xml";


	/**
	 * Load properties from the given EncodedResource,
	 * potentially defining a specific encoding for the properties file.
	 * @see #fillProperties(java.util.Properties, EncodedResource)
	 */
	public static Properties loadProperties(EncodedResource resource) throws IOException {
		Properties props = new Properties();
		fillProperties(props, resource);
		return props;
	}

	/**
	 * Fill the given properties from the given EncodedResource,
	 * potentially defining a specific encoding for the properties file.
	 * @param props the Properties instance to load into
	 * @param resource the resource to load from
	 * @throws IOException in case of I/O errors
	 */
	public static void fillProperties(Properties props, EncodedResource resource)
			throws IOException {

		fillProperties(props, resource, new DefaultPropertiesPersister());
	}

	/**
	 * Actually load properties from the given EncodedResource into the given Properties instance.
	 * @param props the Properties instance to load into
	 * @param resource the resource to load from
	 * @param persister the PropertiesPersister to use
	 * @throws IOException in case of I/O errors
	 */
	static void fillProperties(Properties props, EncodedResource resource, PropertiesPersister persister)
			throws IOException {

		InputStream stream = null;
		Reader reader = null;
		try {
			String filename = resource.getResource().getFilename();
			if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
				stream = resource.getInputStream();
				persister.loadFromXml(props, stream);
			}
			else if (resource.requiresReader()) {
				reader = resource.getReader();
				persister.load(props, reader);
			}
			else {
				stream = resource.getInputStream();
				persister.load(props, stream);
			}
		}
		finally {
			if (stream != null) {
				stream.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * Load properties from the given resource (in ISO-8859-1 encoding).
	 * @param resource the resource to load from
	 * @return the populated Properties instance
	 * @throws IOException if loading failed
	 * @see #fillProperties(java.util.Properties, Resource)
	 */
	public static Properties loadProperties(Resource resource) throws IOException {
		Properties props = new Properties();
		fillProperties(props, resource);
		return props;
	}

	/**
	 * Fill the given properties from the given resource (in ISO-8859-1 encoding).
	 * @param props the Properties instance to fill
	 * @param resource the resource to load from
	 * @throws IOException if loading failed
	 */
	public static void fillProperties(Properties props, Resource resource) throws IOException {
		InputStream is = resource.getInputStream();
		try {
			String filename = resource.getFilename();
			if (filename != null && filename.endsWith(XML_FILE_EXTENSION)) {
				props.loadFromXML(is);
			}
			else {
				props.load(is);
			}
		}
		finally {
			is.close();
		}
	}

	/**
	 * Load all properties from the specified class path resource
	 * (in ISO-8859-1 encoding), using the default class loader.
	 * <p>Merges properties if more than one resource of the same name
	 * found in the class path.
	 * @param resourceName the name of the class path resource
	 * @return the populated Properties instance
	 * @throws IOException if loading failed
	 */
	public static Properties loadAllProperties(String resourceName) throws IOException {
		return loadAllProperties(resourceName, null);
	}

	/**
	 * Load all properties from the specified class path resource
	 * (in ISO-8859-1 encoding), using the given class loader.
	 * <p>Merges properties if more than one resource of the same name
	 * found in the class path.
	 * @param resourceName the name of the class path resource
	 * @param classLoader the ClassLoader to use for loading
	 * (or {@code null} to use the default class loader)
	 * @return the populated Properties instance
	 * @throws IOException if loading failed
	 */ // TODO resourceName is "META-INF/spring.handlers"
	public static Properties loadAllProperties(String resourceName, ClassLoader classLoader) throws IOException {
		// TODO 重要方法，这里决定了spring怎么加载spring.handlers（每个jar包都有一个这个东西）
		Assert.notNull(resourceName, "Resource name must not be null");
		ClassLoader classLoaderToUse = classLoader;
		if (classLoaderToUse == null) {
			classLoaderToUse = ClassUtils.getDefaultClassLoader();
		}
		// TODO cl.getResources(..)是指从这个ClassLoader的所有classpath包括其父级的符合resourceName的文件都获取
		//WebappClassLoader c;c.getResources()
		// TODO，注意如果解压jar包那么每个jar包的spring.handlers文件会被覆盖，故应该手动合并这几个文件
		Enumeration<URL> urls = (classLoaderToUse != null ? classLoaderToUse.getResources(resourceName) :
				ClassLoader.getSystemResources(resourceName));
		// TODO 这个ClassLoader就是WebappClassLoader，所以它能加载lib下所有jar包中的spring.handlers
		System.out.println("PropertiesLoaderUtils#loadAllProperties" + classLoaderToUse.getClass());
		Properties props = new Properties();
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			System.out.println("AllPropreties: " + url.toExternalForm());
			URLConnection con = url.openConnection();
			ResourceUtils.useCachesIfNecessary(con);
			// TODO 注意，这个con指向的是jar内部的spring.handlers文件，故getInputStream获取的是jar包内部的该文件的输入流
			// TODO 至于最终是怎么先解压jar然后再获取这个应该是JDK帮我们做好了。
			InputStream is = con.getInputStream();
			try {
				if (resourceName.endsWith(XML_FILE_EXTENSION)) {
					props.loadFromXML(is);
				}
				else {
					props.load(is);
				}
			}
			finally {
				is.close();
			}
		}
		return props;
	}

}
