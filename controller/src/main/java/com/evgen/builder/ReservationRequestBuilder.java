package com.evgen.builder;

import com.evgen.ReservationRequest;
import com.evgen.wrapper.EditReservation;

public class ReservationRequestBuilder {

  public static ReservationRequest build(EditReservation editReservation) {
    return ReservationRequest.builder()
        .guestId(editReservation.getGuestId())
        .hotelName(editReservation.getHotelName())
        .apartmentNumber(editReservation.getApartmentNumber())
        .startReservationData(editReservation.getStartReservationData())
        .endReservationData(editReservation.getEndReservationData())
        .build();
  }
}