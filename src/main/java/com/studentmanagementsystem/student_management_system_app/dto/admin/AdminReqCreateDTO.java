package com.studentmanagementsystem.student_management_system_app.dto.admin;

import com.studentmanagementsystem.student_management_system_app.enums.LoginAuthProviderType;
import com.studentmanagementsystem.student_management_system_app.enums.Role;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AdminReqCreateDTO extends AdminBaseReq_E_DTO {

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 6, max = 20, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_6_20_CHARACTERS)
  private String password;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String name;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 1, message = Constants.Messages.Validation.AT_LEAST_ONE_ROLE_IS_REQUIRED)
  private Set<Role> roles;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private LoginAuthProviderType loginAuthProviderType;
}
