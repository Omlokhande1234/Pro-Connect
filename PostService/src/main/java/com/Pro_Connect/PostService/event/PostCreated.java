package com.Pro_Connect.PostService.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreated {
    private Long ownerUserId;
    private Long postId;
    private Long userID;
    private String content;

}
