package cn.ex.elephant.core.test.ssh;

import lombok.Data;

/**
 * @author weixin 2023/7/12 3:38 PM
 */
@Data
public class CodeServerBo {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 服务器地址
     */
    private String url;

    /**
     * 密码
     */
    private String password;

    /**
     * 容器id
     */
    private String CodeServerId;
}
