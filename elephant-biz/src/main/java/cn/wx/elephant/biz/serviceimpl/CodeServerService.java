package cn.wx.elephant.biz.serviceimpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import cn.hutool.core.collection.CollectionUtil;
import cn.wx.elephant.biz.bean.Bo.CodeServerBo;
import cn.wx.elephant.core.ssh.SshClient;
import cn.wx.elephant.core.ssh.SshContextConf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weixin 2023/7/12 4:33 PM
 */
@Slf4j
@Service
public class CodeServerService {

    private static final String userName = "root";

    private static final String remoteHost = "101.35.121.177";

    private static final String remotePass = "WEIxin_123456.";

    private static final Map<String, CodeServerBo> map = new ConcurrentHashMap<>();

    private static final Map<String,Integer> portMap = new ConcurrentHashMap<>();

    public CodeServerBo getCode(String userId){
        // 链接到服务器
        SshClient client = new SshClient(new SshContextConf(userName, remoteHost, remotePass));

        String password = userId+"123456";
        String port = getPort();

        if (map.get(userId) != null) {
            return map.get(userId);
        }

        // 获取ssh命令
        String ssh = getSsh(port, password, userId);
        // 启动容器命令，得到容器id
        List<String> codeIds = client.executeExec(ssh);
        if (CollectionUtil.isEmpty(codeIds) || codeIds.size() > 1) {
            log.error("启动失败");
            return null;
        }
        // 组装数据返回给前端
        String codeId = codeIds.get(0);
        CodeServerBo codeServerBo = new CodeServerBo();
        codeServerBo.setUserId(userId);
        codeServerBo.setUrl(remoteHost+":"+port);
        codeServerBo.setPassword(password);
        codeServerBo.setCodeServerId(codeId);
        map.put(userId, codeServerBo);
        return codeServerBo;
    }

    private String getPort(){
        String remotePortKey = "remotePortKey";
        if(portMap.get(remotePortKey) == null){
            portMap.put(remotePortKey,1201);
            return String.valueOf(1201);
        }else{
            portMap.put(remotePortKey,portMap.get(remotePortKey)+1);
            return String.valueOf(portMap.get(remotePortKey));
        }
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
}
