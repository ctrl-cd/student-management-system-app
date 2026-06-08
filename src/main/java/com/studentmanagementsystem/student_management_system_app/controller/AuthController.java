package com.studentmanagementsystem.student_management_system_app.controller;

import com.studentmanagementsystem.student_management_system_app.config.EnvConfig;
import com.studentmanagementsystem.student_management_system_app.dto.admin.*;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.refreshToken.RefreshTokenReqDTO;
import com.studentmanagementsystem.student_management_system_app.dto.refreshToken.RefreshTokenResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import com.studentmanagementsystem.student_management_system_app.mapper.AdminMapper;
import com.studentmanagementsystem.student_management_system_app.security.SecurityUtils;
import com.studentmanagementsystem.student_management_system_app.security.UserDetailsImpl;
import com.studentmanagementsystem.student_management_system_app.security.jwt.JwtUtil;
import com.studentmanagementsystem.student_management_system_app.service.iface.AdminServiceInterface;
import com.studentmanagementsystem.student_management_system_app.service.iface.PasswordHashServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@Tag(name = "Auth", description = "APIs for admin authentication and authorization")
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

  private final AdminServiceInterface adminServiceInterface;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;
  private final AdminMapper adminMapper;
  private final PasswordHashServiceInterface passwordHashServiceInterface;
  private final EnvConfig envConfig;
  private final SecurityUtils securityUtils;

  @PostMapping(path = "/create-admin", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<AdminResBasicInfoDTO>> createAdmin(@Valid @RequestBody AdminReqCreateDTO adminRequestCreateDto) {
    Utils.systemOutPrint("envConfig.getSpringProfilesActive *****", envConfig.getSpringProfilesActive());
    AdminResBasicInfoDTO adminResBasicInfoDto = adminServiceInterface.createAdmin(adminRequestCreateDto);
    ApiResDTO<AdminResBasicInfoDTO> apiResDto = ApiResponseFactory.created(Constants.Messages.Success.SUCCESSFULLY_CREATED_THE_ENTRY, adminResBasicInfoDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResDto);
  }

  @PostMapping(path = "/login", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<AdminResTokensDTO>> login(@Valid @RequestBody AdminReqLoginDTO adminReqLoginDTO, HttpServletRequest httpServletRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(adminReqLoginDTO.getEmail(), adminReqLoginDTO.getPassword()));

      UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
      Admin admin = userDetailsImpl.getAdmin();

      String tokenAccess = jwtUtil.generateTokenAccess(admin);
      String tokenRefresh = jwtUtil.generateTokenRefresh(admin);

      AdminResDTO adminResDto = adminMapper.toAdminResDTOFromAdminEntity(admin);

      AdminResTokensDTO adminResTokensDTO = AdminResTokensDTO.builder().tokenAccess(tokenAccess).tokenRefresh(tokenRefresh).admin(adminResDto).build();
      ApiResDTO<AdminResTokensDTO> apiResDto = ApiResponseFactory.ok("Login successful", adminResTokensDTO);
      return ResponseEntity.ok(apiResDto);
    }
    catch (DataIntegrityViolationException dataIntegrityViolationException) {
      Utils.systemOutPrint("DataIntegrityViolationException *****", dataIntegrityViolationException);
      throw new DataIntegrityViolationException(String.format(Constants.Exceptions.DUPLICATE_ENTRY_S_FOR_KEY_S, "", ""));
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception *****", exception);
      ApiResDTO<AdminResTokensDTO> apiResDto = ApiResponseFactory.unauthorized(Constants.Messages.Validation.INVALID_CREDENTIALS, null);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResDto);
    }
  }

  @PostMapping(path = "/refresh-token", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<RefreshTokenResDTO>> refreshToken(@Valid @RequestBody RefreshTokenReqDTO refreshTokenReqDTO) {
    String tokenRefreshExisting = refreshTokenReqDTO.getTokenRefresh();

    String email = jwtUtil.getUsernameFromToken(tokenRefreshExisting, true);
    Admin admin = adminServiceInterface.getAdminByEmail(email, true);

    boolean isTokenRefreshValid = jwtUtil.isTokenRefreshValid(tokenRefreshExisting, admin);
    if (!isTokenRefreshValid) {
      ApiResDTO<RefreshTokenResDTO> apiResDto = ApiResponseFactory.unauthorized(Constants.Messages.Validation.INVALID_CREDENTIALS, null);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResDto);
    }
    else {
      String tokenAccessNew = jwtUtil.generateTokenAccess(admin);
      String tokenRefreshNew = jwtUtil.generateTokenRefresh(admin);

      RefreshTokenResDTO refreshTokenResDTO = RefreshTokenResDTO.builder().tokenAccess(tokenAccessNew).tokenRefresh(tokenRefreshNew).build();
      ApiResDTO<RefreshTokenResDTO> apiResDto = ApiResponseFactory.ok(Constants.Messages.Success.SUCCESSFULLY_UPDATED_THE_ENTRY, refreshTokenResDTO);
      return ResponseEntity.ok(apiResDto);
    }
  }
}
