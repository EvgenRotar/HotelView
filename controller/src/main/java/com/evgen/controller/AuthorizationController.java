package com.evgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Guest;
import com.evgen.dao.HotelDao;
import com.evgen.wrapper.ReservationId;

@Controller
public class AuthorizationController {

  private final HotelDao hotelDao;

  @Autowired
  public AuthorizationController(HotelDao hotelDao) {
    this.hotelDao = hotelDao;
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

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);

    return "login";
  }

}