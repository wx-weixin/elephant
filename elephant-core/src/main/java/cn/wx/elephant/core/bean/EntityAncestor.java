package cn.wx.elephant.core.bean;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 所有Entity的直接或间接父类
 * @author weixin 2021/3/22 5:59 下午
 */
@Data
public abstract class EntityAncestor {

    /**
     * 主键
     */
    private Long id;

    /**
     * 插入数据时登录者的staffId，若数据并非登录者插入的，值为-1
     * <p>create_staff_id
     * <p>不能为null
     * <p>默认：-1
     */
    private Long createId;

    /**
     * 最近一次更新数据时登录者的staffId，若数据从未更新过，值与create_staff_id保持一致，若数据并非登录者更新的，值为-1
     * <p>update_staff_id
     * <p>不能为null
     * <p>默认：-1
     */
    private Long updateId;

    /**
     * 插入数据的时间
     * <p>create_time
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    private LocalDateTime createTime;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与create_time保持一致
     * <p>update_time
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    private LocalDateTime updateTime;

    /**
     * 插入数据前，对通用属性进行设置
     */
    public void forCreate(Long who, LocalDateTime when) {
        this.createId = who;
        this.createTime = when;
        this.updateId = who;
        this.updateTime = when;
    }


    /**
     * 更新数据前，对通用属性进行设置
     */
    public void forUpdate(Long who, LocalDateTime when) {
        this.createId = who;
        this.updateTime = when;
    }
}
