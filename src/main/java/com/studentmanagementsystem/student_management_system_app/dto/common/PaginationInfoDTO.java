package com.studentmanagementsystem.student_management_system_app.dto.common;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class PaginationInfoDTO {

  private Integer pageNumber;

  private Integer pageSize;

  private Integer totalPages;

  private Long totalElements;

  private Boolean first;

  private Boolean last;
}
