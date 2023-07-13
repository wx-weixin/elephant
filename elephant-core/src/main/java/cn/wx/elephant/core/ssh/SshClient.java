package cn.wx.elephant.core.ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author weixin 2023/7/12 10:37 AM
 */
public class SshClient {


    private JSch jsch;

    private Session session;

    private SshContextConf conf;

    public SshClient(SshContextConf conf) {
        this.conf = conf;
        jsch = new JSch();
        connect();
    }

    /**
     * 关闭ssh连接
     */
    public void close(){
        session.disconnect();
    }

    /**
     * 本地端口转发
     * @param localPort         被转发的本地端口
     * @param remoteHost        转发后的服务器
     * @param remoteHostPost    转发后的服务器的端口
     */
    public void forwardingL(int localPort, String remoteHost, int remoteHostPost)   {
        if (session == null)
            throw new RuntimeException("please establish ssh connection before forwardingL");

        try {
            int assinged_port = session.setPortForwardingL(localPort, remoteHost, remoteHostPost);
            System.out.println("本地端口转发成功  from localhost:"+assinged_port+" to "+remoteHost+":"+remoteHostPost);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 远程端口转发
     * @param remotePort   被转发的远程端口
     * @param localHost    转发后的服务器地址
     * @param localPort    转发后的服务器的端口
     */
    public void forwardingR(int remotePort, String localHost, int localPort){
        if (session == null)
            throw new RuntimeException("please establish ssh connection before forwardingR");

        try {
            session.setPortForwardingR(remotePort, localHost, localPort);
            System.out.println("远程端口转发成功  from "+conf.getRemoteHost()+":"+remotePort+" to "+localHost+":"+localPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消已分配的本地端口转发
     * @param localPort        被转发的本地端口
     */
    public void delForwardingL(int localPort){
        try {
            session.delPortForwardingL(localPort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消已分配的远程端口转发
     * @param remotePort        被转发的远程端口
     */
    public void delForwardingR(int remotePort){
        try {
            session.delPortForwardingR(remotePort);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**  执行指令没有返回结果 */
    public void executeExecN(String command)  {
        executeExec(command,false);
    }

    /**  执行指令有返回结果 */
    public List<String> executeExec(String command)  {
        return executeExec(command,true);
    }


    /**
     * 执行指令
     * @param command           指令
     * @param needResult        是否需要返回急指令执行结果
     * @return                  指令执行结果
     */
    public List<String> executeExec(String command,boolean needResult)  {
        isDisconnect();
        List<String> resultLines = null;
        ChannelExec execChannel = null;
        try {
            execChannel = (ChannelExec)session.openChannel("exec");
            execChannel.setCommand(command);
            execChannel.setErrStream(System.err);
            execChannel.connect(10000);
            if (needResult)
                resultLines = collectResult(execChannel.getInputStream());
        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            if (execChannel != null) {
                try {
                    execChannel.disconnect();
                } catch (Exception e) {
                    System.out.println("JSch channel disconnect error:"+e);
                }
            }
        }
        return resultLines;
    }


    /**
     * 收集脚本执行的结果
     * @param input         ssh连接通道输入流
     * @return              脚本执行的结果
     */
    private List<String> collectResult(InputStream input) {
        List<String> resultLines = new ArrayList<>();
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
            String inputLine = null;
            while((inputLine = inputReader.readLine()) != null) {
                resultLines.add(inputLine);
            }
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    System.err.println("JSch inputStream close error:"+e);
                }
            }
        }
        return resultLines;
    }

    /**
     * 上传文件
     * @param lfile     被上传的本地文件
     * @param rfile     上传后的服务器保存的位置
     */
    public void upload(String lfile,String rfile){
        FileInputStream fis=null;
        try{
            boolean ptimestamp = false;
            // exec 'scp -t rfile' remotely
            rfile=rfile.replace("'", "'\\''");
            rfile="'"+rfile+"'";
            String command="scp " + (ptimestamp ? "-p" :"") +" -t "+rfile;
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out=channel.getOutputStream();
            InputStream in=channel.getInputStream();

            channel.connect();

            if(checkAck(in)!=0){
                return;
            }

            File _lfile = new File(lfile);

            if(ptimestamp){
                command="T "+(_lfile.lastModified()/1000)+" 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command+=(" "+(_lfile.lastModified()/1000)+" 0\n");
                out.write(command.getBytes()); out.flush();
                if(checkAck(in)!=0){
                    System.exit(0);
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize=_lfile.length();
            command="C0644 "+filesize+" ";
            if(lfile.lastIndexOf('/')>0){
                command+=lfile.substring(lfile.lastIndexOf('/')+1);
            }
            else{
                command+=lfile;
            }
            command+="\n";
            out.write(command.getBytes()); out.flush();
            if(checkAck(in)!=0){
                return;
            }

            // send a content of lfile
            fis=new FileInputStream(lfile);
            byte[] buf=new byte[1024];
            while(true){
                int len=fis.read(buf, 0, buf.length);
                if(len<=0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis=null;
            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();
            if(checkAck(in)!=0){
                return;
            }
            out.close();
            channel.disconnect();
        } catch(Exception e){
            e.printStackTrace();
            try{if(fis!=null)
                fis.close();
            }catch(Exception e1){
                e1.printStackTrace();
            }
        }
    }

    /**
     * 下载文件
     * @param source            被下载的文件
     * @param destination       下载后本地保存的路径
     * @return
     */
    public long download(String source, String destination) {
        FileOutputStream fileOutputStream = null;
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("scp -f " + source);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] buf = new byte[1024];
            //send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            while(true) {
                if (checkAck(in) != 'C') {
                    break;
                }
            }
            //read '644 '
            in.read(buf, 0, 4);
            long fileSize = 0;
            while (true) {
                if (in.read(buf, 0, 1) < 0) {
                    break;
                }
                if (buf[0] == ' ') {
                    break;
                }
                fileSize = fileSize * 10L + (long)(buf[0] - '0');
            }
            String file = null;
            for (int i = 0; ; i++) {
                in.read(buf, i, 1);
                if (buf[i] == (byte) 0x0a) {
                    file = new String(buf, 0, i);
                    break;
                }
            }
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            // read a content of lfile
            if (Files.isDirectory(Paths.get(destination))) {
                fileOutputStream = new FileOutputStream(destination + File.separator +file);
            } else {
                fileOutputStream = new FileOutputStream(destination);
            }
            long sum = 0;
            while (true) {
                int len = in.read(buf, 0 , buf.length);
                if (len <= 0) {
                    break;
                }
                sum += len;
                if (len >= fileSize) {
                    fileOutputStream.write(buf, 0, (int)fileSize);
                    break;
                }
                fileOutputStream.write(buf, 0, len);
                fileSize -= len;
            }
            return sum;
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    private int checkAck(InputStream in) throws IOException{
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;

        if(b==1 || b==2){
            StringBuffer sb=new StringBuffer();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                System.out.print(sb.toString());
            }
            if(b==2){ // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    /** 判断是否断开ssh连接进行重连 */
    private void isDisconnect(){
        if (!session.isConnected()){
            connect();
        }
    }

    /** 建立ssh连接 */
    private void connect(){
        try {
            if (Files.exists(Paths.get(conf.getIdentity()))) {
                jsch.addIdentity(conf.getIdentity(), conf.getPassphrase());
            }
            session =  jsch.getSession(conf.getUserName(),conf.getRemoteHost(),conf.getRemotePort());
            session.setPassword(conf.getPassword());
            session.setConfig("StrictHostKeyChecking", "no"); // 关闭确认提示
            session.connect(30000);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
