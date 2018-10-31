package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.evgen.config.HotelControllerTestConfig;
import com.evgen.dao.HotelDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HotelControllerTestConfig.class)
@WebAppConfiguration
public class DeleteReservationTest {

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
  public void deleteReservationTest() throws Exception {
    expect(hotelDao.deleteReservation(anyString(), anyString()))
        .andReturn(new Guest());
    replay(hotelDao);

    this.mockMvc.perform(post("/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/guests"));
  }
}
