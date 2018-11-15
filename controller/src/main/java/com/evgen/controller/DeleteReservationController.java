package com.evgen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.evgen.dao.HotelDao;
import com.evgen.messaging.MessageSender;
import com.evgen.wrapper.ReservationId;

@Controller
public class DeleteReservationController {

  private final HotelDao hotelDao;
  private final MessageSender messageSender;

  @Autowired
  public DeleteReservationController(HotelDao hotelDao, MessageSender messageSender) {
    this.hotelDao = hotelDao;
    this.messageSender = messageSender;
  }

  @PostMapping("/delete")
  public RedirectView deleteReservation(ReservationId reservationId) {
    List<Object> request = new ArrayList<>();
    request.add(reservationId.getId());
    request.add(reservationId.getGuestId());

    //messageSender.sendMessageToReservation("deleteReservation", request);
    hotelDao.deleteReservation(reservationId.getGuestId(), reservationId.getId());

    return new RedirectView("/guests");
  }
}
