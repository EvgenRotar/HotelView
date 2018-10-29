package com.evgen.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.evgen.ReservationRequest;
import com.evgen.builder.ReservationRequestBuilder;
import com.evgen.wrapper.EditReservation;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReservationRequestBuilderTest {

  private static final String RESERVATION_REQUEST = "/Reservation-request.json";
  private static final String UPDATE_RESERVATION_REQUEST = "/Update-reservation-request.json";

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void reservationRequestBuilder() throws IOException {
    ReservationRequest reservationRequest = objectMapper
        .readValue(getClass().getResourceAsStream(RESERVATION_REQUEST), ReservationRequest.class);
    EditReservation editReservation = objectMapper
        .readValue(getClass().getResourceAsStream(UPDATE_RESERVATION_REQUEST), EditReservation.class);

    ReservationRequest reservationRequestTest = ReservationRequestBuilder.build(editReservation);

    Assert.assertEquals(reservationRequest.getHotelName(), reservationRequestTest.getHotelName());
  }
}
