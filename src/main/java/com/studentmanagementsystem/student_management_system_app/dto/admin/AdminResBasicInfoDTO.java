package com.studentmanagementsystem.student_management_system_app.dto.admin;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AdminResBasicInfoDTO {

  private UUID id;
  private String email;
  private String name;
}
