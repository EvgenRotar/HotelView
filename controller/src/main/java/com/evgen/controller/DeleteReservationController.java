package com.evgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.dao.HotelDao;
import com.evgen.wrapper.ReservationId;

@Controller
public class DeleteReservationController {

  private final HotelDao hotelDao;

  @Autowired
  public DeleteReservationController(HotelDao hotelDao) {
    this.hotelDao = hotelDao;
  }

  @PostMapping("/delete")
  public RedirectView deleteReservation(ReservationId reservationId) {
    hotelDao.deleteReservation(reservationId.getGuestId(), reservationId.getId());

    return new RedirectView("/guests");
  }
}
