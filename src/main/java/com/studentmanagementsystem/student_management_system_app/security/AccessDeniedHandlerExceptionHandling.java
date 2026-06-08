package com.studentmanagementsystem.student_management_system_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class AccessDeniedHandlerExceptionHandling implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;
  //  private final JsonMapper jsonMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    String message = accessDeniedException.getMessage();
    if (message == null || message.isBlank() || Objects.equals(message, "Access Denied")) {
      message = Constants.Messages.Validation.ACCESS_DENIED_INSUFFICIENT_ROLE_PRIVILEGES;
    }

    ApiResDTO<?> apiResDto = ApiResponseFactory.forbidden(message, null);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.getWriter().write(objectMapper.writeValueAsString(apiResDto));
  }
}
