package com.Pro_Connect.PostService.controller;

import com.Pro_Connect.PostService.dto.PostCreateRequestDto;
import com.Pro_Connect.PostService.dto.PostDTO;
import com.Pro_Connect.PostService.service.postsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
public class PostController {
    private  final postsService postsService;
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto,Long userId) {
        //for now we have hardcoded the user as 1
        PostDTO postDTO = postsService.createPost(postCreateRequestDto,1L);
        return new  ResponseEntity<>(postDTO, HttpStatus.OK);

    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        PostDTO postDTO=postsService.getPostById(postId);
        return ResponseEntity.ok(postDTO);
    }
    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable Long userId) {
        List<PostDTO> posts=postsService.getAllPostsOfUser(userId);
        return  ResponseEntity.ok(posts);
    }

}
