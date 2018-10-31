package com.evgen;

import javax.validation.constraints.NotNull;

public class ReservationRequest {

  @NotNull(message = "ApartmentNumber cannot be null")
  private String apartmentNumber;

  @NotNull(message = "GuestId cannot be null")
  private String guestId;

  @NotNull(message = "HotelName cannot be null")
  private String hotelName;

  @NotNull(message = "StartReservationData cannot be null")
  private String startReservationData;

  @NotNull(message = "EndReservationData cannot be null")
  private String endReservationData;

  public static Builder builder() {
    return new Builder();
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public void setApartmentNumber(String apartmentId) {
    this.apartmentNumber = apartmentId;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public String getStartReservationData() {
    return startReservationData;
  }

  public void setStartReservationData(String startReservationData) {
    this.startReservationData = startReservationData;
  }

  public String getEndReservationData() {
    return endReservationData;
  }

  public void setEndReservationData(String endReservationData) {
    this.endReservationData = endReservationData;
  }

  public static class Builder {

    private ReservationRequest instance;

    Builder() {
      this.instance = new ReservationRequest();
    }

    public Builder guestId(String guestId) {
      instance.setGuestId(guestId);
      return this;
    }

    public Builder apartmentNumber(String apartmentNumber) {
      instance.setApartmentNumber(apartmentNumber);
      return this;
    }

    public Builder hotelName(String hotelName) {
      instance.setHotelName(hotelName);
      return this;
    }

    public Builder startReservationData(String startReservationData) {
      instance.setStartReservationData(startReservationData);
      return this;
    }

    public Builder endReservationData(String endReservationData) {
      instance.setEndReservationData(endReservationData);
      return this;
    }

    public ReservationRequest build() {
      return instance;
    }
  }
}