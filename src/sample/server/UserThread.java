package sample.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class UserThread implements Callable<Integer> {

    private Scanner fromClient1;
    private PrintWriter toClient1;

    private Scanner fromClient2;
    private PrintWriter toClient2;

    private boolean key[][] = {
            {false, false, true},
            {true, false, false},
            {false, true, false}
    };

    public UserThread(Socket client1, Socket client2) throws IOException {
        this.fromClient1 = new Scanner(client1.getInputStream());
        this.toClient1 = new PrintWriter(client1.getOutputStream(), true);

        this.fromClient2 = new Scanner(client2.getInputStream());
        this.toClient2 = new PrintWriter(client2.getOutputStream(), true);
    }

    @Override
    public Integer call() throws Exception {
        while (true) {
            int player1 = 0;
            int player2 = 0;
            String response1 = fromClient1.nextLine();
            String response2 = fromClient2.nextLine();

            switch (response1) {
                case "1":
                    System.out.println(Thread.currentThread().getName() + " player1 = stone");
                    player1 = 1;
                    break;
                case "2":
                    System.out.println(Thread.currentThread().getName() + " player1 = paper");
                    player1 = 2;
                    break;
                case "3":
                    System.out.println(Thread.currentThread().getName() + " player1 = scissors");
                    player1 = 3;
                    break;
            }
            switch (response2) {
                case "1":
                    System.out.println(Thread.currentThread().getName() + " player2 = stone");
                    player2 = 1;
                    break;
                case "2":
                    System.out.println(Thread.currentThread().getName() + " player2 = paper");
                    player2 = 2;
                    break;
                case "3":
                    System.out.println(Thread.currentThread().getName() + " player2 = scissors");
                    player2 = 3;
                    break;
            }
            if (player1 == player2) {
                toClient1.println("draw");
                toClient2.println("draw");
            } else if (key[player1 - 1][player2 - 1]) {
                toClient1.println("win");
                toClient2.println("lose");
            } else {
                toClient2.println("win");
                toClient1.println("lose");
            }
        }
    }
}
