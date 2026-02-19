package com.hrms.backend.dtos.responseDtos.post;

import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentsAndLikesDto {
    private Long id;
    private String title;
    private String body;
    private String attachmentPath;
    private String tags;
    private EmployeeMinDetailsDto createdBy;
    private int likeCount;
    private int commentCount;
    private List<CommentResponseDto> recentComments;
    private List<EmployeeMinDetailsDto> recentLikedBy;
}
