package com.studentmanagementsystem.student_management_system_app.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExceptionCustomInternalServerError extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -3351973865720784587L;

  private final Object data;

  public ExceptionCustomInternalServerError() {
    super();
    this.data = null;
  }

  public ExceptionCustomInternalServerError(Throwable cause) {
    super(cause);
    this.data = null;
  }

  public ExceptionCustomInternalServerError(String message) {
    super(message);
    this.data = null;
  }

  public ExceptionCustomInternalServerError(String message, Throwable cause) {
    super(message, cause);
    this.data = null;
  }

  public ExceptionCustomInternalServerError(String message, Object data) {
    super(message);
    this.data = data;
  }

  public ExceptionCustomInternalServerError(String message, Object data, Throwable cause) {
    super(message, cause);
    this.data = data;
  }
}
