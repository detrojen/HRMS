package com.hrms.backend.controllers;

import com.hrms.backend.dtos.globalDtos.PageableDto;
import com.hrms.backend.dtos.requestDto.post.CreatePostRequestDto;
import com.hrms.backend.dtos.requestDto.post.PostCommentRequestDto;
import com.hrms.backend.dtos.requestDto.post.DeleteUnappropriatedContentRequestDto;
import com.hrms.backend.dtos.requestDto.post.PostQueryParamsDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.dtos.responseDtos.post.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostWithCommentsAndLikesDto;
import com.hrms.backend.entities.PostEntities.Post;
import com.hrms.backend.services.PostServices.PostService;
import com.hrms.backend.utils.FileUtility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PostController {
    private final PostService _postService;

    @Autowired
    public PostController(PostService postService){
        _postService = postService;
    }
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestPart() @Valid CreatePostRequestDto postDetails, @RequestPart @NotNull(message = "attchemnet must be requried") MultipartFile attachment){
        String attachmentPath = FileUtility.Save(attachment,"posts");
        PostResponseDto post = _postService.createPost(postDetails, attachmentPath);
        return  ResponseEntity.ok().body(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<GlobalResponseDto<Page<PostWithCommentsAndLikesDto>>> getPosts(
            @RequestParam(defaultValue = "0") int page
          ,@RequestParam(defaultValue = "5") int limit
            ,@RequestParam(defaultValue = "") String query
    ){
        PostQueryParamsDto postParams = new PostQueryParamsDto(query,page,limit);
        return ResponseEntity.ok(new GlobalResponseDto<>(_postService.getPosts(postParams)));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<DeletePostResponseDto> deletePost(@PathVariable Long id){
        return ResponseEntity.ok().body(_postService.deletePost(id));
    }

    @PostMapping("/posts/{id}/comment")
    public ResponseEntity<CommentResponseDto> deletePost(@PathVariable Long id,@RequestBody @Valid PostCommentRequestDto requestDto){
        return ResponseEntity.ok().body(_postService.comment(id,requestDto));
    }

    @PutMapping("/posts/delete-unappropriate")
    public ResponseEntity<GlobalResponseDto<Boolean>> deleteUnappropriatedPost(@RequestBody @Valid DeleteUnappropriatedContentRequestDto requestDto){
        _postService.deleteUnappropriatedPost(requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(true));
    }
}
