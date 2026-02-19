package com.hrms.backend.dtos.requestDto.post;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostQueryParamsDto extends PageableDto {
    private String query;
    public PostQueryParamsDto(String query,int page,int limit){
        super(page,limit);
        this.query = query;
    }
}
