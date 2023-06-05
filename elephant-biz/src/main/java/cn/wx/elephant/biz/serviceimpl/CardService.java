package cn.wx.elephant.biz.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import cn.hutool.core.collection.CollectionUtil;
import cn.wx.elephant.biz.bean.dto.CardBriefResDto;
import cn.wx.elephant.biz.bean.entity.CardEntity;
import cn.wx.elephant.biz.bean.enums.BigNumEnum;
import cn.wx.elephant.biz.bean.enums.CardTypeEnum;
import cn.wx.elephant.biz.bean.enums.CardWordEnum;
import cn.wx.elephant.biz.service.ICardService;
import cn.wx.elephant.biz.utils.CardUtil;
import cn.wx.elephant.core.idwork.IdWorkHandler;
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
    public List<CardBriefResDto> chaos() {
        List<CardEntity> cardEntities = CardUtil.chaos();

        List<CardBriefResDto> list = ObjectUtil.copy(cardEntities, CardBriefResDto.class);
        return list;
    }

}
