package com.evgen.dao;

import java.util.List;

import com.evgen.Guest;
import com.evgen.wrapper.CreateReservation;

public interface HotelDao {

  List getHotels();

  List getHotelByName(String name);

  Guest getGuestByName(String name);

  List getReservations(String guestId);

  Guest deleteReservation(String guestId, String reservationId);

  Guest createReservation(CreateReservation createReservation);

  Guest editReservation(CreateReservation createReservation, String reservationId);

}
