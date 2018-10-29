package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.evgen.dao.HotelDao;
import com.evgen.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ServiceTestConfig {

  @Bean
  public HotelDao hotelDao() { return  EasyMock.createMock(HotelDao.class); }

  @Bean
  public UserDetailsServiceImpl userDetailsService() { return new UserDetailsServiceImpl(hotelDao()); }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
