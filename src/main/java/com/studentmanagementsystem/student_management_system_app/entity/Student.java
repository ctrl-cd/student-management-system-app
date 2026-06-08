package com.studentmanagementsystem.student_management_system_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studentmanagementsystem.student_management_system_app.enums.Gender;
import com.studentmanagementsystem.student_management_system_app.util.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.*;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Builder.Default
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Address> addresses = new ArrayList<>();

  @Builder.Default
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
  private Set<Course> courses = new HashSet<>();

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "date_of_birth", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormats.YYYY_MM_DD)
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  private Gender gender;

  @Column(name = "student_code", unique = true, nullable = false)
  private String studentCode;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "mobile_number", unique = true, nullable = false)
  private String mobileNumber;

  @Column(name = "parent_name", nullable = false)
  private String parentName;
}
