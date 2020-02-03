package com.example.socktdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SystemSocketDemo {
    public static void main(String[] args) throws Exception {
        int serverPort = 55520;
        String serverHostName = "127.0.0.1";
        //创建sokcet时就会建立连接通道
        final Socket serverSocket = new Socket(serverHostName, serverPort);
        dealInputWithNewThread(serverSocket);
        dealReceiveData(serverSocket);
    }


    /***
     * 接收服务端发送的消息
     * @param serverSocket
     * @throws IOException
     */
    private static void dealReceiveData(Socket serverSocket) throws IOException {
        String newLine = "";
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        while ((newLine = reader1.readLine()) != null) {
            System.out.println("newLine:" + newLine);

        }
    }


    /**
     * 开启线程接收从键盘输入的数据，并通过socket发送给服务端。
     *
     * @param serverSocket
     */
    private static void dealInputWithNewThread(final Socket serverSocket) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    handleInputData(serverSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 发送消息给服务端
     *
     * @param serverSocket
     * @throws IOException
     */
    private static void handleInputData(Socket serverSocket) throws IOException {
        //socket输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        //键盘输入流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        //不断从键盘读取数据发送给服务端
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            writer.write(line);
            //增加一个换行符，服务端readLine就会知道来了一行数据，否则就一值读取数据，直到读取到一行结束的标记。（****）
            writer.newLine();
            writer.flush();
        }
        bufferedReader.close();
        writer.close();
    }


}
