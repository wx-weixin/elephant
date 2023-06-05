package cn.wx.elephant.core.function;

/**
 * 自定义映射类
 *
 * @author linbh 2021-07-22
 */
public interface CustomMapping<S, T> {

    void mapping(S source, T target);

}
