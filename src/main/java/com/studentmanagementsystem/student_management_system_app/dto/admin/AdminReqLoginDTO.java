package com.studentmanagementsystem.student_management_system_app.dto.admin;

import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AdminReqLoginDTO extends AdminBaseReq_E_DTO {

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 6, max = 20, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_6_20_CHARACTERS)
  private String password;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private String deviceId;

  private Double latitude;

  private Double longitude;
}
