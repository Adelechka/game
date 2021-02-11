package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Random random = new Random();

    private static final int STONE = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    public static final String STONE_IMG = "/img/stone.png";
    public static final String PAPER_IMG = "/img/paper.png";
    public static final String SCISSORS_IMG = "/img/scissors.png";

    private static Integer player;

    @FXML
    public ToolBar toolBar;

    @FXML
    public Button stoneBtn;

    @FXML
    public Button paperBtn;

    @FXML
    public Button scissorsBtn;

    @FXML
    public Image image;

    @FXML
    public Label wins;

    @FXML
    public Label loses;

    @FXML
    public Label rounds;

    @FXML
    public ImageView imageView;

    @FXML
    public void playerChoice(MouseEvent event) throws InterruptedException {
        switch (((Button) event.getSource()).getId()) {
            case "paperBtn":
                player = PAPER;
                break;
            case "stoneBtn":
                player = STONE;
                break;
            case "scissorsBtn":
                player = SCISSORS;
                break;
        }

        paperBtn.setDisable(true);
        stoneBtn.setDisable(true);
        scissorsBtn.setDisable(true);

        game(player, random.nextInt(2) + 1);
        restartGame();
    }

    public void game(int player, int computer) throws InterruptedException {
        switch (player) {
            case STONE:
                image = new Image(STONE_IMG);
                break;
            case PAPER:
                image = new Image(PAPER_IMG);
                break;
            case SCISSORS:
                image = new Image(SCISSORS_IMG);
                break;
        }
        imageView.setImage(image);

        if (player != computer) {
            boolean key[][] = {
                    {false, false, true},
                    {true, false, false},
                    {false, true, false}
            };

            boolean isPlayerWinner = key[player - 1][computer - 1];

            if (isPlayerWinner) {
                wins.setText(String.valueOf(Integer.parseInt(wins.getText()) + 1));
                rounds.setText(String.valueOf(Integer.parseInt(rounds.getText()) + 1));
            } else {
                loses.setText(String.valueOf(Integer.parseInt(loses.getText()) + 1));
                rounds.setText(String.valueOf(Integer.parseInt(rounds.getText()) + 1));
            }
        } else {
            rounds.setText(String.valueOf(Integer.parseInt(rounds.getText()) + 1));
        }
    }

    @FXML
    public void restartGame() {
        Image image = new Image("img/question.png");
        paperBtn.setDisable(false);
        stoneBtn.setDisable(false);
        scissorsBtn.setDisable(false);
        imageView.setImage(image);
        player = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
