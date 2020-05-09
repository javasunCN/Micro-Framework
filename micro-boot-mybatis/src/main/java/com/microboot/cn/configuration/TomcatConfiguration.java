package com.microboot.cn.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfiguration {

    @Value("${server.tomcat.apr.enabled:false}")
    private boolean enabled;


    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // 设置协议
        factory.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        //factory.setProtocol("org.apache.coyote.http11.Http11NioProtocol");
        if (enabled) {
            AprLifecycleListener  aprLifecycleListener = new AprLifecycleListener();
            aprLifecycleListener.setSSLEngine("off");
            factory.addContextLifecycleListeners(aprLifecycleListener);
        }

        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11AprProtocol handler = (Http11AprProtocol)connector.getProtocolHandler();
                //对tomcat进行对应的设置
                boolean isAprRequired = handler.isAprRequired();
                System.out.println("isAprRequired:"+isAprRequired);

            }
        });
        return factory;
    }
}
