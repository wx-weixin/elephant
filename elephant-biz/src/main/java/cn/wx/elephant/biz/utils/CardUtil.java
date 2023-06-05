package cn.wx.elephant.biz.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cn.wx.elephant.biz.bean.entity.CardEntity;
import cn.wx.elephant.biz.bean.enums.BigNumEnum;
import cn.wx.elephant.biz.bean.enums.CardTypeEnum;
import cn.wx.elephant.biz.bean.enums.CardWordEnum;
import cn.wx.elephant.core.idwork.IdWorkHandler;

/**
 * @author weixin 2023/6/3 1:59 PM
 */
public class CardUtil {

    /**
     * 初始化一副牌
     */
    public static List<CardEntity> initCard() {
        List<CardEntity> allList = new ArrayList<>();
        for (CardTypeEnum value : CardTypeEnum.values()) {
            if(CardTypeEnum.CONSTANT_1.equals(value) || CardTypeEnum.CONSTANT_2.equals(value) || CardTypeEnum.CONSTANT_3.equals(value)){
                allList.addAll(initBase(value));
            }else if(CardTypeEnum.CONSTANT_4.equals(value)){
                allList.addAll(initWord(value));
            }
        }

        return allList;
    }

    private static List<CardEntity> initBase(CardTypeEnum value){
        List<CardEntity> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                CardEntity cardEntity = new CardEntity();
                cardEntity.setName(BigNumEnum.of(i).getTitle()+value.getTitle());
                cardEntity.setType(value.getCode());
                cardEntity.setNameSort(i);
                cardEntity.setId(IdWorkHandler.getInstance().nextId());
                cardEntity.setCreateTime(LocalDateTime.now());
                cardEntity.setUpdateTime(LocalDateTime.now());
                list.add(cardEntity);
            }
        }
        return list;
    }

    private static List<CardEntity> initWord(CardTypeEnum value){
        List<CardEntity> list = new ArrayList<>();
        for (CardWordEnum cardWordEnum : CardWordEnum.values()) {
            for (int j = 0; j < 4; j++) {
                CardEntity cardEntity = new CardEntity();
                cardEntity.setName(cardWordEnum.getTitle());
                cardEntity.setType(value.getCode());
                cardEntity.setNameSort(cardWordEnum.getCode());
                cardEntity.setId(IdWorkHandler.getInstance().nextId());
                cardEntity.setCreateTime(LocalDateTime.now());
                cardEntity.setUpdateTime(LocalDateTime.now());
                list.add(cardEntity);
            }
        }
        return list;
    }

    public static List<CardEntity> chaos(){
        List<CardEntity> cardEntities = initCard();
        Collections.shuffle(cardEntities);
        return cardEntities;
    }
}
