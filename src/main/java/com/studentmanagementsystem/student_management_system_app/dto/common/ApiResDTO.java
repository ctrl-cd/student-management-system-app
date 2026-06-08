package com.studentmanagementsystem.student_management_system_app.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ApiResDTO<T> {

  @Builder.Default
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormats.YYYY_MM_DD_T_HH_MM_SS_Z, timezone = Constants.TimezoneType.UTC)
  private Instant timestamp = Instant.now();

  private Integer statusCode;

  private Boolean success;

  private String message;

  private T data;

  private Object errors;
}
