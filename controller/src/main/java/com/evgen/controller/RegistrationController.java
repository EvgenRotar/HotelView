package com.evgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.Guest;
import com.evgen.service.UserCreateService;

@Controller
public class RegistrationController {

  private final UserCreateService userCreateServiceImpl;

  @Autowired
  public RegistrationController(UserCreateService userCreateServiceImpl) {
    this.userCreateServiceImpl = userCreateServiceImpl;
  }

  @GetMapping("/registration")
  public String createGuestForm(@ModelAttribute Guest guest) {
    return "registrationForm";
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

  @RequestMapping("/error-registration")
  public String registrationError(@ModelAttribute Guest guest, Model model) {
    model.addAttribute("registrationError", true);

    return "registrationForm";
  }
}
