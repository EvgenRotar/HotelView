package com.evgen.builder;

import com.evgen.wrapper.CreateReservation;
import com.evgen.wrapper.EditReservation;

public class ReservationRequestBuilder {
  public static CreateReservation build(EditReservation editReservation) {
    return CreateReservation.builder()
        .setGuestId(editReservation.getGuestId())
        .setHotelName(editReservation.getHotelName())
        .setApartmentNumber(editReservation.getApartmentNumber())
        .setStartReservationData(editReservation.getStartReservationData())
        .setEndReservationData(editReservation.getEndReservationData())
        .build();
  }
}
