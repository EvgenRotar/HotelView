package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.evgen.dao.HotelDao;
import com.evgen.service.UserCreateService;
import com.evgen.service.UserCreateServiceImpl;
import com.evgen.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ServiceTestConfig {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public HotelDao hotelDao() {
    return EasyMock.createMock(HotelDao.class);
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl(hotelDao());
  }

  @Bean
  public UserCreateService userCreateService() {
    return new UserCreateServiceImpl(hotelDao(), passwordEncoder());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}