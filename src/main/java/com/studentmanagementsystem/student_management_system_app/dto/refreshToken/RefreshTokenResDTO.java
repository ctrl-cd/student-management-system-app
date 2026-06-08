package com.studentmanagementsystem.student_management_system_app.dto.refreshToken;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class RefreshTokenResDTO {

  private String tokenAccess;
  private String tokenRefresh;
}
