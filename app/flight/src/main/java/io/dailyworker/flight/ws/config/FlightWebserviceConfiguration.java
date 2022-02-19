package io.dailyworker.flight.ws.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@EnableWs
@Configuration
public class FlightWebserviceConfiguration {
    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> wsMimeMappings() {
        return factory -> {
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("xsd", MimeTypeUtils.TEXT_XML_VALUE);
            factory.setMimeMappings(mappings);
        };
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/flight-service/*");
    }

    @Bean
    CommonsXsdSchemaCollection commonsXsdSchemaCollection() {
        CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection();
        commonsXsdSchemaCollection.setXsds(new ClassPathResource("messages.xsd"));
        commonsXsdSchemaCollection.setInline(true);
        return commonsXsdSchemaCollection;
    }

    @Bean(name = "flight")
    DefaultWsdl11Definition flight(CommonsXsdSchemaCollection schemaCollection) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchemaCollection(schemaCollection);
        definition.setPortTypeName("FlightPort");
        definition.setLocationUri("/flight-service");
        definition.setTargetNamespace("http://dailyworker.github.io/tiny-travel/flight/schemas/definitions");
        return definition;
    }
}
