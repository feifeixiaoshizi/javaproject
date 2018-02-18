package com.example.ThreadPool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2017/12/19 0019.
 *
 * 浏览的原理：
 * 1：网络协议  物理层（bit）-》 数据链路层（帧，交换机）-》  网络层（数据包，路由器，ip）-》  传输层（tcp）-》会话层-》表示层-》应用层（Http，Ftp）
 * 2：http主要是利用传输层，传递特定格式的数据。
 * 3：传输层建立的是连个节点之间的连接，每个节点都是由（ip+port）作为唯一标识的。
 *
 */

public class SocketConnectHttp {

    public static void main(String[] args) throws Exception {
        InetSocketAddress inetAddress = new InetSocketAddress("www.baidu.com", 80);
        Socket socket = new Socket();
        socket.connect(inetAddress, 100000);
        socket.setKeepAlive(true);
        boolean isConnected = socket.isConnected();
       if(isConnected){
           communcate(socket);
       }

    }

    public static void communcate(Socket socket) {
        // 注意这里必须制定请求方式 地址 注意空格
        StringBuffer sb = new StringBuffer("GET http://www.baidu.com/ HTTP/1.1\r\n");
        // 以下为请求头
        sb.append("Host: www.baidu.com\r\n");
        sb.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0\r\n");
        sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
        sb.append("Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        // 注意这里不要使用压缩 否则返回乱码
        sb.append("Accept-Encoding: \r\n");
        sb.append("Connection: keep-alive\r\n");
        sb.append("Upgrade-Insecure-Requests: 1\r\n");
        // 注意这里要换行结束请求头
        sb.append("\r\n");
        System.out.println(sb.toString());
        try {
            OutputStream os = socket.getOutputStream();
            os.write(sb.toString().getBytes());

            InputStream is = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            System.out.println(new String(baos.toByteArray()));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
