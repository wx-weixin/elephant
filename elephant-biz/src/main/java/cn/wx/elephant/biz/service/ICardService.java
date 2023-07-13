package cn.wx.elephant.biz.service;

import java.util.List;
import cn.wx.elephant.biz.bean.dto.card.resp.CardBriefRes;
import cn.wx.elephant.biz.bean.dto.card.resp.CardSendRes;
import cn.wx.elephant.biz.bean.entity.CardEntity;

/**
 * @author weixin 2023/6/2 1:45 PM
 */
public interface ICardService {

    /**
     * 初始化
     */
    List<CardEntity> initCard();

    /**
     * 洗牌
     */
    List<CardBriefRes> chaos();

    /**
     * 发牌
     */
    CardSendRes send(int startNum);
}
