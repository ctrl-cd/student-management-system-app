package com.studentmanagementsystem.student_management_system_app.dto.admin;

import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class AdminBaseReq_E_DTO {

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^[\\w]([\\w.%+-]*[\\w])?@[\\w]([\\w.-]*[\\w])?\\.[a-z]{2,}$", message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_VALID)
  private String email;
}
