package com.studentmanagementsystem.student_management_system_app.dto.refreshToken;

import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class RefreshTokenReqDTO {

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private String tokenRefresh;
}
