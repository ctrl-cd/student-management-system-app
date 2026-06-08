package com.studentmanagementsystem.student_management_system_app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class EnvConfig {

  @Value("${SPRING_PROFILES_ACTIVE:dev}") // Default to 'dev' if not set
  private String springProfilesActive;

  @Value("${PASSWORD_HASH_PAPER}")
  private String passwordHashPaper;

  @Value("${JWT_SECRET_KEY}")
  private String jwtSecretKey;

  @Value("${JWT_TOKEN_ACCESS_EXPIRATION_MS}")
  private long jwtTokenAccessExpirationMs;

  @Value("${JWT_TOKEN_REFRESH_EXPIRATION_MS}")
  private long jwtTokenRefreshExpirationMs;

  @Value("${MAX_LIMIT_USER_LOGIN_ACTIVE_SESSION}")
  private int maxLimitUserLoginActiveSession;

  @Value("#{'${CORS_ALLOWED_ORIGINS}'.split(',')}")
  private List<String> corsAllowedOrigins;
}
