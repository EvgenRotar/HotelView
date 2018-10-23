package com.evgen.dao;

import java.util.List;

import com.evgen.Guest;

public interface HotelDao {

  List getHotels();

  List getHotelByName(String name);

  Guest getGuestByName(String name);

  List getReservations(String guestId);

  Guest deleteReservation(String guestId, String reservationId);

}
