package com.evgen.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
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

  private static String authorizationRequestBaseUri
      = "oauth2/authorization";
  private final ClientRegistrationRepository clientRegistrationRepository;
  private final HotelDao hotelDao;
  private Map<String, String> oauth2AuthenticationUrls
      = new HashMap<>();

  @Autowired
  public AuthorizationController(
      ClientRegistrationRepository clientRegistrationRepository, HotelDao hotelDao) {
    this.clientRegistrationRepository = clientRegistrationRepository;
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
  public String login(Model model) {
    Iterable<ClientRegistration> clientRegistrations = null;
    ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
        .as(Iterable.class);
    if (type != ResolvableType.NONE &&
        ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
      clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
    }

    if (clientRegistrations != null) {
      clientRegistrations.forEach(registration ->
          oauth2AuthenticationUrls.put(registration.getClientName(),
              authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
    }
    model.addAttribute("urls", oauth2AuthenticationUrls);

    return "login";
  }

  @RequestMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);

    return "login";
  }

}