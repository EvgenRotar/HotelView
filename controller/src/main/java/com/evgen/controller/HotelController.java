package com.evgen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Guest;
import com.evgen.ReservationRequest;
import com.evgen.builder.ReservationRequestBuilder;
import com.evgen.dao.HotelDao;
import com.evgen.service.UserCreateService;
import com.evgen.wrapper.EditReservation;
import com.evgen.wrapper.ReservationId;

@Controller
public class HotelController {

  private final HotelDao hotelDao;
  private final UserCreateService userCreateServiceImpl;

  @Autowired
  public HotelController(HotelDao hotelDao, UserCreateService userCreateServiceImpl) {
    this.hotelDao = hotelDao;
    this.userCreateServiceImpl = userCreateServiceImpl;
  }

  @GetMapping("/")
  public RedirectView index() {
    return new RedirectView("/guests");
  }

  @GetMapping("/guests")
  public String retrieveGuest(@ModelAttribute ReservationId reservationId, Model model) {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      Guest guest = hotelDao.getGuestByName(authentication.getName());
      model.addAttribute(guest);

      return "guest";
    } catch (HttpClientErrorException | ResourceAccessException | IllegalArgumentException e) {
      return "error";
    }
  }

  @PostMapping("/guests")
  public RedirectView createGuest(Guest guest) {
    try {
      userCreateServiceImpl.createGuest(guest);

      return new RedirectView("/login");
    } catch (HttpServerErrorException e) {
      return new RedirectView("/error-registration");
    }
  }

  @GetMapping("/registration")
  public String createGuestForm(@ModelAttribute Guest guest) {
    return "registrationForm";
  }

  @PostMapping("/hotel")
  public String selectHotelForm(@ModelAttribute ReservationRequest reservationRequest, Model model) {
    try {
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
      hotelDao.createReservation(reservationRequest);

      return new RedirectView("/guests");
    } catch (HttpServerErrorException e) {
      attributes.addFlashAttribute(reservationRequest);

      return new RedirectView("/errorCreate");
    }
  }

  @GetMapping("/errorCreate")
  public String errorPage(ReservationRequest reservationRequest, Model model) {
    model.addAttribute(reservationRequest);

    return "busyApartment";
  }

  @PostMapping("/hotelEdit")
  public String selectHotelEditForm(@ModelAttribute EditReservation editReservation, Model model) {
    try {
      List hotels = hotelDao.getHotels();
      model.addAttribute("hotels", hotels);

      return "selectHotelEditForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/apartmentEdit")
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
      ReservationRequest request = ReservationRequestBuilder.build(editReservation);
      hotelDao.editReservation(request, editReservation.getReservationId());

      return new RedirectView("/guests");
    } catch (HttpServerErrorException e) {
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
  public RedirectView deleteReservation(ReservationId reservationId) {
    hotelDao.deleteReservation(reservationId.getGuestId(), reservationId.getId());

    return new RedirectView("/guests");
  }

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);

    return "login";
  }

  @RequestMapping("/error-registration")
  public String registrationError(@ModelAttribute Guest guest, Model model) {
    model.addAttribute("registrationError", true);

    return "registrationForm";
  }
}
