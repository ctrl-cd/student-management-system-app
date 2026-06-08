package com.studentmanagementsystem.student_management_system_app.service.iface;

import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResBasicInfoDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminServiceInterface {

  Admin createAdmin(Admin admin, boolean isPasswordHashingEnabled);

  AdminResBasicInfoDTO createAdmin(AdminReqCreateDTO adminReqCreateDTO);

  List<Admin> getAllAdmins();

  Page<Admin> getAllAdmins(Pageable pageable);

  List<AdminResDTO> getAllAdminResDTOS();

  PaginatedResDTO<AdminResDTO> getAllAdminResDTOS(int page, int size, List<String> sortParams);

  Admin getAdminById(UUID id, boolean isThrowExceptionIfResourceNotFound);

  AdminResDTO getAdminResDTOById(UUID id, boolean isThrowExceptionIfResourceNotFound);

  Optional<Admin> getAdminOptionalByEmail(String email, boolean isThrowExceptionIfResourceNotFound);

  Admin getAdminByEmail(String email, boolean isThrowExceptionIfResourceNotFound);

  boolean isExistsById(UUID id, boolean isThrowExceptionIfResourceNotFound);
}
