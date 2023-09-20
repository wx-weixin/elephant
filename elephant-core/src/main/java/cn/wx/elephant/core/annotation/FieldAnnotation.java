package cn.wx.elephant.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import cn.wx.elephant.core.enums.EnumAncestor;

/**
 * @author weixin
 * @date 2020/6/17 4:21 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnnotation {

    /**
     * 字段说明
     */
    String describe() default "";

    /**
     * 指定要转化成对应枚举的值
     */
    Class<? extends EnumAncestor> targetEnum() default EnumAncestor.class;

}
