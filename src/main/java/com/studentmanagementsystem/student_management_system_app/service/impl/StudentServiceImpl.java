package com.studentmanagementsystem.student_management_system_app.service.impl;

import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Address;
import com.studentmanagementsystem.student_management_system_app.entity.Course;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import com.studentmanagementsystem.student_management_system_app.exception.ExceptionCustomResourceNotFound;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginatedResMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginationInfoMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.StudentMapper;
import com.studentmanagementsystem.student_management_system_app.repository.StudentRepository;
import com.studentmanagementsystem.student_management_system_app.service.iface.CourseServiceInterface;
import com.studentmanagementsystem.student_management_system_app.service.iface.StudentServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentServiceInterface {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  private final PaginationInfoMapper paginationInfoMapper;
  private final PaginatedResMapper paginatedResMapper;
  private final CourseServiceInterface courseServiceInterface;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  @Transactional
  public Student createStudent(Student student) {
    try {
      student.setStudentCode(Utils.generateStudentCodeUnique());

      if (student.getAddresses() != null) {
        List<Address> addresses = new ArrayList<>(student.getAddresses());
        student.getAddresses().clear();
        for (Address address : addresses) {
          address.setStudent(student);
          student.getAddresses().add(address);
        }
      }
      Student studentSaved = studentRepository.save(student);
      return studentSaved;
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception *****", exception);
      throw exception;
    }
  }

  @Override
  @Transactional
  public StudentResDTO createStudent(StudentReqCreateDTO studentReqCreateDTO) {
    Student student = studentMapper.toStudentEntityFromStudentReqCreateDTO(studentReqCreateDTO);
    Student studentSaved = createStudent(student);
    StudentResDTO studentResDTO = studentMapper.toStudentResDTOFromStudentEntity(studentSaved);
    return studentResDTO;
  }

  @Override
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  @Override
  public Page<Student> getAllStudents(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }

  @Override
  public PaginatedResDTO<StudentResDTO> getAllStudentResDTOS(int page, int size, List<String> sortParams) {
    Sort sort = Utils.sortParamsParse(sortParams);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Student> studentPage = getAllStudents(pageable);
    PaginatedResDTO<StudentResDTO> studentResDTOsPaginatedResDTO = studentMapper.toPaginatedResDTOStudentResDTOListFromPaginatedDetails(studentPage,
                                                                                                                                        paginatedResMapper,
                                                                                                                                        paginationInfoMapper);
    return studentResDTOsPaginatedResDTO;
  }

  @Override
  public PaginatedResDTO<StudentResDTO> getStudentResDTOSByName(String name, int page, int size, List<String> sortParams) {
    Sort sort = Utils.sortParamsParse(sortParams);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Student> StudentPage = studentRepository.findAllByName(name, pageable);

    PaginatedResDTO<StudentResDTO> studentResDTOsPaginatedResDTO = studentMapper.toPaginatedResDTOStudentResDTOListFromPaginatedDetails(StudentPage,
                                                                                                                                        paginatedResMapper,
                                                                                                                                        paginationInfoMapper);
    return studentResDTOsPaginatedResDTO;
  }

  @Override
  public Student getStudentById(UUID id, boolean isThrowExceptionIfResourceNotFound) {
    Utils.systemOutPrint("id *****", id);
    Optional<Student> studentOptional = studentRepository.findById(id);
    if (studentOptional.isEmpty()) {
      if (isThrowExceptionIfResourceNotFound) {
        throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + id);
      }
      else {
        return null;
      }
    }
    return studentOptional.get();
  }

  @Transactional
  public StudentResDTO updateStudentById(UUID idStudent, UUID idCourse, boolean isAddCourse, boolean isThrowExceptionIfResourceNotFound) {
    Student studentExisting = getStudentById(idStudent, isThrowExceptionIfResourceNotFound);
    Course course = courseServiceInterface.getCourseById(idCourse, isThrowExceptionIfResourceNotFound);
    if (isAddCourse) {
      studentExisting.getCourses().add(course);
    }
    else {
      studentExisting.getCourses().remove(course);
    }
    Student studentSaved = studentRepository.save(studentExisting);
    StudentResDTO studentResDTO = studentMapper.toStudentResDTOFromStudentEntity(studentSaved);
    return studentResDTO;
  }
}
