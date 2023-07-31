package com.hsh.myapp.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// Get /posts
// 게시글 목록이 JSON으로 나오게
@RestController
@RequestMapping(value = "/posts")
public class PostController {
    // 동시성을 위한 자료구조
    // HashMap -> ConcurrentHashMap
    // Integer -> AtomicInteger
    Map<Long,Post> post = new ConcurrentHashMap<>();
    AtomicLong num = new AtomicLong(0);
    @GetMapping
    public List<Post> getPostList(){
        // 증가시키고 값 가져오기
//        long no = num.incrementAndGet();
//
//        post.put (no, Post.builder().no(no).title("제목1").content("1내용입니다").creatorName("홍길동").createdTime(new Date().getTime()).build());
//        no = num.incrementAndGet();
//        post.put (no, Post.builder().no(no).title("제목2").content("2내용입니다").creatorName("김길동").createdTime(new Date().getTime()).build());
//        no = num.incrementAndGet();
//        post.put (no, Post.builder().no(no).title("제목3").content("3내용입니다").creatorName("박길동").createdTime(new Date().getTime()).build());

        var list = new ArrayList<>(post.values());
        // 람다식(lambda expression)
        // 식이 1개인 함수식;
        // 매개변수 영역과 함수 본체를 화살표로 구분함
        // 함수 본체의 수식 값이 반환 값
        list.sort((a,b)-> (int)(b.getNo() - a.getNo()));

        return list;
    }

    //    -- 받는 정보
//    title, content
//    -> null 또는 없으면 bad-request 응답


//    -- 서버에 생성
//    no = num.incrementAndGet();
//    creatorName = "John Doe"
//    createdTime = new Date().getTime()

    // title, content 필수 속성
    // creatorName: 서버에서 가짜데이터로 넣음
    @PostMapping
    public ResponseEntity<Map<String, Object>> addPost(@RequestBody Post p) {
    // 1. 입력값 검증(title, content)
    //  -> 입력값 오류(빈 값)가 있으면 400 에러 띄움

        if(p.getTitle() == null || p.getTitle().isEmpty()) {
            // 응답 객체 생성
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[title] field is required");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }
        if(p.getContent() == null || p.getContent().isEmpty()){
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[content] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

    // 2. 채번: 번호를 딴다(1 .. 2, 3....)
        // 데이터베이스의 auto_increment를 사용할 것이므로
        // 아래 2줄은 필요없게 된다.
        long no = num.incrementAndGet();
        p.setNo(no);

    // 3. 번호(no), 시간값(createdTime) 게시자이름(creatorName) 요청 객체에 설정
    // (set필드명(...))
        p.setCreatedTime(new Date().getTime());
        p.setCreatorName("홍길동");
    // 4. 맵에 추가 (서버에서 생성된 값을 설정)
        post.put(no,p);
        System.out.println(post);
    // 5. 생성된 객체를 맵에서 찾아서 반환
        Map<String, Object> res = new HashMap<>();

//        res.put("data", no);
        res.put("data", post.get(no));
        res.put("message", "created");

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @DeleteMapping(value="/{no}")
    public ResponseEntity removeContent(@PathVariable("no") long no){
        System.out.println(no);

        if(post.get(no) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        post.remove(no);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}