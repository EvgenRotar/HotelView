package com.evgen.wrapper;

import org.springframework.stereotype.Component;

@Component
public class ReservationId {

  private String id;

  private String guestId;

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
