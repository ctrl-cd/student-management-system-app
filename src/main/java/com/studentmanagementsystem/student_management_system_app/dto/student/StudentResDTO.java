package com.studentmanagementsystem.student_management_system_app.dto.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.dto.address.AddressResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Address;
import com.studentmanagementsystem.student_management_system_app.entity.Course;
import com.studentmanagementsystem.student_management_system_app.enums.Gender;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StudentResDTO {

  private UUID id;

  private List<AddressResDTO> addresses;

  private Set<CourseResDTO> courses;

  private String name;

  private LocalDate dateOfBirth;

  private Gender gender;

  private String studentCode;

  private String email;

  private String mobileNumber;

  private String parentName;
}
