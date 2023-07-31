package com.hsh.myapp.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor // 전체 필드 초기 생성자
@NoArgsConstructor // 빈 생성자
@Entity
public class Contact {

    @Id
    private String email;   // key

    private String name;
    private String phone;
    // 파일을 base64 data-url 문자열로 저장
    private String image;
}