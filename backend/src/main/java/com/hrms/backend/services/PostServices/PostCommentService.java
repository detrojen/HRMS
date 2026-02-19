package com.hrms.backend.services.PostServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.post.PostCommentRequestDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.PostEntities.PostComment;
import com.hrms.backend.repositories.PostRepositories.PostCommentRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeService employeeService;
    public PostCommentService(PostCommentRepository postCommentRepository,ModelMapper modelMapper, EmployeeService employeeService){
        this.modelMapper = modelMapper;
        this.postCommentRepository = postCommentRepository;
        this.employeeService = employeeService;
    }

    public List<CommentResponseDto> getRecentComments(Long postId){
        List<PostComment> comments = postCommentRepository.findAllByPost_Id(postId, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<CommentResponseDto>  response = comments
                .stream().limit(3)
                .map(comment->modelMapper.map(comment,CommentResponseDto.class))
                .collect(Collectors.toUnmodifiableList());
        return response;
    }

    public CommentResponseDto commentOn(Post post, PostCommentRequestDto requestDto){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostComment comment = new PostComment();
        comment.setComment(requestDto.getComment());
        comment.setPost(post);
        comment.setCommentedBy(employeeService.getReference(jwtInfoDto.getUserId()));
        comment = postCommentRepository.save(comment);
        return modelMapper.map(comment,CommentResponseDto.class);
    }

}
