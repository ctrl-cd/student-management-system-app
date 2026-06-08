package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResBasicInfoDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdminMapper {

  @Mapping(target = "password", ignore = true)
  Admin toAdminEntityFromAdminResDTO(AdminResDTO adminResDTO);

  AdminResDTO toAdminResDTOFromAdminEntity(Admin admin);

  AdminResBasicInfoDTO toAdminResBasicInfoDTOFromAdminEntity(Admin admin);

  @Mapping(target = "id", ignore = true)
  Admin toAdminEntityFromAdminReqSignUpDTO(AdminReqCreateDTO adminReqCreateDTO);

  List<AdminResDTO> toAdminResDTOListFromAdminEntityList(List<Admin> admins);

  default PaginatedResDTO<AdminResDTO> toPaginatedResDTOAdminResDTOListFromPaginatedDetails(Page<Admin> adminPage, PaginatedResMapper paginatedResMapper, PaginationInfoMapper paginationInfoMapper) {
    List<AdminResDTO> adminResDTOS = toAdminResDTOListFromAdminEntityList(adminPage.getContent());
    PaginatedResDTO<AdminResDTO> paginatedResDTO = paginatedResMapper.toPaginatedResDTOFromPaginatedDetails(adminPage, adminResDTOS, paginationInfoMapper);
    return paginatedResDTO;
  }
}
