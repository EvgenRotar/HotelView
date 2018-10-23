package com.evgen.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.evgen.Guest;
import com.evgen.connector.Connector;

@Component
public class HotelDaoImpl implements HotelDao {

  private Connector connector;

  @Value("${url.getHotels}")
  private String getHotelsUrl;

  @Value("${url.getGuestByName}")
  private String getGuestByNameUrl;

  @Value("${url.getReservations}")
  private String getReservationsUrl;

  @Value("${url.getHotelByName}")
  private String getHotelByNameUrl;

  @Value("${url.deleteReservation}")
  private String deleteReservationUrl;

  @Autowired
  public HotelDaoImpl(Connector connector) {
    this.connector = connector;
  }

  @Override
  public List getHotels() {
    return connector.sendRequestWithoutBody(new HttpHeaders(), getHotelsUrl, HttpMethod.GET, ArrayList.class);
  }

  @Override
  public List getHotelByName(String name) {
    return connector.sendRequestWithoutBody(new HttpHeaders(), getHotelByNameUrl + name, HttpMethod.GET, ArrayList.class);
  }

  @Override
  public Guest getGuestByName(String name) {
    return connector.sendRequestWithoutBody(new HttpHeaders(), getGuestByNameUrl + name, HttpMethod.GET, Guest.class);
  }

  @Override
  public List getReservations(String guestId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("guestId", guestId);
    return connector.sendRequestWithoutBody(headers, getReservationsUrl, HttpMethod.GET, ArrayList.class);
  }

  @Override
  public Guest deleteReservation(String guestId, String reservationId) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("guestId", guestId);
    return connector.sendRequestWithoutBody(headers, deleteReservationUrl + reservationId, HttpMethod.DELETE, Guest.class);
  }
}
