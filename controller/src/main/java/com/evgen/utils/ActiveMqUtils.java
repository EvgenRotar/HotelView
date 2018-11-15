package com.evgen.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evgen.Message;
import com.evgen.messaging.MessageReceiver;
import com.evgen.messaging.MessageSender;

@Component
public class ActiveMqUtils {

  private final MessageSender messageSender;
  private final MessageReceiver messageReceiver;

  @Autowired
  public ActiveMqUtils(MessageSender messageSender, MessageReceiver messageReceiver) {
    this.messageSender = messageSender;
    this.messageReceiver = messageReceiver;
  }

  public Object sendMessage(String endpoint, Object requestObject) {
    Message message = new Message(UUID.randomUUID().toString(), endpoint);
    message.getRequestObject().add(requestObject);

    messageReceiver.addToWaitingList(message);
    messageSender.sendMessage(message);

    synchronized (message) {
      try {
        message.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException("send message error");
      }
    }
    Message response = messageReceiver.consume(message.getId());

    return response.getRequestObject();
  }

}
