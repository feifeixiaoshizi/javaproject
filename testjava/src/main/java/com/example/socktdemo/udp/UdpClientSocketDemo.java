package com.example.socktdemo.udp;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClientSocketDemo {
    public static void main(String[] args) throws Exception{
        int clientPort = 44431;
        DatagramSocket datagramSocket = new DatagramSocket(clientPort);
        byte[] bytes = "12345678910qwertyuiopasdfghjkl".getBytes();
        InetAddress inetAddress = InetAddress.getLocalHost();
        int port = 44432;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        byte [] buffer = new byte[10];
        /**
         * 根据服务端的buffer的大小发送数据
         */
        while (byteArrayInputStream.read(buffer,0,buffer.length)>=buffer.length){
            DatagramPacket send = new DatagramPacket(buffer,buffer.length,inetAddress,port);
            datagramSocket.send(send);
        }
        buffer = "end".getBytes();
        DatagramPacket send = new DatagramPacket(buffer,buffer.length,inetAddress,port);
        datagramSocket.send(send);
        receiveData(datagramSocket);
    }

    private static void receiveData( DatagramSocket datagramSocket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagramPacket =new DatagramPacket(buffer,buffer.length);
        datagramSocket.receive(datagramPacket);
        String info = new String(datagramPacket.getData(),datagramPacket.getOffset(),datagramPacket.getLength());
        System.out.println("info:"+info);

    }

}
