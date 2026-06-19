package com.Pro_Connect.PostService.service;

import com.Pro_Connect.PostService.auth.AuthContextHolder;
import com.Pro_Connect.PostService.client.ConnectionServerClient;
import com.Pro_Connect.PostService.dto.PersonDto;
import com.Pro_Connect.PostService.dto.PostCreateRequestDto;
import com.Pro_Connect.PostService.dto.PostDTO;
import com.Pro_Connect.PostService.entity.Post;
import com.Pro_Connect.PostService.event.PostCreated;
import com.Pro_Connect.PostService.exception.ResourceNotFoundException;
import com.Pro_Connect.PostService.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ConnectionServerClient connectionServerClient;
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;
    public PostDTO createPost(PostCreateRequestDto postCreateRequestDto,Long userId) {
           Post post = modelMapper.map(postCreateRequestDto, Post.class);
           post.setUserId(userId);
           post=postRepository.save(post);
           List<PersonDto> personDtoList=connectionServerClient.getFirstDegreeConnections(userId);

           for(PersonDto personDto:personDtoList){// send notification to each connection
               PostCreated postCreated=PostCreated.builder()
                       .postId(post.getId())
                       .content(post.getContent())
                       .userID(personDto.getUserId())
                       .ownerUserId(userId)
                       .build();
               postCreatedKafkaTemplate.send("postCreated",postCreated);
           }
           return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO getPostById(Long postId) {
        log.info("Getting post by id {}", postId);
        Long userId = AuthContextHolder.getCurrentUserId();
        List<PersonDto> personDtoList=connectionServerClient.getFirstDegreeConnections(userId);
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
