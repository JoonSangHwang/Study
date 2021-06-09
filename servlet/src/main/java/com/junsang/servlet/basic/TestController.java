package com.junsang.servlet.basic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/request-param")
    public ResponseEntity a() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/request-header")
    public ResponseEntity b() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/request-body-string")
    public ResponseEntity c() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/request-body-json")
    public ResponseEntity d() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping({"/response-header", "/response-html", "/response-json"})
    public ResponseEntity e() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
