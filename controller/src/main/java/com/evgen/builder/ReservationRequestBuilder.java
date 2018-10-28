package com.evgen.builder;

import com.evgen.ReservationRequest;
import com.evgen.wrapper.EditReservation;

public class ReservationRequestBuilder {

  public static ReservationRequest build(EditReservation editReservation) {
    return ReservationRequest.builder()
        .setGuestId(editReservation.getGuestId())
        .setHotelName(editReservation.getHotelName())
        .setApartmentNumber(editReservation.getApartmentNumber())
        .setStartReservationData(editReservation.getStartReservationData())
        .setEndReservationData(editReservation.getEndReservationData())
        .build();
  }
}
