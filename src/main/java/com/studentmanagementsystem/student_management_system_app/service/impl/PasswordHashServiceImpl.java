package com.studentmanagementsystem.student_management_system_app.service.impl;

import com.password4j.Hash;
import com.password4j.Password;
import com.studentmanagementsystem.student_management_system_app.config.EnvConfig;
import com.studentmanagementsystem.student_management_system_app.service.iface.PasswordHashServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PasswordHashServiceImpl implements PasswordHashServiceInterface {

  private final EnvConfig envConfig;

  public String passwordHash(String passwordPlain) {
    Hash hash = Password.hash(passwordPlain).addRandomSalt().addPepper(envConfig.getPasswordHashPaper()).withArgon2();
    return hash.getResult();
  }

  public boolean isPasswordCheckEquals(String passwordPlain, String passwordHashed) {
    if (passwordPlain == null || passwordHashed == null) {
      return false;
    }

    try {
      return Password.check(passwordPlain, passwordHashed).addPepper(envConfig.getPasswordHashPaper()).withArgon2();
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception -", exception);
      return false;
    }
  }
}
