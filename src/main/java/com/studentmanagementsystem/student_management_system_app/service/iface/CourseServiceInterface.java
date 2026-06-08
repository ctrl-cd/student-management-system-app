package com.studentmanagementsystem.student_management_system_app.service.iface;

import com.studentmanagementsystem.student_management_system_app.dto.course.CourseReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourseServiceInterface {

  Course createCourse(Course course);

  CourseResDTO createCourse(CourseReqCreateDTO courseReqCreateDTO);

  List<Course> getAllCourses();

  Page<Course> getAllCourses(Pageable pageable);

  Course getCourseById(UUID id, boolean isThrowExceptionIfResourceNotFound);
}
