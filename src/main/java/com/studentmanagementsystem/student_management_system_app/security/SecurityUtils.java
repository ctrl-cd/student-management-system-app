package com.studentmanagementsystem.student_management_system_app.security;

import com.studentmanagementsystem.student_management_system_app.config.EnvConfig;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SecurityUtils {

  private final EnvConfig envConfig;

  public UserDetailsImpl getCurrentUserDetailsImplAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetailsImpl) {
      UserDetailsImpl userDetailsImpl = (UserDetailsImpl) principal;
      return userDetailsImpl;
    }
    return null;
  }

  public Admin getCurrentAdminAuthenticated() {
    UserDetailsImpl userDetailsImpl = getCurrentUserDetailsImplAuthenticated();
    if (userDetailsImpl == null) {
      return null;
    }
    Admin currentAdmin = userDetailsImpl.getAdmin();
    return currentAdmin;
  }

  public UUID getCurrentAdminIdAuthenticated() {
    UserDetailsImpl userDetailsImpl = getCurrentUserDetailsImplAuthenticated();
    if (userDetailsImpl == null) {
      return null;
    }
    Admin currentAdmin = userDetailsImpl.getAdmin();
    return currentAdmin.getId();
  }
}
