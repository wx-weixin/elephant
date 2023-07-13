package cn.wx.elephant.app.controller.codeserver;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.wx.elephant.biz.bean.Bo.CodeServerBo;
import cn.wx.elephant.biz.serviceimpl.CodeServerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/7/12 4:30 PM
 */
@RestController
@RequestMapping(value = "/code")
@Slf4j
public class CodeServerController {

    @Resource
    private CodeServerService codeServerService;

    @GetMapping("/getCode")
    public CodeServerBo getCode(@RequestParam(name = "userId") String userId){
        return codeServerService.getCode(userId);
    }

}
