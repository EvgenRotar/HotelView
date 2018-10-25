package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.evgen.connector.Connector;

@Configuration
public class ConnectorTestConfig {

  @Bean
  public RestTemplate restTemplate() { return  EasyMock.createMock(RestTemplate.class); }

  @Bean
  public Connector connector() { return new Connector(restTemplate()); }

}
