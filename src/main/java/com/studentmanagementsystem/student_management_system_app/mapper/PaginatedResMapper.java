package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {PaginationInfoMapper.class})
public interface PaginatedResMapper {

  default <T, E> PaginatedResDTO<T> toPaginatedResDTOFromPaginatedDetails(Page<E> page, List<T> content, PaginationInfoMapper paginationInfoMapper) {
    return PaginatedResDTO.<T>builder().content(content).pageable(paginationInfoMapper.toPaginationInfoDTOFromPage(page)).build();
  }
}
