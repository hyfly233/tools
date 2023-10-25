package com.example.demo.controller;

import com.example.demo.service.ITestService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {

    @Resource
    private ITestService testService;

    @PostMapping("/func1")
    public void func1() throws Exception {
        testService.func1();
    }

}
