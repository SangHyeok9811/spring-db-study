package com.tje.controller.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private long no;
    private String title;
    private String content;
    private String creatorName; // 서버에서 아무나
    private long createdTime;
}