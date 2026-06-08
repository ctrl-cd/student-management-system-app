package com.studentmanagementsystem.student_management_system_app.security.jwt;

import com.studentmanagementsystem.student_management_system_app.config.EnvConfig;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import com.studentmanagementsystem.student_management_system_app.exception.ExceptionCustomUnauthorized;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtUtil {

  private final EnvConfig envConfig;

  public String generateTokenAccess(Admin admin) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("token_type", "access");
    return Jwts.builder().claims(claims).subject(admin.getEmail()).id(UUID.randomUUID().toString()).issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + envConfig.getJwtTokenAccessExpirationMs()))
               .signWith(Keys.hmacShaKeyFor(envConfig.getJwtSecretKey().getBytes()), Jwts.SIG.HS256).compact();
  }

  public String generateTokenRefresh(Admin admin) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("token_type", "refresh");

    return Jwts.builder().claims(claims).subject(admin.getEmail()).id(UUID.randomUUID().toString()).issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + envConfig.getJwtTokenRefreshExpirationMs()))
               .signWith(Keys.hmacShaKeyFor(envConfig.getJwtSecretKey().getBytes()), Jwts.SIG.HS256).compact();
  }

  public Claims getAllClaimsFromToken(String token, boolean isThrowExceptionIfJwtTokenExpired) {
    try {
      return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(envConfig.getJwtSecretKey().getBytes())).build().parseSignedClaims(token).getPayload();
    }
    catch (ExpiredJwtException expiredJwtException) {
      if (isThrowExceptionIfJwtTokenExpired) {
        throw new ExceptionCustomUnauthorized(Constants.Messages.Validation.INVALID_OR_MISSING_TOKEN + " - " + token);
      }
      return expiredJwtException.getClaims();
    }
  }

  public String getUsernameFromToken(String token, boolean isThrowExceptionIfJwtTokenExpired) {
    return getAllClaimsFromToken(token, isThrowExceptionIfJwtTokenExpired).getSubject();
  }

  public String getJtiFromToken(String token, boolean isThrowExceptionIfJwtTokenExpired) {
    return getAllClaimsFromToken(token, isThrowExceptionIfJwtTokenExpired).getId();
  }

  private String getTokenType(String token, boolean isThrowExceptionIfJwtTokenExpired) {
    Object tokenType = getAllClaimsFromToken(token, isThrowExceptionIfJwtTokenExpired).get("token_type");
    return tokenType != null ? tokenType.toString() : null;
  }

  public boolean isTokenAccessValid(String tokenAccessString, UserDetails userDetails) {
    try {
      if (isTokenExpired(tokenAccessString)) {
        return false;
      }

      boolean isTokenTypeCorrect = "access".equals(getTokenType(tokenAccessString, true));
      if (!isTokenTypeCorrect) {
        return false;
      }

      String email = getUsernameFromToken(tokenAccessString, true);
      if (email == null || userDetails == null || !email.equals(userDetails.getUsername())) {
        return false;
      }

      String jti = getJtiFromToken(tokenAccessString, true);
      if (jti == null) {
        return false;
      }
      else {
        return true;
      }
    }
    catch (JwtException | IllegalArgumentException exception) {
      return false;
    }
  }

  public boolean isTokenRefreshValid(String tokenRefreshString, Admin admin) {
    try {
      if (isTokenExpired(tokenRefreshString)) {
        return false;
      }

      boolean isTokenTypeCorrect = "refresh".equals(getTokenType(tokenRefreshString, true));
      if (!isTokenTypeCorrect) {
        return false;
      }

      String email = getUsernameFromToken(tokenRefreshString, true);
      if (email == null || admin == null || !email.equals(admin.getEmail())) {
        return false;
      }

      String jti = getJtiFromToken(tokenRefreshString, true);
      if (jti == null) {
        return false;
      }
      else {
        return true;
      }
    }
    catch (JwtException | IllegalArgumentException exception) {
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    return getAllClaimsFromToken(token, false).getExpiration().before(new Date());
  }
}
