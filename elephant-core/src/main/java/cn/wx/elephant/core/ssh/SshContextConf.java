package cn.wx.elephant.core.ssh;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author weixin 2023/7/12 10:37 AM
 */
@Data
@AllArgsConstructor
public class SshContextConf {
    private String      remoteHost;  //远程连接服务器地址
    private int         remotePort = 22; //远程连接服务器端口
    private String      userName ; // 远程连接的用户名
    private String      password; //远程连接用户的密码
    private String identity = "~/.ssh/id_rsa";
    private String passphrase = "";

    public SshContextConf(String userName, String remoteHost, String password) {
        this.userName = userName;
        this.remoteHost = remoteHost;
        this.password = password;
    }

}
