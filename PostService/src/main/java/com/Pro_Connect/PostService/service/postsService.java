package com.Pro_Connect.PostService.service;

import com.Pro_Connect.PostService.dto.PostCreateRequestDto;
import com.Pro_Connect.PostService.dto.PostDTO;
import com.Pro_Connect.PostService.entity.Post;
import com.Pro_Connect.PostService.exception.ResourceNotFoundException;
import com.Pro_Connect.PostService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class postsService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    public PostDTO createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
           Post post = modelMapper.map(postCreateRequestDto, Post.class);
           post.setUserId(userId);
           post=postRepository.save(post);
           return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        log.info("Getting post by id {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id " + postId));
        return modelMapper.map(post, PostDTO.class);
    }

    public List<PostDTO> getAllPostsOfUser(Long userId) {
        log.info("Getting all posts of user {}", userId);
        List<Post> postList=postRepository.findByUserId(userId);

        return postList
                .stream()
                .map((element)->modelMapper.map(element,PostDTO.class))
                .collect(Collectors.toList());
    }
}
