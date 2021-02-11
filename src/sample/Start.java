package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Controller;
import sample.server.ServerListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Start extends Application {

    private PrintWriter toServer;
    private Scanner fromServer;
    private Socket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Parent root1 = loader1.load();
        primaryStage.setTitle("Камень, ножницы, бумага");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        StartController startController = loader.getController();
        Controller controller = loader1.getController();

        startController.singlePlayerBtn.setOnMouseClicked(event -> {
            primaryStage.setTitle("Камень, ножницы, бумага");
            primaryStage.getScene().setRoot(root1);
            primaryStage.show();
        });

        startController.multiplayerBtn.setOnMouseClicked(mouseEvent -> {
            primaryStage.setTitle("Камень, ножницы, бумага");
            primaryStage.getScene().setRoot(root1);
            primaryStage.show();

            try {
                socket = new Socket("localhost", 12345);
                toServer = new PrintWriter(socket.getOutputStream(), true);
                fromServer = new Scanner(socket.getInputStream());
                ServerListener serverListener = new ServerListener(socket, loader1.getController());
                serverListener.start();

                controller.stoneBtn.setOnMouseClicked(mouseEvent1 -> {
                    controller.imageView.setImage(new Image(Controller.STONE_IMG));
                    sendMessage("1");
                    controller.scissorsBtn.setDisable(true);
                    controller.paperBtn.setDisable(true);
                    controller.stoneBtn.setDisable(true);

                });

                controller.paperBtn.setOnMouseClicked(mouseEvent1 -> {
                    controller.imageView.setImage(new Image(Controller.PAPER_IMG));
                    sendMessage("2");
                    controller.scissorsBtn.setDisable(true);
                    controller.paperBtn.setDisable(true);
                    controller.stoneBtn.setDisable(true);
                });

                controller.scissorsBtn.setOnMouseClicked(mouseEvent1 -> {
                    controller.imageView.setImage(new Image(Controller.SCISSORS_IMG));
                    sendMessage("3");
                    controller.scissorsBtn.setDisable(true);
                    controller.paperBtn.setDisable(true);
                    controller.stoneBtn.setDisable(true);
                });
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        });
    }

    private void sendMessage(String move) {
        toServer.println(move);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
