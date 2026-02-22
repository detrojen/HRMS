package com.hrms.backend.services.PostServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.post.DeleteUnappropriatedContentRequestDto;
import com.hrms.backend.dtos.requestDto.post.PostCommentRequestDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.dtos.responseDtos.post.DeletePostResponseDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.PostEntities.PostComment;
import com.hrms.backend.exceptions.InvalidDeleteAction;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.exceptions.PostNotFound;
import com.hrms.backend.repositories.PostRepositories.PostCommentRepository;
import com.hrms.backend.services.EmailServices.EmailService;
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
    private final EmailService emailService;
    public PostCommentService(PostCommentRepository postCommentRepository,ModelMapper modelMapper, EmployeeService employeeService,EmailService emailService){
        this.modelMapper = modelMapper;
        this.postCommentRepository = postCommentRepository;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }

    public List<CommentResponseDto> getRecentComments(Long postId){
        List<PostComment> comments = postCommentRepository.findAllByPost_IdAndIsDeletedAndIsDeletedByHr(postId,false,false, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<CommentResponseDto>  response = comments
                .stream()
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

    public boolean deleteUnappropriatedPostComment(DeleteUnappropriatedContentRequestDto requestDto){
        PostComment comment = postCommentRepository.findById(requestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("comment not found"));
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getEmployeeById(jwtInfo.getUserId());
        comment.setDeletedBy(employee);
        comment.setRemark(requestDto.getRemark());
        comment.setDeleted(true);
        comment.setDeletedByHr(true);
        comment.getPost().setCommentCount(comment.getPost().getCommentCount() - 1);
        comment = postCommentRepository.save(comment);
        emailService.sendMail(
                "Warning: unappropriate comment",
                "comment\n"+comment.getComment() + "\n\nremark for delete: \n"+requestDto.getRemark()
                ,new String[]{comment.getCommentedBy().getEmail()}
                ,new String[]{}
        );
        return true;
    }
    public Post deletePostComment(Long id){
        PostComment comment = postCommentRepository.findById(id).orElseThrow(()->new PostNotFound());
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = employeeService.getEmployeeById(jwtInfo.getUserId());
        if(!comment.getCommentedBy().getId().equals(employee.getId())){
            throw new InvalidDeleteAction();
        }
        comment.setDeletedBy(employee);
        comment.setDeleted(true);
        postCommentRepository.save(comment);
        return comment.getPost();
    }


}
