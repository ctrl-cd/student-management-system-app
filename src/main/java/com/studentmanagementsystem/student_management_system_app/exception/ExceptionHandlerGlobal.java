package com.studentmanagementsystem.student_management_system_app.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.enums.Role;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerGlobal {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResDTO<?>> handleException(Exception exception) {
    //    exception.printStackTrace();
    Utils.systemOutPrint("exception *****", exception.getMessage(), exception);
    String message = exception.getCause() != null && exception.getCause().getMessage() != null ? exception.getCause()
                                                                                                          .getMessage() : exception.getMessage() != null ? exception.getMessage() : exception.getCause() != null && exception.getCause()
                                                                                                                                                                                                                             .toString() != null ? exception.getCause()
                                                                                                                                                                                                                                                            .toString() : exception.toString() != null ? exception.toString() : Constants.Messages.Error.INTERNAL_SERVER_ERROR;
    Utils.systemOutPrint("message *****", message);

    String stackTraceAsString = ExceptionUtils.getStackTrace(exception);
    ApiResDTO<?> apiResDTO = ApiResponseFactory.internalServerError(message, null, stackTraceAsString);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResDTO);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResDTO<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
    List<Map<String, String>> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream().map(fieldError -> {
      String message = Objects.requireNonNull(fieldError.getDefaultMessage());
      return Map.of("field", fieldError.getField(), "message", message);
    }).toList();
    Utils.systemOutPrint("fieldErrors *****", fieldErrors);

    Map<String, Object> errorBody = Map.of("fields", fieldErrors);
    //    ApiResDTO<?> apiResDto = ApiResDTO.<?>builder().statusCode(HttpStatus.BAD_REQUEST.value()).success(false).message("Validation failed").data(null).errors(errorBody).build();
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest(Constants.Messages.Validation.VALIDATION_FAILED, null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiResDTO<?>> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
    List<Map<String, String>> fieldErrors = constraintViolationException.getConstraintViolations().stream().map(constraintViolation -> {
      String field = constraintViolation.getPropertyPath().toString();
      for (Path.Node node : constraintViolation.getPropertyPath()) {
        field = node.getName(); // will overwrite until last
      }
      String message = constraintViolation.getMessage();
      return Map.of("field", field, "message", message);
    }).collect(Collectors.toList());

    Map<String, Object> errorBody = Map.of("fields", fieldErrors);
    //    ApiResDTO<?> apiResDto = ApiResDTO.<?>builder().statusCode(HttpStatus.BAD_REQUEST.value()).success(false).message("Constraint violation").data(null).errors(errorBody).build();
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Constraint violation", null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResDTO<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
    String fieldName = methodArgumentTypeMismatchException.getName(); // usually the method param name, e.g. "id"

    String message = "This field should be of type " + Objects.requireNonNull(methodArgumentTypeMismatchException.getRequiredType()).getSimpleName();

    List<Map<String, String>> fieldErrors = List.of(Map.of("field", fieldName, "message", message));
    Map<String, Object> errorBody = Map.of("fields", fieldErrors);
    //    ApiResDTO<?> apiResDto = ApiResDTO.<?>builder().statusCode(HttpStatus.BAD_REQUEST.value()).success(false).message("Type mismatch").data(null).errors(errorBody).build();
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Type mismatch", null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(ExceptionCustomResourceNotFound.class)
  public ResponseEntity<ApiResDTO<?>> handleExceptionCustomResourceNotFound(ExceptionCustomResourceNotFound exceptionCustomResourceNotFound) {
    String message = exceptionCustomResourceNotFound.getMessage() != null ? exceptionCustomResourceNotFound.getMessage() : Constants.Messages.Error.ENTRY_NOT_FOUND;
    ApiResDTO<?> apiResDto = ApiResponseFactory.notFound(message, null, null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResDto);
  }

  @ExceptionHandler(InvalidFormatException.class)
  public ResponseEntity<ApiResDTO<?>> handleInvalidFormatException(InvalidFormatException invalidFormatException) {
    String fieldName = invalidFormatException.getPath().stream().map(JsonMappingException.Reference::getFieldName).findFirst().orElse("unknown");

    if (invalidFormatException.getTargetType().equals(Role.class)) {
      String stringRoleValues = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.joining(", "));

      String message = "This field should be of type " + Objects.requireNonNull(invalidFormatException.getTargetType())
                                                                .getSimpleName() + " - " + stringRoleValues;
      Map<String, Object> errorBody = Map.of("fields", List.of(Map.of("field", fieldName, "message", message)));
      ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Type mismatch", null, errorBody);
      return ResponseEntity.badRequest().body(apiResDto);
    }

    Map<String, Object> errorBody = Map.of("fields", List.of(Map.of("field", fieldName, "message", "Type mismatch")));
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Type mismatch", null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(ExceptionCustomNullPointer.class)
  public ResponseEntity<ApiResDTO<?>> handleExceptionCustomNullPointer(ExceptionCustomNullPointer exceptionCustomNullPointer) {
    String message = exceptionCustomNullPointer.getMessage() != null ? exceptionCustomNullPointer.getMessage() : Constants.Messages.Validation.PLEASE_PROVIDE_A_DATA_TO_PROCESS;
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest(message, null, null);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResDto);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiResDTO<?>> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
    String message = Constants.Messages.Validation.ENTRY_ALREADY_EXISTS;
    String fieldName = "unknown";

    Throwable throwable = ExceptionUtils.getRootCause(dataIntegrityViolationException);
    if (throwable != null && throwable.getMessage() != null) {
      String rootMsg = throwable.getMessage();
      Pattern pattern = Pattern.compile("Duplicate entry '(.+?)' for key '(?:.+\\.)?(\\w+)'");
      Matcher matcher = pattern.matcher(rootMsg);
      if (matcher.find()) {
        String value = matcher.group(1);
        fieldName = matcher.group(2);
        message = String.format("This field already has an existing entry '%s'", value);
      }
    }
    if (dataIntegrityViolationException.getMostSpecificCause().getMessage() != null) {
      message = dataIntegrityViolationException.getMostSpecificCause().getMessage();
    }

    Map<String, Object> errorBody = Map.of("fields", List.of(Map.of("field", fieldName, "message", message)));
    ApiResDTO<?> apiResDto = ApiResponseFactory.conflict(Constants.Messages.Validation.ENTRY_ALREADY_EXISTS, null, errorBody);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResDto);
  }

  @ExceptionHandler(ExceptionCustomInternalServerError.class)
  public ResponseEntity<ApiResDTO<?>> handleExceptionCustomInternalServerError(ExceptionCustomInternalServerError exceptionCustomInternalServerError) {
    String message = exceptionCustomInternalServerError.getMessage() != null ? exceptionCustomInternalServerError.getMessage() : Constants.Messages.Validation.PLEASE_PROVIDE_A_DATA_TO_PROCESS;
    ApiResDTO<?> apiResDto = ApiResponseFactory.internalServerError(message, null, null);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResDto);
  }

  @ExceptionHandler(ExceptionCustomResourceDuplicate.class)
  public ResponseEntity<ApiResDTO<?>> handleExceptionCustomResourceDuplicate(ExceptionCustomResourceDuplicate exceptionCustomResourceDuplicate) {
    String message = Constants.Messages.Validation.DUPLICATE_ENTRY_FOUND;
    String fieldName = "unknown";

    Pattern pattern = Pattern.compile("Duplicate entry '(.+?)' for key '(?:.+\\.)?(\\w+)'");
    Matcher matcher = pattern.matcher(exceptionCustomResourceDuplicate.getMessage());
    if (matcher.find()) {
      String value = matcher.group(1);
      fieldName = matcher.group(2);
      message = String.format("This field contains a duplicate or an already existing entry '%s'", value);
    }

    Map<String, Object> errorBody = Map.of("fields", List.of(Map.of("field", fieldName, "message", message)));
    ApiResDTO<?> apiResDto = ApiResponseFactory.conflict(Constants.Messages.Validation.DUPLICATE_ENTRY_FOUND, null, errorBody);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResDto);
  }

  @ExceptionHandler(ExceptionCustomUnauthorized.class)
  public ResponseEntity<ApiResDTO<?>> handleExceptionCustomUnauthorized(ExceptionCustomUnauthorized exceptionCustomUnauthorized) {
    String message = exceptionCustomUnauthorized.getMessage() != null ? exceptionCustomUnauthorized.getMessage() : Constants.Messages.Validation.INVALID_CREDENTIALS;

    ApiResDTO<?> apiResDto = ApiResponseFactory.unauthorized(message, null, null);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResDto);
  }

  @ExceptionHandler(PropertyReferenceException.class)
  public ResponseEntity<ApiResDTO<?>> handleMethodPropertyReferenceException(PropertyReferenceException propertyReferenceException) {
    String fieldName = null;
    try {
      fieldName = propertyReferenceException.getPropertyName();
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception - ", exception);
    }

    if (fieldName == null || fieldName.isBlank()) {
      String messageTemp = propertyReferenceException.getMessage();
      Pattern pattern = Pattern.compile("No property '(.+?)' found");
      Matcher matcher = pattern.matcher(messageTemp);
      if (matcher.find()) {
        fieldName = matcher.group(1);
      }
      else {
        fieldName = "unknown";
      }
    }

    String message = propertyReferenceException.getMessage();
    Map<String, Object> errorBody = Map.of("fields", List.of(Map.of("field", fieldName, "message", message)));
    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Property validation failed", null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResDTO<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
    String fieldName = "unknown";
    String message = "This field must be valid";

    Throwable throwableCause = httpMessageNotReadableException.getCause();
    if (throwableCause instanceof MismatchedInputException mismatchedInputException) {
      message = mismatchedInputException.getOriginalMessage();

      if (!mismatchedInputException.getPath().isEmpty()) {
        fieldName = mismatchedInputException.getPath().getFirst().getFieldName();
      }

      if (mismatchedInputException.getTargetType() != null && Set.class.isAssignableFrom(mismatchedInputException.getTargetType())) {
        message = String.format("Field %s should be of type array", fieldName);
      }

      if (throwableCause instanceof InvalidFormatException invalidFormatException && invalidFormatException.getTargetType() != null && invalidFormatException.getTargetType()
                                                                                                                                                             .isEnum()) {
        message = String.format("Field %s should be %s", fieldName, Arrays.toString(invalidFormatException.getTargetType().getEnumConstants()));
      }
    }
    else if (httpMessageNotReadableException != null && httpMessageNotReadableException.getMessage() != null) {
      Utils.systemOutPrint("httpMessageNotReadableException 1 *****", httpMessageNotReadableException.getMessage());
      //      Utils.systemOutPrint("httpMessageNotReadableException 2 *****", httpMessageNotReadableException.getCause().getMessage());
      message = httpMessageNotReadableException.getMessage();
    }

    List<Map<String, String>> fieldErrors = List.of(Map.of("field", fieldName, "message", message));
    Map<String, Object> errorBody = Map.of("fields", fieldErrors);

    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest(Constants.Messages.Validation.VALIDATION_FAILED, null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiResDTO<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException missingServletRequestParameterException) {
    String fieldName = missingServletRequestParameterException.getParameterName();
    String message = missingServletRequestParameterException.getMessage();

    List<Map<String, String>> fieldErrors = List.of(Map.of("field", fieldName, "message", message));
    Map<String, Object> errorBody = Map.of("fields", fieldErrors);

    ApiResDTO<?> apiResDto = ApiResponseFactory.badRequest("Missing request parameter", null, errorBody);
    return ResponseEntity.badRequest().body(apiResDto);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResDTO<?>> handleNoResourceFoundException(NoResourceFoundException noResourceFoundException) {
    ApiResDTO<?> apiResDto = ApiResponseFactory.notFound(Constants.Messages.Error.NO_RESOURCE_FOUND, null, noResourceFoundException.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResDto);
  }
}
