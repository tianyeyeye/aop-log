package com.example.logdemo.web;

import com.example.logdemo.log.ControllerLog;
import com.example.logdemo.log.OperateModule;
import com.example.logdemo.log.OperateType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HelloController {

    @ControllerLog(module = OperateModule.Order, opType = OperateType.menu)
    @GetMapping("/hello")
    public String hello() {
        return "Hi " + LocalDateTime.now();
    }

}
