package com.studentmanagementsystem.student_management_system_app.controller;

import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.mapper.CourseMapper;
import com.studentmanagementsystem.student_management_system_app.service.iface.CourseServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@Tag(name = "Course", description = "APIs for managing courses")
@RequestMapping("/api/v1/courses")
@RestController
public class CourseController {

  private final CourseServiceInterface courseServiceInterface;
  private final CourseMapper courseMapper;

  @PostMapping(path = "/create-course", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<CourseResDTO>> createCourse(@Valid @RequestBody CourseReqCreateDTO courseReqCreateDTO) {
    Utils.systemOutPrint("courseReqCreateDTO *****", courseReqCreateDTO);
    CourseResDTO courseResDTO = courseServiceInterface.createCourse(courseReqCreateDTO);
    ApiResDTO<CourseResDTO> apiResDTO = ApiResponseFactory.created(Constants.Messages.Success.SUCCESSFULLY_CREATED_THE_ENTRY, courseResDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResDTO);
  }
}
