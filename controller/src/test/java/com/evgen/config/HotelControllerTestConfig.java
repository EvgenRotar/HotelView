package com.evgen.config;

import org.easymock.EasyMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.evgen.dao.HotelDao;
import com.evgen.messaging.MessageReceiver;
import com.evgen.messaging.MessageSender;
import com.evgen.service.UserCreateService;
import com.evgen.service.UserCreateServiceImpl;
import com.evgen.utils.Oauth2Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.evgen.controller")
public class HotelControllerTestConfig {

  @Bean
  public HotelDao hotelDao() {
    return EasyMock.createMock(HotelDao.class);
  }

  @Bean
  public MessageSender messageSender() {
    return EasyMock.createMock(MessageSender.class);
  }

  @Bean
  public MessageReceiver messageReceiver() {
    return EasyMock.createMock(MessageReceiver.class);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public UserCreateService userCreateService() {
    return EasyMock.createMock(UserCreateServiceImpl.class);
  }

  @Bean
  public Oauth2Utils oauth2Utils() {
    return EasyMock.createMock(Oauth2Utils.class);
  }

}