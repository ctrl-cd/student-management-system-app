package com.studentmanagementsystem.student_management_system_app.service.impl;

import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResBasicInfoDTO;
import com.studentmanagementsystem.student_management_system_app.dto.admin.AdminResDTO;
import com.studentmanagementsystem.student_management_system_app.dto.common.PaginatedResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import com.studentmanagementsystem.student_management_system_app.exception.ExceptionCustomResourceDuplicate;
import com.studentmanagementsystem.student_management_system_app.exception.ExceptionCustomResourceNotFound;
import com.studentmanagementsystem.student_management_system_app.mapper.AdminMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginatedResMapper;
import com.studentmanagementsystem.student_management_system_app.mapper.PaginationInfoMapper;
import com.studentmanagementsystem.student_management_system_app.repository.AdminRepository;
import com.studentmanagementsystem.student_management_system_app.service.iface.AdminServiceInterface;
import com.studentmanagementsystem.student_management_system_app.service.iface.PasswordHashServiceInterface;
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
public class AdminServiceImpl implements AdminServiceInterface {

  private final AdminRepository adminRepository;
  private final AdminMapper adminMapper;
  private final PasswordHashServiceInterface passwordHashServiceInterface;
  private final PaginationInfoMapper paginationInfoMapper;
  private final PaginatedResMapper paginatedResMapper;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  @Transactional
  public Admin createAdmin(Admin admin, boolean isPasswordHashingEnabled) {
    try {
      if (isPasswordHashingEnabled && admin.getPassword() != null) {
        admin.setPassword(passwordHashServiceInterface.passwordHash(admin.getPassword()));
      }
      Admin adminSaved = adminRepository.save(admin);
      return adminSaved;
    }
    catch (Exception exception) {
      Utils.systemOutPrint("exception *****", exception);
      throw exception;
    }
  }

  @Override
  @Transactional
  public AdminResBasicInfoDTO createAdmin(AdminReqCreateDTO adminReqCreateDTO) {
    Admin admin = adminMapper.toAdminEntityFromAdminReqSignUpDTO(adminReqCreateDTO);
    Admin adminSaved = createAdmin(admin, true);
    AdminResBasicInfoDTO adminResBasicInfoDTO = adminMapper.toAdminResBasicInfoDTOFromAdminEntity(adminSaved);
    return adminResBasicInfoDTO;
  }

  @Override
  public List<Admin> getAllAdmins() {
    return adminRepository.findAll();
  }

  @Override
  public Page<Admin> getAllAdmins(Pageable pageable) {
    return adminRepository.findAll(pageable);
  }

  @Override
  public List<AdminResDTO> getAllAdminResDTOS() {
    List<Admin> admins = getAllAdmins();
    List<AdminResDTO> adminResDTOS = new ArrayList<>();
    adminResDTOS = adminMapper.toAdminResDTOListFromAdminEntityList(admins);
    return adminResDTOS;
  }

  @Override
  public PaginatedResDTO<AdminResDTO> getAllAdminResDTOS(int page, int size, List<String> sortParams) {
    Sort sort = Utils.sortParamsParse(sortParams);
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Admin> adminPage = getAllAdmins(pageable);

    PaginatedResDTO<AdminResDTO> adminResDTOsPaginatedResDTO = adminMapper.toPaginatedResDTOAdminResDTOListFromPaginatedDetails(adminPage, paginatedResMapper,
                                                                                                                                paginationInfoMapper);
    return adminResDTOsPaginatedResDTO;
  }

  @Override
  public Admin getAdminById(UUID id, boolean isThrowExceptionIfResourceNotFound) {
    Optional<Admin> adminOptional = adminRepository.findById(id);
    if (adminOptional.isEmpty()) {
      if (isThrowExceptionIfResourceNotFound) {
        throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + id);
      }
      else {
        return null;
      }
    }
    return adminOptional.get();
  }

  @Override
  public AdminResDTO getAdminResDTOById(UUID id, boolean isThrowExceptionIfResourceNotFound) {
    Admin admin = getAdminById(id, isThrowExceptionIfResourceNotFound);
    AdminResDTO adminResDTO = adminMapper.toAdminResDTOFromAdminEntity(admin);
    return adminResDTO;
  }

  @Override
  public Optional<Admin> getAdminOptionalByEmail(String email, boolean isThrowExceptionIfResourceNotFound) {
    Optional<Admin> adminOptional = adminRepository.findByEmail(email);
    if (adminOptional.isEmpty() && isThrowExceptionIfResourceNotFound) {
      throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + email);
    }
    return adminOptional;
  }

  @Override
  public Admin getAdminByEmail(String email, boolean isThrowExceptionIfResourceNotFound) {
    Optional<Admin> adminOptional = adminRepository.findByEmail(email);
    if (adminOptional.isEmpty()) {
      if (isThrowExceptionIfResourceNotFound) {
        throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + email);
      }
      else {
        return null;
      }
    }
    return adminOptional.get();
  }

  @Override
  public boolean isExistsById(UUID id, boolean isThrowExceptionIfResourceNotFound) {
    boolean isExists = adminRepository.existsById(id);
    if (!isExists && isThrowExceptionIfResourceNotFound) {
      throw new ExceptionCustomResourceNotFound(Constants.Messages.Error.ENTRY_NOT_FOUND + " - " + id);
    }
    return isExists;
  }
}
