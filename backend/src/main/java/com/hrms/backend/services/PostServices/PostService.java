package com.hrms.backend.services.PostServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.requestDto.post.CreateUpdatePostRequestDto;
import com.hrms.backend.dtos.requestDto.post.CreateUpdateCommentRequestDto;
import com.hrms.backend.dtos.requestDto.post.DeleteUnappropriatedContentRequestDto;
import com.hrms.backend.dtos.requestDto.post.PostQueryParamsDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.dtos.responseDtos.post.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostWithCommentsAndLikesDto;
import com.hrms.backend.entities.EmployeeEntities.Employee;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.exceptions.InvalidActionException;
import com.hrms.backend.exceptions.InvalidDeleteAction;
import com.hrms.backend.exceptions.ItemNotFoundExpection;
import com.hrms.backend.exceptions.PostNotFound;
import com.hrms.backend.repositories.PostRepositories.PostRepository;
import com.hrms.backend.services.EmailServices.EmailService;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import com.hrms.backend.specs.PostSpecs;
import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class PostService {
    private final PostRepository _postRepository;
    private final EmployeeService _employeeService;
    private final ModelMapper _modelMapper;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final EmailService emailService;
    @Autowired
    public PostService(PostRepository postRepository,PostCommentService postCommentService,EmployeeService employeeService,ModelMapper modelMapper, PostLikeService postLikeService, EmailService emailService){
        _postRepository = postRepository;
        _employeeService = employeeService;
        _modelMapper = modelMapper;
        this.postCommentService = postCommentService;
        this.postLikeService = postLikeService;
        this.emailService = emailService;
    }

    public Page<PostWithCommentsAndLikesDto> getPosts(PostQueryParamsDto params){
        Specification<Post> specs = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(criteriaBuilder.isFalse(root.get("isDeleted")),criteriaBuilder.isFalse(root.get("isDeletedByHr")),criteriaBuilder.like(root.get("tags"),"%"+params.getQuery()+"%"));
        });
        if(params.getPostTo() != null){
            specs = specs.and(
                    PostSpecs.postedBefore(params.getPostTo())
            );
        }
        if(params.getPostFrom() != null){
            specs = specs.and(
                    PostSpecs.postedAfter(params.getPostFrom())
            );
        }
        Pageable pageable = PageRequest.of(params.getPageNumber(),params.getLimit(), Sort.by("createdAt").descending());

        Page<Post> posts = _postRepository.findAll(specs,pageable);
        Page<PostWithCommentsAndLikesDto> response= posts.map(post->{
            PostWithCommentsAndLikesDto dto = _modelMapper.map(post,PostWithCommentsAndLikesDto.class);
            dto.setRecentComments(postCommentService.getRecentComments(post.getId()));
            dto.setRecentLikedBy(postLikeService.getRecentLikes(post.getId()));
            return dto;
        });
        return response;
    }

    public PostResponseDto createPost(CreateUpdatePostRequestDto postRequestDto, String attachmentpath){
        Post post = new Post();
        post.setBody(postRequestDto.getBody());
        post.setTitle(postRequestDto.getTitle());
        post.setTags(Arrays.stream(postRequestDto.getTags()).reduce((acc,tag)->acc+ ","+tag).get());
        post.setAttachmentPath(attachmentpath);
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = _employeeService.getEmployeeById(jwtInfo.getUserId());
        post.setCreatedBy(employee);
        post = _postRepository.save(post);
        return _modelMapper.map(post,PostResponseDto.class);
    }

    public PostResponseDto getPostById(Long postId){
        Post post = _postRepository.findById(postId).orElseThrow(()->new ItemNotFoundExpection("post not found"));
        return _modelMapper.map(post,PostResponseDto.class);
    }

    public PostResponseDto updatePost(CreateUpdatePostRequestDto postRequestDto, String attachmentpath){
        Post post = _postRepository.findById(postRequestDto.getId()).orElseThrow(()->new ItemNotFoundExpection("Post not found"));
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(post.getCreatedBy().getId() != jwtInfo.getUserId()){
            throw new InvalidActionException("You can not update others post");
        }
        post.setBody(postRequestDto.getBody());
        post.setTitle(postRequestDto.getTitle());
        post.setTags(Arrays.stream(postRequestDto.getTags()).reduce((acc,tag)->acc+ ","+tag).get());
        if(attachmentpath != null){
            post.setAttachmentPath(attachmentpath);
        }

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

    public boolean deleteUnappropriatedPost(DeleteUnappropriatedContentRequestDto requestDto){
        Post post = _postRepository.findById(requestDto.getId()).orElseThrow(()->new PostNotFound());
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = _employeeService.getEmployeeById(jwtInfo.getUserId());
        post.setDeletedBy(employee);
        post.setRemarkForDelete(requestDto.getRemark());
        post.setDeleted(true);
        post.setDeletedByHr(true);
        post = _postRepository.save(post);
        this.emailService.sendMail(
                "Warning: unapropriate post",
                "post conent\n"+post.getBody() + "\n\nremark for delete: \n"+requestDto.getRemark()
                ,new String[]{post.getCreatedBy().getEmail()}
                ,new String[]{}
        );
        return true;
    }
    @Transactional
    public CommentResponseDto comment(Long postId, CreateUpdateCommentRequestDto requestDto){
        Post post = _postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        CommentResponseDto comment =  postCommentService.commentOn(post,requestDto);
        post.setCommentCount(post.getCommentCount()+1);
        _postRepository.save(post);
        return comment;
    }

    public boolean likeUnlike(Long postId){
        Post post = _postRepository.findById(postId).orElseThrow(()->new RuntimeException("Post not found"));
        boolean flag =  postLikeService.likeUnlike(post);
        if(flag){
            post.setLikeCount(post.getLikeCount() + 1);
        }else{
            post.setLikeCount(post.getLikeCount() - 1);
        }
        _postRepository.save(post);
        return flag;
    }

    public boolean deletePostComment(Long commentId){
        Post post = postCommentService.deletePostComment(commentId);
        post.setCommentCount(post.getCommentCount() - 1);
        _postRepository.save(post);
        return true;
    }
    public void genrateBirthdayAndWorkAniversaryPost(){
        _postRepository.sp_birthdayAndOrkAniversary();
    }
    public Page<PostWithCommentsAndLikesDto> getPostUploadedBySelf(PostQueryParamsDto params){
        JwtInfoDto jwtInfo = (JwtInfoDto)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Specification<Post> specs = ((root, query, criteriaBuilder) -> {
            Join<Post,Employee> employeeJoin = root.join("createdBy");
            return criteriaBuilder.and(criteriaBuilder.isFalse(root.get("isDeleted")),criteriaBuilder.isFalse(root.get("isDeletedByHr")),criteriaBuilder.like(root.get("tags"),"%"+params.getQuery()+"%"),criteriaBuilder.equal(employeeJoin.get("id"), jwtInfo.getUserId()));
        });
        Pageable pageable = PageRequest.of(params.getPageNumber(),params.getLimit(), Sort.by("createdAt").descending());
        Page<Post> posts = _postRepository.findAll(specs,pageable);
        Page<PostWithCommentsAndLikesDto> response= posts.map(post->{
            PostWithCommentsAndLikesDto dto = _modelMapper.map(post,PostWithCommentsAndLikesDto.class);
            dto.setRecentComments(postCommentService.getRecentComments(post.getId()));
            dto.setRecentLikedBy(postLikeService.getRecentLikes(post.getId()));
            return dto;
        });
        return response;
    }



}
