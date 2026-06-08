package com.studentmanagementsystem.student_management_system_app.security;

import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import com.studentmanagementsystem.student_management_system_app.service.iface.AdminServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final AdminServiceInterface adminServiceInterface;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<Admin> optionalAdmin = adminServiceInterface.getAdminOptionalByEmail(email, true);
    if (optionalAdmin.isEmpty()) {
      return null;
    }
    return new UserDetailsImpl(optionalAdmin.get());
  }
}
