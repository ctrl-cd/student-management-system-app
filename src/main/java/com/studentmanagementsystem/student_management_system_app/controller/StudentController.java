package com.studentmanagementsystem.student_management_system_app.controller;

import com.studentmanagementsystem.student_management_system_app.dto.common.ApiReqPageableDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.ApiResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.mapper.StudentMapper;
import com.studentmanagementsystem.student_management_system_app.service.iface.StudentServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.ApiResponseFactory;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@Tag(name = "Student", description = "APIs for managing students")
@RequestMapping("/api/v1/students")
@RestController
public class StudentController {

  private final StudentServiceInterface studentServiceInterface;
  private final StudentMapper studentMapper;

  //  @HasAnyRoleAdminSeller
  @PostMapping(path = "/create-student", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<StudentResDTO>> createStudent(@Valid @RequestBody StudentReqCreateDTO studentReqCreateDTO) {
    Utils.systemOutPrint("studentReqCreateDTO *****", studentReqCreateDTO);
    StudentResDTO studentResDTO = studentServiceInterface.createStudent(studentReqCreateDTO);
    ApiResDTO<StudentResDTO> apiResDTO = ApiResponseFactory.created(Constants.Messages.Success.SUCCESSFULLY_CREATED_THE_ENTRY, studentResDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(apiResDTO);
  }

  @GetMapping(path = "/get-students/all/paginated/sort-multiple", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<PaginatedResDTO<StudentResDTO>>> getStudentsAllPaginatedSortMultiple(@Valid @ModelAttribute ApiReqPageableDTO apiReqPageableDTO) {
    PaginatedResDTO<StudentResDTO> studentResDTOPaginatedResDTO = studentServiceInterface.getAllStudentResDTOS(apiReqPageableDTO.getPage(),
                                                                                                               apiReqPageableDTO.getSize(),
                                                                                                               apiReqPageableDTO.getSortParams());
    String message = studentResDTOPaginatedResDTO.getContent()
                                                 .isEmpty() ? Constants.Messages.Error.ENTRY_NOT_FOUND : Constants.Messages.Success.SUCCESSFULLY_FETCHED_THE_ENTRIES;
    ApiResDTO<PaginatedResDTO<StudentResDTO>> apiResDTO = ApiResponseFactory.ok(message, studentResDTOPaginatedResDTO);
    return ResponseEntity.ok(apiResDTO);
  }

  @GetMapping(path = "/search-student-by-name/paginated/sort-multiple", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<PaginatedResDTO<StudentResDTO>>> searchStudentByNamePaginatedSortMultiple(@Valid @RequestParam @NotBlank(message = Constants.Messages.Validation.THIS_FIELD_IS_REQUIRED) String name, @Valid @ModelAttribute ApiReqPageableDTO apiReqPageableDto) {
    PaginatedResDTO<StudentResDTO> studentResDTOsPaginatedResDto = studentServiceInterface.getStudentResDTOSByName(name, apiReqPageableDto.getPage(),
                                                                                                                   apiReqPageableDto.getSize(),
                                                                                                                   apiReqPageableDto.getSortParams());
    String message = studentResDTOsPaginatedResDto.getContent()
                                                  .isEmpty() ? Constants.Messages.Error.ENTRY_NOT_FOUND : Constants.Messages.Success.SUCCESSFULLY_FETCHED_THE_ENTRIES;
    ApiResDTO<PaginatedResDTO<StudentResDTO>> apiResDto = ApiResponseFactory.ok(message, studentResDTOsPaginatedResDto);
    return ResponseEntity.ok(apiResDto);
  }

  @PutMapping(path = "/leave-course/{idStudent}/course/{idCourse}", headers = Constants.Headers.ACCEPT_APPLICATION_JSON, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResDTO<StudentResDTO>> leaveCourse(@PathVariable UUID idStudent, @PathVariable UUID idCourse) {
    StudentResDTO studentResDTO = studentServiceInterface.updateStudentById(idStudent, idCourse, false, true);
    ApiResDTO<StudentResDTO> apiResDTO = ApiResponseFactory.ok(Constants.Messages.Success.SUCCESSFULLY_UPDATED_THE_ENTRY, studentResDTO);
    return ResponseEntity.ok(apiResDTO);
  }
}
