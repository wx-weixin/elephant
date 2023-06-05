package cn.wx.elephant.biz.mapper;

import java.util.*;
import cn.wx.elephant.biz.bean.entity.CardEntity;
import org.apache.ibatis.annotations.*;

/**
 * 牌信息
 *
 * @see CardEntity
 * @author weixin 2023-06-01
 */
public interface CardMapper {

    /**
     * 插入
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int insert(CardEntity entity);

    /**
     * 批量插入
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int batchInsert(@Param("entities") Collection<CardEntity> entities);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int batchInsertEvenNull(@Param("entities") Collection<CardEntity> entities);

    /**
     * 批量根据ID更新数据
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int batchUpdate(@Param("entities") Collection<CardEntity> entities);

    /**
     * 批量根据ID更新数据，为null对应的字段会被更新为null
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int batchUpdateEvenNull(@Param("entities") Collection<CardEntity> entities);

    /**
     * 根据ID查询
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    CardEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int updateById(CardEntity entity);

    /**
     * 根据ID更新数据，为null属性对应的字段会被更新为null
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int updateByIdEvenNull(CardEntity entity);

    /**
     * 根据多个ID查询
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    List<CardEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    @MapKey("id")
    Map<Long, CardEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    List<CardEntity> queryByEntity(CardEntity entity);

    /**
     * 获取全部
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    List<CardEntity> listAll();

    /**
     * 尝试插入，若指定了id并存在，则更新，即INSERT ON DUPLICATE KEY UPDATE
     *
     * <p> Allison 1875 Lot No: PG0808R-E326B95E (don't modify manually)
     */
    int insertOrUpdate(CardEntity entity);
}
