package com.evgen.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

  private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);

  private Map<String, com.evgen.Message> requestMessages = new HashMap<>();
  private Map<String, com.evgen.Message> responseMessages = new HashMap<>();

  public void addToWaitingList(com.evgen.Message message) {
    requestMessages.put(message.getId(), message);
  }

  public com.evgen.Message consume(String id) {
    return responseMessages.remove(id);
  }

  @JmsListener(destination = "response-queue")
  public void receiveMessage(final Message<com.evgen.Message> message) {

    MessageHeaders headers = message.getHeaders();
    com.evgen.Message response = message.getPayload();

    Optional.ofNullable(requestMessages.get(response.getId()))
        .ifPresent(requestMessage -> {
          responseMessages.put(response.getId(), response);

          synchronized (requestMessage) {
            requestMessage.notifyAll();
          }

          requestMessages.remove(requestMessage.getId());
        });
  }
}
