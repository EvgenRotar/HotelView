package com.evgen;

import java.io.Serializable;

public class Message implements Serializable {

  private String id;
  private String endPoint;
  private Object requestObject;

  public Message() {
  }

  public Message(String id, String endPoint, Object requestObject) {
    this.id = id;
    this.endPoint = endPoint;
    this.requestObject = requestObject;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public Object getRequestObject() {
    return requestObject;
  }

  public void setRequestObject(Object requestObject) {
    this.requestObject = requestObject;
  }
}
