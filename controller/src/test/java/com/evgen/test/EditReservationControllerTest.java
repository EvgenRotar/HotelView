package com.evgen.test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

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
import com.evgen.Message;
import com.evgen.config.HotelControllerTestConfig;
import com.evgen.dao.HotelDao;
import com.evgen.utils.ActiveMqUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HotelControllerTestConfig.class)
@WebAppConfiguration
public class EditReservationControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Autowired
  private HotelDao hotelDao;

  @Autowired
  private ActiveMqUtils activeMqUtils;

  @After
  public void tearDown() {
    reset(activeMqUtils);
    reset(hotelDao);
  }

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void selectHotelEditFormTest() throws Exception {
//    expect(hotelDao.getHotels())
//        .andReturn(new ArrayList());
//    replay(hotelDao);

    expect(activeMqUtils.sendMessage(anyObject(Message.class))).andReturn(new Object());
    replay(activeMqUtils);

    this.mockMvc.perform(post("/hotelEdit"))
        .andExpect(status().isOk())
        .andExpect(view().name("selectHotelEditForm"));
  }

  @Test
  public void selectApartmentEditFormTest() throws Exception {
    expect(hotelDao.getHotelByName(anyString()))
        .andReturn(new ArrayList());
    replay(hotelDao);

    this.mockMvc.perform(post("/apartmentEdit"))
        .andExpect(status().isOk())
        .andExpect(view().name("selectApartmentEditForm"));
  }

  @Test
  public void editReservationFormTest() throws Exception {
//    expect(hotelDao.editReservation(anyObject(), anyString()))
//        .andReturn(new Guest());
//    replay(hotelDao);

    expect(activeMqUtils.sendMessage(anyObject(Message.class))).andReturn(new Object());
    replay(activeMqUtils);

    this.mockMvc.perform(post("/edit"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/guests"));
  }

  @Test
  public void errorPageEditTest() throws Exception {

    this.mockMvc.perform(get("/errorEdit"))
        .andExpect(status().isOk())
        .andExpect(view().name("busyApartmentEdit"));
  }
}
