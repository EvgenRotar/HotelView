package com.evgen.config;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource("classpath:activeMq.properties")
public class MessagingConfiguration {

  @Value("${activeMq.url}")
  private String DEFAULT_BROKER_URL;

  @Value("${activeMq.availability-queue}")
  private String AVAILABILITY_QUEUE;

  @Value("${activeMq.reservation-queue}")
  private String RESERVATION_QUEUE;

  @Bean
  public ActiveMQConnectionFactory connectionFactory() {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
    connectionFactory.setTrustAllPackages(true);
    return connectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplateAvailability() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setDefaultDestinationName(AVAILABILITY_QUEUE);
    return template;
  }

  @Bean
  public JmsTemplate jmsTemplateReservation() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setDefaultDestinationName(RESERVATION_QUEUE);
    return template;
  }
}
