package cn.wx.elephant.biz.bean.dto.card.resp;

import lombok.Data;

/**
 * @author weixin 2023/6/3 1:51 PM
 */
@Data
public class CardBriefRes {

    private Long id;

    private String name;

    private Integer type;

    private Integer nameSort;
}
