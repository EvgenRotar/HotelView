package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.evgen.Guest;
import com.evgen.config.ServiceTestConfig;
import com.evgen.dao.HotelDao;
import com.evgen.service.UserCreateService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfig.class)
public class ServiceTest {

  private static final Logger LOGGER = LogManager.getLogger(ServiceTest.class);

  private static final String GUEST = "/Guest-with-reservations.json";
  private static final String NEW_GUEST_REQUEST = "/New-guest-request.json";
  private static final String NEW_GUEST = "/New-guest.json";

  @Autowired
  private HotelDao hotelDaoMock;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserCreateService userCreateService;

  @After
  public void clean() {
    verify();
  }

  @Before
  public void setUp() {
    reset(hotelDaoMock);
  }

  @Test
  public void loadUserByUsername() throws IOException {
    LOGGER.debug("test: load User By Username");

    Guest guest = objectMapper.readValue(getClass().getResourceAsStream(GUEST), Guest.class);

    expect(hotelDaoMock.getGuestByName("sergei")).andReturn(guest);
    replay(hotelDaoMock);

    UserDetails user = userDetailsService.loadUserByUsername("sergei");

    Assert.assertEquals(user.getUsername(), "sergei");
    Assert.assertEquals(user.getPassword(), "das");
    Assert.assertEquals(user.getAuthorities().toArray()[0], new SimpleGrantedAuthority("ROLE_ADMIN"));
  }

  @Test
  public void createGuest() throws IOException {
    LOGGER.debug("test: create Guest");

    Guest guestRequest = objectMapper.readValue(getClass().getResourceAsStream(NEW_GUEST_REQUEST), Guest.class);
    Guest guestResponse = objectMapper.readValue(getClass().getResourceAsStream(NEW_GUEST), Guest.class);

    expect(hotelDaoMock.createGuest(anyObject(Guest.class))).andReturn(guestResponse);
    replay(hotelDaoMock);

    Guest guest1 = userCreateService.createGuest(guestRequest);

    Assert.assertEquals(guest1.getPassword(), guestResponse.getPassword());
  }
}