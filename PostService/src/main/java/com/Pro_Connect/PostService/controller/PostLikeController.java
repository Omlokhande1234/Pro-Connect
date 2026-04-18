package com.Pro_Connect.PostService.controller;

import com.Pro_Connect.PostService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikeController {
       private final PostLikeService postLikeService;

       @PostMapping("/{postId}")
       public ResponseEntity<Void> likePost(@PathVariable long postId) {
              postLikeService.likePost(postId);
              return ResponseEntity.noContent().build();
       }

       @DeleteMapping("/{postId}")
       public ResponseEntity<Void> deletePost(@PathVariable long postId) {
              postLikeService.unlikePost(postId);
              return ResponseEntity.noContent().build();
       }
}
