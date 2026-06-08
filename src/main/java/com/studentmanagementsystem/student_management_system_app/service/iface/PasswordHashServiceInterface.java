package com.studentmanagementsystem.student_management_system_app.service.iface;

public interface PasswordHashServiceInterface {

  String passwordHash(String passwordPlain);

  boolean isPasswordCheckEquals(String passwordPlain, String passwordHashed);
}
