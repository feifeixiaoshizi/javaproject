package com.example.socktdemo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws Exception{
        Socket serverSocket = new Socket("127.0.0.1",1000);


        Writer writer = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        new Task(writer,"thread1:").start();
        new Task(writer,"thread2:").start();
    }

    private static class Task{
        //多个线程使用同个流通道传送数据
        public Writer writer;
        public String name;

        public Task(Writer writer,String name) {
            this.writer = writer;
            this.name = name;
        }

        public void start(){
            new Thread(){
                @Override
                public void run() {
                    for (int i=0;i<100;i++){
                        System.out.println("I:"+i);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            writer.append(name+i+"\r\n");
                            writer.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
