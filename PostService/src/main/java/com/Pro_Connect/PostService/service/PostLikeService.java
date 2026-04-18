package com.Pro_Connect.PostService.service;

import com.Pro_Connect.PostService.entity.Post;
import com.Pro_Connect.PostService.entity.PostLike;
import com.Pro_Connect.PostService.exception.BadRequestException;
import com.Pro_Connect.PostService.exception.ResourceNotFoundException;
import com.Pro_Connect.PostService.repository.PostLikeRepository;
import com.Pro_Connect.PostService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
     private final PostLikeRepository postLikeRepository;
     private final ModelMapper modelMapper;
     private final PostRepository postRepository;

     @Transactional
     public void likePost(Long postId) {
         Long userId=1L;
         log.info("User with ID:{} liking the post with ID:{} ",userId,postId);
         Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID:"+postId+" not found"));
         boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
         if(hasAlreadyLiked){
             throw new BadRequestException("Post can't be liked again");
         }
         PostLike postLike=new PostLike();
         postLike.setUserId(userId);
         postLike.setPostId(postId);
         postLikeRepository.save(postLike);

//         TODO:send notification to the owner of the post
     }
     @Transactional
     public void unlikePost(Long postId) {
         Long userId=1L;
         log.info("User with ID:{} unliking the post with ID:{}",userId,postId);
         postLikeRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post with ID:"+postId+" not found"));
         boolean hasAlreadyLiked=postLikeRepository.existsByUserIdAndPostId(userId,postId);
         if(!hasAlreadyLiked){
             throw new BadRequestException("You can't unlike the post that you have not liked");
         }
         postLikeRepository.deleteByUserIdAndPostId(userId,postId);
     }
}
