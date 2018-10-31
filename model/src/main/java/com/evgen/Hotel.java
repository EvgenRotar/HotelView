package com.evgen;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Hotel {

  @Id
  private String hotelId;

  private String hotelName;

  private List<Apartment> apartments;

  public String getHotelId() {
    return hotelId;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public List<Apartment> getApartments() {
    return apartments;
  }

  public void setApartments(List<Apartment> apartments) {
    this.apartments = apartments;
  }
}