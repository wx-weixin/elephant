package cn.wx.elephant.biz.bean.dto.card.resp;

import java.util.List;
import cn.wx.elephant.biz.bean.entity.UserEntity;
import lombok.Data;

/**
 * @author weixin 2023/6/5 3:00 PM
 */
@Data
public class PackCardRes {

    private List<CardBriefRes> cardBriefRes;

    private UserEntity userEntity;
}
