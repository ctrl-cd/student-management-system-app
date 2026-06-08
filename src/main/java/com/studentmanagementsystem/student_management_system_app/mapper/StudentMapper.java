package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentReqUpdateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.student.StudentResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {AddressMapper.class, CourseMapper.class})
public interface StudentMapper {

  Student toStudentEntityFromStudentReqCreateDTO(StudentReqCreateDTO studentReqCreateDTO);

  StudentResDTO toStudentResDTOFromStudentEntity(Student student);

  List<StudentResDTO> toStudentResDTOListFromStudentEntityList(List<Student> products);

  @Mapping(target = "id", ignore = true)
  void updateStudentEntityFromStudentReqUpdateDTOExcludingId(StudentReqUpdateDTO studentReqUpdateDTO, @MappingTarget Student studentEntityTarget);

  default PaginatedResDTO<StudentResDTO> toPaginatedResDTOStudentResDTOListFromPaginatedDetails(Page<Student> studentPage, PaginatedResMapper paginatedResMapper, PaginationInfoMapper paginationInfoMapper) {
    List<StudentResDTO> studentResDTOS = toStudentResDTOListFromStudentEntityList(studentPage.getContent());
    PaginatedResDTO<StudentResDTO> paginatedResDTO = paginatedResMapper.toPaginatedResDTOFromPaginatedDetails(studentPage, studentResDTOS,
                                                                                                              paginationInfoMapper);
    return paginatedResDTO;
  }
}
