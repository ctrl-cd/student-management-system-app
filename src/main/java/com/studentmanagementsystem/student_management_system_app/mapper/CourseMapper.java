package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.course.CourseReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseReqUpdateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.course.CourseResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CourseMapper {

  Course toCourseEntityFromCourseReqCreateDTO(CourseReqCreateDTO courseReqCreateDTO);

  CourseResDTO toCourseResDTOFromCourseEntity(Course course);

  @Mapping(target = "id", ignore = true)
  void updateCourseEntityFromCourseReqUpdateDTOExcludingId(CourseReqUpdateDTO courseReqUpdateDTO, @MappingTarget Course courseEntityTarget);
}
