package com.evgen.dao;

import java.util.List;

import com.evgen.Guest;
import com.evgen.ReservationRequest;

public interface HotelDao {

  List getHotels();

  List getHotelByName(String name);

  Guest getGuestByName(String name);

  //List getReservations(String guestId);

  Guest deleteReservation(String guestId, String reservationId);

  Guest createReservation(ReservationRequest reservationRequest);

  Guest editReservation(ReservationRequest reservationRequest, String reservationId);

  Guest createGuest(Guest guest);

}