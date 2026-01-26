package it.pagopa.interop.probing.eservice.mock.webservice.util;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/soap/interop/probing/*");
    }

    @Bean(name = "okStatus")
    public DefaultWsdl11Definition wsdlOk(XsdSchema probingSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("ProbingServiceOk");
        wsdl.setLocationUri("/soap/interop/probing/ok/status");
        wsdl.setTargetNamespace("http://it/pagopa/interop/probing");
        wsdl.setSchema(probingSchema);
        return wsdl;
    }

    @Bean(name = "errorStatus")
    public DefaultWsdl11Definition wsdlError(XsdSchema probingSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("ProbingServiceError");
        wsdl.setLocationUri("/soap/interop/probing/error/status");
        wsdl.setTargetNamespace("http://it/pagopa/interop/probing");
        wsdl.setSchema(probingSchema);
        return wsdl;
    }

    @Bean(name = "randomStatus")
    public DefaultWsdl11Definition wsdlRandom(XsdSchema probingSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("ProbingServiceRandom");
        wsdl.setLocationUri("/soap/interop/probing/random/status");
        wsdl.setTargetNamespace("http://it/pagopa/interop/probing");
        wsdl.setSchema(probingSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema probingSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schema-definition.xsd"));
    }
}

