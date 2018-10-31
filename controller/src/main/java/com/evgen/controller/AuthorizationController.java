package com.evgen.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Guest;
import com.evgen.dao.HotelDao;
import com.evgen.service.UserCreateService;
import com.evgen.utils.Oauth2Utils;
import com.evgen.wrapper.ReservationId;

@Controller
public class AuthorizationController {

  private final UserCreateService userCreateServiceImpl;
  private final HotelDao hotelDao;
  private final Oauth2Utils oauth2Utils;
  private Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

  @Autowired
  public AuthorizationController(
      UserCreateService userCreateServiceImpl, Oauth2Utils oauth2Utils, HotelDao hotelDao) {
    this.userCreateServiceImpl = userCreateServiceImpl;
    this.hotelDao = hotelDao;
    this.oauth2Utils = oauth2Utils;
  }

  @GetMapping("/")
  public RedirectView index() {
    return new RedirectView("/guests");
  }

  @GetMapping("/guests")
  public String retrieveGuest(@ModelAttribute ReservationId reservationId, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    try {
      Guest guest = hotelDao.getGuestByName(authentication.getName());
      model.addAttribute(guest);

      return "guest";
    } catch (HttpClientErrorException e) {
      if (authentication == null) {
        return "error";
      }
      Guest guest = userCreateServiceImpl.createGuestFromGoogle(authentication.getName());
      model.addAttribute(guest);

      return "guest";
    }
  }

  @RequestMapping("/login")
  public String login(Model model) {
    oauth2Utils.setOauth2AuthenticationUrls(oauth2AuthenticationUrls);
    model.addAttribute("urls", oauth2AuthenticationUrls);

    return "login";
  }

  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);

    return "login";
  }

}