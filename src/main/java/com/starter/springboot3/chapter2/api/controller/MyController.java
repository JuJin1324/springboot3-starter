package com.starter.springboot3.chapter2.api.controller;

import com.starter.springboot3.chapter2.service.MyObservedService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyObservedService myObservedService;

    public MyController(MyObservedService myObservedService) {
        this.myObservedService = myObservedService;
    }

    @GetMapping("/aop/{input}")
    public String aopEndpoint(@PathVariable String input) throws InterruptedException {
        return myObservedService.doSomethingAOP(input);
    }

    @GetMapping("/programmatic")
    public String programmaticEndpoint(@RequestParam String userId, @RequestParam String itemId)
            throws InterruptedException {
        return myObservedService.doSomethingProgrammatic(userId, itemId);
    }

    //    @GetMapping("/keyvalues")
    //    public String keyvaluesEndpoint(@RequestParam String segment) throws InterruptedException
    // {
    //        return myObservedService.doSomethingWithKeyValues(segment);
    //    }
}
