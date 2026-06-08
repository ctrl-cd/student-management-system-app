package com.studentmanagementsystem.student_management_system_app.mapper;

import com.studentmanagementsystem.student_management_system_app.dto.address.AddressReqCreateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.address.AddressReqUpdateDTO;
import com.studentmanagementsystem.student_management_system_app.dto.address.AddressResDTO;
import com.studentmanagementsystem.student_management_system_app.entity.Address;
import com.studentmanagementsystem.student_management_system_app.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

  Address toAddressEntityFromAddressReqCreateDTO(AddressReqCreateDTO addressReqCreateDTO);

  AddressResDTO toAddressResDTOFromAddressEntity(Address address);

  List<AddressResDTO> toAddressResDTOListFromAddressListEntity(List<Address> addresses);

  Address toAddressEntityFromAddressReqUpdateDTO(AddressReqUpdateDTO addressReqUpdateDTO);

  @Mapping(target = "id", ignore = true)
  void updateAddressEntityTargetFromAddressReqUpdateDTOExcludingId(AddressReqUpdateDTO addressReqUpdateDTO, @MappingTarget Address addressEntityTarget);

  List<AddressResDTO> toAddressResDTOListFromAddressReqUpdateDTOList(List<AddressReqUpdateDTO> addressReqUpdateDTOS);

  AddressResDTO toAddressResDTOFromAddressReqUpdateDTO(AddressReqUpdateDTO addressReqUpdateDTO);

  /*default List<AddressResDTO> updateStudentWithAddressResDTOS(Student student, List<AddressReqUpdateDTO> addressReqUpdateDTOS) {
    if (addressReqUpdateDTOS == null || addressReqUpdateDTOS.isEmpty()) {
      return Collections.emptyList();
    }
    List<Address> addressesExisting = student.getAddresses();
    List<AddressResDTO> addressResDTOSNotFound = new ArrayList<>();

    for (AddressReqUpdateDTO addressReqUpdateDTO : addressReqUpdateDTOS) {
      UUID idAddressReqUpdateDTOTemp = addressReqUpdateDTO.getId();

      if (idAddressReqUpdateDTOTemp == null) {
        Address addressNew = toAddressEntityFromAddressReqUpdateDTO(addressReqUpdateDTO);
        addressNew.setStudent(student);
        student.getAddresses().add(addressNew);
      }
      else {
        Address addressMatched = null;
        for (Address addressExistingTemp : addressesExisting) {
          if (idAddressReqUpdateDTOTemp.equals(addressExistingTemp.getId())) {
            addressMatched = addressExistingTemp;
            break;
          }
        }

        if (addressMatched != null) {
          updateAddressEntityTargetFromAddressReqUpdateDTOExcludingId(addressReqUpdateDTO, addressMatched);
        }
        else {
          AddressResDTO addressResDTO = toAddressResDTOFromAddressReqUpdateDTO(addressReqUpdateDTO);
          addressResDTOSNotFound.add(addressResDTO);
        }
      }
    }
    return addressResDTOSNotFound;
  }*/
}
