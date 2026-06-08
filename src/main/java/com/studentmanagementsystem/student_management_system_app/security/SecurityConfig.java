package com.studentmanagementsystem.student_management_system_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = false, jsr250Enabled = false)
@Configuration
public class SecurityConfig {

  private final OncePerRequestFilterAuthentication oncePerRequestFilterAuthentication;
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final AuthenticationEntryPointExceptionHandling authenticationEntryPointExceptionHandling;
  private final PasswordEncoderArgon2 passwordEncoderArgon2;
  private final FilterCorsCustom filterCorsCustom;
  private final AccessDeniedHandlerExceptionHandling accessDeniedHandlerExceptionHandling;
  private final OncePerRequestFilterRequestURIAndMethodValidation oncePerRequestFilterRequestURIAndMethodValidation;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            //            .addFilterBefore(filterCorsCustom, ChannelProcessingFilter.class)
            .addFilterBefore(filterCorsCustom, SecurityContextHolderFilter.class)
            .addFilterBefore(oncePerRequestFilterRequestURIAndMethodValidation, FilterCorsCustom.class)
            .csrf(csrfConfigurerHttpSecurity -> csrfConfigurerHttpSecurity.disable())
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPointExceptionHandling)
                                                                                         .accessDeniedHandler(accessDeniedHandlerExceptionHandling))
            .authorizeHttpRequests(
                    authorizeHttpRequestsConfigurer -> authorizeHttpRequestsConfigurer.requestMatchers("/api/v1/tests/**", "/api/v1/auth/create-admin",
                                                                                                       "/api/v1/auth/login", "/api/v1/auth/logout",
                                                                                                       "/api/v1/auth/refresh-token", "/swagger-ui/**",
                                                                                                       "/v3/api-docs/**",
                                                                                                       "/actuator-student-management-system-app-dev/**",
                                                                                                       "/actuator-student-management-system-app-prod/**")
                                                                                      .permitAll()
                                                                                      //                                .requestMatchers("/api/v1/email-leads/**").hasRole("MARKETING") // Working
                                                                                      .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider()).addFilterBefore(oncePerRequestFilterAuthentication, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoderArgon2);
    return daoAuthenticationProvider;
  }

  @Bean
  @Primary
  public PasswordEncoder passwordEncoder() {
    return passwordEncoderArgon2;
  }
}
