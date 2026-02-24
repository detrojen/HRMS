package com.hrms.backend.dtos.requestDto.post;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
public class PostQueryParamsDto extends PageableDto {
    private String query = "";
    private LocalDate postFrom ;
    private LocalDate postTo;
    public PostQueryParamsDto(String query,int page,int limit,LocalDate postFrom,LocalDate postTo){
        super(page,limit);
        this.query = query;
        this.postFrom = postFrom;
        this.postTo = postTo;
    }
    public PostQueryParamsDto(String query,int page,int limit){
        super(page,limit);
        this.query = query;
    }
}
