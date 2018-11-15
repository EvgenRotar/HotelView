package com.evgen.controller;

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
import com.evgen.builder.ReservationRequestBuilder;
import com.evgen.dao.HotelDao;
import com.evgen.utils.ActiveMqUtils;
import com.evgen.wrapper.EditReservation;

@Controller
public class EditReservationController {

  private final HotelDao hotelDao;
  private final ActiveMqUtils activeMqUtils;

  @Autowired
  public EditReservationController(HotelDao hotelDao, ActiveMqUtils activeMqUtils) {
    this.hotelDao = hotelDao;
    this.activeMqUtils = activeMqUtils;
  }

  @PostMapping("/hotelEdit")
  public String selectHotelEditForm(@ModelAttribute EditReservation editReservation, Model model) {
    try {
      Object hotels = activeMqUtils.sendMessage("retrieveHotels", null);
      //List hotels = hotelDao.getHotels();

      model.addAttribute("hotels", hotels);

      return "selectHotelEditForm";
    } catch (HttpClientErrorException | ResourceAccessException e) {
      return "error";
    }
  }

  @PostMapping("/apartmentEdit")
  public String selectApartmentEditForm(@ModelAttribute EditReservation editReservation, Model model) {
    try {
      Object hotels = activeMqUtils.sendMessage("retrieveHotelByName", editReservation.getHotelName());
      //List hotels = hotelDao.getHotelByName(editReservation.getHotelName());

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
    } catch (HttpClientErrorException e) {
      attributes.addFlashAttribute(editReservation);

      return new RedirectView("/errorEdit");
    }
  }

  @GetMapping("/errorEdit")
  public String errorPageEdit(EditReservation editReservation, Model model) {
    model.addAttribute(editReservation);

    return "busyApartmentEdit";
  }
}
