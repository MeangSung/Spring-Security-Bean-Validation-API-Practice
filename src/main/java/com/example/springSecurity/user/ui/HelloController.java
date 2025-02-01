package com.example.springSecurity.user.ui;

import com.example.springSecurity.common.dto.ResponseDto;
import com.example.springSecurity.config.security.validation.ExampleConstraint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/example/validation/test")
    @ExampleConstraint // 직접 설정한 유효성 검증 제약조건
    public ResponseDto<String> exampleValidationTest(){return ResponseDto.ok("success");}


}
