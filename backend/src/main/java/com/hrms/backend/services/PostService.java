package com.hrms.backend.services;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.CreatePostRequestDto;
import com.hrms.backend.dtos.requestDto.deleteUnappropriatedPostRequestDto;
import com.hrms.backend.dtos.responseDtos.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.PostResponseDto;
import com.hrms.backend.entities.Employee;
import com.hrms.backend.entities.Post;
import com.hrms.backend.exceptions.InvalidDeleteAction;
import com.hrms.backend.exceptions.PostNotFound;
import com.hrms.backend.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {
    private final PostRepository _postRepository;
    private final EmployeeService _employeeService;
    private final ModelMapper _modelMapper;
    @Autowired
    public PostService(PostRepository postRepository, EmployeeService employeeService,ModelMapper modelMapper){
        _postRepository = postRepository;
        _employeeService = employeeService;
        _modelMapper = modelMapper;
    }

    public List<PostResponseDto> getPosts(){
        return _postRepository.findPostsByIsDeleted(false).stream().map(e -> _modelMapper.map(e, PostResponseDto.class)).toList();
    }

    public PostResponseDto createPost(CreatePostRequestDto postRequestDto, String attachmentpath){
        Post post = _modelMapper.map(postRequestDto,Post.class);
        post.setAttachmentPath(attachmentpath);
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = _employeeService.getEmployeeById(jwtInfo.getUserId());
        post.setCreatedBy(employee);
        post = _postRepository.save(post);
        return _modelMapper.map(post,PostResponseDto.class);
    }

    public DeletePostResponseDto deletePost(Long id){
        Post post = _postRepository.findById(id).orElseThrow(()->new PostNotFound());
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = _employeeService.getEmployeeById(jwtInfo.getUserId());
        if(!post.getCreatedBy().getId().equals(employee.getId())){
            throw new InvalidDeleteAction();
        }
        post.setDeletedBy(employee);
        post.setDeleted(true);
        _postRepository.save(post);
        return _modelMapper.map(post,DeletePostResponseDto.class);
    }

    public Post deleteUnappropriatedPost(deleteUnappropriatedPostRequestDto requestDto){
        Post post = _postRepository.findById(requestDto.getId()).orElseThrow(()->new PostNotFound());
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = _employeeService.getEmployeeById(jwtInfo.getUserId());
        post.setDeletedBy(employee);
        post.setRemarkForDelete(requestDto.getRemark());
        post.setDeleted(true);
        post = _postRepository.save(post);
        return post;
    }

}
