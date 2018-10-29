package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
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
import com.evgen.ReservationRequest;
import com.evgen.config.HotelControllerTestConfig;
import com.evgen.dao.HotelDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HotelControllerTestConfig.class)
@WebAppConfiguration
public class HotelControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired
  private HotelDao hotelDao;

  @After
  public void tearDown() {
    reset(hotelDao);
  }

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void indexTest() throws Exception {
    this.mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/guests"));
  }

  @Test
  public void selectHotelFormTest() throws Exception {
    this.mockMvc.perform(post("/hotel"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("")))
        .andExpect(view().name("selectHotelForm"));
  }

  @Test
  public void selectApartmentFormTest() throws Exception {
    this.mockMvc.perform(post("/apartment"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("")))
        .andExpect(view().name("selectApartmentForm"));
  }

  @Test
  public void createReservationTest() throws Exception {
    Guest guest = new Guest();
    guest.setName("sergei");
    expect(hotelDao.createReservation(anyObject(ReservationRequest.class))).andReturn(guest);
    replay(hotelDao);

    this.mockMvc.perform(post("/create"))
        .andExpect(redirectedUrl("/guests"));
  }
}