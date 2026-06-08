package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.common.PaginationInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaginationInfoMapper {

  @Mapping(target = "pageNumber", expression = "java(page.getPageable().getPageNumber())")
  @Mapping(target = "pageSize", expression = "java(page.getPageable().getPageSize())")
  PaginationInfoDTO toPaginationInfoDTOFromPage(Page<?> page);
}
