package cn.wx.elephant.biz.service;

import java.util.List;
import cn.wx.elephant.biz.bean.dto.CardBriefResDto;
import cn.wx.elephant.biz.bean.entity.CardEntity;

/**
 * @author weixin 2023/6/2 1:45 PM
 */
public interface ICardService {


    List<CardEntity> initCard();

    /**
     * 洗牌
     */
    List<CardBriefResDto> chaos();


}
