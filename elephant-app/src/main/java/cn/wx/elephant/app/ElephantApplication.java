package cn.wx.elephant.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author weixin 2023/6/1 3:11 PM
 */
@MapperScan(basePackages = {"cn.wx.elephant.biz.mapper"})
@SpringBootApplication
public class ElephantApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElephantApplication.class,args);
    }
}
