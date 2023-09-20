package cn.wx.elephant.core.utils;

import java.util.Collection;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author weixin 2023/9/20 8:29 AM
 */
public class ColUtil {

    public static <T> String  listToString(Collection<T> collection){
        String content = "";
        if(CollectionUtil.isEmpty(collection)){
            return content;
        }

        for (T o : collection) {
            if(StrUtil.isEmpty(content)){
                content = content + o.toString();
            }else{
                content = content + "," + o.toString();
            }
        }
        return content;
    }
}
