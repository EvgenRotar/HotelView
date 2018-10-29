package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evgen.connector.Connector;
import com.evgen.dao.HotelDao;
import com.evgen.dao.HotelDaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class DaoImplTestConfig {

  @Bean
  public Connector connector() {
    return EasyMock.createMock(Connector.class);
  }

  @Bean
  public HotelDao hotelDao() {
    return new HotelDaoImpl(connector());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}