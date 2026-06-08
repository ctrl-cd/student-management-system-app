package com.studentmanagementsystem.student_management_system_app.controller;

import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.service.iface.AdminServiceInterface;
import com.studentmanagementsystem.student_management_system_app.service.iface.StudentServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Tag(name = "Admin", description = "APIs for managing admins")
@RequestMapping("/api/v1/admins")
@RestController
public class AdminController {

  private final AdminServiceInterface adminServiceInterface;
  private final StudentServiceInterface studentServiceInterface;

  @GetMapping(path = "/get-admins/all", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<List<AdminResDTO>>> getAdminsAll() {
    List<AdminResDTO> adminResDTOS = adminServiceInterface.getAllAdminResDTOS();
    String message = adminResDTOS.isEmpty() ? Constants.Messages.Error.ENTRY_NOT_FOUND : Constants.Messages.Success.SUCCESSFULLY_FETCHED_THE_ENTRIES;
    ApiResDTO<List<AdminResDTO>> apiResDto = ApiResponseFactory.ok(message, adminResDTOS);
    return ResponseEntity.ok(apiResDto);
  }

  @PutMapping(path = "/assign-courses-to-students/{idStudent}/courses/{idCourse}", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<StudentResDTO>> assignCoursesToStudents(@PathVariable UUID idStudent, @PathVariable UUID idCourse, @RequestParam boolean isAddCourse) {
    StudentResDTO studentResDTO = studentServiceInterface.updateStudentById(idStudent, idCourse, isAddCourse, true);
    ApiResDTO<StudentResDTO> apiResDTO = ApiResponseFactory.ok(Constants.Messages.Success.SUCCESSFULLY_UPDATED_THE_ENTRY, studentResDTO);
    return ResponseEntity.ok(apiResDTO);
  }
}
