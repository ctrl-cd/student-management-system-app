package com.studentmanagementsystem.student_management_system_app.dto.address;

import com.studentmanagementsystem.student_management_system_app.enums.AddressType;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AddressReqCreateDTO {

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private AddressType addressType;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String lineOne;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String city;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String state;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^[1-9][0-9]{5}$", message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_VALID)
  private String pinCode;
}
