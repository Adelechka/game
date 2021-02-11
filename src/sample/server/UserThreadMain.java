package sample.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserThreadMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<Integer>> results = new LinkedList<>();

            while (true) {
                System.out.println(Thread.currentThread().getName() + " waiting for player1");
                Socket client1 = serverSocket.accept();

                System.out.println(Thread.currentThread().getName() + " waiting for player2");
                Socket client2 = serverSocket.accept();

                System.out.println(Thread.currentThread().getName() + " game started!");

                //BufferedReader writer1 = client1.getInputStream();
                Future<Integer> result = executorService.submit(new UserThread(client1, client2));
                results.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
