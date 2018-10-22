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
public class HotelDaoImpl {

  private Connector connector;

  @Value("${url.getHotels}")
  private String getHotelsUrl;

  @Value("${url.getGuestByName}")
  private String getGuestByNameUrl;

  @Value("${url.getReservations}")
  private String getReservationsUrl;

  @Autowired
  public HotelDaoImpl(Connector connector) {
    this.connector = connector;
  }

  public List getHotels() {
    return connector.sendRequestWithoutBody(new HttpHeaders(), getHotelsUrl, HttpMethod.GET, ArrayList.class);
  }

  public Guest getGuestByName(String name) {
    return connector.sendRequestWithoutBody(new HttpHeaders(), getGuestByNameUrl + name, HttpMethod.GET, Guest.class);
  }



}
