package com.studentmanagementsystem.student_management_system_app.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ExceptionCustomResourceDuplicate extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1794546133507459823L;

  private final Object data;

  public ExceptionCustomResourceDuplicate() {
    super();
    this.data = null;
  }

  public ExceptionCustomResourceDuplicate(Throwable cause) {
    super(cause);
    this.data = null;
  }

  public ExceptionCustomResourceDuplicate(String message) {
    super(message);
    this.data = null;
  }

  public ExceptionCustomResourceDuplicate(String message, Throwable cause) {
    super(message, cause);
    this.data = null;
  }

  public ExceptionCustomResourceDuplicate(String message, Object data) {
    super(message);
    this.data = data;
  }

  public ExceptionCustomResourceDuplicate(String message, Object data, Throwable cause) {
    super(message, cause);
    this.data = data;
  }
}
