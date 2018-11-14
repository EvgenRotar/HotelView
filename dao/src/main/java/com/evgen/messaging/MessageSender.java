package com.evgen.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.evgen.Message;

@Component
public class MessageSender {

  private final JmsTemplate jmsTemplate;

  @Autowired
  public MessageSender(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  public void sendMessage(final Message message) {
    jmsTemplate.send(session -> session.createObjectMessage(message));
  }

}
