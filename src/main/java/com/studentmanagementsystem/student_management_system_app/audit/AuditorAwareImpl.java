package com.studentmanagementsystem.student_management_system_app.audit;

import com.studentmanagementsystem.student_management_system_app.security.SecurityUtils;
import com.studentmanagementsystem.student_management_system_app.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component("auditorAwareImpl")
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<UUID> {

  private final SecurityUtils securityUtils;

  @Override
  public Optional<UUID> getCurrentAuditor() {
    UserDetailsImpl userDetailsImpl = securityUtils.getCurrentUserDetailsImplAuthenticated();
    if (userDetailsImpl == null) {
      return Optional.empty();
    }
    return Optional.of(userDetailsImpl.getAdmin().getId());
  }
}
