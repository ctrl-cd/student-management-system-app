package com.studentmanagementsystem.student_management_system_app.util;

import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import org.springframework.http.HttpStatus;

public class ApiResponseFactory {

  public static <T> ApiResDTO<T> ok(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.OK.value()).success(true).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> ok(String message, T data) {
    return ok(message, data, null);
  }

  public static <T> ApiResDTO<T> noContent(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.NO_CONTENT.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> noContent(String message, T data) {
    return noContent(message, data, null);
  }

  public static <T> ApiResDTO<T> unauthorized(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.UNAUTHORIZED.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> unauthorized(String message, T data) {
    return unauthorized(message, data, null);
  }

  public static <T> ApiResDTO<T> conflict(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.CONFLICT.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> conflict(String message, T data) {
    return conflict(message, data, null);
  }

  public static <T> ApiResDTO<T> created(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.CREATED.value()).success(true).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> created(String message, T data) {
    return created(message, data, null);
  }

  public static <T> ApiResDTO<T> badRequest(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.BAD_REQUEST.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> badRequest(String message, T data) {
    return badRequest(message, data, null);
  }

  public static <T> ApiResDTO<T> notFound(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.NOT_FOUND.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> notFound(String message, T data) {
    return notFound(message, data, null);
  }

  public static <T> ApiResDTO<T> internalServerError(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> internalServerError(String message, T data) {
    return internalServerError(message, data, null);
  }

  public static <T> ApiResDTO<T> forbidden(String message, T data, Object errors) {
    return ApiResDTO.<T>builder().statusCode(HttpStatus.FORBIDDEN.value()).success(false).message(message).data(data).errors(errors).build();
  }

  public static <T> ApiResDTO<T> forbidden(String message, T data) {
    return forbidden(message, data, null);
  }
}
