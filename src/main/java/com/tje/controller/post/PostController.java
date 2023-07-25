package com.tje.controller.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Get /posts
// 게시글 목록이 JSON으로 나오게
@RestController
@RequestMapping(value = "/posts")
public class PostController {
    List<Post> post = new ArrayList<>();
    @GetMapping(value ="/get")
    public List<Post> getPostList(){
        post.clear();
        post.add (new Post(01,"제목1","내용입니다","한상혁",new Date().getTime()));
        post.add (new Post(02,"제목2","내용입니다","한상혁",new Date().getTime()));
        post.add (new Post(03,"제목3","내용입니다","한상혁",new Date().getTime()));
        return post;
    }
}