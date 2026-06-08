package com.studentmanagementsystem.student_management_system_app.dto.common;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PaginatedResDTO<T> {

  private List<T> content;

  private PaginationInfoDTO pageable;
}
