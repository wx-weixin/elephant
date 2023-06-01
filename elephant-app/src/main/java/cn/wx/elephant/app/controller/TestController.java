package cn.wx.elephant.app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wx.elephant.biz.bean.entity.User;
import cn.wx.elephant.biz.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/6/1 3:25 PM
 */
@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/test1")
    public String test1(){
        return "hello world";
    }

    @GetMapping("/test2")
    public List<User> test2(){
        return userMapper.queryAll();
    }
}
