package com.hrms.backend.dtos.requestDto.post;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
public class PostQueryParamsDto extends PageableDto {
    private String query = "";
    private Date postFrom ;
    private Date postTo;
    public PostQueryParamsDto(String query,int page,int limit,Date postFrom,Date postTo){
        super(page,limit);
        this.query = query;
        if(postFrom != null){
            this.postFrom = postFrom;

        }
        if(postTo != null){

            this.postTo = postTo;
        }


    }
    public PostQueryParamsDto(String query,int page,int limit){
        super(page,limit);
        this.query = query;
    }
}
