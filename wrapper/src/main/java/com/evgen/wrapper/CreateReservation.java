package com.evgen.wrapper;

import org.springframework.stereotype.Component;

@Component
public class CreateReservation {

  private String guestId;

  private String apartmentNumber;

  private String hotelName;

  private String startReservationData;

  private String endReservationData;

  public static Builder builder() {
    return new Builder();
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
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

  public void setApartmentNumber(String apartmentNumber) {
    this.apartmentNumber = apartmentNumber;
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

    private CreateReservation instance;

    Builder() {
      this.instance = new CreateReservation();
    }

    public Builder setGuestId(String guestId) {
      instance.setGuestId(guestId);
      return this;
    }

    public Builder setApartmentNumber(String apartmentNumber) {
      instance.setApartmentNumber(apartmentNumber);
      return this;
    }

    public Builder setHotelName(String hotelName) {
      instance.setHotelName(hotelName);
      return this;
    }

    public Builder setStartReservationData(String startReservationData) {
      instance.setStartReservationData(startReservationData);
      return this;
    }

    public Builder setEndReservationData(String endReservationData) {
      instance.setEndReservationData(endReservationData);
      return this;
    }

    public CreateReservation build() {
      return instance;
    }
  }
}
