package com.example.socktdemo.udp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP（User Datagram Protocol）
 * 非面向连接的提供不可靠的数据包式的数据传输协议。类似于从邮局发送信件的过程，发送信件是通过邮局系统一站一站进行传递，中间也有可能丢失。
 * Java中有些类是基于UDP协议来进行网络通讯的，有DatagramPacket、DatagramSocket、MulticastSocket等类。
 */
public class UdpSeverSocketDemo {
    public static void main(String[] args) throws Exception {
        int port = 44432;
        DatagramSocket datagramSocket = new DatagramSocket(port);
        byte[] bytes = new byte[10];
        DatagramPacket receiveDatagramPacket = new DatagramPacket(bytes, bytes.length);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            datagramSocket.receive(receiveDatagramPacket);
            int length = receiveDatagramPacket.getLength();
            String info = new String(receiveDatagramPacket.getData(), receiveDatagramPacket.getOffset(), receiveDatagramPacket.getLength());
            System.out.println("info:" + info + "  length:" + length);
            if (info.endsWith("end")) {
                String receiveData = byteArrayOutputStream.toString();
                System.out.println("receiveData:" + receiveData);
            } else {
                //分批上传的数据进行整合后在使用
                byteArrayOutputStream.write(receiveDatagramPacket.getData(), receiveDatagramPacket.getOffset(), receiveDatagramPacket.getLength());
            }
            ackClient(datagramSocket);
        }

    }


    /**
     * 给客户端回复消息
     *
     * @param serverDataGramSocket
     * @throws IOException
     */
    private static void ackClient(DatagramSocket serverDataGramSocket) throws IOException {
        int clientPort = 44431;
        byte[] bytes = "receive dadta ".getBytes();
        InetAddress inetAddress = InetAddress.getLocalHost();
        DatagramPacket send = new DatagramPacket(bytes, bytes.length, inetAddress, clientPort);
        serverDataGramSocket.send(send);
    }


}
