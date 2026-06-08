package com.studentmanagementsystem.student_management_system_app.entity;

import com.studentmanagementsystem.student_management_system_app.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Builder.Default
  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private Set<Student> students = new HashSet<>();

  @Column(name = "course_name", nullable = false)
  private String courseName;

  @Column(name = "description", nullable = false)
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "course_type", nullable = false)
  private CourseType courseType;

  @Column(name = "duration", nullable = false)
  private Integer duration;

  @Column(name = "topics", nullable = false)
  private String topics;
}
