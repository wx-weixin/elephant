package cn.wx.elephant.app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wx.elephant.biz.bean.dto.card.resp.CardBriefRes;
import cn.wx.elephant.biz.bean.entity.CardEntity;
import cn.wx.elephant.biz.service.ICardService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/6/2 4:18 PM
 */
@RestController
@RequestMapping(value = "/card")
@Slf4j
public class CardController {

    @Resource
    private ICardService cardService;

    @GetMapping("/init")
    public List<CardEntity> init(){
        return cardService.initCard();
    }

    @GetMapping("/chaos")
    public List<CardBriefRes> chaos(){
        return cardService.chaos();
    }
}
