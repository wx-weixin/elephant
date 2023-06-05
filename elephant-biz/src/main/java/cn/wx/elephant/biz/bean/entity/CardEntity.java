package cn.wx.elephant.biz.bean.entity;

import lombok.Data;
import cn.wx.elephant.core.bean.EntityAncestor;
import lombok.EqualsAndHashCode;

/**
 * 牌信息
 * <p>card
 *
 * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
 *
 * @author weixin 2023-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CardEntity extends EntityAncestor implements Cloneable {

    /**
     * 名称
     * <p>name
     * <p>长度：128
     * <p>不能为null
     */
    private String name;

    /**
     * 性别 1-万 2-饼 3-条 4-字
     * <p>type
     * <p>不能为null
     */
    private Integer type;

    /**
     * 牌的大小
     * <p>name_sort
     * <p>不能为null
     */
    private Integer nameSort;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
