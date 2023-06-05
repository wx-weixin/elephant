package cn.wx.elephant.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import cn.hutool.core.collection.CollectionUtil;
import cn.wx.elephant.core.enums.BaseStatusCodeEnum;
import cn.wx.elephant.core.exception.BaseException;
import cn.wx.elephant.core.function.CustomMapping;

/**
 * 通用object工具包
 *
 * @author Spark
 */
public class ObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    private ObjectUtil() {
    }

    /**
     * copy异常中断
     */
    public static <T> T copy(Object source, Class<T> target) {
        try {
            if (null == source) {
                throw new BaseException(BaseStatusCodeEnum.B000004);
            }
            T t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BaseException(BaseStatusCodeEnum.B000004);
        }
    }

    /**
     * 对象copy
     */
    public static <S, T> List<T> copy(List<S> list, Class<T> target) {
        List<T> returnList = new ArrayList<>();
        try {
            if (CollectionUtil.isNotEmpty(list)) {
                for (Object source : list) {
                    if (null != source) {
                        T t = target.newInstance();
                        BeanUtils.copyProperties(source, t);
                        returnList.add(t);
                    }
                }
            }
            return returnList;
        } catch (Exception e) {
            logger.error("List复制发生异常", e);
            throw new BaseException(BaseStatusCodeEnum.B000004);
        }
    }


    public static <S, T> T copy(S source, Class<T> targetClass, CustomMapping<S, T> customMapping) {
        try {
            if (null == source) {
                throw new BaseException(BaseStatusCodeEnum.B000004);
            }
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            if (Objects.nonNull(customMapping)) {
                customMapping.mapping(source, target);
            }
            return target;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BaseException(BaseStatusCodeEnum.B000004);
        }
    }

    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass, CustomMapping<S, T> customMapping) {
        List<T> returnList = new ArrayList<>();
        try {
            if (CollectionUtil.isNotEmpty(sourceList)) {
                for (S source : sourceList) {
                    if (null != source) {
                        T target = targetClass.newInstance();
                        BeanUtils.copyProperties(source, target);
                        if (Objects.nonNull(customMapping)) {
                            customMapping.mapping(source, target);
                        }
                        returnList.add(target);
                    }
                }
            }
            return returnList;
        } catch (Exception e) {
            logger.error("List复制发生异常", e);
            throw new BaseException(BaseStatusCodeEnum.B000004);
        }
    }

}
