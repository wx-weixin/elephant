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
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 牌信息
     * @return 插入条数
     */
    int insert(CardEntity entity);

    /**
     * 批量插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）牌信息
     * @return 插入条数
     */
    int batchInsert(@Param("entities") Collection<CardEntity> entities);

    /**
     * 批量插入，为null的属性会被作为null插入
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）牌信息
     * @return 插入条数
     */
    int batchInsertEvenNull(@Param("entities") Collection<CardEntity> entities);

    /**
     * （批量版）根据ID更新数据
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）牌信息
     * @return 更新条数
     */
    int batchUpdate(@Param("entities") Collection<CardEntity> entities);

    /**
     * （批量版）根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entities （多个）牌信息
     * @return 更新条数
     */
    int batchUpdateEvenNull(@Param("entities") Collection<CardEntity> entities);

    /**
     * 根据ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param id
     * @return （多个）牌信息
     */
    CardEntity queryById(Long id);

    /**
     * 根据ID更新数据，忽略值为null的属性
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 牌信息
     * @return 更新条数
     */
    int updateById(CardEntity entity);

    /**
     * 根据ID更新数据，为null的属性会被更新为null
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 牌信息
     * @return 更新条数
     */
    int updateByIdEvenNull(CardEntity entity);

    /**
     * 根据多个ID查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）
     * @return （多个）牌信息
     */
    List<CardEntity> queryByIds(@Param("ids") Collection<Long> ids);

    /**
     * 根据多个ID查询，并以ID作为key映射到Map
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param ids （多个）
     * @return （多个）（以ID为key）牌信息
     */
    @MapKey("id")
    Map<Long, CardEntity> queryByIdsEachId(@Param("ids") Collection<Long> ids);

    /**
     * 根据实体内的属性查询
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @param entity 条件
     * @return （多个）牌信息
     */
    List<CardEntity> queryByEntity(CardEntity entity);

    /**
     * 获取全部
     *
     * 出于性能考虑，这个方法只会返回最多500条数据
     * 事实上，只建议对数据量不大于500的配置表或常量表使用该方法，否则无法返回“全部”数据
     *
     * <strong>该方法由Allison 1875生成，请勿人为修改</strong>
     *
     * @return （多个）牌信息
     */
    List<CardEntity> listAll();
}
