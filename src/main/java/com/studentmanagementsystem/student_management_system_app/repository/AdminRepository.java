package com.studentmanagementsystem.student_management_system_app.repository;

import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

  @Query(value = """
          SELECT * FROM admins
          WHERE email = :email LIMIT 1
          """, nativeQuery = true)
  Optional<Admin> findByEmail(@Param("email") String email);

  @Query(value = """
          SELECT EXISTS(SELECT 1 FROM admins WHERE email = :email)
          """, nativeQuery = true)
  int existsByEmail(@Param("email") String email);
}
