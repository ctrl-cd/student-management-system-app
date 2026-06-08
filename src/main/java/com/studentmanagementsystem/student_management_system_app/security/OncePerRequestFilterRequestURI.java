package com.studentmanagementsystem.student_management_system_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
//public class OncePerRequestFilterRequestURIAndMethodValidation extends OncePerRequestFilter {
public class OncePerRequestFilterRequestURI extends OncePerRequestFilter {

  private final RequestMappingHandlerMapping requestMappingHandlerMapping;
  private final ObjectMapper objectMapper;
  //  private final JsonMapper jsonMapper;
  private final AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    String requestMethod = request.getMethod();
    Set<RequestMappingInfo> requestMappingInfos = requestMappingHandlerMapping.getHandlerMethods().keySet();

    boolean isMatchesPathAndMethod = requestMappingInfos.stream().anyMatch(requestMappingInfo -> {
      boolean isMatchesPath = requestMappingInfo.getPathPatternsCondition().getPatterns().stream()
                                                .anyMatch(pathPattern -> antPathMatcher.match(pathPattern.toString(), requestURI));
      boolean isMatchesMethod = requestMappingInfo.getMethodsCondition().getMethods().stream()
                                                  .anyMatch(reqMethod -> reqMethod.toString().equalsIgnoreCase(requestMethod));
      return isMatchesPath && isMatchesMethod;
    });

    if (!isMatchesPathAndMethod) {
      ApiResDTO<?> apiResDTO = ApiResponseFactory.notFound(Constants.Messages.Error.NO_RESOURCE_FOUND, null,
                                                           Constants.Messages.Error.NO_STATIC_RESOURCE + " " + requestURI);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setStatus(HttpStatus.NOT_FOUND.value());
      response.getWriter().write(objectMapper.writeValueAsString(apiResDTO));
      return;
    }

    // Continue the filter chain, pass the request/response to the next filter in the chain
    filterChain.doFilter(request, response);
  }
}
