package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDtos.CreatePostRequestDto;
import com.hrms.backend.dtos.requestDtos.deleteUnappropriatedPostRequestDto;
import com.hrms.backend.dtos.responseDtos.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.PostResponseDto;
import com.hrms.backend.entities.Post;
import com.hrms.backend.services.PostService;
import com.hrms.backend.utils.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestPart CreatePostRequestDto createPostRequestDto, @RequestPart MultipartFile attachment){
        String attachmentPath = FileUtility.Save(attachment,"posts");
        PostResponseDto post = _postService.createPost(createPostRequestDto, attachmentPath);
        return  ResponseEntity.ok().body(post);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts(){
        return ResponseEntity.ok(_postService.getPosts());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<DeletePostResponseDto> deletePost(@PathVariable Long id){
        return ResponseEntity.ok().body(_postService.deletePost(id));
    }

    @DeleteMapping("/hr/posts/delete-unappropriate")
    public ResponseEntity<Post> deleteUnappropriatedPost(@RequestBody deleteUnappropriatedPostRequestDto requestDto){
        return ResponseEntity.ok().body(_postService.deleteUnappropriatedPost(requestDto));
    }
}
