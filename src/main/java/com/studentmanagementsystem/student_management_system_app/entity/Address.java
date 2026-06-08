package com.studentmanagementsystem.student_management_system_app.entity;

import com.studentmanagementsystem.student_management_system_app.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fk_student_id", nullable = false)
  private Student student;

  @Enumerated(EnumType.STRING)
  @Column(name = "address_type", nullable = false)
  private AddressType addressType;

  @Column(name = "line_one")
  private String lineOne;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "pin_code")
  private String pinCode;
}
