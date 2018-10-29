package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.evgen.Guest;
import com.evgen.ReservationRequest;
import com.evgen.config.ConnectorTestConfig;
import com.evgen.connector.Connector;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConnectorTestConfig.class)
public class ConnectorTest {

  private static final Logger LOGGER = LogManager.getLogger(ConnectorTest.class);

  @Autowired
  private Connector connector;

  @Autowired
  private RestTemplate restTemplateMock;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(restTemplateMock);
  }

  @Test
  public void sendRequestWithBody() throws URISyntaxException {
    LOGGER.debug("test: send request with body");
    ResponseEntity<Guest> res = new ResponseEntity<>(new Guest(), HttpStatus.OK);

    expect(restTemplateMock.exchange(anyObject(URI.class), anyObject(HttpMethod.class), anyObject(HttpEntity.class),
        anyObject(Class.class)))
        .andReturn(res);
    replay(restTemplateMock);

    Guest response = connector
        .sendRequestWithBody(new ReservationRequest(), new HttpHeaders(), new URI(""), HttpMethod.POST, Guest.class);

    assertNotNull(response);
  }

  @Test
  public void sendRequestWithOutBody() throws URISyntaxException {
    LOGGER.debug("test: send request without body");
    ResponseEntity<Guest> res = new ResponseEntity<>(new Guest(), HttpStatus.OK);

    expect(restTemplateMock.exchange(anyObject(URI.class), anyObject(HttpMethod.class), anyObject(HttpEntity.class),
        anyObject(Class.class)))
        .andReturn(res);
    replay(restTemplateMock);

    Guest response = connector
        .sendRequestWithoutBody(new HttpHeaders(), new URI(""), HttpMethod.GET, Guest.class);

    assertNotNull(response);
  }
}