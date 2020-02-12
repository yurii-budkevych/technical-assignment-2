package com.technical.assignment.demo;

import com.technical.assignment.demo.controller.EndpointImpl;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Configuration
@ApplicationPath("/")
public class JerseyApplication extends ResourceConfig {
    public JerseyApplication() {
        register(EndpointImpl.class);
    }

    @Bean
    public Client createClient() {
        return ClientBuilder.newClient(new ClientConfig().register(EndpointImpl.class));
    }
}