package cn.wx.elephant.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author weixin 2023/6/1 3:11 PM
 */
@MapperScan(basePackages = {"cn.wx.elephant.biz.mapper"})
@ComponentScan(basePackages = {"cn.wx.elephant.**"})
@SpringBootApplication
public class ElephantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElephantApplication.class,args);
    }
}
