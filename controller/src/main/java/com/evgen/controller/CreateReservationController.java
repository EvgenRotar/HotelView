package com.evgen.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Message;
import com.evgen.ReservationRequest;
import com.evgen.dao.HotelDao;
import com.evgen.utils.ActiveMqUtils;

@Controller
public class CreateReservationController {

  private final HotelDao hotelDao;
  private final ActiveMqUtils activeMqUtils;

  @Autowired
  public CreateReservationController(HotelDao hotelDao, ActiveMqUtils activeMqUtils) {
    this.hotelDao = hotelDao;
    this.activeMqUtils = activeMqUtils;
  }

  @PostMapping("/hotel")
  public String selectHotelForm(@ModelAttribute ReservationRequest reservationRequest, Model model) {
    try {
      Message message = new Message(UUID.randomUUID().toString(), "retrieveHotels", null);

      Object hotels = activeMqUtils.sendMessage(message);
      //List hotels = hotelDao.getHotels();

      model.addAttribute("hotels", hotels);

      return "selectHotelForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/apartment")
  public String selectApartmentForm(@ModelAttribute ReservationRequest reservationRequest, Model model) {
    try {
      Message message = new Message(UUID.randomUUID().toString(), "retrieveHotelByName",
          reservationRequest.getHotelName());

      Object hotels = activeMqUtils.sendMessage(message);
      //List hotels = hotelDao.getHotelByName(reservationRequest.getHotelName());

      model.addAttribute("hotels", hotels);

      return "selectApartmentForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/create")
  public RedirectView createReservation(ReservationRequest reservationRequest, RedirectAttributes attributes) {
    try {
      hotelDao.createReservation(reservationRequest);

      return new RedirectView("/guests");
    } catch (HttpClientErrorException e) {
      attributes.addFlashAttribute(reservationRequest);

      return new RedirectView("/errorCreate");
    }
  }

  @GetMapping("/errorCreate")
  public String errorPage(ReservationRequest reservationRequest, Model model) {
    model.addAttribute(reservationRequest);

    return "busyApartment";
  }
}
