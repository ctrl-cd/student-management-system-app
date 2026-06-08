package com.studentmanagementsystem.student_management_system_app.dto.admin;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AdminResTokensDTO {

  private String tokenAccess;
  private String tokenRefresh;
  private AdminResDTO admin;
}
