package sample.server;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sample.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ServerListener extends Service<String> {

    private Socket socket;
    private Scanner fromServer;
    private Controller controller;
    public ServerListener(Socket socket, Controller controller) throws IOException {
        this.socket = socket;
        this.fromServer = new Scanner(socket.getInputStream());
        this.controller = controller;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                int rounds = 0;
                int wins = 0;
                int loses = 0;
                while (!socket.isInputShutdown()) {
                    if (fromServer.hasNext()) {
                        String message = fromServer.nextLine();
                        switch (message) {
                            case "draw":
                                System.out.println(message);
                                rounds++;
                                int finalRounds = rounds;
                                Platform.runLater(() -> {controller.rounds.setText(String.valueOf(finalRounds));
                                    controller.scissorsBtn.setDisable(false);
                                    controller.paperBtn.setDisable(false);
                                    controller.stoneBtn.setDisable(false);
                                });
                                break;
                            case "lose":
                                System.out.println(message);
                                rounds++;
                                loses++;
                                int finalRounds1 = rounds;
                                int finalLoses = loses;
                                Platform.runLater(() -> {
                                    controller.rounds.setText(String.valueOf(finalRounds1));
                                    controller.loses.setText(String.valueOf(finalLoses));
                                    controller.scissorsBtn.setDisable(false);
                                    controller.paperBtn.setDisable(false);
                                    controller.stoneBtn.setDisable(false);
                                });
                                break;
                            case "win":
                                System.out.println(message);
                                rounds++;
                                wins++;
                                int finalRounds2 = rounds;
                                int finalWins = wins;
                                Platform.runLater(() -> {
                                    controller.rounds.setText(String.valueOf(finalRounds2));
                                    controller.wins.setText(String.valueOf(finalWins));
                                    controller.scissorsBtn.setDisable(false);
                                    controller.paperBtn.setDisable(false);
                                    controller.stoneBtn.setDisable(false);
                                });
                                break;
                            default:
                                System.out.println(message);
                                break;
                        }
                    }
                }
                return null;
            }
        };
    }
}

