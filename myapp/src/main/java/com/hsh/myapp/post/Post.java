package com.hsh.myapp.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    // database의 auto-increment를 사용함
    // auto_increment: 레코드가 추가될 때 자동으로 증가되는 값을 사용
    // 1, 2, 3 .....
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String title;
    private String content;
    private String creatorName;

    // created_time bigint not null
    // primitive type: int, char, long, double
    // nullable 불가, long  기본값이 0
    // 데이터베이스에 NOT NULL로 세팅해줌
//    private long createdTime;

    // 데이터베이스에 NULL을 넣고 싶으면 Class 타입을 써야함
    private Long createdTime;
    private String image;
}