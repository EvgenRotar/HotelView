package com.evgen.wrapper;

import org.springframework.stereotype.Component;

@Component
public class GuestName {

  private String name;


  public GuestName(){
  }

  public GuestName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
