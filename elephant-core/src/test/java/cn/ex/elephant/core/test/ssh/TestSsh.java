package cn.ex.elephant.core.test.ssh;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import cn.wx.elephant.core.ssh.SshClient;
import cn.wx.elephant.core.ssh.SshContextConf;

/**
 * @author weixin 2023/7/12 10:46 AM
 */
public class TestSsh {

    /**
     * 正向代理：A不能到C。A可以到B，B到C。 A访问B，B再代理到C。
     * 启动之后，在浏览器访问本地 localhost:9997,会被代理到 101.35.121.177:1104。
     */
    @DisplayName("正向代理测试")
    @Test
    public void forwardProxy(){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf("root", "101.35.121.177", "WEIxin_123456."));
        // 本案例只做了 A->B的代理。 把本地端口 9997 代理到 服务器1104上。
        client.forwardingL(9997,"101.35.121.177",1104);

        while (true){

        }
    }


    /**
     * todo 目前反向代理有问题。 服务器无法连接到本地，还需要处理
     * 启动之后，在浏览器访问本地 101.35.121.177:1106 会被代理到 127.0.0.1:5101
     */
    @DisplayName("反向代理测试")
    @Test
    public void reverseProxy(){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf("root", "101.35.121.177", "WEIxin_123456."));

        client.forwardingR(1106,"127.0.0.1",5101);

        while (true){

        }
    }

    /**
     * 发送指令
     */
    @DisplayName("发送指令")
    @Test
    public void sshExec(){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf("root", "101.35.121.177", "WEIxin_123456."));

        // 发送单条命令
        List<String> result = client.executeExec("docker port 4b6a27d8439a");
        result.forEach(System.out::println);

        // 发送复合命令
/*
      每个命令之间用 ; 隔开。
          说明：各命令的执行给果，不会影响其它命令的执行。换句话说，各个命令都会执行，但不保证每个命令都执行成功。
      每个命令之间用 && 隔开。
          说明：若前面的命令执行成功，才会去执行后面的命令。这样可以保证所有的命令执行完毕后，执行过程都是成功的。
      每个命令之间用 || 隔开。
          说明：|| 是或的意思，只有前面的命令执行失败后才去执行下一条命令，直到执行成功一条命令为止。
   */
//        client.executeExecN("mkdir demo1 && cd demo1 && touch xxx.txt");

    }


    /**
     * 上传文件
     */
    @DisplayName("上传文件")
    @Test
    public void uploadFile(){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf("root", "101.35.121.177", "WEIxin_123456."));

        client.upload("/Users/zaizai/Downloads/时间问题.txt","demo1/");

    }

    /**
     * 下载文件
     */
    @DisplayName("下载文件")
    @Test
    public void downloadFile(){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf("root", "101.35.121.177", "WEIxin_123456."));

        client.download("demo1/时间问题.txt","/Users/zaizai/Downloads/");

    }
}
