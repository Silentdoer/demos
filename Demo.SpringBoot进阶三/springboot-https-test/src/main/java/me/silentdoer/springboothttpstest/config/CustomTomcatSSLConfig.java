package me.silentdoer.springboothttpstest.config;

import lombok.val;
import org.apache.catalina.connector.Connector;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author liqi.wang
 * @version 1.0.0
 */
@ConditionalOnExpression(value = "${custom.tomcat.ssl.enable:false}")
@Configuration
public class CustomTomcatSSLConfig {

    /*@Profile("dev")*/

    @Value("${custom.tomcat.ssl.port:8443}")
    private int tomcatSSLPort;

    @Value("${custom.tomcat.ssl.key-store:/keystore/client.p12}")
    private String tomcatSSLKeyStore;

    @Value("${custom.tomcat.ssl.key-store-password:88888888}")
    private String keystorePassword;

    @Value("${custom.tomcat.ssl.key-store-type:PKCS12}")
    private String keystoreType;

    @Value("${custom.tomcat.ssl.key-alias:client}")
    private String keyAlias;

    @Bean
    public ServletWebServerFactory servletContainer() throws IOException {
        val tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSSLConnector());
        return tomcat;
    }

    private Connector createSSLConnector() throws IOException {
        // 由于下面需要填写签名的地址，如果我们打包成Jar，这个地址就失效了，从 resources 读取 签名文件

        File origin = ResourceUtils.getFile(tomcatSSLKeyStore);
        // 复制到临时文件
        val dest = Files.createTempFile("ssl", ".p12").toFile();

        FileUtils.copyFile(origin, dest);

        val connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

        // 8443 or 443, https is default 443
        connector.setPort(tomcatSSLPort);

        connector.setSecure(true);
        connector.setScheme("https");

        connector.setAttribute("SSLEnabled", true);
        connector.setAttribute("sslProtocol", "TLS");
        connector.setAttribute("protocol", "org.apache.coyote.http11.Http11Protocol");
        connector.setAttribute("clientAuth", false);
        // 使用临时的前面文件路径
        connector.setAttribute("keystoreFile", dest.getAbsolutePath());
        connector.setAttribute("keystoreType", keystoreType);
        connector.setAttribute("keystorePass", keystorePassword);
        connector.setAttribute("keyPass", keystorePassword);
        connector.setAttribute("keyAlias", keyAlias);
        return connector;
    }
}
