package com.swyg.oneului.controller;

import com.swyg.oneului.controller.doc.HelloControllerDoc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController implements HelloControllerDoc {

    @GetMapping("/hello")
    public String hello() {
        log.info("hello 호출");
        return "hello dev";
    }
}
