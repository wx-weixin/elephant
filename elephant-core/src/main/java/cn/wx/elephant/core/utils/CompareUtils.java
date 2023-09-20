package cn.wx.elephant.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.wx.elephant.core.annotation.FieldAnnotation;
import cn.wx.elephant.core.enums.BaseStatusCodeEnum;
import cn.wx.elephant.core.enums.EnumAncestor;
import cn.wx.elephant.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * 对象比较工具
 *
 * @author weixin
 * @date 2020/9/3 3:46 下午
 */
@Slf4j
public class CompareUtils {

    /**
     * 老对象和新对象字段比较，生成文案描述。
     */
    public static String compareObject(Object old, Object newObj) {
        if (!old.getClass().equals(newObj.getClass())) {
            return null;
        }

        String conent = "";
        Field[] oldFields = old.getClass().getDeclaredFields();
        Field[] fields = newObj.getClass().getDeclaredFields();
        for (Field oldF : oldFields) {
            oldF.setAccessible(true);

            //获取字段注解
            FieldAnnotation fieldAnnotation = oldF.getAnnotation(FieldAnnotation.class);
            if (fieldAnnotation == null) {
                continue;
            }

            for (Field newF : fields) {
                newF.setAccessible(true);
                if (oldF.getName().equals(newF.getName())) {
                    Object oldValue;
                    Object newValue;
                    try {
                        oldValue = oldF.get(old);
                        newValue = newF.get(newObj);
                    } catch (IllegalAccessException e) {
                        throw new BaseException(BaseStatusCodeEnum.B000001);
                    }

                    if (oldValue == null && newValue == null) {
                        continue;
                    }
                    String result = "";
                    if (oldValue instanceof Collection<?>  || newValue instanceof Collection<?> ) {
                        Collection<?> oldCollection = null;
                        if (oldValue instanceof Collection<?>) {
                            oldCollection = (Collection<?>) oldValue;
                        }
                        Collection<?> newCollection = (Collection<?>) newValue;
                        String oldContent = JsonUtils.toJson(oldCollection);
                        String newContent = JsonUtils.toJson(newCollection);
                        if (!oldContent.equals(newContent)) {
                            result = getResult(fieldAnnotation.describe(), oldContent, newContent);
                        }
                    } else if (oldValue instanceof Map<?, ?>) {
                        log.warn("暂不处理");
                    } else {
                        if (oldValue != null && newValue != null) {
                            if (oldValue instanceof BigDecimal oldBig) {
                                BigDecimal newBig = (BigDecimal) newValue;
                                if (oldBig.compareTo(newBig) != 0) {
                                    result = getResult(fieldAnnotation.describe(), oldValue, newValue);
                                }
                            } else {
                                if (!oldValue.equals(newValue)) {
                                    result = getResult(fieldAnnotation.describe(), oldValue, newValue);
                                }
                            }
                        } else {
                            result = getResult(fieldAnnotation.describe(), oldValue, newValue);
                        }
                    }
                    conent = getContent(conent, result);
                }
            }

        }
        return conent;
    }

    private static String getResult(String desc, Object oldValue, Object newValue) {
        return String.format("字段【%s】由【%s】改为【%s】", desc, oldValue, newValue);
    }

    private static String getContent(String conent, String result) {
        if(StrUtil.isEmpty(result)){
            return conent;
        }
        if (StrUtil.isEmpty(conent)) {
            return result;
        }
        return conent + ", " + result;
    }

    /**
     * 生成需要的操作日志描述
     */
    public static String ObjectToLogString(Object object) {
        String conent = "";
        if (object == null) {
            return conent;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            //获取字段注解,没有注解的字段不需要处理
            FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
            if (fieldAnnotation == null) {
                continue;
            }
            Object value = null;
            try {
                value = field.get(object);
                //获取对于的枚举值
                value = getEnumTitleIfNeed(value, fieldAnnotation.targetEnum());
            } catch (IllegalAccessException e) {
                throw new BaseException(BaseStatusCodeEnum.B000001);
            }
            String describe = fieldAnnotation.describe();
            String result = String.format("%s:%s", describe, value);

            if ("".equals(conent)) {
                conent = result;
            } else {
                conent = conent + "\n" + result;
            }
        }
        return conent;
    }

    private static Object getEnumTitleIfNeed(Object value, @SuppressWarnings("rawtypes") Class<? extends EnumAncestor> targetEnumClazz) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (EnumAncestor.class.equals(targetEnumClazz)) {
            return value;
        }
        for (EnumAncestor<?> enumConstant : targetEnumClazz.getEnumConstants()) {
            if (String.valueOf(enumConstant.getCode()).equals(String.valueOf(value))) {
                return enumConstant.getTitle();
            }
        }
        return value;
    }

}
