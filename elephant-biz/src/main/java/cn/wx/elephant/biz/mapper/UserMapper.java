package cn.wx.elephant.biz.mapper;

import java.util.List;
import cn.wx.elephant.biz.bean.entity.User;

/**
 * @author weixin 2023/6/1 4:29 PM
 */
public interface UserMapper {

    List<User> queryAll();
}
