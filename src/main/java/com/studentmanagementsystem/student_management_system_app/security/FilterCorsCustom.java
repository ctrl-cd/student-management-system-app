package com.studentmanagementsystem.student_management_system_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.studentmanagementsystem.student_management_system_app.config.EnvConfig;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FilterCorsCustom implements Filter {

  private final ObjectMapper objectMapper;
  //  private final JsonMapper jsonMapper;
  private final EnvConfig envConfig;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    String origin = httpServletRequest.getHeader("Origin");

    if (origin != null && envConfig.getCorsAllowedOrigins().contains(origin)) {
      // Set CORS headers for valid origins
      httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
      httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
      httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
      httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");

      // Handle preflight request
      if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return;
      }

      filterChain.doFilter(servletRequest, servletResponse);
    }
    else if (origin != null && !envConfig.getCorsAllowedOrigins().contains(origin)) {
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());

      ApiResDTO<?> apiResDto = ApiResponseFactory.forbidden(Constants.Messages.Validation.INVALID_CORS_REQUEST, null);

      httpServletResponse.getWriter().write(objectMapper.writeValueAsString(apiResDto));
      httpServletResponse.getWriter().flush();
    }
    else {
      // No origin header — likely a non-browser request
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }
}
