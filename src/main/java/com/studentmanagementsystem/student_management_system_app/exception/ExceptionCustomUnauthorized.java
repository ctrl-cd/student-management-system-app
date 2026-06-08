package com.studentmanagementsystem.student_management_system_app.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExceptionCustomUnauthorized extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -9043267375474215339L;

  private final Object data;

  public ExceptionCustomUnauthorized() {
    super();
    this.data = null;
  }

  public ExceptionCustomUnauthorized(Throwable cause) {
    super(cause);
    this.data = null;
  }

  public ExceptionCustomUnauthorized(String message) {
    super(message);
    this.data = null;
  }

  public ExceptionCustomUnauthorized(String message, Throwable cause) {
    super(message, cause);
    this.data = null;
  }

  public ExceptionCustomUnauthorized(String message, Object data) {
    super(message);
    this.data = data;
  }

  public ExceptionCustomUnauthorized(String message, Object data, Throwable cause) {
    super(message, cause);
    this.data = data;
  }
}
