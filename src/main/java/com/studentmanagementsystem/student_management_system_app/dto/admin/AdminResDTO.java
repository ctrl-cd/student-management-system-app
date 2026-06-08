package com.studentmanagementsystem.student_management_system_app.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.enums.LoginAuthProviderType;
import com.studentmanagementsystem.student_management_system_app.enums.Role;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import lombok.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AdminResDTO {

  private UUID id;

  private String email;

  private String name;

  private Set<Role> roles;

  //  @JsonFormat(pattern = "dd-MMM-yyyy hh:mm:ss a") // Not recommended convert for local timezone in Java - It should convert in frontend as per local timezone
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormats.YYYY_MM_DD_T_HH_MM_SS_Z, timezone = Constants.TimezoneType.UTC)
  private Instant createdAt;

  private LoginAuthProviderType loginAuthProviderType;
}
