package com.hrms.backend.dtos.globalDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageableDto {
    private int pageNumber;
    private int limit;
}
