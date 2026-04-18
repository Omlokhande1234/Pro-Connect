package com.Pro_Connect.PostService.dto;

import lombok.Data;

@Data
// When the post is created we will just take input of
// content from the user other all the things would be configured by us
public class PostCreateRequestDto {
    private String content;

}
