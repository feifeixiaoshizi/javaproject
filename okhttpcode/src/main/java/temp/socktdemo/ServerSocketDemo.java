package temp.socktdemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1000);
        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();
            handleData(socket);
        }

    }

    /**
     * 多个线程使用同个流传递数据
     * line:thread1:0
     * line:thread2:0
     * line:thread2:1
     * line:thread1:1
     * line:thread1:2
     * line:thread2:2
     * line:thread2:3
     * line:thread1:3
     * line:thread2:4
     * line:thread1:4
     *
     * @param socket
     */
    private static void handleData(Socket socket) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line:" + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
