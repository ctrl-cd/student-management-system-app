package com.studentmanagementsystem.student_management_system_app.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.dto.address.AddressReqUpdateDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Address;
import com.studentmanagementsystem.student_management_system_app.enums.Gender;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StudentReqUpdateDTO {

  @Valid
  @NotEmpty(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private List<AddressReqUpdateDTO> addresses;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String name;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Past(message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_VALID)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormats.YYYY_MM_DD)
  private LocalDate dateOfBirth;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private Gender gender;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^[\\w]([\\w.%+-]*[\\w])?@[\\w]([\\w.-]*[\\w])?\\.[a-z]{2,}$", message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_VALID)
  private String email;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^[6-9][0-9]{9}$", message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_VALID)
  private String mobileNumber;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String parentName;
}
