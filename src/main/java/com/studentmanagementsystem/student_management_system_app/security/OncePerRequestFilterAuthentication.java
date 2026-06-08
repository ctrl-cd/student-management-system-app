package com.studentmanagementsystem.student_management_system_app.security;

import com.studentmanagementsystem.student_management_system_app.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OncePerRequestFilterAuthentication extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsServiceImpl;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
      // Continue the filter chain, pass the request/response to the next filter in the chain
      filterChain.doFilter(request, response);
      return;
    }

    String tokenAccess;
    String email;
    try {
      tokenAccess = headerAuthorization.substring(7);
      email = jwtUtil.getUsernameFromToken(tokenAccess, true);
    }
    catch (Exception exception) {
      // Continue the filter chain, pass the request/response to the next filter in the chain
      filterChain.doFilter(request, response); // or return error
      return;
    }

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
      if (userDetails != null && jwtUtil.isTokenAccessValid(tokenAccess, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                                                                                                                          userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }

    // Continue the filter chain, pass the request/response to the next filter in the chain
    filterChain.doFilter(request, response);
  }
}
