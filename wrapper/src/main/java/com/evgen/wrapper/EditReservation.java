package com.evgen.wrapper;

import com.evgen.ReservationRequest;

public class EditReservation extends ReservationRequest {

  private String reservationId;

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }
}