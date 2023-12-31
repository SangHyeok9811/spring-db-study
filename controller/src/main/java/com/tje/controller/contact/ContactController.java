package com.tje.controller.contact;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/contacts")
public class ContactController {
    // 동시처리에 대한 지원을 해주는 자료구조
    // 여러명의 유저들이 같은 데이터를 접근할 수 있음.
    // 데이터베이스는 기본적으로 동시성에 대한 구현이 있음.
    Map<String, Contact> map = new ConcurrentHashMap<>();

    @GetMapping
    public List<Contact> getContactList() {
//        map.put("hong@naver.com", Contact.builder()
//                .name("홍길동").phone("010-1234-5678")
//                .email("hong@naver.com").build());
//        map.put("kim@gmail.com", Contact.builder()
//                .name("김철수").phone("010-9873-8382")
//                .email("kim@gmail.com").build());

        var list = new ArrayList<>(map.values());
//        list.sort((a, b)-> a.getName().compareTo(a.getName()));
        list.sort(Comparator.comparing(Contact::getName));

        return list;
    }

    // HTTP 1.1 Post /contacts
    // HTTP 버전 메서드 리소스 URL
    // : Request Line

    // Content-Type: application/json - 요청보낼 데이터 형식
    // Accept: */*  = 서버로부터 어떤 형식의 데이터를 받을지
    //                  ex) image/jpeg, application/json, text/html, plain/text
    // : Request Header
    // : 서버에 부가적인 요청정보

    // {"name":"홍길동", "phone":"010-1111-2222", "email":"hong@naver.com"}
    // : Request Body(요청 본문)
    
    // JSON: 문자열, 자바스크립트 객체 표기법
    // Client(브라우저) Request Header 에 Content-Type: application/json
    // Request Body 가 JSON 문자열이면
    
    // Server(스프링) @RequestBody 를 메서드 매개변수에 넣어주면
    // JSON(문자열) -> Java 객체로 변환

    // 백엔드 API 메서드 구조
//    1. 요청에 데이터에 대해서 검증
//       -> 잘못된 데이터, 충돌되는 데이터면 400, 409와 같은 응답코드 내보냄
//    2. 요청 데이터에서 대해서 조회를 하거나 생성/수정/삭제
//    3. 요청 데이터를 처리후 응답을 줌
    
    // Object 는 모든데이터타입을 받는 클래스?의 느낌
    // return 값의 데이터타입(클래스)
    // Contact 클래스의 contact 객체
    @PostMapping
    public ResponseEntity<Map<String, Object>> addContact(@RequestBody Contact contact) {
        // 클라이언트에서 넘어온 JSON 이 객체로 잘 변환됐는지 확인
        System.out.println(contact.getName());
        System.out.println(contact.getPhone());
        System.out.println(contact.getEmail());

        // 이메일 필수값 검증
        // 400: bad request
        if(contact.getEmail() == null || contact.getEmail().isEmpty()) {
            // 응답 객체 생성
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[email] field is required");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(res);
        }

        // 이메일 중복 검증
        // 409: conflict

        if(contact.getEmail() != null && map.get(contact.getEmail()) != null) {
            // 맵에 해당 이메일이 있음
            // 이미 있는 데이터를 클라이언트(브라우저) 보냈거나
            // 클라이언트에서 중복 데이터를 보냈거나..
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "동일한 정보가 이미 존재합니다.");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
        }

        // 맵에 객체 추가
        map.put(contact.getEmail(), contact);

        // 응답 객체 생성
        Map<String, Object> res = new HashMap<>();

//        res.put("data",contact.getEmail()); // contact 객체의 email만 출력
        res.put("data",map.get(contact.getEmail())); // contact 객체의 email과 동일한 map의 객체 전체 출력
        res.put("message", "created");

        // HTTP Status Code: 201 Created
        // 리소스가 정상적으로 생성되었음.
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // DELETE
    @DeleteMapping(value="/{email}")
    // @PathVariable("email") : 경로 문자열(email)과 변수명 String email이 동일하면 안 써도 된다.
    public ResponseEntity removeContact(@PathVariable("email") String email){
        System.out.println(email);

        // 해당 키(Key)의 데이터가 없으면
        if(map.get(email)==null){
            // 404:NOT FOUND, 해당 경로에 리소스가 없다.
            // DELETE /contacts/kdkcom@naver.com
            // Response Status Code : 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 객체(리소스) 삭제
        map.remove(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}