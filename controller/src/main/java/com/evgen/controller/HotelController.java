package com.evgen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Guest;
import com.evgen.builder.ReservationRequestBuilder;
import com.evgen.dao.HotelDao;
import com.evgen.wrapper.CreateReservation;
import com.evgen.wrapper.EditReservation;
import com.evgen.wrapper.GuestName;
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
    } catch (HttpClientErrorException | ResourceAccessException | IllegalArgumentException e) {
      return "error";
    }
  }

  @PostMapping("/selectHotel")
  public String selectHotelForm(@ModelAttribute CreateReservation createReservation, Model model) {
    try {
      List hotels = hotelDao.getHotels();
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
  public RedirectView createReservation(CreateReservation createReservation, RedirectAttributes attributes) {
    try {
      Guest guest = hotelDao.createReservation(createReservation);
      attributes.addAttribute("name", guest.getName());

      return new RedirectView("/guests");
    } catch (HttpServerErrorException e) {
      attributes.addFlashAttribute(createReservation);

      return new RedirectView("/errorCreate");
    }
  }

  @GetMapping("/errorCreate")
  public String errorPage(CreateReservation createReservation, Model model) {
    model.addAttribute(createReservation);

    return "busyApartment";
  }

  @PostMapping("/selectHotelEdit")
  public String selectHotelEditForm(@ModelAttribute EditReservation editReservation, Model model) {
    try {
      List hotels = hotelDao.getHotels();
      model.addAttribute("hotels", hotels);

      return "selectHotelEditForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/selectApartmentEdit")
  public String selectApartmentEditForm(@ModelAttribute EditReservation editReservation, Model model) {
    try {
      List hotels = hotelDao.getHotelByName(editReservation.getHotelName());
      model.addAttribute("hotels", hotels);

      return "selectApartmentEditForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/edit")
  public RedirectView editReservationForm(EditReservation editReservation,
      RedirectAttributes attributes) {
    try {
      CreateReservation request = ReservationRequestBuilder.build(editReservation);
      Guest guest = hotelDao.editReservation(request, editReservation.getReservationId());
      attributes.addAttribute("name", guest.getName());

      return new RedirectView("/guests");
    } catch (HttpServerErrorException e){
      attributes.addFlashAttribute(editReservation);

      return new RedirectView("/errorEdit");
    }
  }

  @GetMapping("/errorEdit")
  public String errorPageEdit(EditReservation editReservation, Model model) {
    model.addAttribute(editReservation);

    return "busyApartmentEdit";
  }

  @PostMapping("/delete")
  public RedirectView deleteReservation(ReservationId reservationId, RedirectAttributes attributes) {
    Guest guest = hotelDao.deleteReservation(reservationId.getGuestId(), reservationId.getId());
    attributes.addAttribute("name", guest.getName());

    return new RedirectView("/guests");
  }
}
