package com.studentmanagementsystem.student_management_system_app.service.impl;

import com.studentmanagementsystem.student_management_system_app.dto.course.CourseReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Course;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import com.studentmanagementsystem.student_management_system_app.exception.ExceptionCustomResourceNotFound;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginatedResMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginationInfoMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.CourseMapper;
import com.studentmanagementsystem.student_management_system_app.repository.CourseRepository;
import com.studentmanagementsystem.student_management_system_app.service.iface.CourseServiceInterface;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import com.studentmanagementsystem.student_management_system_app.util.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseServiceInterface {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final PaginationInfoMapper paginationInfoMapper;
  private final PaginatedResMapper paginatedResMapper;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  @Transactional
  public Course createCourse(Course course) {
    try {
      Course courseSaved = courseRepository.save(course);
      return courseSaved;
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception *****", exception);
      throw exception;
    }
  }

  @Override
  @Transactional
  public CourseResDTO createCourse(CourseReqCreateDTO courseReqCreateDTO) {
    Course course = courseMapper.toCourseEntityFromCourseReqCreateDTO(courseReqCreateDTO);
    Course courseSaved = createCourse(course);
    CourseResDTO courseResDTO = courseMapper.toCourseResDTOFromCourseEntity(courseSaved);
    return courseResDTO;
  }

  @Override
  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  @Override
  public Page<Course> getAllCourses(Pageable pageable) {
    return courseRepository.findAll(pageable);
  }

  @Override
  public Course getCourseById(UUID id, boolean isThrowExceptionIfResourceNotFound) {
    Utils.systemOutPrint("id *****", id);
    Optional<Course> courseOptional = courseRepository.findById(id);
    if (courseOptional.isEmpty()) {
      if (isThrowExceptionIfResourceNotFound) {
        throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + id);
      }
      else {
        return null;
      }
    }
    return courseOptional.get();
  }
}
