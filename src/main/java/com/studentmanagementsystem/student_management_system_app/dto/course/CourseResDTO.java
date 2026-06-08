package com.studentmanagementsystem.student_management_system_app.dto.course;

import com.studentmanagementsystem.student_management_system_app.enums.CourseType;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CourseResDTO {

  private UUID id;

  private String courseName;

  private String description;

  private CourseType courseType;

  private Integer duration;

  private String topics;
}
