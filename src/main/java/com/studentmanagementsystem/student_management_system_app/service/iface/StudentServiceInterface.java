package com.studentmanagementsystem.student_management_system_app.service.iface;

import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface StudentServiceInterface {

  Student createStudent(Student student);

  StudentResDTO createStudent(StudentReqCreateDTO studentReqCreateDTO);

  List<Student> getAllStudents();

  Page<Student> getAllStudents(Pageable pageable);

  PaginatedResDTO<StudentResDTO> getAllStudentResDTOS(int page, int size, List<String> sortParams);

  PaginatedResDTO<StudentResDTO> getStudentResDTOSByName(String name, int page, int size, List<String> sortParams);

  Student getStudentById(UUID id, boolean isThrowExceptionIfResourceNotFound);

  StudentResDTO updateStudentById(UUID idStudent, UUID idCourse, boolean isAddCourse, boolean isThrowExceptionIfResourceNotFound);
}
