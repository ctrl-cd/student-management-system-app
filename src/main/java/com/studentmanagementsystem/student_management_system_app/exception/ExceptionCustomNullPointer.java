package com.studentmanagementsystem.student_management_system_app.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExceptionCustomNullPointer extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5680183142877214090L;

  private final Object data;

  public ExceptionCustomNullPointer() {
    super();
    this.data = null;
  }

  public ExceptionCustomNullPointer(Throwable cause) {
    super(cause);
    this.data = null;
  }

  public ExceptionCustomNullPointer(String message) {
    super(message);
    this.data = null;
  }

  public ExceptionCustomNullPointer(String message, Throwable cause) {
    super(message, cause);
    this.data = null;
  }

  public ExceptionCustomNullPointer(String message, Object data) {
    super(message);
    this.data = data;
  }

  public ExceptionCustomNullPointer(String message, Object data, Throwable cause) {
    super(message, cause);
    this.data = data;
  }
}
