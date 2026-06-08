package com.studentmanagementsystem.student_management_system_app.dto.common;

import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ApiReqPageableDTO {

  @Builder.Default
  @PositiveOrZero(message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0)
  private Integer page = 0;

  @Builder.Default
  @Positive(message = Constants.Messages.Validation.THIS_FIELD_MUST_BE_GREATER_THAN_OR_EQUAL_TO_1)
  private Integer size = 10;

  private List<String> sortParams;
}
