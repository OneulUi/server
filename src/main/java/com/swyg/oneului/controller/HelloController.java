package com.swyg.oneului.controller;

import com.swyg.oneului.controller.doc.HelloControllerDoc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloControllerDoc {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
