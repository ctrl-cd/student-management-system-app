package com.studentmanagementsystem.student_management_system_app.dto.course;

import com.studentmanagementsystem.student_management_system_app.enums.CourseType;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CourseReqCreateDTO {

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String courseName;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String description;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  private CourseType courseType;

  @NotNull(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Min(value = 1, message = "Duration must be at least 1 day")
  @Max(value = 1825, message = "Duration cannot exceed 5 year (in day)")
  private Integer duration;

  @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED)
  @Size(min = 2, max = 50, message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS)
  private String topics;
}
