package cn.wx.elephant.biz.serviceimpl;

import java.util.List;
import org.springframework.stereotype.Service;
import cn.wx.elephant.biz.bean.dto.card.resp.CardBriefRes;
import cn.wx.elephant.biz.bean.dto.card.resp.CardSendRes;
import cn.wx.elephant.biz.bean.entity.CardEntity;
import cn.wx.elephant.biz.service.ICardService;
import cn.wx.elephant.biz.utils.CardUtil;
import cn.wx.elephant.core.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/6/2 1:42 PM
 */
@Service
@Slf4j
public class CardService implements ICardService {

    @Override
    public List<CardEntity> initCard() {

        return CardUtil.initCard();
    }


    @Override
    public List<CardBriefRes> chaos() {
        List<CardEntity> cardEntities = CardUtil.chaos();
        return ObjectUtil.copy(cardEntities, CardBriefRes.class);
    }

    @Override
    public CardSendRes send(int startNum) {
        if(startNum < 2 || startNum > 12){
            return null;
        }
        List<CardBriefRes> chaos = chaos();
        List<CardBriefRes> sternList = chaos.subList(0, startNum);
        List<CardBriefRes> cardList = chaos.subList(startNum, chaos.size());


        return null;
    }

}
