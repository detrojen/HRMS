package com.hrms.backend.services.PostServices;

import com.hrms.backend.dtos.globalDtos.JwtInfoDto;
import com.hrms.backend.dtos.responseDtos.employee.EmployeeMinDetailsDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.entities.PostEntities.PostLike;
import com.hrms.backend.repositories.PostRepositories.PostLikeRepository;
import com.hrms.backend.services.EmployeeServices.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;
    public PostLikeService(PostLikeRepository postLikeRepository, EmployeeService employeeService, ModelMapper modelMapper){
        this.postLikeRepository = postLikeRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public boolean likeUnlike(Post post){
        JwtInfoDto jwtInfoDto = (JwtInfoDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostLike postLike = postLikeRepository.findByLikedBy_IdAndPost_Id(jwtInfoDto.getUserId(), post.getId());
        if(postLike!=null){
            postLikeRepository.delete(postLike);
            return false;
        }
        PostLike like = new PostLike();
        like.setLikedBy(employeeService.getReference(jwtInfoDto.getUserId()));
        like.setPost(post);
        postLikeRepository.save(like);
        return true;
    }

    public List<EmployeeMinDetailsDto> getRecentLikes(Long postId){
        List<PostLike> likes = postLikeRepository.findAllByPost_Id(postId, Sort.by(Sort.Direction.DESC,"createdAt"));
        List<EmployeeMinDetailsDto>  response = likes
                .stream().limit(3)
                .map(like->modelMapper.map(like.getLikedBy(),EmployeeMinDetailsDto.class))
                .collect(Collectors.toUnmodifiableList());
        return response;
    }
}
