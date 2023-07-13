package cn.ex.elephant.core.test.ssh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import cn.hutool.core.collection.CollectionUtil;
import cn.wx.elephant.core.ssh.SshClient;
import cn.wx.elephant.core.ssh.SshContextConf;
import cn.wx.elephant.core.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/7/12 3:20 PM
 */
@Slf4j
public class TestCodeServer {

    private SshClient client;

    private Map<String, CodeServerBo> map = new HashMap<>();

    private static final String userName = "root";

    private static final String remoteHost = "101.35.121.177";

    private static final String remotePass = "WEIxin_123456.";


    @BeforeEach
    public void before() {
        // 链接到服务器
        client = new SshClient(new SshContextConf(userName, remoteHost, remotePass));
    }


    @DisplayName("根据用户id获取codeServer")
    @Test
    public void getCode() {
        String userId = "user_1";
        String password = "123456";
        String port = "1202";

        if (map.get(userId) != null) {
            CodeServerBo codeServerBo = map.get(userId);
            System.out.println(JsonUtils.toJson(codeServerBo));
            return;
        }

        // 启动容器分给用户
        String ssh = getSsh(port, password, userId);
        List<String> codeIds = client.executeExec(ssh);
        if (CollectionUtil.isEmpty(codeIds) || codeIds.size() > 1) {
            log.error("启动失败");
            return;
        }
        String codeId = codeIds.get(0);
        CodeServerBo codeServerBo = new CodeServerBo();
        codeServerBo.setUserId(userId);
        codeServerBo.setUrl(port);
        codeServerBo.setPassword(password);
        codeServerBo.setCodeServerId(codeId);
        map.put(userId, codeServerBo);

    }

    private String getSsh(String port, String password, String userId) {
        String ssh = """
                docker run -d  \\
                --name=jdk-code-server-web_%s \\
                -p %s:8443 \\
                -e PASSWORD=%s \\
                -e TZ=Asia/Shanghai \\
                --restart unless-stopped \\
                jdk-c-code-server
                """;

        return String.format(ssh, userId, port, password);
    }

    @Test
    public void a() {
        System.out.println(getSsh("1202", "123456", "user_1"));
    }

}
