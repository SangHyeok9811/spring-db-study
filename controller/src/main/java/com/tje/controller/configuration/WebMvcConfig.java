package com.hsh.myapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    // CORS(cross origin resource sharing)
    // 다른 origin끼리 자원을 공유할 수 있게 하는 것
    // 기본으로 웹(js)에서는 CORS가 안 됨.

    // origin의 구성 요소는 프로토콜 + 주소(도메인,url) + 포트
    // https:// + 127.0.0.1 + :5500
    // https://localhost:8080

    // 서버쪽에서 접근 가능한 origin 목록, 경로목록, 멤서드 목록을 설정해주어야함.

    @Override
    public void addCorsMappings(@org.jetbrains.annotations.NotNull CorsRegistry registry) {
        registry
                .addMapping("/**") // 모든 경로에 대해
                .allowedOrigins(
                        "http://localhost:5500",
                        "http://127.0.0.1:5500") // 로컬 호스트 origin 허용
                .allowedMethods("*"); // 모든 메서드 허용 (GET, POST.....)
    }
}











