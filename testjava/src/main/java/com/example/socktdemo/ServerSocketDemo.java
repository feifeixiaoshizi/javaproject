package com.example.socktdemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一、网络模式：
 * 1、在网络上，readLine()是阻塞模式，也就是说如果readLine()读取不到数据的话，会一直阻塞，而不是返回null，所以如果你想要在while循环后执形相关操作是不可能的，因为while()里面是一个死循环，一旦读不到数据，它又开始阻塞，因此永远也无法执形while()循环外面的操作，所以应该把操作放在while循环里面。（在我做的即时通讯里，为了能够不断获取服务器返回的消息，就是用这种方法，不断去服务器获取消息，一旦有就返回。）
 * 2、在while()里面判断readLine()！= null的时候要赋值给一个String，因为如果不为null，那么这时候已经读了一行。如果用while (br.readLine()!=null)，那么下面没法再获取到这一行，所以应该用
 * while ((line = br.readLine())!=null){}
 * <p>
 * 3、readLine()通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行,所以我们在发送数据的时候要再后面加上这些标志符，否则程序会阻塞。而我是直接用下面这种方法：
 * PrintStream ps = new PrintStream(socket.getOutputStream(), true, "UTF-8");
 * ps.println()。
 * ps.println()已经包含换行了，所以不要用print()，若是要就要在后面加上换行符;
 * <p>
 * 4、readLine()只有在数据流发生异常或者另一端被close()掉时，才会返回null值。
 * <p>
 * 二、读取文件模式：
 * 1、readLine()什么时候才会返回null呢？读取到文件等的结尾时候。（注意和网络上的是不一样的）。
 * 如果不指定buffer大小，则readLine()使用的buffer有8192个字符。在达到buffer大小之前，只有遇到"/r"、"/n"、"/r/n"才会返回。
 */
public class ServerSocketDemo {
    private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(50);

    public static void main(String[] args) throws Exception {
        int port = 55520;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();
            //通过线程池提供服务端并发
            threadPoolExecutor.submit(new Task(socket));
        }

    }

    /**
     * 处理客户端发送消息的任务
     */
    private static class Task implements Callable<Boolean> {
        Socket socket = null;

        public Task(Socket socket) {
            this.socket = socket;
        }

        @Override
        public Boolean call() throws Exception {
            try {
                handleData(socket);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private static void handleData(Socket socket) throws IOException {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("line:" + line);
                //读到数据后发送消息通知客户端
                bufferedWriter.write("receive data:" + line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            socket.close();
        }
    }
}
