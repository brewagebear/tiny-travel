package io.dailyworker.flight.ws.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

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
    CommonsXsdSchemaCollection commonsXsdSchemaCollection() {
        CommonsXsdSchemaCollection commonsXsdSchemaCollection = new CommonsXsdSchemaCollection();
        commonsXsdSchemaCollection.setXsds(new ClassPathResource("messages.xsd"));
        commonsXsdSchemaCollection.setInline(true);

        return commonsXsdSchemaCollection;
    }

    @Bean("services")
    DefaultWsdl11Definition airline(CommonsXsdSchemaCollection schemaCollection) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchemaCollection(schemaCollection);
        definition.setPortTypeName("FlightPort");
        definition.setLocationUri("/wsdl");
        definition.setTargetNamespace("http://dailyworker.github.io/tiny-travel/flight/schemas/messages");
        return definition;
    }
}
