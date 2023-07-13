package cn.wx.elephant.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/6/1 3:25 PM
 */
@RestController
@RequestMapping(value = "")
@Slf4j
public class TestController {

    @GetMapping("/test/hello")
    public String test1(){
        return "hello world";
    }

    @GetMapping("")
    public String test2(){
        return "hello world";
    }
}
