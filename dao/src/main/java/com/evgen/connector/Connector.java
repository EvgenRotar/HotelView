package com.evgen.connector;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class Connector {

  private static final Logger logger = LoggerFactory.getLogger(Connector.class);

  private RestTemplate restTemplate;

  @Autowired
  public Connector(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public <T> T sendRequestWithBody(Object request, HttpHeaders headers, URI url, HttpMethod method,
      Class<T> responseClazz) {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> fullRequest = new HttpEntity<>(request, headers);
    try {
      logger.info("Send request with body");
      ResponseEntity<T> response = restTemplate.exchange(url, method, fullRequest, responseClazz);
      return response.getBody();
    } catch (HttpClientErrorException | ResourceAccessException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public <T> T sendRequestWithoutBody(HttpHeaders headers, URI url, HttpMethod method, Class<T> responseClazz)
      throws HttpClientErrorException, ResourceAccessException {
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> fullRequest = new HttpEntity<>(headers);
    try {
      logger.info("Send request without body");
      ResponseEntity<T> response = restTemplate.exchange(url, method, fullRequest, responseClazz);
      return response.getBody();
    } catch (HttpClientErrorException | ResourceAccessException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
