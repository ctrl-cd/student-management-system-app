package com.studentmanagementsystem.student_management_system_app.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExceptionCustomResourceNotFound extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 4499099471272429863L;

  private final Object data;

  public ExceptionCustomResourceNotFound() {
    super();
    this.data = null;
  }

  public ExceptionCustomResourceNotFound(Throwable cause) {
    super(cause);
    this.data = null;
  }

  public ExceptionCustomResourceNotFound(String message) {
    super(message);
    this.data = null;
  }

  public ExceptionCustomResourceNotFound(String message, Throwable cause) {
    super(message, cause);
    this.data = null;
  }

  public ExceptionCustomResourceNotFound(String message, Object data) {
    super(message);
    this.data = data;
  }

  public ExceptionCustomResourceNotFound(String message, Object data, Throwable cause) {
    super(message, cause);
    this.data = data;
  }
}
