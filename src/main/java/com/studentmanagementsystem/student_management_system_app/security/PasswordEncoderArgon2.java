package com.studentmanagementsystem.student_management_system_app.security;

import com.studentmanagementsystem.student_management_system_app.service.iface.PasswordHashServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderArgon2 implements PasswordEncoder {

  private final PasswordHashServiceInterface passwordHashServiceInterface;

  @Override
  public String encode(CharSequence rawPassword) {
    return passwordHashServiceInterface.passwordHash(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return passwordHashServiceInterface.isPasswordCheckEquals(rawPassword.toString(), encodedPassword);
  }
}
