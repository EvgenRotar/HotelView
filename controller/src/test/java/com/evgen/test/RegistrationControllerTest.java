package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.evgen.Guest;
import com.evgen.config.HotelControllerTestConfig;
import com.evgen.service.UserCreateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HotelControllerTestConfig.class)
@WebAppConfiguration
public class RegistrationControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired
  private UserCreateService userCreateService;

  @After
  public void tearDown() {
    reset(userCreateService);
  }

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void createGuestFormTest() throws Exception {
    this.mockMvc.perform(get("/registration"))
        .andExpect(status().isOk())
        .andExpect(view().name("registrationForm"));
  }

  @Test
  public void registrationErrorTest() throws Exception {
    this.mockMvc.perform(get("/error-registration"))
        .andExpect(status().isOk())
        .andExpect(view().name("registrationForm"));
  }

  @Test
  public void createGuestTest() throws Exception {
    expect(userCreateService.createGuest(anyObject()))
        .andReturn(new Guest());
    replay(userCreateService);

    this.mockMvc.perform(post("/guests"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/login"));
  }
}
