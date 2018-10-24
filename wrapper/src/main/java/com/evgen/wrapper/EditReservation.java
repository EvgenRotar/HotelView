package com.evgen.wrapper;

public class EditReservation {

  private String reservationId;

  private String guestId;

  private String apartmentNumber;

  private String hotelName;

  private String startReservationData;

  private String endReservationData;

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
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
}
