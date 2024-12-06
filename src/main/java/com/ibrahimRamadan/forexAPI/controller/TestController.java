package com.ibrahimRamadan.forexAPI.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("t1")
    public ResponseEntity<String> t1()
    {
        return new ResponseEntity<>("Test Works",HttpStatus.OK);
    }
}
