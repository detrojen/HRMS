package com.hrms.backend.dtos.responseDtos.post;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
public class PostWithCommentsAndLikesDto {
    private Long id;
    private String title;
    private String body;
    private String attachmentPath;
    private String tags;
    private boolean isLiked;
    private EmployeeMinDetailsDto createdBy;

    private int likeCount;
    private int commentCount;
    private List<CommentResponseDto> recentComments;
    private List<EmployeeMinDetailsDto> recentLikedBy;

    public PostWithCommentsAndLikesDto(
            Long id,
            String title,
            String body,
            String attachmentPath,
            String tags,
            Long empId,
            Employee createdBy,
            int likeCount,
            int commentCount){
        ModelMapper modelMapper = new ModelMapper();
        this.id = id;
        this.title = title;
        this.body = body;
        this.attachmentPath = attachmentPath;
        this.tags = tags;
        this.isLiked = empId == null? false:true;
        this.createdBy = createdBy!=null?modelMapper.map(createdBy, EmployeeMinDetailsDto.class):null;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }
}
