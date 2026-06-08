package com.studentmanagementsystem.student_management_system_app.dto.address;

import com.studentmanagementsystem.student_management_system_app.enums.AddressType;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AddressResDTO {

  private UUID id;

  private AddressType addressType;

  private String lineOne;

  private String city;

  private String state;

  private String pinCode;
}
