package com.company.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

  PING_FAILED("Ping failed.");

  private String message;

  ErrorMessage(String err) {
    this.message = err;
  }

  public String toString() {
    return message;
  }
}
