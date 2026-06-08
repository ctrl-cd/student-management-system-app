package com.studentmanagementsystem.student_management_system_app.repository;

import com.studentmanagementsystem.student_management_system_app.entity.Admin;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

  @Query("""
          SELECT p FROM Student p
          WHERE p.name = :name
          """)
  Page<Student> findAllByName(@Param("name") String name, Pageable pageable);
}
