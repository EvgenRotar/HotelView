package com.evgen.messaging;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.evgen.Message;

@Component
public class MessageSender {

  private final JmsTemplate jmsTemplateAvailability;
  private final JmsTemplate jmsTemplateReservation;
  private final MessageReceiver messageReceiver;

  @Autowired
  public MessageSender(JmsTemplate jmsTemplateAvailability,
      JmsTemplate jmsTemplateReservation, MessageReceiver messageReceiver) {
    this.jmsTemplateAvailability = jmsTemplateAvailability;
    this.jmsTemplateReservation = jmsTemplateReservation;
    this.messageReceiver = messageReceiver;
  }

  public Object sendMessageToAvailability(String endpoint, Object requestObject) {
    return sendMessage(endpoint, requestObject, jmsTemplateAvailability);

  }

  public Object sendMessageToReservation(String endpoint, Object requestObject) {
    return sendMessage(endpoint, requestObject, jmsTemplateReservation);
  }

  private Object sendMessage(String endpoint, Object requestObject, JmsTemplate jmsTemplateReservation) {
    Message message = new Message(UUID.randomUUID().toString(), endpoint, requestObject);

    messageReceiver.addToWaitingList(message);
    jmsTemplateReservation.send(session -> session.createObjectMessage(message));
    Message response = waitResponse(message);

    return response.getRequestObject();
  }

  private Message waitResponse(Message message) {
    synchronized (message) {
      try {
        message.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException("send message error");
      }
    }
    return messageReceiver.consume(message.getId());
  }
}
