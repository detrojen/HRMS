package com.hrms.backend.controllers;

import com.hrms.backend.dtos.requestDto.post.CreateUpdatePostRequestDto;
import com.hrms.backend.dtos.requestDto.post.CreateUpdateCommentRequestDto;
import com.hrms.backend.dtos.requestDto.post.DeleteUnappropriatedContentRequestDto;
import com.hrms.backend.dtos.requestDto.post.PostQueryParamsDto;
import com.hrms.backend.dtos.responseDtos.GlobalResponseDto;
import com.hrms.backend.dtos.responseDtos.post.CommentResponseDto;
import com.hrms.backend.dtos.responseDtos.post.DeletePostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostResponseDto;
import com.hrms.backend.dtos.responseDtos.post.PostWithCommentsAndLikesDto;
import com.hrms.backend.services.PostServices.PostCommentService;
import com.hrms.backend.services.PostServices.PostService;
import com.hrms.backend.utils.FileUtility;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Optional;

@RestController
public class PostController {
    private final PostService postService;
    private final PostCommentService postCommentService;
    @Value("${hrms.backend.uploads.posts}")
    private String postsDir;
    @Autowired
    public PostController(PostService postService, PostCommentService postCommentService){
        this.postService = postService;
        this.postCommentService = postCommentService;
    }
    @PostMapping("/posts")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> createPost(@RequestPart() @Valid CreateUpdatePostRequestDto postDetails, @RequestPart @NotNull(message = "attchemnet must be requried") MultipartFile attachment){
        String attachmentPath = FileUtility.Save(attachment,postsDir);
        PostResponseDto post = postService.createPost(postDetails, attachmentPath);
        return  ResponseEntity.ok().body(new GlobalResponseDto<>(post,"Post created"));
    }

    @PutMapping("/posts")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> updatePost(@RequestPart() @Valid CreateUpdatePostRequestDto postDetails, @RequestPart  Optional<MultipartFile> attachment){
        String attachmentPath = null;
        if(attachment.isPresent()){
            attachmentPath= FileUtility.Save(attachment.get(),postsDir);
        }
        PostResponseDto post = postService.updatePost(postDetails, attachmentPath);
        return  ResponseEntity.ok().body(new GlobalResponseDto<>(post,"Post updated"));
    }

    @GetMapping("/posts")
    public ResponseEntity<GlobalResponseDto<Page<PostWithCommentsAndLikesDto>>> getPosts(
            @RequestParam(defaultValue = "0") int page
          ,@RequestParam(defaultValue = "5") int limit
            ,@RequestParam(defaultValue = "") String query
            ,@RequestParam(required = false) Date postFrom
            ,@RequestParam(required = false) Date postTo
    ){
        PostQueryParamsDto postParams = new PostQueryParamsDto(query,page,limit,postFrom,postTo);
        return ResponseEntity.ok(new GlobalResponseDto<>(postService.getPosts(postParams)));
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<GlobalResponseDto<PostResponseDto>> getPostById( @PathVariable Long postId){
        return ResponseEntity.ok(new GlobalResponseDto<>(postService.getPostById(postId)));
    }

    @GetMapping("/posts/uploaded-by-self")
    public ResponseEntity<GlobalResponseDto<Page<PostWithCommentsAndLikesDto>>> getPostUploadedBySelf(
            @RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "5") int limit
            ,@RequestParam(defaultValue = "") String query

    ){
        PostQueryParamsDto postParams = new PostQueryParamsDto(query,page,limit);
        return ResponseEntity.ok(new GlobalResponseDto<>(postService.getPostUploadedBySelf(postParams)));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<GlobalResponseDto<DeletePostResponseDto>> deletePost(@PathVariable Long id){
        return ResponseEntity.ok().body(new GlobalResponseDto<>(postService.deletePost(id)));
    }

    @DeleteMapping("/posts/comment/{commentId}")
    public ResponseEntity<GlobalResponseDto<Boolean>> deletePostComment(@PathVariable Long commentId){
        return ResponseEntity.ok().body(new GlobalResponseDto<>(postService.deletePostComment(commentId)));
    }

    @PostMapping("/posts/{id}/comment")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long id,@RequestBody @Valid CreateUpdateCommentRequestDto requestDto){
        return ResponseEntity.ok().body(postService.comment(id,requestDto));
    }

    @PutMapping("/posts/delete-unappropriate")
    public ResponseEntity<GlobalResponseDto<Boolean>> deleteUnappropriatedPost(@RequestBody @Valid DeleteUnappropriatedContentRequestDto requestDto){
        postService.deleteUnappropriatedPost(requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(true));
    }
    @PutMapping("/posts/comment/delete-unappropriate")
    public ResponseEntity<GlobalResponseDto<Boolean>> deleteUnappropriatedPostComment(@RequestBody @Valid DeleteUnappropriatedContentRequestDto requestDto){
        postCommentService.deleteUnappropriatedPostComment(requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(true));
    }

    @PostMapping("/posts/{id}/like-unlike")
    public ResponseEntity<GlobalResponseDto<Boolean>> likeUnlike(@PathVariable Long id){
        Boolean flag = postService.likeUnlike(id);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(flag));
    }

    @PutMapping("/posts/comment")
    public ResponseEntity<GlobalResponseDto<CommentResponseDto>> updateComment(@RequestBody() @Valid CreateUpdateCommentRequestDto requestDto){
        CommentResponseDto comment = postCommentService.updateComment(requestDto);
        return ResponseEntity.ok().body(new GlobalResponseDto<>(comment));

    }
}
