package com.example.nettyboot.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test")
    String test(String g)
    {
        log.info("Controller log : {}",g);
        return "<h1>I AM TEST CONTROLLER</h1>";
    }
}
