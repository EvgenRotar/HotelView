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

import com.evgen.Apartment;
import com.evgen.Guest;
import com.evgen.Hotel;
import com.evgen.dao.HotelDao;
import com.evgen.wrapper.GuestName;
import com.evgen.wrapper.CreateReservation;
import com.evgen.wrapper.ReservationId;

@Controller
public class HotelController {

  private final HotelDao hotelDao;

  @Autowired
  public HotelController(HotelDao hotelDao) {
    this.hotelDao = hotelDao;
  }

  @GetMapping("/")
  public String index(@ModelAttribute GuestName guestName) {
    return "index";
  }

  @GetMapping("/guests")
  public String retrieveGuest(@ModelAttribute ReservationId reservationId, GuestName guestName, Model model) {
    try {
      Guest guest = hotelDao.getGuestByName(guestName.getName());

      model.addAttribute(guest);
      return "guest";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @GetMapping("/selectHotel")
  public String selectHotelForm(@ModelAttribute CreateReservation createReservation, Model model) {
    try {
      List<Hotel> hotels = hotelDao.getHotels();
      model.addAttribute("hotels", hotels);
      return "selectHotelForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/selectApartment")
  public String selectApartmentForm(@ModelAttribute CreateReservation createReservation, Model model) {
    try {
      List hotels = hotelDao.getHotelByName(createReservation.getHotelName());
      model.addAttribute("hotels", hotels);
      return "selectApartmentForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/create")
  public RedirectView createReservation(CreateReservation createReservation, RedirectAttributes attributes){
    Guest guest = hotelDao.createReservation(createReservation);

    attributes.addAttribute("name", guest.getName());
    return new RedirectView("/guests");
  }

  @PostMapping("/edit")
  public String editReservationForm(ReservationId reservationId, Model model) {

    return "editForm";
  }

  @PostMapping("/delete")
  public RedirectView deleteReservation(ReservationId reservationId, RedirectAttributes attributes) {
    Guest guest = hotelDao.deleteReservation(reservationId.getGuestId(), reservationId.getId());

    attributes.addAttribute("name", guest.getName());
    return new RedirectView("/guests");
  }
}
