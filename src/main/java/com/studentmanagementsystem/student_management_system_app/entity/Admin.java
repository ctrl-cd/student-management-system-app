package com.studentmanagementsystem.student_management_system_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.enums.LoginAuthProviderType;
import com.studentmanagementsystem.student_management_system_app.enums.Role;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "admins")
public class Admin {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "name")
  private String name;

  @Builder.Default
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Set<Role> roles = new HashSet<>();

  @CreatedDate
  @Column(name = "created_at", updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormats.YYYY_MM_DD_T_HH_MM_SS_Z, timezone = Constants.TimezoneType.UTC)
  private Instant createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "login_auth_provider_login_type", nullable = false)
  private LoginAuthProviderType loginAuthProviderType;
}
