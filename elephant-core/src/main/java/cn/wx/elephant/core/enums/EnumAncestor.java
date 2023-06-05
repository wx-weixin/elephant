package cn.wx.elephant.core.enums;

/**
 * 所有枚举的直接或间接父类
 *
 * 用法参考：
 *
 * @param <C> 枚举的code的类型 e.g.: String
 * @author weixin 2021/3/22 5:59 下午
 */
public interface EnumAncestor<C> {

    /**
     * 获取枚举的code的方式
     *
     * 当派生的枚举对象转化为JSON时，JSON Value会来自这个方法
     */
    C getCode();

    /**
     * 获取枚举的标题的方式
     */
    String getTitle();

    /**
     * 转化成Javabean形式
     */
    default CodeAndTitle asJavabean() {
        return new CodeAndTitle(getCode(), getTitle());
    }

}
