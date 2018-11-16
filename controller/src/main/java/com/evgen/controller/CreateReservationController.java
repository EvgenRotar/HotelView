package com.evgen.controller;

import java.util.List;

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

import com.evgen.ReservationRequest;
import com.evgen.dao.HotelDao;
import com.evgen.messaging.MessageSender;

@Controller
public class CreateReservationController {

  private final HotelDao hotelDao;
  private final MessageSender messageSender;

  @Autowired
  public CreateReservationController(HotelDao hotelDao, MessageSender messageSender) {
    this.hotelDao = hotelDao;
    this.messageSender = messageSender;
  }

  @PostMapping("/hotel")
  public String selectHotelForm(@ModelAttribute ReservationRequest reservationRequest, Model model) {
    try {
      //Object hotels = messageSender.sendMessageToAvailability("retrieveHotels", null);
      List hotels = hotelDao.getHotels();

      model.addAttribute("hotels", hotels);

      return "selectHotelForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/apartment")
  public String selectApartmentForm(@ModelAttribute ReservationRequest reservationRequest, Model model) {
    try {
      //Object hotels = messageSender.sendMessageToAvailability("retrieveHotelByName", reservationRequest.getHotelName());
      List hotels = hotelDao.getHotelByName(reservationRequest.getHotelName());

      model.addAttribute("hotels", hotels);

      return "selectApartmentForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/create")
  public RedirectView createReservation(ReservationRequest reservationRequest, RedirectAttributes attributes) {
    try {
      //messageSender.sendMessageToReservation("createReservation", reservationRequest);
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
